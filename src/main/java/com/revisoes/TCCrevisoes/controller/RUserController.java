package com.revisoes.TCCrevisoes.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import com.revisoes.TCCrevisoes.DTO.RUserDTO;
import org.hibernate.ObjectNotFoundException;
import com.revisoes.TCCrevisoes.dominio.RUser;
import org.springframework.http.ResponseEntity;
import com.revisoes.TCCrevisoes.config.TokenService;
import com.revisoes.TCCrevisoes.service.RUserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.revisoes.TCCrevisoes.DTO.AuthenticationDto;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping()
public class RUserController {

  @Autowired 
  private AuthenticationManager authenticationManager;

  @Autowired
  private RUserService rUserService;

  @Autowired
  private TokenService tokenService;

  @PostMapping(path = "/login")
  public ResponseEntity<String> login(@RequestBody @Valid AuthenticationDto data){
    try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(),data.getPassword());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((RUser) auth.getPrincipal());
    return ok(token);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

  }

  @GetMapping(path = "/all")
  public ResponseEntity< List<RUser>> findAll(){
    return status(HttpStatus.OK).body(rUserService.findAll());

  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<RUser> findById(@PathVariable Long id){
   try {
          RUser user = rUserService.findById(id);
          return status(HttpStatus.OK).body(user);
        } catch (ObjectNotFoundException ex) {
          return status(NOT_FOUND).build();

        }
  }

  @PostMapping(value = "/register")
  public ResponseEntity<RUser> saveRUser(@Valid @RequestBody  RUser rUser){
    return status(HttpStatus.CREATED).body(rUserService.saveRUser(rUser));

  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteRUser(@PathVariable Long id){
    try {
      rUserService.deleteRUser(id);
      return ResponseEntity.noContent().build();
    } catch (ObjectNotFoundException ex) {
       return status(NOT_FOUND).build();

    }

  }
 
  @PutMapping(value = "/{id}")
  public ResponseEntity<RUser> updateRUserPassword(@PathVariable Long id, @RequestBody @Valid RUserDTO rUserDTO){
    try{
      return ok().body(rUserService.updateRUser(id, rUserDTO));
    }catch(ObjectNotFoundException ex){
      return status(NOT_FOUND).build();
    }
    }

    @PutMapping(value = "/{id}/{id_subjects}")
    public ResponseEntity<RUser> updateRUserAddSubjectss(@PathVariable Long id, @PathVariable Long id_subjects ){
      try {
        return ok().body(rUserService.updateRUserAddSubjects(id,id_subjects));
      }catch (IllegalArgumentException ex) {
        return status(CONFLICT).build();
    }
       catch (ObjectNotFoundException ex) {
        return status(NOT_FOUND).build();
      }
      
    }
  
}