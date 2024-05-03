package com.pokemon.pokemonGame.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private JwtGenerator jwtGenerator;
  @Autowired
  private CustomUserDetailService customUserDetailService;



  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
   String token = getJwtFromRequest(request);
   if (StringUtils.hasText(token) && jwtGenerator.validateToken(token)){
     String username = jwtGenerator.getUsernameFromJWT(token);

     UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
     UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,
             userDetails.getAuthorities());
     authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
     SecurityContextHolder.getContext().setAuthentication(authenticationToken);
   }
   filterChain.doFilter(request, response);

  }

  private String getJwtFromRequest(HttpServletRequest request){
    String bearToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearToken) && bearToken.startsWith("Bearer ")){
      return bearToken.substring(7, bearToken.length());
    }
    return null;
  }
}
