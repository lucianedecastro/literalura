package br.com.alura.literalura.model;

import br.com.alura.literalura.dto.DadosAutor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    private List<Livro> livros = new ArrayList<>();


    public Autor() {}


    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nome(); // Atribui o nome do DTO
        this.anoNascimento = dadosAutor.anoNascimento(); // Atribui o ano de nascimento do DTO
        this.anoFalecimento = dadosAutor.anoFalecimento(); // Atribui o ano de falecimento do DTO
    }

    // --- Getters e Setters (métodos para acessar e modificar os atributos) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    // Ao definir a lista de livros, também é importante garantir que o autor seja setado em cada livro
    public void setLivros(List<Livro> livros) {
        if (livros != null) {
            livros.forEach(l -> l.setAutor(this)); // Garante a ligação bidirecional
        }
        this.livros = livros;
    }

    @Override
    public String toString() {
        // Formata os títulos dos livros associados para uma string legível
        // Evita NullPointerException verificando se a lista 'livros' é nula ou vazia
        String livrosDoAutor = (livros != null && !livros.isEmpty()) ?
                livros.stream()
                        .map(Livro::getTitulo) // Mapeia para o título de cada livro
                        .collect(Collectors.joining(", ")) : // Junta os títulos com vírgula e espaço
                "Nenhum livro associado"; // Mensagem se não houver livros

        // Retorna uma representação em string do objeto Autor
        return "Autor{" +
                "nome='" + nome + '\'' +
                ", anoNascimento=" + (anoNascimento != null ? anoNascimento : "N/A") + // Exibe N/A se o ano for nulo
                ", anoFalecimento=" + (anoFalecimento != null ? anoFalecimento : "N/A") + // Exibe N/A se o ano for nulo
                ", livros=[" + livrosDoAutor + "]" +
                '}';
    }
}