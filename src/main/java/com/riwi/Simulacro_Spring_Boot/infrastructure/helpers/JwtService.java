package com.riwi.Simulacro_Spring_Boot.infrastructure.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.riwi.Simulacro_Spring_Boot.domain.entities.UserSecurity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
    
    // 1. Crear Firma o clave
    private final String SECRET_KEY = "RG9uZGUgdmEgY2lyaWxvLCBuZWdybyBjaXJpbG8sIGRvbmRlIHZhIGNpcmlsbyBtb250YWRvIGVuIHN1IGNhaW1hbiwgdmEgcGEnbCBhbWF6b25hcyBhIGJhaWxhciBsYSBiYW1iYQ==";

    // 2. Metodo para encriptar la clave secreta
    public SecretKey getKey() {
        
        // Convertir la llave a bytes
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        // Retornamos la llave cifrada cifrada
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 3. Construir el JWT
    public String getToken(Map<String, Object> claims, UserSecurity user) {

        return Jwts.builder()
                    .claims(claims) // Agregamos el cuerpo del JWT
                    .subject(user.getUserName()) // Agregamos de quien es el JWT
                    .issuedAt(new Date(System.currentTimeMillis())) // Fecha de creacion
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Fecha de expiracion
                    .signWith(this.getKey()) // Firmar el token
                    .compact(); // compact() es como un build pero de Jwts
    }

    // 4. Metodo para obtener el JWT
    public String getToken(UserSecurity user) {

        // Crear el map de claims
        Map<String, Object> claims = new HashMap<>();  

        claims.put("id", user.getId());
        claims.put("role", user.getRole().name());

        return getToken(claims, user);
    }
}
