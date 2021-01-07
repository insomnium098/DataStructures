package mx.unam.ciencias.icc.laberinto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;


/**
 * Laberinto para Extraordinario de ICC
 */
public class Laberinto {

    public static void main(String[] args) {
        ///El primer argumento es el nombre del archivo
        String nombreArchivo = args[0];


        ///Obtenemos el laberinto original

        LinkedList<String> laberintoOriginal = leeLaberinto(nombreArchivo);


        for (String s : laberintoOriginal){
            System.out.println(s);
        }

        ///

        ////Obtenemos un array de dos dimensiones del laberinto
        Integer [][] arrayLaberinto = laberintoArray(laberintoOriginal);

        imprimeLaberintoNumerico(arrayLaberinto);


        ///Procesamos todos los caracteres del laberinto para homogeneizar


        ////Llenamos el laberino con numeros, que representaran a cada char












        /*

        System.out.println("test");

        Grafo grafo = new Grafo(5);
        grafo.conecta(0,1);
        grafo.conecta(0,2);
        grafo.conecta(1,2);
        grafo.conecta(2,3);
        grafo.conecta(1,4);

        grafo.imprimeGrafo();

        grafo.bfs(3,1);

         */


    }

    /*
    Metodo que recibe el laberinto original y crea un array de dos dimensiones a partir de el
     */



    public static Integer [][] laberintoArray (LinkedList<String> laberinto){
        ///Primero determinamos la longitud del laberinto
        ///El numero de "columnas" será la longitud de cada string
        Integer nColumnas = laberinto.get(0).length();
        ///Las rows serán el numero de strings en la lista que contiene al laberinto
        Integer nRows = laberinto.size();

        Integer numElementos = nColumnas * nRows;

        Integer [][] laberintoNum = new Integer[nRows] [nColumnas];

        ///Llenar el laberinto con integers, cada uno representa un char en el string
        //El contador comenzará en 0
        Integer contador = 0;

        for(int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nColumnas; columnas ++){
                laberintoNum[rows][columnas] = contador;
                contador ++;
            }

        }

        return laberintoNum;

    }

    /*
    Metodo que imprime un laberinto numerico
     */

    public static void imprimeLaberintoNumerico (Integer [][] board){
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                //board[row][col] = row * col;
                System.out.print(board[row][col] + "\t");
            }
            System.out.println();
        }

    }




    /*
    Metodo que lee el laberinto y devuelve una lista doblemente ligada con cada renglon del archivo
     */

    public static LinkedList<String> leeLaberinto (String nombreArchivo){
        ///leemos el archivo

        LinkedList<String> laberinto = new LinkedList<>();

        try {
            Scanner scanner = new Scanner(new File(nombreArchivo));
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                //line = line.trim();
                //line = line.replace("\t", "");
                //System.out.println(line);
                laberinto.add(line);

            }

        } catch (Exception e){
            System.out.println("Archivo no encontrado");
            System.exit(1);
        }

        return laberinto;


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
