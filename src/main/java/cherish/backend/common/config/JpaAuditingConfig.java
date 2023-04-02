package cherish.backend.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *  Mock mvc시 매번
 *  @MockBean(JpaMetamodelMappingContext.class)
 *  를 적어줘야해서 설정 파일로 뺐다.
 *  혹시 @DataJpaTest를 쓰면 해당 파일을 Import 해야한다.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
