package mx.unam.ciencias.edd.proyecto2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            // Aquí va su código.
            iterador = vertices.iterator();

        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.

            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.

            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La lista de vecinos del vértice. */
        public Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            this.color = Color.NINGUNO;
            this.vecinos = new Lista<>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            // Aquí va su código.
            return this.elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            // Aquí va su código.
            return vecinos.getLongitud();


        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            // Aquí va su código.

            return this.color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return vecinos;
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
        vertices = new Lista<>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        // Aquí va su código.
        return this.aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null){
            throw new IllegalArgumentException();
        }

        if(contiene(elemento)){
            throw new IllegalArgumentException();
        }
        Vertice v = new Vertice(elemento);
        vertices.agregaFinal(v);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        // Aquí va su código.
        if(!contiene(a) || !contiene(b)){
            throw new NoSuchElementException("No existe el elemento");
        }

        if(a == b){
            throw new IllegalArgumentException("Los elementos son iguales");
        }

        if(sonVecinos(a,b)){
            throw new IllegalArgumentException("Los elementos son vecinos");
        }


        Vertice v1 = null;
        Vertice v2 = null;

        for(Vertice i : vertices){
            if(i.elemento.equals(a)){
                v1 = i;
            } else if (i.elemento.equals(b)){
                v2 = i;
            }
        }



        v1.vecinos.agregaFinal(v2);
        v2.vecinos.agregaFinal(v1);

        this.aristas ++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        // Aquí va su código.
        if(!contiene(a) || !contiene(b)){
            throw new NoSuchElementException();
        }

        if(!sonVecinos(a,b)){
            throw new IllegalArgumentException();
        }

        Vertice v1 = null;
        Vertice v2 = null;

        for(Vertice i : vertices){
            if(i.elemento.equals(a)){
                v1 = i;
            } else if (i.elemento.equals(b)){
                v2 = i;
            }
        }


        v2.vecinos.elimina(v1);
        v1.vecinos.elimina(v2);

        this.aristas --;


    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.

        for (Vertice ver : vertices){
            if(ver.get().equals(elemento)){
                return true;
            }
        }

        return false;






    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.

        if(elemento == null){
            throw new NoSuchElementException();
        }


        Iterator it = this.iterator();
        Vertice v1 = null;

        for (Vertice v : vertices){
            if (v.get().equals(elemento)){
                v1 = v;
            }
        }

        if(v1 == null){
            throw new NoSuchElementException();
        }
        ///Recorrer vecinos del vertice y eliminar de las listas
        //de vecinos de los vecinos y decrementar el contador interno
        //de aristas por cada vecino

        for (Vertice vecinos : v1.vecinos){
            vecinos.vecinos.elimina(v1);
            this.aristas --;

        }


        this.vertices.elimina(v1);


    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        // Aquí va su código.
        if(a == null || b == null){
            throw new NoSuchElementException();
        }
        if(!contiene(a) || !contiene(b)){
            throw new NoSuchElementException();
        }

        Vertice ver_a = null;
        Vertice ver_b = null;


        for (Vertice i : vertices){
            if (i.get().equals(a)){
                ver_a = i;
            } else if (i.get().equals(b)){
                ver_b = i;
            }
        }


        if(ver_a.vecinos.contiene(ver_b) && ver_b.vecinos.contiene(ver_a)){
            return true;
        } else {
            return false;
        }


    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        // Aquí va su código.
        if(elemento == null){
            throw new NoSuchElementException();
        }

        Vertice vertice = null;
        for (Vertice i : vertices){
            if(i.get().equals(elemento)){
                vertice = i;
            }
        }

        if(vertice == null){
            throw new NoSuchElementException();
        }

        return vertice;

    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        // Aquí va su código.
        if(vertice == null){
            throw new IllegalArgumentException();
        }

        if(vertice.getClass()!=Vertice.class){
            throw new IllegalArgumentException();
        }

        Vertice ver = (Vertice)vertice;
        if(!vertices.contiene(ver)){
            throw new NoSuchElementException();
        }

        ver.color = color;
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        // Aquí va su código.
        if(vertices.getLongitud() == 0 || vertices.getLongitud() == 1){
            return true;
        }
        Cola<Vertice> cola = new Cola<>();
        Vertice ver_primero = vertices.getPrimero();

        for (VerticeGrafica<T> ver : vertices){
            setColor(ver,Color.ROJO);
        }

        setColor(ver_primero, Color.NEGRO);

        cola.mete(ver_primero);

        while(!cola.esVacia()){
            Vertice ver3 = cola.saca();
            for (Vertice ver4 : ver3.vecinos){
                if(ver4.getColor().equals(Color.ROJO)){
                    setColor(ver4, Color.NEGRO);
                    cola.mete(ver4);
                }
            }

        }


        for(Vertice i : vertices){
            if(!i.getColor().equals(Color.NEGRO)){
                return false;
            }
        }

        for(Vertice j : vertices){
            setColor(j, Color.NINGUNO);
        }

        return true;




    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        for (Vertice ver : vertices){
            accion.actua(ver);
        }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        auxRecorrido(accion, new Cola<>(), elemento);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        auxRecorrido(accion, new Pila<>(), elemento);
    }


    ////Auxiliar para bfs y dfs

    private void auxRecorrido (AccionVerticeGrafica<T> accion,
                               MeteSaca<Vertice> tipo_dato,
                               T elemento){

        for (VerticeGrafica<T> ver : vertices){
            setColor(ver,Color.ROJO);
        }

        Vertice ver1 = null;

        for (Vertice ver2 : vertices){
            if(ver2.elemento.equals(elemento)){
                ver1 = ver2;
            }
        }

        if(ver1 == null){
            throw new NoSuchElementException();
        }

        setColor(ver1, Color.NEGRO);

        tipo_dato.mete(ver1);

        while(!tipo_dato.esVacia()){
            Vertice ver3 = tipo_dato.saca();
            accion.actua(ver3);
            for (Vertice ver4 : ver3.vecinos){
                if(ver4.getColor().equals(Color.ROJO)){
                    setColor(ver4, Color.NEGRO);
                    tipo_dato.mete(ver4);
                }
            }
        }


        for (VerticeGrafica<T> ver5 : vertices){
            setColor(ver5,Color.NINGUNO);
        }







    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        vertices.limpia();
        this.aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        // Aquí va su código.

        for (Vertice i : vertices){
            setColor(i, Color.ROJO);
        }


        String llave_izq = "{";
        String llave_der = "{";


        for(Vertice i : vertices){
            llave_izq += i.get() + ", ";
            for(Vertice j : i.vecinos){
                if(j.color == Color.ROJO)
                    llave_der += "(" + i.get() + ", " + j.get() + "), ";
                i.color = Color.NEGRO;
            }
        }

        for (Vertice i : vertices){
            setColor(i, Color.NINGUNO);
        }

        return llave_izq + "}, " + llave_der + "}";


    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        // Aquí va su código.

        if(grafica.aristas != this.aristas){
            return false;
        }

        if(grafica.vertices.getLongitud() != this.vertices.getLongitud()){
            return false;
        }

        for(Vertice v_this : vertices){
            if(!grafica.contiene(v_this.get())){
                return false;
            }
        }

        for(Vertice v1 : vertices){
            for(Vertice v2_vecinos : v1.vecinos){
                if(!grafica.sonVecinos(v1.elemento, v2_vecinos.elemento)){
                    return false;
                }
            }

        }

        return true;







    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
