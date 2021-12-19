package com.cssnj.server.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  JwtToken工具类
 * @author panbing
 * @date 2021/12/16 15:52
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token){
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return username;
    }

    /**
     * 验证Token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUserNameFromToken(token);
        //1、判断Token是否过期
        //2、判断Token荷载中的用户名和UserDetails中的用户名是否一致
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断Token是否失效
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从Token中获取失效时间
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断token是否可以被刷新（过期可以刷新，未过期则不能刷新）
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 刷新Token
     * @param token
     * @return
     */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 根据荷载生成JWT TOKEN
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 生成Token失效时间
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    /**
     * 从Token中获取荷载
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return claims;
    }




}
