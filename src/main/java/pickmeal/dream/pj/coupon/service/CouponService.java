package pickmeal.dream.pj.coupon.service;

import java.util.List;

import pickmeal.dream.pj.coupon.domain.Coupon;
import pickmeal.dream.pj.coupon.domain.CouponCategory;
import pickmeal.dream.pj.restaurant.domain.Restaurant;

public interface CouponService {
	CouponCategory findCouponCategoryByRestaurant(Restaurant restaurant);
	
	CouponCategory findCouponCategoryTest();
	
	Coupon addCoupon(Coupon coupon);
	
	Restaurant findRestaurantById(long id);
	
	Coupon changeUsedCouponById(long id);
	
	Coupon findCouponById(long id);
	
	List<Coupon> findAllCouponsByMemberId(long memberId);
	
	List<Coupon> findUsedConponsByMemberId(long memberId);
	
	List<Coupon> findUnusedCouponsByMemberId(long memberId);
	
	CouponCategory findCouponCategoryByid(long id);
	
	Coupon findCouponByCouponNumber(String couponNumber);
	
	
}
