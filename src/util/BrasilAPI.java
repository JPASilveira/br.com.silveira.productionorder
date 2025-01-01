package util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BrasilAPI {
    private static final Map<String, String> STATE_MAP = new HashMap<>();

    static {
        STATE_MAP.put("AC", "ACRE");
        STATE_MAP.put("AL", "ALAGOAS");
        STATE_MAP.put("AP", "AMAPÁ");
        STATE_MAP.put("AM", "AMAZONAS");
        STATE_MAP.put("BA", "BAHIA");
        STATE_MAP.put("CE", "CEARÁ");
        STATE_MAP.put("DF", "DISTRITO FEDERAL");
        STATE_MAP.put("ES", "ESPÍRITO SANTO");
        STATE_MAP.put("GO", "GOIÁS");
        STATE_MAP.put("MA", "MARANHÃO");
        STATE_MAP.put("MT", "MATO GROSSO");
        STATE_MAP.put("MS", "MATO GROSSO DO SUL");
        STATE_MAP.put("MG", "MINAS GERAIS");
        STATE_MAP.put("PA", "PARÁ");
        STATE_MAP.put("PB", "PARAÍBA");
        STATE_MAP.put("PR", "PARANÁ");
        STATE_MAP.put("PE", "PERNAMBUCO");
        STATE_MAP.put("PI", "PIAUÍ");
        STATE_MAP.put("RJ", "RIO DE JANEIRO");
        STATE_MAP.put("RN", "RIO GRANDE DO NORTE");
        STATE_MAP.put("RS", "RIO GRANDE DO SUL");
        STATE_MAP.put("RO", "RONDÔNIA");
        STATE_MAP.put("RR", "RORAIMA");
        STATE_MAP.put("SC", "SANTA CATARINA");
        STATE_MAP.put("SP", "SÃO PAULO");
        STATE_MAP.put("SE", "SERGIPE");
        STATE_MAP.put("TO", "TOCANTINS");
    }

    public static String getStateName(String stateAbbreviation) {
        if (stateAbbreviation == null) {
            throw new IllegalArgumentException("State abbreviation cannot be null.");
        }
        String stateName = STATE_MAP.get(stateAbbreviation.toUpperCase());
        if (stateName == null) {
            throw new IllegalArgumentException("Invalid state abbreviation: " + stateAbbreviation);
        }
        return stateName;
    }


    public static JsonNode getCnpjData(String cnpj) throws IOException, InterruptedException {
        // URL da API
        String url = "https://brasilapi.com.br/api/cnpj/v1/" + cnpj;

        // Cliente HTTP
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Envia a requisição e obtém a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verifica se o CNPJ é válido
        if (response.body().contains("\"type\": bad_request")) {
            throw new IllegalArgumentException("CNPJ inválido: " + cnpj);
        }

        // Mapeia os dados para JsonNode
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readTree(response.body());
    }
}
