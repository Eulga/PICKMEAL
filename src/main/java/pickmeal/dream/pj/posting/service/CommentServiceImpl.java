package pickmeal.dream.pj.posting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pickmeal.dream.pj.member.domain.Member;
import pickmeal.dream.pj.member.service.MemberAchievementService;
import pickmeal.dream.pj.member.service.MemberService;
import pickmeal.dream.pj.posting.domain.Comment;
import pickmeal.dream.pj.posting.repository.CommentDao;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao cd;
	
	@Autowired
	private MemberService ms;
	
	@Autowired
	private MemberAchievementService mas;

	@Override
	public Comment addComment(Comment comment) {
		// 추가 후
		cd.addComment(comment);
		// 아이디랑 등록 날짜까지 한 것을 가져와야한다.
		comment = cd.findLastAddComment(comment.getMember().getId(), comment.getPosting().getCategory());
		// 멤버 셋팅 (필요한 값만 셋팅한다)
		comment.setMember(doSettingMemberForComment(ms.findMemberById(comment.getMember().getId())));
		// 포스팅 셋팅을 할 필요는 없다. (아이디만 알면 된다)
		
		return comment;
	}

	@Override
	public Comment updateComment(Comment comment) {
		Comment beforeComment = findCommentById(comment);
		// 수정할 댓글 내용이 이 전과 같으면 업데이트 불가
		if (beforeComment.getContent().equals(comment.getContent())) {
			return comment;
		}
		// 업데이트 후
		cd.updateComment(comment);
		// member를 셋팅해서 들고간다.
		Member member = ms.findMemberById(comment.getMember().getId());
		comment.setMember(doSettingMemberForComment(member));
		return comment;
	}

	@Override
	public boolean deleteComment(Comment comment) {
		// 댓글을 삭제 후
		cd.deleteComment(comment);
		// 제대로 삭제되었는지 확인
		if (cd.isCommentById(comment)) { // 해당 댓글이 존재하면 거짓
			return false;
		}
		return true;
	}

	@Override
	public Comment findCommentById(Comment comment) {
		return cd.findCommentById(comment);
	}

	@Override
	public List<Comment> findAllCommentByMemberId(long memberId, char category) {
		// 우선 모든 댓글을 불러온다.
		List<Comment> comments = cd.findAllCommentByMemberId(memberId, category);
		// 각 댓글에 대한 member 셋팅
		// memberId, foodpowerpoint에 따른 프로필이미지이다, mannertemperature, nickname 필요		
		// 각 댓글에 대ㅐ한 posting 셋팅
		// postId 필요 - 얘는 이미 있다.
		Member member = ms.findMemberById(memberId);
		Member enterMember = doSettingMemberForComment(member);		
		// 모든 댓글에 멤버를 셋팅
		for (Comment c : comments) {
			c.setMember(enterMember);
		}
		return comments;
	}

	@Override
	public List<Comment> findAllCommentByPostId(long postId, char category) {
		List<Comment> comments = cd.findAllCommentByMemberId(postId, category);
		for (Comment c : comments) {
			c.setMember(doSettingMemberForComment(ms.findMemberById(c.getMember().getId())));
		}
		return comments;
	}
	
	/**
	 * 댓글에 필요한 멤버를 셋팅하기 위한 메소드
	 * 댓글에서만 사용된다
	 * 완전한 멤버 객체를 인자로 넣어줘야한다.
	 * @param member
	 * @return
	 */
	private Member doSettingMemberForComment(Member member) {
		member = mas.doSettingMemberInfo(member);
		Member enterMember = new Member();
		// 필요한 정보만 셋팅한다.
		enterMember.setId(member.getId()); // id 필수!
		enterMember.setFoodPowerPoint(member.getFoodPowerPoint()); // 식력 포인트를 받아서
		enterMember.makeProfileImgPath(); // 프로필 이미지를 셋팅해준다.
		enterMember.setMannerTemperature(member.getMannerTemperature()); // 신뢰 온도 셋팅
		enterMember.setNickName(member.getNickName()); // 닉네임 셋팅
		
		return enterMember;
	}

}