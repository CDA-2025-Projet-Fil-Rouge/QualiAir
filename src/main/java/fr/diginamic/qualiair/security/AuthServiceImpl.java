package fr.diginamic.qualiair.security;

import fr.diginamic.qualiair.dto.entitesDto.AdresseDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.mapper.AdresseMapper;
import fr.diginamic.qualiair.mapper.UtilisateurMapper;
import fr.diginamic.qualiair.repository.AdresseRepository;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.service.UtilisateurService;
import fr.diginamic.qualiair.validator.AdresseValidator;
import fr.diginamic.qualiair.validator.UtilisateurValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Classe de service dédiée à l'inscription/connexion sécurisée des utilisateurs
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    BCryptPasswordEncoder bcrypt;
    @Autowired
    IJwtAuthentificationService IJwtAuthentificationService;
    @Autowired
    AdresseRepository adresseRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    UtilisateurMapper utilisateurMapper;
    @Autowired
    UtilisateurValidator utilisateurValidator;
    @Autowired
    CommuneRepository communeRepository;
    @Autowired
    private AdresseMapper adresseMapper;
    @Autowired
    private AdresseValidator adresseValidator;

    @Override
    public ResponseCookie logUser(UtilisateurDto userDto) {
        Utilisateur utilisateur;
        try {
            utilisateur = utilisateurService.getUser(userDto.getEmail());
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Email ou mot de passe incorrect");
        }

        if (bcrypt.matches(userDto.getMotDePasse(), utilisateur.getMotDePasse())) {
            return IJwtAuthentificationService.generateToken(utilisateur);
        }
        throw new BadCredentialsException("Email ou mot de passe incorrect");
    }

    @Override
    public void createUser(UtilisateurDto userDto, RoleUtilisateur role)
            throws FileNotFoundException, BusinessRuleException {

        AdresseDto adresseDto = userDto.getAdresseDto();
        Commune commune = communeRepository.findByNomReelAndCodePostal(adresseDto.getNomCommune(), adresseDto.getCodePostal())
                .orElseThrow(() -> new FileNotFoundException("Commune non trouvée"));

        Adresse adresse = adresseMapper.fromDto(adresseDto, commune);
        adresseValidator.validate(adresse);
        adresseRepository.save(adresse);

        Utilisateur userToSave = utilisateurMapper.fromDto(userDto, adresse, role);
        utilisateurValidator.validate(userToSave);
        utilisateurRepository.save(userToSave);
    }
}
