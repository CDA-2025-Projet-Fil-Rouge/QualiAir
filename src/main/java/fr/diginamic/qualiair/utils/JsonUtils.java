package fr.diginamic.qualiair.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.qualiair.entity.api.UtilisateurAtmoFrance;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {

    public String userToJson(String username, String password) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        UtilisateurAtmoFrance user = new UtilisateurAtmoFrance(username, password);
        return mapper.writeValueAsString(user);
    }
}
