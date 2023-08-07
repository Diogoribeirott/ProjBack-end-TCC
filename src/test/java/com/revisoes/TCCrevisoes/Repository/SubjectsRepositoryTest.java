package com.revisoes.TCCrevisoes.Repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.revisoes.TCCrevisoes.dominio.Subjects;
import com.revisoes.TCCrevisoes.repository.SubjectsRepository;

@DataJpaTest
public class SubjectsRepositoryTest {

  @Autowired
  private SubjectsRepository subjectsRepository;

  @Test
  @DisplayName("must persist a subject in the database")
  public void saveSubjectsTest(){
    //scenario 
    Subjects subjects = createSubjectsAdmin();

    //execution 
    Subjects sujectR = subjectsRepository.save(subjects);

    //validation
    Assertions.assertThat(sujectR).isNotNull();
  }

  @Test
  @DisplayName("must persist a subject in the database after updating ")
  public void updateSubjectsTest(){
    //scenario 
    Subjects subjects = createSubjectsAdmin();

    //execution 
    Subjects sujectR = subjectsRepository.save(subjects);
    sujectR.setName("update");
    Subjects subjectsUpdate = subjectsRepository.save(sujectR);

    //validation
    Assertions.assertThat(subjectsUpdate).isNotNull();
    Assertions.assertThat(subjectsUpdate.getName()).isEqualTo("update");
  }
  
  @Test
  @DisplayName("must delete a subject in the database")
  public void deleteSubjectsTest(){
    //scenario 
    Subjects subjects = createSubjectsAdmin();

    //execution 
    subjectsRepository.save(subjects);
    subjectsRepository.deleteById(subjects.getId());
    Optional<Subjects> subjectOptional = subjectsRepository.findById(subjects.getId());

    //validation
    Assertions.assertThat(subjectOptional).isEmpty();
  }

  @Test
  @DisplayName("must return a suject")
  public void findByidSubjectsTest(){
    //scenario 
    Subjects subjects = createSubjectsAdmin();

    //execution 
    subjectsRepository.save(subjects);
    Optional<Subjects> sujectOptional = subjectsRepository.findById(subjects.getId());
    
    //validation
    Assertions.assertThat(sujectOptional).isNotNull();
    Assertions.assertThat(sujectOptional).isNotEmpty();
  }

  @Test
  @DisplayName("must return a suject")
  public void findByAllSubjectsTest(){
    //scenario 
    Subjects subjects = createSubjectsAdmin();

    //execution 
    subjectsRepository.save(subjects);
    List<Subjects> listSubjectss = subjectsRepository.findAll();
    
    //validation
    Assertions.assertThat(listSubjectss).isNotNull();
    Assertions.assertThat(listSubjectss).isNotEmpty();
  }

    private Subjects createSubjectsAdmin(){
      return Subjects.builder()
                  .id(1l)
                  .name("batata")
                  .build();
    }
    
}