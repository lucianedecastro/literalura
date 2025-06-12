package br.com.alura.literalura.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    // Método para obter dados de uma URL
    public String obterDados(String endereco) {
        // Cria um cliente HTTP para enviar requisições
        HttpClient client = HttpClient.newHttpClient();

        // Constrói a requisição HTTP GET para o endereço fornecido
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco)) // Define a URL da requisição
                .build(); // Constrói o objeto de requisição

        // Variável para armazenar a resposta da API
        HttpResponse<String> response = null;

        try {
            // Envia a requisição e recebe a resposta como uma String
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) { // Captura exceções como problemas de conexão
            System.out.println("Erro ao obter dados da API: " + e.getMessage());
        }

        // Retorna o corpo da resposta (o JSON, neste caso)
        return response.body();
    }
}
