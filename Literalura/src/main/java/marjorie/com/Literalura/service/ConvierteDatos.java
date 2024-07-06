package marjorie.com.Literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements ConvierteDatos1 {
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public <L> L obtenerDatos(String json, Class<L> lClass) {
        try {
            return objectMapper.readValue(json,lClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
