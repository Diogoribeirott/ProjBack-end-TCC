package com.revisoes.TCCrevisoes.controllers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.revisoes.TCCrevisoes.DTO.AuthenticationDto;
import com.revisoes.TCCrevisoes.Exceptions.ObjectNotFoundException;
import com.revisoes.TCCrevisoes.config.TokenService;
import com.revisoes.TCCrevisoes.controller.RUserController;
import com.revisoes.TCCrevisoes.dominio.RUser;
import com.revisoes.TCCrevisoes.enums.RUserEnum;
import com.revisoes.TCCrevisoes.service.RUserService;

@ExtendWith(SpringExtension.class)
public class RUserControlerTest {
  
  @InjectMocks
  private RUserController rUserController;

  @Mock
  private RUserService rUserService;

  @Mock
    private AuthenticationManager authenticationManager;

  @Mock
  private TokenService tokenService;
  

  @Test
  @DisplayName("save new rUser when sucessfull")
  void saveNewRUserWithSucessfulAndReturnStatusCreatedWithoutErrorTest(){
    //scenario
    RUser createRUserAdmin = createRUserAdmin();
    BDDMockito.when(rUserService.saveRUser(any(RUser.class))).thenReturn(createRUserAdmin);

    //execution
    ResponseEntity<RUser> rUser = rUserController.saveRUser(createRUserAdmin);

    //validation
    RUser rUserBody = rUser.getBody();
    Assertions.assertThat(rUser.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    Assertions.assertThat(rUserBody).isNotNull();
    Assertions.assertThat(rUserBody).isEqualTo(createRUserAdmin);
    Assertions.assertThat(rUserBody.getId()).isNotNull();

  }

  @Test
  @DisplayName("trying new empty rUser  when unsucessfull")
  void tryingToSaveNewEmptyRUserAndReturnStatusCreatedWithoutErrorTest(){
    //scenario
    
    BDDMockito.when(rUserService.saveRUser(any())).thenThrow(new RuntimeException(
"error in object declaration "));

    //execution and validation
    Assertions.assertThatThrownBy(()-> rUserController.saveRUser(new RUser()))
                                .isInstanceOf(RuntimeException.class)
                                .hasMessage("error in object declaration ");

  }
 @Test
 @DisplayName("when login sucessful")
  public void testLoginSuccess() {
        //scenario
        AuthenticationDto loginData = new AuthenticationDto(null, null);
        loginData.setLogin("admin");
        loginData.setPassword("123456");

        Authentication authentication = new UsernamePasswordAuthenticationToken(new RUser(), null);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        String token = "token";
        when(tokenService.generateToken(any(RUser.class))).thenReturn(token);

        //execution
        ResponseEntity<String> responseEntity = rUserController.login(loginData);

        //validation
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(token);
    }

    @Test
    @DisplayName("should return a list of ruser")
    public void findAllRUserTest(){
      //scenario
      RUser rUserAdmin = createRUserAdmin();
      List<RUser> listRUser = List.of(rUserAdmin);

      BDDMockito.when(rUserService.findAll()).thenReturn(listRUser);

      //execution 
      ResponseEntity<List<RUser>> listOfRUser = rUserController.findAll();

      //validation
      List<RUser> listBodyRUser = listOfRUser.getBody();
      Assertions.assertThat(listOfRUser.getStatusCode()).isEqualTo(HttpStatus.OK);
      Assertions.assertThat(listBodyRUser.get(0)).isNotNull();
      Assertions.assertThat(listBodyRUser.get(0)).isEqualTo(rUserAdmin);

    }

    @Test
    @DisplayName("should return a  ruser")
    public void findByIdRUserTest(){
      //scenario
      RUser rUserAdmin = createRUserAdmin();

      BDDMockito.when(rUserService.findById(anyLong())).thenReturn(rUserAdmin);

      //execution 
       ResponseEntity<RUser> rUser = rUserController.findById(1l);

      //validation
      RUser rUserBody = rUser.getBody();
      Assertions.assertThat(rUser.getStatusCode()).isEqualTo(HttpStatus.OK);
      Assertions.assertThat(rUserBody).isNotNull();
      Assertions.assertThat(rUserBody).isEqualTo(rUserAdmin);

    }

    @Test
    @DisplayName("should return error :ObjectNotFoundException when it doesn't exist rUser ")
    public void findByIdWhenRUserDoesNotExistTest(){
      //scenario
      BDDMockito.when(rUserService.findById(100L)).thenThrow(new ObjectNotFoundException("Object not found"));
      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> rUserController.findById(100L));
      //validation
      Assertions.assertThat(assertThrows.getMessage()).isEqualTo("Object not found");

    }

    @Test
    @DisplayName("should delete a rUser")
    public void deleteByIdRUserWhenSucessful(){
      //scenario
      BDDMockito.doNothing().when(rUserService).deleteRUser(anyLong());
      //execution
      ResponseEntity<Void> deleteRUser = rUserController.deleteRUser(1l);
      //validation
      Assertions.assertThat(deleteRUser).isNotNull();
      Assertions.assertThat(deleteRUser.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

     @Test
    @DisplayName("trying to delete a rUser that doesn't exist and return Object not found exception")
    public void deleteByIdRUserWhenNofoundRUser(){
      //scenario
     when(rUserController.deleteRUser(anyLong())).thenThrow(new ObjectNotFoundException("object not found"));
      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> rUserController.deleteRUser(1l));
      //validation
      Assertions.assertThat(assertThrows.getMessage()).isEqualTo("object not found");
    }

    


    
    private RUser createRUserAdmin(){
      return RUser.builder()
                  .id(1l)
                  .name("admin")
                  .password("123456")
                  .email("admin@gmail.com")
                  .dateOfBirth(LocalDate.of(2023, 8, 2))
                  .role(RUserEnum.ADMIN)
                  .build();
    }
   
}