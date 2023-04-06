package cherish.backend.auth.security;

import cherish.backend.auth.jwt.JwtAuthenticationFilter;
import cherish.backend.auth.jwt.JwtExceptionFilter;
import cherish.backend.auth.jwt.JwtTokenProvider;
import cherish.backend.common.constant.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;
    // 현재 화이트 리스트 모두 열어 놓음
    private static final String[] PUBLIC_WHITELIST = {
            "/public/**", "/test/**"
    };

    // 비밀번호 변환 알고리즘
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeHttpRequests(authorize ->
                    authorize
                    .shouldFilterAllDispatcherTypes(false)
                    .requestMatchers(CorsUtils::isPreFlightRequest)
                    .permitAll()
                    .requestMatchers(PUBLIC_WHITELIST)
                    .permitAll()
                    .anyRequest()
                    .authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
                .build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin(CommonConstants.LOCALHOST);
        config.addAllowedOrigin(CommonConstants.CLIENT_ORIGIN); // 프론트 IPv4 주소
        config.addAllowedMethod(""); // 모든 메소드 허용.
        config.addAllowedHeader("");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
