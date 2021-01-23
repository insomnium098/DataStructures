package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.*;
import java.util.Iterator;

public class ConjuntoArchivosTexto {
    private Diccionario<String, Conjunto<String>> diccionario;
    private Grafica<String> grafica;

    public ConjuntoArchivosTexto(Diccionario<String, Conjunto<String>> diccionario){
        this.diccionario = diccionario;
        this.grafica = new Grafica<>();
        inicializaGrafo();
        generaGrafo();
    }

    public Grafica<String> getGrafica() {
        return grafica;
    }

    /*
    Metodo para inicializar la grafica. Se recorren las llaves del diccionario y se agregan al grafo
     */

    private void inicializaGrafo(){
        Iterator<String> llaves = diccionario.iteradorLlaves();
        while(llaves.hasNext()) {
            grafica.agrega(llaves.next());
        }

    }

    /*
    Metodo para generar el grafo. Se debe de recorrer cada archivo y su lista con los demas archivos
     */
    private void generaGrafo(){
        Iterator<String> llaves = diccionario.iteradorLlaves();
        while(llaves.hasNext()){
            String s = llaves.next();
            //Obtenemos el conjunto de palabras de ese archivo
            Conjunto<String> conjuntoArchivo = diccionario.get(s);
            //Hacemos un segundo iterador para recorrer todos los conjuntos
            Iterator<String> llaves2 = diccionario.iteradorLlaves();
            while (llaves2.hasNext()){
                String elemento2 = llaves2.next();
                Conjunto<String> conjuntoAux = diccionario.get(elemento2);
                ///Hacemos la interseccion
                Conjunto<String> interseccion = conjuntoArchivo.interseccion(conjuntoAux);
                ///Si la interseccion no es vacia conectamos a los elementos
                if (!interseccion.esVacia()){

                    //Revisamos si los elementos ya han sido conectados
                    //o si son el mismo elemento, en caso contrario se agregan
                    if(grafica.sonVecinos(s,elemento2) || s.equals(elemento2)){
                        continue;
                    }
                    grafica.conecta(s,elemento2);
                }
            }


        }

    }

    public void imprime(){
        System.out.println(diccionario.toString());
    }

    public void imprimeGrafo(){
        System.out.println("Imprimiendo el grafo");
        System.out.println(grafica.toString());
    }
}
