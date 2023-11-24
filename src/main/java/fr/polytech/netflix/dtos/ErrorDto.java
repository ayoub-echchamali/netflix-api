package fr.polytech.netflix.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorDto {
    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;
}
