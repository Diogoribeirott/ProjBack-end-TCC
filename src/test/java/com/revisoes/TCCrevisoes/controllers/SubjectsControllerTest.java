package com.revisoes.TCCrevisoes.controllers;

import java.util.List;
import org.mockito.Mock;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.revisoes.TCCrevisoes.DTO.SubjectsDto;
import com.revisoes.TCCrevisoes.dominio.Subjects;
import org.junit.jupiter.api.extension.ExtendWith;
import com.revisoes.TCCrevisoes.service.SubjectsService;
import com.revisoes.TCCrevisoes.controller.SubjectsController;
import com.revisoes.TCCrevisoes.Exceptions.ObjectNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class SubjectsControllerTest {

  @InjectMocks
  private SubjectsController subjectsController;

  @Mock
  private SubjectsService subjectsService;

  @Test
  @DisplayName("save new subject when sucessfull")
  void saveNewSubjectWithSucessfulAndReturnStatusCreatedTest(){

    //scenario
    Subjects subject = createSubject();
    BDDMockito.when(subjectsService.saveSubjects(any(Subjects.class))).thenReturn(subject);

    //execution
     ResponseEntity<Subjects> objectSubjectResponseEntity = subjectsController.saveSubjects(subject);

    //validation
    Subjects subjectR = objectSubjectResponseEntity.getBody();
    assertThat(objectSubjectResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(subjectR).isNotNull();
    assertThat(subjectR).isEqualTo(subject);
    assertThat(subjectR.getId()).isNotNull();
  }

  @Test
  @DisplayName("trying to save new empty subject  when invalid object subject")
  void tryingToSaveNewEmptySubjectAndReturnStatusCreatedWithErrorTest(){

    //scenario
    BDDMockito.when(subjectsService.saveSubjects(any())).thenThrow(new IllegalArgumentException("error in object declaration"));

    //execution and validation
    Assertions.assertThatThrownBy(()-> subjectsService.saveSubjects(new Subjects()))
                                .isInstanceOf(IllegalArgumentException.class)
                                .hasMessage("error in object declaration");
  }

    @Test
    @DisplayName("should return a list of subject")
    public void findAllSubjectTest(){

      //scenario
      Subjects subject = createSubject();
      List<Subjects> listSubject = List.of(subject);

      BDDMockito.when(subjectsService.findAll()).thenReturn(listSubject);

      //execution 
      ResponseEntity<List<Subjects>> ResponseEntityListSubject = subjectsController.findAll();

      //validation
      List<Subjects> listwithSubject = ResponseEntityListSubject.getBody();
      assertThat(ResponseEntityListSubject.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(listwithSubject.get(0)).isNotNull();
      assertThat(listwithSubject.get(0)).isEqualTo(subject);

    }

    @Test
    @DisplayName("should return a  subject")
    public void findByIdSubjectTest(){

      //scenario
      Subjects subject = createSubject();
      BDDMockito.when(subjectsService.findById(anyLong())).thenReturn(subject);

      //execution 
       ResponseEntity<Subjects> responseEntitySubject = subjectsController.findById(1l);

      //validation
      Subjects subjectR = responseEntitySubject.getBody();
      assertThat(responseEntitySubject.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(subjectR).isNotNull();
      assertThat(subjectR).isEqualTo(subject);
    }

    @Test
    @DisplayName("should return ObjectNotFoundException when it doesn't exist subject ")
    public void findByIdWhenSubjectDoesNotExistTest(){

      //scenario
      BDDMockito.when(subjectsService.findById(100L)).thenThrow(new ObjectNotFoundException("Object not found"));

      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> subjectsController.findById(100L));

      //validation
      assertThat(assertThrows.getMessage()).isEqualTo("Object not found");
    }

    @Test
    @DisplayName("should delete a Subject")
    public void deleteByIdSubjectWhenSucessful(){

      //scenario
      BDDMockito.doNothing().when(subjectsService).deleteSubjects(anyLong());

      //execution
      ResponseEntity<Void> responseEntityVoidOfDelete = subjectsController.deleteSubjects(1l);

      //validation
      assertThat(responseEntityVoidOfDelete).isNotNull();
      assertThat(responseEntityVoidOfDelete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("trying to delete a Subjects that doesn't exist and return Object not found exception")
    public void deleteByIdSubjectsWhenNofound(){

      //scenario
     when(subjectsController.deleteSubjects(anyLong())).thenThrow(new ObjectNotFoundException("object not found"));

      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> subjectsController.deleteSubjects(1l));

      //validation
      assertThat(assertThrows.getMessage()).isEqualTo("object not found");
    }

    @Test
    @DisplayName("must update subject name")
    public void updateSubjecNametWhenSucessfull(){
      
      //scenario
      Subjects subject = createSubject();
       SubjectsDto subjectsDto = createSubjectsDto();
      BDDMockito.when(subjectsService.updateSubjects(anyLong(),any(SubjectsDto.class))).thenReturn(subject);

      //execution
      ResponseEntity<Subjects> responseEntitySubject = subjectsController.saveSubjects(1l,subjectsDto);

      //validation
      Subjects subjectR = responseEntitySubject.getBody();
      assertThat(responseEntitySubject.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(subjectR).isNotNull();
      assertThat(subjectR.getName()).isEqualTo(subjectsDto.getName());
    }

    @Test
    @DisplayName("trying to update a subject that does not exist and returning Object not found exception")
    public void updateByIdSubjectWhenNofoundSubject(){

      //scenario
     BDDMockito.when(subjectsController.saveSubjects(1L ,new SubjectsDto())).thenThrow(new ObjectNotFoundException("object not found"));

      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> subjectsController.saveSubjects(1l,new SubjectsDto()));

      //validation
      assertThat(assertThrows.getMessage()).isEqualTo("object not found");
    }

    @Test
    @DisplayName("should adding Content in subject")
    public void updateRUserAddingContentInSubject(){

      //scenario
      Subjects subject = createSubject();
      BDDMockito.when(subjectsService.updateSubjectsAddContent(anyLong(),anyLong())).thenReturn(subject);

      //execution
      ResponseEntity<Subjects> responseEntitySubject = subjectsController.updateSubjectsAddContent(1l, 1l);

      //validation
      Subjects subjectR = responseEntitySubject.getBody();
      assertThat(responseEntitySubject.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(subjectR).isNotNull();
      assertThat(subjectR).isEqualTo(subject);
    }

    @Test
    @DisplayName("trying to update a rUser password that does not exist and returning Object not found exception")
    public void updateRUserAddingContentInSubjectButReturnsErrorBecauseOneOrBothAreInvalid(){

      //scenario
     BDDMockito.when(subjectsController.updateSubjectsAddContent(1L ,1l)).thenThrow(new ObjectNotFoundException("object not found"));

      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> subjectsController.updateSubjectsAddContent(1l,1l));

      //validation
      assertThat(assertThrows.getMessage()).isEqualTo("object not found");
    }
 
    private SubjectsDto createSubjectsDto(){
      return SubjectsDto.builder()
                        .name("dev java")
                        .build();
    }

    private Subjects createSubject(){
      return Subjects.builder()
                      .id(1l)
                      .name("dev java")
                      .build();
    }

   
    
}