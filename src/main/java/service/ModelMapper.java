package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.CountryDailyData;

class ModelMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static CountryDailyData[] parseApiResponse(String jsonApiResponse) {
        try {
            return objectMapper.readValue(jsonApiResponse, CountryDailyData[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
