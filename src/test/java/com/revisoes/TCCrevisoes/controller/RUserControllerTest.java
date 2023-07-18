package com.revisoes.TCCrevisoes.controller;

import java.util.List;
import org.mockito.Mock;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import com.revisoes.TCCrevisoes.dominio.RUser;
import com.revisoes.TCCrevisoes.Util.RUserTest;
import org.springframework.http.ResponseEntity;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.extension.ExtendWith;
import com.revisoes.TCCrevisoes.Util.RUserDtoTest;
import static org.mockito.ArgumentMatchers.anyLong;
import com.revisoes.TCCrevisoes.service.RUserService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class RUserControllerTest {

  @InjectMocks
  private RUserController rUserController;

  @Mock
  private RUserService rUserService;

  @BeforeEach
  void setUp(){
    BDDMockito.when(rUserService.findAll()).thenReturn(List.of(RUserTest.createRUser()));
    BDDMockito.when(rUserService.findById(anyLong())).thenReturn(RUserTest.createRUser());
    BDDMockito.when(rUserService.updateRUser(anyLong(),any())).thenReturn(RUserTest.createRUser());
    BDDMockito.doNothing().when(rUserService).deleteRUser(anyLong());

  }

  @Test
  @DisplayName("test find by All and return one list of RUser sucessful")
   void FindByALL_when_sucessful(){
    List<RUser> ListRUser = rUserController.findAll().getBody();
    Assertions.assertThat(ListRUser).isNotEmpty();
    Assertions.assertThat(ListRUser.get(0)).isNotNull();
    Assertions.assertThat(ListRUser.get(0)).isEqualTo(RUserTest.createRUser());

  }

  @Test
  @DisplayName("test find by id and return one RUser sucessful")
  public void FindByID_when_sucessful(){
    RUser rUser = rUserController.findById(5l).getBody();
    Assertions.assertThat(rUser).isNotNull();
    Assertions.assertThat(rUser.getId()).isNotNull();
    Assertions.assertThat(rUser).isEqualTo(RUserTest.createRUser());

  }

  @Test
  @DisplayName("test update when sucessful")
  void update_when_sucessful(){
    RUser rUser = rUserController.updateRUser(2l, RUserDtoTest.createUserDTO()).getBody();
    Assertions.assertThat(rUser).isNotNull();
    Assertions.assertThat(rUser.getId()).isNotNull();
    Assertions.assertThat(rUser).isEqualTo(RUserTest.createRUser());

  }

   @Test
  @DisplayName("test delete when sucessful")
  void delete_when_sucessful(){ 
    Assertions.assertThatCode(() -> rUserController.deleteRUser(2l)).doesNotThrowAnyException();
    ResponseEntity<Void> deleteRUser = rUserController.deleteRUser(2l);
    Assertions.assertThat(deleteRUser).isNotNull();
    Assertions.assertThat(deleteRUser.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

  }

}