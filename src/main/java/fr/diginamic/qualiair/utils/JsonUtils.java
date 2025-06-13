package fr.diginamic.qualiair.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {

    @Deprecated
    public String userToJson(String username, String password) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();


        return mapper.writeValueAsString(username + " " + password);
    }
}
