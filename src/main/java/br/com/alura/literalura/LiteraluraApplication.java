package br.com.alura.literalura; // Confirme se o pacote está correto

import br.com.alura.literalura.dto.DadosAutor;
import br.com.alura.literalura.dto.DadosLivro;
import br.com.alura.literalura.dto.DadosRespostaApi;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional; // Importe esta anotação

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner; // Para entrada do usuário

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private AutorRepository autorRepository;

	private ConsumoApi consumoApi = new ConsumoApi();
	private ConverteDados conversor = new ConverteDados();
	private Scanner teclado = new Scanner(System.in); // Para interagir com o usuário

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello world!");

		var opcao = -1;
		while (opcao != 0) {
			var menu = """
                1 - Buscar livro pelo título
                2 - Listar todos os livros registrados
                3 - Listar todos os autores registrados
                4 - Listar autores vivos em um determinado ano
                5 - Listar livros em um determinado idioma
                0 - Sair
                """;
			System.out.println(menu);
			System.out.print("Escolha uma opção: ");
			opcao = teclado.nextInt();
			teclado.nextLine(); // Consome a nova linha

			switch (opcao) {
				case 1:
					buscarLivroPorTitulo();
					break;
				case 2:
					listarLivrosRegistrados();
					break;
				case 3:
					listarAutoresRegistrados();
					break;
				case 4:
					listarAutoresVivosPorAno();
					break;
				case 5:
					listarLivrosPorIdioma();
					break;
				case 0:
					System.out.println("Saindo...");
					break;
				default:
					System.out.println("Opção inválida!");
			}
		}
		teclado.close();
	}

	// --- MÉTODOS DE NEGÓCIO ---

	@Transactional // Esta anotação garante que o método execute dentro de uma transação
	public void buscarLivroPorTitulo() {
		System.out.println("Digite o título do livro para buscar:");
		var tituloLivro = teclado.nextLine();

		String endereco = "https://gutendex.com/books/?search=" + tituloLivro.replace(" ", "%20");
		String json = consumoApi.obterDados(endereco);

		DadosRespostaApi dadosBusca = conversor.obterDados(json, DadosRespostaApi.class);

		if (dadosBusca != null && !dadosBusca.resultados().isEmpty()) {
			DadosLivro dadosLivro = dadosBusca.resultados().get(0); // Pega o primeiro livro da lista

			// Verifica se o livro já existe no banco de dados pelo título
			if (livroRepository.existsByTitulo(dadosLivro.titulo())) {
				System.out.println("\n--- Livro '" + dadosLivro.titulo() + "' já existe no banco de dados. ---");
			} else {
				// Processa o autor
				Autor autor = null;
				if (dadosLivro.autores() != null && !dadosLivro.autores().isEmpty()) {
					DadosAutor dadosAutor = dadosLivro.autores().get(0); // Pega o primeiro autor (simplificação)
					Optional<Autor> autorExistente = autorRepository.findByNomeContainsIgnoreCase(dadosAutor.nome());

					if (autorExistente.isPresent()) {
						autor = autorExistente.get();
						System.out.println("\n--- Autor '" + autor.getNome() + "' já existe. Associando ao livro existente. ---");
					} else {
						autor = new Autor(dadosAutor);
						autorRepository.save(autor); // Salva o novo autor
						System.out.println("\n--- Novo autor '" + autor.getNome() + "' salvo. ---");
					}
				} else {
					System.out.println("\n--- Livro sem informações de autor. Não será salvo. ---");
					return; // Aborta se não há autor para evitar salvar livro sem autor
				}

				// Cria a entidade Livro e associa o autor
				Livro livro = new Livro(dadosLivro);
				livro.setAutor(autor);

				livroRepository.save(livro); // Salva o livro no banco de dados
				System.out.println("\n--- Livro '" + livro.getTitulo() + "' salvo com sucesso! ---");
			}
		} else {
			System.out.println("\n--- Nenhum livro encontrado na busca. ---");
		}
	}

	public void listarLivrosRegistrados() {
		System.out.println("\n--- Todos os livros registrados no banco de dados ---");
		List<Livro> livros = livroRepository.findAll();
		if (livros.isEmpty()) {
			System.out.println("Nenhum livro registrado.");
		} else {
			livros.forEach(System.out::println);
		}
	}

	public void listarAutoresRegistrados() {
		System.out.println("\n--- Todos os autores registrados no banco de dados ---");
		List<Autor> autores = autorRepository.findAll();
		if (autores.isEmpty()) {
			System.out.println("Nenhum autor registrado.");
		} else {
			autores.forEach(System.out::println);
		}
	}

	public void listarAutoresVivosPorAno() {
		System.out.println("Digite o ano para listar autores vivos:");
		var ano = teclado.nextInt();
		teclado.nextLine(); // Consome a nova linha

		// Supondo que você queira autores que nasceram antes ou no ano e faleceram depois ou não faleceram
		List<Autor> autoresVivos = autorRepository.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqualOrAnoFalecimentoIsNull(ano, ano);
		if (autoresVivos.isEmpty()) {
			System.out.println("Nenhum autor encontrado vivo no ano " + ano + ".");
		} else {
			System.out.println("\n--- Autores vivos no ano " + ano + " ---");
			autoresVivos.forEach(System.out::println);
		}
	}

	public void listarLivrosPorIdioma() {
		System.out.println("Digite o idioma para listar livros (ex: en, pt, es):");
		var idioma = teclado.nextLine();

		// Usando o novo método do repositório para buscar por idioma
		List<Livro> livrosPorIdioma = livroRepository.findByIdiomas(idioma);

		if (livrosPorIdioma.isEmpty()) {
			System.out.println("Nenhum livro encontrado no idioma '" + idioma + "'.");
		} else {
			System.out.println("\n--- Livros no idioma '" + idioma + "' ---");
			livrosPorIdioma.forEach(System.out::println);
		}
	}
}