package fr.diginamic.qualiair.security;

import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.service.UtilisateurService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    IJwtAuthentificationService IJwtAuthentificationService;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (req.getCookies() != null) {
            Stream.of(req.getCookies()).filter(cookie -> cookie.getName().equals(TOKEN_COOKIE)).map(Cookie::getValue)
                    .forEach(token ->
                    {
                        if (IJwtAuthentificationService.validateToken(token)) {

                            String email = IJwtAuthentificationService.getSubject(token);

                            try {
                                Utilisateur user = utilisateurService.getUser(email);
                                UsernamePasswordAuthenticationToken auth =
                                        new UsernamePasswordAuthenticationToken(
                                                email,
                                                null,
                                                List.of(new SimpleGrantedAuthority(
                                                        "ROLE_" + user.getRole().toString()))
                                        );
                                SecurityContextHolder.getContext().setAuthentication(auth);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
        }
        filterChain.doFilter(req, response);
    }
}
