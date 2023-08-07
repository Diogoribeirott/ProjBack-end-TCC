package com.revisoes.TCCrevisoes.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.List;
import java.util.Optional;


import org.mockito.Mock;
import org.mockito.BDDMockito;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revisoes.TCCrevisoes.DTO.RUserDTO;
import com.revisoes.TCCrevisoes.dominio.RUser;
import com.revisoes.TCCrevisoes.enums.RUserEnum;
import com.revisoes.TCCrevisoes.dominio.Subjects;
import com.revisoes.TCCrevisoes.repository.RUserRepository;
import com.revisoes.TCCrevisoes.Exceptions.ObjectNotFoundException;

@ExtendWith(SpringExtension.class)
public class RUserServiceTest {

  @InjectMocks
  private RUserService rUserService;
    
  @Mock
  private RUserRepository rUserRepository;

  @Mock
  private SubjectsService subjectsService;

  @Test
  @DisplayName("should return a RUser and save")
  public void saveNewRUserTest(){

    //scenario
    RUser rUser = createRUser();
    System.out.println(rUser);
    when(rUserRepository.save(rUser)).thenReturn(rUser);

    //execution
    RUser rUserR = rUserService.saveRUser(rUser);

    //validation
    Assertions.assertThat(rUserR).isNotNull();
    Assertions.assertThat(rUserR.getId()).isNotNull();
    Assertions.assertThat(rUserR.getName()).isNotNull();
  }

  @Test
  @DisplayName("sould return a rUser by id")
  public void findIdRUserTest(){
    
    //scenario
    RUser rUser = createRUser();
    BDDMockito.when(rUserRepository.findById(anyLong())).thenReturn(Optional.of(rUser));

    //execution
    RUser rUserR = rUserService.findById(1l);

    //validation
    Assertions.assertThat(rUserR).isNotNull();
    Assertions.assertThat(rUserR.getId()).isNotNull();
    Assertions.assertThat(rUserR.getName()).isNotNull();
  }

  @Test
  @DisplayName("sould return a error when id invalid")
  public void findIdRUserAndReturnErrorTest(){
    
    //scenario
    BDDMockito.when(rUserRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("object not found"));

    //execution
    Assertions.assertThatThrownBy(() -> rUserService.findById(1L))
    .isInstanceOf(ObjectNotFoundException.class)
    .hasMessage("object not found");
  }

  @Test
  @DisplayName("sould return a list of RUser")
  public void findAllRUserTest(){
    
    //scenario
    RUser rUser = createRUser();
    BDDMockito.when(rUserRepository.findAll()).thenReturn(List.of(rUser));

    //execution
     List<RUser> listRUser = rUserService.findAll();

    //validation
    Assertions.assertThat(listRUser).isNotNull();
    Assertions.assertThat(listRUser.get(0).getId()).isEqualTo(rUser.getId());
    Assertions.assertThat(listRUser.get(0).getName()).isEqualTo(rUser.getName());
  }

  @Test
  @DisplayName("should update password rUser")
  public void updateRUserTest() {
      // Scenario
      RUser rUser = createRUser();
      RUserDTO rUserDTO = createRUserDto();

      when(rUserRepository.findById(1l)).thenReturn(Optional.of(rUser));
      when(rUserRepository.save(any(RUser.class))).thenReturn(rUser);

      // Execution
      RUser updateRUser = rUserService.updateRUser(1l, rUserDTO);

      // Validation
      Assertions.assertThat(updateRUser).isNotNull();
      Assertions.assertThat(updateRUser.getId()).isEqualTo(rUser.getId());
      Assertions.assertThat(updateRUser.getPassword()).isEqualTo(rUser.getPassword());
    }
  
  @Test
  @DisplayName("trying to update name rUser with id invalid and return error")
  public void updateRUserWithRUserIdInvalidTest() {
      // Scenario
      RUser rUser = createRUser();
      RUserDTO RUserDTO = createRUserDto();

      when(rUserRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object invalid"));
      when(rUserRepository.save(any(RUser.class))).thenReturn(rUser);

      // Execution and Validation
     Assertions.assertThatThrownBy(() -> rUserService.updateRUser(1l, RUserDTO))
      .isInstanceOf(ObjectNotFoundException.class)
      .hasMessage("Object invalid");
    }

  @Test
  @DisplayName(" update rUser adding Subject with sucessful")
  public void updateRUserAddingSujectTest() {
      // Scenario
      RUser rUser = createRUser();

      when(rUserRepository.findById(anyLong())).thenReturn(Optional.of(rUser));
      when(subjectsService.findById(anyLong())).thenReturn(new Subjects());
      when(rUserRepository.save(any(RUser.class))).thenReturn(rUser);

      // Execution
      RUser rUserR = rUserService.updateRUserAddSubjects(1l,1l);

      //Validation
      Assertions.assertThat(rUserR).isNotNull();
      Assertions.assertThat(rUserR).isEqualTo(rUser);
    }

  @Test
  @DisplayName("trying to update rUser invalid or adding Subject invalid and return error")
  public void updateRUserAddingSubjectWhenObjectInvalidTest() {
      // Scenario
      RUser rUser = createRUser();

      when(rUserRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("object not found"));
      when(subjectsService.findById(anyLong())).thenReturn(new Subjects());
      when(rUserRepository.save(any(RUser.class))).thenReturn(rUser);

      // Execution and Validation
      Assertions.assertThatThrownBy(() ->rUserService.updateRUserAddSubjects(1l,1l)
      ).isInstanceOf(ObjectNotFoundException.class)
      .hasMessage("object not found");
    }
 
  @Test
  @DisplayName("delete rUser when successful")
  public void deleteByIdWhenSuccessful() {
      // Scenario
      when(rUserRepository.findById(1l)).thenReturn(Optional.of(new RUser()));
      BDDMockito.doNothing().when(rUserRepository).deleteById(1l);

      // Execution
      rUserService.deleteRUser(1l);

      // Validation
      verify(rUserRepository).findById(1l);
      verify(rUserRepository).deleteById(1l);
    }

  @Test
  @DisplayName("trying to delete rUser when not exist")
  public void deleteByIdWhenUnsuccessful() {
      // Scenario
      when(rUserRepository.findById(1l)).thenThrow(new ObjectNotFoundException("Object not fould"));
      BDDMockito.doNothing().when(rUserRepository).deleteById(1l);

      // Execution and Validation
      Assertions.assertThatThrownBy(()->rUserService.deleteRUser(1l))
                                      .isInstanceOf(ObjectNotFoundException.class)
                                      .hasMessage("Object not fould");
    }

  public RUserDTO createRUserDto(){
    return RUserDTO.builder()
                      .password("123456")
                      .build();
  }

  public RUser createRUser(){
   return RUser.builder()
            .id(1l)
            .name("batata")
            .password("123456")
            .login("batata")
            .email("batata@gmail.com")
            .role(RUserEnum.ADMIN)
            .build();
  }
    
}