package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.CommuneDto;
import fr.diginamic.qualiair.exception.RouteParamException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CommuneController {
    @GetMapping("/match-name")
    ResponseEntity<List<CommuneDto>> getCommunesbyNameContaining(@RequestParam String containing) throws RouteParamException;
}
