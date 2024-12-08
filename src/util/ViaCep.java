package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class ViaCep {
    public static JsonNode getAddress(String cep) throws IOException, InterruptedException {
        // URL da API ViaCEP
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        // Cliente HTTP
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Envia a requisição e obtém a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verifica se o CEP é válido
        if (response.body().contains("\"erro\": true")) {
            throw new IllegalArgumentException("CEP inválido: " + cep);
        }

        // Mapeia os dados para JsonNode
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readTree(response.body());
    }
}
