package com.sdlg.apipasticita.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para recibir los datos necesarios en una operación de inicio de sesión.
 * Esta clase se utiliza para transportar la información enviada por el cliente
 * durante el proceso de autenticación.
 * Campos incluidos:
 *  email -> Correo electrónico del usuario que intenta autenticarse.
 *  password -> Contraseña asociada al usuario.
 */
@Getter
@Setter
public class LoginRequest {

    /** Correo electrónico del usuario. */
    private String email;

    /** Contraseña del usuario proporcionada en el intento de acceso. */
    private String password;
}
