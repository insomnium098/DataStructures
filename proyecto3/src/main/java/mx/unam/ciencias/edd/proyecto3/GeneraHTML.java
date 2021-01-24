package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
            String parrafo = this.generaConteo(archivo);
            fw.write(parrafo);
            //fw.write("<p>This is a paragraph.</p>");

            ///COLA
            fw.write("\n");
            fw.write(cola);
            fw.close();




        }







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

}
