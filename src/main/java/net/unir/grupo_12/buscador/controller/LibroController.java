package net.unir.grupo_12.buscador.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.unir.grupo_12.buscador.entity.Libro;
import net.unir.grupo_12.buscador.service.LibroService;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
	
	@Autowired
    private LibroService libroService;

    @GetMapping
    public List<Libro> getAllLibros() {
        return libroService.getAllLibros();
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
            libro.setAnio(libroDetails.getAnio());
            libro.setPublicacion(libroDetails.getPublicacion());
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

    @GetMapping("/search")
    public List<Libro> searchLibros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer anio,
            @RequestParam(required = false) Integer publicacion,
            @RequestParam(required = false) String editorial) {
        if (titulo != null) {
            return libroService.searchByTitulo(titulo);
        } else if (autor != null) {
            return libroService.searchByAutor(autor);
        } else if (isbn != null) {
            return libroService.searchByIsbn(isbn);
        } else if (anio != null) {
            return libroService.searchByAnio(anio);
        } else if (publicacion != null) {
            return libroService.searchByPublicacion(publicacion);
        } else if (editorial != null) {
            return libroService.searchByEditorial(editorial);
        } else {
            return libroService.getAllLibros();
        }
    }

}
