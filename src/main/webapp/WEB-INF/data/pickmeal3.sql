CREATE TABLE Member (
	id				BIGINT			PRIMARY KEY	AUTO_INCREMENT,
	memberType		CHAR(1)			NOT NULL,
	email			VARCHAR(30)		NOT NULL	UNIQUE,
	passwd			VARCHAR(30)		NOT NULL,
	nickName		VARCHAR(30)		NOT NULL	UNIQUE,
	birth			CHAR(8)			NOT NULL,
	gender			CHAR(1)			NOT NULL,
	profileImgPath	VARCHAR(100)	NOT NULL,
	regDate			TIMESTAMP		NOT NULL	DEFAULT CURRENT_TIMESTAMP
)

CREATE TABLE FoodPowerPoint (
	id			BIGINT		PRIMARY KEY	AUTO_INCREMENT,
	memberId	BIGINT		NOT NULL,
	point		INT			NOT NULL,
	detail		INT			NOT NULL,
	regDate		TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT	FoodPowerPoint_memberId_FK	FOREIGN KEY(memberId)	REFERENCES Member(id)
)

CREATE TABLE MannerTemperature (
	id			BIGINT		PRIMARY KEY	AUTO_INCREMENT,
	memberId	BIGINT		NOT NULL,
	temperature	DOUBLE		NOT NULL,
	CONSTRAINT	MannerTemperature_memberId_FK	FOREIGN KEY(memberId)	REFERENCES Member(id)
)

CREATE TABLE Attendance (
	id			BIGINT		PRIMARY KEY	AUTO_INCREMENT,
	memberId	BIGINT		NOT NULL,
	attendance	INT			NOT NULL,
	regDate		TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT	Attendance_memberId_FK	FOREIGN KEY(memberId)	REFERENCES Member(id)
)


DROP TABLE Member;
DROP TABLE FoodPowerPoint;
DROP TABLE MannerTemperature;
DROP TABLE Attendance;

SELECT * FROM Member;
SELECT * FROM FoodPowerPoint;
SELECT * FROM MannerTemperature;
SELECT * FROM Attendance;



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
)

CREATE TABLE RestaurantPreference (
	id				BIGINT			PRIMARY KEY	AUTO_INCREMENT,
	restaurantId	BIGINT,
	gender CHAR(1),
	age CHAR(3)
)

CREATE TABLE Review (
	id				BIGINT			PRIMARY KEY	AUTO_INCREMENT,
	restaurantId	BIGINT,
	bathroom		INT,
	kind			INT,
	specialDay		INT,
	clean			INT,
	parking			INT,
	goodgroup		INT,
	alone			INT,
	big				INT,
	interior		INT
)

ALTER TABLE Review ADD userCount INT NOT NULL DEFAULT '0';

INSERT INTO Review(restaurantId, bathroom, kind, specialDay, clean, parking, goodgroup, alone, big, interior, usercount)
VALUES (1, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 );


SELECT * FROM RestaurantPreference;
SELECT * FROM Menu;
SELECT id, menuName, weather, imgPath, soupy, hot_ice, carbohydrate, mainFood, spicy FROM Menu WHERE soupy = 1 AND hot_ice = 0 AND carbohydrate = 2 AND mainFood = 1 AND spicy = 0;
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말일",1,1,0,2,1,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이",2,1,1,3,2,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말삼",0,0,1,3,2,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말사",1,1,0,1,2,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말오",2,1,1,0,1,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말육",1,1,1,2,2,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말칠",0,0,1,3,2,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말팔",0,1,0,1,2,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말구",1,0,0,1,2,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말십",2,1,0,1,2,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말십일",1,0,0,1,2,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말십이",2,0,0,2,2,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말십삼",0,0,0,0,1,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말십사",0,1,0,3,1,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말십오",0,0,0,3,1,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말십육",1,1,0,3,0,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말십칠",2,0,1,3,0,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말십팔",3,0,0,3,0,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말십구",3,1,1,3,0,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이십",2,0,1,3,2,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이십일",3,1,0,1,1,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이십이",3,1,1,2,1,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이십삼",3,1,0,1,1,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이십사",4,1,1,0,1,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이십오",4,1,0,2,2,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이십육",4,1,1,1,2,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이십칠",4,1,0,2,1,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이십팔",4,1,1,0,2,0);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이십구",4,1,1,2,2,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이삼십",4,1,0,1,2,1);
INSERT INTO Menu(menuName,weather,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("김말이삼십일",1,1,0,0,2,1);
INSERT INTO Menu(menuName,weather,imgPath,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("찜닭",1,"/pickmeal/resources/img/menu/찜닭.jpg",1,1,3,2,1);
INSERT INTO Menu(menuName,weather,imgPath,soupy,hot_ice,carbohydrate,mainFood,spicy) VALUES ("찜닭",1,"/pickmeal/resources/img/menu/찜닭.jpg",1,1,2,2,1);


SELECT * FROM Review;
