package net.unir.grupo_12.buscador.utils;

public class Consts {

    private Consts() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FIELD_TITULO = "Titulo";
    public static final String FIELD_AUTOR = "Autor";
    public static final String FIELD_PORTADA = "Portada";
    public static final String FIELD_GENERO = "Genero";
    public static final String FIELD_ISBN = "ISBN";
    public static final String FIELD_FECHA_PUBLICACION = "FechaPublicacion";
    public static final String FIELD_EDICION = "Edicion";
    public static final String FIELD_EDITORIAL = "Editorial";

    public static final String AGG_KEY_TERM_GENERO = "generoValues";
}
