package com.pokemon.pokemonGame.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtGenerator {
  private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  public String generateToken(Authentication authentication){
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expireData = new Date(currentDate.getTime() + SecurityConstant.JWT_EXPIRATION);

    String token = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(expireData)
            .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
            .compact();
    return token;
  }
  public String getUsernameFromJWT(String token){
    Claims claims = Jwts
            .parser()
            .build()
            .parseClaimsJws(token)
            .getBody();
    return claims.getSubject();
  }

  public boolean validateToken(String token){
    try{
      Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
      return true;
    }catch (Exception exception){
      throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
    }
  }
}
