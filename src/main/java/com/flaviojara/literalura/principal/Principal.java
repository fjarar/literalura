package com.flaviojara.literalura.principal;

import com.flaviojara.literalura.model.*;
import com.flaviojara.literalura.repository.AutorRepository;
import com.flaviojara.literalura.repository.LibroRepository;
import com.flaviojara.literalura.servicios.ConsumoAPI;
import com.flaviojara.literalura.servicios.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private final ConvierteDatos conversor = new ConvierteDatos();

    private List<DatosLibros> datosLibros;
    private List<Libro> libros;
    private List<Autor> autores;
    private Optional<Libro> libroBuscado;

    private final LibroRepository repositorio;
    private final AutorRepository autorRepository;

    @Autowired
    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepository = autorRepository;
    }

    public void mostrarDatos() {
        System.out.println(consumoAPI.obtenerDatos(URL_BASE));
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ---------------------------------
                    1 - Buscar libro por titulo
                    2 - Mostrar lista de autores registrados
                    3 - Mostrar libros de libros registrados
                    4 - Listar autores vivos en determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    ---------------------------------
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarAutoresRegistrados();
                    break;
                case 3:
                    listarLibrosRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnCiertaFecha();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    public String mostrarIdiomas() {
        var idioma = "";
        var menu = """
                ---------------------------------
                Ingrese el idioma para buscar los libros: 
                
                1 - [es] -> Español
                2 - [en] -> Inglés
                3 - [fr] -> Frances
                4 - [pr] -> Portugués
                ---------------------------------
                """;
        System.out.println(menu);
        return teclado.nextLine().replace(" ", "").toLowerCase();
    }
        /*switch (idioma) {
            case "es":
                buscarLibroWeb();
                break;
            case "en":
                listarAutoresRegistrados();
                break;
            case "fr":
                listarLibrosRegistrados();
                break;
            case "pr":
                listarAutoresVivosEnCiertaFecha();
                break;
            default:
                System.out.println("Opción inválida");
        }

    }*/

    private void listarAutoresRegistrados() {
        autores = autorRepository.findAll();

        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void listarLibrosRegistrados() {
        libros = repositorio.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getIdiomas))
                .forEach(System.out::println);
    }

    private void listarAutoresVivosEnCiertaFecha() {
        System.out.println("Ingrese el año para mostrar autores vivos en esa fecha");
        var year = teclado.nextInt();
        teclado.nextLine();
        autores = autorRepository.findAliveInYear(year);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año" + year);
        } else {
            autores.stream()
                    .sorted(Comparator.comparing(Autor::getNombre))
                    .forEach(System.out::println);
        }


    }

    private void listarLibrosPorIdioma() {
        /*mostrarIdiomas();
        var idioma = teclado.nextLine();*/
        var idioma = mostrarIdiomas();
        libros = repositorio.findByIdiomasContainingIgnoreCase(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con ese idioma almacenados en la base de datos");
        } else {
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(System.out::println);
        }

    }

    private DatosLibros getDatosLibro() {
        try {
            System.out.println("Escriba el nombre del libro que desea buscar");
            var tituloLibro = teclado.nextLine().trim();

            if (tituloLibro.isEmpty()) {
                System.out.println("El título del libro no puede estar vacío.");
                return null;
            }

            var url = URL_BASE + "?search=" + tituloLibro.replace(" ", "+");
            var json = consumoAPI.obtenerDatos(url);

            if (json == null || json.isEmpty()) {
                System.out.println("No se pudo obtener una respuesta válida de la API.");
                return null;
            }

            var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

            if (datosBusqueda == null || datosBusqueda.libros() == null || datosBusqueda.libros().isEmpty()) {
                System.out.println("No se encontraron datos para el libro buscado. Regresando al menú principal...");
                return null;
            }

            Optional<DatosLibros> libroBuscado = datosBusqueda.libros().stream()
                    .filter(l -> l.titulo() != null && l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                    .findFirst();

            /*if (libroBuscado.isEmpty()) {
                System.out.println("No se encontró un libro que coincida con el título proporcionado. Regresando al menú principal...");
                return null;
            }*/

            return libroBuscado.orElse(null);
        } catch (Exception e){
            System.out.println("Ocurrió un error al buscar el libro. Por favor, intente nuevamente.");
            return null;
        }


    }

    private void buscarLibroWeb() {
        DatosLibros libros = getDatosLibro();
        if (libros == null) {
            System.out.println("Regresando al menú principal.");
            return;
        } else {
            System.out.println("Libro encontrado: " + libros);
        }

        String idioma = libros.idiomas().isEmpty() ? "Idioma desconocido" : libros.idiomas().get(0);
        Double numeroDeDescargas = libros.numeroDeDescargas();

        for (DatosAutor datosAutor : libros.autores()) {
            Autor autor = autorRepository.findByNombreIgnoreCase(datosAutor.nombre())
                    .orElseGet(() -> {
                        Autor nuevoAutor = new Autor();
                        nuevoAutor.setNombre(datosAutor.nombre());
                        nuevoAutor.setFechaNacimiento(datosAutor.fechaNacimiento());
                        nuevoAutor.setFechaMuerte(datosAutor.fechaMuerte());
                        autorRepository.save(nuevoAutor);
                        return nuevoAutor;
                    });
            Libro libro = new Libro();
            libro.setTitulo(libros.titulo());
            libro.setIdiomas(idioma);
            libro.setNumeroDeDescargas(numeroDeDescargas);
            libro.setAutor(autor);

            repositorio.save(libro);

            System.out.println("Libro guardado: " + libros);
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var tituloLibro = teclado.nextLine();
        libroBuscado = repositorio.findByTituloContainingIgnoreCase(tituloLibro);

        if (libroBuscado.isPresent()) {
            System.out.println("El libro buscado es: " + libroBuscado);
        } else {
            System.out.println("El libro buscado no ha sido encontrado.");
        }
    }
}
