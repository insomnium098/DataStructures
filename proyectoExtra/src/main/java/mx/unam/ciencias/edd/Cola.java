package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
        // Aquí va su código.
        String s = "";

        if (this.esVacia()){
            return s;
        }

        Nodo n1 = this.cabeza;
        while (n1!=null){
            s += String.valueOf(n1.elemento) + ",";
            n1 = n1.siguiente;
        }
        return s;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {

        // Aquí va su código.
        if(elemento == null){
            throw new IllegalArgumentException();
        }

        Nodo n = new Nodo(elemento);
        ///
        if(this.rabo == null){
            this.cabeza = this.rabo = n;
        } else{
            this.rabo.siguiente = n;
            this.rabo = n;
        }
    }
}
