package com.revisoes.TCCrevisoes.service;

import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import com.revisoes.TCCrevisoes.DTO.ContentDto;
import com.revisoes.TCCrevisoes.dominio.Content;
import com.revisoes.TCCrevisoes.dominio.Review;
import com.revisoes.TCCrevisoes.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ContentService {
  
  @Autowired
  private ContentRepository contentRepository;

  @Autowired
  private ReviewService reviewService;

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

  public Content updateContentAddReview(Long id, Long id_review){
    Content content = findById(id);
    Review review = reviewService.findById(id_review);
    if(content.getReviews().stream().anyMatch(rev -> rev.getId().equals(review.getId()))){
      throw new IllegalArgumentException("review with ID " +review.getId() + " already exists in the list");

    }else if ( content.getReviews().stream().anyMatch(rev -> rev.getDayOfReview().equals(review.getDayOfReview())))  {
      throw new IllegalArgumentException("review with ID " +review.getId() + " already exists in the list");
      
    } else {
      content.getReviews().add(review);
      return contentRepository.save(content);
    }

  }

  public void deleteContent(Long id){
    findById(id);
    contentRepository.deleteById(id);

  }
   
    
}