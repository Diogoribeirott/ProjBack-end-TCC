package com.revisoes.TCCrevisoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.revisoes.TCCrevisoes.DTO.RUserDTO;
import com.revisoes.TCCrevisoes.dominio.RUser;
import com.revisoes.TCCrevisoes.service.RUserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cadastro")
public class RUserController {

  @Autowired
  private RUserService rUserService;

  @GetMapping(path = "/all")
  public ResponseEntity< List<RUser>> findALL(){
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