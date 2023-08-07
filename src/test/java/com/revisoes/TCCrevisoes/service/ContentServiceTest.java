package com.revisoes.TCCrevisoes.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.List;
import java.util.Optional;

import org.mockito.Mock;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revisoes.TCCrevisoes.DTO.ContentDto;
import com.revisoes.TCCrevisoes.dominio.Review;
import com.revisoes.TCCrevisoes.dominio.Content;
import com.revisoes.TCCrevisoes.repository.ContentRepository;
import com.revisoes.TCCrevisoes.Exceptions.ObjectNotFoundException;

@ExtendWith(SpringExtension.class)
public class ContentServiceTest {

  @InjectMocks
  private ContentService contentService;
    
  @Mock
  private ContentRepository contentRepository;

  @Mock
  private ReviewService reviewService;

  @Test
  @DisplayName("should return a Content and save")
  public void saveNewContentTest(){

    //scenario
    Content content = createContent();
    System.out.println(content);
    when(contentRepository.save(content)).thenReturn(content);

    //execution
    Content contentR = contentService.saveContent(content);

    //validation
    Assertions.assertThat(contentR).isNotNull();
    Assertions.assertThat(contentR.getId()).isNotNull();
    Assertions.assertThat(contentR.getName()).isNotNull();
  }

  @Test
  @DisplayName("sould return a content by id")
  public void findIdContentTest(){
    
    //scenario
    Content content = createContent();
    BDDMockito.when(contentRepository.findById(anyLong())).thenReturn(Optional.of(content));

    //execution
    Content contentR = contentService.findById(1l);

    //validation
    Assertions.assertThat(contentR).isNotNull();
    Assertions.assertThat(contentR.getId()).isNotNull();
    Assertions.assertThat(contentR.getName()).isNotNull();
  }

  @Test
  @DisplayName("sould return a error when id invalid")
  public void findIdContentAndReturnErrorTest(){
    
    //scenario
    BDDMockito.when(contentRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("object not found"));

    //execution
    Assertions.assertThatThrownBy(() -> contentService.findById(1L))
    .isInstanceOf(ObjectNotFoundException.class)
    .hasMessage("object not found");
  }

  @Test
  @DisplayName("sould return a list of Content")
  public void findAllContentTest(){
    
    //scenario
    Content content = createContent();
    BDDMockito.when(contentRepository.findAll()).thenReturn(List.of(content));

    //execution
     List<Content> listContet = contentService.findAll();

    //validation
    Assertions.assertThat(listContet).isNotNull();
    Assertions.assertThat(listContet.get(0).getId()).isEqualTo(content.getId());
    Assertions.assertThat(listContet.get(0).getName()).isEqualTo(content.getName());
  }

  @Test
  @DisplayName("should update name and description - content")
  public void updateContentTest() {
      // Scenario
      Content content = createContent();
      ContentDto contentDto = createContentDto();

      when(contentRepository.findById(1l)).thenReturn(Optional.of(content));
      when(contentRepository.save(any(Content.class))).thenReturn(content);

      // Execution
      Content updateContent = contentService.updateContent(1l, contentDto);

      // Validation
      Assertions.assertThat(updateContent).isNotNull();
      Assertions.assertThat(updateContent.getId()).isEqualTo(content.getId());
      Assertions.assertThat(updateContent.getName()).isEqualTo(content.getName());
      Assertions.assertThat(updateContent.getDescription()).isEqualTo(content.getDescription());
    }
  
  @Test
  @DisplayName("trying to update name content with id invalid and return error")
  public void updateContentWithContentIdInvalidTest() {
      // Scenario
      Content content = createContent();
      ContentDto ContentDTO = createContentDto();

      when(contentRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object invalid"));
      when(contentRepository.save(any(Content.class))).thenReturn(content);

      // Execution and Validation
     Assertions.assertThatThrownBy(() -> contentService.updateContent(1l, ContentDTO))
      .isInstanceOf(ObjectNotFoundException.class)
      .hasMessage("Object invalid");
    }

  @Test
  @DisplayName(" update content adding Review with sucessful")
  public void updateContentAddReviewTest() {
      // Scenario
      Content content = createContent();

      when(contentRepository.findById(anyLong())).thenReturn(Optional.of(content));
      when(reviewService.findById(anyLong())).thenReturn(new Review());
      when(contentRepository.save(any(Content.class))).thenReturn(content);

      // Execution
      Content contentR = contentService.updateContentAddReview(1l,1l);

      //Validation
      Assertions.assertThat(contentR).isNotNull();
      Assertions.assertThat(contentR).isEqualTo(content);
    }

  @Test
  @DisplayName("trying to update content invalid or adding Review invalid and return error")
  public void updateContentAddReviewWhenObjectInvalidTest() {
      // Scenario
      Content content = createContent();

      when(contentRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("object not found"));
      when(reviewService.findById(anyLong())).thenReturn(new Review());
      when(contentRepository.save(any(Content.class))).thenReturn(content);

      // Execution and Validation
      Assertions.assertThatThrownBy(() ->contentService.updateContentAddReview(1l,1l)
      ).isInstanceOf(ObjectNotFoundException.class)
      .hasMessage("object not found");
    }
 
  @Test
  @DisplayName("delete content when successful")
  public void deleteByIdWhenSuccessful() {
      // Scenario
      when(contentRepository.findById(1l)).thenReturn(Optional.of(new Content()));
      BDDMockito.doNothing().when(contentRepository).deleteById(1l);

      // Execution
      contentService.deleteContent(1l);

      // Validation
      verify(contentRepository).findById(1l);
      verify(contentRepository).deleteById(1l);
    }

  @Test
  @DisplayName("trying to delete content when not exist")
  public void deleteByIdWhenUnsuccessful() {
      // Scenario
      when(contentRepository.findById(1l)).thenThrow(new ObjectNotFoundException("Object not fould"));
      BDDMockito.doNothing().when(contentRepository).deleteById(1l);

      // Execution and Validation
      Assertions.assertThatThrownBy(()->contentService.deleteContent(1l))
                                      .isInstanceOf(ObjectNotFoundException.class)
                                      .hasMessage("Object not fould");
    }

  public ContentDto createContentDto(){
    return ContentDto.builder()
                      .name("batata")
                      .description("blabla")
                      .build();
  }

  public Content createContent(){
   return Content.builder()
            .id(1l)
            .name("batata")
            .description("blabla")
            .build();
  }
    
}