package br.com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosRespostaApi(
        @JsonAlias("count") Integer contagem,
        @JsonAlias("results") List<DadosLivro> resultados // Lista de objetos DadosLivro
) {}
