package com.revisoes.TCCrevisoes.dominio;

import lombok.Data;
import lombok.Builder;
import java.util.List;
import java.time.LocalDate;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contents")
public class Content {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotEmpty(message = "Name of content cannot be empty")
  private String name;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate starDate;
  
  @OneToMany
  private List<Review> revisions;
  
  private String description;
  
}