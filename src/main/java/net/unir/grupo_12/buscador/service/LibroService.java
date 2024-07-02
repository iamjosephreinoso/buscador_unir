package net.unir.grupo_12.buscador.service;

import net.unir.grupo_12.buscador.entity.Libro;

import java.util.List;

public interface LibroService {

    List<Libro> findAll();

    Libro findById(String id);

    Libro save(Libro libro);

    void deleteById(String id);

}
