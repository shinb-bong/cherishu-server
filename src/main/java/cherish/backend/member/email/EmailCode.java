package cherish.backend.member.email;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.random.RandomGenerator;

@Slf4j
@Builder
@Getter
public class EmailCode {
    private String code;
    private static final RandomGenerator randomGenerator = RandomGenerator.of("L128X256MixRandom");

    public static EmailCode createCode(){
        return EmailCode.builder().
                code(makeCode()).build();
    }

    private static String makeCode() {
        int rand = randomGenerator.nextInt(1000000);
        log.info("code - {}", rand);
        return String.format("%06d", rand);
    }
}
