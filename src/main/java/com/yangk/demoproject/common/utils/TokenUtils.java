package com.yangk.demoproject.common.utils;

import com.yangk.demoproject.common.constant.Constant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangk
 * @date 2020/11/29
 */
@Slf4j
@Component
public class TokenUtils {

    @Value("${app.constant.token.key}")
    private String key;

    /*
     * 生成有期限的token
     *
     * token有效期 ===> Constant.TOKEN_EXPIRATION_MINUTES
     *
     * @param params token含义
     * @return java.lang.String
     * @author yangk
     * @date 2020/11/29
     */
    public String createToken(final Map<String, Object> params) {
        Calendar notTime = Calendar.getInstance();
        //token有效期/分钟
        notTime.add(Calendar.MINUTE, Constant.TOKEN_EXPIRATION_MINUTES);
        //失效日期
        Date expireDate = notTime.getTime();

        //设置header
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", SignatureAlgorithm.HS512.getJcaName());
        headerClaims.put("typ", "JWT");

        //自定义token含义
        Claims claims = new DefaultClaims(params);
        JwtBuilder jwtBuilder = Jwts.builder().setHeader(headerClaims).setClaims(claims)
                .setExpiration(expireDate).signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary(this.key));
        String token = jwtBuilder.compact();

        return token;
    }

    /*
     * 解析token,过期依然返回token含义
     *
     * @param token
     * @return io.jsonwebtoken.Claims
     * @author yangk
     * @date 2020/11/29
     */
    public Claims parseToken(final String token){
        log.debug("token : " + token);
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(this.key).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.warn("token [{}] 已过期", token);
            //过期后依然返回claims
            claims = e.getClaims();
        } catch (Exception e) {
            log.error("token [{}] parseToken 异常", token);
        }
        return claims;
    }

    /*
     * 通过自定义key，生成长期的token
     *
     * @param params token含义
	 * @param key 自定义key
     * @return java.lang.String
     * @author yangk
     * @date 2020/11/29
     */
    public String createNotExpiredToken(final Map<String, Object> params, final String key) {
        //设置header
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", SignatureAlgorithm.HS512.getJcaName());
        headerClaims.put("typ", "JWT");

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
        //自定义token含义
        Claims claims = new DefaultClaims(params);
        JwtBuilder jwtBuilder = Jwts.builder().setHeader(headerClaims).setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKeySpec);
        String token = jwtBuilder.compact();

        return token;
    }

    /*
     * 解析token
     *
     * @param token
     * @param key 自定义key
     * @return io.jsonwebtoken.Claims
     * @author yangk
     * @date 2020/11/29
     */
    public Claims parseNotExpiredToken(final String token, final String key) {
        log.debug("token : [{}]", token + " and key [{}]", key);
        Claims claims = null;
        try {
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
            SecretKeySpec secretKeySpec = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
            claims = Jwts.parser().setSigningKey(secretKeySpec).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("token [{}] parseNotExpiredToken 异常", token);
        }
        return claims;
    }

    /*
     * 验证token有效期
     *
     * @param token
     * @return boolean
     * @author yangk
     * @date 2020/11/29
     */
    public boolean validateToken(final String token){
        Claims claims = parseToken(token);
        return doValidateToken(claims,token);
    }

    private boolean doValidateToken(final Claims claims, final String token){
        if (claims == null) {
            return false;
        }

        Date expirationDate = claims.getExpiration();

        if(expirationDate.before(new Date())){
            return false;
        }

        return true;
    }
}
