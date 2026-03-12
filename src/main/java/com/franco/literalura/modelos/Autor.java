package com.franco.literalura.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;
    @OneToMany(mappedBy = "autor",fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(DatosAutor datos){
        this.id = null;
        this.nombre = datos.nombre();
        this.fechaDeNacimiento = datos.fechaDeNacimiento();
        this.fechaDeFallecimiento = datos.fechaDeFallecimiento();
    }

    @Override
    public String toString() {
        return " Autor= " + nombre +
                ", Fecha de nacimiento= " + fechaDeNacimiento +
                ", Fecha de fallecimiento= " + fechaDeFallecimiento;
    }
}

