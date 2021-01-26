package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * Clase abtracta para estructuras lineales restringidas a operaciones
 * mete/saca/mira.
 */
public abstract class MeteSaca<T> {

    /**
     * Clase interna protegida para nodos.
     */
    protected class Nodo {
        /** El elemento del nodo. */
        public T elemento;
        /** El siguiente nodo. */
        public Nodo siguiente;

        /**
         * Construye un nodo con un elemento.
         * @param elemento el elemento del nodo.
         */
        public Nodo(T elemento) {
            this.elemento = elemento;
            // Aquí va su código.
        }
    }

    /** La cabeza de la estructura. */
    protected Nodo cabeza;
    /** El rabo de la estructura. */
    protected Nodo rabo;

    /**
     * Agrega un elemento al extremo de la estructura.
     * @param elemento el elemento a agregar.
     */
    public abstract void mete(T elemento);

    /**
     * Elimina el elemento en un extremo de la estructura y lo regresa.
     * @return el elemento en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura está vacía.
     */
    public T saca() {
        // Aquí va su código.
        if (this.esVacia()==true){
            throw new NoSuchElementException();
        }
        T e = this.cabeza.elemento;
        if (this.cabeza == this.rabo){
            this.cabeza = this.rabo = null;
        } else {
            this.cabeza = this.cabeza.siguiente;
        }

        return e;


    }

    /**
     * Nos permite ver el elemento en un extremo de la estructura, sin sacarlo
     * de la misma.
     * @return el elemento en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura está vacía.
     */
    public T mira() {
        // Aquí va su código.

        if (this.esVacia()==true){
            throw new NoSuchElementException();
        } else{
           return this.cabeza.elemento;
        }

    }

    /**
     * Nos dice si la estructura está vacía.
     * @return <code>true</code> si la estructura no tiene elementos,
     *         <code>false</code> en otro caso.
     */
    public boolean esVacia() {
        // Aquí va su código.
        if (this.cabeza == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Compara la estructura con un objeto.
     * @param object el objeto con el que queremos comparar la estructura.
     * @return <code>true</code> si el objeto recibido es una instancia de la
     *         misma clase que la estructura, y sus elementos son iguales en el
     *         mismo orden; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass())
            return false;
        @SuppressWarnings("unchecked") MeteSaca<T> m = (MeteSaca<T>)object;
        // Aquí va su código.
            if(m == null){
                return false;
            }
            Nodo n1 = this.cabeza;
            Nodo n2 = m.cabeza;
            while (n1!=null && n2!=null){
                if(n1.elemento.equals(n2.elemento)){
                    n1 = n1.siguiente;
                    n2 = n2.siguiente;
                } else {
                    return false;
                }

            }

            if (n1 == null && n2 == null){
                return true;
            } else{
                return false;
            }
        //return true;

    }
}
