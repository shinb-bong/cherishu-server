package cherish.backend.member.email;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.Collectors;

@Builder
@Getter
public class EmailCode {
    private String code;

    public static EmailCode createCode(){
        return EmailCode.builder().
                code(makeCode()).build();
    }

    static String makeCode() {
        List<Integer> numbers = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executorService = Executors.newCachedThreadPool();

        RandomGenerator.SplittableGenerator sourceGenerator = RandomGeneratorFactory
                .<RandomGenerator.SplittableGenerator>of("L128X256MixRandom")
                .create();

        sourceGenerator.splits(6).forEach((splitGenerator) -> {
            executorService.submit(() -> {
                numbers.add(splitGenerator.nextInt(10));
            });
        });
        String sb = numbers.stream().map(String::valueOf).collect(Collectors.joining());
        return sb;
    }
}
