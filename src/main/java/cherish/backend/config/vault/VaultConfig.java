package cherish.backend.config.vault;

import cherish.backend.config.db.SimpleHikariConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(VaultProperties.class)
@Configuration
public class VaultConfig extends AbstractVaultConfiguration {
    private final VaultProperties vaultProperties;

    @Override
    public VaultEndpoint vaultEndpoint() {
        try {
            return VaultEndpoint.from(new URI(String.format("%s://%s:%s", vaultProperties.getSchema(), vaultProperties.getHost(), vaultProperties.getPort())));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        AppRoleAuthenticationOptions appRoleAuthenticationOptions = null;
        if (vaultProperties != null) {
            appRoleAuthenticationOptions = AppRoleAuthenticationOptions.builder()
                .roleId(AppRoleAuthenticationOptions.RoleId.provided(vaultProperties.getRoleId()))
                .secretId(AppRoleAuthenticationOptions.SecretId.provided(vaultProperties.getSecretId()))
                .build();
        }
        return appRoleAuthenticationOptions != null ? new AppRoleAuthentication(appRoleAuthenticationOptions, restOperations()) : null;
    }

    @Bean
    public SimpleHikariConfig simpleHikariConfig() {
        return Objects.requireNonNull(vaultTemplate().read(vaultProperties.getVaultPath(), SimpleHikariConfig.class)).getData();
    }
}
