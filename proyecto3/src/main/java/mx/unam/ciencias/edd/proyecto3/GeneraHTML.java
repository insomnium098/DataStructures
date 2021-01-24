package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class GeneraHTML {
    private Diccionario<String,ArchivoTexto> diccionario;
    private String carpeta;

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


            ///COLA
            fw.write("\n");
            fw.write(cola);
            fw.close();




        }







    }

    /*
    Metodo que genera un String con la gráfica de pastel
     */

    public String generaPastel(ArchivoTexto archivo){

        /*
        String inicio = "<p>";
        String end = "</p>";
        Diccionario<String, Integer> dicTop5 = archivo.getListaPalabrasTop5();
        String s = dicTop5.toString();

        String res = inicio + s + end;
        return res;

         */


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
        System.out.println("Palabras totales: "+ numPalabras);


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

        //StringBuilder linea = new StringBuilder("<line x1='50%' y1='50%' x2='50%' y2='10%' stroke='black' stroke-width='3'f/>");
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

            //xContador+=coordXelem;
            //yContador+=coordYelem;

            String textoPie = llave + " "+ valor.toString();


            String lineaAux = lineaSVG(50.0, 50.0,coordXelem,coordYelem,false);
            String textoAux = TextoPieSVG(coordXelem, coordYelem,textoPie);
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

}
