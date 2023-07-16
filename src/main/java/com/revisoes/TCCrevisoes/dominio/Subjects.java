  package com.revisoes.TCCrevisoes.dominio;

  import lombok.Data;
  import lombok.Builder;
  import jakarta.persistence.Id;
  import lombok.NoArgsConstructor;
  import lombok.AllArgsConstructor;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
  import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;
  import jakarta.persistence.GeneratedValue;
  import jakarta.validation.constraints.NotEmpty;

  @Data
  @Entity
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public class Subjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @ManyToOne
    private RUser rUser;

    @OneToMany(mappedBy = "subjects",cascade = CascadeType.ALL)
    private List<Content> content;

  }