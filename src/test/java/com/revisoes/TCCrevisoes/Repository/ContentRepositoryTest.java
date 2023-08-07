package com.revisoes.TCCrevisoes.Repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.revisoes.TCCrevisoes.dominio.Content;
import com.revisoes.TCCrevisoes.repository.ContentRepository;

@DataJpaTest
public class ContentRepositoryTest {

  @Autowired
  private ContentRepository contentRepository;

  @Test
  @DisplayName("must persist a content in the database")
  public void saveContentTest(){
    //scenario 
    Content content = createContentAdmin();

    //execution 
    Content contentR = contentRepository.save(content);

    //validation
    Assertions.assertThat(contentR).isNotNull();
  }

  @Test
  @DisplayName("must persist a content in the database after updating ")
  public void updateContentTest(){
    //scenario 
    Content content = createContentAdmin();

    //execution 
    Content contentR = contentRepository.save(content);
    contentR.setName("update");
    Content contentUpdate = contentRepository.save(contentR);

    //validation
    Assertions.assertThat(contentUpdate).isNotNull();
    Assertions.assertThat(contentUpdate.getName()).isEqualTo("update");
  }
  
  @Test
  @DisplayName("must delete a content in the database")
  public void deleteContentTest(){
    //scenario 
    Content content = createContentAdmin();

    //execution 
    contentRepository.save(content);
    contentRepository.deleteById(content.getId());
    Optional<Content> contentOptional = contentRepository.findById(content.getId());

    //validation
    Assertions.assertThat(contentOptional).isEmpty();
  }

  @Test
  @DisplayName("must return a content")
  public void findByidContentTest(){
    //scenario 
    Content content = createContentAdmin();

    //execution 
    contentRepository.save(content);
    Optional<Content> contetOptional = contentRepository.findById(content.getId());
    
    //validation
    Assertions.assertThat(contetOptional).isNotNull();
  }

  @Test
  @DisplayName("must return a content")
  public void findByAllContentTest(){
    //scenario 
    Content content = createContentAdmin();

    //execution 
    contentRepository.save(content);
    List<Content> listContents = contentRepository.findAll();
    
    //validation
    Assertions.assertThat(listContents).isNotNull();
    Assertions.assertThat(listContents).isNotEmpty();
  }

    private Content createContentAdmin(){
      return Content.builder()
                  .id(1l)
                  .description("blabla")
                  .name("batata")
                  .build();
    }    
}