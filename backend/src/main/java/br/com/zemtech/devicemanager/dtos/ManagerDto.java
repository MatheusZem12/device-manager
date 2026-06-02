package br.com.zemtech.devicemanager.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {

    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("managerUserId")
    private Long managerUserId;
    
    @JsonProperty("supervisedUserId")
    private Long supervisedUserId;
    
    @JsonProperty("managerUsername")
    private String managerUsername;
    
    @JsonProperty("supervisedUsername")
    private String supervisedUsername;

}
