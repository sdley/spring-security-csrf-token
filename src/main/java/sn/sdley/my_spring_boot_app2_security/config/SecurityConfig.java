package sn.sdley.my_spring_boot_app2_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){

        SimpleUrlLogoutSuccessHandler logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        logoutSuccessHandler.setDefaultTargetUrl("/login?logout");

        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(customizer -> customizer
                        .requestMatchers("/login", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(customizer -> customizer
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .logout(customizer -> customizer
                        .logoutRequestMatcher(PathPatternRequestMatcher.pathPattern(HttpMethod.GET, "/logout"))
                        .logoutSuccessHandler(logoutSuccessHandler)
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User
                .withDefaultPasswordEncoder()
                .username("sdley")
                .password("passer")
                .roles("ADMIN")
                .build();

        UserDetails user2 = User
                .withDefaultPasswordEncoder()
                .username("user2")
                .password("password2")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}
