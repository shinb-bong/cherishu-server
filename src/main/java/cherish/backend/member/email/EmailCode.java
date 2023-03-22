package cherish.backend.member.email;

import lombok.Builder;
import lombok.Getter;

import java.util.Random;

@Builder
@Getter
public class EmailCode {
    private String code;

    public static EmailCode createCode(){
        return EmailCode.builder().
                code(makeCode()).build();
    }

    public static String makeCode() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 8자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }
}
