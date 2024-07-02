package net.unir.grupo_12.buscador.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.unir.grupo_12.buscador.entity.Libro;
import net.unir.grupo_12.buscador.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "Endpoint de Libros")
public class LibroController {

    @Autowired
    private LibroService service;

    @GetMapping
    public List<Libro> getAllLibros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) LocalDate fechaPublicacion,
            @RequestParam(required = false) Integer edicion,
            @RequestParam(required = false) String editorial
    ) {
        return service.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Libro> findById(@PathVariable String id) {
        Libro libro = service.findById(id);
        if (libro != null) {
            return ResponseEntity.ok(libro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Libro libro) {
        service.save(libro);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@NotNull @PathVariable String id, @RequestBody Libro libro) {
        service.save(Libro.builder()
                .id(id)
                .titulo(libro.getTitulo())
                .autor(libro.getAutor())
                .isbn(libro.getIsbn())
                .fechaPublicacion(libro.getFechaPublicacion())
                .editorial(libro.getEditorial())
                .edicion(libro.getEdicion())
                .portada(libro.getPortada())
                .genero(libro.getGenero())
                .build());
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }
}
