package com.revisoes.TCCrevisoes.service;

import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revisoes.TCCrevisoes.DTO.SubjectsDto;
import com.revisoes.TCCrevisoes.dominio.Subjects;
import com.revisoes.TCCrevisoes.repository.SubjectsRepository;

@Service
public class SubjectsService {

    @Autowired
    private SubjectsRepository subjectsRepository;

    public List<Subjects> findAll(){
      return subjectsRepository.findAll();

    }

    public Subjects  findById(Long id){
      return subjectsRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not exist",id ));

    }

    public Subjects saveSubjects(Subjects subjects){
      return subjectsRepository.save(subjects);
      
      
    }

    public Subjects updateSubjects(Long id, SubjectsDto subjectsDto ){
      Subjects subjects = findById(id);
      subjects.setName(subjectsDto.getName());
      return subjectsRepository.save(subjects);

    }
    public void deleteSubjects(Long id){
      subjectsRepository.deleteById(id);

    }



    
}