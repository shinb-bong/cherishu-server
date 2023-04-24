package cherish.backend.member.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String MEMBER_NOT_FOUND = "해당 유저가 없습니다.";
    public static final String EMAIL_ALREADY = "해당 이메일로 이미 가입한 이력이 있습니다.";
    public static final String TIME_LIMIT = "5분 내에 이메일을 재전송 할 수 없습니다.";
    public static final String DAILY_COUNT_EXCEEDED = "하루 인증 횟수를 초과했습니다.";
    public static final String EMAIL_CODE_NOT_FOUND = "이메일 인증 코드를 발송한 내역이 없습니다.";
    public static final String EMAIL_VERIFICATION_EXPIRED = "이메일 인증 정보가 없거나 만료되었습니다. 이메일 인증을 다시 해주세요.";
    public static final String EMAIL_CODE_NOT_EQUAL = "입력한 입력코드가 다릅니다.";
    public static final String JOB_NOT_FOUND = "해당 직업이 없습니다.";
    public static final String FAILED_TO_LOGIN = "아이디 또는 비밀번호가 틀립니다.";
}
