package com.franco.literalura.principal;

import com.franco.literalura.modelos.*;
import com.franco.literalura.service.ServiciosDeBusqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Scanner;

@Service
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    @Autowired
    private ServiciosDeBusqueda serviciosDeBusqueda;

    public void muestraElMenu(){
        var opcion = -1;
        while (opcion != 6) {
            System.out.println("""
                    ¡Bienvenido a nuestra biblioteca!
                    
                    1)Buscar libro por titulo
                    2)Mostrar lista de libros registrados
                    3)Mostrar lista de autores registrados
                    4)Mostrar lista de libros de autores vivos en un determinado año
                    5)Mostrar lista de libros por idioma
                    6)Salir de la aplicacion
                    """);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    serviciosDeBusqueda.buscarLibroPorTitulo();
                    break;
                case 2:
                    serviciosDeBusqueda.mostrarListaDeLibros();
                    break;
                case 3:
                    serviciosDeBusqueda.mostrarListaDeAutores();
                    break;
                case 4:
                    serviciosDeBusqueda.mostrarListaDeAutoresVivosEnDeterminadaFecha();
                    break;
                case 5:
                    serviciosDeBusqueda.mostrarListaDeLibrosPorIdioma();
                    break;
                case 6:
                    System.out.println("Saliendo de la aplicacion");
                    break;
                default:
                    break;
            }
        }
    }
}
