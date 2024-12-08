package util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class BrasilAPI {
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
