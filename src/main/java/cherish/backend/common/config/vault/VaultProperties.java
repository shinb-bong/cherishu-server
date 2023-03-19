package cherish.backend.common.config.vault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Profile("main")
@ConfigurationProperties(VaultProperties.PREFIX)
@Getter
@AllArgsConstructor
public class VaultProperties {
    public static final String PREFIX = "vault.props";

    private String host;
    private int port;
    private String schema;
    private String roleId;
    private String secretId;
    private String vaultPath;

}
