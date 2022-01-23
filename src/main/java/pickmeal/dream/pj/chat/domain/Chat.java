package pickmeal.dream.pj.chat.domain;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import pickmeal.dream.pj.member.domain.Member;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Slf4j
public class Chat {
	private long id;
	private Member writer;
	private Member commenter;
	private Member member; // 누구의 파일인지 알아야한다.
	private char readType; // R: 읽음 / N: 읽지 않은 메시지 있음
	private Date regDate;	
}
