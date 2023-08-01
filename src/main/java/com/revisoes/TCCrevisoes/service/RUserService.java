  package com.revisoes.TCCrevisoes.service;

  import java.util.List;
  import org.hibernate.ObjectNotFoundException;
  import com.revisoes.TCCrevisoes.DTO.RUserDTO;
  import org.springframework.stereotype.Service;
  import com.revisoes.TCCrevisoes.dominio.RUser;
  import com.revisoes.TCCrevisoes.repository.RUserRepository;
  import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

  @Service
  public class RUserService implements UserDetailsService {

    @Autowired
    private RUserRepository rUserRepository;

    public List<RUser> findAll(){
      return rUserRepository.findAll();

    }

    public RUser findById(Long id){
      return rUserRepository.findById(id)
      .orElseThrow(() -> new ObjectNotFoundException("Object not found", id));

    }

    public RUser saveRUser(RUser rUser){

      String encryptedPassword = new BCryptPasswordEncoder().encode(rUser.getPassword());
      rUser.setPassword(encryptedPassword);
      return rUserRepository.save(rUser);

    }

    public RUser updateRUser(Long id, RUserDTO rUserDTO){
      RUser rUser = findById(id);
      rUser.setPassword(rUserDTO.getPassword());
      return rUserRepository.save(rUser);

    }

    public void deleteRUser(Long id){
      findById(id);
      rUserRepository.deleteById(id);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     return rUserRepository.findByLogin(username);
     
    }
    
  }