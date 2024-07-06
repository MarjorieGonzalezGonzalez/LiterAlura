package marjorie.com.Literalura;

import marjorie.com.Literalura.principal.Principal;
import marjorie.com.Literalura.repository.AutorRepository;
import marjorie.com.Literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		// agrego esto para poder hacer nuestro constructor en clase principal
		Principal principal = new Principal(libroRepository,autorRepository);
		principal.muestraElMenú();
	}
}
