package com.revisoes.TCCrevisoes.dominio;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Builder;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
  private List<Review> revisions;

  @NotEmpty(message = "Name of content cannot be empty")
  private String name;
  

  @JsonIgnore
  private String description;

  @JsonIgnore
  private LocalDate starDate;
    
}