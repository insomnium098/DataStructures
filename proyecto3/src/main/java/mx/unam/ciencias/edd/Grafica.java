package mx.unam.ciencias.edd;

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
    private class Vertice implements VerticeGrafica<T>,
            ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* La lista de vecinos del vértice. */
        public Lista<Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            color = Color.NINGUNO;
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
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            // Aquí va su código.

            if(distancia == vertice.distancia){
                return 0;
            }
            if(distancia < vertice.distancia){
                return -1;
            }

            return 1;

        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            // Aquí va su código.
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            // Aquí va su código.
            return this.vecino.get();
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            // Aquí va su código.
            return this.vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            // Aquí va su código.
            return this.vecino.getColor();
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return vecino.vecinos();
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
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
        this.vertices = new Lista<>();
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
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if (this.contiene(elemento) || elemento == null){
            throw new IllegalArgumentException();
        }

        Vertice vertice = new Vertice(elemento);
        vertices.agregaFinal(vertice);
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
        if(!this.contiene(a) || !this.contiene(b)){
            throw new NoSuchElementException();
        }

        if( a == b || this.sonVecinos(a,b)){
            throw new IllegalArgumentException();
        }

        conecta(a, b, 1.0);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        // Aquí va su código.
        if(!this.contiene(a) || !this.contiene(b)){
            throw new NoSuchElementException();
        }

        if(a == b || sonVecinos(a,b) || peso < 0){
            throw new IllegalArgumentException();
        }

        Vertice vertA = (Vertice) vertice(a);
        Vertice vertB = (Vertice) vertice(b);

        vertA.vecinos.agregaFinal( new Vecino(vertB,peso));
        vertB.vecinos.agregaFinal( new Vecino(vertA,peso));

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

        if( a == null || b == null || !this.contiene(a) || !this.contiene(b)){
            throw new NoSuchElementException();
        }

        if(!sonVecinos(a,b)){
            throw new IllegalArgumentException();
        }

        Vertice vertA = (Vertice) vertice(a);
        Vertice vertB = (Vertice) vertice(b);

        ///Obtenemos los vecinos

        Vecino vecinoA = null;
        for (Vecino vecino : vertB.vecinos){
            if(vecino.vecino.equals(vertA)){
                vecinoA = vecino;
            }
        }

        Vecino vecinoB = null;
        for (Vecino vecino : vertA.vecinos){
            if(vecino.vecino.equals(vertB)){
                vecinoB = vecino;
            }
        }

        ///Los eliminamos

        vertA.vecinos.elimina(vecinoB);
        vertB.vecinos.elimina(vecinoA);

        this.aristas --;



    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.

        for (Vertice vertice : vertices) {
            if (vertice.elemento.equals(elemento)) {
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


        Vertice v1 = (Vertice) vertice(elemento);

        if(v1 == null){
            throw new NoSuchElementException();
        }


        ///Recorrer vecinos del vertice y eliminar de las listas
        //de vecinos de los vecinos y decrementar el contador interno
        //de aristas por cada vecino



        for (Vecino vecino : v1.vecinos){
            for(Vecino vecino2 : vecino.vecino.vecinos){
                if (vecino2.vecino == v1){
                    vecino.vecino.vecinos.elimina(vecino2);
                    this.aristas --;

                }
            }

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

        Vertice ver_a = (Vertice) vertice(a);
        Vertice ver_b = (Vertice) vertice(b);

        Vertice vecinoB = null;


        for (Vecino i : ver_a.vecinos){
            if(i.vecino == ver_b){
                vecinoB = i.vecino;
            }
        }

        if(vecinoB != null){
            return true;
        }

        return false;

    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        // Aquí va su código.
        if(!contiene(a) || !contiene(b)){
            throw new NoSuchElementException();
        }

        if(!sonVecinos(a,b)){
            throw new IllegalArgumentException();
        }

        double peso = -1;

        Vertice vertA = (Vertice) vertice(a);
        Vertice vertB = (Vertice) vertice(b);

        for (Vecino vecino : vertA.vecinos){
            if(vecino.vecino == vertB){
                peso = vecino.peso;
            }
        }

        return peso;




    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        // Aquí va su código.
        if(!contiene(a) || !contiene(b)){
            throw new NoSuchElementException();
        }

        if(!sonVecinos(a,b)){
            throw new IllegalArgumentException();
        }


        Vertice vertA = (Vertice) vertice(a);
        Vertice vertB = (Vertice) vertice(b);

        for (Vecino vecino : vertB.vecinos){
            if(vecino.vecino == vertA){
                vecino.peso = peso;
            }
        }

        for (Vecino vecino : vertA.vecinos){
            if(vecino.vecino == vertB){
                vecino.peso = peso;
            }
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
        if (elemento == null){
            throw new NoSuchElementException();
        }


        for (Vertice vertice : vertices){
            if(vertice.elemento.equals(elemento)){
                return vertice;
            }
        }

        throw new NoSuchElementException();

    }




    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        // Aquí va su código.
        if ( vertice == null || (vertice.getClass() != Vertice.class && vertice.getClass() != Vecino.class))
            throw new IllegalArgumentException();


        if ( vertice.getClass() == Vertice.class ){
            Vertice v = (Vertice) vertice;
            v.color = color;
        }
        if ( vertice.getClass() == Vecino.class ){
            Vecino v = (Vecino) vertice;
            v.vecino.color = color;
        }



        /*

        Vertice ver = (Vertice) vertice;

        ver.color = color;

         */
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
            for (Vecino ver4 : ver3.vecinos){
                if(ver4.getColor().equals(Color.ROJO)){
                    setColor(ver4, Color.NEGRO);
                    cola.mete(ver4.vecino);
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

        for (Vertice ver : vertices){
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
            for (Vecino ver4 : ver3.vecinos){
                if(ver4.getColor().equals(Color.ROJO)){
                    setColor(ver4, Color.NEGRO);
                    tipo_dato.mete(ver4.vecino);
                }
            }
        }


        for (Vertice ver5 : vertices){
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
            for(Vecino j : i.vecinos){
                if(j.getColor() == Color.ROJO)
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
            for(Vecino v2_vecinos : v1.vecinos){
                if(!grafica.sonVecinos(v1.elemento, v2_vecinos.get())){
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

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        // Aquí va su código.
        if(origen == null || destino == null){
            throw new NoSuchElementException();
        }

        if(!contiene(origen) || !contiene(destino)){
            throw new NoSuchElementException();
        }

        ///Inicializamos los vertices
        Vertice auxiliar;
        Vertice vertOrigen = (Vertice) vertice(origen);


        //Si el origen y el destino son iguales se se regresa una lista con el vertice origen como unico elemento

        if(origen == destino){
            Lista<VerticeGrafica<T>> lista = new Lista<>();
            lista.agregaFinal(vertOrigen);
            return lista;

        }

        //Se define la distancia de todos los vertices de la grafica como infinito, excepto al vertice origen
        //que tendra una distancia de 0

        for(Vertice vert : vertices){
            vert.distancia = Double.POSITIVE_INFINITY;
        }

        vertOrigen.distancia = 0;

        Cola<Vertice> cola = new Cola<>();
        cola.mete(vertOrigen);

        //Mientras la cola nos sea vacia se saca al siguiente y se revisa si los vecinos tienen infinito,
        // si es asi se defina la distancia del vecino como la distancia de u mas 1

        while(!cola.esVacia()){
            auxiliar = cola.saca();
            for (Vecino vecino : auxiliar.vecinos){
                if (vecino.vecino.distancia == Double.POSITIVE_INFINITY){
                    vecino.vecino.distancia = auxiliar.distancia + 1;
                    cola.mete(vecino.vecino);
                }
            }
        }

        ///Si la distancia del destino es infinito, son inconexas, devolver lista vacia
        Vertice vertDestino = (Vertice) vertice(destino);

        if(vertDestino.distancia == Double.POSITIVE_INFINITY){
            return new Lista<VerticeGrafica<T>>();
        }

        //En caso contrario hay que reconstruir la trayectoria.
        Lista<VerticeGrafica<T>> trayectoria = new Lista<>();
        auxiliar = vertDestino;

        while (auxiliar != vertOrigen){
            for (Vecino vecino : auxiliar.vecinos){
                if((auxiliar.distancia - 1) == vecino.vecino.distancia){
                    trayectoria.agregaInicio(auxiliar);
                    auxiliar = vecino.vecino;
                    break;
                }
            }
            if (auxiliar == vertOrigen){
                trayectoria.agregaInicio(vertOrigen);

            }
        }

        return trayectoria;




    }






    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        // Aquí va su código.
        if(origen == null || destino == null){
            throw new IllegalArgumentException();
        }

        if(!contiene(origen) || !contiene(destino)){
            throw new NoSuchElementException();
        }



        ///Inicializamos los vertices
        Vertice auxiliar;
        Vertice vertOrigen = (Vertice) vertice(origen);

        //Si el origen y el destino son iguales se se regresa una lista con el vertice origen como unico elemento

        if(origen == destino){
            Lista<VerticeGrafica<T>> lista = new Lista<>();
            lista.agregaFinal(vertOrigen);
            return lista;

        }

        for(Vertice vert : vertices){
            vert.distancia = Double.POSITIVE_INFINITY;
        }

        vertOrigen.distancia = 0;

        //Creamos el monticulo de Dijkstra
        MonticuloArreglo<Vertice> monticulo = new MonticuloArreglo<Vertice>(this.vertices);

        while(!monticulo.esVacia()){
            auxiliar = monticulo.elimina();
            for (Vecino vecino : auxiliar.vecinos){
                //Revisar si es infinito
                if(vecino.vecino.distancia > auxiliar.distancia + vecino.peso){
                    vecino.vecino.distancia = auxiliar.distancia + vecino.peso;
                    ///Reordenar
                    monticulo.reordena(vecino.vecino);
                }
            }
        }


        ///Si el destino tiene distancia infinita entonces los componentes son inconexos, regresar trayectoria vacia

        Vertice vertDestino = (Vertice) vertice(destino);

        if(vertDestino.distancia == Double.POSITIVE_INFINITY){
            return new Lista<VerticeGrafica<T>>();
        }

        //Reconstruir trayectoria pero con pesos

        Lista<VerticeGrafica<T>> trayectoria = new Lista<>();
        auxiliar = vertDestino;

        while (auxiliar != vertOrigen){
            for (Vecino vecino : auxiliar.vecinos){
                if((auxiliar.distancia - vecino.peso) == vecino.vecino.distancia){
                    trayectoria.agregaInicio(auxiliar);
                    auxiliar = vecino.vecino;
                    break;
                }
            }
            if (auxiliar == vertOrigen){
                trayectoria.agregaInicio(vertOrigen);

            }
        }

        return trayectoria;



    }

}
