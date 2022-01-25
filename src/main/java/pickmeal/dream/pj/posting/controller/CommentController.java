package pickmeal.dream.pj.posting.controller;

import static pickmeal.dream.pj.web.constant.Constants.COMMENT_LIST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.java.Log;
import pickmeal.dream.pj.member.domain.Member;
import pickmeal.dream.pj.member.service.MemberService;
import pickmeal.dream.pj.posting.command.CommentCommand;
import pickmeal.dream.pj.posting.domain.Comment;
import pickmeal.dream.pj.posting.domain.Posting;
import pickmeal.dream.pj.posting.service.CommentService;
import pickmeal.dream.pj.web.util.Validator;

@Controller
@Log
public class CommentController {
	
	@Autowired
	CommentService cs;
	
	@Autowired
	MemberService ms;
	
	@Autowired
	Validator v;
	
	@GetMapping("/posting/viewTogetherEatingComment")
	public ModelAndView viewTogetherEatingComment(@RequestParam("cpageNum") int cpageNum) {
		ModelAndView mav = new ModelAndView();
		log.info("category 테스트로 넣어놓은 것 반드시 삭제할 것★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		// 항상 pageNum은 1이 default 이다.
		List<Comment> comments = cs.findCommentsByPostId(1, 'E', cpageNum); // 게시물 댓글 1페이지
		int allCmtNum = cs.countCommentByPostId(1, 'E'); // 해당 게시글의 총 댓글 수
		// double 형으로 캐스팅을 한 후에 나누기를 해줘야 소수점이 제대로 나온다
		int allPageNum = (int)Math.ceil((double)allCmtNum / (int)COMMENT_LIST.getPoint()); // 페이지 개수 구하기
//		log.info(comments.toString());
//		for(Comment c : comments) {
//			log.info(c.toString());
//		}
		
		if (cpageNum > allPageNum) { // 만일 사용자가 url 에 더 큰 값을 적는다면 가장 큰 페이지로 가도록 한다
			cpageNum = allPageNum;
		}
		log.info(String.valueOf(cpageNum));
		
		
//		여기서부터
		Posting posting = new Posting(1, 'E');
		Member writer = ms.findMemberById(1);
		Member enterWriter = new Member();
		enterWriter.setId(writer.getId());
		enterWriter.setEmail(writer.getEmail());
		posting.setMember(enterWriter);
//		여기까지 삭제해야한다
		
		mav.addObject("posting", posting);
		mav.addObject("comments", comments);
		mav.addObject("allPageNum", allPageNum);
		mav.addObject("allCmtNum", allCmtNum);
		mav.addObject("cpageNum", cpageNum);
		mav.addObject("viewPageNum", (int)COMMENT_LIST.getPoint());
		mav.setViewName("posting/together_eating_comment");
		return mav;
	}
	
	// 댓글 쓰기
	@PostMapping("/posting/addComment")
	public ResponseEntity<?> addComment(@ModelAttribute CommentCommand cc, 
			@SessionAttribute("member") Member member) {
		// 빈 문자열일 경우 그냥 돌려보냄
		if (v.isEmpty(cc.getContent())) {
			return ResponseEntity.ok("empty");
		}
		// comment 객체 셋팅
		Comment comment = new Comment();
		comment.setMember(member); // 현재 사용자 셋팅
		comment.setPosting(new Posting(cc.getPostId(), cc.getCategory()));
		comment.setContent(cc.getContent());
		log.info(comment.toString());
		// comment 등록
		comment = cs.addComment(comment);
//		log.info(comment.toString());
		log.info("category 테스트로 넣어놓은 것 반드시 삭제할 것");
		//comment.getPosting().setCategory(cc.getCategory()); 

		return ResponseEntity.ok(comment);
	}
	
	// 댓글 수정하기
	@GetMapping(value="/posting/modifyComment", produces="application/text; charset=utf8")
	public ResponseEntity<?> modifyComment(@ModelAttribute CommentCommand cc
			, @RequestParam("beforeContent") String beforeContent){
//		log.info("id : " + String.valueOf(id));
//		log.info("content : " + content);
//		log.info("beforeContent : " + beforeContent);
		
		// 수정할 문자열이 빈 문자열일 경우 다시 반환
		if (v.isEmpty(cc.getContent())) {
			return ResponseEntity.ok(beforeContent);
		} else if (cc.getContent().equals(beforeContent)) {
			return ResponseEntity.ok(cc.getContent());
		}
		// comment 객체 만들기
		Comment comment = new Comment();
		comment.setId(cc.getId());
		comment.setContent(cc.getContent());
		comment.setPosting(new Posting(cc.getPostId(), cc.getCategory()));
		comment.setMember(new Member(cc.getMemberId()));
		
		comment = cs.updateComment(comment);
		
		return ResponseEntity.ok(comment.getContent());
	}
	
	// 삭제하기
	@GetMapping("/posting/deleteComment")
	public ResponseEntity<?> deleteComment(@ModelAttribute CommentCommand cc){
		// comment 객체 만들기
		Comment comment = new Comment();
		comment.setId(cc.getId());
		comment.setPosting(new Posting(cc.getPostId(), cc.getCategory()));
		comment.setMember(new Member(cc.getMemberId()));

		// 삭제하면서 boolean 값 보내기
		return ResponseEntity.ok(cs.deleteComment(comment));
	}
	
	// ajax 로 불러오기
	@GetMapping("/posting/changeCommentPage")
	public ResponseEntity<?> changeCommentPage(@RequestParam("postId") long postId,
			@RequestParam("category") char category, @RequestParam("cpageNum") int cpageNum) {
		// 총 페이지 개수
		int allPageNum = cs.countCommentByPostId(postId, category);
		// 15개씩 변경
		List<Comment> comments = cs.findCommentsByPostId(postId, category, cpageNum);
//		log.info("실행");
		return ResponseEntity.ok(comments);
	}
	
	// 삭제 확인 팝업 띄우기
	@GetMapping("/posting/checkDeleteComment")
	public String checkDeleteComment() {
		return "posting/check_delete_comment";
	}
}
