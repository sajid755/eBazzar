package com.shoppingcart.eBazzar.security.jwt;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.shoppingcart.eBazzar.security.user.ShopUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private int expirationTime;

    public String GenerateTokenForUser(Authentication authentication) {
        ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return generateToken(userPrincipal.getEmail(), userPrincipal.getId(), roles);

    }

    public String generateToken(String email, Long id, List<String> roles) {
        return Jwts.builder()
                .subject(email)
                .claim("id", id)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key())
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject(); // Extract email
    }

    // public boolean validateToken(String token, UserDetails userDetails) {
    // final String username = getUsernameFromToken(token);
    // return (username.equals(userDetails.getUsername()) &&
    // !isTokenExpired(token));
    // }

    public boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getExpiration().before(new Date());
    }

    // This Method is Depricated So will try later
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token expired", e);
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Token unsupported", e);
        } catch (MalformedJwtException e) {
            throw new JwtException("Token malformed", e);
        } catch (SignatureException e) {
            throw new JwtException("Signature validation failed", e);
        } catch (IllegalArgumentException e) {
            throw new JwtException("Illegal argument token", e);
        }
    }

}
