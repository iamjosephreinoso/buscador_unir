package net.unir.grupo_12.buscador.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import net.unir.grupo_12.buscador.entity.Libro;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long>, JpaSpecificationExecutor<Libro> {
}
