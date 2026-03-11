package com.franco.literalura.principal;

import com.franco.literalura.modelos.*;
import com.franco.literalura.repository.AutorRepository;
import com.franco.literalura.repository.LibroRepository;
import com.franco.literalura.service.ConsultaAPI;
import com.franco.literalura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private ConsultaAPI consultaAPI = new ConsultaAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private Scanner teclado = new Scanner(System.in);

    private final String URL = "https://gutendex.com/books/";

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository,AutorRepository autorRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        }
    public void muestraElMenu(){
        var json = consultaAPI.obtenerDatos(URL);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Libros.class);
        System.out.println(datos);
        var opcion = -1;
        while (opcion != 6) {
            System.out.println("""
                    ¡Bienvenido a nuestra biblioteca!
                    
                    1)Buscar libro por titulo
                    2)Mostrar lista de libros registrados
                    3)Mostrar lista de autores registrados
                    4)Mostrar lista de autores vivos en un determinado año
                    5)Mostrar lista de libros por idioma
                    6)Salir de la aplicacion
                    """);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarListaDeLibros();
                    break;
                case 3:
                    mostrarListaDeAutores();
                    break;
                case 4:
                    mostrarListaDeAutoresVivosEnDeterminadaFecha();
                    break;
                case 5:
                    mostrarListaDeLibrosPorIdioma();
                    break;
                case 6:
                    System.out.println("Saliendo de la aplicacion");
                    break;
                default:
                    break;
            }
        }
    }
    //BUSCA UN TITULO POR UNA PARTE DE SU TITULO
    public void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var titulo = teclado.nextLine();
        var json = consultaAPI.obtenerDatos(URL+"?search="+titulo.replace(" ","+"));
         var libro = conversor.obtenerDatos(json, Libros.class);
        Optional<DatosLibros> libroBuscado = libro.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(titulo.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()) {
            var libro1 = verificarLibro(libroBuscado.get());
            System.out.println(libro1);
        }else {
            System.out.println("Libro no encontrado");
        }
    }
    //MUESTRA LA LISTA DE LIBROS REGISTRADOS
    public void mostrarListaDeLibros(){
        List<Libro> listaDeLibros = libroRepository.findAll().stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .collect(Collectors.toList());
        listaDeLibros.forEach(System.out::println);
    }

    //MUESTRA LA LISTA DE AUTORES REGISTRADOS
    public void mostrarListaDeAutores(){
        List<Autor> listaDeAutores = autorRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .collect(Collectors.toList());
        listaDeAutores.forEach(System.out::println);
    }

    //MUESTRA LA LISTA DE LIBROS QUE ESTUVIERON VIVOS UN DETERMINADO AÑO
    public void mostrarListaDeAutoresVivosEnDeterminadaFecha(){
        System.out.println("Ingrese el año que desee buscar");
        var anio = teclado.nextInt();
    List<Libro> listaDeLibrosAutoresVivos = libroRepository.librosDeAutoresVivosEn(anio);
    listaDeLibrosAutoresVivos.forEach(System.out::println);
    }
    //MUESTRA LA LISTA DE LIBROS EN UN DETERMINADO IDIOMA
    public void mostrarListaDeLibrosPorIdioma(){
        System.out.println("""
                ingrese idioma a buscar:
                1) es
                2) en
                3) pr
                4) fr
                """);
        var idioma = teclado.nextLine();
        List<Libro> mostrarListaPorIdioma = libroRepository.findByIdiomasIgnoreCase(idioma);
        mostrarListaPorIdioma.forEach(System.out::println);
    }

    //VERIFICA SI YA EXISTE EL AUTOR EN LA BD, SI NO EXISTE CREA UNO
    public Autor verificaAutor(DatosAutor datos) {
        return autorRepository.findByNombre(datos.nombre()).orElseGet(() -> {
                var autor = new Autor(datos);
                return autorRepository.save(autor);
        });
    }
    //VERIFICA SI YA EXISTE EL LIBRO EN LA BASE DE DATOS, SI NO EXISTE CREA UNO
    public Libro verificarLibro(DatosLibros datos) {
        Optional<Libro> libroEncontrado = libroRepository.findByTitulo(datos.titulo());
        if (libroEncontrado.isPresent()) {
            return libroEncontrado.get();
        }
        var autor = verificaAutor(datos.autor().get(0));
        var libro = new Libro(null,datos.titulo(),autor,datos.idiomas().get(0),datos.numeroDeDescargas());
        return libroRepository.save(libro);
    }



}
