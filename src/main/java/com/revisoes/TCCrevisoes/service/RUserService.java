  package com.revisoes.TCCrevisoes.service;

  import java.util.List;
  import org.hibernate.ObjectNotFoundException;
  import com.revisoes.TCCrevisoes.DTO.RUserDTO;
  import org.springframework.stereotype.Service;
  import com.revisoes.TCCrevisoes.dominio.RUser;
  import com.revisoes.TCCrevisoes.repository.RUserRepository;
  import org.springframework.beans.factory.annotation.Autowired;

  @Service
  public class RUserService {

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
    
  }