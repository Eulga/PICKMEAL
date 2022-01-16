package pickmeal.dream.pj.restaurant.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pickmeal.dream.pj.restaurant.domain.FavoriteRestaurant;

@Repository("favoriteRestaurantDao")
public class FavoriteRestaurantDaoImpl implements FavoriteRestaurantDao{
	
	@Autowired
	JdbcTemplate jt;
	
	@Override
	public FavoriteRestaurant findFavoriteRestaurantById(long id) {
		
		String sql = "SELECT id, memberId, restaurantId FROM FavoriteRestaurant WHERE id = ?";
		
		return jt.queryForObject(sql, new FavoriteRestaurantRowMapper(), id);
	}

	@Override
	public List<FavoriteRestaurant> findFavoriteRestaurantBymemberId(long memberId) {
		
		String sql = "SELECT id, memberId, restaurantId FROM FavoriteRestaurant WHERE memberId = ?";
		
		
		return jt.query(sql, new FavoriteRestaurantRowMapper(), memberId);
	}

	@Override
	public void deleteFavoriteRestaurantById(long id) {
		String sql = "DELETE FROM FavoriteRestaurant WHERE id = ?";
		
		jt.update(sql,id);
		
	}

	@Override
	public void addFavoriteRestaurant(FavoriteRestaurant favoriteRestaurant) {
		String sql ="INSERT INTO FavoriteRestaurant(memberId,RestaurantId) + VALUES (?,?)";
		
		jt.update(sql,favoriteRestaurant.getMember().getId(),favoriteRestaurant.getRestaurant().getId());
		
	}

}
