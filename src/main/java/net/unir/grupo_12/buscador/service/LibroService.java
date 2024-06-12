package net.unir.grupo_12.buscador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.unir.grupo_12.buscador.entity.Libro;
import net.unir.grupo_12.buscador.repository.LibroRepository;

@Service
public class LibroService {
	
	@Autowired
    private LibroRepository libroRepository;

    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
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
    public List<Libro> searchByTitulo(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }
    public List<Libro> searchByAutor(String autor) {
        return libroRepository.findByAutor(autor);
    }
    public List<Libro> searchByIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn);
    }
    public List<Libro> searchByAnio(int anio){
    	return libroRepository.findByAnio(anio);
    }
    public List<Libro> searchByPublicacion(int publicacion) {
        return libroRepository.findByPublicacion(publicacion);
    }
    public List<Libro> searchByEditorial(String editorial) {
        return libroRepository.findByEditorial(editorial);
    }
}
