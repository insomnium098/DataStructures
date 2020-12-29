package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;


/**
 * Proyecto 2.
 */
public class Proyecto2 {


    /* Imprime el uso del programa y lo termina. */

    /*

    private static void uso() {
        System.err.println("Uso: java -jar practica7.jar N");
        System.exit(1);
    }

     */

    public static void main(String[] args) {


        ///Primero leer el archivo y procesar la linea

        String linea = null;
        String nombreArchivo;
        String estructura;





        int num_argumentos = args.length;

        if(num_argumentos == 0){
            nombreArchivo = "terminal_data";

        } else {
            nombreArchivo = args[0];
        }





        try {
            linea = leeLinea(nombreArchivo);
        } catch (Exception ex) {

        }

        ///Determinar primero que tipo de estructura se esta leyendo
        //La primer palabra debe de ser la estructura
        int index = linea.indexOf(' ');
        estructura = linea.substring(0,index);
        //Obtenemos los elementos de la estructura
        String elementos = linea.substring(index, linea.length());
        elementos = elementos.trim();

        ///Se hace array con los elementos para verificar que sean integers

        String[] elementos_split = elementos.split("\\s+");


        for ( int i = 0; i < elementos_split.length; i++){
            try {
                int N = Integer.parseInt(elementos_split[i]);
            } catch (Exception ex){
                System.out.println("Los elementos de la estructura no son enteros validos");
                System.exit(1);

            }

        }



        switch(estructura){
            case "Lista":
                //System.out.println("La estructura es una Lista");
                Lista<Integer> lista = new Lista<>();
                for (String s : elementos_split){
                    lista.agrega(Integer.parseInt(s));
                }

                graficaLista(lista);
                //System.out.println(lista.toString());

                break;

            case "Pila":
                Pila<Integer> pila = new Pila<>();
                for (String s : elementos_split){
                    pila.mete(Integer.parseInt(s));
                }

                graficaMeteSaca(pila);

                break;

            case "Cola":
                Cola<Integer> cola = new Cola<>();
                for (String s : elementos_split){
                    cola.mete(Integer.parseInt(s));
                }

                graficaMeteSaca(cola);
                break;

            case "ArbolBinarioCompleto":
                ArbolBinarioCompleto<Integer> arbol = new ArbolBinarioCompleto<>();
                for (String s : elementos_split){
                    arbol.agrega(Integer.parseInt(s));
                }
                graficaArbolBFS(arbol,"ArbolBinarioCompleto");

                break;

            case "ArbolBinarioOrdenado":
                System.out.println("La estructura es un Arbol Binario Ordenado");
                break;

            case "ArbolRojinegro":
                //System.out.println("La estructura es un Arbol Rojinegro");
                ArbolRojinegro<Integer> arbolRojinegro = new ArbolRojinegro<>();
                for (String s : elementos_split){
                    arbolRojinegro.agrega(Integer.parseInt(s));
                }
                graficaArbolBFS(arbolRojinegro,"ArbolRojinegro");

                //ArbolRojinegro.VerticeRojinegro vert = (ArbolRojinegro.VerticeRojinegro)arbolRojinegro.raiz;

                //System.out.println(vert.toString());
                //System.out.println(vert.elemento);
                //System.out.println(vert.color);
                break;

            case "ArbolAVL":
                System.out.println("La estructura es un Arbol AVL");
                break;

            case "Arreglos":
                //System.out.println("La estructura es un Arreglo");
                graficaArreglo(elementos_split);

                break;

            case "Grafica":
                System.out.println("La estructura es una Grafica");
                break;

            case "MonticuloMinimo":
                System.out.println("La estructura es un Monticulo minimo");
                break;

            case "MonticuloArreglo":
                System.out.println("La estructura es un Monticulo Arreglo");
                break;


            default:
                System.out.println("La estructura ingresada no es reconocida");
                System.exit(1);
        }



        /*

        for (String s : elementos_split){
            System.out.println(s);
        }

         */

        //System.out.println(elementos_split.toString());



        //System.out.println(linea);
        //System.out.println(estructura);



        /*
        if (args.length != 1)
            uso();

        int N = -1;
        try {
            N = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            uso();
        }

        if (N < 1)
            uso();

         */

    }

    /*
    Lee el archivo, lo procesa y devuelve una sola linea con la estructura y sus elementos
     */

    public static String leeLinea(String nombreArchivo) throws IOException {
        String estructura = null;
        String linea;
        StringBuilder result = new StringBuilder();
        BufferedReader br = null;
        if (nombreArchivo.equals("terminal_data")){
             br = new BufferedReader(new InputStreamReader(System.in));

        } else {
             br = new BufferedReader(new FileReader(nombreArchivo));
        }

            while ((linea = br.readLine()) != null){
                if(linea.startsWith("#")){
                    continue;
                } else {

                    //Revisar si la linea tiene una almohadilla
                    int indexAlmohadilla = linea.indexOf("#");
                    if (indexAlmohadilla != -1){
                        linea = linea.substring(0,indexAlmohadilla);

                    }
                    result.append(" ");
                    result.append(linea);

                }
            }

        estructura = result.toString();
        ///Limpiar el string de tabulaciones y espacios
        estructura = estructura.trim();
        estructura = estructura.replaceAll("[\\n]", " ");
        estructura = estructura.replaceAll("\\s+", " ");


        return estructura;
    }

    /*
    Metodo Que grafica un arbolbinario basado en BFS por vertices

     */

    public static void graficaArbolBFS (ArbolBinario<Integer> arbol, String tipo_arbol){
        //arbol.bfs(vertice -> System.out.println(vertice.get()));
        //System.out.println();

        //System.out.println(arbol.toString());


        ///Definimos los parametros iniciales
        //Las X se mueven en porcentajes
        int x_Circulo_inicial = 50;
        int y_Circulo_inicial = 40;

        //Definimos las constantes que variaran las X y Y cada que se cambie de nivel



        graficaHeaderSVG();




        //Inicializamos profundidad y x
        int profundidad = 0;
        int aux_profundidad;
        int contadorY = y_Circulo_inicial;



        ////Hacemos arrays para los Padres


        //Hacemos un array con el nombre
        Integer [] arrayNombresPadres = new Integer[(int)Math.pow(2,profundidad)];
        //Hacemos un array con las coordenadas del indice
        Double [] arrayCoordenadasPadres = new Double[(int)Math.pow(2,profundidad)];

        ///Contador para recorrer el numero de elementos
        int contadorElementos = 0;

        ///Hacemos arrays para los elementos
        //Hacemos un array con el nombre
        Integer [] arrayNombresElementos = new Integer[(int)Math.pow(2,profundidad)];
        //Hacemos un array con las coordenadas del indice
        Double [] arrayCoordenadasElementos = new Double[(int)Math.pow(2,profundidad)];



        /////////RECORRER POR BFS
        Cola<VerticeArbolBinario<Integer>> cola = new Cola<>();

        cola.mete(arbol.raiz);
        while (!cola.esVacia()) {
            VerticeArbolBinario<Integer> vertice = cola.saca();
            Integer nombreElemento = vertice.get();//Integer.parseInt(vertice.toString());
            String color = "white";


            /*

            ///Caso Rojinegro
            if(tipo_arbol == "ArbolRojinegro"){
                ArbolRojinegro.VerticeRojinegro verticeRojinegro = (ArbolRojinegro.VerticeRojinegro)vertice;
                nombreElemento = Integer.parseInt(verticeRojinegro.elemento.toString());
                color = verticeRojinegro.color.toString();
            } else {
                nombreElemento = Integer.parseInt(vertice.toString());
                color = "white";

            }

             */




            ////AQUI HACER EL PROCESO
            ///Primero verificar si la profundidad cambio


            //Si cambio reiniciamos hacemos los arrays de elementos como los padres
            ///Y reiniciamos los de elementos
            if(profundidad != vertice.profundidad()){

                profundidad = vertice.profundidad();
                contadorY = contadorY + 70;


                arrayNombresPadres = arrayNombresElementos;
                arrayCoordenadasPadres = arrayCoordenadasElementos;


                arrayNombresElementos = new Integer[(int)Math.pow(2,profundidad)];
                arrayCoordenadasElementos = new Double[(int)Math.pow(2,profundidad)];
                contadorElementos = 0;



            }



            ////Primer caso donde el elemento es la raiz
            if (!vertice.hayPadre()){


                ///Despues llenamos los arrays de ELEMENTOS
                arrayNombresElementos[contadorElementos] = nombreElemento;//vertice.get();

                ///Aqui va la funcion que calcula la coordenada, como es raiz será de 50
                arrayCoordenadasElementos [contadorElementos] = Double.valueOf(50);

                ///Despues llenamos los arrays de PADRES
                arrayNombresPadres[contadorElementos] = nombreElemento;//vertice.get();

                ///Aqui va la funcion que calcula la coordenada, como es raiz será de 50
                arrayCoordenadasPadres [contadorElementos] = Double.valueOf(50);



                circuloSVG(Double.valueOf(50),contadorY,nombreElemento.toString(),color);


            } else {

                ///Buscamos las coordenadas del padre
                ///Buscar el padre en array

                /*

                Integer nombrePadre;

                if(tipo_arbol == "ArbolRojinegro"){
                    ArbolRojinegro.VerticeRojinegro verticeRojinegroPadre = (ArbolRojinegro.VerticeRojinegro)vertice.padre();
                    nombrePadre = Integer.parseInt(verticeRojinegroPadre.elemento.toString());
                } else {
                    nombrePadre = Integer.parseInt(vertice.padre().toString());

                }

                 */

                Integer nombrePadre = Integer.parseInt(vertice.padre().toString());



                int index_padre = Arreglos.busquedaBinaria(arrayNombresPadres,nombrePadre);
                //Se extraen sus coordenadas
                double coordPadre = arrayCoordenadasPadres[index_padre];

                double coordElemento;





                ///Vemos si el elemento es izquierdo o derecho y calculamos su coordenada
                if(esIzquierdo(vertice)){
                    coordElemento = calculaCoordenadas(coordPadre,"Izquierdo",profundidad);

                } else {
                    coordElemento = calculaCoordenadas(coordPadre,"Derecho",profundidad);;

                }



                arrayNombresElementos[contadorElementos] = vertice.get();
                arrayCoordenadasElementos [contadorElementos] = coordElemento;
                contadorElementos++;


                lineaSVG(coordPadre,contadorY-70,coordElemento,contadorY);



                circuloSVG(coordElemento,contadorY,nombreElemento.toString(),color);


            }






            //////AQUI TERMINA EL PROCESO

            if(vertice.hayIzquierdo()){
                cola.mete(vertice.izquierdo());
            }

            if(vertice.hayDerecho()){
                cola.mete(vertice.derecho());
            }


        }




        //////////

        graficaFinalSVG();



    }



    /*
    Metodo que recibe un Arbol binario y lo grafica
    Funciona cuando no se tienen valores repetidos
    ///Se deprecara por la version BFS
     */

    public static void graficaArbolBinario(ArbolBinario<Integer> arbol){

        System.out.println(arbol.toString());


        ///Definimos los parametros iniciales
        //Las X se mueven en porcentajes
        int x_Circulo_inicial = 50;
        int y_Circulo_inicial = 40;

        //Definimos las constantes que variaran las X y Y cada que se cambie de nivel



        //graficaHeaderSVG();
        ;



        //Inicializamos profundidad y x
        int profundidad = 0;
        int aux_profundidad;
        int contadorX = x_Circulo_inicial;
        int contadorY = y_Circulo_inicial;



        ////Hacemos arrays para los Padres


        //Hacemos un array con el nombre
        Integer [] arrayNombresPadres = new Integer[(int)Math.pow(2,profundidad)];
        //Hacemos un array con las coordenadas del indice
        Integer [] arrayCoordenadasPadres = new Integer[(int)Math.pow(2,profundidad)];

        ///Contador para recorrer el numero de elementos
        int contadorElementos = 0;

        ///Hacemos arrays para los elementos
        //Hacemos un array con el nombre
        Integer [] arrayNombresElementos = new Integer[(int)Math.pow(2,profundidad)];
        //Hacemos un array con las coordenadas del indice
        Integer [] arrayCoordenadasElementos = new Integer[(int)Math.pow(2,profundidad)];





        for (Integer i : arbol){





            VerticeArbolBinario<Integer> vertice = arbol.busca(i);


            ///Primero verificar si la profundidad cambio


            //Si cambio reiniciamos hacemos los arrays de elementos como los padres
            ///Y reiniciamos los de elementos
            if(profundidad != vertice.profundidad()){
                System.out.println("La profundidad cambio");


                System.out.println("Antes:");
                System.out.println("El contenido de arrayNombresElementos es:");


                for (int y = 0; y < arrayNombresElementos.length; y++){
                    System.out.println(arrayNombresElementos[y]);
                }



                profundidad = vertice.profundidad();


                arrayNombresPadres = arrayNombresElementos;
                arrayCoordenadasPadres = arrayCoordenadasElementos;


                arrayNombresElementos = new Integer[(int)Math.pow(2,profundidad)];
                arrayCoordenadasElementos = new Integer[(int)Math.pow(2,profundidad)];
                contadorElementos = 0;




                System.out.println("DESPUES");

                System.out.println("El contenido de arrayNombresElementos es:");


                for (int y = 0; y < arrayNombresElementos.length; y++){
                    System.out.println(arrayNombresElementos[y]);
                }

            } else {
                System.out.println("La profundidad no cambio");
            }




            ////Primer caso donde el elemento es la raiz
            if (!vertice.hayPadre()){
                System.out.println(i);
                System.out.println("Es la raiz");
                System.out.println("La profundidad es");
                System.out.println(profundidad);

                ///Despues llenamos los arrays
                arrayNombresElementos[contadorElementos] = i;

                ///Aqui va la funcion que calcula la coordenada
                arrayCoordenadasElementos [contadorElementos] = (contadorElementos + 1)* 50;

                ///Despues llenamos los arrays
                arrayNombresPadres[contadorElementos] = i;

                ///Aqui va la funcion que calcula la coordenada
                arrayCoordenadasPadres [contadorElementos] = (contadorElementos + 1)* 50;







            } else {

                ///Buscamos las coordenadas del padre
                ///Buscar el padre en array

                for (int x = 0; x < arrayNombresPadres.length; x++){
                    System.out.println(arrayNombresPadres[x]);
                }

                System.out.println("El i es;");
                System.out.println(i);

                Integer nombrePadre = Integer.parseInt(vertice.padre().toString());
                System.out.println("El padre de I es;");
                System.out.println(nombrePadre);






                int index_padre = Arreglos.busquedaBinaria(arrayNombresPadres,nombrePadre);
                //Se extraen sus coordenadas
                int coordPadre = arrayCoordenadasPadres[index_padre];

                System.out.println("Las coordenadas del padre son");
                System.out.println(coordPadre);
                int coordElemento;





                ///Vemos si el elemento es izquierdo o derecho y calculamos su coordenada
                if(esIzquierdo(vertice)){
                    coordElemento = coordPadre - 10 ;

                } else {
                    coordElemento = coordPadre + 20;

                }



                //coordElemento = i+50;

                ///Llenamos el array de elementos con su coordenada


                System.out.println("El contador elementos es");
                System.out.println(contadorElementos);


                arrayNombresElementos[contadorElementos] = i;
                arrayCoordenadasElementos [contadorElementos] = coordElemento;
                contadorElementos++;



            }





            ////////////











            //System.out.println(arrayNombres[0]);
            //System.out.println(arrayCoordenadas [0]);


            /*







            //Caso donde es el primer elemento y por lo tanto la raiz
            if (!vertice.hayPadre()){
                System.out.println(i);
                System.out.println("Es la raiz");
                System.out.println("La profundidad es");
                System.out.println(profundidad);






            } else {

                aux_profundidad = profundidad;
                System.out.println(i);
                System.out.println("Su padre es: ");
                System.out.println(vertice.padre().toString());


                ///Buscar el padre en array
                int index_padre = Arreglos.busquedaBinaria(arrayNombres,i);
                //Se extraen sus coordenadas
                int coord = arrayCoordenadas[index_padre];

                System.out.println("Las coordenadas son:");
                System.out.println(coord);


                ///Revisar si la profundidad cambio

                if(profundidad != vertice.profundidad()){
                    System.out.println("La profundidad cambio");
                    profundidad = vertice.profundidad();
                } else {
                    System.out.println("La profundidad no cambio");
                }

                if(esIzquierdo(vertice)){
                    //System.out.println("Es izquierdo");


                    System.out.println("La profundidad es");
                    System.out.println(vertice.profundidad());
                    /*
                    System.out.println("En este nivel caben:");
                    System.out.println(Math.pow(2,vertice.profundidad()));
                    System.out.println("Cada uno con una separacion de");
                    System.out.println(calculaDistancia(vertice.profundidad()));

                     */

                /*
                } else {
                    //System.out.println("Es derecho");


                    System.out.println("La profundidad es");
                    System.out.println(vertice.profundidad());
                    /*
                    System.out.println("En este nivel caben:");
                    System.out.println(Math.pow(2,vertice.profundidad()));
                    System.out.println("Cada uno con una separacion de");
                    System.out.println(calculaDistancia(vertice.profundidad()));

                     */
                /*
                }






            }

                 */







            /*


            if(vertice.hayDerecho()){
                System.out.println("Hay derecho");
            }
            if(vertice.hayIzquierdo()){
                System.out.println("Hay izquierdo");
            }

            if(!vertice.hayIzquierdo() && !vertice.hayDerecho()){
                System.out.println("No hay hijos");
            }

             */



        }


        System.out.println("El contenido de arrayNombresPadres es:");


        for (int y = 0; y < arrayNombresPadres.length; y++){
            System.out.println(arrayNombresPadres[y]);
        }

        System.out.println(arbol.toString());




        //graficaFinalSVG();








    }


    /*
    Metodo que recibe las coordenadas X y Y del vertice padre y del vertice y grafica la linea
     */

    public static void lineaSVG(Double coordXPadre, Integer coordYPadre, Double coordXVertice,Integer coordYVertice){

        String linea = String.format("<line x1='%1$s%%' y1='%2$s' x2='%3$s%%' y2='%4$s' stroke='black' stroke-width='3' />",
                coordXPadre,coordYPadre+9,coordXVertice,coordYVertice);

        //System.out.println("<g>");
        System.out.println(linea);
        //System.out.println("</g>");
    }



    /*
    Metodo que recibe las coordenadas X del vertice padre y el lado del vertice
    y devuelve las coordenadas del vertice. Las coordenadas estaran dadas en porcentaje.
    Entre mas profundidad hay, la distancia entre el vertice y el padre se acorta
     */

    public static double calculaCoordenadas (double coordXPadre, String ladoVertice, int profundidad){

        if(profundidad ==1){
            if(ladoVertice.equals("Izquierdo")){
                return coordXPadre - 25;
            } else {
                return coordXPadre + 25;
            }

        }

        double max_elementos = Math.pow(2,(double)profundidad);
        max_elementos = 100/(max_elementos*2);


        //double coordenadas = coordXPadre/4;

        if(ladoVertice.equals("Izquierdo")){
            return coordXPadre - max_elementos;
        } else {
            return coordXPadre + max_elementos;
        }



    }


    /*
    Metodo que regresa si un vertice es derecho
     */

    public static boolean esDerecho(VerticeArbolBinario<Integer> vertice){
        if(vertice.equals(vertice.padre().derecho())){
            return true;
        } else {
            return false;
        }
    }

        /*
    Metodo que regresa si un vertice es izquierdo
     */

    public static boolean esIzquierdo(VerticeArbolBinario<Integer> vertice){
        if(vertice.equals(vertice.padre().izquierdo())){
            return true;
        } else {
            return false;
        }
    }

    /*
    Metodo que grafica un circulo
    Las coordenadas X se representaran en porcentaje
     */
    public static void circuloSVG(Double coordX, Integer coordY, String texto, String color){


        String circulo = String.format("<circle cx='%1$s%%' cy='%2$s' r='10' stroke='black' stroke-width='3' fill='%3$s' />",
                coordX,coordY, color);


        int y_text = coordY + 2;


        //String circuloTexto = String.format("<text x=\"%1$s\" y=\"%2$s\" font-family=\"Verdana\" font-size=\"10\" fill=\"black\">%3$s</text>",
        //        x_text,y_text,texto);

        String circuloTexto = String.format("<text fill='black' font-family='sans-serif' font-size='10' x='%1$s%%' y='%2$s'\n" +
                "          text-anchor='middle'>%3$s</text>",coordX,y_text,texto);

        System.out.println("<g>");
        System.out.println(circulo);
        System.out.println(circuloTexto);
        System.out.println("</g>");
    }


    /*
    Metodo que recibe un objeto de MeteSaca y lo grafica
     */

    public static void graficaMeteSaca(MeteSaca<Integer> estructura){

        graficaHeaderSVG();

        int x_cuadrado_inicial = 10;
        int y_cuadrado_inicial = 10;
        //Definimos las constantes para separar los cuadrados
        int x_const = 40;

        String elemento = "";

        int contador_cuadrado = x_cuadrado_inicial;
        int cont_cuad_y = 1;



        while(!estructura.esVacia()){
            elemento = String.valueOf(estructura.saca());
            if((cont_cuad_y % 20) == 0){
                y_cuadrado_inicial = y_cuadrado_inicial + 70;
                contador_cuadrado = x_cuadrado_inicial;
            }
            cuadradoSVG(contador_cuadrado,
                    y_cuadrado_inicial,elemento);

            contador_cuadrado = contador_cuadrado + x_const;
            cont_cuad_y ++;
        }

        graficaFinalSVG();

    }

    public static void graficaArreglo(String [] arreglo){

        //Creamos el arreglo
        Integer[] arreglo_int = new Integer [arreglo.length];
        for (int i = 0; i < arreglo.length; i ++){
            arreglo_int[i] = Integer.parseInt(arreglo[i]);
        }

        //La ordenamos

        Arreglos.quickSort(arreglo_int);

        ///Lo graficamos
        graficaHeaderSVG();


        ///Definimos los valores iniciales de X y Y para el cuadrado y las dobles flechas

        int x_cuadrado_inicial = 10;
        int y_cuadrado_inicial = 10;
        //Definimos las constantes para separar los cuadrados
        int x_const = 40;

        String elemento = "";

        int contador_cuadrado = x_cuadrado_inicial;
        int cont_cuad_y = 1;




        for (int i = 0; i < arreglo_int.length; i ++){
            elemento = String.valueOf(arreglo_int[i]);
            if((cont_cuad_y % 20) == 0){
                y_cuadrado_inicial = y_cuadrado_inicial + 70;
                contador_cuadrado = x_cuadrado_inicial;
            }
            cuadradoSVG(contador_cuadrado,
                    y_cuadrado_inicial,elemento);

            contador_cuadrado = contador_cuadrado + x_const;
            cont_cuad_y ++;
        }

        graficaFinalSVG();

    }

    /*
    Metodo que imprime el header de un SVG
     */

    public static void graficaHeaderSVG(){
        String line1 = "<svg xmlns=\"http://www.w3.org/2000/svg\"";
        String line2 = "xmlns:xlink=\"http://www.w3.org/1999/xlink\">";
        System.out.println(line1);
        System.out.println(line2);
    }

        /*
    Metodo que imprime el final de un SVG
     */

    public static void graficaFinalSVG(){
        String string_final = "</svg>";
        System.out.println(string_final);

    }


    /*
    Recibimos una lista y generamos la grafica en SVG
     */

    public static void graficaLista (Lista<Integer> lista){

        //Definimos las constantes para separar los cuadrados
        int x_const = 120;
        //Definimos la constante para separar las dobles flechas
        int x_arrow_const = 120;

        ///Definimos los valores iniciales de X y Y para el cuadrado y las dobles flechas

        int x_cuadrado_inicial = 10;
        int y_cuadrado_inicial = 10;

        int x_flecha_inicial = 80;
        int y_flecha_inicial = 31;




        ///Imprimimos el SVG


        graficaHeaderSVG();


        //Primero imprimimos todos los cuadrados
        String elemento = null;
        int contador_cuadrado = x_cuadrado_inicial;
        int cont_cuad_y = 1;

        for (Integer i : lista){
            elemento = i.toString();
            //Si el cont_cuad_y es mod 10 se añaden 70 a la y_cuadrado_inicial
            //y se reinicia el contador cuadrado a x_cuadrado_inicial
            if((cont_cuad_y % 10) == 0){
                y_cuadrado_inicial = y_cuadrado_inicial + 70;
                contador_cuadrado = x_cuadrado_inicial;
            }

            cuadradoSVG(contador_cuadrado,
                    y_cuadrado_inicial,elemento);

            contador_cuadrado = contador_cuadrado + x_const;
            cont_cuad_y ++;
        }

        ///Imprimimos las dobles flechas correspondientes

        //Calculamos el numero de flechas, esto es el numero de elementos -1
        int n_elementos = lista.getLongitud();
        int contador = 1;
        int contadorFlechaX = x_flecha_inicial;
        int y_flecha = y_flecha_inicial;



        for(int i = 0; i < n_elementos-1; i++){
            if((contador % 10) == 0){
                contadorFlechaX = x_flecha_inicial;
                y_flecha = y_flecha + 69;
            }

            flechaDoble(contadorFlechaX,y_flecha,contador);
            contadorFlechaX = contadorFlechaX + x_arrow_const;
            contador++;
        }

        graficaFinalSVG();
    }

    /*
    Metodo que dibuja un cuadrado en SVG
     */
    public static void cuadradoSVG(Integer coordX, Integer coordY, String texto){

        String cuadrado = String.format("<rect x=\"%1$s\" y=\"%2$s\" height=\"40\" width=\"40\" style=\"stroke:#000000; fill: #66000000\"/>",
                coordX,coordY);

        int x_text = coordX + 17;
        int y_text = coordY + 23;


        String cuadradoTexto = String.format("<text x=\"%1$s\" y=\"%2$s\" font-family=\"Verdana\" font-size=\"10\" fill=\"black\">%3$s</text>",
                x_text,y_text,texto);

        System.out.println("<g>");
        System.out.println(cuadrado);
        System.out.println(cuadradoTexto);
        System.out.println("</g>");
    }

    /*
    Metodo que dibuja una doble flecha
     */

    public static void flechaDoble(Integer coordX, Integer coordY, Integer contador){

        String flechaMarkerStart = String.format("startarrow%1$s",contador);
        String flechaMarkerEnd = String.format("endarrow%1$s",contador);

        String flechas = String.format("<defs>\n" +
                "   <marker id=\"%1$s\" markerWidth=\"10\" markerHeight=\"7\"\n" +
                "   refX=\"10\" refY=\"3.5\" orient=\"auto\">\n" +
                "     <polygon points=\"10 0, 10 7, 0 3.5\" fill=\"black\" />\n" +
                "   </marker>\n" +
                "   <marker id=\"%2$s\" markerWidth=\"10\" markerHeight=\"7\"\n" +
                "   refX=\"0\" refY=\"3.5\" orient=\"auto\" markerUnits=\"strokeWidth\">\n" +
                "       <polygon points=\"0 0, 10 3.5, 0 7\" fill=\"black\" />\n" +
                "   </marker>\n" +
                " </defs>",flechaMarkerStart,flechaMarkerEnd);

        String markerEnd = String.format("url(#endarrow%1$s)",
                contador);
        String markerStart = String.format("url(#startarrow%1$s)",
                contador);
        Integer coordX2 = coordX + 20;


        String linea = String.format("<line x1=\"%1$s\" y1=\"%2$s\" x2=\"%3$s\" y2=\"%2$s\" stroke=\"#000\" stroke-width=\"2\"\n" +
                " marker-end=\"%4$s\" marker-start=\"%5$s\" />",
                coordX,coordY,coordX2,markerEnd,markerStart);

        System.out.println(flechas);
        System.out.println(linea);

    }
}
