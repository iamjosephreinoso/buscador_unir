Para crear un índice con datos de prueba, sigue estos pasos:

1) Ejecutar, desde la consola de Bonsai.io la operación ``PUT /libros`` con el siguiente cuerpo:
```
{
   "mappings": {
      "properties": {
         "Titulo": {
            "type": "search_as_you_type"
         },
         "Autor": {
            "type": "search_as_you_type"
         },
         "Portada": {
            "type": "text"
         },
         "Genero": {
            "type": "keyword"
         },
         "ISBN": {
            "type": "text"
         },
         "FechaPublicacion": {
            "type": "date",
            "format": "yyyy-MM-dd"
         },
         "Edicion": {
            "type": "integer"
         },
         "Editorial": {
            "type": "text"
         }
      }
   }
}
```
2) Introducir datos de prueba. Utilizar el fichero [Lirbos_raw.json](./Lirbos_raw.json):

   (Para Unix)
    ```
    curl -XPUT '<<host_obtenido_de_bonsai>>/_bulk' --data-binary @Lirbos_raw.json -H 'Content-Type: application/json'
    ```

   (Para Windows)
    ```
    curl -XPUT "<<host_obtenido_de_bonsai>>/_bulk" --data-binary @Lirbos_raw.json -H "Content-Type: application/json"
    ```
   
3) Para verificar ejecutamos desde la consola de Bonsai.io la operación ``/libros/_count?pretty``