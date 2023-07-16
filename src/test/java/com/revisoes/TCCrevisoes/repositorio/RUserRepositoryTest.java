package com.revisoes.TCCrevisoes.repositorio;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import com.revisoes.TCCrevisoes.dominio.RUser;
import jakarta.validation.ConstraintViolationException;
import com.revisoes.TCCrevisoes.repository.RUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("Test RUserRepository")
public class RUserRepositoryTest {
  
  @Autowired
  private RUserRepository rUserRepository;

  @Test
  @DisplayName("Test  save RUser when sucessful ")
  void save_RUser_when_sucessful(){
    RUser rUser = createRUser();
    RUser rUserSave = rUserRepository.save(rUser);
    Assertions.assertThat(rUserSave).isNotNull();
    Assertions.assertThat(rUserSave).isEqualTo(rUser);
    Assertions.assertThat(rUserSave.getId()).isNotNull();
    Assertions.assertThat(rUserSave.getName()).isNotEmpty();
    Assertions.assertThat(rUserSave.getEmail()).isNotEmpty();
    Assertions.assertThat(rUserSave.getLogin()).isNotEmpty();
    Assertions.assertThat(rUserSave.getPassword()).isNotEmpty();
    Assertions.assertThat(rUserSave.getDateOfBirth()).isNotNull();

  }
   @Test
  @DisplayName("Test rUser with name invalid Or Empty ")
  void rUser_with_name_invalid_Or_Empty(){
    RUser rUser = createRUserWithNameInvalid();
    rUser.getName().isEmpty();
    Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> rUserRepository.save(rUser)).withMessageContaining("Name cannot be empty");
    
  }


  @Test
  @DisplayName("Test save update RUser when sucessful")
  void Save_Update_RUser_when_sucessful(){
    RUser RUser = createRUser();
    RUser saveRUser = rUserRepository.save(RUser);
    saveRUser.setPassword("123456789");
    RUser saveUpadateRUser = rUserRepository.save(saveRUser);
    Assertions.assertThat(saveUpadateRUser.getId()).isEqualTo(saveRUser.getId());
    Assertions.assertThat(saveUpadateRUser.getPassword()).isNotEqualTo(saveRUser);
    Assertions.assertThat(saveUpadateRUser.getName()).isEqualTo(saveRUser.getName());
    
  }

  @Test
  @DisplayName("Test delete RUser when sucessful")
  void delete_RUser_when_sucessful(){
    RUser RUser = createRUser();
    RUser saveRUser = rUserRepository.save(RUser);
    rUserRepository.delete(saveRUser);
    Optional<RUser> rUserOptional = rUserRepository.findById(saveRUser.getId());
    Assertions.assertThat(rUserOptional.isEmpty()).isTrue();

  }

  @Test
  @DisplayName("Test find By Id  RUser when sucessful")
  void find_By_id_RUser_when_sucessful(){
    RUser RUser = createRUser();
    RUser saveRUser = rUserRepository.save(RUser);
    Optional<RUser> rUserOptional = rUserRepository.findById(saveRUser.getId());
    Assertions.assertThat(rUserOptional.get()).isNotNull();
    Assertions.assertThat(rUserOptional.get().getId()).isEqualTo(RUser.getId());

  }

  @Test
  @DisplayName("Test find By Id and not found none")
  void find_By_id_RUser_when_not_found_none(){
    Optional<RUser> rUserOptional = rUserRepository.findById(1l);
    Assertions.assertThat(rUserOptional.isEmpty());
  }

  @Test
  @DisplayName("Test find By all  RUser when sucessful")
  void find_By_all_RUser_when_sucessful(){
    RUser RUser = createRUser();
    rUserRepository.save(RUser);
    List<RUser> AllRUser = rUserRepository.findAll();
    Assertions.assertThat(AllRUser).isNotEmpty();
    Assertions.assertThat(AllRUser.get(0)).isNotNull();
    Assertions.assertThat(AllRUser.get(0).getId()).isNotNull();
    Assertions.assertThat(AllRUser.get(0)).isEqualTo(RUser);

  }

  @Test
  @DisplayName("Test find by all and return list empty")
  void find_By_all_RUser_when_not_found_RUser(){
    List<RUser> AllRUser = rUserRepository.findAll();
    Assertions.assertThat(AllRUser).isEmpty();
    
  }

  private RUser createRUser(){
    return RUser.builder()
                .name("João")
                .dateOfBirth(LocalDate.parse("2000-02-17"))
                .email("João@gmail.com")
                .login("joao123")
                .password("joao123456")
                .build();
  }

    private RUser createRUserWithNameInvalid(){
    return RUser.builder()
                .name("")
                .dateOfBirth(LocalDate.parse("2000-02-17"))
                .email("João@gmail.com")
                .login("joao123")
                .password("joao123456")
                .build();
  }
  
}