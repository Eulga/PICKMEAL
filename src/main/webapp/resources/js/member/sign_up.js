$("#signUpBtn").click(function(e) {
	// 성별 체크했는지
	let gender = $("input[name=gender]:checked").val();
	let errMsg = $("#female + label").next();
	if (gender == null){
		infoChkArr[5] = 0;
		errMsg.text("성별을 선택해주세요.")
		infoChk = false;
	} else {
		infoChkArr[5] = 1;
		errMsg.text("")
		infoChk = true;
	}
	let submitChk = false;
	let arr = [1, 1, 1, 1, 1, 1];
	if (JSON.stringify(infoChkArr) === JSON.stringify(arr)) {
		submitChk = true;
	} else {
		submitChk = false;
	}
	
	if (infoChk == false || submitChk == false) {
		e.preventDefault();
	} else {
		
	}
	
	
})

let infoChk = false;
let infoChkArr = [0, 0, 0, 0, 0, 0];

$("#email").focusout(function() {
	let email = $(this).val();
	let errMsg = $(this).next();
	checkSignUpInfo(email, "email", errMsg);
})

$("#nickName").focusout(function() {
	let nickName = $(this).val();
	let errMsg = $(this).next();
	checkSignUpInfo(nickName, "nickName", errMsg);
})

// 이메일, 닉네임 중복확인
function checkSignUpInfo(signInfo, type, errMsg){
	let json = {"signInfo" : signInfo, "type" : type};
	$.ajax({
		url: "checkSignUpInfo",
		type: "get",
		data: json,
		dataType: 'text',
		success: function(data){
			if (data == "ok") {
				errMsg.text("");
				infoChk = true;
				if (type == "email") {
					infoChkArr[0] = 1;
				} else if (type == "nickName"){
					infoChkArr[3] = 1;
				}
			} else {
				errMsg.text(data);
				infoChk = false;
				if (type == "email") {
					infoChkArr[0] = 0;
				} else if (type == "nickName"){
					infoChkArr[3] = 0;
				}
			}
		}
	})
}

// 비밀번호 체크
$("#passwdChk").focusin(function() {
	let passwd = $("#passwd").val();
	let errMsg = $("#passwd").next();
	if (passwd.length == 0) { // 비밀번호가 비었을 때
		errMsg.text("비밀번호를 입력해주세요.");
		infoChk = false;
		infoChkArr[1] = 0;
	} else { // 비밀번호를 적었다면
		errMsg.text("");
		infoChk = true;
		infoChkArr[1] = 1;
	}
})

// 비밀번호와 비밀번호확인의 일치 확인
$("#passwdChk").focusout(function() {
	let passwd = $("#passwd").val();
	let passwdChk = $(this).val();
	let errMsg = $(this).next();
	if (passwd == passwdChk){
		infoChk = true;
		errMsg.text("");
		infoChkArr[2] = 1;
	} else {
		errMsg.text("비밀번호가 일치하지 않습니다.");
		infoChk = false;
		infoChkArr[2] = 0;
	}
})

// 생년월일 확인
$("#birth").focusout(function() {
	let birth = $(this).val();
	let errMsg = $(this).next();
	if (isNaN(birth)){ // 숫자가 아니라면
		infoChk = false;
		errMsg.text("숫자만 입력해주세요.");
		infoChkArr[4] = 0;
	} else { // 숫자일 경우
		if (birth.length == 8) { // 8자리로 잘 적었다면
			infoChk = true;
			errMsg.text("");
			infoChkArr[4] = 1;
		} else { // 8자리가 아니라면
			infoChk = false;
			errMsg.text("생년월일은 8자리로 적어주세요.");
			infoChkArr[4] = 0;
		}
	}
})