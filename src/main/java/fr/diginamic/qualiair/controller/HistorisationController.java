package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
import fr.diginamic.qualiair.exception.ExportException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;

public interface HistorisationController {
    @GetMapping("/prevision/{nature}/for/{code}")
    ResponseEntity<HistoriquePrevision> getPrevisionsByNatureForScope(
            @PathVariable NatureMesurePrevision nature,
            @PathVariable String code,
            @RequestParam GeographicalScope scope,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd
    );

    @GetMapping("/air/{polluant}/for/{code}")
    ResponseEntity<HistoriqueAirQuality> getAirQualityByPolluantForScope(
            @PathVariable AirPolluant polluant, @PathVariable String code,
            @RequestParam GeographicalScope scope,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd
    );

    @GetMapping("/population/for/{code}")
    ResponseEntity<HistoriquePopulation> getPopulationForScope(
            @PathVariable String code,
            @RequestParam GeographicalScope scope,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd
    );

    @GetMapping("/prevision/{nature}/for/{code}/csv")
    void getPrevisionByNatureForScopeCsv(
            @PathVariable NatureMesurePrevision nature, @PathVariable String code,
            @RequestParam GeographicalScope scope,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd, HttpServletResponse response
    ) throws IOException, ExportException;

    @GetMapping("/air/{polluant}/for/{code}/csv")
    void getAirQualityByPolluantForScopeCsv(
            @PathVariable AirPolluant polluant, @PathVariable String code,
            @RequestParam GeographicalScope scope,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd, HttpServletResponse response
    ) throws IOException, ExportException;

    @GetMapping("/population/for/{code}/csv")
    void getPopulationCsvForScope(
            @PathVariable String code,
            @RequestParam GeographicalScope scope,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd, HttpServletResponse response
    ) throws IOException, ExportException;
}
