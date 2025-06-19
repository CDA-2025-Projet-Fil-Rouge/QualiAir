package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.enumeration.AirPolluant;
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
    @GetMapping("/prevision/{nature}/for/{codeInsee}")
    ResponseEntity<HistoriquePrevision> getPrevisionForCommune(
            @PathVariable NatureMesurePrevision nature,
            @PathVariable String codeInsee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd
    );

    @GetMapping("/air/{polluant}/for/{codeInsee}")
    ResponseEntity<HistoriqueAirQuality> getAirQualityForCommune(
            @PathVariable AirPolluant polluant, @PathVariable String codeInsee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd
    );

    @GetMapping("/population/for/{codeInsee}")
    ResponseEntity<HistoriquePopulation> getPopulationForCommune(
            @PathVariable String codeInsee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd
    );

    @GetMapping("/prevision/{nature}/for/{codeInsee}/csv")
    void getPrevisionCsvForCommune(
            @PathVariable NatureMesurePrevision nature, @PathVariable String codeInsee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd, HttpServletResponse response
    ) throws IOException, ExportException;

    @GetMapping("/air/{polluant}/for/{codeInsee}/csv")
    void getAirQualityCsvForCommune(
            @PathVariable AirPolluant polluant, @PathVariable String codeInsee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd, HttpServletResponse response
    ) throws IOException, ExportException;

    @GetMapping("/population/for/{codeInsee}/csv")
    void getPopulationForCommuneCsv(
            @PathVariable String codeInsee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd, HttpServletResponse response
    ) throws IOException, ExportException;
}
