package com.math.dailymath.filters;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(filterName = "JWTFilter", urlPatterns = "/*")
public class JWTFilter implements Filter {
    private static final String secret = System.getenv("SECRET");


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        String auth = req.getHeader("Authorization");

        // No authorization received
        if(auth == null || !auth.matches("Bearer .+")){
            System.out.println("Sem autorização: " + auth);
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = auth.replaceAll("Bearer", "").trim();

        try {
            Jws<Claims> jwsClaims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .requireIssuer("dailymath")
                    .build()
                    .parseSignedClaims(token);
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (ExpiredJwtException e){
            System.err.println("JWT expired: " + e.getMessage());
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        } catch (JwtException e){
            System.err.println("JWT error: " + e.getMessage());
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        } catch (Exception e){
            System.err.println("Server error: " + e.getMessage());
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void destroy() {

    }
}
