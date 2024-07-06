package marjorie.com.Literalura.repository;

import marjorie.com.Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreContains(String nombre);
    List<Autor> findByAñoNacimientoLessThanEqualAndAñoMuerteGreaterThanEqual(Integer añoNacimiento, Integer añoMuerte);
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
}
