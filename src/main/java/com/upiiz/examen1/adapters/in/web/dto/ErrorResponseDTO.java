package com.upiiz.examen1.adapters.in.web.dto;

public class ErrorResponseDTO {
    private String mensaje;

    public ErrorResponseDTO(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}