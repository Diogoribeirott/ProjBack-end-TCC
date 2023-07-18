package com.revisoes.TCCrevisoes.service;

import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revisoes.TCCrevisoes.DTO.ContentDto;
import com.revisoes.TCCrevisoes.dominio.Content;
import com.revisoes.TCCrevisoes.repository.ContentRepository;

@Service
public class ContentService {
  @Autowired
  private ContentRepository contentRepository;


  public List<Content> findAll(){
    return contentRepository.findAll();

  }

  public Content findById(Long id){
    return contentRepository.findById(id)
    .orElseThrow(() -> new ObjectNotFoundException("Object not found", id));

  }

  public Content saveContent(Content content){
    return contentRepository.save(content);

  }

  public Content updateContent(Long id, ContentDto contentDto){
    Content content = findById(id);
    content.setDescription(contentDto.getDescription());
    content.setName(contentDto.getName());
    return contentRepository.save(content);

  }

  public void deleteContent(Long id){
    findById(id);
    contentRepository.deleteById(id);

  }
   

    
}