package net.unir.grupo_12.buscador.service;

import net.unir.grupo_12.buscador.entity.Libro;
import net.unir.grupo_12.buscador.exception.NotFoundException;
import net.unir.grupo_12.buscador.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository repository;

    public LibroServiceImpl(LibroRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Libro> findAll() {
        return (List<Libro>) repository.findAll();
    }

    @Override
    public Libro findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el Libro con id: " + id));
    }

    @Override
    public Libro save(Libro libro) {
        Optional<Libro> optionalLibro = repository.findById(libro.getId());
        if (optionalLibro.isPresent()) {
            Libro model = optionalLibro.get();
            model.setTitulo(libro.getTitulo());
            model.setIsbn(libro.getIsbn());
            model.setGenero(libro.getGenero());
            model.setEditorial(libro.getEditorial());
            model.setFechaPublicacion(libro.getFechaPublicacion());
            model.setPortada(libro.getPortada());
            model.setAutor(libro.getAutor());
            return repository.save(model);
        } else {
            return repository.save(libro);
        }
    }

    @Override
    public void deleteById(String id) {
        repository.delete(findById(id));
    }
}
