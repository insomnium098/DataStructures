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

    public static Integer origen;
    public static Integer destino;


    public static void main(String[] args) {
        ///El primer argumento es el nombre del archivo
        String nombreArchivo = args[0];


        ///Obtenemos el laberinto original

        LinkedList<String> laberintoOriginal = leeLaberinto(nombreArchivo);

        /*


        for (String s : laberintoOriginal){
            System.out.println(s);
        }

         */

        ///

        ////Obtenemos un array con integers de dos dimensiones del laberinto
        Integer [][] arrayLaberinto = laberintoArray(laberintoOriginal);
        //imprimeLaberintoNumerico(arrayLaberinto);


        ////Obtenemos un array con chars de dos dimensiones del laberinto
        char [][] charLaberinto = laberintoChar(laberintoOriginal);
        //imprimeLaberintoNumerico(charLaberinto);


        /////Creamos el grafo con ambas arrays
        //Obtenemos el numero de columnas y rows
        Integer nRow = arrayLaberinto[0].length;
        Integer nCols = arrayLaberinto.length;
        Integer nElementos = nRow * nCols;


        //Creamos el grafo
        Grafo grafo = new Grafo(nElementos);




        ////Procesamos los laberintos para crear los edges del grafo
        construyeEdges(grafo, arrayLaberinto, charLaberinto);

        ///Si no existe el nodo de entrada o el de salida, imprimimos el laberinto y terminamos



        if(origen == null || destino == null){
            LinkedList<Integer> trayectoriaVacia = new LinkedList<>();
            graficaLaberinto(trayectoriaVacia, charLaberinto);
            System.exit(1);
        }




        ///Calculamos la trayectoria

        LinkedList<Integer> trayectoria = grafo.bfsTrayectoria(origen,destino);



        ////Ya con la trayectoria, procesamos los laberintos para dibujar la trayectoria e imprimirla
        graficaLaberinto(trayectoria,charLaberinto);




        //grafo.bfs(origen,destino);

        System.out.println("El Origen es:");
        System.out.println(origen);

        System.out.println("El Destino es:");
        System.out.println(destino);


    }

    /*
    Metodo que recibe la trayectoria, ambos laberintos y grafica el laberinto con su trayectoria
     */

    public static void graficaLaberinto(LinkedList<Integer> trayectoria, char [][] charLaberinto) {

        ////Removemos de la lista al origen y destino para no sobreescribir su character
        trayectoria.remove(origen);
        trayectoria.remove(destino);


        ///Vamos a recorrer el laberinto con un contador, y si el valor del contador está
        // en la trayectoria vamos a imprimir un asterisco

        Integer nCols = charLaberinto[0].length;
        Integer nRows = charLaberinto.length;


        Integer contador = 0;

        for(int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){
                if(trayectoria.contains(contador)){
                    System.out.print('*');
                } else {
                    System.out.print(charLaberinto[rows][columnas]);

                }

                contador ++;
            }
            System.out.println();

        }



    }

    /*
    Metodo que recibe dos chars, devuelve TRUE si ambos son espacio o si uno de ellos es
    E ó S y el otro un espacio
     */

    public static boolean estanConectados(char a, char b){

        if(a == ' ' && b == ' '){
            return true;
        }

        if(a == 'E' && b == ' ' || a == ' ' && b == 'E'){
            return true;
        }

        if(a == 'S' && b == ' ' || a == ' ' && b == 'S'){
            return true;
        }

        return false;
    }




    /*
    Metodo que recibe un grafo, el laberinto numerico, un laberinto en char y crea los edges
     */


    public static void construyeEdges(Grafo grafo, Integer [][] arrayLaberinto,
                                      char [][] charLaberinto){

        Integer nCols = arrayLaberinto[0].length;
        Integer nRows = arrayLaberinto.length;

        ///Primero se recorrerá el array con un anterior y siguiente por rows
        Integer anteriorNum = null;
        Integer siguienteNum = null;
        char anteriorChar;
        char siguienteChar;


        for(int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){
                ///Definir anterior y siguiente para ambos arrays
                anteriorNum = arrayLaberinto[rows][columnas];
                anteriorChar = charLaberinto[rows][columnas];

                if(columnas + 1 == nCols ){
                    if(rows + 1 == nRows){
                        break;
                    } else {
                        siguienteNum = arrayLaberinto[rows+1][0];
                        siguienteChar = charLaberinto[rows+1][0];
                    }

                } else {
                    siguienteNum = arrayLaberinto[rows][columnas+1];
                    siguienteChar = charLaberinto[rows][columnas+1];
                }


                ///Revisar si son el origen y el destino para guardar su
                //localizacion
                if(anteriorChar == 'E'){
                    origen = anteriorNum;
                }

                if(siguienteChar == 'E'){
                    origen = siguienteNum;
                }

                if(anteriorChar == 'S'){
                    destino = anteriorNum;
                }

                if(siguienteChar == 'S'){
                    destino = siguienteNum;
                }




                ///Revisar si estan conectados en el charLaberinto

                if(estanConectados(anteriorChar,siguienteChar)){

                    ///Conectar los numericos
                    grafo.conecta(anteriorNum,siguienteNum);
                }



            }




            //////Despues recorrer los arrays por columnas
            for(int columnas1 = 0; columnas1 < nCols; columnas1 ++){
                for (int rows1 = 0; rows1 < nRows; rows1 ++){
                    ///Definir anterior y siguiente para ambos arrays

                    anteriorNum = arrayLaberinto[rows1][columnas1];
                    anteriorChar = charLaberinto[rows1][columnas1];
                    if(rows1 + 1 == nRows){
                        continue;

                    }

                    siguienteNum = arrayLaberinto[rows1+1][columnas1];
                    siguienteChar = charLaberinto[rows1+1][columnas1];

                    if(estanConectados(anteriorChar,siguienteChar)){

                        ///Conectar los numericos
                        grafo.conecta(anteriorNum,siguienteNum);
                    }

                }

            }




        }



        /*
        grafo.conecta(0,1);
        grafo.conecta(0,2);
        grafo.conecta(1,2);
        grafo.conecta(2,3);
        grafo.conecta(1,4);

        grafo.imprimeGrafo();
         */
        //grafo.imprimeGrafo();




    }





    /*
    Metodo que recibe el laberinto original y crea un array de chars de dos dimensiones
     */

    public static char [] [] laberintoChar (LinkedList<String> laberinto){
        ///Primero determinamos la longitud del laberinto
        ///El numero de "columnas" será la longitud de cada string
        Integer nColumnas = laberinto.get(0).length();
        ///Las rows serán el numero de strings en la lista que contiene al laberinto
        Integer nRows = laberinto.size();

        char [][] laberintoChar = new char [nRows] [nColumnas];

        ///Debemos convertir cada string de la lista en chars y agregarlas al laberinto
        int contador = 0;

        for(String s : laberinto){
            char [] character = s.toCharArray();
            for (int columns = 0; columns < nColumnas; columns++){
                laberintoChar[contador][columns] = character[columns];
            }
            contador ++;
        }





        return laberintoChar;



    }

    /*
    Metodo que recibe el laberinto original y crea un array numerico de dos dimensiones a partir de el
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

    public static void imprimeLaberintoNumerico (Integer [][] laberinto){
        for (int row = 0; row < laberinto.length; row++) {
            for (int col = 0; col < laberinto[row].length; col++) {
                System.out.print(laberinto[row][col] + "\t");
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
