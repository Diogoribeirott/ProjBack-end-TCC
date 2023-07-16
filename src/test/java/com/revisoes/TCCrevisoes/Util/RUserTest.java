package com.revisoes.TCCrevisoes.Util;

import java.time.LocalDate;

import com.revisoes.TCCrevisoes.dominio.RUser;

public class RUserTest {

  public static RUser createRUser(){
    return RUser.builder()
                .id(1l)
                .name("João")
                .dateOfBirth(LocalDate.parse("2000-02-17"))
                .email("João@gmail.com")
                .login("joao123")
                .password("joao123456")
                .build();
  }
  
    
}