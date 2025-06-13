package br.com.alura.literalura.model;

import br.com.alura.literalura.dto.DadosLivro;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "livros") // Define o nome da tabela no banco de dados como "livros"
public class Livro {
    @Id // Marca o campo 'id' como a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a estratégia de geração de ID como auto-incremento
    private Long id;

    @Column(unique = true) // Garante que o campo 'titulo' seja único na tabela
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER)

    private Autor autor; // Relacionamento muitos-para-um: Muitos livros podem ter um autor

    @ElementCollection(fetch = FetchType.EAGER) // Indica que é uma coleção de tipos básicos (String)
    @CollectionTable(name = "livro_idiomas", joinColumns = @JoinColumn(name = "livro_id"))
    // Define a tabela que armazenará os idiomas e a coluna de ligação com a tabela de livros
    @Column(name = "idioma") // Define o nome da coluna que armazenará o idioma nesta nova tabela
    private List<String> idiomas; // Uma lista para armazenar os idiomas do livro

    private Integer numeroDownloads; // Campo para o número de downloads do livro

    // Construtor padrão (obrigatório para JPA)
    public Livro() {}

    // Construtor para criar um Livro a partir de um objeto DadosLivro (DTO)
    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idiomas = dadosLivro.idiomas(); // Popula a lista de idiomas a partir do DTO
        this.numeroDownloads = dadosLivro.numeroDownloads();
    }

    // --- Getters e Setters (métodos para acessar e modificar os atributos) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        // Retorna uma representação em string do objeto Livro
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor=" + (autor != null ? autor.getNome() : "Desconhecido") + // Exibe o nome do autor ou "Desconhecido"
                ", idiomas=" + idiomas +
                ", numeroDownloads=" + numeroDownloads +
                '}';
    }
}