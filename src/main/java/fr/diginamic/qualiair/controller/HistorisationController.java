package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.exception.ExportException;
import fr.diginamic.qualiair.service.HistoriqueService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/historique/mesures")
public class HistorisationController {

    @Autowired
    private HistoriqueService service;

    @GetMapping("/prevision/{nature}/for/{codeInsee}")
    public ResponseEntity<HistoriquePrevision> getPrevisionForCommune(
            @PathVariable NatureMesurePrevision nature,
            @PathVariable String codeInsee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd
    ) {
        return ResponseEntity.ok().body(service.executePrevisionForCommune(nature, codeInsee, dateStart, dateEnd));
    }

    @GetMapping("/air/{polluant}/for/{codeInsee}")
    public ResponseEntity<HistoriqueAirQuality> getAirQualityForCommune(
            @PathVariable AirPolluant polluant, @PathVariable String codeInsee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd
    ) {
        return ResponseEntity.ok().body(service.executeAirQualityForCommune(polluant, codeInsee, dateStart, dateEnd));
    }

    @GetMapping("/population/for/{codeInsee}")
    public ResponseEntity<HistoriquePopulation> getPopulationForCommune(
            @PathVariable String codeInsee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd
    ) {
        return ResponseEntity.ok().body(service.executePopulationForCommune(codeInsee, dateStart, dateEnd));
    }

    @GetMapping("/prevision/{nature}/for/{codeInsee}/csv")
    public void getPrevisionCsvForCommune(
            @PathVariable NatureMesurePrevision nature, @PathVariable String codeInsee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd, HttpServletResponse response
    ) throws IOException, ExportException {
        service.executePrevisionForCommuneCsv(response, nature, codeInsee, dateStart, dateEnd);
    }

    @GetMapping("/air/{polluant}/for/{codeInsee}/csv")
    public void getAirQualityCsvForCommune(
            @PathVariable AirPolluant polluant, @PathVariable String codeInsee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd, HttpServletResponse response
    ) throws IOException, ExportException {
        service.executeAirQualityForCommuneCsv(response, polluant, codeInsee, dateStart, dateEnd);
    }

    @GetMapping("/population/for/{codeInsee}/csv")
    public void getPopulationForCommuneCsv(
            @PathVariable String codeInsee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd, HttpServletResponse response
    ) throws IOException, ExportException {
        service.executePopulationForCommuneCsv(response, codeInsee, dateStart, dateEnd);
    }
}
