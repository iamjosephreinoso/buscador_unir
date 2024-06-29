package net.unir.grupo_12.buscador.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 20, nullable = false)
    private String isbn;
    private int edicion;
    private String autor;
    private String titulo;
    private String genero;
    private String portada;
    private String editorial;
    private LocalDate fechaPublicacion;

}
