package com.revisoes.TCCrevisoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revisoes.TCCrevisoes.DTO.AuthenticationDto;
import com.revisoes.TCCrevisoes.DTO.RUserDTO;
import com.revisoes.TCCrevisoes.dominio.RUser;
import com.revisoes.TCCrevisoes.service.RUserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cadastro")
public class RUserController {

  @Autowired 
  private AuthenticationManager authenticationManager;

  @Autowired
  private RUserService rUserService;


  @PostMapping(path = "/login")
  public ResponseEntity<String> login(@RequestBody @Valid AuthenticationDto data){
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(),data.getPassword());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    return ResponseEntity.ok().body("sucesso!!! " + auth);

  }

  @GetMapping(path = "/all")
  public ResponseEntity< List<RUser>> findAll(){
    return ResponseEntity.status(HttpStatus.OK).body(rUserService.findAll());

  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<RUser> findById(@PathVariable Long id){
   return ResponseEntity.status(HttpStatus.OK).body(rUserService.findById(id));

  }

  @PostMapping
  public ResponseEntity<RUser> sabeRUser(@Valid @RequestBody  RUser rUser){
    return ResponseEntity.status(HttpStatus.CREATED).body(rUserService.saveRUser(rUser));

  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteRUser(@PathVariable Long id){
    rUserService.deleteRUser(id);
    return ResponseEntity.noContent().build();

  }
 
  @PutMapping(value = "/{id}")
  public ResponseEntity<RUser> updateRUser(@PathVariable Long id, @RequestBody @Valid RUserDTO rUserDTO){
  return ResponseEntity.ok().body(rUserService.updateRUser(id, rUserDTO));

}
    
}