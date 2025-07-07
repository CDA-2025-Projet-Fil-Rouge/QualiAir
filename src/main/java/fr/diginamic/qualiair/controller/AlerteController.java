package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.notification.AlerteInfo;
import fr.diginamic.qualiair.dto.notification.DemandeNotification;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.exception.RouteParamException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interface du contrôleur REST pour la gestion des alertes liées à la qualité de l'air.
 * Permet de récupérer des alertes paginées et de notifier les utilisateurs selon une demande spécifique.
 */
@Tag(name = "Alertes Qualité de l'air", description = "Gère les alertes et notifications liées à la qualité de l'air")
public interface AlerteController {
    /**
     * Récupère une page d'alertes filtrées par indice et type de polluant.
     *
     * @param page      Le numéro de la page à récupérer (indexé à 0). Par défaut 0.
     * @param size      La taille de la page (nombre d'éléments par page). Par défaut 20.
     * @param maxIndice L'indice maximum des alertes à inclure. Par défaut 4.
     * @param polluant  Le type de polluant concerné par l'alerte. Par défaut {@link AirPolluant#ATMO}.
     * @return Une réponse HTTP contenant une page d'objets {@link AlerteInfo} correspondant aux alertes filtrées.
     */
    @Operation(
            summary = "Liste des alertes filtrées",
            description = "Récupère une page d'alertes filtrées par indice de qualité maximum et type de polluant.",
            parameters = {
                    @Parameter(name = "page", description = "Numéro de page (commence à 0)", example = "0"),
                    @Parameter(name = "size", description = "Taille de la page", example = "20"),
                    @Parameter(name = "maxIndice", description = "Indice de qualité maximum à filtrer", example = "4"),
                    @Parameter(name = "polluant", description = "Polluant ciblé (ex : ATMO, NO2, PM10)", example = "ATMO")
            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste paginée d'alertes",
            content = @Content(schema = @Schema(implementation = AlerteInfo.class))
    )
    @GetMapping({"/get-all"})
    ResponseEntity<Page<AlerteInfo>> getAllAlertes(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "20") int size,
                                                   @RequestParam(defaultValue = "4") int maxIndice,
                                                   @RequestParam(defaultValue = "ATMO") AirPolluant polluant);

    /**
     * Envoie des notifications aux utilisateurs selon la demande spécifiée.
     *
     * @param demandeNotification Objet contenant les paramètres de la demande de notification.
     * @return Une réponse HTTP indiquant le résultat de la notification.
     * @throws RouteParamException          Si un paramètre de la route est invalide.
     * @throws ExternalApiResponseException Si une erreur survient lors de l'appel à une API externe.
     * @throws ParsedDataException          Si une erreur survient lors du traitement des données récupérées.
     * @throws DataNotFoundException        Si les données nécessaires ne sont pas trouvées.
     */
    @Operation(
            summary = "Notifier les utilisateurs",
            description = "Envoie une alerte à des utilisateurs en fonction de la zone géographique et du message fourni."
    )
    @ApiResponse(responseCode = "200", description = "Notification envoyée avec succès")
    @ApiResponse(responseCode = "400", description = "Paramètres invalides ou données introuvables")
    @ApiResponse(responseCode = "500", description = "Erreur serveur ou API externe")
    @PostMapping("/notify-users")
    ResponseEntity<?> notifyUsers(@RequestBody DemandeNotification demandeNotification) throws RouteParamException, ExternalApiResponseException, ParsedDataException, DataNotFoundException;
}
