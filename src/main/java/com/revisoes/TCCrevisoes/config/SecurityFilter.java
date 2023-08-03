package com.revisoes.TCCrevisoes.config;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import com.revisoes.TCCrevisoes.repository.RUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private RUserRepository rUserRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
 
    var token = this.recoverToken(request);
      if(token != null){

        var login = tokenService.validateToken(token);
        UserDetails rUser = rUserRepository.findByLogin(login);
        var authentication = new UsernamePasswordAuthenticationToken(rUser,null,rUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

      }
    filterChain.doFilter(request, response);
    
  }

  private String recoverToken(HttpServletRequest request){
    var authHeader = request.getHeader("Authorization");
      if(authHeader == null) return null;

    return authHeader.replace("Bearer ", "" );

  }
    
}