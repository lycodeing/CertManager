package cn.lycodeing.cert.web.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String issuer = "lycodeing";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-time}")
    private Long expirationTime;

    public String createJwt(Integer userId) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(String.valueOf(userId))
                // 设置过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime * 1000L))
                .sign(algorithm);
    }

    public Integer getUserId(String token) {
        return Integer.parseInt(JWT.decode(token).getSubject());
    }
}
