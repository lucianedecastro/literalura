package br.com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora propriedades JSON que não estão mapeadas nesta classe
public record DadosAutor(
        @JsonAlias("name") String nome, // Mapeia o campo "name" do JSON para "nome" em Java
        @JsonAlias("birth_year") Integer anoNascimento,
        @JsonAlias("death_year") Integer anoFalecimento
) {}
