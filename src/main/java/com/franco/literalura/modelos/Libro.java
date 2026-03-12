package com.franco.literalura.modelos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idiomas;
    private Double numeroDeDescargas;

    public Libro(DatosLibros datos) {
        this.id = null;
        this.titulo = datos.titulo();
        this.autor = new Autor(datos.autor().get(0));
        this.idiomas = datos.idiomas().get(0);
        this.numeroDeDescargas = datos.numeroDeDescargas();
    }

    @Override
    public String toString() {
        return "Titulo= " + titulo +
                autor +
                ", Idiomas='" + idiomas +
                ", Numero de descargas=" + numeroDeDescargas;
    }
}
