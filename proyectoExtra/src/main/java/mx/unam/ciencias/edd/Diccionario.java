package mx.unam.ciencias.edd;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, mapeando un conjunto de <em>llaves</em> a una colección
 * de <em>valores</em>.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /* Clase interna privada para entradas. */
    private class Entrada {

        /* La llave. */
        public K llave;
        /* El valor. */
        public V valor;

        /* Construye una nueva entrada. */
        public Entrada(K llave, V valor) {
            // Aquí va su código.
            this.llave=llave;
            this.valor = valor;

        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador {

        /* En qué lista estamos. */
        private int indice;
        /* Iterador auxiliar. */
        private Iterator<Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
        public Iterador() {
            // Aquí va su código.
            /*
            Debemos de recorrer todo el arreglo para encontrar la primer entrada
            que no sea null
             */


            for (int i=0; i < entradas.length; i++){
                if (entradas[i]!= null){
                    indice = i;
                    //Hacemos el iterador como el iterador de esa lista
                    iterador = entradas[i].iteradorLista();
                    return;
                } else {
                    iterador = null;
                }
            }




        }

        /* Nos dice si hay una siguiente entrada. */
        public boolean hasNext() {
            // Aquí va su código.

            if(iterador!= null){
                return true;
            } else {
                return false;
            }



        }

        /* Regresa la siguiente entrada. */
        public Entrada siguiente() {
            // Aquí va su código.


            if(iterador == null){
                throw new NoSuchElementException();
            }
            ///Movemos el iterador
            if(iterador.hasNext()){
                Entrada e = iterador.next();
                if(!iterador.hasNext()){
                    mueveIterador();
                }
                return e;
            } else {
                throw new NoSuchElementException();
            }




        }

        /* Mueve el iterador a la siguiente entrada válida. */
        private void mueveIterador() {
            // Aquí va su código.
            //Partimos desde el indice actual + 1
            for (int i = indice + 1; i < entradas.length; i++){
                if (entradas[i]!=null){
                    indice = i;
                    iterador = entradas[i].iteradorLista();
                    return;
                }

            }

            //En otro caso el ya no hay entradas y el iterador se hace null
            iterador = null;

        }
    }

    /* Clase interna privada para iteradores de llaves. */
    private class IteradorLlaves extends Iterador
        implements Iterator<K> {

        /* Regresa el siguiente elemento. */
        @Override public K next() {
            // Aquí va su código.
            ///Usaremos los metodos de Iterador y solo cambiaremos el tipo de elemento que regresamos
            if(!super.hasNext()){
                throw new NoSuchElementException();
            }
            //Regresamos llaves
            return super.siguiente().llave;

        }
    }

    /* Clase interna privada para iteradores de valores. */
    private class IteradorValores extends Iterador
        implements Iterator<V> {

        /* Regresa el siguiente elemento. */
        @Override public V next() {
            // Aquí va su código.
            ///Usaremos los metodos de Iterador y solo cambiaremos el tipo de elemento que regresamos
            if(!super.hasNext()){
                throw new NoSuchElementException();
            }
            //Regresamos valores
            return super.siguiente().valor;

        }
    }

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Capacidad mínima; decidida arbitrariamente a 2^6. */
    private static final int MINIMA_CAPACIDAD = 64;

    /* Dispersor. */
    private Dispersor<K> dispersor;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores. */
    private int elementos;

    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked")
    private Lista<Entrada>[] nuevoArreglo(int n) {
        return (Lista<Entrada>[])Array.newInstance(Lista.class, n);
    }

    /**
     * Construye un diccionario con una capacidad inicial y dispersor
     * predeterminados.
     */
    public Diccionario() {
        // Aquí va su código.
        this(MINIMA_CAPACIDAD, Object::hashCode);


    }

    /**
     * Construye un diccionario con una capacidad inicial definida por el
     * usuario, y un dispersor predeterminado.
     * @param capacidad la capacidad a utilizar.
     */
    public Diccionario(int capacidad) {
        // Aquí va su código.
        this(capacidad, Object::hashCode);
    }

    /**
     * Construye un diccionario con una capacidad inicial predeterminada, y un
     * dispersor definido por el usuario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(Dispersor<K> dispersor) {
        // Aquí va su código.
        this(MINIMA_CAPACIDAD, dispersor);
    }

    /**
     * Construye un diccionario con una capacidad inicial y un método de
     * dispersor definidos por el usuario.
     * @param capacidad la capacidad inicial del diccionario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(int capacidad, Dispersor<K> dispersor) {
        // Aquí va su código.
        this.dispersor = dispersor;

        int n = Math.max(capacidad, 64);
        int c = 1;
        while (c < n * 2)
            c *= 2;

        this.entradas = nuevoArreglo(c);


    }



    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     * @throws IllegalArgumentException si la llave o el valor son nulos.
     */
    public void agrega(K llave, V valor) {
        // Aquí va su código.
        if(llave == null || valor == null){
            throw new IllegalArgumentException();
        }
        //i es la dispersion de la llave con la mascara aplicada
        int i = dispersor.dispersa(llave) & this.entradas.length -1;
        //Si el iesimo elemento del arreglo es null, se crea una lista de entradas y se ponen en el indice del arreglo,
        //agregamos una nueva entrada con la llave y el valor en esta lista e incrementamos el contador de elementos
        if (entradas[i]== null){
            entradas[i] = new Lista<>();
            Entrada nuevaEntrada = new Entrada(llave,valor);

            entradas[i].agrega(nuevaEntrada);
            elementos ++;
        } else {
            /*
            En caso contrario, se recorre para ver si existe una entrada con la misma llave. Si este es el caso, reemplazamos
            el valor de la entrada con el nuevo valor.Si no es el caso, agregamos una nueva entrada con la llave y el
            valor de la lista e incrementamos el contador de elementos
             */

            boolean existe = false;


            for (Entrada e : entradas[i]){
                if(e.llave.equals(llave)){
                    existe = true;
                    e.valor = valor;
                    break;
                }
            }

            if(!existe){
                Entrada agregar = new Entrada(llave,valor);
                entradas[i].agrega(agregar);
                this.elementos ++;
            }



            }


        /*
            Si la carga alcanza o excede la carga maxima, doblamos la capacidad del arreglo y volvemos a agregar todas
            las entradas y calculamos de nuevo el indice ya que la mascara cambio
             */

        double carga = (double) elementos / entradas.length;

        if (carga >= MAXIMA_CARGA){
            Lista<Entrada>[] nuevaArray = nuevoArreglo(entradas.length * 2);
            for (Lista<Entrada> entrada : entradas) {
                if (entrada != null) {
                    for (Entrada e : entrada) {
                        int index = dispersor.dispersa(e.llave) & (nuevaArray.length - 1);
                        if (nuevaArray[index] == null) {
                            nuevaArray[index] = new Lista<>();
                        }
                        nuevaArray[index].agrega(e);
                    }
                }

            }

            this.entradas = nuevaArray;


        }


    }


    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave) {
        // Aquí va su código.
        if( llave == null){
            throw new IllegalArgumentException();
        }

        int index = dispersor.dispersa(llave) & this.entradas.length -1;
        V valor = null;

        if(entradas[index] == null){
            throw new NoSuchElementException();
        }

        for (Entrada e : entradas[index]){
            if (e.llave.equals(llave)){
                valor = e.valor;
            }
        }

        if(valor != null){
            return valor;
        } else {
            throw new NoSuchElementException();

        }
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <code>true</code> si la llave está en el diccionario,
     *         <code>false</code> en otro caso.
     */
    public boolean contiene(K llave) {
        // Aquí va su código.
        if (llave == null){
            return false;
        }
        int index = dispersor.dispersa(llave) & this.entradas.length -1;

        if(entradas[index] == null){
            return false;
        }

        for (Entrada e : entradas[index]){
            if (e.llave.equals(llave)){
                return true;
            }
        }
         return false;


    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
        // Aquí va su código.
        if (llave == null){
            throw new IllegalArgumentException();
        }

        int index = dispersor.dispersa(llave) & this.entradas.length -1;

        if (entradas[index] == null){
            throw new NoSuchElementException();
        }

        for (Entrada e : entradas[index]){
            if (e.llave.equals(llave)){
                entradas[index].elimina(e);
                this.elementos --;
                if (entradas[index].esVacia()){
                    entradas[index] = null;
                }
                break;
            }
        }

    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
        // Aquí va su código.
        int contador = 0;
        if(elementos == 0){
            return contador;
        }

        for (Lista<Entrada> entrada : entradas) {
            if (entrada != null) {
                contador = contador + entrada.getLongitud();
            }
        }

        return contador - 1;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
        // Aquí va su código.
        //Regresar la longitud de la lista mas grade del arreglo menos 1

        int maximo = 0;
        int aux = 0;

        if(elementos == 0){
            return maximo;
        }



        for (Lista<Entrada> entrada : entradas) {
            if (entrada != null) {
                aux =  entrada.getLongitud() - 1;
                if (maximo < aux){
                    maximo = aux;
                }
            }
        }

        return maximo;
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
        // Aquí va su código.

        return ((double) this.elementos) / entradas.length;


    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
        // Aquí va su código.
        return this.elementos;
    }

    /**
     * Nos dice si el diccionario es vacío.
     * @return <code>true</code> si el diccionario es vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacia() {
        // Aquí va su código.
        return this.elementos == 0;
    }

    /**
     * Limpia el diccionario de elementos, dejándolo vacío.
     */
    public void limpia() {
        // Aquí va su código.
        this.entradas = nuevoArreglo(entradas.length);
        this.elementos = 0;
    }

    /**
     * Regresa una representación en cadena del diccionario.
     * @return una representación en cadena del diccionario.
     */
    @Override public String toString() {
        // Aquí va su código.


        if (elementos == 0){
            return "{}";
        }

        StringBuilder s = new StringBuilder("{ ");



        for (Lista<Entrada> entrada : entradas) {
            if (entrada != null) {
                for (Entrada e : entrada)
                    s.append(String.format("'%s': '%s', ", e.llave, e.valor));
            }
        }

        return s + "}";


    }

    /**
     * Nos dice si el diccionario es igual al objeto recibido.
     * @param o el objeto que queremos saber si es igual al diccionario.
     * @return <code>true</code> si el objeto recibido es instancia de
     *         Diccionario, y tiene las mismas llaves asociadas a los mismos
     *         valores.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Diccionario<K, V> d =
            (Diccionario<K, V>)o;
        // Aquí va su código.

        if(d.getElementos() != this.elementos){
            return false;
        }

        Iterator<K> iterador1 = iteradorLlaves();
        Iterator<V> iterador2 = iterator();
        while (iterador1.hasNext() && iterador2.hasNext()){
            K llave = iterador1.next();
            if (!d.contiene(llave) || !d.get(llave).equals(this.get(llave))){
                return false;
            }

        }

        return true;


    }

    /**
     * Regresa un iterador para iterar las llaves del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar las llaves del diccionario.
     */
    public Iterator<K> iteradorLlaves() {
        return new IteradorLlaves();
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar los valores del diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new IteradorValores();
    }
}
