package ru.os.OnlineShop.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.os.OnlineShop.services.interfaces.JwtServiceInterface;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/*
* @author ZQR0
* The main JWT service for filters and controllers
*/
@Service
@Slf4j
public class JwtService implements JwtServiceInterface {

    @Value(value = "${jwt.secret.key}")
    private String JWT_SECRET;

    private final Date EXPIRATION_TIME = new Date(System.currentTimeMillis() + 1000 * 60 * 24);

    @Override
    public String extractUsername(String token) {
        log.info("extractUsername works");
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        log.info("extractClaims works");
        return claimsResolver.apply(claims);
    }

    @Override
    public String getGeneratedToken(UserDetails userDetails) {
        log.info("getGeneratedToken works");
        return this.generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
        log.info("generateToken works");
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(this.EXPIRATION_TIME)
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        log.info("isTokenValid works");
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        log.info("isTokenExpired works");
        return this.extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        log.info("extractExpiration works");
        return this.extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Claims extractAllClaims(String token) {
        log.info("extractAllClaims works");
        return Jwts
                .parserBuilder()
                .setSigningKey(this.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.JWT_SECRET);
        log.info("getSignInKey works");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
