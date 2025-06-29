package fr.diginamic.qualiair.security;

import fr.diginamic.qualiair.entity.Utilisateur;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Implémentation du service JWT utilisant la librairie JJWT.
 * Gère la génération, la validation et l'extraction d'informations des tokens.
 */
@Service
public class JwtAuthentificationService implements IJwtAuthentificationService {
    @Value("${jwt.expires_in}")
    private Integer EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    /**
     * Génère une clé secrète basée sur la clé définie dans les propriétés
     * pour signer et vérifier les tokens JWT.
     */
    private SecretKey getSecuredKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    @Override
    public ResponseCookie generateToken(Utilisateur user) {
        System.out.println("Role: " + user.getRole());
        String jwt = Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
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

    @Override
    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(getSecuredKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
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

    @Override
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
