package com.revisoes.TCCrevisoes.controllers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import org.mockito.Mock;
import java.time.LocalDate;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import com.revisoes.TCCrevisoes.DTO.RUserDTO;
import com.revisoes.TCCrevisoes.dominio.RUser;
import org.springframework.http.ResponseEntity;
import com.revisoes.TCCrevisoes.enums.RUserEnum;
import org.junit.jupiter.api.extension.ExtendWith;
import com.revisoes.TCCrevisoes.config.TokenService;
import com.revisoes.TCCrevisoes.service.RUserService;
import com.revisoes.TCCrevisoes.DTO.AuthenticationDto;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.core.Authentication;
import com.revisoes.TCCrevisoes.controller.RUserController;
import com.revisoes.TCCrevisoes.Exceptions.ObjectNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class RUserControllerTest {
  
  @InjectMocks
  private RUserController rUserController;

  @Mock
  private RUserService rUserService;

  @Mock
  private TokenService tokenService;

  @Mock
  private AuthenticationManager authenticationManager;

  @Test
  @DisplayName("save new rUser when sucessfull")
  void saveNewRUserWithSucessfulAndReturnStatusCreatedTest(){

    //scenario
    RUser createRUserAdmin = createRUserAdmin();
    BDDMockito.when(rUserService.saveRUser(any(RUser.class))).thenReturn(createRUserAdmin);

    //execution
    ResponseEntity<RUser> ObjectRUserResponseEntity = rUserController.saveRUser(createRUserAdmin);

    //validation
    RUser rUser = ObjectRUserResponseEntity.getBody();
    Assertions.assertThat(ObjectRUserResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    Assertions.assertThat(rUser).isNotNull();
    Assertions.assertThat(rUser).isEqualTo(createRUserAdmin);
    Assertions.assertThat(rUser.getId()).isNotNull();

  }

  @Test
  @DisplayName("trying to save new empty rUser when place an invalid object")
  void tryingToSaveNewEmptyRUserAndReturnStatusCreatedWithErrorTest(){

    //scenario
    BDDMockito.when(rUserService.saveRUser(any())).thenThrow(new IllegalArgumentException("error in object declaration "));

    //execution and validation
    Assertions.assertThatThrownBy(()-> rUserController.saveRUser(new RUser()))
                                .isInstanceOf(IllegalArgumentException.class)
                                .hasMessage("error in object declaration ");

  }

 @Test
 @DisplayName("when login sucessful")
  public void LoginSuccesfulTest() {

        //scenario
        AuthenticationDto loginData = new AuthenticationDto("admin", "123456");

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
  @DisplayName("when login unsuccessful because invalid credentials")
  public void LoginUnsuccessfulTest() {

    // Scenario
    AuthenticationDto loginData = new AuthenticationDto("login_invalid", "Password_invalid");

    // Mocking a failed Authentication
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Invalid credentials"));

    // Execution
    ResponseEntity<String> responseEntity = rUserController.login(loginData);

    // Validation
    Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    Assertions.assertThat(responseEntity.getBody()).isNull();
  }

    @Test
    @DisplayName("should return a list of rUser")
    public void findAllRUserShouldReturnAListOfRUserTest(){

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
    public void findByIdRUserShouldReturnARUserTest(){

      //scenario
      RUser rUserAdmin = createRUserAdmin();
      BDDMockito.when(rUserService.findById(anyLong())).thenReturn(rUserAdmin);

      //execution 
       ResponseEntity<RUser> objectRUserResponseEntity = rUserController.findById(1l);

      //validation
      RUser rUser = objectRUserResponseEntity.getBody();
      Assertions.assertThat(objectRUserResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
      Assertions.assertThat(rUser).isNotNull();
      Assertions.assertThat(rUser).isEqualTo(rUserAdmin);

    }

    @Test
    @DisplayName("should return error: ObjectNotFoundException when it doesn't exist rUser ")
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
      ResponseEntity<Void> objectResponseEntityOfDelete = rUserController.deleteRUser(1l);

      //validation
      Assertions.assertThat(objectResponseEntityOfDelete).isNotNull();
      Assertions.assertThat(objectResponseEntityOfDelete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
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

    @Test
    @DisplayName("must update User password")
    public void updateRUserWhenSucessfull(){
      
      //scenario
      RUser rUserAdmin = createRUserAdmin();
      RUserDTO rUserDTO = createRUserDTO();
      BDDMockito.when(rUserService.updateRUser(anyLong(), any(RUserDTO.class))).thenReturn(rUserAdmin);

      //execution
      ResponseEntity<RUser> ObjectRUserResponseEntityOfUpdatePassword = rUserController.updateRUserPassword(1l,rUserDTO );

      //validation
      RUser rUser = ObjectRUserResponseEntityOfUpdatePassword.getBody();
      Assertions.assertThat(ObjectRUserResponseEntityOfUpdatePassword.getStatusCode()).isEqualTo(HttpStatus.OK);
      Assertions.assertThat(rUser).isNotNull();
      Assertions.assertThat(rUser.getPassword()).isEqualTo(rUserDTO.getPassword());

    }

    @Test
    @DisplayName("trying to update a rUser password that does not exist and returning Object not found exception")
    public void updateByIdRUserWhenNofoundRUser(){

      //scenario
     BDDMockito.when(rUserController.updateRUserPassword(1L ,new RUserDTO())).thenThrow(new ObjectNotFoundException("object not found"));

      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> rUserController.updateRUserPassword(1l,new RUserDTO()));

      //validation
      Assertions.assertThat(assertThrows.getMessage()).isEqualTo("object not found");
    }

    @Test
    @DisplayName("adding subject in RUser")
    public void updateRUserAddingSubjectInRUser(){

      //scenario
      RUser rUserAdmin = createRUserAdmin();
      BDDMockito.when(rUserService.updateRUserAddSubjects(anyLong(),anyLong())).thenReturn(rUserAdmin);

      //execution
      ResponseEntity<RUser> rUserWithSubject = rUserController.updateRUserAddSubjectss(1l, 1l);

      //validation
      RUser rUser = rUserWithSubject.getBody();
      Assertions.assertThat(rUserWithSubject.getStatusCode()).isEqualTo(HttpStatus.OK);
      Assertions.assertThat(rUser).isNotNull();
      Assertions.assertThat(rUser).isEqualTo(rUserAdmin);
    }

    @Test
    @DisplayName("trying to update a rUser password that does not exist and returning Object not found exception")
    public void updateRUserAddingSubjectInRUserButReturnsErrorBecauseOneOrBothAreInvalid(){

      //scenario
     BDDMockito.when(rUserController.updateRUserAddSubjectss(1L ,1l)).thenThrow(new ObjectNotFoundException("object not found"));

      //execution 
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> rUserController.updateRUserAddSubjectss(1l,1l));

      //validation
      Assertions.assertThat(assertThrows.getMessage()).isEqualTo("object not found");
    }

    private RUserDTO createRUserDTO(){
      return RUserDTO.builder()
                      .password("123456")
                      .build();
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