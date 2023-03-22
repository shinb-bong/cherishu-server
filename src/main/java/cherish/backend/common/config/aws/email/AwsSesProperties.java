package cherish.backend.common.config.aws.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Profile({"dev","main"})
@Getter
@AllArgsConstructor
@ConfigurationProperties("aws.ses")
public class AwsSesProperties {

    private String accessKey;
    private String secretKey;
}
