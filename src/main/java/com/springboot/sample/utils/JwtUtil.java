package com.springboot.sample.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java web token 工具类: 引入com.auth0.java-jwt来实现JWT
 * 
 * @author huang.yj
 * @date 2019.06.03 
 */
public class JwtUtil {
	/**
     * 过期时间   15分钟（毫秒为单位）
     */
    private static final long EXPIRE_TIME = 15 * 60 * 1000;
    
    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "f26e587c28064d0e855e72c0a6a0e618";
    
    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
        	// 构建密钥信息：私钥及HS256加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            // 通过密钥信息等等其他信息（如：签名的发布者的信息）生成JWTVerifier (JWT验证类)
            JWTVerifier verifier = JWT.require(algorithm)
                    // .withIssuer("SERVICE")  // 不添加.withIssuer("SERVICE") 也是可以获取 JWTVerifier
                    .build();

            // 通过JWTVerifier的verify获取token中的信息。
            DecodedJWT jwt = verifier.verify(token);

            // 如下面代码所示就可以获取到我们之前生成token时设置的信息
            String subject = jwt.getSubject();
            System.out.println("subject=" + subject);

            List<String> audience = jwt.getAudience();
            System.out.println("audience=" + JSON.toJSONString(audience));

            Map<String, Claim> claims = jwt.getClaims();
            for (Map.Entry<String, Claim> entry : claims.entrySet()) {
                String key = entry.getKey();
                Claim claim = entry.getValue();
                System.out.println("key:"+key+" value:"+claim.asString());
            }

            String header = jwt.getHeader();
            System.out.println("header=" + header);

            String payload = jwt.getPayload();
            System.out.println("payload=" + payload);

            String signature = jwt.getSignature();
            System.out.println("signature=" + signature);

            String jwtToken = jwt.getToken();
            System.out.println("jwt token="  + jwtToken );

            return true;
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 生成签名,15分钟后过期
     *
     * @param username 用户名
     * @return 加密的token
     */
    public static String sign(String username,String userId) {
        	// 过期时间，当前时间加15分钟
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

            // 构建密钥信息：私钥及HS256加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            // 构建头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");

            // 携带自定义信息，如username，userId信息，生成签名
            return JWT.create()
                    .withHeader(header) // 设置头部信息 Header
                    //.withIssuer("SERVICE")  // 设置签名是有谁生成，例如：服务器
                    //.withSubject("this is test token")  // 设置签名的主题
                    // .withNotBefore(new Date())  // 设置定义在什么时间之前，该jwt都是不可用的.
                    //.withAudience("APP") // 设置签名的观众，也可以理解谁接受签名的
                    .withIssuedAt(new Date()) // 设置生成签名的时间
                    .withExpiresAt(date) // 设置签名过期的时间
                    .withClaim("loginName", username)  // 设置自定义载荷信息
                    .withClaim("userId",userId)     // 设置自定义载荷信息
                    .sign(algorithm);  // 签名生成token
    }
}
