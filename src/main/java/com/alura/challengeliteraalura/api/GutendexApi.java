package com.alura.challengeliteraalura.api;
import com.alura.challengeliteraalura.modelo.Autor;
import com.alura.challengeliteraalura.modelo.Libro;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GutendexApi {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GutendexApi() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<Libro> obtenerLibros() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://gutendex.com/books/"))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new Exception("Error al hacer la solicitud HTTP: " + e.getMessage());
        }

        if (response.statusCode() != 200) {
            throw new Exception("Error en la respuesta de la API de Gutendex: " + response.statusCode());
        }

        Map<String, Object> responseMap;
        try {
            responseMap = objectMapper.readValue(response.body(), new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new Exception("Error al deserializar la respuesta: " + e.getMessage());
        }

        List<Map<String, Object>> results = (List<Map<String, Object>>) responseMap.get("results");
        List<Libro> libros = new ArrayList<>();
        for (Map<String, Object> result : results) {
            Libro libro = new Libro();
            libro.setTitulo((String) result.get("title"));
            libro.setNumeroDeDescargas((Integer) result.get("download_count"));

            List<Map<String, Object>> authors = (List<Map<String, Object>>) result.get("authors");
            List<Autor> autores = new ArrayList<>();
            for (Map<String, Object> author : authors) {
                Autor autor = new Autor();
                autor.setNombre((String) author.get("name"));
                autor.setAnioNacimiento((Integer) author.get("birth_year"));
                autor.setAnioMuerte((Integer) author.get("death_year"));
                autores.add(autor);
            }
            libro.setAutores(autores);

            List<String> idiomas = (List<String>) result.get("languages");
            libro.setIdiomas(idiomas);

            libros.add(libro);
        }
        return libros;
    }
}