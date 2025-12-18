package umc9th_hackathon.daybreak.domain.mission.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class SimpleJsonArrayParser {
    private static final ObjectMapper om = new ObjectMapper();

    public static List<String> parse(String jsonArrayString) {
        try {
            return om.readValue(jsonArrayString, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new IllegalStateException("OpenAI content is not a JSON array: " + jsonArrayString, e);
        }
    }
}
