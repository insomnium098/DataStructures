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


            FileWriter fw = new FileWriter(new File(carpeta,nombreArchivo));
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
            fw.write("<h1>Pastel</h1>");
            String pastel = this.generaPastel(archivo);
            fw.write(pastel);
            fw.write("\n");

            //Grafica de Barras
            fw.write("<h1>Barras</h1>");
            String barras = this.generaBarras(archivo);
            fw.write(barras);
            fw.write("\n");

            ///Arbol Rojinegro
            fw.write("<h1>Arbol rojinegro</h1>");
            String arbolRojinegro = this.generaArbolRojinegro(archivo);
            fw.write(arbolRojinegro);
            fw.write("\n");



            ///COLA
            fw.write("\n");
            fw.write(cola);
            fw.close();




        }







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
        //System.out.println("Palabras totales: "+ numPalabras);


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
        String header = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>";
        return header;
    }


    /*
    Metodo que genera la cola del HTML
     */

    public String colaHTML(){
        String cola = "</body>\n" +
                "</html>";
        return cola;
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
                    //coordXPadre -0.4 , coordYPadre - 0.4, coordXVertice + 0.4, coordYVertice + 0.4);
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
        Iterator<String> itllaves = diccionarioTop15.iteradorLlaves();
        System.out.println("IteradorLlaves");
        while (itllaves.hasNext()){
            System.out.println(itllaves.next());
        }

        String arbolSVG = "";

        ArbolRojinegro<Integer> arbol = new ArbolRojinegro<>();


        System.out.println("Iterador valores agregados:");
        for (Integer i : diccionarioTop15){
            arbol.agrega(i);
            System.out.println(i);
        }

        System.out.println("El arbol : ");
        System.out.println(arbol.toString());
        //VerticeArbolBinario<Integer> vert = arbol.raiz();
        //arbol.getColor(vert);

        arbolSVG += graficaArbolRojinegroBFS(arbol);


        String header = headerSVG(500, 500);
        String cola = colaSVG();


        return header + arbolSVG + cola;





    }









    /*
    Metodo Que grafica un arbolbinario basado en BFS por vertices

     */

    public String graficaArbolRojinegroBFS (ArbolRojinegro<Integer> arbol){

        StringBuilder arbolSVG = new StringBuilder("");


        Double y_Circulo_inicial = 40.0;


        //Inicializamos profundidad y el contador de Y
        int profundidad = 0;

        Double contadorY = y_Circulo_inicial;


        //////V2 HACER LISTAS PARA LOS PADRES Y LOS ELEMENTOS
        Lista<Integer> nombresPadres = new Lista<>();
        Lista<Double> coordenadasPadres = new Lista<>();

        Lista<Integer> nombresElementos = new Lista<>();
        Lista<Double> coordenadasElementos = new Lista<>();



        /////////RECORRER POR BFS

        VerticeArbolBinario<Integer> raiz = arbol.raiz();
        //arbol.getColor(vert);




        Cola<VerticeArbolBinario<Integer>> cola = new Cola<>();

        cola.mete(arbol.raiz());
        while (!cola.esVacia()) {



            //ArbolBinario.Vertice vertice = cola.saca();
            VerticeArbolBinario<Integer> vertice = cola.saca();

            Integer nombreElemento;

            String color;

            //ArbolRojinegro.VerticeRojinegro vert = (ArbolRojinegro.VerticeRojinegro)vertice;
            color = arbol.getColor(vertice).toString();
            String parseElemento = vertice.toString();
            parseElemento = nombreRojinegro(parseElemento);

            nombreElemento = Integer.parseInt(parseElemento);


            /*

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

             */

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



                arbolSVG.append(circuloSVG(Double.valueOf(50),contadorY,nombreElemento.toString(),color,true));

                /*

                if(tipoArbol.equals("ArbolAVL")){
                    balanceSVG(Double.valueOf(50),contadorY,getBalanceAVL(vertice),"Izquierdo");
                }

                 */


            } else {

                ///Buscamos las coordenadas del padre

                Integer nombrePadre;


                VerticeArbolBinario<Integer> vert = vertice.padre();
                String stringNombrePadre = vert.toString();
                stringNombrePadre = nombreRojinegro(stringNombrePadre);

                nombrePadre = Integer.parseInt(stringNombrePadre);

                /*


                if (tipoArbol.equals("ArbolRojinegro")) {
                    ArbolRojinegro.VerticeRojinegro vert = (ArbolRojinegro.VerticeRojinegro)vertice;

                    nombrePadre = Integer.parseInt(vert.padre.elemento.toString());

                } else if (tipoArbol.equals("ArbolAVL")){
                    ArbolAVL.VerticeAVL vert = (ArbolAVL.VerticeAVL)vertice;
                    nombrePadre = Integer.parseInt(vert.padre.elemento.toString());


                } else {
                    nombrePadre = Integer.parseInt(vertice.padre().toString());

                }

                 */


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


                arbolSVG.append(lineaSVG(coordPadre,contadorY-70,coordElemento,contadorY,true));

                arbolSVG.append(circuloSVG(coordElemento,contadorY,nombreElemento.toString(),color,true));

                /*

                if(tipoArbol.equals("ArbolAVL")){

                    if(esIzquierdo(vertice)){
                        balanceSVG(coordElemento,contadorY,getBalanceAVL(vertice),"Izquierdo");
                    } else {
                        balanceSVG(coordElemento,contadorY,getBalanceAVL(vertice),"Derecho");
                    }

                }

                 */



            }






            //////AQUI TERMINA EL PROCESO

            if(vertice.hayIzquierdo()){
                //cola.mete((ArbolBinario.Vertice) vertice.izquierdo());
                cola.mete(vertice.izquierdo());
            }

            if(vertice.hayDerecho()){
                //cola.mete((ArbolBinario.Vertice) vertice.derecho());
                cola.mete(vertice.derecho());
            }


        }

        return arbolSVG.toString();



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
                    "          text-anchor='middle'>%3$s</text>",coordX,y_text,texto,colorTexto);

        }




        /*

        System.out.println("<g>");
        System.out.println(circulo);
        System.out.println(circuloTexto);
        System.out.println("</g>");

         */

        return circulo+circuloTexto;
    }

            /*
    Metodo que regresa si un vertice es izquierdo
     */

    public static boolean esIzquierdo(VerticeArbolBinario<Integer> vertice){

        if(vertice.padre().hayIzquierdo()){
            VerticeArbolBinario<Integer> izquierdo = vertice.padre().izquierdo();
            return vertice.equals(izquierdo);

        } else {
            return false;
        }



        /*
        if(vertice == vertice.padre.izquierdo){
            return true;
        } else {
            return false;
        }

         */

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
