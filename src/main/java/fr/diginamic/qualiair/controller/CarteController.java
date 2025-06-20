package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.carte.InfoCarteCommune;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Interface du contrôleur pour la gestion des cartes et données des communes.
 */
@Tag(name = "Carte", description = "API pour la gestion des données cartographiques des communes")
public interface CarteController {
    /**
     * Récupère les données miniatures des communes filtées par population.
     * <br>
     * Cette méthode permet d'obtenir une liste des communes ayant une population
     * supérieure ou égale au nombre d'habitants spécifié. Les données retournées
     * incluent les informations essentielles pour l'affichage sur une carte :
     * coordonnées géographiques, qualité de l'air, et conditions météorologiques.
     *
     * @param nbHabitant Le nombre minimum d'habitants pour filtrer les communes.
     *                   Doit être un nombre entier positif.
     * @return Une ResponseEntity contenant la liste des {@link InfoCarteCommune}
     * correspondant aux critères de filtrage, ou une liste vide si aucune
     * commune ne correspond aux critères.
     * @throws IllegalArgumentException si nbHabitant est négatif
     */
    @Operation(
            summary = "Récupère les données miniatures des communes par population",
            description = "Retourne une liste des communes ayant une population supérieure ou égale au seuil spécifié. " +
                          "Les données incluent les coordonnées GPS, la qualité de l'air et les conditions météorologiques.",
            operationId = "getThumbnailData"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des communes récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = InfoCarteCommune.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Paramètre nbHabitant invalide (valeur négative)",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur interne du serveur",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/commune/get-thumbnail-data")
    ResponseEntity<List<InfoCarteCommune>> getThumbnailData(@RequestParam int nbHabitant);
}
