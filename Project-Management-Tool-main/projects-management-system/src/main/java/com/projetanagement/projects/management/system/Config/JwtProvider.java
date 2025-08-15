package com.projetanagement.projects.management.system.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.crypto.SecretKey;

import java.util.Date;

public class JwtProvider {
  static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());


    public static  String generateToken(Authentication auth){
       String Jwt = Jwts.builder()
               .setIssuedAt(new Date())
               .setExpiration(new Date(new Date().getTime()+864000000))
               .claim("email", auth.getName())
               .signWith(key)
               .compact();
       return  Jwt ;
    }

    public static String getEmailFromToken(String jwt){
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("email"));
         return  email;
    }


}
