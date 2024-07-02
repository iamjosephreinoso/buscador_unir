package net.unir.grupo_12.buscador.entity;

import jakarta.persistence.Id;
import lombok.*;
import net.unir.grupo_12.buscador.utils.Consts;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "libros", createIndex = true)
public class Libro {

    @Id
    private String id;
    @Field(type = FieldType.Search_As_You_Type, name = Consts.FIELD_TITULO)
    private String titulo;
    @Field(type = FieldType.Text, name = Consts.FIELD_ISBN)
    private String isbn;
    @Field(type = FieldType.Integer, name = Consts.FIELD_EDICION)
    private int edicion;
    @Field(type = FieldType.Search_As_You_Type, name = Consts.FIELD_AUTOR)
    private String autor;
    @Field(type = FieldType.Keyword, name = Consts.FIELD_GENERO)
    private String genero;
    @Field(type = FieldType.Text, name = Consts.FIELD_PORTADA)
    private String portada;
    @Field(type = FieldType.Text, name = Consts.FIELD_EDITORIAL)
    private String editorial;
    @Field(type = FieldType.Date, name = Consts.FIELD_FECHA_PUBLICACION)
    private LocalDate fechaPublicacion;

}
