package com.s2f.s2fapi.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> {
                            request.requestMatchers(mvc.pattern("/api/v1/auth/**")).permitAll();
                            request.requestMatchers(mvc.pattern("/swagger-ui/**"))
                                    .permitAll(); // Permet l'accès à Swagger UI
                            request.requestMatchers(mvc.pattern("/v3/api-docs/**"))
                                    .permitAll(); // Permet l'accès aux API docs
                            request.anyRequest().authenticated();
                        })
                .sessionManagement(
                        httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults());
        return httpSecurity.build();
    }

    //    @Bean
    //    public CorsConfigurationSource corsConfigurationSource() {
    //        log.info(String.format("[%s][%s] => check my cors config", this.getClass(),
    // "corsConfigurationSource"));
    //        CorsConfiguration configuration = new CorsConfiguration();
    //        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    //        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE",
    // "OPTIONS"));
    //        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type",
    // "x-auth-token", "Authorization"));
    //        configuration.setExposedHeaders(Arrays.asList("x-auth-token", "X-Get-Header"));
    //        UrlBasedCorsConfigurationSource source = new
    //                UrlBasedCorsConfigurationSource();
    //        source.registerCorsConfiguration("/**", configuration);
    //
    //        return source;
    //    }
}
