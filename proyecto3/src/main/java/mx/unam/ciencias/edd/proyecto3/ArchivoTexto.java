package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.*;

import java.io.*;
import java.util.Iterator;

public class ArchivoTexto {

    private String nombreArchivo;
    private Diccionario<String, Integer> conteoPalabras;
    private Diccionario<String,Integer> listaPalabrasTop5;
    private String lineaElementos;
    private Conjunto<String> conjunto;
    private String[] elementosArray;

    public ArchivoTexto(String nombreArchivo) throws IOException {
        this.nombreArchivo = nombreArchivo;
        this.conteoPalabras = new Diccionario<>();
        this.listaPalabrasTop5 = new Diccionario<>();
        this.lineaElementos = null;
        this.conjunto = new Conjunto<>();
        this.procesaArchivo();
        this.creaConjunto();
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
        this.elementosArray = elementos_split;

        //Despues las agregamos al diccionario conteoPalabras

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
        }


        //imprimeDiccionario();
        //imprimeConteo();

        

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
    Metodo para imprimir el numero de apariciones de cada palabra
     */

    public void imprimeConteo(){
        String palabras = conteoPalabras.toString();
        palabras = palabras.replaceAll("\\{", "Lista de apariciones de las palabras:");
        palabras = palabras.replaceAll("\\}", "");
        //palabras = palabras.replaceAll(":", "apareció:");
        //palabras = palabras.replaceAll(",", "veces,");
        palabras = palabras.replaceAll(" '", "");
        palabras = palabras.replaceAll("'", " ");



        System.out.println(palabras);

        
    }

    private void creaConjunto() {
        //Conjunto<String> conjunto1 = new Conjunto<>();

        //Agregamos las llaves al conjunto
        Iterator<String> llaves = conteoPalabras.iteradorLlaves();
        while (llaves.hasNext()) {
            this.conjunto.agrega(llaves.next());
        }

        System.out.println("El conjunto:");
        System.out.println(this.conjunto.toString());

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




}
