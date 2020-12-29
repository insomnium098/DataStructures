package mx.unam.ciencias.edd.proyecto2;

import java.util.NoSuchElementException;

/**
 * Clase para montículos de Dijkstra con arreglos.
 */
public class MonticuloArreglo<T extends ComparableIndexable<T>>
    implements MonticuloDijkstra<T> {

    /* Número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arreglo;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor para montículo de Dijkstra con un arreglo a partir de una
     * colección.
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloArreglo(Coleccion<T> coleccion) {
        // Aquí va su código.
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Construye un nuevo para montículo de Dijkstra con arreglo a partir de un
     * iterable.
     * @param iterable el iterable a partir de la cual construir el montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloArreglo(Iterable<T> iterable, int n) {
        // Aquí va su código.
        this.elementos = n;
        int indice = 0;

        this.arreglo = nuevoArreglo(n);
        //Se agregan en orden los elementos al arreglo
        for( T e : iterable){
            arreglo[indice] = e;
            e.setIndice(indice);
            indice++;

        }
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        // Aquí va su código.

        if (this.elementos == 0){
            throw new IllegalStateException();
        }

        ///Buscar el primer elemento que no sea null para la comparacion


        T minimo = null;

        boolean exist_minimo = false;

        for (int i = 0; i < arreglo.length ; i ++){
            if(arreglo[i] != null && !exist_minimo){
                if (arreglo[i] != null){
                    minimo = arreglo[i];
                    exist_minimo = true;

                }
            } else if(exist_minimo) {
                if(arreglo[i] != null && minimo.compareTo(arreglo[i]) > 0)
                    minimo = arreglo[i];
            }

        }





        arreglo[minimo.getIndice()] = null;
        minimo.setIndice(-1);
        elementos--;
        return minimo;




    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del arreglo.
     * @param i el índice del elemento que queremos.
     * @return el <i>i</i>-ésimo elemento del arreglo.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        // Aquí va su código.
        if( i < 0 || i >= this.elementos){
            throw new NoSuchElementException();
        }
        return arreglo[i];
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        if (this.elementos == 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Regresa el número de elementos en el montículo.
     * @return el número de elementos en el montículo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return this.elementos;
    }
}
