package com.example.library.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import com.example.library.etc.ServiceException;
import com.example.library.pojo.entity.User;
import com.example.library.pojo.model.UserJwtInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author WY
 */
@Component
public class JwtTokenUtil {
    /**
     * 加密盐
     */
    @Value("${common.jwt.salt.secret}")
    private String secret;

    /**
     * 过期时间 秒
     */
    @Value("${common.jwt.valid.seconds}")
    private Integer expirationSeconds;

    /**
     * 生成用户令牌
     * @param user
     * @return
     */
    public String generateUserToken(User user) {
        Map<String, Object> claims = new HashMap<>(4);
        claims.put(JWT.JWT_ID, user.getId());
        claims.put(JWT.SUBJECT, user.getAccount());
        return JWT.create().addPayloads(claims).setKey(secret.getBytes())
                .setExpiresAt(DateUtil.offsetSecond(new Date(), expirationSeconds)).sign();
    }

    /**
     * 验证令牌合法性，返回解密后信息
     * @param token
     * @return
     */
    public UserJwtInfo getUserJwtInfo(String token) {
        try {
            JWT tokenJwt = JWT.of(token).setKey(secret.getBytes());
            //验证令牌合法性
            if (! tokenJwt.verify()) {
                return null;
            }

            JSONObject payLoads = tokenJwt.getPayloads();
            UserJwtInfo info = new UserJwtInfo();
            info.setId(payLoads.get(JWT.JWT_ID, Long.class));
            info.setAccount(payLoads.get(JWT.SUBJECT, String.class));
            return info;
        } catch (Exception e) {
            throw new ServiceException("无效的认证凭证");
        }
    }
}
