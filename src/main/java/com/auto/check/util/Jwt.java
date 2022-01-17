package com.auto.check.util;

import com.auto.check.enums.ErrorMessage;
import com.auto.check.domain.user.UserType;
import com.auto.check.exception.NonCriticalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Jwt {

    @Value("${spring.jwt.secret}")
    private String key;

    public String generateToken(Long id, UserType userType){

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg","HS256");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("id", id );
        payloads.put("aud", userType.name());

        // 토큰 유효기간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.YEAR, 1);

        return Jwts.builder().setHeader(headers).setClaims(payloads).setExpiration(calendar.getTime()).signWith(SignatureAlgorithm.HS256, key.getBytes()).compact();
    }

    public boolean isValid(String token){

        if(token == null) throw new NonCriticalException(ErrorMessage.JWT_NOT_EXIST);
        if(!token.startsWith("Bearer ")) throw new NonCriticalException(ErrorMessage.JWT_NOT_START_BEARER);

        Claims claims = this.parseClaimsFromJwt(token);

        if(claims.get("id") == null || claims.get("exp") == null || claims.get("aud") == null){
            throw new NonCriticalException(ErrorMessage.TOKEN_INVALID_EXCEPTION);
        }

        return true;
    }

    public Claims getClaimsFromJwt(String token){
        isValid(token);
        return parseClaimsFromJwt(token);
    }

    private Claims parseClaimsFromJwt(String token){
        //System.out.println(token);
        Claims claims = null;
        token = token.substring(7);

        try{
            claims = Jwts.parser()
                    .setSigningKey(key.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

        } catch(ExpiredJwtException e){
            throw new NonCriticalException(ErrorMessage.TOKEN_EXPIRED_EXCEPTION);

        } catch(Exception e){
            throw new NonCriticalException(ErrorMessage.TOKEN_INVALID_EXCEPTION);
        }

        return claims;
    }
}