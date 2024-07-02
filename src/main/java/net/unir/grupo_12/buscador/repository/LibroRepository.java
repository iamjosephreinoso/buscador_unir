package net.unir.grupo_12.buscador.repository;

import net.unir.grupo_12.buscador.entity.Libro;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends ElasticsearchRepository<Libro, String> {

}
