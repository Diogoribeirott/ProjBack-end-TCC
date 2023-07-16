package com.revisoes.TCCrevisoes.Service;

import java.util.List;
import org.mockito.Mock;
import java.util.Optional;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import com.revisoes.TCCrevisoes.dominio.RUser;
import com.revisoes.TCCrevisoes.Util.RUserTest;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyLong;
import com.revisoes.TCCrevisoes.service.RUserService;
import com.revisoes.TCCrevisoes.repository.RUserRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class RUserServiceTest {


  @InjectMocks
  private RUserService rUserService;

  @Mock
  private RUserRepository rUserRepository;

  @BeforeEach
  void setUp(){
    BDDMockito.when(rUserRepository.findAll()).thenReturn(List.of(RUserTest.createRUser()));
    BDDMockito.when(rUserRepository.findById(anyLong())).thenReturn(Optional.of(RUserTest.createRUser()));
    BDDMockito.when(rUserRepository.save(any())).thenReturn(RUserTest.createRUser());
    BDDMockito.doNothing().when(rUserRepository).deleteById(1l);

  }

  @Test
  @DisplayName("test find by All and return one list of RUser sucessful")
   void FindByALL_when_sucessful(){
     List<RUser> listRUser = rUserService.findAll();
    Assertions.assertThat(listRUser)
                                    .isNotEmpty()
                                    .isNotNull();
    Assertions.assertThat(listRUser.get(0).getId()).isNotNull();
    Assertions.assertThat(listRUser.get(0)).isEqualTo(RUserTest.createRUser());

  }

  @Test
  @DisplayName("test find by id and return one RUser sucessful")
  public void FindByID_when_sucessful(){
    RUser rUser = rUserService.findById(5l);
    Assertions.assertThat(rUser).isNotNull();
    Assertions.assertThat(rUser.getId()).isNotNull();
    Assertions.assertThat(rUser).isEqualTo(RUserTest.createRUser());

  }

  @Test
  @DisplayName("test update when sucessful")
  void update_when_sucessful(){
    RUser rUser = rUserService.saveRUser(null);
    Assertions.assertThat(rUser).isNotNull();
    Assertions.assertThat(rUser.getId()).isNotNull();
    Assertions.assertThat(rUser).isEqualTo(RUserTest.createRUser());

  }

  @Test
  @DisplayName("test delete when sucessful")
  void delete_when_sucessful(){ 
    Assertions.assertThatCode(() -> rUserService.deleteRUser(2l)).doesNotThrowAnyException();

  }
    
}