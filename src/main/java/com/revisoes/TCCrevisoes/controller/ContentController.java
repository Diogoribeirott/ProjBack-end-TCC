package com.revisoes.TCCrevisoes.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.revisoes.TCCrevisoes.DTO.ContentDto;
import com.revisoes.TCCrevisoes.dominio.Content;
import com.revisoes.TCCrevisoes.service.ContentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/content")
public class ContentController {
  @Autowired
  private ContentService contentService;

  @GetMapping(path = "/all")
  public ResponseEntity< List<Content>> findAll(){
    return ok().body(contentService.findAll());

  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Content> findById(@PathVariable Long id){
    try {
      return ok().body(contentService.findById(id));
    } catch (ObjectNotFoundException e) {
      return notFound().build();
    }

  }

  @PostMapping
  public ResponseEntity<Content> saveContent(@Valid @RequestBody Content content){
    return status(HttpStatus.CREATED).body(contentService.saveContent(content));

  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteContent(@PathVariable Long id){
    try {
      contentService.deleteContent(id);
      return noContent().build();
    } catch (ObjectNotFoundException e) {
      return notFound().build();
    }

  }
 
  @PutMapping(value = "/{id}")
  public ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody @Valid ContentDto contentDto){
    try {
       return ok().body(contentService.updateContent(id, contentDto));
    } catch (ObjectNotFoundException e) {
        return notFound().build();
    }
 
  }

  @PutMapping(value = "/{id}/{id_review}")
  public ResponseEntity<Content> updateContentAddReview(@PathVariable Long id, @PathVariable Long id_review){
    try {
      return ok().body(contentService.updateContentAddReview(id,id_review));
    } catch (ObjectNotFoundException e) {
      return notFound().build();
    }catch(IllegalArgumentException e){
      return status(CONFLICT).build();
    }

  }
    
}