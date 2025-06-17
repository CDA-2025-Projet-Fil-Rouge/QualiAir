package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.service.ApiOpenWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller des requetes à destination de l'api Open-Weather
 * path "/external/api/atmo-france"
 */
@RestController
@RequestMapping({"/external/api/open-weather"})
public class ApiOpenWeatherController {
    @Autowired
    private ApiOpenWeatherService service;

}
