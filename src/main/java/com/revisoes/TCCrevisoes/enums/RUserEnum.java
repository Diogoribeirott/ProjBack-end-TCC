package com.revisoes.TCCrevisoes.enums;

public enum RUserEnum {
  ADMIN("admin"),
  USER("user");

  private String role;
    
  RUserEnum(String role){
    this.role= role;
  }

  public String getRole(){
    return role;
  }
}