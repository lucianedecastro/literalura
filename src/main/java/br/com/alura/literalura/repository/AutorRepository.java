package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    // Método para buscar um autor pelo nome, ignorando maiúsculas/minúsculas e permitindo partes do nome
    Optional<Autor> findByNomeContainsIgnoreCase(String nome);

    // Método para verificar se um autor com o nome dado já existe no banco de dados
    boolean existsByNome(String nome);

    // Método de consulta para buscar autores que estavam vivos em um determinado ano.
    // A lógica é: (anoNascimento <= ano) E (anoFalecimento >= ano OU anoFalecimento É NULO)
    // Isso cobre autores que nasceram até o ano dado E (faleceram depois ou no ano dado OU ainda não faleceram)
    List<Autor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqualOrAnoFalecimentoIsNull(Integer anoNascimento, Integer anoFalecimento);


}