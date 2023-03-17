package cherish.backend.common.config.vault;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(VaultProperties.PREFIX)
@Data
public class VaultProperties {
    public static final String PREFIX = "vault.props";

    private String host;
    private int port;
    private String schema;
    private String roleId;
    private String secretId;
    private String vaultPath;
}
