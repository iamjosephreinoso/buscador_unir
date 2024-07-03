package net.unir.grupo_12.buscador.service;

import net.unir.grupo_12.buscador.entity.Libro;
import net.unir.grupo_12.buscador.response.LibrosQueryResponse;

import java.time.LocalDate;
import java.util.List;

public interface LibroService {

    LibrosQueryResponse findAll(
            String titulo,
            String autor,
            String isbn,
            List<String> generos,
            LocalDate fechaInicial,
            LocalDate fechaFinal,
            Integer edicion,
            String editorial,
            Integer page
    );

    Libro findById(String id);

    Libro save(Libro libro);

    void deleteById(String id);

}
