package com.epam.esm.security.config;

import com.epam.esm.model.entity.Role;
import com.epam.esm.security.filter.JwtTokenFilter;
import com.epam.esm.security.permission.Permission;
import com.epam.esm.security.permission.RoleAuthority;
import com.epam.esm.security.service.SecurityUserServiceImpl;
import com.epam.esm.utils.exception.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.util.MimeTypeUtils;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register", "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/certificates/**").permitAll()
                        .requestMatchers(HttpMethod.GET).hasAuthority(Permission.READ.name())
                        .requestMatchers(HttpMethod.POST, "api/orders/**").hasAuthority(Permission.PLACE_ORDER.name())
                        .requestMatchers(HttpMethod.POST).hasAuthority(Permission.WRITE.name())
                        .requestMatchers(HttpMethod.PUT).hasAuthority(Permission.WRITE.name())
                        .requestMatchers(HttpMethod.DELETE).hasAuthority(Permission.WRITE.name())
                        .anyRequest().authenticated())
                .exceptionHandling(http -> http
                        .authenticationEntryPoint(
                                (request, response, authException) -> {
                                    response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                    response.setCharacterEncoding("UTF-8");
                                    String details = authException.getMessage();

                                    ObjectMapper objectMapper = new ObjectMapper();
                                    response.getWriter()
                                            .write(objectMapper.writeValueAsString(new ExceptionResponse(HttpStatus.UNAUTHORIZED.toString(), details)));

                                }
                        ).accessDeniedHandler(
                                (request, response, accessDeniedException) -> {
                                    response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                                    response.setStatus(HttpStatus.FORBIDDEN.value());
                                    response.setCharacterEncoding("UTF-8");
                                    String details = accessDeniedException.getMessage();

                                    ObjectMapper objectMapper = new ObjectMapper();
                                    response.getWriter()
                                            .write(objectMapper.writeValueAsString(new ExceptionResponse(HttpStatus.FORBIDDEN.toString(), details)));

                                }
                        ))
                .addFilterAfter(jwtTokenFilter, ExceptionTranslationFilter.class)
                .oauth2Login()
                .and()
                .logout(c -> {
                    c.logoutUrl("/logout");
                    c.deleteCookies("JSESSIONID");
                    c.invalidateHttpSession(true);
                    c.clearAuthentication(true);
                    c.permitAll();
                });

        return httpSecurity.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        DefaultOAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();

        return userRequest -> {
            OAuth2User user = oAuth2UserService.loadUser(userRequest);

            user.getAuthorities();

            return new DefaultOAuth2User(
                    RoleAuthority.getAuthoritiesForRole(Role.ADMIN),
                    user.getAttributes(),
                    "id");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, DaoAuthenticationProvider authenticationProvider) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(SecurityUserServiceImpl service, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(service);

        return provider;
    }

}
