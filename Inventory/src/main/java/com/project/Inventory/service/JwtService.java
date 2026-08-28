package com.project.Inventory.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;

    public JwtService(@Value("${jwt.secret}") String secret) {

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )
                .signWith(secretKey)
                .compact();
    }
}




//package com.project.Inventory.service;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//@Service
//public class JwtService {
//
//    private final SecretKey secretKey;
//
//    public JwtService(
//            @Value("${jwt.secret}") String secret) {
//
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//
//        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public String generateToken(String username) {
//
//        return Jwts.builder()
//                .subject(username)
//                .issuedAt(new Date())
//                .expiration(
//                        new Date(
//                                System.currentTimeMillis()
//                                        + 1000 * 60 * 60
//                        )
//                )
//                .signWith(secretKey)
//                .compact();
//    }
//}