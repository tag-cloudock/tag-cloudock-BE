package gachonherald.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer ->
                        corsConfigurer.configurationSource(source())
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        http
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/oauth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/anno/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/post/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/review/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/council/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/manage/council/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/council-item/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/council-item/**").permitAll()
                                .requestMatchers("/image/**").permitAll()
                                .requestMatchers("/ws/**").permitAll()

                                .requestMatchers(HttpMethod.POST, "/anno/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/anno/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/certifi/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/certifi/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtFilter(jwtProvider), AbstractPreAuthenticatedProcessingFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource source() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addExposedHeader("Authorization");
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
