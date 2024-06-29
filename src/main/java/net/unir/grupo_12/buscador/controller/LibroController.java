package net.unir.grupo_12.buscador.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.unir.grupo_12.buscador.entity.Libro;
import net.unir.grupo_12.buscador.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "Endpoint de Libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public List<Libro> getAllLibros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) LocalDate fechaPublicacion,
            @RequestParam(required = false) Integer edicion,
            @RequestParam(required = false) String editorial
    ) {
        return libroService.getAllLibros(
                titulo, autor, isbn, fechaPublicacion, edicion, editorial
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id) {
        Libro libro = libroService.getLibroById(id);
        if (libro != null) {
            return ResponseEntity.ok(libro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Libro createLibro(@RequestBody Libro libro) {
        return libroService.saveLibro(libro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> updateLibro(@PathVariable Long id, @RequestBody Libro libroDetails) {
        Libro libro = libroService.getLibroById(id);
        if (libro != null) {
            libro.setTitulo(libroDetails.getTitulo());
            libro.setAutor(libroDetails.getAutor());
            libro.setIsbn(libroDetails.getIsbn());
            libro.setPortada(libro.getPortada());
            libro.setGenero(libroDetails.getGenero());
            libro.setFechaPublicacion(libroDetails.getFechaPublicacion());
            libro.setEdicion(libroDetails.getEdicion());
            libro.setEditorial(libroDetails.getEditorial());
            return ResponseEntity.ok(libroService.saveLibro(libro));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        if (libroService.getLibroById(id) != null) {
            libroService.deleteLibro(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
