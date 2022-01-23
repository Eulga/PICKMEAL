CREATE TABLE Member (															# 사용자
	id				BIGINT			PRIMARY KEY	AUTO_INCREMENT,					# SQL 아이디
	memberType		CHAR(1)			NOT NULL,									# 사용자 타입 (M : 일반 사용자 / A : 관리자)
	email			VARCHAR(30)		NOT NULL	UNIQUE,							# 이메일(아이디 형식)	
	passwd			VARCHAR(60)		NOT NULL,									# 비밀번호
	nickName		VARCHAR(30)		NOT NULL	UNIQUE,							# 닉네임
	birth			CHAR(8)			NOT NULL,									# 생년월일
	gender			CHAR(1)			NOT NULL,									# 성별
	profileImgPath	VARCHAR(100)	NOT NULL,									# 프로필 이미지 경로
	regDate			TIMESTAMP		NOT NULL	DEFAULT CURRENT_TIMESTAMP		# 회원가입 날짜
);

ALTER TABLE Member MODIFY COLUMN passwd VARCHAR(60) NOT NULL;

INSERT INTO Member(memberType,email,passwd,nickName,birth,gender,profileImgPath)
VALUES("M","god@naver.com","1234","godgod","19901201","F","temp");
INSERT INTO Member(memberType,email,passwd,nickName,birth,gender,profileImgPath)
VALUES("M","god2@naver.com","1234","godgod2","19901201","F","temp");

CREATE TABLE FoodPowerPoint (													# 식력 포인트
	id			BIGINT		PRIMARY KEY	AUTO_INCREMENT,							# SQL 아이디
	memberId	BIGINT		NOT NULL,											# 사용자 아이디
	point		INT			NOT NULL,											# 적립 포인트
	detail		INT			NOT NULL,											# 내용
	regDate		TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,				# 등록 날짜
	CONSTRAINT	FoodPowerPoint_memberId_FK	FOREIGN KEY(memberId)	REFERENCES Member(id) ON DELETE CASCADE
);

CREATE TABLE MannerTemperature (												# 신뢰 온도
	id			BIGINT		PRIMARY KEY	AUTO_INCREMENT,							# SQL 아이디
	memberId	BIGINT		NOT NULL,											# 사용자 아이디
	temperature	DOUBLE		NOT NULL,											# 온도
	CONSTRAINT	MannerTemperature_memberId_FK	FOREIGN KEY(memberId)	REFERENCES Member(id) ON DELETE CASCADE
);

CREATE TABLE Attendance (														# 출석
	id			BIGINT		PRIMARY KEY	AUTO_INCREMENT,							# SQL 아이디
	memberId	BIGINT		NOT NULL,											# 사용자 아이디
	attendance	INT			NOT NULL	DEFAULT 1,								# 연속 출석 수
	regDate		TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,				# 마지막 출석 날짜
	CONSTRAINT	Attendance_memberId_FK	FOREIGN KEY(memberId)	REFERENCES Member(id) ON DELETE CASCADE
);
INSERT INTO MannerTemperature(memberId,temperature) VALUES(1,"36.5");
INSERT INTO FoodPowerPoint(memberId,point,detail) 
VALUES(1,100,1);
INSERT INTO Attendance(memberId,attendance) VALUES(1,1);

SELECT TIMESTAMPDIFF(DAY, a.regDate, CURDATE()) AS DIFF_DAY FROM Attendance AS a WHERE memberId=36;
SELECT attendance FROM Attendance WHERE memberId=37

DROP TABLE Member;
DROP TABLE FoodPowerPoint;
DROP TABLE MannerTemperature;
DROP TABLE Attendance;

DELETE FROM Member WHERE id=3;
DELETE FROM Attendance WHERE memberId=41;
DELETE FROM FoodPowerPoint WHERE memberId=41;
DELETE FROM MannerTemperature WHERE memberId=41;

SELECT * FROM Member;
SELECT * FROM FoodPowerPoint;
SELECT * FROM MannerTemperature;
SELECT * FROM Attendance;

SELECT SUM(point) FROM FoodPowerPoint;

UPDATE Attendance SET attendance=1, regDate=CURRENT_TIMESTAMP() WHERE memberId=39


CREATE TABLE Menu (
	id				BIGINT			PRIMARY KEY AUTO_INCREMENT,
	menuName		VARCHAR(20),
	weather			INT,	
	imgPath			VARCHAR(100),
	soupy			INT,
	hot_ice			INT,
	carbohydrate	INT,
	mainFood		INT, 
	spicy			INT
);
SELECT * FROM Menu; 

CREATE TABLE Restaurant (
	id			BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	apiId		BIGINT			NOT NULL, 
	rType		BOOLEAN			DEFAULT FALSE,
	lat			DOUBLE			NOT NULL,
	lng			DOUBLE			NOT	NULL,
	address		VARCHAR(100)	NOT NULL,
	rName		VARCHAR(50)		NOT NULL
);
DROP TABLE Restaurant;
SELECT * FROM Restaurant;

INSERT INTO Restaurant(apiId,rType,lat,lng,address,rName) VALUES(1,true,31.0000,24.0000,"대구 중구","코리아IT식당");

CREATE TABLE CouponCategory (
	id			BIGINT			PRIMARY KEY AUTO_INCREMENT,
	couponName	VARCHAR(20)		NOT NULL,
	couponType	CHAR(1)			NOT NULL
);
SELECT * FROM CouponCategory;


CREATE TABLE Coupon(
	id				BIGINT			PRIMARY KEY AUTO_INCREMENT,
	memberId		BIGINT			NOT NULL,
	couponId		BIGINT			NOT NULL,
	restaurantId	BIGINT			NOT NULL,
	couponNumber	VARCHAR(13)		NOT NULL,
	used			BOOLEAN			DEFAULT FALSE,
	regDate			TIMESTAMP		NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (memberId) REFERENCES Member (id) ON DELETE CASCADE,
	FOREIGN KEY (couponId) REFERENCES CouponCategory (id) ON DELETE CASCADE,
	FOREIGN KEY (restaurantId) REFERENCES Restaurant (id) ON DELETE CASCADE
);
DROP TABLE Coupon;
SELECT * FROM Coupon;
DELETE FROM Coupon WHERE id =1;


CREATE TABLE RestaurantPreference (
	id				BIGINT			PRIMARY KEY	AUTO_INCREMENT,
	restaurantId	BIGINT,
	gender CHAR(1),
	age CHAR(3)
);

SELECT * FROM RestaurantPreference;
SELECT * FROM Menu;


CREATE TABLE RecommendRestaurantPosting (									# 식당 추천 게시판
	id				BIGINT			PRIMARY KEY	AUTO_INCREMENT,				# SQL 아이디
	memberId		BIGINT			NOT NULL,								# 사용자 아이디
	restaurantId	BIGINT			NOT NULL,								# 식당 아이디
	title			VARCHAR(100)	NOT NULL,								# 제목
	content			MEDIUMTEXT		NOT NULL,								# 내용
	likes			INT				NOT NULL	DEFAULT 0,					# 좋아요 수
	views			INT				NOT NULL	DEFAULT 0,					# 조회 수
	regDate			TIMESTAMP		NOT NULL	DEFAULT CURRENT_TIMESTAMP,	# 글 등록 날짜
	CONSTRAINT		RecommendRestaurantPosting_memberId_FK	FOREIGN KEY(memberId)	REFERENCES Member(id) ON DELETE CASCADE,
	CONSTRAINT		RecommendRestaurantPosting_restaurantId_FK	FOREIGN KEY(restaurantId)	REFERENCES Restaurant(id) ON DELETE CASCADE
);

DROP TABLE RecommendRestaurantPosting;
SELECT * FROM RecommendRestaurantPosting;

INSERT INTO RecommendRestaurantPosting(memberId,restaurantId,title,content)
VALUES(1,1,"도리집이 가성비 짱","이 집이 문을 잘 열지는 않기는 한데 가성비가 좋아서 미워할 수 없네욤");

CREATE TABLE RecommendRestaurantComment (									# 식당 추천 댓글
	id			BIGINT			PRIMARY KEY	AUTO_INCREMENT,					# SQL 아이디
	memberId	BIGINT			NOT NULL,									# 사용자 아이디
	postId		BIGINT			NOT NULL,									# 식당 추천 게시글 아이디
	content		VARCHAR(1000)	NOT NULL,									# 내용
	regDate		TIMESTAMP		NOT NULL	DEFAULT CURRENT_TIMESTAMP,		# 등록 날짜
	CONSTRAINT	RecommendRestaurantComment_memberId_FK	FOREIGN KEY(memberId)	REFERENCES Member(id) ON DELETE CASCADE,
	CONSTRAINT	RecommendRestaurantComment_postId_FK	FOREIGN KEY(postId)	REFERENCES RecommendRestaurantPosting(id) ON DELETE CASCADE
);

DROP TABLE RecommendRestaurantComment;
SELECT * FROM RecommendRestaurantComment;

select exists (select id from RecommendRestaurantComment)

SELECT COUNT(*) FROM RecommendRestaurantComment GROUP BY postId=1;

SELECT * FROM RecommendRestaurantComment WHERE postId=1 LIMIT 15 OFFSET 29;

INSERT INTO RecommendRestaurantComment(memberId,postId,content)
VALUES(1,1,"마자요!! 이 집 일요일에만 닫는다구 하더니 월요일에 가보니깐 안열었더라구요!!");

CREATE TABLE TogetherEatingPosting (										# 밥친구 게시판
	id				BIGINT			PRIMARY KEY	AUTO_INCREMENT,				# SQL 아이디
	memberId		BIGINT			NOT NULL,								# 사용자 아이디
	restaurantId	BIGINT			NOT NULL,								# 식당 아이디
	title			VARCHAR(100)	NOT NULL,								# 제목
	content			MEDIUMTEXT		NOT NULL,								# 내용
	likes			INT				NOT NULL	DEFAULT 0,					# 좋아요 수
	views			INT				NOT NULL	DEFAULT 0,					# 조회 수
	mealTime		TIMESTAMP		NOT NULL,								# 식사 시간
	recruitment		BOOLEAN			NOT NULL	DEFAULT	FALSE,				# 모집 완료
	mealChk			BOOLEAN			NOT NULL	DEFAULT	FALSE,				# 식사 완료
	regDate			TIMESTAMP		NOT NULL	DEFAULT CURRENT_TIMESTAMP,	# 게시글 등록 날짜
	CONSTRAINT		TogetherEatingPosting_memberId_FK	FOREIGN KEY(memberId)	REFERENCES Member(id) ON DELETE CASCADE,
	CONSTRAINT		TogetherEatingPosting_restaurantId_FK	FOREIGN KEY(restaurantId)	REFERENCES Restaurant(id) ON DELETE CASCADE
)

DROP TABLE TogetherEatingPosting;
SELECT * FROM TogetherEatingPosting;

INSERT INTO TogetherEatingPosting(memberId,restaurantId,title,content,mealTime)
VALUES(1,1,"돈까스 튀기는 오빠 세트 같이 드실 분!","오늘 돈까스 세트에 쫄면이 땡기는데 저는 1인이라서 혼자 먹을 수가 없네욤. 같이 드실 분 댓글용"
, "20220115120000");

CREATE TABLE TogetherEatingComment (										# 밥친구 댓글
	id			BIGINT			PRIMARY KEY	AUTO_INCREMENT,					# SQL 아이디
	memberId	BIGINT			NOT NULL,									# 사용자 아이디
	postId		BIGINT			NOT NULL,									# 밥친구 게시글 아이디
	content		VARCHAR(1000)	NOT NULL,									# 내용
	regDate		TIMESTAMP		NOT NULL	DEFAULT CURRENT_TIMESTAMP,		# 등록 날짜
	CONSTRAINT	TogetherEatingComment_memberId_FK	FOREIGN KEY(memberId)	REFERENCES Member(id) ON DELETE CASCADE,
	CONSTRAINT	TogetherEatingComment_postId_FK	FOREIGN KEY(postId)	REFERENCES TogetherEatingPosting(id) ON DELETE CASCADE
)

DROP TABLE TogetherEatingComment;
SELECT * FROM TogetherEatingComment;

INSERT INTO TogetherEatingComment(memberId,postId,content)
VALUES(40,1,"저랑 드시면 밥을 더 드실 수 있습니당.");


CREATE TABLE NoticePosting (									# 식당 추천 게시판
	id				BIGINT			PRIMARY KEY	AUTO_INCREMENT,				# SQL 아이디
	memberId		BIGINT			NOT NULL,								# 사용자 아이디							# 식당 아이디
	title			VARCHAR(100)	NOT NULL,								# 제목
	content			MEDIUMTEXT		NOT NULL,								# 내용				# 좋아요 수
	views			INT				NOT NULL	DEFAULT 0,					# 조회 수
	regDate			TIMESTAMP		NOT NULL	DEFAULT CURRENT_TIMESTAMP,	# 글 등록 날짜
	CONSTRAINT		NoticePosting_memberId_FK	FOREIGN KEY(memberId)	REFERENCES Member(id) ON DELETE CASCADE
	
);
DROP TABLE NoticePosting;
SELECT * FROM NoticePosting;


drop table chat

CREATE TABLE Chat(
	id			BIGINT			PRIMARY KEY	AUTO_INCREMENT,
	writerId	BIGINT			NOT NULL,
	commenterId	BIGINT			NOT NULL,
	memberId	BIGINT			NOT NULL,
	regDate		TIMESTAMP		NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT	Chat_writerId_FK	FOREIGN KEY(writerId)	REFERENCES Member(id) ON DELETE CASCADE,
	CONSTRAINT	Chat_commenterId_FK	FOREIGN KEY(commenterId)	REFERENCES Member(id) ON DELETE CASCADE,
	CONSTRAINT	Chat_memberId_FK	FOREIGN KEY(memberId)	REFERENCES Member(id) ON DELETE CASCADE
)
select * from Chat;

