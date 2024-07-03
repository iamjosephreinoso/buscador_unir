package net.unir.grupo_12.buscador.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.unir.grupo_12.buscador.entity.Libro;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibrosQueryResponse {

    private List<Libro> libros;
    private Map<String, List<AggregationDetails>> aggs;

}
