package com.crm.config;

import com.crm.filter.RequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider ;
    private final RequestFilter requestFilter;
    final String [] byPassUrls={"/auth/**","/rule/**"};
    @Bean
    public SecurityFilterChain securityFilterChain(@NonNull HttpSecurity httpSecurity) throws Exception {
       httpSecurity.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests((auth)-> auth.
                       requestMatchers(byPassUrls).
                       permitAll().
                       anyRequest().
                       authenticated())
        .sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authenticationProvider(authenticationProvider)
               .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
       return httpSecurity.build();
    }
}
