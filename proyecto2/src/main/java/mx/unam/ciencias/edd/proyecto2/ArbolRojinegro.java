package mx.unam.ciencias.edd.proyecto2;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {

            // Aquí va su código.
            super(elemento);
            this.color = color.NINGUNO;

        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            // Aquí va su código.
            String s = this.elemento.toString();

            if(this.color == color.ROJO){
                return "R{" + s + "}";
            } else {
                return "N{" + s + "}";
            }
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            // Aquí va su código.
            return (color==vertice.color) && super.equals(objeto);

        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        VerticeRojinegro vertice_r = (VerticeRojinegro)vertice;
        return vertice_r.color;

    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);
        VerticeRojinegro ultimoAgregado = (VerticeRojinegro)super.ultimoAgregado;
        ultimoAgregado.color = Color.ROJO;
        rebalancear_aux(ultimoAgregado);

    }

    private void rebalancear_aux(VerticeRojinegro vertice){
        ///Caso 1
        if(!vertice.hayPadre()){
            vertice.color = Color.NEGRO;
            return;
        }
        ///Caso 2
        VerticeRojinegro padre = (VerticeRojinegro)vertice.padre;
        if (getColor(padre)==Color.NEGRO){
            return;
        }

        ///Caso 3
        ///El vertice t es rojo; Se colorea t y vertice de negro,
        //al abuelo de rojo y se hace recursion sobre el abuelo.
        VerticeRojinegro abuelo = Abuelo(vertice);
        VerticeRojinegro tio = Tio(vertice);

        if (esRojo(tio)) {
            padre.color = Color.NEGRO;
            tio.color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            rebalancear_aux(abuelo);
        } else {
            ///Caso 4
            //El tio es negro
            //Los vertices Vertice y Padre estan cruzados

            if (estanCruzados(vertice, padre)){
                VerticeRojinegro temp = padre;
                if (abuelo.izquierdo == padre && padre.derecho == vertice){
                    super.giraIzquierda(padre);
                    padre = vertice;
                    vertice = temp;
                }

                if (abuelo.derecho == padre && padre.izquierdo == vertice){
                    super.giraDerecha(padre);
                    padre = vertice;
                    vertice = temp;
                }
            }

            ///Caso 5

            padre.color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            if (!esDerecho(vertice)){
                super.giraDerecha(abuelo);
            } else {
                super.giraIzquierda(abuelo);
            }
        }


    }

    ///Metodos auxiliares para rebalancear
    private boolean esRojo (VerticeRojinegro vertice){
        return (vertice != null && vertice.color == Color.ROJO);
    }
    private boolean esDerecho (VerticeRojinegro vertice){
        return (vertice != null && vertice.padre.derecho == vertice);
    }

    private VerticeRojinegro Tio (VerticeRojinegro vertice) {
        if (Abuelo(vertice).izquierdo == vertice.padre){
            return (VerticeRojinegro) Abuelo(vertice).derecho;
        } else {
            return (VerticeRojinegro) Abuelo(vertice).izquierdo;
        }
    }

    private VerticeRojinegro Abuelo (VerticeRojinegro vertice){

        return (VerticeRojinegro) vertice.padre.padre;
    }

    private boolean estanCruzados(VerticeRojinegro vertice, VerticeRojinegro padre){
        //Si el padre es derecho y vertice es izquierdo
        if (esDerecho(padre) && !esDerecho(vertice)){
            return true;
        } else if (!esDerecho(padre) && esDerecho(vertice)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.

        VerticeRojinegro eliminar = (VerticeRojinegro) super.busca(elemento);
        VerticeRojinegro fantasma = null;

        if(eliminar == null){
            return;
        }


        ///Revisar que vertice a eliminar tenga a lo mas un hijo distinto de null



        //Caso donde tiene sus dos hijos

        if (eliminar.izquierdo != null && eliminar.derecho!=null){
            fantasma = eliminar;
            eliminar = (VerticeRojinegro) super.MaximoEnSubarbol(eliminar.izquierdo);
            fantasma.elemento = eliminar.elemento;
            fantasma = null;

        }

        ///Caso donde no tiene hijos, añadirle fantasma a la izquierda

        if(!eliminar.hayIzquierdo() && !eliminar.hayDerecho()){
            fantasma = (VerticeRojinegro) nuevoVertice(null);
            fantasma.padre = eliminar;
            fantasma.color = Color.NEGRO;
            eliminar.izquierdo = fantasma;
        }



        ////////


        ///Sea h el hijo del vertice a eliminar
        VerticeRojinegro hijo;
        if (eliminar.hayIzquierdo()){
            hijo = (VerticeRojinegro) eliminar.izquierdo;
        } else {
            hijo = (VerticeRojinegro) eliminar.derecho;
        }

        desconectaElimina(eliminar, hijo);
        //El vertice hijo ahora ocupa el lugar del vertice a eliminar

        if (esRojo(hijo) || esRojo(eliminar)){
            hijo.color = Color.NEGRO;
        } else if (!esRojo(hijo) && !esRojo(eliminar)){
            rebalancea(hijo);
        }

        ///Eliminar fantasma si es que existe
        //////
        if (fantasma != null){
            if (!fantasma.hayPadre()){
                raiz = ultimoAgregado = null;
            } else if (!esDerecho(fantasma)){
                fantasma.padre.izquierdo = null;
            } else {
                fantasma.padre.derecho = null;
            }

        }
        //////

        this.elementos --;




    }

    ////Metodos auxiliares para eliminar


    private void rebalancea (VerticeRojinegro vertice){

        //Caso 1
        if (!vertice.hayPadre()){
            return;
        }

        VerticeRojinegro padre = (VerticeRojinegro) vertice.padre;
        VerticeRojinegro hermano = Hermano(vertice);


        ///Caso 2
        if (esRojo(hermano)){
            padre.color = Color.ROJO;
            hermano.color = Color.NEGRO;
            ///Giramos sobre el padre en direccion del vertice
            if (!esDerecho(vertice)){
                super.giraIzquierda(padre);
            } else {
                super.giraDerecha(padre);
            }

            hermano = Hermano(vertice);
            padre = (VerticeRojinegro) vertice.padre;
        }

        VerticeRojinegro H_izq = (VerticeRojinegro) hermano.izquierdo;
        VerticeRojinegro H_der = (VerticeRojinegro) hermano.derecho;

        ///Caso 3

        if (!esRojo(padre) && !esRojo(hermano) && !esRojo(H_izq) && !esRojo(H_der)){
            hermano.color = Color.ROJO;
            rebalancea(padre);
            return;
        }

        ///Caso 4
        if (esRojo(padre) && !esRojo(hermano) && !esRojo(H_izq) && !esRojo(H_der)){
            hermano.color = Color.ROJO;
            padre.color = Color.NEGRO;
            return;

        }

        ///Caso 5
        if (!esDerecho(vertice) && esRojo(H_izq) && !esRojo(H_der)){
            hermano.color = Color.ROJO;
            H_izq.color = Color.NEGRO;
            super.giraDerecha(hermano);
        } else if (esDerecho(vertice) && !esRojo(H_izq) && esRojo(H_der)){
            hermano.color = Color.ROJO;
            H_der.color = Color.NEGRO;
            super.giraIzquierda(hermano);
        }

        hermano = Hermano(vertice);
        H_izq = (VerticeRojinegro) hermano.izquierdo;
        H_der = (VerticeRojinegro) hermano.derecho;

        ///Caso 6

        hermano.color = padre.color;
        padre.color = Color.NEGRO;
        if (!esDerecho(vertice)){
            H_der.color = Color.NEGRO;
            super.giraIzquierda(padre);
        } else {
            H_izq.color = Color.NEGRO;
            super.giraDerecha(padre);
        }


    }

    private VerticeRojinegro Hermano (VerticeRojinegro vertice){
        if (!esDerecho(vertice)){
            return (VerticeRojinegro) vertice.padre.derecho;
        } else {
            return (VerticeRojinegro) vertice.padre.izquierdo;
        }
    }



    //Desconecta el vertice a eliminar y deja a su hijo h en el
    //lugar que ocupaba vertice
    private void desconectaElimina (VerticeRojinegro vertice, VerticeRojinegro hijo){


        //Caso donde el vertice es la raiz
        if (!vertice.hayPadre()){
            raiz = hijo;
            raiz.padre = null;
            return;
        }

        hijo.padre = vertice.padre;

        //Caso donde el vertice es izquierdo

        if (!esDerecho(vertice)){
            if (vertice.izquierdo == hijo){
                vertice.padre.izquierdo = vertice.izquierdo;
            } else {
                vertice.padre.izquierdo = vertice.derecho;
            }

        } else {

            //Caso donde el vertice es izquierdo

            if (vertice.izquierdo == hijo){
                vertice.padre.derecho = vertice.izquierdo;
            } else {
                vertice.padre.derecho = vertice.derecho;
            }

        }



    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
