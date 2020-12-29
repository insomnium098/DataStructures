Estructuras de Datos
====================

PROYECTO 2
------------------------------

### Fecha de entrega: martes 8 de diciembre, 2020

Fecha de entrega: viernes 8 de enero, 2020
Deben escribir un programa para graficar las estructuras de datos que se han
cubierto a lo largo del curso (hasta montículos mínimos), utilizando SVG.
SVG es un lenguaje descriptivo para gráficos escalares muy sencillo. Por
ejemplo, la siguiente imagen:

Decidir cómo deben verse las gráficas (cómo acomodar los vértices y cómo
conectarlos), es parte de lo que tienen que resolver para el proyecto. Pilas,
colas y el resto de las otras clases debería ser trivial. Los montículos mínimos
son árboles binarios y además arreglos.
El programa escribirá su salida en la salida estándar, y recibirá su entrada a
través de un nombre de archivo o de la entrada estándar (si no se especifica
ningún nombre de archivo en la línea de comandos). El formato del archivo es el
siguiente:

Los espacios (incluyendo tabuladores, saltos de línea y cualquier otro
carácter no imprimible) son ignorados excepto como separadores.
Si el programa encuentra una almohadilla (el símbolo #), se ignoran todos los
siguientes caracteres hasta el fin de línea.
Lo primero que debe encontrar el programa es el nombre de una de las clases
concretas permitidas.
Después del nombre de clase deben venir enteros (siempre enteros) que son
los elementos de la estructura.
En el caso de las gráficas, el número de elementos debe ser par, y cada par
de elementos es una arista. Si un par de elementos son iguales, esto
representa un vértice desconectado del resto de la gráfica.

Por ejemplo, el siguiente archivo:
ArbolRojinegro 1 2 3 4 5 6 7 8 9 10 11 12 13 14 5
describe el mismo árbol rojinegro que el siguiente archivo:
# Clase:
        ArbolRojinegro
    # Elementos:
    1 2 3 4
# Más elementos
5 6 7 8
                        # Todavía MÁS elementos
                        9 10 11 12
    # Los últimos elementos
    13 14 5