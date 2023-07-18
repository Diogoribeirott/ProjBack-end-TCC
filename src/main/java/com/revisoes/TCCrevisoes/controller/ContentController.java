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
import com.revisoes.TCCrevisoes.DTO.ContentDto;
import com.revisoes.TCCrevisoes.dominio.Content;
import com.revisoes.TCCrevisoes.service.ContentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/content")
public class ContentController {
  @Autowired
  private ContentService contentService;

  @GetMapping(path = "/all")
  public ResponseEntity< List<Content>> findAll(){
    return ResponseEntity.status(HttpStatus.OK).body(contentService.findAll());

  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Content> findById(@PathVariable Long id){
   return ResponseEntity.status(HttpStatus.OK).body(contentService.findById(id));

  }

  @PostMapping
  public ResponseEntity<Content> sabeContent(@Valid @RequestBody  Content content){
    return ResponseEntity.status(HttpStatus.CREATED).body(contentService.saveContent(content));

  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteContent(@PathVariable Long id){
    contentService.deleteContent(id);
    return ResponseEntity.noContent().build();

  }
 
  @PutMapping(value = "/{id}")
  public ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody @Valid ContentDto contentDto){
  return ResponseEntity.ok().body(contentService.updateContent(id, contentDto));

}
    
}