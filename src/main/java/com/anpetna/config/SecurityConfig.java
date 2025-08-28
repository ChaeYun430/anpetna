package com.anpetna.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// ⭐ 추가: PasswordEncoder import
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CORS + CSRF
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())

                // 세션(무상태, JWT 기준)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 권한 규칙
                .authorizeHttpRequests(auth -> auth
                                // preflight 허용
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                                // 로그인/리프레시/정적/에러 등 완전 허용
                                .requestMatchers(
                                        "/jwt/login",
                                        "/jwt/refresh",
                                        "/actuator/**",
                                        "/error",
                                        "/favicon.ico",
                                        "/static/**", "/css/**", "/js/**", "/images/**"
                                ).permitAll()

                                // ========================= Board =============================
                                .requestMatchers(HttpMethod.GET, "/anpetna/board/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/anpetna/comment/**").permitAll()

                                // ======================== Item =================================
                                .requestMatchers(HttpMethod.GET, "/items/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll()

                                // ======================== Member ================================
                                .requestMatchers("/member/join").permitAll()

                                // 나머지는 인증
                                .anyRequest().authenticated()
                )
                // 로그아웃
                .logout(logout -> logout
                        // jwt/logout 허용
                        .logoutUrl("/jwt/logout").permitAll()
                )

                // 기본 인증/폼 로그인 비활성
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable());

        // JWT 필터가 있다면 여기에 추가
        // http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // JwtServiceImpl 등에서 주입받는 PasswordEncoder 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt 사용 (강도 기본값 10). 필요하면 new BCryptPasswordEncoder(12)로 조정
        return new BCryptPasswordEncoder();
    }

    // CORS 허용 설정 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of(
                "http://192.168.0.168:3000",
                "http://192.168.0.160:3000",
                "http://localhost:3000"
        ));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);
        cfg.setExposedHeaders(List.of("Authorization", "Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
