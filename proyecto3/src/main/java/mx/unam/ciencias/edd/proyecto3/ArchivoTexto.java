package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.*;

import java.io.*;
import java.util.Iterator;

public class ArchivoTexto {

    private final String nombreArchivo;
    private Diccionario<String, Integer> conteoPalabras;
    private Diccionario<String,Integer> listaPalabrasTop5;
    private Diccionario<String,Integer> listaPalabrasTop15;
    private String lineaElementos;
    private final Conjunto<String> conjunto;
    private String[] elementosArray;
    private Integer palabrasTotales;

    public ArchivoTexto(String nombreArchivo) throws IOException {
        this.nombreArchivo = nombreArchivo;
        this.conteoPalabras = new Diccionario<>();
        this.listaPalabrasTop5 = new Diccionario<>();
        this.listaPalabrasTop15 = new Diccionario<>();
        this.lineaElementos = null;
        this.palabrasTotales = null;
        this.conjunto = new Conjunto<>();
        this.procesaArchivo();
        this.creaConjunto();
        this.calculaTop5Palabras();
        this.calculaTop15Palabras();
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

        try {
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
            this.elementosArray = elementos_split;

            //Despues las agregamos al diccionario conteoPalabras
            int contadorPalabras = 0;

            for (String s : elementos_split){
                //Primero revisamos si el elemento ya existe en el diccionario
                if (!conteoPalabras.contiene(s)){
                    //Lo agregamos y lo inicializamos en 1
                    conteoPalabras.agrega(s,1);
                } else {
                    //En caso contrario obtenemos su valor, le sumamos 1 y lo volvemos a agregar
                    Integer valor = conteoPalabras.get(s);
                    valor += 1;
                    conteoPalabras.agrega(s,valor);
                }

                contadorPalabras +=1;
            }

            this.palabrasTotales = contadorPalabras;

        } catch (Exception e){
            System.out.println("No se pudo leer el archivo: " + nombreArchivo);
            System.out.println("No tienes los permisos para leer el archivo ó este no existe");
            System.out.println("Saliendo del programa");
            System.exit(1);
        }




    }

    /*
    Metodo para imprimir el diccionario conteopalabras  con to String
     */

    public void imprimeDiccionario(){


        /*

        System.out.println("Las llaves");
        Iterator<String> llaves = conteoPalabras.iteradorLlaves();
        while(llaves.hasNext()){
            System.out.println(llaves.next());
        }


        System.out.println("Los valores:");
        for (Integer s : conteoPalabras){
            System.out.println(s);
        }
        
         */


        System.out.println(conteoPalabras.toString());



    }

    /*
    Metodo para generar el numero de apariciones de cada palabra
     */

    public String imprimeConteo(){
        String palabras = conteoPalabras.toString();
        palabras = palabras.replaceAll("\\{", "Palabras: ");
        palabras = palabras.replaceAll("\\}", "");
        palabras = palabras.replaceAll(":", ": ");
        palabras = palabras.replaceAll(",", ", ");
        palabras = palabras.replaceAll(" '", "");
        palabras = palabras.replaceAll("'", " ");


        //System.out.println(palabras);
        return palabras;

        
    }

    private void creaConjunto() {
        //Conjunto<String> conjunto1 = new Conjunto<>();

        //Agregamos las llaves al conjunto
        Iterator<String> llaves = conteoPalabras.iteradorLlaves();
        while (llaves.hasNext()) {
            this.conjunto.agrega(llaves.next());
        }

        //System.out.println("El conjunto:");
        //System.out.println(this.conjunto.toString());

    }

    /*
    Metodo que devuelve el conjunto
     */

    public Conjunto<String> getConjunto(){
        return this.conjunto;
    }

    /*
    Metodo que devuelve las palabras con más de 7 caracteres en una Lista
     */

    public Lista<String> getPalabrasLista(){

        Lista<String> palabras = new Lista<>();

        for (String s : this.elementosArray){
            if (s.length() >= 7){
                palabras.agrega(s);
            }
        }

        return palabras;


    }

    /*
    Metodo que calcula el top 5 de palabras
     */

    public void calculaTop5Palabras() {
        Diccionario<String, Integer> conteo = this.conteoPalabras;

        if(this.conteoPalabras.getElementos() <= 5){
            this.listaPalabrasTop5 = this.conteoPalabras;
            return;
        }

        /*
        Inicializamos un array con los valores
         */

        Integer[] arregloValores = new Integer[conteo.getElementos()];
        //Le agregamos los valores
        int contador = 0;
        for (Integer i : conteo){
            arregloValores[contador] = i;
            contador++;
        }

        ///Lo ordenamos
        Arreglos.quickSort(arregloValores);

        //Obtenemos los 5 primeros haciendo una lista

        Lista<Integer> listaMayoresInt = new Lista<>();

        int contadorArray = 1;
        while (contadorArray < arregloValores.length ){
            if (contadorArray >5){
                break;
            }
            listaMayoresInt.agrega(arregloValores[arregloValores.length-contadorArray]);
            contadorArray++;
        }



        Diccionario<String,Integer> diccionarioTop5 = new Diccionario<>();




        Iterator<String> itLlaves = conteo.iteradorLlaves();
        while (itLlaves.hasNext()){
            String llave = itLlaves.next();
            Integer valor = conteo.get(llave);
            if(listaMayoresInt.contiene(valor)){
                diccionarioTop5.agrega(llave, valor);
                listaMayoresInt.elimina(valor);
            }
        }

        this.listaPalabrasTop5 = diccionarioTop5;

    }

    public Diccionario<String,Integer> getListaPalabrasTop5(){
        return this.listaPalabrasTop5;
    }



    /*
    Metodo que devuelve el diccionario conteoPalabras, donde el string llave es la palabra
    y el int valor es el numero de apariciones
     */

    public Diccionario<String, Integer> getConteoPalabras() {
        return conteoPalabras;
    }

    public Integer getPalabrasTotales() {
        return palabrasTotales;
    }

    public String getNombreArchivo(){
        return this.nombreArchivo;
    }


    /*
    Metodo que calcula el Top15 palabras
     */

    public void calculaTop15Palabras() {
        Diccionario<String, Integer> conteo = this.conteoPalabras;

        if(this.conteoPalabras.getElementos() <= 15){
            this.listaPalabrasTop15 = this.conteoPalabras;
            return;
        }

        /*
        Inicializamos un array con los valores
         */

        Integer[] arregloValores = new Integer[conteo.getElementos()];
        //Le agregamos los valores
        int contador = 0;
        for (Integer i : conteo){
            arregloValores[contador] = i;
            contador++;
        }

        ///Lo ordenamos
        Arreglos.quickSort(arregloValores);

        //Obtenemos los 5 primeros haciendo una lista

        Lista<Integer> listaMayoresInt = new Lista<>();

        int contadorArray = 1;
        while (contadorArray < arregloValores.length ){
            if (contadorArray > 15){
                break;
            }
            listaMayoresInt.agrega(arregloValores[arregloValores.length-contadorArray]);
            contadorArray++;
        }



        Diccionario<String,Integer> diccionarioTop15 = new Diccionario<>();




        Iterator<String> itLlaves = conteo.iteradorLlaves();
        while (itLlaves.hasNext()){
            String llave = itLlaves.next();
            Integer valor = conteo.get(llave);
            if(listaMayoresInt.contiene(valor)){
                diccionarioTop15.agrega(llave, valor);
                listaMayoresInt.elimina(valor);
            }
        }

        this.listaPalabrasTop15 = diccionarioTop15;

    }

    public Diccionario<String, Integer> getListaPalabrasTop15(){
        return this.listaPalabrasTop15;
    }
}
