package net.unir.grupo_12.buscador.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.unir.grupo_12.buscador.entity.Libro;
import net.unir.grupo_12.buscador.response.LibrosQueryResponse;
import net.unir.grupo_12.buscador.service.LibroService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "Endpoint de Libros")
public class LibroController {

    private final LibroService service;

    public LibroController(LibroService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public LibrosQueryResponse getAllLibros(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer edicion,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) List<String> generos,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFinal,
            @RequestParam(required = false, defaultValue = "0") Integer page
    ) {
        return service.findAll(
                titulo,
                autor,
                isbn,
                generos,
                fechaInicial,
                fechaFinal,
                edicion,
                editorial,
                page
        );
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Libro findById(@PathVariable String id) {
        return service.findById(id);
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


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }
}
