package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        public Iterador() {

            // Aquí va su código.
            //Se crea un vertice temporal y lo inicializa con la raiz
            // y mientras el mismo sea distinto del vacio mete a la
            // pila toda esa rama

            pila = new Pila<Vertice>();
            Vertice vertice = raiz;



            while (vertice != null){
                pila.mete(vertice);
                vertice = vertice.izquierdo;
            }


        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.



            if (!pila.esVacia()){
                return true;
            } else {
                return false;
            }
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            // Aquí va su código.
            //Sacamos el vertice de la pila

            Vertice vertice = pila.saca();
            T elemento = vertice.elemento;
            vertice = vertice.derecho;

            while (vertice != null){
                pila.mete(vertice);
                vertice = vertice.izquierdo;
            }

            return elemento;




        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        //Se compara el elemento que vamos a agregar con la raiz (o el elemento de la lista
        /*  y si es menor se agrega a la izquierda y si es mayor se agrega a la derecha

         */

        if (elemento == null){
            throw new IllegalArgumentException("");
        }

        Vertice vertice = nuevoVertice(elemento);
        this.elementos ++;
        if (this.raiz == null){
            this.raiz = vertice;
            this.ultimoAgregado = raiz;
        } else {
            //vertice actual y vertice nuevo
            agrega_aux(raiz , vertice);
        }


    }

    ///Metodo auxiliar de agrega
    ///Vertice1 es el actual y vertice2 es el nuevo


    public void agrega_aux(Vertice vertice1, Vertice vertice2){

        int comparador = vertice1.elemento.compareTo(vertice2.elemento);

        if(comparador >= 0){
            if(vertice1.izquierdo == null){
                vertice1.izquierdo = vertice2;
                vertice2.padre = vertice1;
                this.ultimoAgregado = vertice2;
            } else {
                agrega_aux(vertice1.izquierdo, vertice2);
            }

        } else {

            if(vertice1.derecho == null){
                vertice1.derecho = vertice2;
                vertice2.padre = vertice1;
                this.ultimoAgregado = vertice2;
            } else {
                agrega_aux(vertice1.derecho, vertice2);
            }

        }


    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        /*
        Puede ser que haya dos hijos. Si el vertice a eliminar tiene dos
        hijos es trivial. Si tiene un solo hijo lo subimos a que reemplaze
        el vertice a eliminar. Si tiene dos hijos, el vertice maximo
        del subarbol izquierdo del vertice a eliminar, ese vertice es el anterior
        al vertice a eliminar. Podemos reemplazar los elementos en estos dos
        vertices y eliminar el que antes era el maximo del subarbol izquierdo.
        Como es el maximo, por definicion no tiene hijo derecho, entonces a lo mas
        tiene un hijo y podemos eliminarlo. Nunca sabemos cual es el hijo que va a
        reemplazar el vertice a eliminar. Si es hoja lo eliminamos y ya. Si tiene un hijo,
        Ese hijo lo subimos. Hay que tener un metodo IntercambiaEliminable
         */

        Vertice eliminar = vertice(busca(elemento));

        if (eliminar == null){
            return;
        }

        this.elementos --;

        if(eliminar.hayIzquierdo() && eliminar.hayDerecho()){
            Vertice eliminable = intercambiaEliminable(eliminar);
            eliminaVertice(eliminable);
        } else {
            eliminaVertice(eliminar);
        }




    }

    public Vertice MaximoEnSubarbol(Vertice vertice){
        if (!vertice.hayDerecho()){
            return vertice;
        } else {
            return MaximoEnSubarbol(vertice.derecho);
        }

    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        // Aquí va su código.
        //return null;

        Vertice maximo = MaximoEnSubarbol(vertice.izquierdo);
        vertice.elemento = maximo.elemento;
        return maximo;



    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        // Aquí va su código.


        ///Primero revisar si el vertice tiene padre, si no entonces es la raiz

        //Caso donde es la raiz

        if(this.raiz == vertice){
            ///Caso donde no hay hijos
            if(!vertice.hayIzquierdo() && !vertice.hayDerecho()){
                this.raiz = null;
                //Caso donde hay hijos, determinarlos
            } else if (vertice.hayIzquierdo()){
                this.raiz = vertice.izquierdo;
                vertice.izquierdo.padre = null;
            } else if (vertice.hayDerecho()){
                this.raiz = vertice.derecho;
                vertice.derecho.padre = null;
            }

        ///Caso donde no es la raiz
        } else {
            ///Determinar si el vertice es izquierdo o derecho
            if (vertice.padre.hayIzquierdo() && vertice.padre.izquierdo == vertice){
                ///Determinar si el hijo del vertice es derecho o izquierdo
                if(vertice.hayIzquierdo()){
                    vertice.padre.izquierdo = vertice.izquierdo;
                    vertice.izquierdo.padre = vertice.padre;
                } else if (vertice.hayDerecho()){
                    vertice.padre.izquierdo = vertice.derecho;
                    vertice.derecho.padre = vertice.padre;

                //Caso donde no hay hijos
                } else if (!vertice.hayIzquierdo() && !vertice.hayDerecho()){
                    if (vertice.padre.hayIzquierdo() && vertice.padre.izquierdo == vertice){
                        vertice.padre.izquierdo = null;
                    } else {
                        vertice.padre.derecho = null;
                    }

                }

            } else if (vertice.padre.hayDerecho() && vertice.padre.derecho == vertice){
                ///Determinar si el hijo del vertice es derecho o izquierdo
                if(vertice.hayIzquierdo()){
                    vertice.padre.derecho = vertice.izquierdo;
                    vertice.izquierdo.padre = vertice.padre;
                } else if (vertice.hayDerecho()){
                    vertice.padre.derecho = vertice.derecho;
                    vertice.derecho.padre = vertice.padre;


                //Caso donde no hay hijos
                } else if (!vertice.hayIzquierdo() && !vertice.hayDerecho()){
                    if (vertice.padre.hayIzquierdo() && vertice.padre.izquierdo == vertice){
                        vertice.padre.izquierdo = null;
                    } else {
                        vertice.padre.derecho = null;
                    }

                }


            }



        }




    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código.
        /*
        Podemos usar busqueda binaria. Primero checamos con la raiz el elemento
        que estamos buscando. Si es la raiz regresamos la raiz. Si es menor,
        recursivamente busca por la izquierda y si es mayor, recursivamente busca por la
        derecha. Donde si se llega a un vertice vacio regresamos null
         */


        //int comparador = vertice1.elemento.compareTo(vertice2.elemento);

        Vertice busca = busca_aux(elemento, raiz);
        return busca;


    }

    public  Vertice busca_aux(T elemento, Vertice vertice){

        if (vertice == null || elemento == null){
            return null;
        }
        int comparador = elemento.compareTo(vertice.elemento);

        if(comparador == 0){
            return vertice;
        } else if (comparador < 0){
            return busca_aux(elemento, vertice.izquierdo);
        }


        return busca_aux(elemento, vertice.derecho);


    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * /agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        // Aquí va su código.

        return this.ultimoAgregado;
    }




    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        /*
        Para girar un vertice a la derecha , el vertice tiene que tener un
        hijo izquierdo. Y para girarlo a la izquierda el vertice tiene que tener un hijo
        a la derecha
         */

        if (!vertice.hayIzquierdo() || vertice == null){
             return;
        }

        //El hijo izquierdo p existe y se se vuelve padre de vertice, mientras
        //que vertice se vuelve el hijo derecho de p.
        //Si existe el hijo derecho de p se convierte izquierdo de vertice

        Vertice vertice_orig = vertice(vertice);
        Vertice p = vertice_orig.izquierdo;

        p.padre = vertice_orig.padre;

        if(vertice_orig == this.raiz){
            raiz = p;
        } else if(vertice_orig.padre.izquierdo == vertice_orig )
            vertice_orig.padre.izquierdo = p;
        else {
            vertice_orig.padre.derecho = p;
        }

        vertice_orig.izquierdo = p.derecho;
        if(p.hayDerecho()){
            p.derecho.padre = vertice_orig;
        }

        p.derecho = vertice_orig;
        vertice_orig.padre = p;



    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if (!vertice.hayDerecho() || vertice == null){
            return;
        }



        Vertice vertice_orig = vertice(vertice);
        Vertice q = vertice_orig.derecho;

        q.padre = vertice_orig.padre;

        if(this.raiz == vertice_orig ){
            raiz = q;
        } else if(vertice_orig.padre.izquierdo == vertice_orig )
            vertice_orig.padre.izquierdo = q;
        else {
            vertice_orig.padre.derecho = q;
        }

        vertice_orig.derecho = q.izquierdo;
        if(q.hayIzquierdo()){
            q.izquierdo.padre = vertice_orig;
        }

        q.izquierdo = vertice_orig;
        vertice_orig.padre = q;
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsPreOrder(accion, this.raiz);


    }

    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion ,Vertice vertice){
        if (vertice == null){
            return;
        }

        accion.actua(vertice);
        dfsPreOrder(accion, vertice.izquierdo);
        dfsPreOrder(accion, vertice.derecho);


    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsInOrder(accion, raiz);

    }
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion, Vertice vertice) {
        // Aquí va su código.

        if(vertice == null){
            return;
        }
        dfsInOrder(accion, vertice.izquierdo);
        accion.actua(vertice);
        dfsInOrder(accion, vertice.derecho);

    }



    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsPostOrder(accion, raiz);

    }

    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion, Vertice vertice) {
        // Aquí va su código.

        if(vertice == null){
            return;
        }

        dfsPostOrder(accion, vertice.izquierdo);
        dfsPostOrder(accion, vertice.derecho);
        accion.actua(vertice);


    }



    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
