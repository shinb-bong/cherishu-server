package cherish.backend.common.config;

import cherish.backend.common.constant.CommonConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins(
                CommonConstants.CLIENT_ORIGIN,
                CommonConstants.LOCALHOST)
            .allowedMethods(
                GET.name(),
                POST.name(),
                PATCH.name(),
                DELETE.name());
    }
}
