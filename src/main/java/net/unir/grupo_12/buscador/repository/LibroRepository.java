package net.unir.grupo_12.buscador.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import net.unir.grupo_12.buscador.entity.Libro;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long>  {

	List<Libro> findByTitulo(String titulo);
    List<Libro> findByAutor(String autor);
    List<Libro> findByIsbn(String isbn);
    List<Libro> findByAnio(int anio);
    List<Libro> findByEdicion(int edicion);
    List<Libro> findByEditorial(String editorial);
}
