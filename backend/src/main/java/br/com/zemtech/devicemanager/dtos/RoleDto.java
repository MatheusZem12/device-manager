package br.com.zemtech.devicemanager.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("name")
    private String name;

}
