package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {

            // Aquí va su código.
            if(indice < elementos && arbol[indice]!= null){
                return true;
            } else {
                return false;
            }


        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {

            // Aquí va su código.
            if (indice >= elementos){
                throw new NoSuchElementException();
            }
            return arbol[indice ++];


        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            this.indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return this.indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            // Aquí va su código.
            return this.elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        // Aquí va su código.
        this.arbol = nuevoArreglo(200);
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        // Aquí va su código.
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {


        // Aquí va su código.
        this.elementos = n;
        int indice = 0;

        this.arbol = nuevoArreglo(n);
        //Se agregan en orden los elementos al arreglo
        for( T e : iterable){
            arbol[indice] = e;
            e.setIndice(indice);
            indice++;

        }

        ///Usar auxiliar acomodaAbajo
        //Se recorre desde el indice ((n/2) - 1) hasta 0

        for (int i = (elementos/2) - 1; i>= 0; i--){
            acomodaAbajo(arbol[i]);

        }





    }

    /// Acomoda abajo

    public void acomodaAbajo(T elemento){
        ///Primero revisar que el elemento sea valido
        if(elemento == null){
            return;
        }

        ///Falta implementar metodo que revise si el indice del elemento es valido
        //if(elemento.getIndice() < 0 || elemento.getIndice() >= elementos){
        //    return;
        //}
        if(!validaIndice(elemento.getIndice())){
            return;
        }

        ///Obtener el indice de los elementos izquierdo y derecho del elemento
        int izq = elemento.getIndice() * 2 + 1;
        int der = elemento.getIndice() * 2 + 2;

        ///Verificar que los indices izq y der sean validos, si no entonces terminamos
        if(!validaIndice(izq) && !validaIndice(der)){
            return;
        }

        //Obtener el elemento minimo

        int index_minimo = 0;


        ///Caso 1, hay izquierdo y derecho

        if(validaIndice(izq) && validaIndice(der)){
            if(arbol[izq].compareTo(arbol[der]) < 0){
                index_minimo = izq;
            } else{
                index_minimo = der;
            }
        } else {
            index_minimo = izq;
        }



        //Si el elemento es mayor que el minimo se hace el intercambio
        // y se hace recursion sobre el elemento

        if(elemento.compareTo(arbol[index_minimo]) > 0){
            intercambia(elemento, arbol[index_minimo]);
            acomodaAbajo(elemento);

        }


    }

    ///Acomoda hacia arriba

    private void acomodaArriba (T elemento){
        //Obtener el indice del padre
        int padre = (elemento.getIndice() -1);

        if(padre != -1){
            padre = padre /2;
        }


        //Validar el indice
        if(!validaIndice(padre)){
            return;
        }


        //Si el padre ya es menor que el elemento terminamos
        if(arbol[padre].compareTo(elemento) < 0){
            return;
        }

        intercambia(arbol[padre], elemento);
        acomodaArriba(elemento);


    }

    ///Intercambiar dos elementos


    private void intercambia (T a, T b){
        int a_aux = a.getIndice();
        int b_index = b.getIndice();

        arbol[a.getIndice()] = b;
        arbol[b.getIndice()] = a;
        a.setIndice(b_index);
        b.setIndice(a_aux);
    }



    //Revisar que el indice recibido sea valido

    private boolean validaIndice (int i){

        if (i < 0 || i >= elementos){
            return false;
        } else {
            return true;
        }


    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {

        // Aquí va su código.
        //Revisar si el numero de elementos del monticulo
        //es igual al tamaño del arreglo
        if(elementos == arbol.length){
            T[] nuevo_arbol = nuevoArreglo(arbol.length * 2);

            //Agregar los elementos del arbol viejo al nuevo

            for (int i = 0; i < elementos; i++){
                nuevo_arbol[i] = arbol[i];
            }

            this.arbol = nuevo_arbol;
        }

        arbol[elementos] = elemento;
        elemento.setIndice(elementos);
        this.elementos ++;
        acomodaArriba(elemento);




    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {


        // Aquí va su código.
        if(esVacia()){
            throw new IllegalStateException();
        }



        //Obtenemos la raiz
        T raiz = arbol[0];
        //Intercambiamos la raiz con el ultimo elemento y decrementamos
        intercambia(raiz, arbol[elementos - 1]);

        this.elementos --;

        //Hacemos null al ultimo elemento
        arbol[elementos].setIndice(-1);
        arbol[elementos] = null;
        acomodaAbajo(arbol[0]);

        return raiz;




    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {



        // Aquí va su código.
        int index_elemento = elemento.getIndice();
        if(!validaIndice(index_elemento)){
            return;
        }

        //Intercambiamos el elemento con el ultimo
        intercambia(arbol[index_elemento], arbol[elementos-1]);
        this.elementos --;
        //Hacemos null al elemento a eliminar
        elemento.setIndice(-1);

        //Acomodamos hacia arriba o hacia abajo con reordena
        reordena(arbol[index_elemento]);





    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        int index_elemento = elemento.getIndice();

        if(!validaIndice(index_elemento)){
            return false;
        }

        if(arbol[index_elemento].compareTo(elemento) == 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        if( this.elementos > 0){
            return false;
        } else {
            return true;
        }

    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
        for (int i = 0; i < elementos; i ++){
            arbol[i] = null;
        }
        elementos = 0;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        // Aquí va su código.

        int padre_index = (elemento.getIndice() -1) / 2;
        //Caso 1, no tiene padre
        if(!validaIndice(padre_index)){
            acomodaAbajo(elemento);
        } else //Caso 2: El elemento es mayor que sus hijos
            if(arbol[padre_index].compareTo(elemento) <= 0){
                acomodaAbajo(elemento);
            } else {
                acomodaArriba(elemento);
            }



    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return this.elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        // Aquí va su código.

        if(!validaIndice(i)){
            throw new NoSuchElementException();
        }

        return arbol[i];


    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        // Aquí va su código.
        String string = "";

        for(int i = 0; i < arbol.length; i++)
            string += arbol[i] + ", ";

        return string;


    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
        // Aquí va su código.


        //Revisar por numero de elementos
        if(monticulo.elementos != this.elementos){
            return false;
        }

        //Revisar elemento por elemento
        for (int i = 0; i < this.elementos; i++){
            if(!arbol[i].equals(monticulo.get(i))){
                return false;
            }
        }
        return true;


    }


    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
        // Aquí va su código.

        ///Crear lista con adaptadores, cada
        //adaptador contiene un elemento de la coleccion
        Lista<Adaptador<T>> lista1 = new Lista<>();

        for (T elemento : coleccion){
            lista1.agregaFinal(new Adaptador<>(elemento));
        }

        //Crear lista de elementos del tipo de la coleccion
        Lista<T> lista2 = new Lista<>();

        ///Crear monticulo minimo con la lista1
        MonticuloMinimo<Adaptador<T>> monticulo_min = new MonticuloMinimo<>(lista1);

        ///Mientras el monticulo tenga elementos, eliminar raiz y agregar el elemento a l2
        while(!monticulo_min.esVacia()){
            //Usamos elimina() ya que el minimo es la raiz
            lista2.agregaFinal(monticulo_min.elimina().elemento);
        }

        return lista2;


    }
}
