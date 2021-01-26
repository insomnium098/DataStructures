package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para árboles binarios genéricos.</p>
 *
 * <p>La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        public T elemento;
        /** El padre del vértice. */
        public Vertice padre;
        /** El izquierdo del vértice. */
        public Vertice izquierdo;
        /** El derecho del vértice. */
        public Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public Vertice(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * @return <code>true</code> si el vértice tiene padre,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayPadre() {
            // Aquí va su código.

            if (this.padre != null) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * @return <code>true</code> si el vértice tiene izquierdo,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
            // Aquí va su código.
            if (this.izquierdo != null) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * @return <code>true</code> si el vértice tiene derecho,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayDerecho() {
            // Aquí va su código.
            if (this.derecho != null) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Regresa el padre del vértice.
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() {
            // Aquí va su código.
            if (!this.hayPadre()){
                throw new NoSuchElementException("");
            }

            return this.padre;

        }

        /**
         * Regresa el izquierdo del vértice.
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() {
            // Aquí va su código.
            if (!this.hayIzquierdo()){
                throw new NoSuchElementException("");
            }

            return this.izquierdo;
        }

        /**
         * Regresa el derecho del vértice.
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() {
            // Aquí va su código.
            if (!this.hayDerecho()){
                throw new NoSuchElementException("");
            }

            return this.derecho;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.


            return (altura(this));



        }

        //Métodos auxiliares para altura

        public int altura(Vertice vertice){
            if (vertice == null){
                return -1;
            }

            int alt_izq = altura(vertice.izquierdo);
            int alt_der = altura(vertice.derecho);
            return 1 + Math.max(alt_izq, alt_der);
        }


        /////////

        /**
         * Regresa la profundidad del vértice.
         * @return la profundidad del vértice.
         */
        @Override public int profundidad() {
            // Aquí va su código.


            //1 mas la recursion sobre el padre

            return padre_count(this);
        }

        ///Método auxiliar para profundidad
        public int padre_count(Vertice padre_vertice){

            if(padre_vertice.padre == null){
                return 0;
            }

            return 1 + padre_count(padre_vertice.padre);
            /*
            int cont = 0;

            while(this.hayPadre()){
                cont ++;
                padre_count(padre_vertice.padre);
            }
            return cont;

             */

        }



        /**
         * Regresa el elemento al que apunta el vértice.
         * @return el elemento al que apunta el vértice.
         */
        @Override public T get() {
            // Aquí va su código.
            return this.elemento;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)objeto;
            // Aquí va su código.
            //return true;

            Vertice vertice_this = this;

            return equals(vertice_this, vertice);


        }

        public boolean equals (Vertice vertice_1, Vertice vertice_2) {

            if (vertice_1 != null && vertice_2 == null || vertice_1 == null && vertice_2 != null) {
                return false;
            }

            //Caso en el que ambos tienen izquierdo y derecho

            if (vertice_1.hayIzquierdo() && vertice_2.hayIzquierdo() && vertice_1.hayDerecho() && vertice_2.hayDerecho()) {
                if (vertice_1.elemento.equals(vertice_2.elemento)) {
                    if (vertice_1.derecho.equals(vertice_2.derecho) && vertice_1.izquierdo.equals(vertice_2.izquierdo)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            ///Caso donde solo hay izquierdo
            else if (vertice_1.hayIzquierdo() && !vertice_1.hayDerecho() && vertice_2.hayIzquierdo() && !vertice_2.hayDerecho()) {
                if (vertice_1.elemento.equals(vertice_2.elemento)) {
                    if (vertice_1.izquierdo.equals(vertice_2.izquierdo)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            ///Caso donde solo hay derecho
            else if (!vertice_1.hayIzquierdo() && vertice_1.hayDerecho() && !vertice_2.hayIzquierdo() && vertice_2.hayDerecho()) {
                if (vertice_1.elemento.equals(vertice_2.elemento)) {
                    if (vertice_1.derecho.equals(vertice_2.derecho)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            ///Caso donde ninguno tiene izquierdo ni derecho
            else if (!vertice_1.hayIzquierdo() && !vertice_1.hayDerecho() && !vertice_2.hayIzquierdo() && !vertice_2.hayDerecho()) {
                if (vertice_1.elemento.equals(vertice_2.elemento)) {
                    return true;
                } else {
                    return false;
                }
            }

            return false;







        }

        /**
         * Regresa una representación en cadena del vértice.
         * @return una representación en cadena del vértice.
         */
        public String toString() {
            // Aquí va su código.
            return String.valueOf(this.elemento);
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {}

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        // Aquí va su código.


        Iterator<T> it = coleccion.iterator();
        while (it.hasNext()){
            this.agrega(it.next());
        }





    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
        // Aquí va su código.

        if(this.elementos == 0){
            return -1;
        } else {
            return this.raiz.altura();

        }
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return this.elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        if(this.raiz == null){
            return false;
        }


        return busca(elemento) != null;
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <code>null</code>.
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código.

        if(elemento == null){
            return null;
        }

        return (busca(elemento, this.raiz));

    }

    ///Metodo auxiliar recursivo de busca
    public VerticeArbolBinario<T> busca(T elemento, Vertice vertice_lado){

        if(vertice_lado == null){
            return null;
        }

        if (vertice_lado.elemento.equals(elemento)){
            return vertice_lado;
        }

        VerticeArbolBinario<T> ver_izq = busca(elemento,vertice_lado.izquierdo);
        VerticeArbolBinario<T> ver_der = busca(elemento,vertice_lado.derecho);


        if(ver_izq!= null){
            return ver_izq;
        } else {
            return ver_der;
        }


    }





    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
        // Aquí va su código.

        if (this.raiz == null){
            throw new NoSuchElementException("");
        }
        return this.raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.

        if(this.elementos == 0){
            return true;
        }

        return false;

    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
        this.raiz = null;
        this.elementos = 0;
    }

    /**
     * Compara el árbol con un objeto.
     * @param objeto el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
            ArbolBinario<T> arbol = (ArbolBinario<T>)objeto;
        // Aquí va su código.

        if(arbol.esVacia() && this.esVacia()){
            return true;
        }


        return this.raiz.equals(arbol.raiz);
    }


    ///Auxuliar que dibuja espacios

    private String dibujaEspacios(int l, int[] A){
        String s = "";


        for(int i=0; i<l; i++){
            if(A[i] == 1){
                s = s +  "│  ";
            } else{
                s = s +  "   ";
            }
        }

        return s;
    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
        // Aquí va su código.
        if(raiz == null){
            return "";
        }

        int[] A = new int[altura() + 1];

        for(int i=0; i<A.length; i++){
            A[i] = 0;
        }

        return toString_aux(raiz,0,A);

    }




    ///Auxiliar tostring
    private String toString_aux(Vertice vertice, int l, int[] A){
        String s = vertice.toString()+ "\n";

        A[l] = 1;


        if(vertice.hayIzquierdo() && vertice.hayDerecho()){
            s = s +dibujaEspacios(l,A);

            s = s +"├─›";

            s = s +toString_aux(vertice.izquierdo, l+1, A);
            s = s +dibujaEspacios(l, A);
            s = s +"└─»";

            A[l] = 0;
            s = s + toString_aux(vertice.derecho, l+1, A);
        } else if(vertice.hayIzquierdo()){
            s = s +dibujaEspacios(l, A);
            s = s +"└─›";
            A[l] = 0;


            s = s+ toString_aux(vertice.izquierdo, l+1, A);
        } else if(vertice.hayDerecho()){
            s = s +dibujaEspacios(l, A);



            s = s +"└─»";
            A[l] = 0;
            s = s +toString_aux(vertice.derecho, l+1, A);
        }
        return s;
    }



    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice)vertice;
    }
}
