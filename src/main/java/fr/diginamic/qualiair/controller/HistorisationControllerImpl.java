package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
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
public class HistorisationControllerImpl implements HistorisationController {

    @Autowired
    private HistoriqueService service;

    @GetMapping("/prevision/{nature}/for/{scopedCode}")
    @Override
    public ResponseEntity<HistoriquePrevision> getPrevisionsByNatureForScope(
            @PathVariable NatureMesurePrevision nature,
            @PathVariable String scopedCode,
            @RequestParam GeographicalScope scope,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd
    ) {
        HistoriquePrevision result = service.executePrevision(scope, scopedCode, nature, dateStart, dateEnd);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/air/{polluant}/for/{scopedCode}")
    @Override
    public ResponseEntity<HistoriqueAirQuality> getAirQualityByPolluantForScope(
            @PathVariable AirPolluant polluant,
            @PathVariable String scopedCode,
            @RequestParam GeographicalScope scope,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd
    ) {
        HistoriqueAirQuality result = service.executeAirQuality(scope, scopedCode, polluant, dateStart, dateEnd);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/population/for/{scopedCode}")
    @Override
    public ResponseEntity<HistoriquePopulation> getPopulationForScope(
            @PathVariable String scopedCode,
            @RequestParam GeographicalScope scope,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd
    ) {
        HistoriquePopulation result = service.executePopulation(scope, scopedCode, dateStart, dateEnd);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/prevision/{nature}/for/{scopedCode}/csv")
    @Override
    public void getPrevisionByNatureForScopeCsv(
            @PathVariable NatureMesurePrevision nature,
            @PathVariable String scopedCode,
            @RequestParam GeographicalScope scope,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd,
            HttpServletResponse response
    ) throws IOException, ExportException {
        service.executePrevisionCsv(response, scope, scopedCode, nature, dateStart, dateEnd);
    }

    @GetMapping("/air/{polluant}/for/{scopedCode}/csv")
    @Override
    public void getAirQualityByPolluantForScopeCsv(
            @PathVariable AirPolluant polluant, @PathVariable String scopedCode,
            @RequestParam GeographicalScope scope,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd, HttpServletResponse response
    ) throws IOException, ExportException {

        service.executeAirQualityCsv(response, scope, scopedCode, polluant, dateStart, dateEnd);
    }

    @GetMapping("/population/for/{scopedCode}/csv")
    @Override
    public void getPopulationCsvForScope(
            @PathVariable String scopedCode,
            @RequestParam GeographicalScope scope,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd, HttpServletResponse response
    ) throws IOException, ExportException {

        service.executePopulationCsv(response, scope, scopedCode, dateStart, dateEnd);
    }
}
