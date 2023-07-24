package com.revisoes.TCCrevisoes.DTO;

import lombok.Data;

@Data
public class AuthenticationDto {
    private String login;
    private String password;

    public AuthenticationDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
