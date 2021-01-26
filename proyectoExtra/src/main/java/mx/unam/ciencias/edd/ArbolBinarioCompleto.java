package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        public Iterador() {
            // Aquí va su código.
            cola = new Cola<Vertice>();
            if( raiz!=null){
                cola.mete(raiz);
            }

        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            if (cola.esVacia()){
                return false;
            } else {
                return true;
            }
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            // Aquí va su código.

            if(cola.esVacia()){
                throw new NoSuchElementException("ErrorCastroso");
            }



            Vertice v = cola.saca();
            if (v.izquierdo != null){
                cola.mete(v.izquierdo);
            }

            if (v.derecho != null){
                cola.mete(v.derecho);
            }
            return v.elemento;


        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {


        if (elemento == null){
            throw new IllegalArgumentException("");
        }
        Vertice nuevo = nuevoVertice(elemento);

        if (this.raiz == null)
            raiz  = nuevo;
        else {
            Vertice v = raiz;
            Cola<ArbolBinario<T>.Vertice> cola = new Cola<>();
            cola.mete(v);
            while (!cola.esVacia()) {
                v = cola.saca();
                if ( !v.hayIzquierdo() || !v.hayDerecho()) {
                    nuevo.padre = v;
                    if (!v.hayIzquierdo())
                        v.izquierdo = nuevo;
                    else if (!v.hayDerecho())
                        v.derecho = nuevo;
                    break;
                }
                cola.mete(v.izquierdo);
                cola.mete(v.derecho);
            }
        }
        elementos++;


    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        //Intercambiar elemento a eliminar por el
        //ultimo y eliminar ese. usar bfs

        Vertice eliminar =  vertice(this.busca(elemento));

        if(eliminar == null){
            return;
        }

        elementos--;
        if(elementos == 0){
            this.raiz = null;
            return;
        }

        ///Buscar ultimo nodo de la cola
        ///mediante bfs

        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(this.raiz);
        Vertice ultimo = nuevoVertice(this.raiz.elemento);
        while(!cola.esVacia()){
            ultimo = cola.saca();
            if (ultimo.hayIzquierdo()){
                cola.mete(ultimo.izquierdo);
            }
            if (ultimo.hayDerecho()){
                cola.mete(ultimo.derecho);
            }
        }

        ///Intercambiar elementos
        ///No nos interesa el elemento de eliminar
        //solo el del ultimo elemento
        eliminar.elemento = ultimo.elemento;
        ///Modificamos al padre del ultimo

        if(ultimo.padre.izquierdo == ultimo){
            ultimo.padre.izquierdo = null;
        } else if (ultimo.padre.derecho == ultimo){
            ultimo.padre.derecho = null;
        }


    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        // Aquí va su código.

        if(this.elementos==0){
            return -1;
        }
        int alt = (int)(Math.floor(Math.log(this.elementos) / Math.log(2)));
        return alt;


    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        //Si es null terminamos, si no hacer
        //cola de vertices, meter raiz a la cola
        // y mientras la cola no sea vacia sacamos
        // el elemento de la cola y realizamos la accion.
        //Si hay vertice izquierdo o derecho  lo metemos a la cola
        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(this.raiz);
        while (!cola.esVacia()) {
            Vertice vertice = cola.saca();
            accion.actua(vertice);
            if (vertice.izquierdo != null) {
                cola.mete(vertice.izquierdo);
            }
            if (vertice.derecho != null) {
                cola.mete(vertice.derecho);
            }

        }



    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
