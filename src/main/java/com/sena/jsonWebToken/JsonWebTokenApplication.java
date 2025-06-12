package com.sena.jsonWebToken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JsonWebTokenApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonWebTokenApplication.class, args);

        // // Generar una nueva clave HMAC-SHA256 segura:
        //Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        //byte[] keyBytes = key.getEncoded();
        //System.out.println("Generated key: " + Base64.getEncoder().encodeToString(keyBytes));
    }

}