package edu.obya.blueprint.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.security.config.Customizer.withDefaults;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .authorizeHttpRequests((authz) -> authz
                        .mvcMatchers("/customers", "/customers/**").hasRole("USER")
                        .requestMatchers(EndpointRequest.to("health","info","metrics","loggers")).permitAll()
                        .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAnyRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(withDefaults())
                .csrf().disable();
        return http.build();
    }

    /**
     * @return a CORS configuration for trying out the API from Swagger UI
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:*", "https://vondacho.github.io"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(List.of(AUTHORIZATION, CONTENT_TYPE));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/customers/**", configuration);
        return source;
    }

    @Profile("dev")
    @Bean
    public UserDetailsService devUserDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("user"))
                        .roles("USER")
                        .build(),
                User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles("USER", "ADMIN")
                        .build()
        );
    }

    @Profile("test")
    @Bean
    public UserDetailsService testUserDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new InMemoryUserDetailsManager(
            User.builder()
                    .username("anonymous")
                    .password(passwordEncoder.encode("none"))
                    .roles("NONE")
                    .build(),
            User.builder()
                    .username("test")
                    .password(passwordEncoder.encode("test"))
                    .roles("USER")
                    .build(),
            User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles("USER", "ADMIN")
                    .build()
        );
    }
}
