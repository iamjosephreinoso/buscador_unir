package net.unir.grupo_12.buscador.service;

import net.unir.grupo_12.buscador.entity.Libro;
import net.unir.grupo_12.buscador.repository.LibroRepository;
import net.unir.grupo_12.buscador.utils.SearchCriteria;
import net.unir.grupo_12.buscador.utils.SearchOperation;
import net.unir.grupo_12.buscador.utils.SearchStatement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> getAllLibros(
            String titulo,
            String autor,
            String isbn,
            LocalDate fechaPublicacion,
            Integer edicion,
            String editorial
    ) {
        SearchCriteria<Libro> spec = new SearchCriteria<>();
        if (StringUtils.isNotBlank(titulo)) {
            spec.add(new SearchStatement("titulo", titulo, SearchOperation.MATCH));
        }
        if (StringUtils.isNotBlank(autor)) {
            spec.add(new SearchStatement("autor", autor, SearchOperation.MATCH));
        }
        if (StringUtils.isNotBlank(isbn)) {
            spec.add(new SearchStatement("isbn", isbn, SearchOperation.MATCH));
        }
        if (fechaPublicacion != null) {
            spec.add(new SearchStatement("fechaPublicacion", fechaPublicacion, SearchOperation.EQUAL));
        }
        if (edicion != null) {
            spec.add(new SearchStatement("edicion", edicion, SearchOperation.EQUAL));
        }
        if (StringUtils.isNotBlank(editorial)) {
            spec.add(new SearchStatement("editorial", editorial, SearchOperation.EQUAL));
        }

        return libroRepository.findAll(spec);
    }

    public Libro getLibroById(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    public Libro saveLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }
}
