package net.unir.grupo_12.buscador.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String path;
    private String message;
    private String error;
    private int statusCode;
    private LocalDateTime timestamp;
}
