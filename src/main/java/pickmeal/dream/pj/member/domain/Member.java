package pickmeal.dream.pj.member.domain;

import static pickmeal.dream.pj.web.constant.Constants.*;
import static pickmeal.dream.pj.web.constant.SavingPointConstants.*;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pickmeal.dream.pj.web.constant.Constants;
import pickmeal.dream.pj.web.constant.SavingPointConstants;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	private long id;
	private char memberType;
	private String email;
	private String passwd;
	private String nickName;
	private String birth;
	private char gender;
	private String profileImgPath;
	private Date regDate;
	private int foodPowerPoint; // 테이블로부터 셋팅 필요
	private double mannerTemperature; // 테이블로부터 셋팅 필요
	private int attendance; // 테이블로부터 셋팅 필요
	

	public Member(long id) {
		super();
		this.id = id;
	}
	
	/**
	 * 식력 포인트에 따른 프로필 이미지 경로 잡기
	 * @param c
	 */
	public void makeProfileImgPath() {
		if (this.foodPowerPoint >= 50000) {
			this.profileImgPath = LEVEL5.getImgPath();	
		} else if (this.foodPowerPoint >= 15000) {
			this.profileImgPath = LEVEL4.getImgPath();	
		} else if (this.foodPowerPoint >= 5000) {
			this.profileImgPath = LEVEL3.getImgPath();	
		} else if (this.foodPowerPoint >= 1000) {
			this.profileImgPath = LEVEL2.getImgPath();	
		} else if (this.foodPowerPoint >= 300) {
			this.profileImgPath = LEVEL1.getImgPath();	
		} else {
			this.profileImgPath = LEVEL0.getImgPath();	
		}
		this.profileImgPath = "/resources/img/profile/" + getProfileImgPath() + ".png";
	}
	
	/**
	 * 식력 포인트 적립
	 * 회원 가입 제외 모든 활동에서는 ++ 한다.
	 * @param spc
	 */
	public void saveFoodPowerPoint(SavingPointConstants spc) {
		switch(spc) {
		case SIGN_UP:
			this.foodPowerPoint = SIGN_UP.getPoint();
			break;
		default:
			this.foodPowerPoint += spc.getPoint();
			break;
		}
	}
	
	/**
	 * 회원 가입 시 매너 온도는 0 이기 때문에 36.5 로 맞춰준다.
	 * 그 후, 평가 시 좋아요를 받았다면 5도씩 올려준다.
	 * 싫어요의 경우 5도를 낮춘다.
	 * 보통이였어요는 그대로
	 */
	public void saveMannerTemperature(Constants c) {
		switch(c) {
		case SIGN_UP_MANNER:
			this.mannerTemperature = SIGN_UP_MANNER.getPoint();
			break;
		default: 
			this.mannerTemperature += c.getPoint();
			break;
		}
	}
}