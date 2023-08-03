package com.revisoes.TCCrevisoes.service;

import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import com.revisoes.TCCrevisoes.DTO.SubjectsDto;
import com.revisoes.TCCrevisoes.dominio.Content;
import com.revisoes.TCCrevisoes.dominio.Subjects;
import com.revisoes.TCCrevisoes.repository.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SubjectsService {

    @Autowired
    private SubjectsRepository subjectsRepository;

    @Autowired
    private ContentService contentService;

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

    public Subjects updateSubjectsAddContent(Long id, Long id_content){
      Subjects subjects = findById(id);
      Content content= contentService.findById(id_content);
      if(subjects.getContents().stream().anyMatch(cont -> cont.getId().equals(content.getId()))){
        throw new IllegalArgumentException("Content with ID " + content.getId() + " already exists in the list");
      }
      subjects.getContents().add(content);
      return subjectsRepository.save(subjects);

    }

    public void deleteSubjects(Long id){
      findById(id);
      subjectsRepository.deleteById(id);

    }
    
}