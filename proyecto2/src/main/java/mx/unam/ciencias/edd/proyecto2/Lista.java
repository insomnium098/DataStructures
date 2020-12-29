package mx.unam.ciencias.edd.proyecto2;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            this.anterior = null;
            this.siguiente = null;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            // Aquí va su código.
            this.anterior = null;
            this.siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            if (siguiente == null){
              return false;
            }
            return true;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            // Aquí va su código.
            if (siguiente == null){
              throw new NoSuchElementException("NSE");
            }

            T t =siguiente.elemento;
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return t;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            // Aquí va su código.
            if (anterior == null){
              return false;
            }
            return true;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            // Aquí va su código.
            if (anterior == null){
              throw new NoSuchElementException("NSE");
            }

            T t =anterior.elemento;
            anterior = anterior.anterior;
            return t;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            // Aquí va su código.
            this.siguiente = cabeza;
            this.anterior = null;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            // Aquí va su código.
            this.anterior = rabo;
            this.siguiente = null;

        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
        int contador = 0;

        Iterator n = this.iterator();
        while (n.hasNext()) {
            contador += 1;
            n.next();

        }

        return contador;

    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        // Aquí va su código
        int contador = 0;
        Iterator n = this.iterator();
        while (n.hasNext()){
          contador += 1;
          n.next();
        }

        return contador;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        if (this.cabeza == null && this.rabo == null){
          return true;
        } else{
          return false;
        }
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código
        if( elemento == null ){
          throw new IllegalArgumentException("");
        }

        Nodo n = new Nodo(elemento);
        if (rabo == null){
          cabeza=rabo=n;

        } else{
          rabo.siguiente = n;
          n.anterior = rabo;
          rabo = n;
        }
        this.longitud ++;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        // Aquí va su código.
        if( elemento == null ){
          throw new IllegalArgumentException("");
        }

        Nodo n = new Nodo(elemento);
        if (rabo == null){
          cabeza=rabo=n;

        } else{
          rabo.siguiente = n;
          n.anterior = rabo;
          rabo = n;
        }
        this.longitud ++;
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        // Aquí va su código.
        if(elemento == null){
          throw new IllegalArgumentException("");
        }

        Nodo n= new Nodo(elemento);
        if(rabo == null){
          cabeza=rabo=n;
        } else {
          cabeza.anterior = n;
          n.siguiente = cabeza;
          cabeza = n;

        }

        this.longitud ++;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        // Aquí va su código.
        int l1 = this.getLongitud();

        if (elemento == null){
          throw new IllegalArgumentException("a");
        }

        if (i <= 0){
          agregaInicio(elemento);
          return;
        }

        if(i >= l1){
          agregaFinal(elemento);
          return;
        }


        int cont = 0;
        Nodo n= cabeza;


        while(cont < i){
          n=n.siguiente;
          cont ++;
        }

        //Se inserta el nodo
        Nodo nuevo= new Nodo(elemento);

        if(rabo == null){
          cabeza=rabo=nuevo;
        }



        Nodo aux_ant= n.anterior;
        aux_ant.siguiente = nuevo;
        nuevo.anterior = aux_ant;
        nuevo.siguiente = n;
        n.anterior = nuevo;
        longitud ++;
        return;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        Nodo n = buscaNodo(elemento);

        if(n == null){
          return;
        }

        ///Si cabeza y rabo son null

        if(n.anterior == null && n.siguiente == null){
          this.cabeza = this.rabo = null;
          return;
        }

        //Hacer funcion por si el nodo es cabeza
        if (n.anterior == null){


          Nodo nodo_siguiente = n.siguiente;
          nodo_siguiente.anterior = null;
          n.siguiente = null;
          cabeza = nodo_siguiente;
          return;
        }
        //Si el nodo es rabo
        if(n.siguiente == null){
          Nodo nodo_anterior = n.anterior;
          nodo_anterior.siguiente = null;
          n.anterior = null;
          rabo = nodo_anterior;
          return;

        }

        Nodo nodo_anterior = n.anterior;
        Nodo nodo_siguiente = n.siguiente;

        nodo_anterior.siguiente = nodo_siguiente;
        nodo_siguiente.anterior = nodo_anterior;

        n.anterior = null;
        n.siguiente = null;
        this.longitud --;

        return;
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        // Aquí va su código.
        if(this.esVacia()){
          throw new NoSuchElementException("");
        }

        T result = cabeza.elemento;
        if(longitud == 1){
          this.cabeza = this.rabo = null;
          this.longitud--;
          return result;
        }

        this.cabeza = cabeza.siguiente;
        cabeza.anterior = null;
        this.longitud--;
        return result;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        // Aquí va su código.
        if(this.esVacia()){
          throw new NoSuchElementException("");
        }

        T result = rabo.elemento;
        if(longitud == 1){
          this.cabeza = this.rabo = null;
          this.longitud--;
          return result;
        }

        this.rabo = rabo.anterior;
        this.rabo.siguiente = null;
        this.longitud--;
        return result;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        if (this.buscaNodo(elemento) == null){
          return false;
        } else{
          return true;
        }
    }

    private Nodo buscaNodo(T e){

      Nodo n = cabeza;

      if( n == null){
        return null;
      }
      while(n!= null){
        if(n.elemento.equals(e)){
          return n;
        }
        n=n.siguiente;
      }

      return null;

    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        // Aquí va su código.
        Lista<T> L_nueva = new Lista<T>();

        Nodo n = rabo;
        while (n!=null) {

          L_nueva.agregaFinal(n.elemento);
          n=n.anterior;
        }

        return L_nueva;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        // Aquí va su código
        Lista<T> L_nueva = new Lista<T>();

        Nodo n = cabeza;
        while (n!=null) {

          L_nueva.agregaFinal(n.elemento);
          n=n.siguiente;
        }

        return L_nueva;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        this.cabeza = null;
        this.rabo = null;
        this.longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        // Aquí va su código.
        Nodo n = cabeza;



        if(this.esVacia()){
          throw new NoSuchElementException("");
        }

        if (n.elemento == null){
          //return null;
          throw new NoSuchElementException("");
        }

        return n.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        // Aquí va su código.
        Nodo n = rabo;

        if (n == null){
          //return null;
          throw new NoSuchElementException("");
        }

        return n.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        // Aquí va su código.
        int l1 = this.getLongitud();
        int cont = 0;

        if(i >= l1){
          //return null;
          throw new ExcepcionIndiceInvalido("");
        }

        if( i < 0){
          //return null;
          throw new ExcepcionIndiceInvalido("");
        }

        Nodo n = cabeza;

        while(cont < i){
          n=n.siguiente;
          cont ++;
        }
        return n.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        // Aquí va su código
        int contador_indice = 0;
        Nodo n = cabeza;
        while(n!= null){
          if(n.elemento.equals(elemento)){
            return contador_indice;
          }
          contador_indice ++;
          n=n.siguiente;
        }

        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        // Aquí va su código.
        int l1 = getLongitud();
        String s = "[";

        if(l1 == 0){
          return "[]";
        }

        for ( int i = 0; i < l1-1; i++ ){
          s += String.format("%s, ", get(i));
          //n = n.siguiente;
        }
        s += String.format("%s]", this.get(l1-1));
        return s;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        // Aquí va su código.
        if(lista == null){
            return false;
        }

        if(lista.esVacia()){
            return true;
        }

        int l1 = this.getLongitud();
        int l2 = lista.getLongitud();

        if(l1 != l2){
            return false;
        }

        Nodo n1 = this.cabeza;
        Nodo n2 = lista.cabeza;

        while (n1!=null && n2!=null){
            if(n1.elemento.equals(n2.elemento)){
                n1 = n1.siguiente;
                n2 = n2.siguiente;
            } else {
                return false;
            }

        }

        return true;
    }


    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        Lista<T> copia = this.copia();
        return mergeSort(copia, comparador);
    }

    private Lista<T> mergeSort(Lista<T> lista, Comparator<T> comparador){
        if(lista.esVacia() || lista.getLongitud() == 1) return lista;
        int m = lista.getLongitud() / 2 ;
        Lista<T> l1 = new Lista<T>();
        Lista<T> l2;
        while(lista.getLongitud() != m){
            l1.agregaFinal(lista.getPrimero());
            if(lista.getLongitud() != 0)
                lista.eliminaPrimero();
        }
        l2 = lista.copia();
        return mezcla(mergeSort(l1, comparador), mergeSort(l2, comparador), comparador);
    }

    private Lista<T> mezcla(Lista<T> a, Lista<T> b, Comparator<T> comparador) {
        Lista<T> ordenada = new Lista<T>();
        while(a.cabeza != null && b.cabeza != null) {
            int i = comparador.compare(a.cabeza.elemento, b.cabeza.elemento);
            if(i <= 0) {
                ordenada.agregaFinal(a.getPrimero());
                a.eliminaPrimero();
            }else {
                ordenada.agregaFinal(b.getPrimero());
                b.eliminaPrimero();
            }
        }
        while(a.cabeza != null) {
            ordenada.agregaFinal(a.getPrimero());
            a.eliminaPrimero();
        }
        while(b.cabeza != null) {
            ordenada.agregaFinal(b.getPrimero());
            b.eliminaPrimero();
        }
        return ordenada;
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        // Aquí va su código.
                //return true;
        Nodo n = this.cabeza;
        while(n != null){
            if(comparador.compare(elemento, n.elemento) == 0) {
                return true;
            }
            n = n.siguiente;
        }
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
