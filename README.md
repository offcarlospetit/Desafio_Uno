# Carlos Petit
# Desafío 1: Periodos perdidos

El desafío consiste en lo siguiente:

-	Existe un servicio REST llamado Generador De Datos o GDD.
	-	El servicio responde con una lista de fechas generadas aleatoriamente. Estas fechas se encuentran en un lapso definidos por dos valores: fechaCreacion y fechaFin.
	-	Cada fecha generada corresponde al primer día de un mes.
	-	La respuesta contienen un máximo de 100 fechas. 
	-	El servicio no entrega todas las fechas dentro del periodo, omite algunas de forma también aleatoria.
-	El objetivo de este ejercicio es determinar cuáles son los periodos que faltan.


- Se desarrollo un menu donde se puede elegir el nivel que se desea probar. 



Este es un ejemplo de la respuesta que entrega este servicio:

```json
{
    "id": 6,
    "fechaCreacion": "1968-08-01",
    "fechaFin": "1971-06-01",
    "fechas": [
      "1969-03-01",
      "1969-05-01",
      "1969-09-01",
      "1971-05-01"]
}
```

Acá se puede apreciar que el servicio generó fechas entre el 1 de agosto de 1968 y el 1 de junio de 1971. Sólo se generaron 4 fechas en este caso. 
De acuerdo a esto, faltarían 5 fechas de 1968, 9 fechas de 1969 y 5 fechas de 1971.

He desarrollado dos soluciones posibles para el desafio. 
Me centre para realizar el nivel 1 y 2 del desafio

## Nivel 1: 
	Cree un programa que recibe, a través de la entrada estándar, un archivo en formato Json con la estructura de la respuesta de servicio (como el ejemplo de arriba) y que entrega a través de la salida estándar, como respuesta, un archivo Json con las fechas faltantes.
Ejemplo:
	Se entrega un archivo con este contenido: este es el formato que debe tener el archivo para que el programa pueda realizar el calculo.
	
```json
{
    "id": 6,
    "fechaCreacion": "1969-03-01",
    "fechaFin": "1970-01-01",
    "fechas": [
      "1969-03-01",
      "1969-05-01",
      "1969-09-01",
      "1970-01-01"]
}
```

El programa responde con archivo con esta estructura:
	
```json
{
    "id": 6,
    "fechaCreacion": "1969-03-01",
    "fechaFin": "1970-01-01",
    "fechasFaltantes": [
      "1969-04-01",
      "1969-06-01",
      "1969-07-01",
      "1969-08-01",
      "1969-10-01",
      "1969-11-01",
      "1969-12-01"]
}
```
 
El programa se ejecutar de la siguiente manera: la ruta o nombre del archivo de entrada y la ruta o nombre del archivo de salida separados por un espacio en blanco. 
	$ < nombre_archivo_entrada > < nombre_archivo_salida >

## Nivel 2:

Se creo un programa que invoca al servicio REST GDD y se escribe como salida un archivo con las fechas, los periodos recibidos y la lista de periodos faltantes.
Ejemplo:

```
INVOCACION:
	$ mi-solucion
SALIDA (un archivo con el siguiente contenido) :
	  fecha creación: 2018-10-01
         fecha fin: 2019-04-01
         fechas recibidas: 2018-10-01, 2018-12-01, 2019-01-01, 2019-04-01
	    fechas faltantes: 2018-11-01, 2019-02-01, 2019-03-01
```
Para la realizacion del ejercicio me apoye de la libreria gson, la cual se debe añadir al momento de reazaliar configuracion del proyecto luego de descargarlo del repositorio. 



