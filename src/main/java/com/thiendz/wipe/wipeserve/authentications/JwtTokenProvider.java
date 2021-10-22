package com.thiendz.wipe.wipeserve.authentications;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtTokenProvider {
    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";

    private static final long EXPIRED_TIME = 86_400_000;
    private static final String SECRET = "hello_everyone_my_name_is_thien_i_am_dev_c404f5e8eb6d34e1eea78f91a40a72492eb42597bbba5918438387c2ed3aea57";

    public static String generateToken(UserDetails userDetail) {
        Date datePresent = new Date();
        Date dateExpired = new Date(datePresent.getTime() + EXPIRED_TIME);
        return Jwts.builder()
                .setSubject(userDetail.getUsername())
                .claim("type", HEADER)
                .claim("prefix", PREFIX.trim())
                .setIssuedAt(datePresent)
                .setExpiration(dateExpired)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public static void validateToken(String token) throws
            MalformedJwtException,
            ExpiredJwtException,
            UnsupportedJwtException,
            IllegalArgumentException,
            SignatureException {
        Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
    }

}

