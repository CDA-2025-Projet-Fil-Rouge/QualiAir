package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.api.ApiToken;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HttpResponseValidator implements IHttpResponseValidator {

    public boolean validate(ResponseEntity<?> response) throws ExternalApiResponseException {
        isTrue(response.getStatusCode() == HttpStatus.OK, String.format("Outgoing request not ok, status : %s \n \t body : %s ", response.getStatusCode(), response.getBody()));
        isTrue(response.getBody() != null, "Outgoing request not ok, response body is empty");
        return true;
    }

    public String validateAndReturnToken(ResponseEntity<ApiToken> response) throws ExternalApiResponseException {
        if (validate(response)) {
            isTrue(response.getBody().getToken() != null, "Error fetching the token, response body is null");
            return response.getBody().getToken();
        } else {
            throw new ExternalApiResponseException("Error fetching the token");
        }
    }
}
