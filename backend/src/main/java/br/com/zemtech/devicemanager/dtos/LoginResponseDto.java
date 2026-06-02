package br.com.zemtech.devicemanager.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de login
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    @JsonProperty("success")
    private Boolean success;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("user")
    private UserDto user;

}
