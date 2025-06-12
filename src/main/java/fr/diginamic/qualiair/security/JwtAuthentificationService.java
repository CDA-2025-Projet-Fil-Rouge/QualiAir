package fr.diginamic.qualiair.security;

import fr.diginamic.qualiair.entity.Utilisateur;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Stream;

@Service
public class JwtAuthentificationService {
    @Value("${jwt.expires_in}")
    private Integer EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private SecretKey getSecuredKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    public ResponseCookie generateToken(Utilisateur user) {
        System.out.println("Role: " + user.getRole());
        String jwt = Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().toString())
                .expiration(new Date(System.currentTimeMillis() + EXPIRES_IN))
                .signWith(getSecuredKey())
                .compact();
        return ResponseCookie.from(TOKEN_COOKIE, jwt)
                .httpOnly(true)
                .maxAge(EXPIRES_IN / 1000)
                .path("/")
                .build();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(getSecuredKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getEmailFromCookie(HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {

            String token = Stream.of(cookies)
                    .filter(cookie -> cookie.getName().equals(TOKEN_COOKIE))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);

            if (token != null) {
                return getSubject(token);
            }
        }
        throw new Exception("Nothing found with cookie");
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecuredKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expiré");
        } catch (UnsupportedJwtException e) {
            System.out.println("Format du token non supporté");
        } catch (MalformedJwtException e) {
            System.out.println("Token malformé");
        } catch (SecurityException e) {
            System.out.println("Signature invalide");
        } catch (IllegalArgumentException e) {
            System.out.println("Token vide ou null");
        } catch (Exception e) {
            System.out.println("Exception");
        }
        return false;
    }
}
