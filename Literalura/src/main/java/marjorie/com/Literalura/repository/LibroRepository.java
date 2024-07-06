package marjorie.com.Literalura.repository;

import marjorie.com.Literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdiomasContains(String idiomas);
    Optional<Libro> findByTituloContains(String titulo);
    List<Libro> findTop10ByOrderByNumeroDeDescargasDesc();
}
