package com.revisoes.TCCrevisoes.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.revisoes.TCCrevisoes.dominio.RUser;
import com.revisoes.TCCrevisoes.enums.RUserEnum;
import com.revisoes.TCCrevisoes.repository.RUserRepository;

@DataJpaTest
public class RUserRepositoryTest {

  @Autowired
  private RUserRepository rUserRepository;

  @Test
  @DisplayName("must return userDatails")
  public void findByLoginRUserTest(){
    //scenario 
    RUser rUserAdmin = createRUserAdmin();

    //execution 
    rUserRepository.save(rUserAdmin);
    UserDetails userDetails = rUserRepository.findByLogin(rUserAdmin.getLogin());
    
    //validation
    Assertions.assertThat(userDetails).isNotNull();
    Assertions.assertThat(userDetails.getUsername()).isEqualTo(rUserAdmin.getLogin());
  }

  @Test
  @DisplayName("must persist a User in the database")
  public void saveRUserTest(){
    //scenario 
    RUser rUserAdmin = createRUserAdmin();

    //execution 
    RUser rUser = rUserRepository.save(rUserAdmin);

    //validation
    Assertions.assertThat(rUser).isNotNull();
    Assertions.assertThat(rUser.getLogin()).isEqualTo(rUserAdmin.getLogin());
  }

  @Test
  @DisplayName("must persist a User in the database after updating ")
  public void updateRUserTest(){
    //scenario 
    RUser rUserAdmin = createRUserAdmin();

    //execution 
    RUser rUser = rUserRepository.save(rUserAdmin);
    rUser.setName("update");
    RUser rUserUpdate = rUserRepository.save(rUser);

    //validation
    Assertions.assertThat(rUserUpdate).isNotNull();
    Assertions.assertThat(rUserUpdate.getName()).isEqualTo("update");
  }
  
  @Test
  @DisplayName("must delete a User in the database")
  public void deleteRUserTest(){
    //scenario 
    RUser rUserAdmin = createRUserAdmin();

    //execution 
    rUserRepository.save(rUserAdmin);
    rUserRepository.deleteById(rUserAdmin.getId());
    Optional<RUser> rUserOptional = rUserRepository.findById(rUserAdmin.getId());

    //validation
    Assertions.assertThat(rUserOptional).isEmpty();
  }

  @Test
  @DisplayName("must return a rUser")
  public void findByidRUserTest(){
    //scenario 
    RUser rUserAdmin = createRUserAdmin();

    //execution 
    rUserRepository.save(rUserAdmin);
    Optional<RUser> rUserOptional = rUserRepository.findById(rUserAdmin.getId());
    
    //validation
    Assertions.assertThat(rUserOptional).isNotNull();
  }

  @Test
  @DisplayName("must return a rUser")
  public void findByAllRUserTest(){
    //scenario 
    RUser rUserAdmin = createRUserAdmin();

    //execution 
    rUserRepository.save(rUserAdmin);
    List<RUser> listRUsers = rUserRepository.findAll();
    
    //validation
    Assertions.assertThat(listRUsers).isNotNull();
  }

    private RUser createRUserAdmin(){
      return RUser.builder()
                  .id(1l)
                  .name("admin")
                  .login("batata")
                  .password("123456")
                  .email("admin@gmail.com")
                  .dateOfBirth(LocalDate.of(2023, 8, 2))
                  .role(RUserEnum.ADMIN)
                  .build();
    }
}