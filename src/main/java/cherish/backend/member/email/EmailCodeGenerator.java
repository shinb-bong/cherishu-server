package cherish.backend.member.email;

import lombok.experimental.UtilityClass;

import java.util.random.RandomGenerator;

@UtilityClass
public class EmailCodeGenerator {
    private static final RandomGenerator randomGenerator = RandomGenerator.of("L128X256MixRandom");

    public static String generateCode(){
        int rand = randomGenerator.nextInt(1000000);
        return String.format("%06d", rand);
    }
}
