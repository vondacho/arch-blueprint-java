package edu.obya.blueprint;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        // Matches /customers, /customers/, /customers/123
                        .mvcMatchers("/customers/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(EndpointRequest.to("health","info","metrics","loggers")).permitAll()
                        .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .csrf().disable();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // By-passing Security
        return (web) -> web.ignoring().antMatchers("/resources/**");
    }

    @Profile("dev")
    @Bean
    public UserDetailsService userDetailsService() {
        // Uses BCrypt as a default "best practice" encoding scheme for now
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        return new InMemoryUserDetailsManager(
            User.builder()
                    .username("test")
                    .password(passwordEncoder.encode("test"))
                    .roles("USER")
                    .build(),
            User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles("ADMIN")
                    .build()
        );
    }
}
