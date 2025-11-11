package cl.duoc.fullstack.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomUserDetailsConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails user1 = User.withUsername("user1")
                .passwordEncoder(encoder::encode)
                .password("password")
                .roles("USER")          // ROLE_USER
                .build();

        UserDetails user2 = User.withUsername("user2")
                .passwordEncoder(encoder::encode)
                .password("password")
                .roles("ADMIN")         // ROLE_ADMIN
                .build();

        UserDetails user3 = User.withUsername("user3")
                .passwordEncoder(encoder::encode)
                .password("password")
                .roles("USER", "ADMIN") // ambos roles
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3);
    }
}

