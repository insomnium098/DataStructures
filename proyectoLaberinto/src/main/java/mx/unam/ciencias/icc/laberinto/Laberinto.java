package mx.unam.ciencias.icc.laberinto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/**
 * Laberinto para Extraordinario de ICC
 */
public class Laberinto {

    public static void main(String[] args) {

        System.out.println("test");

        Grafo grafo = new Grafo(5);
        grafo.conecta(0,1);
        grafo.conecta(0,2);
        grafo.conecta(1,2);
        grafo.conecta(2,3);
        grafo.conecta(1,4);

        grafo.imprimeGrafo();

        grafo.bfs(3,1);


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


}
