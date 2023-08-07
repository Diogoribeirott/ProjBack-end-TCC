package com.revisoes.TCCrevisoes.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.List;
import java.util.Optional;

import org.mockito.Mock;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revisoes.TCCrevisoes.DTO.SubjectsDto;
import com.revisoes.TCCrevisoes.dominio.Content;
import com.revisoes.TCCrevisoes.dominio.Subjects;
import com.revisoes.TCCrevisoes.repository.SubjectsRepository;
import com.revisoes.TCCrevisoes.Exceptions.ObjectNotFoundException;

@ExtendWith(SpringExtension.class)
public class SubjectsServiceTest {

  @InjectMocks
  private SubjectsService subjectsService;
    
  @Mock
  private SubjectsRepository subjectsRepository;

  @Mock
  private ContentService contentService;

  @Test
  @DisplayName("should return a Subjects and save")
  public void saveNewSubjectTest(){

    //scenario
    Subjects subject = creaSubjects();
    BDDMockito.when(subjectsRepository.save(subject)).thenReturn(subject);

    //execution
    Subjects subjectR = subjectsService.saveSubjects(subject);

    //validation
    Assertions.assertThat(subjectR).isNotNull();
    Assertions.assertThat(subjectR.getId()).isNotNull();
    Assertions.assertThat(subjectR.getName()).isNotNull();
  }

  @Test
  @DisplayName("sould return a Subject by id")
  public void findIdSubjectsTest(){
    
    //scenario
    Subjects subject = creaSubjects();
    BDDMockito.when(subjectsRepository.findById(anyLong())).thenReturn(Optional.of(subject));

    //execution
    Subjects subjectR = subjectsService.findById(1l);

    //validation
    Assertions.assertThat(subjectR).isNotNull();
    Assertions.assertThat(subjectR.getId()).isNotNull();
    Assertions.assertThat(subjectR.getName()).isNotNull();
  }

  @Test
  @DisplayName("sould return a error when id invalid")
  public void findIdSubjectsAndReturnErrorTest(){
    
    //scenario
    BDDMockito.when(subjectsRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("object not found"));

    //execution
    Assertions.assertThatThrownBy(() -> subjectsService.findById(1L))
    .isInstanceOf(ObjectNotFoundException.class)
    .hasMessage("object not found");
  }

  @Test
  @DisplayName("sould return a list of Subjects")
  public void findAllSubjectsTest(){
    
    //scenario
    Subjects subject = creaSubjects();
    BDDMockito.when(subjectsRepository.findAll()).thenReturn(List.of(subject));

    //execution
     List<Subjects> listSubjects = subjectsService.findAll();

    //validation
    Assertions.assertThat(listSubjects).isNotNull();
    Assertions.assertThat(listSubjects.get(0).getId()).isEqualTo(subject.getId());
    Assertions.assertThat(listSubjects.get(0).getName()).isEqualTo(subject.getName());
  }

  @Test
  @DisplayName("should update  name subject")
  public void updateSubjectsTest() {
      // Scenario
      Subjects subject = creaSubjects();
      SubjectsDto subjectsDto = createSubjectsDto();

      when(subjectsRepository.findById(1l)).thenReturn(Optional.of(subject));
      when(subjectsRepository.save(any(Subjects.class))).thenReturn(subject);

      // Execution
      Subjects updateSubjects = subjectsService.updateSubjects(1l, subjectsDto);

      // Validation
      Assertions.assertThat(updateSubjects).isNotNull();
      Assertions.assertThat(updateSubjects.getId()).isEqualTo(subject.getId());
      Assertions.assertThat(updateSubjects.getName()).isEqualTo(subjectsDto.getName());
    }
    
  @Test
  @DisplayName("trying to update name subject with id invalid and return error")
  public void updateSubjectsWithSubjectIdInvalidTest() {
      // Scenario
      Subjects subject = creaSubjects();
      SubjectsDto subjectsDto = createSubjectsDto();

      when(subjectsRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object invalid"));
      when(subjectsRepository.save(any(Subjects.class))).thenReturn(subject);

      // Execution and Validation
     Assertions.assertThatThrownBy(() -> subjectsService.updateSubjects(1l, subjectsDto))
      .isInstanceOf(ObjectNotFoundException.class)
      .hasMessage("Object invalid");
    }

  @Test
  @DisplayName(" update subject adding Content with sucessful")
  public void updateSubjectsAddingContentTest() {
      // Scenario
      Subjects subject = creaSubjects();

      when(subjectsRepository.findById(anyLong())).thenReturn(Optional.of(subject));
      when(contentService.findById(anyLong())).thenReturn(new Content());
      when(subjectsRepository.save(any(Subjects.class))).thenReturn(subject);

      // Execution
      Subjects subjectR = subjectsService.updateSubjectsAddContent(1l,1l);

      //Validation
      Assertions.assertThat(subjectR).isNotNull();
      Assertions.assertThat(subjectR).isEqualTo(subject);
    }

  @Test
  @DisplayName("trying to update subject invalid or adding content invalid and return error")
  public void updateSubjectsAddingContentWhenObjectInvalidTest() {
      // Scenario
      Subjects subject = creaSubjects();

      when(subjectsRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("object not found"));
      when(contentService.findById(anyLong())).thenReturn(new Content());
      when(subjectsRepository.save(any(Subjects.class))).thenReturn(subject);

      // Execution and Validation
      Assertions.assertThatThrownBy(() ->subjectsService.updateSubjectsAddContent(1l,1l)
      ).isInstanceOf(ObjectNotFoundException.class)
      .hasMessage("object not found");
    }

  @Test
  @DisplayName("delete Subject when successful")
  public void deleteByIdWhenSuccessful() {
      // Scenario
      when(subjectsRepository.findById(1l)).thenReturn(Optional.of(new Subjects()));
      BDDMockito.doNothing().when(subjectsRepository).deleteById(1l);

      // Execution
      subjectsService.deleteSubjects(1l);

      // Validation
      verify(subjectsRepository).findById(1l);
      verify(subjectsRepository).deleteById(1l);
    }

  @Test
  @DisplayName("trying to delete subject when not exist")
  public void deleteByIdWhenUnsuccessful() {
      // Scenario
      when(subjectsRepository.findById(1l)).thenThrow(new ObjectNotFoundException("Object not fould"));
      BDDMockito.doNothing().when(subjectsRepository).deleteById(1l);

      // Execution and Validation
      Assertions.assertThatThrownBy(()->subjectsService.deleteSubjects(1l))
                                      .isInstanceOf(ObjectNotFoundException.class)
                                      .hasMessage("Object not fould");
    }

  public SubjectsDto createSubjectsDto(){
    return SubjectsDto.builder()
                      .name("batata")
                      .build();
  }

  public Subjects creaSubjects(){
   return Subjects.builder()
            .id(1l)
            .name("batata")
            .build();
  }

}