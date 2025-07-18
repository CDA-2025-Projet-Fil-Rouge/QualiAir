package fr.diginamic.qualiair.config;

import fr.diginamic.qualiair.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private static final String[] ROLES_ACTIFS = {"UTILISATEUR", "ADMIN", "SUPERADMIN"};
    private static final String[] ROLES_ADMIN = {"ADMIN", "SUPERADMIN"};

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://92.88.240.121:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter filter) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        //docs
                        .requestMatchers("/swagger-ui/", "/v3/api-docs/**").permitAll()
                        //auth
                        .requestMatchers("/auth/**").permitAll()
                        //carte
                        .requestMatchers("/map/**").permitAll()
                        //favoris
                        .requestMatchers("/favoris/**").permitAll()
                        //remote data
                        .requestMatchers("/commune/recensement/insertion/load-from-server-hosted-files", "/external/api/atmo-france/air-quality/national-data/date/**").permitAll()
                        // forum en lecture
                        .requestMatchers(HttpMethod.GET,"/forum/**").permitAll()

                        //historique
                        .requestMatchers("/historique/**").hasAnyRole(ROLES_ACTIFS)
                        // forum: messages + topics
                        .requestMatchers(HttpMethod.POST, "/forum/message/**", "/forum/topic/**").hasAnyRole(ROLES_ACTIFS)
                        .requestMatchers(HttpMethod.PUT, "/forum/message/**", "/forum/topic/**").hasAnyRole(ROLES_ACTIFS)
                        .requestMatchers(HttpMethod.DELETE, "/forum/message/**", "/forum/topic/**").hasAnyRole(ROLES_ACTIFS)

                        // forum : rubriques
                        .requestMatchers(HttpMethod.POST, "/forum/rubrique/**").hasAnyRole(ROLES_ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/forum/rubrique/**").hasAnyRole(ROLES_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/forum/rubrique/**").hasAnyRole(ROLES_ADMIN)
                        // Admin : gestion des utilisateurs
                        .requestMatchers("/user/get-all-paginated", "/user/get-all", "/user/toggle-admin/**", "/user/toggle-activation/**", "/user/toggle-ban/**").hasAnyRole(ROLES_ADMIN)

                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
