package com.crm.filter;

import com.crm.service.impl.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException
    {

        String token=request.getHeader("Authorization");
        if(token==null||!token.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return ;
        }
        token=token.substring(7);
        String email=jwtService.extractUsername(token);
        if(SecurityContextHolder.getContext().getAuthentication()==null && email!=null)
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if(jwtService.isTokenValid(token,email)) {
                UsernamePasswordAuthenticationToken upassToken = new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());
                upassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upassToken);

            }
        }
        filterChain.doFilter(request, response);
    }
}
