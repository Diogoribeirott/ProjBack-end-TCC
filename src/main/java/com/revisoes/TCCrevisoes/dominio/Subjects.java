  package com.revisoes.TCCrevisoes.dominio;

  import lombok.Data;
  import lombok.Builder;
  import java.util.List;
  import java.util.ArrayList;
  import jakarta.persistence.Id;
  import lombok.NoArgsConstructor;
  import lombok.AllArgsConstructor;
  import jakarta.persistence.Table;
  import jakarta.persistence.Entity;
  import jakarta.persistence.OneToMany;
  import jakarta.persistence.GenerationType;
  import jakarta.persistence.GeneratedValue;
 import jakarta.validation.constraints.NotEmpty;

  @Data
  @Entity
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Table(name = "Subjects")
  public class Subjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @OneToMany
    private List<Content> content = new ArrayList<>();

  }