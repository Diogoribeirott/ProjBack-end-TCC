package com.revisoes.TCCrevisoes.Util;

import com.revisoes.TCCrevisoes.DTO.RUserDTO;

public class RUserDtoTest {

  public static RUserDTO createUserDTO(){
    return RUserDTO.builder()
                  .password("123456654")
                  .build();
  }
    
}