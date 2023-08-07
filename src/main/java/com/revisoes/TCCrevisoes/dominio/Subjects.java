package com.revisoes.TCCrevisoes.dominio;

  import lombok.Data;
  import lombok.Builder;
  import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;
  import lombok.AllArgsConstructor;
  import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JoinColumn(name = "subjects_id")
    private List<Content> contents = new ArrayList<>();

  }