package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.CommuneDto;
import fr.diginamic.qualiair.exception.RouteParamException;
import fr.diginamic.qualiair.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/commune"})
public class CommuneController {

    @Autowired
    private CommuneService service;

    @GetMapping("/match-name")
    public ResponseEntity<List<CommuneDto>> getCommunesbyNameContaining(@RequestParam String containing) throws RouteParamException {
        return ResponseEntity.ok().body(service.matchTop10ByName(containing));
    }

}
