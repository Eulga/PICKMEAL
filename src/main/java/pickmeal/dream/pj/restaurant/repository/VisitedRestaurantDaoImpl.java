package pickmeal.dream.pj.restaurant.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import pickmeal.dream.pj.restaurant.domain.VisitedRestaurant;

@Repository("visitedRestaurantDao")
@Slf4j
public class VisitedRestaurantDaoImpl implements VisitedRestaurantDao{
	
	@Autowired
	JdbcTemplate jt;
	@Override
	public List<VisitedRestaurant> findAllVisitedRestaurantByMemberId(long memberId) {
		String sql = "SELECT id, memberId, restaurantId, Review, regDate FROM VisitedRestaurant WHERE memberId = ? ORDER BY regDate DESC";
		return jt.query(sql, new VisitedRestaurantRowMapper(),memberId);
	}

	@Override
	public void addVisitedRestaurant(VisitedRestaurant visitedRestaurant) {
		String sql = "INSERT INTO VisitedRestaurant(memberId, restaurantId) VALUES(?,?)";
		
		jt.update(sql,visitedRestaurant.getMember().getId(),visitedRestaurant.getRestaurant().getId());
		
	}

	@Override
	public VisitedRestaurant findVisitedRestaurantById(long id) {
		String sql = "SELECT id, memberId, restaurantId, Review, regDate FROM VisitedRestaurant WHERE id = ? ";
		
		return jt.queryForObject(sql, new VisitedRestaurantRowMapper(), id);
	}

	@Override
	public void writeVisitedRestaurantReviewById(long id) {
		String sql = "UPDATE VisitedRestaurant SET Review = true WHERE id = ?;";
		jt.update(sql,id);
		
	}

	@Override
	public void removeVisitedRestaurantById(long id) {
		String sql = "DELETE FROM VisitedRestaurant WHERE id = ?";
		
		jt.update(sql,id);
		
	}

	@Override
	public boolean isVisitedRestaurantById(long id) {
		String sql = "SELECT EXISTS (SELECT id FROM FavoriteRestaurant WHERE id = ?)";
		
		return jt.queryForObject(sql, boolean.class,id);
	}
	
	

}
