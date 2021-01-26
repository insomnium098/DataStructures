package mx.unam.ciencias.edd.proyectoextra;

import java.io.IOException;
import mx.unam.ciencias.edd.*;
import java.io.*;

/**
 * Proyecto extra.
 */
public class Proyectoextra {

    private static void uso() {
        System.err.println("Debes de ingresar al menos un archivo");
        System.exit(1);
    }



    public static void main(String[] args) throws IOException {



        if (args.length == 0)
            uso();

        String argumento = args[0];

        switch (argumento) {
            case "resuelve":
                if(args.length != 2){
                    uso();
                } else {
                    Lista<String> laberinto = leeLaberinto(args[1]);
                    resuelveLaberinto(laberinto);
                }
                break;

            case "genera": {
                if (args.length != 3){
                    uso();
                } else {
                    try {
                        int nRows = Integer.parseInt(args[1]);
                        int nCols = Integer.parseInt(args[2]);
                        generaLaberinto(nRows, nCols);
                    } catch (Exception ex) {
                        System.out.println("ERROR: Las dimensiones del laberinto a generar deben de ser un entero");
                        //uso();
                        ex.printStackTrace();
                    }
                }
                break;
            }

            default:
                uso();

        }




        //Lista<String> laberinto = leeLaberinto(args[0]);

        //resuelveLaberinto(laberinto);



    }

    /*
    Metodo que lee el laberinto y devuelve una lista doblemente ligada con cada renglon del archivo
     */

    public static Lista<String> leeLaberinto (String nombreArchivo){
        ///leemos el archivo

        Lista<String> laberinto = new Lista<>();
        BufferedReader br;
        String linea;

        try {
            br = new BufferedReader(new FileReader(nombreArchivo));
            while ((linea = br.readLine()) != null){
                laberinto.agrega(linea);
            }


        } catch (Exception e ){
            System.out.println("No se pudo leer el archivo con el laberinto");
            System.exit(1);
        }


        ///Revisamos el laberinto, si no es valido terminamos el programa

        if (!revisaLaberinto(laberinto)){
            System.out.println("El laberinto no es valido, ");
            System.out.println("el número de columnas no es el mismo en todas las rows ó");
            System.out.println("alguna row es vacía. Saliendo del programa");
            System.exit(1);
        }


        return laberinto;


    }

    /*
    Metodo que genera un laberinto
     */
    public static void generaLaberinto(Integer nRows, Integer nCols){
        Laberinto lab = new Laberinto(nRows, nCols);
    }


    /*
    Metodo que resuelve un laberinto
     */

    public static void resuelveLaberinto(Lista<String> laberinto){

        Laberinto lab = new Laberinto(laberinto);
        //lab.imprimeLaberinto();
        //lab.imprimeGrafica();
        //System.out.println("La entrada es:" + lab.getOrigen());
        //System.out.println("La salida es:" + lab.getDestino());
    }

    /*
    Metodo que revisa si el laberinto ingresado tiene el mismo número de cols en cada linea y si alguna linea
    no es vacia
     */

    public static boolean revisaLaberinto(Lista<String> laberinto){
        Integer nCols = laberinto.get(0).length();
        ///Las rows serán el numero de strings en la lista que contiene al laberinto
        Integer nRows = laberinto.getLongitud();

        for (String linea : laberinto){
            if (linea.length() != nCols){
                return false;
            }

            if (linea.isEmpty()){
                return false;
            }
        }

        return true;

    }



}
