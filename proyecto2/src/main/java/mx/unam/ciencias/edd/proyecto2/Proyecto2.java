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
    private static String cadena;


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
                Lista<Integer> lista = new Lista<>();
                for (String s : elementos_split){
                    lista.agrega(Integer.parseInt(s));
                }

                graficaLista(lista);

                break;

            case "Pila":
                Pila<Integer> pila = new Pila<>();
                for (String s : elementos_split){
                    pila.mete(Integer.parseInt(s));
                }
                graficaPila(pila);

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
                graficaArbolBFS(arbol,estructura,true);

                break;

            case "ArbolBinarioOrdenado":
                ArbolBinarioOrdenado<Integer> arbolOrdenado = new ArbolBinarioOrdenado<>();
                for (String s : elementos_split){
                    arbolOrdenado.agrega(Integer.parseInt(s));
                }

                graficaArbolBFS(arbolOrdenado,estructura,true);
                break;

            case "ArbolRojinegro":

                ArbolRojinegro<Integer> arbolRojinegro = new ArbolRojinegro<>();
                for (String s : elementos_split){
                    arbolRojinegro.agrega(Integer.parseInt(s));
                }

                //graficaArbolRojinegro(arbolRojinegro);
                graficaArbolBFS(arbolRojinegro,estructura,true);
                //System.out.println(arbolRojinegro.toString());

                /*

                ArbolRojinegro.VerticeRojinegro vert = (ArbolRojinegro.VerticeRojinegro)arbolRojinegro.raiz;

                System.out.println(vert.toString());
                System.out.println(vert.elemento.toString());
                System.out.println(vert.color);

                 */
                break;

            case "ArbolAVL":
                //System.out.println("La estructura es un Arbol AVL");
                ArbolAVL<Integer> arbolAVL = new ArbolAVL<>();
                for (String s : elementos_split){
                    arbolAVL.agrega(Integer.parseInt(s));
                }


                /*
                ArbolAVL.VerticeAVL vert = (ArbolAVL.VerticeAVL)arbolAVL.raiz;
                System.out.println(vert.toString());
                System.out.println(vert.elemento.toString());
                System.out.println(vert.get());
                System.out.println("El balance de la raiz es");
                System.out.println(getBalanceAVL(arbolAVL.raiz));

                 */



                graficaArbolBFS(arbolAVL,estructura,true);

                break;

            case "Arreglos":
                //System.out.println("La estructura es un Arreglo");
                graficaArreglo(elementos_split,70,true);

                break;

            case "Grafica":
                //System.out.println("La estructura es una Grafica");
                Grafica<Integer> grafica = new Grafica<>();
                Lista<Integer> listag = new Lista<>();

                ///Verificamos que el numero de elementos sea par
                if ((elementos_split.length % 2 ) !=0){
                    System.out.println("El numero de elementos en la grafica debe de ser par");
                    System.exit(1);
                }

                svgGrafica(grafica, listag, elementos_split);


                break;

            case "MonticuloMinimo":

            case "MonticuloArreglo":
                //System.out.println("La estructura es un Monticulo Arreglo");
                //System.out.println("La estructura es un Monticulo minimo");
                graficaMonticulo(elementos_split);


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
    Metodo que grafica un monticulo
     */

    public static void graficaMonticulo (String [] elementos_split){
        Lista<Integer> listaMonticulo = new Lista<>();


        for (String s : elementos_split){
            listaMonticulo.agrega(Integer.parseInt(s));
        }

        listaMonticulo = MonticuloMinimo.heapSort(listaMonticulo);


        String [] elementosOrdenados = new String [listaMonticulo.getLongitud()];



        ArbolBinarioCompleto<Integer> arbolMonticulo = new ArbolBinarioCompleto<>();

        int contador = 0;
        for (Integer i : listaMonticulo){
            arbolMonticulo.agrega(i);
            elementosOrdenados[contador] = i.toString();
            contador ++;

        }

        graficaArbolBFS(arbolMonticulo,"ArbolBinarioCompleto",false);



        graficaArreglo(elementosOrdenados,500,false);

    }

    /*
    Metodo que procesa una grafica y la visualiza en SVG
     */

    public static void svgGrafica (Grafica<Integer> grafica, Lista<Integer> lista, String[] elementos_split){

        ///La lista va a contener los elementos repetidos, que estaran desconectados de la grafica

        Integer anterior;
        Integer actual;


        for (int i = 1; i < elementos_split.length; i = i + 2){

            anterior = Integer.valueOf(elementos_split[i-1]);
            actual = Integer.valueOf(elementos_split[i]);

            ///Revisar si el par de elementos son iguales
            if(anterior.equals(actual)){
                lista.agrega(actual);
                continue;
            }



            ///Buscamos si ambos elementos ya existen en la grafica

            if(grafica.contiene(anterior) && grafica.contiene(actual)){

                ///Revisar si la arista ya existe
                if (!grafica.sonVecinos(anterior, actual)) {
                    grafica.conecta(anterior, actual);
                }
                continue;

            }

            ///Buscamos si ya existe el anterior pero no el actual
            if(grafica.contiene(anterior) && !grafica.contiene(actual)){
                grafica.agrega(actual);
                grafica.conecta(anterior,actual);
                continue;
            }
            ///Buscamos si ya existe el actual pero no el anterior
            if(grafica.contiene(actual) && !grafica.contiene(anterior)){
                grafica.agrega(anterior);
                grafica.conecta(anterior,actual);
                continue;
            }

            ///Si no existen ambos se agregan y se conectan
            grafica.agrega(actual);
            grafica.agrega(anterior);
            grafica.conecta(actual,anterior);



        }



        ///Hacemos listas para guardar los elementos y sus coordenadas X y Y
        ////Estas coordenadas estaran fuera del loop
        Lista<String> nombreElemento = new Lista<>();
        Lista<Double> listacoordX = new Lista<>();
        Lista<Double> listacoordY = new Lista<>();


        ////Obtenemos el numero de elementos para graficarlos en el circulo
        ///Establecemos el centro 700 y 300 en normal
        double xCentro = 50.0;
        double yCentro = 50.0;

        int nElementos = grafica.getElementos();
        int radio = 35;
        int contador = 1;

        Double coordXelem;
        double coordYelem;

        Double coordXPadre;
        Double coordYPadre;


        Lista<Integer> listaElementos = new Lista<>();

        //Primero graficamos todos los nodos de la grafica y los agregamos a una lista
        //Hacemos una lista para guardar las lineas ya hechas


        Lista<String> listaLineas = new Lista<>();

        graficaHeaderSVG();




        for (Integer i : grafica){
            listaElementos.agrega(i);
            String s = i.toString();
            coordXelem = xCentro + radio * Math.cos(2*Math.PI * contador / nElementos);
            coordYelem = yCentro + radio * Math.sin(2*Math.PI * contador / nElementos);

            ///Despues agregamos el elemento y sus coordenadas a las listas
            nombreElemento.agrega(s);
            listacoordX.agrega(coordXelem);
            listacoordY.agrega(coordYelem);
            contador ++;

        }



        for (Integer i : grafica){


            for (Integer b : listaElementos){

                ///Vemos si estan conectados
                if(!grafica.sonVecinos(i,b)){
                    continue;
                }


                ///Hacer un string con los elementos y su conexion
                String texto = i.toString() + "_" + b.toString();
                String textor = b.toString() + "_" + i.toString();


                ///Vemos si las lineas entre los elementos ya existen en listaLineas,
                //Si ya existen continuamos el loop
                if(listaLineas.contiene(texto) || listaLineas.contiene(textor)) {
                    continue;
                }

                listaLineas.agrega(texto);
                listaLineas.agrega(textor);


                //Si estan conectados se obtienen los indices y se hace la linea
                int indicePadre = nombreElemento.indiceDe(i.toString());
                coordXPadre = listacoordX.get(indicePadre);
                coordYPadre = listacoordY.get(indicePadre);

                int indice = nombreElemento.indiceDe(b.toString());
                ///Obtenemos sus coordenadas X y Y
                coordXelem = listacoordX.get(indice);
                coordYelem = listacoordY.get(indice);


                ///Graficamos las lineas entre el elemento y su padre
                lineaSVG(coordXPadre,coordYPadre,coordXelem,coordYelem,false);
                circuloSVG(coordXelem,coordYelem,b.toString(),"NEGRO",false);

            }
            int indiceP = nombreElemento.indiceDe(i.toString());
            double coordXP = listacoordX.get(indiceP);
            double coordYP= listacoordY.get(indiceP);
            circuloSVG(coordXP,coordYP,i.toString(),"NEGRO",false);



        }


        ///Finalmente graficamos los elementos inconexos en lista
        contador = 1;
        double yInicial = 90;
        double xInicial = 2;

        double separacionX = 5;
        double separacionY= 3;

        double yContador = yInicial;
        double xContador= xInicial;

        for (Integer i : lista){
            //Si el elemento ya existe en la grafica continuamos
            if(grafica.contiene(i) || listaElementos.contiene(i)){
                continue;
            }

            ///Si el contador es modulo 20 se reinicia el Xcontador y se actualiza el Y contador
            if((contador % 20)==0){
                xContador = xInicial;
                yContador = yInicial + separacionY;
                contador = 1;
            }

            listaElementos.agrega(i);
            String s = i.toString();
            coordXelem = xContador + (separacionX*contador);
            coordYelem = yContador;

            ///Despues agregamos el elemento y sus coordenadas a las listas
            nombreElemento.agrega(s);
            listacoordX.agrega(coordXelem);
            listacoordY.agrega(coordYelem);
            circuloSVG(coordXelem,coordYelem,s,"NEGRO",false);

            contador ++;


        }





        graficaFinalSVG();


    }







    /*
    Metodo que procesa un vertice AVL y devuelve un string con su balance
     */

    public static String getBalanceAVL(ArbolBinario.Vertice vertice){
        String balance;
        ArbolAVL.VerticeAVL vert = (ArbolAVL.VerticeAVL) vertice;

        balance = vert.toString();
        int espacio = balance.indexOf(" ");

        balance = balance.substring(espacio,balance.length());

        return balance.trim();

    }

    /*
    Metodo que grafica el balance de un vertice AVL
     */
    public static void balanceSVG(Double coordX, Double coordY, String balance,String lado){

        if (lado.equals("Izquierdo")){
            coordX = coordX-0.4;
        } else {
            coordX = coordX+0.4;
        }



        String imprimeBalance = String.format("<text fill='black' font-family='sans-serif' font-size='10' x='%1$s%%' y='%2$s'\n" +
                "          text-anchor='middle'>%3$s</text>",coordX,coordY - 12,balance);


        System.out.println(imprimeBalance);
    }



    /*
    Metodo Que grafica un arbolbinario basado en BFS por vertices

     */

    public static void graficaArbolBFS (ArbolBinario<Integer> arbol, String tipoArbol, boolean header ){

        Double y_Circulo_inicial = 40.0;
        graficaHeaderSVG();


        //Inicializamos profundidad y el contador de Y
        int profundidad = 0;

        Double contadorY = y_Circulo_inicial;


        //////V2 HACER LISTAS PARA LOS PADRES Y LOS ELEMENTOS
        Lista<Integer> nombresPadres = new Lista<>();
        Lista<Double> coordenadasPadres = new Lista<>();

        Lista<Integer> nombresElementos = new Lista<>();
        Lista<Double> coordenadasElementos = new Lista<>();



        /////////RECORRER POR BFS


        Cola<ArbolBinario.Vertice> cola = new Cola<>();

        cola.mete(arbol.raiz);
        while (!cola.esVacia()) {



            ArbolBinario.Vertice vertice = cola.saca();

            Integer nombreElemento;

            String color;

            if(tipoArbol.equals("ArbolRojinegro")){
                ArbolRojinegro.VerticeRojinegro vert = (ArbolRojinegro.VerticeRojinegro)vertice;
                color = vert.color.toString();
                nombreElemento = Integer.parseInt(vert.elemento.toString());

            } else if (tipoArbol.equals("ArbolAVL")){
                color = "white";
                ArbolAVL.VerticeAVL vert = (ArbolAVL.VerticeAVL)vertice;
                nombreElemento = Integer.parseInt(vert.elemento.toString());


            } else {

                color = "white";
                nombreElemento = Integer.parseInt(vertice.toString());

            }

            ///Primero verificar si la profundidad cambio


            //Si cambio reiniciamos hacemos los arrays de elementos como los padres
            ///Y reiniciamos los de elementos
            if(profundidad != vertice.profundidad()){

                profundidad = vertice.profundidad();
                contadorY = contadorY + 70;

                nombresPadres.limpia();
                for(Integer a : nombresElementos){
                    nombresPadres.agrega(a);
                }

                coordenadasPadres.limpia();
                for(Double a : coordenadasElementos){
                    coordenadasPadres.agrega(a);
                }


                nombresElementos.limpia();
                coordenadasElementos.limpia();



            }



            ////Primer caso donde el elemento es la raiz
            if (!vertice.hayPadre()){
                ///Despues llenamos los arrays de ELEMENTOS

                nombresElementos.agrega(nombreElemento);

                ///Aqui va la funcion que calcula la coordenada, como es raiz será de 50
                coordenadasElementos.agrega(Double.valueOf(50));

                ///Despues llenamos los arrays de PADRES
                nombresPadres.agrega(nombreElemento);

                ///Aqui va la funcion que calcula la coordenada, como es raiz será de 50
                coordenadasPadres.agrega(Double.valueOf(50));



                circuloSVG(Double.valueOf(50),contadorY,nombreElemento.toString(),color,true);

                if(tipoArbol.equals("ArbolAVL")){
                    balanceSVG(Double.valueOf(50),contadorY,getBalanceAVL(vertice),"Izquierdo");
                }


            } else {

                ///Buscamos las coordenadas del padre

                Integer nombrePadre;

                if (tipoArbol.equals("ArbolRojinegro")) {
                    ArbolRojinegro.VerticeRojinegro vert = (ArbolRojinegro.VerticeRojinegro)vertice;

                    nombrePadre = Integer.parseInt(vert.padre.elemento.toString());

                } else if (tipoArbol.equals("ArbolAVL")){
                    ArbolAVL.VerticeAVL vert = (ArbolAVL.VerticeAVL)vertice;
                    nombrePadre = Integer.parseInt(vert.padre.elemento.toString());


                } else {
                    nombrePadre = Integer.parseInt(vertice.padre().toString());

                }


                //int index_padre = Arreglos.busquedaBinaria(arrayNombresPadres,nombrePadre);
                int index_padre = nombresPadres.indiceDe(nombrePadre);
                //Se extraen sus coordenadas
                //double coordPadre = arrayCoordenadasPadres[index_padre];
                double coordPadre = coordenadasPadres.get(index_padre);

                double coordElemento;
                ///Vemos si el elemento es izquierdo o derecho y calculamos su coordenada
                if(esIzquierdo(vertice)){
                    coordElemento = calculaCoordenadas(coordPadre,"Izquierdo",profundidad);

                } else {
                    coordElemento = calculaCoordenadas(coordPadre,"Derecho",profundidad);
                }

                nombresElementos.agrega(Integer.parseInt(nombreElemento.toString()));
                coordenadasElementos.agrega(coordElemento);


                lineaSVG(coordPadre,contadorY-70,coordElemento,contadorY,true);

                circuloSVG(coordElemento,contadorY,nombreElemento.toString(),color,true);

                if(tipoArbol.equals("ArbolAVL")){

                    if(esIzquierdo(vertice)){
                        balanceSVG(coordElemento,contadorY,getBalanceAVL(vertice),"Izquierdo");
                    } else {
                        balanceSVG(coordElemento,contadorY,getBalanceAVL(vertice),"Derecho");
                    }

                }



            }






            //////AQUI TERMINA EL PROCESO

            if(vertice.hayIzquierdo()){
                cola.mete((ArbolBinario.Vertice) vertice.izquierdo());
            }

            if(vertice.hayDerecho()){
                cola.mete((ArbolBinario.Vertice) vertice.derecho());
            }


        }




        //////////

        if(header){
            graficaFinalSVG();
        }





    }





    /*
    Metodo que recibe las coordenadas X y Y del vertice padre y del vertice y grafica la linea
     */

    public static void lineaSVG(Double coordXPadre, Double coordYPadre, Double coordXVertice,Double coordYVertice,
                                boolean arbol){

        String linea;

        if(arbol){
            linea = String.format("<line x1='%1$s%%' y1='%2$s' x2='%3$s%%' y2='%4$s' stroke='black' stroke-width='3' />",
                    coordXPadre,coordYPadre+9,coordXVertice,coordYVertice);

        } else {
            linea = String.format("<line x1='%1$s%%' y1='%2$s%%' x2='%3$s%%' y2='%4$s%%' stroke='black' stroke-width='3' />",
                    //coordXPadre -0.4 , coordYPadre - 0.4, coordXVertice + 0.4, coordYVertice + 0.4);
                    coordXPadre  , coordYPadre , coordXVertice , coordYVertice);

        }



        System.out.println(linea);
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
    Metodo que regresa si un vertice es izquierdo
     */

    public static boolean esIzquierdo(ArbolBinario.Vertice vertice){

        if(vertice == vertice.padre.izquierdo){
            return true;
        } else {
            return false;
        }

    }


    /*
    Metodo que grafica un circulo
    Las coordenadas X se representaran en porcentaje
     */
    public static void circuloSVG(Double coordX, Double coordY, String texto, String color, boolean arbol){

        String colorTexto = "black";


        if(color.equals("NEGRO")){
            color = "black";
            colorTexto = "white";
        } else if (color.equals("ROJO")){
            color = "red";
            colorTexto = "white";
        } else {
            color = "white";
        }

        Double y_text;

        String circulo;
        String circuloTexto;

        if (arbol ){
            y_text = coordY + 2;
            circulo = String.format("<circle cx='%1$s%%' cy='%2$s' r='10' stroke='black' stroke-width='3' fill='%3$s' />",
                    coordX,coordY, color);


            circuloTexto = String.format("<text fill='%4$s' font-family='sans-serif' font-size='10' x='%1$s%%' y='%2$s'\n" +
                    "          text-anchor='middle'>%3$s</text>",coordX,y_text,texto,colorTexto);

        } else {
            y_text = coordY + 0.5;

            circulo = String.format("<circle cx='%1$s%%' cy='%2$s%%' r='10' stroke='black' stroke-width='3' fill='%3$s' />",
                    coordX,coordY, color);


            circuloTexto = String.format("<text fill='%4$s' font-family='sans-serif' font-size='10' x='%1$s%%' y='%2$s%%'\n" +
                    "          text-anchor='middle'>%3$s</text>",coordX,y_text,texto,colorTexto);

        }






        System.out.println("<g>");
        System.out.println(circulo);
        System.out.println(circuloTexto);
        System.out.println("</g>");
    }


    /*
    Metodo que recibe una Pila y la grafica
     */

    public static void graficaPila(Pila<Integer> pila){


        ///Hacemos una lista para guardar los elementos de la pila en orden reverso
        Lista<Integer> lista = new Lista<>();
        while (!pila.esVacia()){
            lista.agrega(pila.saca());
        }

        lista.reversa();

        //Obtenemos el numero de elementos para establecer el height del svg
        ///Por cada elemento se agregan 50 al svg
        int longSVG = lista.getLongitud()*50;

        String headerSVG = String.format("<svg xmlns=\"http://www.w3.org/2000/svg\"\n" +
                        "xmlns:xlink=\"http://www.w3.org/1999/xlink\" width='700'  height= '%1$s'>",
                longSVG);

        System.out.println(headerSVG);



        int x_cuadrado_inicial = 500;
        int y_cuadrado_inicial = 10;
        //Definimos las constantes para separar los cuadrados
        int x_const = 40;

        String elemento = "";

        int contador_cuadrado = x_cuadrado_inicial;


        for (Integer i : lista){
            elemento = String.valueOf(i);
            cuadradoSVG(contador_cuadrado,
                    y_cuadrado_inicial,elemento);

            y_cuadrado_inicial = y_cuadrado_inicial + x_const;



        }

        graficaFinalSVG();

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
        int cont_cuad_y = 0;



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

    public static void graficaArreglo(String [] arreglo, Integer yInicial, boolean header){

        //Creamos el arreglo
        Integer[] arreglo_int = new Integer [arreglo.length];
        for (int i = 0; i < arreglo.length; i ++){
            arreglo_int[i] = Integer.parseInt(arreglo[i]);
        }

        //La ordenamos

        //Arreglos.quickSort(arreglo_int);

        ///Lo graficamos

        if(header){
            graficaHeaderSVG();
        }



        ///Definimos los valores iniciales de X y Y para el cuadrado y las dobles flechas

        int x_cuadrado_inicial = 10;
        //int y_cuadrado_inicial = 10;
        int y_cuadrado_inicial = yInicial;
        //Definimos las constantes para separar los cuadrados
        int x_const = 40;

        String elemento = "";

        int contador_cuadrado = x_cuadrado_inicial;
        int cont_cuad_y = 0;


        for (Integer integer : arreglo_int) {
            elemento = String.valueOf(integer);
            if ((cont_cuad_y % 20) == 0) {
                y_cuadrado_inicial = y_cuadrado_inicial + 70;
                contador_cuadrado = x_cuadrado_inicial;
            }
            cuadradoSVG(contador_cuadrado,
                    y_cuadrado_inicial, elemento);

            contador_cuadrado = contador_cuadrado + x_const;
            cont_cuad_y++;
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

        String elemento;
        int contador_cuadrado = x_cuadrado_inicial;
        int cont_cuad_y = 0;

        int contador = 0;
        int contadorFlechaX = x_flecha_inicial;
        int y_flecha = y_flecha_inicial;

        for (Integer i : lista){
            elemento = i.toString();
            //Si el cont_cuad_y es mod 10 se añaden 70 a la y_cuadrado_inicial
            //y se reinicia el contador cuadrado a x_cuadrado_inicial
            if((cont_cuad_y % 10) == 0){
                y_cuadrado_inicial = y_cuadrado_inicial + 70;
                contador_cuadrado = x_cuadrado_inicial;
                contadorFlechaX = x_flecha_inicial;
                y_flecha = y_flecha + 69;
            }

            //Imprimimos las dobles flechas correspondientes
            if(!i.equals(lista.getUltimo())){
                flechaDoble(contadorFlechaX,y_flecha,contador);
                contadorFlechaX = contadorFlechaX + x_arrow_const;
                contador++;
            }

            cuadradoSVG(contador_cuadrado,
                    y_cuadrado_inicial,elemento);

            contador_cuadrado = contador_cuadrado + x_const;
            cont_cuad_y ++;
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
