package cb.task.backend.config;

import cb.task.backend.entity.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(antMatcher("/swagger-ui/**"),
                                    antMatcher("/swagger-ui.html"),
                                    antMatcher("/v3/**"),
                                    antMatcher("/h2-console/**")).permitAll()
                            .requestMatchers(antMatcher("/product")).authenticated()
                            .requestMatchers(antMatcher("/order/client_list")).hasRole(RoleEnum.ROLE_USER.getAuthority())
                            .requestMatchers(antMatcher("/order/list")).hasRole(RoleEnum.ROLE_EMPLOYEE.getAuthority())
                            .requestMatchers(antMatcher("/order/create")).hasRole(RoleEnum.ROLE_USER.getAuthority())
                            .requestMatchers(antMatcher("/order/{id}/get_history")).authenticated()
                            .requestMatchers(antMatcher("/order/{id}/pay")).hasRole(RoleEnum.ROLE_USER.getAuthority())
                            .requestMatchers(antMatcher("/order/{id}/payBack")).hasRole(RoleEnum.ROLE_USER.getAuthority())
                            .requestMatchers(antMatcher("/order/{id}/delivery")).hasRole(RoleEnum.ROLE_EMPLOYEE.getAuthority())
                            .requestMatchers(antMatcher("/order/{id}/receive")).hasRole(RoleEnum.ROLE_USER.getAuthority())
                            .requestMatchers(antMatcher("/order/{id}/cancel")).hasRole(RoleEnum.ROLE_USER.getAuthority())
                            .anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}