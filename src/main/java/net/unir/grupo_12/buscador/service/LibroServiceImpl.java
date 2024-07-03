package net.unir.grupo_12.buscador.service;

import net.unir.grupo_12.buscador.entity.Libro;
import net.unir.grupo_12.buscador.exception.NotFoundException;
import net.unir.grupo_12.buscador.repository.LibroRepository;
import net.unir.grupo_12.buscador.response.AggregationDetails;
import net.unir.grupo_12.buscador.response.LibrosQueryResponse;
import net.unir.grupo_12.buscador.utils.Consts;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository repository;
    private final ElasticsearchOperations elasticClient;

    public LibroServiceImpl(LibroRepository repository,
                            ElasticsearchOperations elasticClient) {
        this.repository = repository;
        this.elasticClient = elasticClient;
    }

    @Override
    public LibrosQueryResponse findAll(
            String titulo,
            String autor,
            String isbn,
            List<String> generos,
            LocalDate fechaInicial,
            LocalDate fechaFinal,
            Integer edicion,
            String editorial,
            Integer page
    ) {
        BoolQueryBuilder querySpec = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(titulo)) {
            querySpec.must(QueryBuilders.multiMatchQuery(titulo,
                            new String[]{"Titulo", "Titulo._2gram", "Titulo._3gram"})
                    .type(MultiMatchQueryBuilder.Type.BOOL_PREFIX));
        }

        if (!StringUtils.isEmpty(autor)) {
            querySpec.must(QueryBuilders.multiMatchQuery(autor,
                            new String[]{"Autor", "Autor._2gram", "Autor._3gram"})
                    .type(MultiMatchQueryBuilder.Type.BOOL_PREFIX));
        }

        if (!StringUtils.isEmpty(isbn)) {
            querySpec.must(QueryBuilders.matchQuery(Consts.FIELD_ISBN, isbn));
        }

        if (generos != null && !generos.isEmpty()) {
            generos.forEach(
                    genero -> querySpec.must(QueryBuilders.termQuery(Consts.FIELD_GENERO, genero))
            );
        }

        if (fechaInicial != null && fechaFinal != null) {
            querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_FECHA_PUBLICACION)
                    .gte(fechaInicial)
                    .lte(fechaFinal));
        }

        if (edicion != null) {
            querySpec.must(QueryBuilders.matchQuery(Consts.FIELD_EDICION, edicion));
        }

        if (!StringUtils.isEmpty(editorial)) {
            querySpec.must(QueryBuilders.matchQuery(Consts.FIELD_EDITORIAL, editorial));
        }

        if (!querySpec.hasClauses()) {
            querySpec.must(QueryBuilders.matchAllQuery());
        }

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(querySpec);

        nativeSearchQueryBuilder.addAggregation(AggregationBuilders
                .terms(Consts.AGG_KEY_TERM_GENERO)
                .field(Consts.FIELD_GENERO).size(10000));

        nativeSearchQueryBuilder.withMaxResults(5);

        nativeSearchQueryBuilder.withPageable(PageRequest.of(page, 5));

        Query query = nativeSearchQueryBuilder.build();
        SearchHits<Libro> result = elasticClient.search(query, Libro.class);

        return new LibrosQueryResponse(getResponseLibros(result), getResponseAggregations(result));
    }

    @Override
    public Libro findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el Libro con id: " + id));
    }

    @Override
    public Libro save(Libro libro) {
        String id = libro.getId() == null ? "-1" : libro.getId();
        Optional<Libro> optionalLibro = repository.findById(id);
        if (optionalLibro.isPresent()) {
            Libro model = optionalLibro.get();

            model.setIsbn(libro.getIsbn());
            model.setAutor(libro.getAutor());
            model.setTitulo(libro.getTitulo());
            model.setGenero(libro.getGenero());
            model.setPortada(libro.getPortada());
            model.setEdicion(libro.getEdicion());
            model.setEditorial(libro.getEditorial());
            model.setFechaPublicacion(libro.getFechaPublicacion());

            return repository.save(model);
        } else {
            return repository.save(libro);
        }
    }

    @Override
    public void deleteById(String id) {
        repository.delete(findById(id));
    }

    /**
     * Metodo que convierte los resultados de la busqueda en una lista de empleados.
     *
     * @param result Resultados de la busqueda.
     * @return Lista de empleados.
     */
    private List<Libro> getResponseLibros(SearchHits<Libro> result) {
        return result.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    /**
     * Metodo que convierte las agregaciones de la busqueda en una lista de detalles de agregaciones.
     * Se ha de tener en cuenta que el tipo de agregacion puede ser de tipo rango o de tipo termino.
     *
     * @param result Resultados de la busqueda.
     * @return Lista de detalles de agregaciones.
     */
    private Map<String, List<AggregationDetails>> getResponseAggregations(SearchHits<Libro> result) {

        //Mapa de detalles de agregaciones
        Map<String, List<AggregationDetails>> responseAggregations = new HashMap<>();

        //Recorremos las agregaciones
        if (result.hasAggregations()) {
            Map<String, Aggregation> aggs = result.getAggregations().asMap();

            //Recorremos las agregaciones
            aggs.forEach((key, value) -> {

                //Si no existe la clave en el mapa, la creamos
                if (!responseAggregations.containsKey(key)) {
                    responseAggregations.put(key, new LinkedList<>());
                }

                //Si la agregacion es de tipo termino, recorremos los buckets
                if (value instanceof ParsedStringTerms parsedStringTerms) {
                    parsedStringTerms.getBuckets().forEach(bucket -> {
                        responseAggregations.get(key)
                                .add(new AggregationDetails(bucket.getKey().toString(),
                                        (int) bucket.getDocCount()));
                    });
                }

                //Si la agregacion es de tipo rango, recorremos tambien los buckets
                if (value instanceof ParsedRange parsedRange) {
                    parsedRange.getBuckets().forEach(bucket -> {
                        responseAggregations.get(key)
                                .add(new AggregationDetails(bucket.getKeyAsString(),
                                        (int) bucket.getDocCount()));
                    });
                }
            });
        }
        return responseAggregations;
    }
}
