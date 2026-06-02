package br.com.zemtech.devicemanager.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDto {

    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("userId")
    private Long userId;
    
    @JsonProperty("roleId")
    private Long roleId;
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("roleName")
    private String roleName;

}
