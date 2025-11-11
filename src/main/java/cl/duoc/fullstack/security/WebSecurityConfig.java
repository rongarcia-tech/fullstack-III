package cl.duoc.fullstack.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
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
                /*
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .formLogin(login -> login
                        .loginPage("/login")                 // GET /login (tu controller ya lo tiene)
                        .loginProcessingUrl("/login")        // POST del formulario
                        .defaultSuccessUrl("/home", false)   // tras login OK -> /home (si no hay SavedRequest)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                )
                .csrf(Customizer.withDefaults());*/
                // Sin formularios, sin logout: puro HTTP Basic
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


}
