package com.auto.check.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Jwt {

    @Value("${spring.jwt.secret}")
    private String key;


    public String generateToken(Long id){

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg","HS256");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("id", id );
        Calendar calendar = Calendar.getInstance(); // singleton object java calendar
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 24); // access token expire 24h later
        Date exp = calendar.getTime();

        return Jwts.builder().setHeader(headers).setClaims(payloads).setExpiration(exp).signWith(SignatureAlgorithm.HS256, key.getBytes()).compact();
    }

    public boolean isValid(String token){

        //if(token == null) throw new Exception(ErrorMessage.JWT_NOT_EXIST);
        //if(!token.startsWith("Bearer ")) throw new Exception(ErrorMessage.JWT_NOT_START_BEARER);

        Claims claims = this.getClaimsFromJwtToken(token);

        String sub = String.valueOf(claims.get("sub"));

        return true;
    }

    public Claims getClaimsFromJwtToken(String token){
        //System.out.println(token);
        Claims claims = null;
        token = token.substring(7);

        try{
            claims = Jwts.parser()
                    .setSigningKey(key.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

        } catch(ExpiredJwtException e){

        } catch(Exception e){

        }

        return claims;
    }
}