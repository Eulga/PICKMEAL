package pickmeal.dream.pj.coupon.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import pickmeal.dream.pj.coupon.domain.Coupon;
import pickmeal.dream.pj.coupon.domain.CouponCategory;
import pickmeal.dream.pj.coupon.service.CouponService;
import pickmeal.dream.pj.member.domain.Member;
import pickmeal.dream.pj.member.service.MemberService;
import pickmeal.dream.pj.restaurant.domain.Restaurant;

@Controller
public class CouponCountroller {
	
	@Resource(name="couponService")
	CouponService cs;
	@Resource(name="memberService")
	MemberService ms;
	/**
	 * 쿠폰발생 시점(게임하고 식당 띄워줄 때) 이거 안써용 
	 * @return
	 */
	@GetMapping("/couponGenerate")
	public ModelAndView CouponGenerate() {
		
		ModelAndView mav = new ModelAndView();
		//mav.addObject("coupon",coupon);
		mav.setViewName("menu/coupongeneratetest");
		
		return mav;
		
	}
	@GetMapping("/startPage")
	public ModelAndView StartPage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		session.removeAttribute("couponCategory");
		mav.setViewName("coupon/restaurant_game_example");
		return mav;
	}
	/**
	 * 게임 끝난 후 식당 정보를 보고 식당이 제휴식당인지 확인 후, 
	 * 쿠폰 카테고리 발행 성공 // 실패 성공시 쿠폰 카테고리를 세션에 저장
	 * **이거 지금 테스트용 Service부르는거라 변경 필요함!!
	 * CouponCategory couponCategory = cs.findCouponCategoryTest(); 이 부분
	 * @return
	 */
	@GetMapping("/couponCategoryGeneric")
	public ModelAndView CouponCategoryGeneric(HttpSession session) {
		/*이거 나중에 게임 완료 후로 다 옴겨야해용 다 옴겨야행용*/
		/*레스토랑이 제휴 레스토랑인지 비교 하기*/
		Restaurant restaurant = cs.findRestaurantById(3);
		System.out.println(restaurant);
		System.out.println(restaurant.isrType());
		
		if(restaurant.isrType()== true) {
			/*제휴 레스토랑이면 메소드 돌려서 쿠폰나오면 발급 해주기*/
			CouponCategory couponCategory = cs.findCouponCategoryTest();
			/*쿠폰이 발급이 안되서 리턴값이 없을 경우 그냥 통과*/
			System.out.println("트루값 받고 여기 들어옴");
			if(couponCategory == null) {
				System.out.println("없는쪽임 = " + couponCategory);
			}else {
				/*쿠폰이 발급이 되어 리턴값이 있을 경우는 세션에 저장 시켜준다.*/
				session.setAttribute("couponCategory", couponCategory);
				session.setAttribute("restaurant", restaurant);	
				System.out.println(session.getAttribute("member"));
				System.out.println("카테고리,식당 세션등록 완료");
			//mav.addObject("couponCategory",couponCategory);
			}
		}else
		{
			System.out.println("쿠폰발급 불가식당");
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("coupon/index_couponCategory_generate");
		return mav;
		
	}
	
	@GetMapping("/couponPopupCreate")
	public ModelAndView CouponPopupCreate(HttpSession session) {
		
		CouponCategory couponCategory = new CouponCategory();
		Member member = new Member();
		couponCategory = (CouponCategory) session.getAttribute("couponCategoty");
		member = (Member) session.getAttribute("member");
		System.out.println("드루 가나욘??");
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("coupon/coupon_generate_popup");
		
		return mav;
	}
	/**
	 * 쿠폰 발행
	 */
	@GetMapping("genericCoupon")
	public ModelAndView GenericCoupon(HttpSession session) {
		System.out.println(session.getAttribute("member"));
		System.out.println(session.getAttribute("restaurant"));
		System.out.println(session.getAttribute("couponCategory"));
		/*세션안에 멤버, 레스토랑, 쿠폰카테고리가 있는지 확인 후*/
		if(!(session.getAttribute("member") == null) && !(session.getAttribute("restaurant") == null) && !(session.getAttribute("couponCategory") == null)) {
			System.out.println("세션에 3개 다 있음");
		Member member = (Member) session.getAttribute("member");
		Restaurant restaurant = (Restaurant) session.getAttribute("restaurant");
		CouponCategory couponCategory = (CouponCategory) session.getAttribute("couponCategory");
		System.out.println("들어가나요??");
		System.out.println("있나요?" + session.getAttribute("couponCategory"));
		
		
		Coupon coupon = new Coupon();
		coupon.setMember(member);
		coupon.setRestaurant(restaurant);
		coupon.setCouponCategory(couponCategory);
		cs.addCoupon(coupon);
		
		session.removeAttribute("couponCategory");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("coupon/index_couponCategory_generate");
		
		return mav;
		/*세션안에 세개중 하나가 없을 경우에는 자동 로그인기능만 할 수 있도록*/
		}else {
			System.out.println("세션에 3개중 뭐가 없음!");
		}
		return null;
	}
	
}