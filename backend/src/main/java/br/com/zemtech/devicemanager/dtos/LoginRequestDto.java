package br.com.zemtech.devicemanager.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisição de login
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @JsonProperty("email")
    private String email;
    
    @JsonProperty("password")
    private String password;

}
