package ru.tanaxxt.currencysystem.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.tanaxxt.currencysystem.configs.CurrencyConfig;
import ru.tanaxxt.currencysystem.models.CurrencyRateDto;

import java.util.*;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(CurrencyConfig.class)
public class CbrClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CurrencyConfig config;

    public List<CurrencyRateDto> fetchRates() {

        String response = restTemplate.getForObject(config.getCbApiUrl(), String.class);

        List<CurrencyRateDto> result = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode valuteNode = root.get("Valute");

            if (valuteNode != null) {
                Iterator<Map.Entry<String, JsonNode>> fields = valuteNode.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    JsonNode node = entry.getValue();

                    CurrencyRateDto dto = new CurrencyRateDto(
                            node.get("CharCode").asText(),
                            node.get("Name").asText(),
                            node.get("Value").asDouble(),
                            node.get("Previous").asDouble()
                    );

                    result.add(dto);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CBR response", e);
        }

        return result;
    }
}