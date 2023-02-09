package ru.os.OnlineShop.services.interfaces;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

public interface JwtServiceInterface {
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimResolver);
    String getGeneratedToken(UserDetails userDetails);
    String generateToken(HashMap<String, Object> extraClaims, UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
    Date extractExpiration(String token);
    Claims extractAllClaims(String token);
    Key getSignInKey();
}
