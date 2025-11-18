package DemoFromArquetype.fullstack.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

        private final CustomUserDetailsService customUserDetailsService;
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                // Matchers para CSRF ignoring
        var pp = PathPatternRequestMatcher.withDefaults();

        var openApiDocs = pp.matcher("/v3/api-docs/**");
        var swaggerUi   = pp.matcher("/swagger-ui/**");

        http
                .authorizeHttpRequests(auth -> auth
                        //swagger
                        .requestMatchers("/v3/api-docs/**","/swagger-ui.html","/swagger-ui/**").permitAll()
                        // estáticos y páginas públicas
                        .requestMatchers("/", "/home", "/login", "/register").permitAll()
                        // privadas
                        // Reglas por método para /libros/**
                        .requestMatchers(HttpMethod.GET, "/libros/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/libros/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/libros/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/libros/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )

                .csrf(csrf -> csrf.ignoringRequestMatchers(
                         openApiDocs, swaggerUi
                ))
                .userDetailsService(customUserDetailsService)
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

                // Para APIs con Basic, deshabilitar CSRF simplifica (sobre todo con POST/PUT/DELETE)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var cfg = new CorsConfiguration();
        cfg.setAllowedOriginPatterns(List.of("http://localhost:*", "http://127.0.0.1:*"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type","Accept"));
        cfg.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/v3/api-docs/**", cfg);
        source.registerCorsConfiguration("/swagger-ui/**", cfg);
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
