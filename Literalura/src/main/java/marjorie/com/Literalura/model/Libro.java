package marjorie.com.Literalura.model;

import jakarta.persistence.*;
import marjorie.com.Literalura.dto.DatosAutor;
import marjorie.com.Literalura.dto.DatosLibro;

@Entity
@Table(name = "Libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    private String idiomas;
    private Double numeroDeDescargas;

    public Libro() {}

    public Libro(String titulo, Autor autor, String idiomas, Double numeroDeDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idiomas = idiomas;
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return "**** Información del Libro ****" +
                "\n\ttitle: " + titulo +
                "\n\tauthors: " + autor.getNombre() +
                "\n\tlanguages: " + idiomas +
                "\n\tdownload_count: " + numeroDeDescargas;
    }

}
