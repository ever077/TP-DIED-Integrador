# TP-DIED-Integrador

Falta:
[ok] 1) Ver porque no se actualiza la tabla al Eliminar un Libro y video.
		-> SOLUCION: EL GRAFO en el DAO estaba como "static" + Agregue el metodo
		   deleteRow(int induce) en los Tables Models de Libro y Video
2) Agregar el campo CALIFICACION en "nuevo libro" y "modificar libro" y Fecha actual
   al guardarse en el archivo (ya esta hacho para los videos).
   ** Ver si se puede cambiar el orden de los costos al realizar esto.
[ok] 3) ver videos cuando se cierra el programa y se abre de nuevo, cuando levanta los datos de
   videos.csv y campos que los pone en 0 y la fecha no la muestra.