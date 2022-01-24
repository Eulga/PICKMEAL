package pickmeal.dream.pj.posting.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import pickmeal.dream.pj.member.domain.Member;
import pickmeal.dream.pj.posting.domain.Posting;
import pickmeal.dream.pj.restaurant.domain.Restaurant;

public class RecommendRestaurantPostingRowMapper implements RowMapper<Posting>{

	@Override
	public Posting mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Posting posting = new Posting();
		posting.setId(rs.getLong("id"));
		posting.setMember(new Member(rs.getLong("memberId")));
		posting.setAddress(rs.getString("address"));
		posting.setCategory('R');
		posting.setTitle(rs.getString("title"));
		posting.setContent(rs.getString("content"));
		//서비스에서 해주기 
		//posting.setCommentsNumber(rs.getInt(rowNum));
		posting.setLikes(rs.getInt("likes"));
		posting.setViews(rs.getInt("views"));
		posting.setRegDate(new Date(rs.getTimestamp("regDate").getTime()));
		return posting;
	}

}
