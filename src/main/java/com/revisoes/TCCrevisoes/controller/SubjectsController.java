  package com.revisoes.TCCrevisoes.controller;
  
  
  import java.util.List;
  import jakarta.validation.Valid;
  import org.springframework.http.HttpStatus;
  import org.springframework.http.ResponseEntity;
  import com.revisoes.TCCrevisoes.DTO.SubjectsDto;
  import com.revisoes.TCCrevisoes.dominio.Subjects;
  import com.revisoes.TCCrevisoes.service.SubjectsService;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PutMapping;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestBody;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.DeleteMapping;
  import org.springframework.web.bind.annotation.RestController;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.hibernate.ObjectNotFoundException;
  import org.springframework.beans.factory.annotation.Autowired;
  
  import static org.springframework.http.ResponseEntity.ok;
  import static org.springframework.http.ResponseEntity.status;
  import static org.springframework.http.HttpStatus.CONFLICT;
  import static org.springframework.http.ResponseEntity.noContent;
  import static org.springframework.http.ResponseEntity.notFound;

  @RestController
  @RequestMapping(value = "/subjects")
  public class SubjectsController {

    @Autowired
    private SubjectsService subjectsService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Subjects>> findAll(){
      return ok().body(subjectsService.findAll());

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Subjects> findById(@PathVariable Long id ){
      try {
        return ok().body(subjectsService.findById(id));
      } catch (ObjectNotFoundException e) {
        return notFound().build();
      }
      
    }

    @PostMapping
    public ResponseEntity<Subjects> saveSubjects(@RequestBody @Valid Subjects subjects){
      return status(HttpStatus.CREATED).body(subjectsService.saveSubjects(subjects));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Subjects> saveSubjects(@PathVariable Long id,@RequestBody @Valid SubjectsDto subjectsDto){
      try {
        return ok().body(subjectsService.updateSubjects(id, subjectsDto));
      } catch (ObjectNotFoundException e) {
        return notFound().build();
      }
      
    }

    @PutMapping("/{id}/{id_content}")
    public ResponseEntity<Subjects> updateSubjectsAddContent(@PathVariable Long id, @PathVariable Long id_content){
      try {
         return ok().body(subjectsService.updateSubjectsAddContent(id, id_content));
      } catch (ObjectNotFoundException e) {
          return notFound().build();
      }catch (IllegalArgumentException ex) {
        return status(CONFLICT).build();
    }
     
      
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubjects(@PathVariable Long id){
      try {
        subjectsService.deleteSubjects(id);
        return noContent().build();
      } catch (ObjectNotFoundException e) {
        return notFound().build();
      }
      
    }
      
  }