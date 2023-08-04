package com.revisoes.TCCrevisoes.controllers;

import java.util.List;
import org.mockito.Mock;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.revisoes.TCCrevisoes.DTO.ContentDto;
import com.revisoes.TCCrevisoes.dominio.Content;
import org.junit.jupiter.api.extension.ExtendWith;
import com.revisoes.TCCrevisoes.service.ContentService;
import com.revisoes.TCCrevisoes.controller.ContentController;
import com.revisoes.TCCrevisoes.Exceptions.ObjectNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class ContentControllerTest {

  @InjectMocks
  private ContentController contentController;

  @Mock
  private ContentService contentService;

  @Test
  @DisplayName("save new content when sucessfull")
  void saveNewSubjectWithSucessfulAndReturnStatusCreatedTest(){

    //scenario
    Content content = createContent();
    BDDMockito.when(contentService.saveContent(any(Content.class))).thenReturn(content);

    //execution
     ResponseEntity<Content> responseEntityContent = contentController.saveContent(content);

    //validation
    Content contentR = responseEntityContent.getBody();
    Assertions.assertThat(responseEntityContent.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    Assertions.assertThat(contentR).isNotNull();
    Assertions.assertThat(contentR).isEqualTo(content);
    Assertions.assertThat(contentR.getId()).isNotNull();
  }

  @Test
  @DisplayName("trying to save new empty content and return error")
  void tryingToSaveNewEmptyContentAndReturnStatusCreatedWithErrorTest(){

    //scenario
    BDDMockito.when(contentService.saveContent(any())).thenThrow(new IllegalArgumentException("error in object declaration"));

    //execution and validation
    Assertions.assertThatThrownBy(()-> contentService.saveContent(new Content()))
                                .isInstanceOf(IllegalArgumentException.class)
                                .hasMessage("error in object declaration");
  }

  @Test
  @DisplayName("should return a list of content")
  public void findAllContentTest(){

    //scenario
    Content content = createContent();
    List<Content> listContent = List.of(content);

    BDDMockito.when(contentService.findAll()).thenReturn(listContent);

    //execution 
    ResponseEntity<List<Content>> responseEntityListContent = contentController.findAll();

    //validation
    List<Content> listWithContent = responseEntityListContent.getBody();
    Assertions.assertThat(responseEntityListContent.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(listWithContent.get(0)).isNotNull();
    Assertions.assertThat(listWithContent.get(0)).isEqualTo(content);
    }

  @Test
  @DisplayName("should return a  content")
  public void findByIdContentTest(){

    //scenario
    Content content = createContent();
    BDDMockito.when(contentService.findById(anyLong())).thenReturn(content);

    //execution 
    ResponseEntity<Content> responseEntityContent = contentController.findById(1l);

    //validation
    Content contentR = responseEntityContent.getBody();
    Assertions.assertThat(responseEntityContent.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(contentR).isNotNull();
    Assertions.assertThat(contentR).isEqualTo(content);
    }

  @Test
  @DisplayName("should return ObjectNotFoundException when it doesn't exist content")
  public void findByIdWhenContentDoesNotExistTest(){

    //scenario
    BDDMockito.when(contentService.findById(100L)).thenThrow(new ObjectNotFoundException("Object not found"));

    //execution
    ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> contentController.findById(100L));

    //validation
    Assertions.assertThat(assertThrows.getMessage()).isEqualTo("Object not found");
    }

  @Test
  @DisplayName("should delete a content")
  public void deleteByIdContentWhenSucessful(){

      //scenario
      BDDMockito.doNothing().when(contentService).deleteContent(anyLong());

      //execution
      ResponseEntity<Void> responseEntityVoid = contentController.deleteContent(1l);

      //validation
      Assertions.assertThat(responseEntityVoid).isNotNull();
      Assertions.assertThat(responseEntityVoid.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

  @Test
  @DisplayName("trying to delete a Content that doesn't exist and return Object not found exception")
  public void deleteByIdContentWhenNofound(){

      //scenario
     when(contentController.deleteContent(anyLong())).thenThrow(new ObjectNotFoundException("object not found"));

      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> contentController.deleteContent(1l));

      //validation
      Assertions.assertThat(assertThrows.getMessage()).isEqualTo("object not found");
    }

  @Test
  @DisplayName("must update content name")
  public void updateContentNameAndDescriptinWhenSucessfull(){
      
      //scenario
      Content content = createContent();
      ContentDto ContentDto = createContentDto();
      BDDMockito.when(contentService.updateContent(anyLong(),any(ContentDto.class))).thenReturn(content);

      //execution
      ResponseEntity<Content> responseEntityContent = contentController.updateContent(1l,ContentDto);

      //validation
      Content contentR = responseEntityContent.getBody();
      Assertions.assertThat(responseEntityContent.getStatusCode()).isEqualTo(HttpStatus.OK);
      Assertions.assertThat(contentR).isNotNull();
      Assertions.assertThat(contentR.getDescription()).isEqualTo(ContentDto.getDescription());
    }

  @Test
  @DisplayName("trying to update a content that does not exist and returning Object not found exception")
  public void updateByIdContentWhenNofound(){

      //scenario
     BDDMockito.when(contentController.updateContent(1L ,new ContentDto())).thenThrow(new ObjectNotFoundException("object not found"));

      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> contentController.updateContent(1l,new ContentDto()));

      //validation
      Assertions.assertThat(assertThrows.getMessage()).isEqualTo("object not found");
    }

  @Test
  @DisplayName("adding Review in content")
  public void updateRUserAddingReviewInContent(){

      //scenario
      Content content = createContent();
      BDDMockito.when(contentService.updateContentAddReview(anyLong(),anyLong())).thenReturn(content);

      //execution
      ResponseEntity<Content> responseEntityContentAddReview = contentController.updateContentAddReview(1l, 1l);

      //validation
      Content contentR = responseEntityContentAddReview.getBody();
      Assertions.assertThat(responseEntityContentAddReview.getStatusCode()).isEqualTo(HttpStatus.OK);
      Assertions.assertThat(contentR).isNotNull();
      Assertions.assertThat(contentR).isEqualTo(content);
    }

  @Test
  @DisplayName("trying to update a rUser password that does not exist and returning Object not found exception")
  public void updateContentAddingReviewButReturnsErrorBecauseOneOrBothAreInvalid(){

      //scenario
     BDDMockito.when(contentController.updateContentAddReview(1L ,1l)).thenThrow(new ObjectNotFoundException("object not found"));

      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> contentController.updateContentAddReview(1l,1l));

      //validation
      Assertions.assertThat(assertThrows.getMessage()).isEqualTo("object not found");
    }
 
  private ContentDto createContentDto(){
    return ContentDto.builder()
                      .name("batata")
                      .description("batatabatata")
                      .build();
    }

  private Content createContent(){
    return Content.builder()
                  .id(1l)
                  .name("batata")
                  .description("batatabatata")
                  .build();
    }

}