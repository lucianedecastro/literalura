package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByTitulo(String titulo);
    Optional<Livro> findByTitulo(String titulo);

    // Novo método para buscar livros por idioma
    // @Query é necessária para buscar em @ElementCollection de forma otimizada
    @Query("SELECT l FROM Livro l JOIN l.idiomas i WHERE i = :idioma")
    List<Livro> findByIdiomas(String idioma);
}