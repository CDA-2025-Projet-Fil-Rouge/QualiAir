package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurUpdateDto;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.security.CusomUserPrincipal;
import fr.diginamic.qualiair.service.UtilisateurService;
import fr.diginamic.qualiair.utils.HttpRequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UtilisateurControllerImpl implements UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private HttpRequestUtils httpRequestUtils;

    @GetMapping("/get-personal-data")
    @Override
    public ResponseEntity<UtilisateurDto> getPersonalData(HttpServletRequest request) throws Exception {
        Utilisateur user = httpRequestUtils.getUtilisateurFromRequest(request);
        UtilisateurDto dto = utilisateurService.getPersonalData(user.getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get-all-paginated")
    @Override
    public ResponseEntity<Page<UtilisateurDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            HttpServletRequest request) throws Exception {
        Utilisateur demandeur = httpRequestUtils.getUtilisateurFromRequest(request);
        Pageable pageable = PageRequest.of(page, size, Sort.by("nom").ascending());
        return ResponseEntity.ok(utilisateurService.getAllUsers(pageable, demandeur));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UtilisateurDto>> getAllUsersNonPaginated(
            @AuthenticationPrincipal CusomUserPrincipal principal) {

        Utilisateur demandeur = utilisateurService.getUser(principal.getUsername());
        List<UtilisateurDto> users = utilisateurService.getAllUsers(demandeur);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update-personal-data")
    @Override
    public ResponseEntity<UtilisateurUpdateDto> updatePersonalData(
            @RequestBody UtilisateurUpdateDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur utilisateur = httpRequestUtils.getUtilisateurFromRequest(request);
        UtilisateurUpdateDto updated = utilisateurService.updatePersonalData(utilisateur, dto);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/toggle-admin/{id}")
    @Override
    public ResponseEntity<String> toggleAdminRole(
            @PathVariable("id") Long idUser,
            HttpServletRequest request) throws Exception {
        Utilisateur demandeur = httpRequestUtils.getUtilisateurFromRequest(request);
        String message = utilisateurService.toggleAdminUser(idUser, demandeur);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/toggle-activation/{id}")
    @Override
    public ResponseEntity<String> toggleActivationRole(
            @PathVariable("id") Long idUser,
            HttpServletRequest request) throws Exception {
        Utilisateur demandeur = httpRequestUtils.getUtilisateurFromRequest(request);
        String message = utilisateurService.toggleActivationUser(idUser, demandeur);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/toggle-ban/{id}")
    @Override
    public ResponseEntity<String> toggleBanUser(
            @PathVariable("id") Long idUser,
            HttpServletRequest request) throws Exception {
        Utilisateur demandeur = httpRequestUtils.getUtilisateurFromRequest(request);
        String message = utilisateurService.toggleBanUser(idUser, demandeur);
        return ResponseEntity.ok(message);
    }
}
