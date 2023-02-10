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
    private String JWT_SECRET_KEY;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String getGeneratedToken(UserDetails userDetails) {
        return this.generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getSignInKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    @Override
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.JWT_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
