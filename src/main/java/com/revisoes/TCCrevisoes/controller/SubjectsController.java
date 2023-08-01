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
  import org.springframework.beans.factory.annotation.Autowired;

  @RestController
  @RequestMapping(value = "/subjects")
  public class SubjectsController {

    @Autowired
    private SubjectsService subjectsService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Subjects>> findAll(){
      return ResponseEntity.ok().body(subjectsService.findAll());

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Subjects> findById(@PathVariable Long id ){
      return ResponseEntity.ok().body(subjectsService.findById(id));

    }

    @PostMapping
    public ResponseEntity<Subjects> saveSubjects(@RequestBody @Valid Subjects subjects){
      return ResponseEntity.status(HttpStatus.CREATED).body(subjectsService.saveSubjects(subjects));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Subjects> saveSubjects(@PathVariable Long id,@RequestBody @Valid SubjectsDto subjectsDto){
      return ResponseEntity.ok().body(subjectsService.updateSubjects(id, subjectsDto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubjects(@PathVariable Long id){
      subjectsService.deleteSubjects(id);
      return ResponseEntity.noContent().build();
    }
      
  }