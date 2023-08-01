package com.revisoes.TCCrevisoes.config;

import com.auth0.jwt.JWT;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.LocalDateTime;
import com.auth0.jwt.algorithms.Algorithm;
import com.revisoes.TCCrevisoes.dominio.RUser;
import org.springframework.stereotype.Service;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(RUser rUser){
    try{

      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token =JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(rUser.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

          return token;

    }catch(JWTCreationException exception){
      throw new RuntimeException("Error while generating token", exception);

    }
    
  }

      public String validateToken(String token){
      try {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        
        return JWT.require(algorithm)
                          .withIssuer("auth-api")
                          .build()
                          .verify(token)
                          .getSubject();
      
      } catch (JWTVerificationException exception) {
        return "";

      }

    }
    
  private Instant genExpirationDate(){
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
    
}