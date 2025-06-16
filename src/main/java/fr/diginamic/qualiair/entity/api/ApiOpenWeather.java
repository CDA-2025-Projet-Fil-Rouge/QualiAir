package fr.diginamic.qualiair.entity.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class ApiOpenWeather {
    @Value("${external.api.ow.uri.meteo-current}")
    private URI uriCurrentWeather;
    @Value("${external.api.ow.uri.meteo-5d}")
    private URI uriWeather5Days;
    @Value("${external.api.ow.uri.air}")
    private URI uriLocalAirData;

    @Value("${external.api.ow.token}")
    private String token;
    @Value("${external.api.ow.param-name}")
    private String tokenParam;

    /**
     * Getter
     *
     * @return uriCurrentWeather
     */
    public URI getUriCurrentWeather() {
        return uriCurrentWeather;
    }

    /**
     * Getter
     *
     * @return uriWeather5Days
     */
    public URI getUriWeather5Days() {
        return uriWeather5Days;
    }

    /**
     * Getter
     *
     * @return uriLocalAirData
     */
    public URI getUriLocalAirData() {
        return uriLocalAirData;
    }

    /**
     * Getter
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * Getter
     *
     * @return tokenParanAttribute
     */
    public String getTokenParam() {
        return tokenParam;
    }


}
