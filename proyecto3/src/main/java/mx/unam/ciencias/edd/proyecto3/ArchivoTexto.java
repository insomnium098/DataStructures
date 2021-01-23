package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.*;

import java.io.*;

public class ArchivoTexto {

    private String nombreArchivo;
    private Diccionario<String, Lista<String>> conteoPalabras;
    private Diccionario<String,Integer> listaPalabrasTop5;
    private String lineaElementos;

    public ArchivoTexto(String nombreArchivo){
        this.nombreArchivo = nombreArchivo;
        this.conteoPalabras = null;
        this.listaPalabrasTop5 = null;
        this.lineaElementos = null;
    }

    /*
    Metodo que lee el archivo, genera la lineaElementos y llena el conteo de palabras
     */

    public void procesaArchivo() throws IOException {

        //Primero convertimos todas las lineas del archivo en una sola linea
        String linea;
        StringBuilder result = new StringBuilder();
        BufferedReader br;
        String lineafinal;

        br = new BufferedReader(new FileReader(nombreArchivo));
        while ((linea = br.readLine()) != null){
            result.append(" ");
            result.append(linea);
        }

        lineafinal = result.toString();
        lineafinal = lineafinal.trim();
        lineafinal = lineafinal.replaceAll("[\\n]", " ");
        lineafinal = lineafinal.replaceAll("\\s+", " ");
        //Quitar puntos, comas, signos de exclamación y admiración
        lineafinal = lineafinal.replaceAll("\\.", "");
        lineafinal = lineafinal.replaceAll(",", "");
        lineafinal = lineafinal.replaceAll("¿", "");
        lineafinal = lineafinal.replaceAll("\\?", "");
        lineafinal = lineafinal.replaceAll("!", "");
        lineafinal = lineafinal.replaceAll("¡", "");
        ///Remover acentos y cambiar a lowercase
        lineafinal =lineafinal.replace('á','a');
        lineafinal =lineafinal.replace('é','e');
        lineafinal =lineafinal.replace('í','i');
        lineafinal =lineafinal.replace('ó','o');
        lineafinal =lineafinal.replace('ú','u');
        lineafinal =lineafinal.replace('ñ','n');
        lineafinal= lineafinal.toLowerCase();
        this.lineaElementos = lineafinal; 
        //System.out.println(lineafinal);

        //Hacer array con las palabras
        String[] elementos_split = lineafinal.split("\\s+");

        for (String s : elementos_split){
            System.out.println(s);
        }

        

    }



}
