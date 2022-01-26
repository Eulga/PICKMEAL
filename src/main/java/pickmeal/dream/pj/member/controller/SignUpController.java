package pickmeal.dream.pj.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.java.Log;
import pickmeal.dream.pj.coupon.domain.Coupon;
import pickmeal.dream.pj.coupon.domain.CouponCategory;
import pickmeal.dream.pj.coupon.service.CouponService;
import pickmeal.dream.pj.member.command.MemberCommand;
import pickmeal.dream.pj.member.domain.Member;
import pickmeal.dream.pj.member.service.MemberService;
import pickmeal.dream.pj.member.util.PasswordEncoding;
import pickmeal.dream.pj.restaurant.domain.Restaurant;
import pickmeal.dream.pj.web.util.Validator;

@Controller
@Log
public class SignUpController {
	
	@Autowired
	PasswordEncoding pe;
	
	@Autowired
	MemberService ms;
	
	/*쿠폰 서비스 추가*/
	@Autowired
	CouponService cs;
	
	@Autowired
	Validator v;
	
	@GetMapping("/member/viewSignUp")
	public ModelAndView viewSignUp() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("memberCommand", new MemberCommand());
		mav.setViewName("member/sign_up");
		return mav; 
	}
	
	@GetMapping(value="/member/checkSignUpInfo", produces="application/json; charset=utf8")
	@ResponseBody
	public String checkSignUpInfo(@RequestParam("signInfo") String signInfo, 
			@RequestParam("type") String type) {
		String check = null;
		
		if (signInfo.equals("")) {
			check = "";
		} else {
			if (type.equals("email")) {
				if(!ms.isMemberByEmail(signInfo)) {
					check = "ok";
				} else {
					check = "중복 이메일입니다.";
				}
			} else if (type.equals("nickName")) {
				if (!ms.isMemberByNickName(signInfo)) {
					check = "ok";
				} else {
					check = "중복 닉네임입니다.";
				}
			}
		}
		
		
		return check;
	}
	
	/**
	 * 회원 가입 후 메인 화면으로 보내기
	 * @param mc
	 * @param session
	 * @return
	 */
	@PostMapping("/member/generateMember")
	public ModelAndView generateMember(@ModelAttribute MemberCommand mc, 
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String errMsg = null;
		
		// command 를 domain 객체로 만든다.
		Member member = new Member();
		member.setEmail(mc.getEmail());
		member.setPasswd(mc.getPasswd());
		member.setNickName(mc.getNickName());
		member.setBirth(mc.getBirth());
		member.setGender(mc.getGender());
		
		// 비밀번호 자릿수
		if (member.getPasswd().length() < 4 || member.getPasswd().length() > 20) {
			errMsg = "비밀번호 형식에 맞지 않습니다.";
		}
		
		String[] inputPasswd = member.getPasswd().split("");
		String allow = String.valueOf(pe.allowArr);
		String number = String.valueOf(pe.numberArr);
		String upper = String.valueOf(pe.upperArr);
		String small = String.valueOf(pe.smallArr);
		
		// 비밀번호에 허용하지 않는 특수문자가 있는지 확인
		for (int i=0; i<inputPasswd.length; i++) {
			if (allow.contains(inputPasswd[i]) || number.contains(inputPasswd[i])
					|| upper.contains(inputPasswd[i]) || small.contains(inputPasswd[i])) {
				inputPasswd[i] = "O";
			} else {
				inputPasswd[i] = "X";
			}
		}
		
		if (String.join("", inputPasswd).contains("X")) {
			errMsg = "비밀번호 형식에 맞지 않습니다.";
		}
		
		if (!v.isEmpty(errMsg)) {
			mav.addObject("errMsg", errMsg);
			mav.setViewName("member/sign_up");
			return mav;
		}
		
		// 셋팅한 객체를 서비스로 보낸다.
		member = ms.addMember(member);

		// session에 들어갈 member
		Member enterMember = new Member();
		enterMember.setId(member.getId());
		enterMember.setEmail(member.getEmail());
		enterMember.setNickName(member.getNickName());
		enterMember.setFoodPowerPoint(member.getFoodPowerPoint());
		enterMember.setMannerTemperature(member.getMannerTemperature());
		enterMember.setProfileImgPath(member.getProfileImgPath());
		enterMember.setMemberType(member.getMemberType());
		
		log.info(member.toString());
		
		
		int cntForRetry = 0;
		// 완료 후 세션에 멤버를 넣어준다 (자동 로그인)
		session.setAttribute("member", enterMember);
		session.setAttribute("cntForRetry", cntForRetry);
		
		
		
		/*쿠폰 자동 넣기 서비스*/
		if(!(session.getAttribute("member") == null) && !(session.getAttribute("restaurant") == null) && !(session.getAttribute("couponCategory") == null)) {
			Member member2 = (Member) session.getAttribute("member");
			Restaurant restaurant = (Restaurant) session.getAttribute("restaurant");
			CouponCategory couponCategory = (CouponCategory) session.getAttribute("couponCategory");
		
		
			Coupon coupon = new Coupon();
			coupon.setMember(member2);
			coupon.setRestaurant(restaurant);
			coupon.setCouponCategory(couponCategory);
			cs.addCoupon(coupon);
		
			session.removeAttribute("couponCategory");
		}
		mav.setViewName("redirect:/index");
		return mav;
	}
}
