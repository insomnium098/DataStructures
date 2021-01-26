package mx.unam.ciencias.edd.proyecto3;

import java.io.IOException;
import mx.unam.ciencias.edd.*;
import java.io.File;

/**
 * Proyecto 3.
 */
public class Proyecto3 {

    public static Boolean hayCarpeta = false;
    public static String carpeta = null;

    private static void uso() {
        System.err.println("Debes de ingresar al menos un archivo");
        System.exit(1);
    }



    public static void main(String[] args) throws IOException {

        if (args.length == 0)
            uso();



        int i = 0;
        ///La lista de archivos va a contener los nombres de los archivos

        Lista<String> archivos = new Lista<>();

        while (i < args.length){
            String s = args[i];

            if (s.equals("-o")){
                carpeta = args[i+1];
                hayCarpeta = true;
                i+=2;
                continue;
            }
            archivos.agrega(args[i]);
            i++;
        }

        if(hayCarpeta){
            boolean carpetaCreada = false;
            File file = new File(carpeta);

            ///Revisamos si la carpeta existe
            if (!file.exists()){
                carpetaCreada = file.mkdir();
                if(!carpetaCreada){
                    System.out.println("La carpeta no pudo ser creada, no existe o no tienes los permisos necesarios");
                    System.out.println("Saliendo del programa");
                    System.exit(1);
                }
            }

        } else {
            System.out.println("No se especificó una carpeta, saliendo.");
            System.exit(1);
        }

        if (archivos.esVacia()){
            System.out.println("No ingresaste ningun archivo, saliendo del programa");
            System.exit(1);
        }

        /*
        Hacer un diccionario cuyas llaves sean String y los nombres de los archivos y como
        valores serán objetos de la clase ArchivoTexto
         */

        Diccionario<String,ArchivoTexto> dicc = new Diccionario<>();


        /*
        Recorremos la lista de archivos, generamos archivosTexto y los agregamos al diccionario
        Este diccionaro contiene a todos los archivos

        Tambien creamos un diccionario con el nombre de los archivos y un conjunto de sus palabras mayores de 7

         */

        Diccionario<String, Conjunto<String>> diccionarioPalabras7 = new Diccionario<>();


        for (String s : archivos){
            ArchivoTexto aux = new ArchivoTexto(s);
            dicc.agrega(s,aux);
            Lista<String> ls = aux.getPalabrasLista();
            Conjunto<String> cs = new Conjunto<>();
            for (String palabra : ls){
                cs.agrega(palabra);
            }
            diccionarioPalabras7.agrega(s,cs);

        }

        //Aqui se imprimen los archivos HTML
        ////dicc contiene los ArchivosTexto, sobre estos hay que generar el HTML
        GeneraHTML archivosHMTL = new GeneraHTML(dicc, carpeta);

        ///Aqui se imprime el indice

        ConjuntoArchivosTexto Index = new ConjuntoArchivosTexto(diccionarioPalabras7);
        Grafica<String> grafica = Index.getGrafica();
        Lista<String> palabrasCompartidas = Index.getPalabrasCompartidas();
        archivosHMTL.GeneraIndice(grafica, palabrasCompartidas);

    }



}
