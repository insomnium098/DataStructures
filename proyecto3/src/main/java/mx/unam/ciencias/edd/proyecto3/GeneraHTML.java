package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.edd.VerticeArbolBinario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class GeneraHTML {
    private final Diccionario<String,ArchivoTexto> diccionario;
    private final String carpeta;
    private Integer numPalabras;
    private Diccionario<String,Integer> topPalabras;

    public GeneraHTML (Diccionario<String,ArchivoTexto> diccionario, String carpeta) throws IOException {
        this.diccionario = diccionario;
        this.carpeta = carpeta;
        this.Genera();
    }


    public void Genera() throws IOException {


        for (ArchivoTexto archivo: diccionario){
            String nombreArchivo = archivo.getNombreArchivo();
            nombreArchivo +=".html";

            //String rutaArchivo = carpeta+"/"+nombreArchivo;
            

            FileWriter fw = new FileWriter(new File(carpeta,nombreArchivo));
            //FileWriter fw = new FileWriter(new File(rutaArchivo));
            String header = headerHTML();
            String cola = colaHTML();

            ///HEADER
            fw.write(header);
            fw.write("\n");
            fw.write("<h1>Lista de apariciones de palabras</h1>");
            String conteo = this.generaConteo(archivo);
            fw.write(conteo);
            fw.write("\n");

            ///Grafica de pastel
            //fw.write("<p>This is a paragraph.</p>");
            fw.write("<h1>Gráfica de Pastel</h1>");
            String pastel = this.generaPastel(archivo);
            fw.write(pastel);
            fw.write("\n");

            //Grafica de Barras
            fw.write("<h1>Gráfica de Barras</h1>");
            String barras = this.generaBarras(archivo);
            fw.write(barras);
            fw.write("\n");

            ///Arbol Rojinegro
            fw.write("<h1>Árbol RojiNegro</h1>");
            String arbolRojinegro = this.generaArbolRojinegro(archivo);
            fw.write(arbolRojinegro);
            fw.write("\n");

            ///Arbol AVL
            fw.write("<h1>Árbol AVL</h1>");
            String arbolAVL = this.generaArbolAVL(archivo);
            fw.write(arbolAVL);
            fw.write("\n");



            ///COLA
            fw.write("\n");
            fw.write(cola);
            fw.close();




        }







    }

    public void GeneraIndice(Grafica<String> grafica) throws IOException{
        String nombreArchivo ="index.html";

        FileWriter fw = new FileWriter(new File(carpeta,nombreArchivo));
        String header = headerHTML();
        fw.write("\n");
        fw.write("<h1>Indice</h1>");
        fw.write("\n");
        fw.write("<h2>Archivos analizados</h2>");
        fw.write("\n");

        for (ArchivoTexto archivo : diccionario){
            String nombre = archivo.getNombreArchivo();
            String nombreHTML = nombre + ".html";
            Integer palabrasTotales = archivo.getPalabrasTotales();
            String parrafo = generaParrafo(nombre, nombreHTML, palabrasTotales);
            fw.write("\n");
            fw.write(parrafo);
        }

        fw.write("<h2>Grafo</h2>");
        fw.write("\n");
        fw.write("<p>El grafo representa con nodos a los archivos analizados y las aristas si hay " +
                "palabras de al menos 7 caracteres en común entre los nodos</p>");
        fw.write("\n");
        String grafo = generaGrafica(grafica);
        fw.write(grafo);
        fw.write("\n");





        String cola = colaHTML();
        fw.close();

    }

    /*
    Metodo que genera el string representando a la grafica
     */

    public String generaGrafica (Grafica<String> grafica){

        StringBuilder graficaSVG = new StringBuilder("");

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


        Lista<String> listaElementos = new Lista<>();

        //Primero graficamos todos los nodos de la grafica y los agregamos a una lista
        //Hacemos una lista para guardar las lineas ya hechas


        Lista<String> listaLineas = new Lista<>();




        for (String i : grafica){
            listaElementos.agrega(i);
            String s = i;
            coordXelem = xCentro + radio * Math.cos(2*Math.PI * contador / nElementos);
            coordYelem = yCentro + radio * Math.sin(2*Math.PI * contador / nElementos);

            ///Despues agregamos el elemento y sus coordenadas a las listas
            nombreElemento.agrega(s);
            listacoordX.agrega(coordXelem);
            listacoordY.agrega(coordYelem);
            contador ++;

        }



        for (String i : grafica){


            for (String b : listaElementos){

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
                graficaSVG.append(lineaSVG(coordXPadre,coordYPadre,coordXelem,coordYelem,false));
                graficaSVG.append(circuloSVG(coordXelem,coordYelem,b.toString(),"NEGRO",false));

            }
            int indiceP = nombreElemento.indiceDe(i.toString());
            double coordXP = listacoordX.get(indiceP);
            double coordYP= listacoordY.get(indiceP);
            graficaSVG.append(circuloSVG(coordXP,coordYP,i.toString(),"NEGRO",false));



        }


        String header = headerSVG(500,500);
        String cola = colaSVG();


        return header + graficaSVG.toString() + cola;




    }


    /*
    Metodo que genera el string con los archivos analizados y el numero de palabras que contienen
     */

    public String generaParrafo(String nombreArchivo, String nombreHTML, Integer palabrasTotales){



        String linea = String.format("<li ><a  href= %1$s style='color:green;'>%2$s </a> tiene %3$s palabras </li>",
                nombreHTML, carpeta+"/"+nombreArchivo,palabrasTotales);

        return linea;



    }

    /*
    Metodo que genera una String con la gráfica de barras
     */

    public String generaBarras (ArchivoTexto archivo){
        String header = headerSVG(500,500);
        /*
        Procesamos el top 5 de palabras para hacer las lineas y el texto
         */

        //Obtenemos el numero de palabras totales
        Integer numPalabras = archivo.getPalabrasTotales();
        //System.out.println("Palabras totales: "+ numPalabras);


        Diccionario<String,Integer> topPalabras = archivo.getListaPalabrasTop5();

        //Agregarle a topPalabras el grupo de OTROS y de paso obtener el maximo valor
        Integer maximo = 0;

        Integer numTop5 = 0;
        for (Integer in : topPalabras){
            numTop5+= in;
            if (maximo < in){
                maximo = in;
            }
        }

        Integer numOtros = numPalabras - numTop5;

        if(numOtros > 0){
            topPalabras.agrega("Otros",numOtros);
        }

        ///Calculamos el width de cada barra en porcentaje
        Integer NumBarras = topPalabras.getElementos();
        Integer widthBarras = 100/NumBarras;


        //Inicializamos la linea con los cuadrado
        StringBuilder linea = new StringBuilder("");

        //Inicializamos el contador que tendra las coordX
        Double contX = 0.0;
        //Double contY = 100.0;

        Iterator<String> itLlaves = topPalabras.iteradorLlaves();

        while (itLlaves.hasNext()){
            String llave = itLlaves.next();
            Integer valor = topPalabras.get(llave);
            Double altura = (double)(valor * 100) / maximo;
            altura -=2;
            String texto = llave + ": " + valor;

            String cuadrado = cuadradoSVG(contX,100.00-altura, altura,texto);
            contX += widthBarras;
            linea.append(cuadrado);

        }




        String cola = colaSVG();


        return header+linea+cola;

    }

    /*
    Metodo que genera un String con la gráfica de pastel
     */

    public String generaPastel(ArchivoTexto archivo){

        /*
        Inicializamos el circulo
         */
        String header = headerSVG(500,500);
        String circulo = "<circle cx=\"50%\" cy=\"50%\" r=\"30%\" stroke=\"black\" stroke-width=\"3\" fill=\"blue\" />";

        /*
        Procesamos el top 5 de palabras para hacer las lineas y el texto
         */

        //Obtenemos el numero de palabras totales
        Integer numPalabras = archivo.getPalabrasTotales();


        Diccionario<String,Integer> topPalabras = archivo.getListaPalabrasTop5();

        //Agregarle a topPalabras el grupo de OTROS
        Integer numTop5 = 0;
        for (Integer in : topPalabras){
            numTop5+= in;
        }

        Integer numOtros = numPalabras - numTop5;

        if(numOtros > 0){
            topPalabras.agrega("Otros",numOtros);
        }



        Iterator<String> itLlaves = topPalabras.iteradorLlaves();

        //Inicializamos la primer linea que estará en medio
        StringBuilder linea = new StringBuilder("");

        Double valorContador = 0.0;

        double trozos = 2 * Math.PI / numPalabras;
        while (itLlaves.hasNext()){
            String llave = itLlaves.next();

            Integer valor = topPalabras.get(llave);
            //Calculamos las coordenadas X y Y de la linea, dependiendo del valor y el numPalabras
            valorContador += valor;

            double angulo = trozos * (valorContador);
            Double coordXelem = 50.0 + 30.0 * Math.cos(angulo);
            Double coordYelem = 50.0 + 30.0 * Math.sin(angulo);

            //El angulo del texto será el angulo de la linea - 2

            double anguloTexto = trozos * (valorContador - 0.5);
            Double coordXtexto = 50.0 + 30.0 * Math.cos(anguloTexto);
            Double coordYtexto = 50.0 + 30.0 * Math.sin(anguloTexto);



            String textoPie = llave + " "+ valor.toString();


            String lineaAux = lineaSVG(50.0, 50.0,coordXelem,coordYelem,false);
            String textoAux = TextoPieSVG(coordXtexto, coordYtexto,textoPie);
            linea.append(lineaAux);
            linea.append(textoAux);

        }

        String cola = colaSVG();

        return header+circulo+linea+cola;
    }

    /*
    Metodo que genera un String con el conteo de palabras en HTML
     */

    public String generaConteo (ArchivoTexto archivo){
        String inicio = "<p>";
        String end = "</p>";
        String conteo = archivo.imprimeConteo();

        String res = inicio + conteo + end;
        return res;
    }



    /*
    Metodo que genera el header del HTML
     */
    public String headerHTML(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>";
    }


    /*
    Metodo que genera la cola del HTML
     */

    public String colaHTML(){
        return "</body>\n" +
                "</html>";
    }


    public void imprime (){
        System.out.println("La carpeta es " + this.carpeta);
        System.out.println(diccionario);
    }

    private String headerSVG(Integer height, Integer width){
        String header = String.format("<svg height=\"%1$s\" width=\"%2$s\">",height,width);
        return header;

    }

    private String colaSVG(){
        String cola = "</svg>";
        return cola;
    }

        /*
    Metodo que recibe las coordenadas X y Y del vertice padre y del vertice y grafica la linea
     */

    private String lineaSVG(Double coordXPadre, Double coordYPadre, Double coordXVertice,Double coordYVertice,
                                boolean arbol){

        String linea;

        if(arbol){
            linea = String.format("<line x1='%1$s%%' y1='%2$s' x2='%3$s%%' y2='%4$s' stroke='black' stroke-width='3' />",
                    coordXPadre,coordYPadre+9,coordXVertice,coordYVertice);

        } else {
            linea = String.format("<line x1='%1$s%%' y1='%2$s%%' x2='%3$s%%' y2='%4$s%%' stroke='black' stroke-width='3' />",
                    coordXPadre  , coordYPadre , coordXVertice , coordYVertice);

        }

        return linea;
    }

    private String TextoPieSVG(Double coordXVertice,Double coordYVertice,String texto){

        String linea;


        linea = String.format("<text fill='lawngreen' font-family='sans-serif' font-size='20' x='%1$s%%' y='%2$s%%'\n" +
                "          text-anchor='middle'>%3$s</text>", coordXVertice , coordYVertice, texto);

        return linea;
    }

    private String cuadradoSVG(Double coordX, Double coordY, Double altura,String texto){

        String cuadrado = String.format("<rect x=\"%1$s%%\" y=\"%2$s%%\" height=\"%3$s%%\" width=\"40\" style=\"stroke:#000000; fill: #34eb95\"/>",
                coordX,coordY, altura);

        Double x_text = coordX;
        Double y_text = coordY -0.1;


        String cuadradoTexto = String.format("<text x=\"%1$s%%\" y=\"%2$s%%\" font-family=\"Verdana\" font-size=\"10\" font-weight=\"bold\" fill=\"black\">%3$s</text>",
                x_text,y_text,texto);


        return cuadrado + cuadradoTexto;

    }


    /*
    Metodo que genera el String para representar a un arbol
     */

    private String generaArbolRojinegro(ArchivoTexto archivo){
        Diccionario<String, Integer> diccionarioTop15 = archivo.getListaPalabrasTop15();

        String arbolSVG = "";

        ArbolRojinegro<Integer> arbol = new ArbolRojinegro<>();


        //System.out.println("Iterador valores agregados:");
        for (Integer i : diccionarioTop15){
            arbol.agrega(i);
            //System.out.println(i);
        }

        //System.out.println("El arbol rojinegro:");
        //System.out.println(arbol.toString());


        arbolSVG += graficaArbolRojinegroBFS(arbol);


        String header = headerSVG(500, 1000);
        String cola = colaSVG();

        return header + arbolSVG + cola;



    }


    /*
    Metodo que genera un String con el Arbol AVL
     */

    private String generaArbolAVL (ArchivoTexto archivo){


        Diccionario<String, Integer> diccionarioTop15 = archivo.getListaPalabrasTop15();

        String arbolSVG = "";

        ArbolAVL<Integer> arbol = new ArbolAVL<>();


        //System.out.println("Iterador valores agregados:");
        for (Integer i : diccionarioTop15){
            arbol.agrega(i);
          //  System.out.println(i);
        }

        //System.out.println("El arbol : ");
        //System.out.println(arbol.toString());

        arbolSVG += graficaArbolAVLBFS(arbol);


        String header = headerSVG(500, 1000);
        String cola = colaSVG();


        return header + arbolSVG + cola;



    }

        /*
    Metodo Que grafica un arbol AVL basado en BFS por vertices

     */

    public String graficaArbolAVLBFS (ArbolAVL<Integer> arbol){

        StringBuilder arbolSVG = new StringBuilder("");


        Double y_Circulo_inicial = 40.0;


        //Inicializamos profundidad y el contador de Y
        int profundidad = 0;

        Double contadorY = y_Circulo_inicial;


        //////V2 HACER LISTAS PARA LOS PADRES Y LOS ELEMENTOS
        Lista<String> nombresPadres = new Lista<>();
        Lista<Double> coordenadasPadres = new Lista<>();

        Lista<String> nombresElementos = new Lista<>();
        Lista<Double> coordenadasElementos = new Lista<>();

        ////V3 Hacer Listas con los vertices para resolver el problema de los repetidos por nivel
        //// la llave será simplemente un contador de nivel, que será un index en las listas
        Lista<VerticeArbolBinario<Integer>> diccionarioPadres = new Lista<>();
        Lista<VerticeArbolBinario<Integer>> diccionarioElementos = new Lista<>();



        /////////RECORRER POR BFS

        VerticeArbolBinario<Integer> raiz = arbol.raiz();

        Cola<VerticeArbolBinario<Integer>> cola = new Cola<>();

        cola.mete(arbol.raiz());
        int contadorNivel = 0;
        while (!cola.esVacia()) {



            VerticeArbolBinario<Integer> vertice = cola.saca();

            Integer nombreElemento;

            String color = "white";

            /*

            String elementoString = vertice.toString();
            String parseElemento = vertice.get().toString();

            nombreElemento = Integer.parseInt(parseElemento);

             */

            String elementoString = hashRojiNegro(vertice);
            //String parseElemento = nombreRojinegro(elementoString);
            String parseElemento = vertice.get().toString();//nombreRojinegro(vertice.toString());

            nombreElemento = Integer.parseInt(parseElemento);


            ///Primero verificar si la profundidad cambio


            //Si cambio reiniciamos hacemos los arrays de elementos como los padres
            ///Y reiniciamos los de elementos
            if(profundidad != vertice.profundidad()){

                profundidad = vertice.profundidad();
                contadorY = contadorY + 70;

                contadorNivel = 0;

                nombresPadres.limpia();
                for(String a : nombresElementos){
                    nombresPadres.agrega(a);
                }

                coordenadasPadres.limpia();
                for(Double a : coordenadasElementos){
                    coordenadasPadres.agrega(a);
                }

                diccionarioPadres.limpia();

                //int contDiccionario = 0;
                for (VerticeArbolBinario<Integer> ve : diccionarioElementos){
                    diccionarioPadres.agrega(ve);
                    //contDiccionario++;
                }


                nombresElementos.limpia();
                coordenadasElementos.limpia();
                diccionarioElementos.limpia();



            }



            ////Primer caso donde el elemento es la raiz
            if (!vertice.hayPadre()){
                ///Despues llenamos los arrays de ELEMENTOS

                nombresElementos.agrega(elementoString);

                ///Aqui va la funcion que calcula la coordenada, como es raiz será de 50
                coordenadasElementos.agrega(50.0);

                ///Despues llenamos los arrays de PADRES
                nombresPadres.agrega(elementoString);

                ///Aqui va la funcion que calcula la coordenada, como es raiz será de 50
                coordenadasPadres.agrega(50.0);

                //diccionarioPadres.agrega(0,vertice);
                diccionarioPadres.agrega(vertice);
                diccionarioElementos.agrega(vertice);



                arbolSVG.append(circuloSVG(50.0,contadorY,nombreElemento.toString(),color,true));
                arbolSVG.append(balanceSVG(50.0,contadorY,getBalanceAVL(vertice),"Izquierdo"));



            } else {

                ///Buscamos las coordenadas del padre

                Integer nombrePadre;


                VerticeArbolBinario<Integer> vert = vertice.padre();
                ///Este string tendra los corchetes con el color y el valor del padre que debe ser parseado
                String stringNombrePadre = hashRojiNegro(vert);

                //String nombrePadreParsed = nombreRojinegro(stringNombrePadre);
                String nombrePadreParsed = nombreRojinegro(vert.toString());

                //nombrePadre = Integer.parseInt(nombrePadreParsed);

                ///Hay algunos casos donde en el mismo nivel se encuentran elementos repetidos,
                // por lo que al buscar el padre es probable obtener un elemento que no es el verdadero padre,
                ///El siguiente metodo se encarcargará de esto

                ///Vamos a recorrer la lista nombres padres, si encontramos un padre obtenemos sus coordenadas,
                //calculamos las coordenadas del elemento y verificamos que estas coordenadas no se encuentren
                //agregadas en coordenadasElementos. Si ya se encuentran, vamos a seguir buscando el padre
                Integer contadorListaPadres = 0;
                double coordPadre = 0;
                double coordElemento = 0;






                for (String s : nombresPadres){
                    if (s.equals(stringNombrePadre)){
                        coordPadre = coordenadasPadres.get(contadorListaPadres);
                        ///Vemos si el elemento es izquierdo o derecho y calculamos su coordenada
                        if(esIzquierdo(vertice)){
                            coordElemento = calculaCoordenadas(coordPadre,"Izquierdo",profundidad);

                        } else {
                            coordElemento = calculaCoordenadas(coordPadre,"Derecho",profundidad);
                        }

                        ///Debemos revisar si coordElemento ya existe en coordenadasElementos,
                        /// si no lo contiene terminamos
                        if (!coordenadasElementos.contiene(coordElemento)){
                            break;

                        }


                    }
                    contadorListaPadres++;

                }






                ///OLD CODE

                /*
                String stringNombrePadre = vert.toString();

                String nombrePadreParsed = vert.get().toString();
                nombrePadre = Integer.parseInt(nombrePadreParsed);

                 */

                /*


                int index_padre = nombresPadres.indiceDe(stringNombrePadre);
                //Se extraen sus coordenadas
                double coordPadre = coordenadasPadres.get(index_padre);

                double coordElemento;
                ///Vemos si el elemento es izquierdo o derecho y calculamos su coordenada
                if(esIzquierdo(vertice)){
                    coordElemento = calculaCoordenadas(coordPadre,"Izquierdo",profundidad);

                } else {
                    coordElemento = calculaCoordenadas(coordPadre,"Derecho",profundidad);
                }

                 */





                ///////

                nombresElementos.agrega(elementoString);
                coordenadasElementos.agrega(coordElemento);
                diccionarioElementos.agrega(vertice);


                arbolSVG.append(lineaSVG(coordPadre,contadorY-70,coordElemento,contadorY,true));

                arbolSVG.append(circuloSVG(coordElemento,contadorY,nombreElemento.toString(),color,true));

                if(esIzquierdo(vertice)){
                    arbolSVG.append(balanceSVG(coordElemento,contadorY,getBalanceAVL(vertice),"Izquierdo"));
                } else {
                    arbolSVG.append(balanceSVG(coordElemento,contadorY,getBalanceAVL(vertice),"Derecho"));
                }


            }






            //////AQUI TERMINA EL PROCESO

            if(vertice.hayIzquierdo()){
                cola.mete(vertice.izquierdo());
            }

            if(vertice.hayDerecho()){
                cola.mete(vertice.derecho());
            }


        }

        return arbolSVG.toString();



    }

    /*
    Metodo que devuelve el balance de un vertice AVL
     */
    public String balanceSVG(Double coordX, Double coordY, String balance,String lado){

        if (lado.equals("Izquierdo")){
            coordX = coordX-0.4;
        } else {
            coordX = coordX+0.4;
        }



        String imprimeBalance = String.format("<text fill='black' font-family='sans-serif' font-size='10' x='%1$s%%' y='%2$s'\n" +
                "          text-anchor='middle'>%3$s</text>",coordX,coordY - 12,balance);


        return imprimeBalance;
    }


        /*
    Metodo que procesa un vertice AVL y devuelve un string con su balance
     */

    public static String getBalanceAVL(VerticeArbolBinario<Integer> vertice){
        String balance;
        balance = vertice.toString();
        int espacio = balance.indexOf(" ");

        balance = balance.substring(espacio,balance.length());

        return balance.trim();

    }









    /*
    Metodo Que grafica un arbol rojinegro basado en BFS por vertices

     */

    public String graficaArbolRojinegroBFS (ArbolRojinegro<Integer> arbol){

        StringBuilder arbolSVG = new StringBuilder("");


        Double y_Circulo_inicial = 40.0;


        //Inicializamos profundidad y el contador de Y
        int profundidad = 0;

        Double contadorY = y_Circulo_inicial;


        //////V2 HACER LISTAS PARA LOS PADRES Y LOS ELEMENTOS
        Lista<String> nombresPadres = new Lista<>();
        Lista<Double> coordenadasPadres = new Lista<>();

        Lista<String> nombresElementos = new Lista<>();
        Lista<Double> coordenadasElementos = new Lista<>();

        ////V3 Hacer Listas con los vertices para resolver el problema de los repetidos por nivel
        //// la llave será simplemente un contador de nivel, que será un index en las listas
        Lista<VerticeArbolBinario<Integer>> diccionarioPadres = new Lista<>();
        Lista<VerticeArbolBinario<Integer>> diccionarioElementos = new Lista<>();






        /////////RECORRER POR BFS

        VerticeArbolBinario<Integer> raiz = arbol.raiz();

        Cola<VerticeArbolBinario<Integer>> cola = new Cola<>();

        cola.mete(arbol.raiz());

        int contadorNivel = 0;
        while (!cola.esVacia()) {



            VerticeArbolBinario<Integer> vertice = cola.saca();

            Integer nombreElemento;

            String color;

            color = arbol.getColor(vertice).toString();
            //String elementoString = vertice.toString();
            String elementoString = hashRojiNegro(vertice);
            //String parseElemento = nombreRojinegro(elementoString);
            String parseElemento = nombreRojinegro(vertice.toString());

            nombreElemento = Integer.parseInt(parseElemento);

            ///Primero verificar si la profundidad cambio

            //Si cambio reiniciamos hacemos los arrays de elementos como los padres
            ///Y reiniciamos los de elementos y el contador de nivel
            if(profundidad != vertice.profundidad()){

                profundidad = vertice.profundidad();
                contadorY = contadorY + 70;

                contadorNivel = 0;

                nombresPadres.limpia();
                for(String a : nombresElementos){
                    nombresPadres.agrega(a);
                }

                coordenadasPadres.limpia();
                for(Double a : coordenadasElementos){
                    coordenadasPadres.agrega(a);
                }

                diccionarioPadres.limpia();

                //int contDiccionario = 0;
                for (VerticeArbolBinario<Integer> ve : diccionarioElementos){
                    diccionarioPadres.agrega(ve);
                    //contDiccionario++;
                }


                nombresElementos.limpia();
                coordenadasElementos.limpia();
                diccionarioElementos.limpia();



            }



            ////Primer caso donde el elemento es la raiz
            if (!vertice.hayPadre()){
                ///Despues llenamos los arrays de ELEMENTOS

                nombresElementos.agrega(elementoString);

                ///Aqui va la funcion que calcula la coordenada, como es raiz será de 50
                coordenadasElementos.agrega(50.0);

                ///Despues llenamos los arrays de PADRES
                nombresPadres.agrega(elementoString);

                ///Aqui va la funcion que calcula la coordenada, como es raiz será de 50
                coordenadasPadres.agrega(50.0);


                //diccionarioPadres.agrega(0,vertice);
                diccionarioPadres.agrega(vertice);
                diccionarioElementos.agrega(vertice);



                arbolSVG.append(circuloSVG(50.0,contadorY,nombreElemento.toString(),color,true));



            } else {

                ///Buscamos las coordenadas del padre

                Integer nombrePadre;


                VerticeArbolBinario<Integer> vert = vertice.padre();

                ///Este string tendra los corchetes con el color y el valor del padre que debe ser parseado
                //String stringNombrePadre = vert.toString();
                String stringNombrePadre = hashRojiNegro(vert);

                //String nombrePadreParsed = nombreRojinegro(stringNombrePadre);
                String nombrePadreParsed = nombreRojinegro(vert.toString());

                nombrePadre = Integer.parseInt(nombrePadreParsed);


                ///Hay algunos casos donde en el mismo nivel se encuentran elementos repetidos,
                // por lo que al buscar el padre es probable obtener un elemento que no es el verdadero padre,
                ///El siguiente metodo se encarcargará de esto

                ///Vamos a recorrer la lista nombres padres, si encontramos un padre obtenemos sus coordenadas,
                //calculamos las coordenadas del elemento y verificamos que estas coordenadas no se encuentren
                //agregadas en coordenadasElementos. Si ya se encuentran, vamos a seguir buscando el padre
                Integer contadorListaPadres = 0;
                double coordPadre = 0;
                double coordElemento = 0;






                for (String s : nombresPadres){
                    if (s.equals(stringNombrePadre)){
                        coordPadre = coordenadasPadres.get(contadorListaPadres);
                        ///Vemos si el elemento es izquierdo o derecho y calculamos su coordenada
                        if(esIzquierdo(vertice)){
                            coordElemento = calculaCoordenadas(coordPadre,"Izquierdo",profundidad);

                        } else {
                            coordElemento = calculaCoordenadas(coordPadre,"Derecho",profundidad);
                        }

                        ///Debemos revisar si coordElemento ya existe en coordenadasElementos,
                        /// si no lo contiene terminamos
                        if (!coordenadasElementos.contiene(coordElemento)){
                            break;

                        }


                    }
                    contadorListaPadres++;

                }





                ////////////// OLD CODE
                /*


                int index_padre = nombresPadres.indiceDe(stringNombrePadre);
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

                 */




                ////////////

                nombresElementos.agrega(elementoString);
                coordenadasElementos.agrega(coordElemento);
                diccionarioElementos.agrega(vertice);


                arbolSVG.append(lineaSVG(coordPadre,contadorY-70,coordElemento,contadorY,true));

                arbolSVG.append(circuloSVG(coordElemento,contadorY,nombreElemento.toString(),color,true));

                contadorNivel++;


            }






            //////AQUI TERMINA EL PROCESO

            if(vertice.hayIzquierdo()){
                cola.mete(vertice.izquierdo());
            }

            if(vertice.hayDerecho()){
                cola.mete(vertice.derecho());
            }


        }

        return arbolSVG.toString();



    }

    /*
    Metodo que recibe un vertice Rojinegro y devuelve un string con el toString del vertice
    + su profundidad + el nombre de su izquierdo + el nombre de su derecho
     */

    private String hashRojiNegro (VerticeArbolBinario<Integer> vertice){
        StringBuilder resfinal = new StringBuilder("");

        String nombre = vertice.toString();
        Integer profundidad = vertice.profundidad();
        String valProfundidad = profundidad.toString();

        resfinal.append(nombre);
        resfinal.append(valProfundidad);


        if (vertice.hayIzquierdo()){
            resfinal.append(vertice.izquierdo().toString());

        }

        if(vertice.hayDerecho()){
            resfinal.append(vertice.derecho().toString());
        }

        return resfinal.toString();


    }

    /*
    Metodo que recibe el  toString de un vertice Rojinegro y devuelve el nombre del elemento
     */

    private String nombreRojinegro (String nombre){

        nombre = nombre.replaceAll("R","");
        nombre = nombre.replaceAll("N","");
        nombre = nombre.replaceAll("\\{","");
        nombre = nombre.replaceAll("}","");

        return nombre;


    }

    /*
    Metodo que grafica un circulo
    Las coordenadas X se representaran en porcentaje
     */
    public String circuloSVG(Double coordX, Double coordY, String texto, String color, boolean arbol){

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
                    "          text-anchor='middle'>%3$s</text>",coordX,y_text,texto,"orange");

        }



        return circulo+circuloTexto;
    }

            /*
    Metodo que regresa si un vertice es izquierdo
     */

    public static boolean esIzquierdo(VerticeArbolBinario<Integer> vertice){

        if(vertice.padre().hayIzquierdo()){
            VerticeArbolBinario<Integer> izquierdo = vertice.padre().izquierdo();
            return vertice == izquierdo;

        } else {
            return false;
        }

    }

    /*
    Metodo que recibe las coordenadas X del vertice padre y el lado del vertice
    y devuelve las coordenadas del vertice. Las coordenadas estaran dadas en porcentaje.
    Entre mas profundidad hay, la distancia entre el vertice y el padre se acorta
     */

    public double calculaCoordenadas (double coordXPadre, String ladoVertice, int profundidad){

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


}
