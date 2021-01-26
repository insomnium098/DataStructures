package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
            return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            // Aquí va su código.
            return String.format("%s %d/%d", String.valueOf(this.elemento),
                    this.altura, getBalance(this));
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            // Aquí va su código.
            return (altura == vertice.altura && super.equals(vertice));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);

        ///Rebalancear
        //VerticeAVL vertice = (VerticeAVL) ultimoAgregado.padre;
        rebalancea((VerticeAVL) ultimoAgregado);




    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        VerticeAVL eliminar = (VerticeAVL)(busca(elemento));

        if (eliminar == null){
            return;
        }

        //Primer caso, el vertice a eliminar tiene izquierdo
        //Utilizar aux y maximoensubarbol

        if(eliminar.hayIzquierdo()){
            VerticeAVL aux = eliminar;
            eliminar = (VerticeAVL) MaximoEnSubarbol(eliminar.izquierdo);
            aux.elemento = eliminar.elemento;
        }

        //Segundo caso, el vertice a eliminar es una hoja

        if(!eliminar.hayIzquierdo() && !eliminar.hayDerecho()){
            //Verificar si es padre
            if(eliminar.padre == null){
                raiz = ultimoAgregado = null;
            } else if (eliminar.padre.izquierdo == eliminar){
                eliminar.padre.izquierdo = null;
            } else {
                eliminar.padre.derecho = null;
            }
        } else {
            //Caso donde hay que subir al hijo
            subirHijo(eliminar);
        }


        ////////







        ////Rebalancear sobre el padre
        rebalancea((VerticeAVL) eliminar.padre);

        elementos --;



    }

    public void subirHijo (VerticeAVL vertice){

        ///Caso 1: Hay un hijo izquierdo



        if(vertice.hayIzquierdo()){

            //Caso donde es la raiz
            if (!vertice.hayPadre()){
                raiz = vertice.izquierdo;
                vertice.izquierdo.padre = null;
            } else {
                ///No es raiz
                vertice.izquierdo.padre = vertice.padre;
                if(vertice.padre.izquierdo == vertice){
                    vertice.padre.izquierdo = vertice.izquierdo;
                } else {
                    vertice.padre.derecho = vertice.izquierdo;
                }
        }

            ////Caso 2: Hay un hijo derecho, hacer lo mismo

        } else if(vertice.hayDerecho()){
            if(!vertice.hayPadre()){
                raiz = vertice.derecho;
                vertice.derecho.padre = null;
            } else {
                vertice.derecho.padre = vertice.padre;
                if (vertice.padre.izquierdo == vertice){
                    vertice.padre.izquierdo = vertice.derecho;
                } else {
                    vertice.padre.derecho = vertice.derecho;
                }
            }
        }





    }

    public void rebalancea(VerticeAVL vertice){
        if (vertice == null){
            return;
        }
        rebalanceaAltura(vertice);

        int b = getBalance(vertice);//vertice.izquierdo.altura() - vertice.derecho.altura();
        //vertice.altura = (Math.max(vertice.izquierdo.altura(),vertice.derecho.altura()) + 1);

        VerticeAVL p = (VerticeAVL) vertice.izquierdo;
        VerticeAVL q = (VerticeAVL) vertice.derecho;


        /// Caso donde el balance es -2
        if ( b == -2){

            if(getBalance(q) == 1){
                super.giraDerecha(q);
                rebalanceaAltura(q);
                rebalanceaAltura((VerticeAVL) q.padre);
            }
            ///girar a la izquierda el vertice
            super.giraIzquierda(vertice);
            rebalanceaAltura(vertice);
            rebalanceaAltura((VerticeAVL) vertice.padre);

        }

         else if(b == 2){
            if(getBalance(p) == -1){
                super.giraIzquierda(p);
                rebalanceaAltura(p);
                rebalanceaAltura((VerticeAVL) p.padre);
            }
            ///girar a la derecha el vertice
            super.giraDerecha(vertice);
            rebalanceaAltura(vertice);
            rebalanceaAltura((VerticeAVL) vertice.padre);

        }

        rebalancea((VerticeAVL)vertice.padre);


    }



    private int alturaAVL (VerticeAVL vertice){
        if (vertice == null){
            return -1;
        } else {
            return vertice.altura;
        }
    }

    private void rebalanceaAltura(VerticeAVL vertice){
        vertice.altura = (Math.max(alturaAVL((VerticeAVL) vertice.izquierdo),
                alturaAVL((VerticeAVL) vertice.derecho))) + 1;
    }

    public int getBalance(VerticeAVL vertice){
        return alturaAVL((VerticeAVL) vertice.izquierdo) -
                alturaAVL((VerticeAVL) vertice.derecho);
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
