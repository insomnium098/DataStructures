package mx.unam.ciencias.edd.proyecto2;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        // Aquí va su código.
        String s = "";
        if (this.esVacia()){
            return s;
        }

        Nodo n1 = this.cabeza;
        while (n1!=null){
            s += String.valueOf(n1.elemento) + "\n";
            n1 = n1.siguiente;
        }
        return s;

    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        // Aquí va su código

        if(elemento == null){
            throw new IllegalArgumentException();
        }

        Nodo n = new Nodo(elemento);
        ///
        if(this.cabeza == null){
            this.cabeza = this.rabo = n;
        } else{
            n.siguiente = this.cabeza;
            this.cabeza = n;
        }

    }
}
