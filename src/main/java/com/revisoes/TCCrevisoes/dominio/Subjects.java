  package com.revisoes.TCCrevisoes.dominio;

  import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
  import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.NoArgsConstructor;
import java.util.List;
import jakarta.persistence.Entity;
  import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @ManyToOne
    private RUser rUser;

    @OneToMany
    private List<Content> content;

  }