package mx.unam.ciencias.edd.proyecto3;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Random;
import mx.unam.ciencias.edd.*;

/**
 * Proyecto 3.
 */
public class Proyecto3 {

    public static Boolean hayCarpeta = false;
    public static String carpeta = null;



    /* Imprime el uso del programa y lo termina. */


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
                System.out.println("Hay carpeta");
                continue;
            }
            archivos.agrega(args[i]);
            i++;
        }

        if(hayCarpeta){
            System.out.println("La carpeta es:");
            System.out.println(carpeta);
        }




        for (String s : archivos){
            System.out.println(s);
        }


        /*

        Diccionario<Lista<String>, String> dictest = new Diccionario<>(10);
        Lista<String> lista1 = new Lista<>();
        lista1.agrega("Hola1");
        lista1.agrega("Hola2");
        lista1.agrega("Hola3");
        lista1.agrega("Hola4");

        dictest.agrega(lista1,"Lista1");
        dictest.agrega(archivos,"Archivos");

        if(dictest.contiene(lista1)){
            System.out.println("SI LA CONTIENE");


        Iterator<Lista<String>> itLlaves = dictest.iteradorLlaves();
        while(itLlaves.hasNext()){
            Lista<String> lis = itLlaves.next();
            //System.out.println(lis.toString());
            for (String a : lis){
                System.out.println(a);
            }
        }
        }

         */

        ///Hacer diccionario con llave que sea un string

        /*
        Diccionario<String, Lista<String>> dictest = new Diccionario<>(10);
        Lista<String> lista1 = new Lista<>();
        lista1.agrega("Hola1");
        lista1.agrega("Hola2");
        lista1.agrega("Hola3");
        lista1.agrega("Hola4");

        dictest.agrega("ListaHola",lista1);
        dictest.agrega("NombresDeArchivos",archivos);

        Iterator<String> itLllaves = dictest.iteradorLlaves();
        while(itLllaves.hasNext()){
            System.out.println(itLllaves.next());
        }

        if(dictest.contiene("ListaHola")){
            Lista<String> listtest = dictest.get("ListaHola");
            listtest.agrega("HolaMIIIIL");
            dictest.agrega("ListaHola",listtest);
        }


        for (Lista<String> aa : dictest){
            System.out.println(aa.toString());
        }

         */

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
            System.out.println("Palabras con longitud igual o mayor que 7");
            System.out.println(ls.toString());
        }

        ConjuntoArchivosTexto Index = new ConjuntoArchivosTexto(diccionarioPalabras7);
        Index.imprimeGrafo();


        /*

        Conjunto<String> conjuntoA = new Conjunto<>();
        Conjunto<String> conjuntoB = new Conjunto<>();

        conjuntoA.agrega("A");
        conjuntoA.agrega("B");
        conjuntoA.agrega("C");

        conjuntoB.agrega("A");
        conjuntoB.agrega("B");
        conjuntoB.agrega("X");

        Conjunto<String> union = conjuntoA.interseccion(conjuntoB);
        System.out.println("La union es:");
        System.out.println(union.toString());

         */





        //ArchivoTexto test = new ArchivoTexto(archivos.getPrimero());
        //test.procesaArchivo();

        /*
        Iterator<String> itLllaves2 = dictest.iteradorLlaves();
        while(itLllaves2.hasNext()){
            System.out.println(itLllaves2.next());
        }

         */













        /*
        for (String s : dictest){
            System.out.println(s);
        }

         */




        /*
        a = AlgoritmoDispersor.DJB_STRING;
        Dispersor<String> djb = FabricaDispersores.dispersorCadena(a);
        Diccionario<String, String> diccDJB =
            new Diccionario<String, String>(N, djb);
        tiempoInicial = System.nanoTime();
        for (int i = 0; i < N; i++)
            diccDJB.agrega(arreglo[i], arreglo[i]);
        tiempoTotal = System.nanoTime() - tiempoInicial;
        System.out.printf("%2.9f segundos en llenar un diccionario " +
                          "con %s elementos (dispersor Daniel Bernstein).\n",
                          (tiempoTotal/1000000000.0), nf.format(N));
        System.out.printf("\t%d colisiones, %d colisión máxima\n",
                          diccDJB.colisiones(), diccDJB.colisionMaxima());

        a = AlgoritmoDispersor.XOR_STRING;
        Dispersor<String> xor = FabricaDispersores.dispersorCadena(a);
        Diccionario<String, String> diccXOR =
            new Diccionario<String, String>(N, xor);
        tiempoInicial = System.nanoTime();
        for (int i = 0; i < N; i++)
            diccXOR.agrega(arreglo[i], arreglo[i]);
        tiempoTotal = System.nanoTime() - tiempoInicial;
        System.out.printf("%2.9f segundos en llenar un diccionario " +
                          "con %s elementos (dispersor XOR).\n",
                          (tiempoTotal/1000000000.0), nf.format(N));
        System.out.printf("\t%d colisiones, %d colisión máxima\n",
                          diccXOR.colisiones(), diccXOR.colisionMaxima());

         */
    }


    /*
    Metodo que recibe el nombre de un archivo, lo lee, procesa y devuelve un diccionario con las palabras
     */

    public static Diccionario<String, String> procesaArchivo (String nombreArchivo){
        return null;
    }


}
