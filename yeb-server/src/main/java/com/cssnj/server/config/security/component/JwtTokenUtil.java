package com.cssnj.server.config.security.component;

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

    //荷载用户名key
    private static final String CLAIM_KEY_USERNAME = "sub";
    //JWT创建时间
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 1、根据用户信息生成token
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
     * 2、从token中获取用户名
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
     * 3、验证Token是否有效
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
     * 4、判断token是否可以被刷新（过期可以刷新，未过期则不能刷新）
     * @param token
     * @return true:已过期，可以刷新 false:未过期，不可以刷新
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 5、刷新Token(修改创建时间，重新生成)
     * @param token
     * @return
     */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断Token中有效时间是否过期
     * @param token
     * @return true:未过期 false:已过期
     */
    private boolean isTokenExpired(String token) {
        //先获取荷载，从荷载中获取失效时间
        Claims claims = getClaimsFromToken(token);
        Date expiredDate = claims.getExpiration();
        return expiredDate.before(new Date());
    }

    /**
     * 根据荷载生成JWT TOKEN
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+expiration*1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
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
