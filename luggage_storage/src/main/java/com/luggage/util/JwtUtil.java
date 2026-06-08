package com.luggage.util;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private static String SECRET;
    private static long EXPIRATION_TIME;
    //todo 密钥暴露需要后期利用算法动态生成通过查询数据库再生成token
    @Value("${jwt.secret:luggage_storage_system_secret_key_2026_secure}")
    public void setSecret(String secret) {
        SECRET = secret;
    }

    @Value("${jwt.expiration:604800000}")
    public void setExpiration(long expiration) {
        EXPIRATION_TIME = expiration;
    }

    /**
     * 用户id
     * 用户role
     * 用户名 username
     * @return
     */
    public static String generateToken(Long id,String role,String username){
        Date now = new Date();
        Date expireData = new Date(now.getTime()+EXPIRATION_TIME);

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setSubject(String.valueOf(id))
                .claim("用户角色",role)
                .claim("用户名",username)
                .setIssuedAt(now)
                .setExpiration(expireData)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;

    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public static Claims parseToken(String token){
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
            
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims;

        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    /**
     * 获取id
     * @param token
     * @return
     */
    public static Long getIdFromToken(String token){
        Claims claims = parseToken(token);
        return claims !=null ? Long.parseLong(claims.getSubject()) : null;
    }

    /**
     * 获取用户名
     * @param token
     * @return
     */
    public static String getUsernameFromToken(String token){
        Claims claims = parseToken(token);
        return claims !=null ? claims.get("用户名").toString() : null;
    }

    /**
     * 获取用户角色
     * @param token
     * @return
     */
    public static String getRoleFromToken(String token){
        Claims claims = parseToken(token);
        return claims !=null ? claims.get("用户角色").toString():null;
    }
}
