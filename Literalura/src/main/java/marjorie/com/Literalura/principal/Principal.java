package marjorie.com.Literalura.principal;

import marjorie.com.Literalura.dto.DatosAutor;
import marjorie.com.Literalura.dto.DatosLibro;
import marjorie.com.Literalura.dto.results;
import marjorie.com.Literalura.model.Autor;
import marjorie.com.Literalura.model.Libro;

import java.awt.print.Book;
import java.util.Optional;
import java.util.Scanner;
import java.util.List;
import java.util.Comparator;

import marjorie.com.Literalura.repository.AutorRepository;
import marjorie.com.Literalura.repository.LibroRepository;
import marjorie.com.Literalura.service.ConsumoApi;
import marjorie.com.Literalura.service.ConvierteDatos;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoAPI = new ConsumoApi();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    //agrego esta variable para almacernar los datos
    List<Libro> libros;
    List<Autor> autores;


    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.consumoAPI = consumoAPI;
        this.convierteDatos = convierteDatos;
        this.teclado = teclado;
    }

    public void muestraElMenú() {
        var opcion = -1;
        while (opcion != 0) {
            String menu = """
                    ***********Bienvenido/a*********\n
                    Selecciona una opción
                    1 - Buscar Libro Por Título
                    2 - Mostrar Libros Registrados
                    3 - Mostrar Autores Registrados
                    4 - Buscar Autores Vivos Según Año
                    5 - Mostrar Libros Por Idioma
                    6 - Mostrar top 10 descargas
                    7 - Buscar Autor por nombre
                    0 - Salir""";
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    BuscarLibroPorTitulo();
                    break;
                case 2:
                    MostrarLibrosRegistrados();
                    break;
                case 3:
                    MostrarAutoresRegistrados();
                    break;
                case 4:
                    BuscarAutoresVivosSegunAño();
                    break;
                case 5:
                    MostrarLibrosPorIdioma();
                    break;
                case 6:
                    MostarTop10();
                    break;
                case 7:
                    buscarAutorPorNombre();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
        System.out.println("****************************************");
    }

    private void BuscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        String nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(nombreLibro.replace(" ", "%20"));
        var datos = convierteDatos.obtenerDatos(json, results.class);

        if (datos.results().isEmpty()) {
            System.out.println("El libro ingresado no se encuentra disponible");
        } else {
            DatosLibro datosLibro = datos.results().get(0);
            DatosAutor datosAutor = datosLibro.autor().get(0); // Obtén el primer autor
            Autor autor = new Autor(datosAutor.nombre(), datosAutor.añoNacimiento(), datosAutor.añoMuerte());
            Libro libro = new Libro(datosLibro.titulo(), autor, datosLibro.idiomas().get(0), datosLibro.numeroDeDescargas());
            guardar(libro, autor);
        }
    }

    private void guardar(Libro libro, Autor autor) {
        Optional<Autor> autorExistente = autorRepository.findByNombreContains(autor.getNombre());
        if (autorExistente.isPresent()) {
            libro.setAutor(autorExistente.get());
        } else {
            autorRepository.save(autor);
            libro.setAutor(autor);
        }
        libroRepository.save(libro);
        System.out.println("Libro y autor registrados correctamente");
    }

    private void MostrarLibrosRegistrados() {
        System.out.println("Lista de libros registrados actualmente en la base de datos\n");
        libros = libroRepository.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);

    }

    private void MostrarAutoresRegistrados() {
        System.out.println("Lista de autores registrados actualmente en la base de datos\n");
        autores = autorRepository.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void BuscarAutoresVivosSegunAño() {
        System.out.println("Ingrese el año que desea buscar \n");
        Integer año = Integer.valueOf(teclado.nextLine());
        autores = autorRepository
                .findByAñoNacimientoLessThanEqualAndAñoMuerteGreaterThanEqual(año, año);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año indicado");
        } else {
            autores.stream()
                    .sorted(Comparator.comparing(Autor::getNombre))
                    .forEach(System.out::println);
        }
    }

    private void MostrarLibrosPorIdioma() {
        System.out.println("""
                Escribe el codigo del lenguaje que deseas \n
                en - Ingles
                es - Español
                fr - Frances
                pt - Portugues
                """);
        String Idiomas = teclado.nextLine();
        libros = libroRepository.findByIdiomasContains(Idiomas);
        if (libros.isEmpty()) {
            System.out.println("No hay libro para ese idioma");
        } else {
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(System.out::println);
        }
    }

    private void MostarTop10() {
        List<Libro> top10Libros = libroRepository.findTop10ByOrderByNumeroDeDescargasDesc();
        System.out.println("Top 10 libros más descargados:");
        for (Libro libro : top10Libros) {
            System.out.println("Título: " + libro.getTitulo() + ", Descargas: " + libro.getNumeroDeDescargas());
        }
    }

    private void buscarAutorPorNombre() {
        System.out.println("Escribe el nombre del autor que deseas buscar");
        String nombreAutor = teclado.nextLine();
        List<Autor> autores = autorRepository.findByNombreContainingIgnoreCase(nombreAutor);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores con ese nombre");
        } else {
            System.out.println("Autores encontrados:");
            for (Autor autor : autores) {
                System.out.println("Nombre: " + autor.getNombre() + ", Año de Nacimiento: " + autor.getAñoNacimiento() + ", Año de Muerte: " + autor.getAñoMuerte());
            }
        }
    }
}