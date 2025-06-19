package fr.diginamic.qualiair.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller des requetes à destination de l'api Open-Weather
 * path "/external/api/atmo-france"
 */
@RestController
@RequestMapping({"/external/api/open-weather"})
public class ApiOpenWeatherController {
    //For now data from this api are fetched by schedule
}
