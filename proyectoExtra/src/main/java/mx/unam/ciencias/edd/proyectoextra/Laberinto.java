package mx.unam.ciencias.edd.proyectoextra;
import mx.unam.ciencias.edd.*;

public class Laberinto {

    private Lista<String> laberintoOriginal;
    private Integer [][] arrayLaberinto;
    private char [][] charLaberinto;
         /*
        Para resuelveLaberinto, la grafica contiene los nodos conectados en ese laberinto
        En el caso de generaLaberinto, la grafica contiene a los nodos y que nodos son sus vecinos válidos
        */
    private Grafica<Integer> grafica;
    private Grafica<Integer> graficaGeneraLaberinto;
    private Integer nRows;
    private Integer nCols;
    private Conjunto<VerticeGrafica<Integer>> conjuntoParedes;


    ////Conjuntos integer
    private Conjunto<Integer> conjuntoIntegerParedes;
    private Conjunto<Integer> conjuntoIntegerEspacios;

    ///variables para resuelveLaberinto
    private Integer origen;
    private Integer destino;
    private Lista<Integer> trayectoria;


    /*
    El laberinto tendrá dos constructores, uno para inicializarlo desde generaLaberinto
    y otro para recibir una lista desde resuelveLaberinto
     */


    //Constructor para generaLaberinto
    public Laberinto(Integer nRows, Integer nCols){
        System.out.println("Constructor GeneraLaberinto");
        this.nRows = nRows;
        this.nCols = nCols;
        this.laberintoOriginal = construyeLaberintoOriginal(nRows,nCols);

        /*
        Para resuelveLaberinto, la grafica contiene los nodos conectados en ese laberinto
        En el caso de generaLaberinto, la grafica contiene a los nodos y que nodos son sus vecinos válidos
         */
        this.grafica = new Grafica<>();
        this.graficaGeneraLaberinto = new Grafica<>();
        this.trayectoria = new Lista<>();


        this.iniciaArray();
        this.iniciaChar();
        this.construyeEdges(false);
        this.inicializaParedes();

        this.inicializaGraficaConstruye();

        ////
        System.out.println("Imprimiendo laberinto");
        this.imprimeLaberinto();
        //System.out.println("Imprimiendo grafica");
        //this.imprimeGrafica();
        ///

        /*
        Aqui va el algoritmo que genere el laberinto
         */

        this.algoritmoPrim();


    }


    //Constructor para resuelveLaberinto
    public Laberinto (Lista<String> laberintoOriginal){
        this.laberintoOriginal = laberintoOriginal;
        this.grafica = new Grafica<>();
        this.trayectoria = new Lista<>();
        this.iniciaArray();
        this.iniciaChar();
        this.construyeEdges(true);
        this.calculaTrayectoria();
        this.imprimeLaberinto();

    }

    /*
    Inicializadores para resuelveLaberinto
     */
    private void iniciaArray(){

        ///Primero determinamos la longitud del laberinto
        ///El numero de "columnas" será la longitud de cada string
        nCols = laberintoOriginal.get(0).length();
        ///Las rows serán el numero de strings en la lista que contiene al laberinto
        nRows = laberintoOriginal.getLongitud();

        Integer [][] laberintoNum = new Integer[nRows] [nCols];

        ///Llenar el laberinto con integers, cada uno representa un char en el string
        //El contador comenzará en 0
        Integer contador = 0;

        for(int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){
                laberintoNum[rows][columnas] = contador;
                contador ++;
            }

        }
        this.arrayLaberinto = laberintoNum;

    }

    private void iniciaChar(){
        ///Primero determinamos la longitud del laberinto
        ///El numero de "columnas" será la longitud de cada string
        //Integer nColumnas = laberintoOriginal.get(0).length();
        ///Las rows serán el numero de strings en la lista que contiene al laberinto
        //Integer nRows = laberintoOriginal.getLongitud();

        char [][] laberintoChar = new char [nRows] [nCols];

        ///Debemos convertir cada string de la lista en chars y agregarlas al laberinto
        int contador = 0;

        for(String s : laberintoOriginal){
            char [] character = s.toCharArray();
            for (int columns = 0; columns < nCols; columns++){
                laberintoChar[contador][columns] = character[columns];
            }
            contador ++;
        }

        this.charLaberinto = laberintoChar;
    }

    private void construyeEdges(boolean resuelve){
        //Integer nCols = arrayLaberinto[0].length;
        //Integer nRows = arrayLaberinto.length;
        Integer nElementos = nRows * nCols;

        ///Inicializamos la grafica
        for (int i=0; i<nElementos; i++){
            grafica.agrega(i);
        }


        //////
        ///Primero se recorrerá el array con un anterior y siguiente por rows
        Integer anteriorNum = null;
        Integer siguienteNum = null;
        char anteriorChar;
        char siguienteChar;
        for(int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){
                ///Definir anterior y siguiente para ambos arrays
                anteriorNum = arrayLaberinto[rows][columnas];
                anteriorChar = charLaberinto[rows][columnas];
                ///Revisar si son entrada o salida
                if (esEntradaOSalida(anteriorChar, rows, columnas)){
                    if(resuelve){
                        if (origen == null){
                            origen = anteriorNum;
                        } else {
                            destino = anteriorNum;
                        }

                    }

                }


                if(columnas + 1 == nCols ){
                    if(rows + 1 == nRows){
                        break;
                    } else {
                        siguienteNum = arrayLaberinto[rows+1][0];
                        siguienteChar = charLaberinto[rows+1][0];
                        ///Revisar si son entrada o salida
                        if (esEntradaOSalida(siguienteChar, rows+1, 0)){

                            if (resuelve){
                                if (origen == null){
                                    origen = siguienteNum;
                                } else {
                                    destino = siguienteNum;
                                }
                            }

                        }
                    }

                } else {
                    siguienteNum = arrayLaberinto[rows][columnas+1];
                    siguienteChar = charLaberinto[rows][columnas+1];

                    ///Revisar si son entrada o salida
                    if (esEntradaOSalida(siguienteChar, rows, columnas+1)){
                        if (resuelve){
                            if (origen == null){
                                origen = siguienteNum;
                            } else {
                                destino = siguienteNum;
                            }
                        }

                    }
                }


                ///Revisar si son el origen y el destino para guardar su
                //localizacion




                /*
                if(anteriorChar == 'E'){
                    origen = anteriorNum;
                }

                if(siguienteChar == 'E'){
                    origen = siguienteNum;
                }

                if(anteriorChar == 'S'){
                    destino = anteriorNum;
                }

                if(siguienteChar == 'S'){
                    destino = siguienteNum;
                }

                 */




                ///Revisar si estan conectados en el charLaberinto, Este metodo solo entra si se llama
                ///al metodo desde resuelve, en caso contrario se conectan los edges

                /*
                Caso para resuelve
                 */

                if (resuelve){
                    if(estanConectados(anteriorChar,siguienteChar)){

                        ///Si no son vecinos o el mismo elemento, se agregan
                        if (!grafica.sonVecinos(anteriorNum,siguienteNum) && anteriorNum != siguienteNum){

                            ///Conectar los numericos
                            grafica.conecta(anteriorNum,siguienteNum);

                        }

                    }
                }  else {

                    /*
                    Caso para genera laberinto
                     */
                    ///Si no son vecinos o el mismo elemento, se agregan
                    if (!grafica.sonVecinos(anteriorNum,siguienteNum) && anteriorNum != siguienteNum){

                        ///Conectar los numericos
                        grafica.conecta(anteriorNum,siguienteNum);
                    }

                }





            }



            //////Despues recorrer los arrays por columnas
            for(int columnas1 = 0; columnas1 < nCols; columnas1 ++){
                for (int rows1 = 0; rows1 < nRows; rows1 ++){
                    ///Definir anterior y siguiente para ambos arrays

                    anteriorNum = arrayLaberinto[rows1][columnas1];
                    anteriorChar = charLaberinto[rows1][columnas1];
                    if(rows1 + 1 == nRows){
                        continue;

                    }

                    siguienteNum = arrayLaberinto[rows1+1][columnas1];
                    siguienteChar = charLaberinto[rows1+1][columnas1];


                    /*
                    Caso para resuelveLaberinto
                     */

                    if (resuelve){
                        if(estanConectados(anteriorChar,siguienteChar)){

                            if(!grafica.sonVecinos(anteriorNum, siguienteNum) && anteriorNum != siguienteNum){
                                ///Conectar los numericos
                                grafica.conecta(anteriorNum,siguienteNum);

                            }


                        }

                    } else {
                        /*
                        Caso para generaLaberinto
                         */
                        if(!grafica.sonVecinos(anteriorNum, siguienteNum) && anteriorNum != siguienteNum){
                            ///Conectar los numericos
                            grafica.conecta(anteriorNum,siguienteNum);

                        }

                    }


                }

            }




        }

        //////



    }

    public void imprimeGrafica(){
        System.out.println(grafica.toString());
    }

    private boolean estanConectados (char a, char b){

        if(a == ' ' && b == ' '){
            return true;
        }

        /*

        if(a == 'E' && b == ' ' || a == ' ' && b == 'E'){
            return true;
        }

        if(a == 'S' && b == ' ' || a == ' ' && b == 'S'){
            return true;
        }

         */

        return false;

    }

    public Integer getOrigen(){
        return this.origen;
    }

    public Integer getDestino(){
        return this.destino;
    }


    public void calculaTrayectoria(){
        if (origen == null | destino == null | destino == origen){
            return;
        }
        Lista<VerticeGrafica<Integer>> listaDijkstra = grafica.dijkstra(origen,destino);
        for (VerticeGrafica<Integer> elem : listaDijkstra){
            //System.out.println(elem.get());
            trayectoria.agrega(elem.get());
        }

    }

    /*
    Metodo que devuelve si el caracter es una Entrada o Salida
    Los caracteres en las coordenadas (0,b), (2n,b), (a, 0) y (a, 2m)
    deben ser todos caracteres visibles, excepto por 2 (son las paredes externas
    del laberinto, y la entrada y salida respectivamente.
     */

    private boolean esEntradaOSalida(char caracter, Integer m, Integer n){
        if (caracter != ' '){
            return false;
        }

        if(m == 0 || n == 0){
            return true;
        }

        /*

        if (n % 2 == 0 || m % 2 == 0){
            return true;
        }

         */

        return false;

    }



    /*
    End metodos para resuelve laberinto
     */


    /*
    Metodo para imprimir el laberinto
     */

    public void imprimeLaberinto(){

        ////Removemos de la trayectoria al origen y destino para no sobreescribir su character
        //trayectoria.elimina(origen);
        //trayectoria.elimina(destino);


        ///Vamos a recorrer el laberinto con un contador, y si el valor del contador está
        // en la trayectoria vamos a imprimir un asterisco

        Integer nCols = charLaberinto[0].length;
        Integer nRows = charLaberinto.length;


        Integer contador = 0;

        for(int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){

                if(trayectoria.contiene(contador)){
                    System.out.print('*');
                } else {
                    System.out.print(charLaberinto[rows][columnas]);

                }



                contador ++;
            }
            System.out.println();

        }


    }


    /*
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                        Metodos para generar un Laberinto
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /*
    Metodo que construye una Lista<String> como el laberinto original de resuelveLaberinto
     */

    private Lista<String> construyeLaberintoOriginal (Integer nRows, Integer nCols){
        String paredesChar = "#";
        ///repetimos las paredes por el numero de nCols
        String paredes = new String(new char[(2*nCols) + 1]).replace("\0", paredesChar);
        Lista<String> laberinto = new Lista<>();
        ///El string paredes se agregará un número (2*nRows) + 1 veces. Esto representa las rows
        Integer veces = (2*nRows) + 1;

        for (int i = 0; i < veces; i ++){
            laberinto.agrega(paredes);
            //System.out.println(paredes);
        }

        return laberinto;
    }


    /*
    Metodo que inicializa la graficaConstruyeLaberinto solo con los nodos
     */


    private void inicializaGraficaConstruye(){

        for (Integer i = 0; i < nRows * nCols; i++){
            graficaGeneraLaberinto.agrega(i);
        }

        System.out.println("La graficaGeneraLaberinto:");

        System.out.println(this.graficaGeneraLaberinto.toString());

    }

    private void algoritmoPrim(){

        /*
        VerticeGrafica<Integer> n = grafica.vertice(0);
        System.out.println("Los vecinos de 0:");
        for (VerticeGrafica<Integer> s : n.vecinos()){
            System.out.println(s.get());
        }

        //
        System.out.println("Numeros aleatorios;");

        for (int i = 0; i < 10; i++){
            System.out.println(numeroAleatorio());
        }

         */

        ///Inicio del algoritmo
        //

        //Creamos un conjunto V
        Conjunto<VerticeGrafica<Integer>> V = new Conjunto<>();



        /////////////       PASO 1          //////////////////////////////
        ///Elegimos al azar un elemento de la gráfica, obtenemos el vertice
        // correspondiente y lo agregamos al conjunto

        Integer n = numeroAleatorioConjuntoInteger(conjuntoIntegerEspacios);
        System.out.println("El nodo espacio aleatorio es: " + n);
        System.out.println("La grafica: " + grafica.toString());

        VerticeGrafica<Integer> vertice = grafica.vertice(n);
        System.out.println("El vertice es: " + vertice.get());
        //Lo agregamos al conjunto que tiene los vertices agregados al laberinto
        V.agrega(vertice);


        /////////// PASO 2 ///////////////////////
        //Obtenemos los nodos frontera (Paredes) del nodo agregado y los agregamos a V. Nos quedamos con uno de ellos
        //

        System.out.println("Sus vecinos son: ");
        VerticeGrafica<Integer> nodoFrontera = null;
        for (VerticeGrafica<Integer> nFrontera : vertice.vecinos()){
            if (conjuntoIntegerParedes.contiene(nFrontera.get())){
                System.out.println(nFrontera.get());
                V.agrega(nFrontera);
                nodoFrontera = nFrontera;
            }

        }

        ///Elegimos un nodo Frontera al azar, este no debe de ser el primer n
        boolean nfron1 = false;
        Integer fronteraAzar = null;

        while(!nfron1){
            fronteraAzar = numeroAleatorioConjuntoVertice(V);
            if (fronteraAzar != n){
                nfron1 = true;
            }

        }
        //fronteraAzar = numeroAleatorioConjuntoVertice(V);

        nodoFrontera = grafica.vertice(fronteraAzar);


        System.out.println("El nodo frontera es: " + nodoFrontera.get());

        ///Debemos buscar a los nodos conectados del nodoFrontera que estén en V, al primero que cumpla esta condición
        /// es conectado con el nodo frontera. Esta conexion se dará en la graficaGeneraLaberinto
        System.out.println("Los vecinos del nodo frontera son:");
        Boolean conectado = false;

        for (VerticeGrafica<Integer> vecinoFrontera : nodoFrontera.vecinos()){

            if (conectado){
                break;
            }
            for (VerticeGrafica<Integer> elementoEnV : V){
                if (grafica.sonVecinos(vecinoFrontera.get(), elementoEnV.get())){
                    graficaGeneraLaberinto.conecta(nodoFrontera.get(), vecinoFrontera.get());


                    actualizaChar(nodoFrontera.get(), ' ');

                    System.out.println(nodoFrontera.get() + " conectado con " + vecinoFrontera.get());
                    actualizaChar(vecinoFrontera.get(), ' ');

                    conectado = true;
                    break;
                }
            }
            System.out.println(vecinoFrontera.get());


        }

        imprimeLaberinto();




        //System.out.println("Conjunto V: " + V.toString());
        //System.out.println("GraficaLaberinto : " + graficaGeneraLaberinto.toString());

        ///

        /*
        System.out.println("Los elementos en el conjunto; ");

        for (VerticeGrafica<Integer> ve : V){
            System.out.println(ve.get());
        }

        System.out.println("El numero aleatorio en el conjunto: " + numeroAleatorioConjunto(V));
        System.out.println(V.getElementos());
        System.out.println(grafica.getElementos());

         */

        while (grafica.getElementos() > V.getElementos()){
            ///Elegimos un nodo aleatorio de V
            Integer aleatorio = numeroAleatorioConjuntoVertice(V);
            ///Obtenemos su VERTICE DE V
            VerticeGrafica<Integer> veAleatorio = null;
            for (VerticeGrafica<Integer> ve : V){
                if (ve.get() == aleatorio){
                    veAleatorio = ve;
                    break;
                }
            }

            Boolean con = false;
            ///Debemos de buscar un nodo frontera del veAleatorio que se encuentr en V y conectarlos
            //Tambien añadimos todos los nodos frontera a V
            for (VerticeGrafica<Integer> vFron : veAleatorio.vecinos()){
                if (V.contiene(vFron) && !con){
                    if (!graficaGeneraLaberinto.sonVecinos(veAleatorio.get(), vFron.get()) && veAleatorio.get() != vFron.get()){
                        graficaGeneraLaberinto.conecta(veAleatorio.get(), vFron.get());

                        actualizaChar(vFron.get(),' ');
                        actualizaChar(veAleatorio.get(),' ');

                        con = true;
                    }

                } else {
                    V.agrega(vFron);
                }
            }

        }
        System.out.println("Imprimiendo el segundo laberinto");
        imprimeLaberinto();


        /*
        System.out.println("Los elementos en el conjunto DESPUES; ");

        for (VerticeGrafica<Integer> ve : V){
            System.out.println(ve.get());
        }

         */

        //Fallan las operaciones con graficaGeneraLaberinto
        //System.out.println("La graficaGeneraLaberinto despues: " + graficaGeneraLaberinto.getAristas());


        //imprimeLaberinto();





        /*


        while (!V.esVacia()){
            //nodoFrontera = V.g
        }

         */





        /*
        ///Elegimos al azar un elemento de la gráfica, obtenemos el vertice
        // correspondiente y lo agregamos al conjunto
        Integer n = numeroAleatorio();
        VerticeGrafica<Integer> vertice = grafica.vertice(n);
        V.agrega(vertice);
        ///Obtenemos sus coordenadas para modificar el arrayChar por un espacio
        Integer coordX = coordenadasX(n);
        Integer coordY = coordenadasY(n);
        charLaberinto[coordX][coordY] = ' ';


        //Aqui debemos obtener un nodo frontera que no esté agregado al conjunto
        //Deberia ser randomizado,en nuestro caso tomaremos el primer elemento que no esté en la lista
        VerticeGrafica<Integer> nodoFrontera = null;
        Integer nodoFronteraInteger = null;

        for (VerticeGrafica<Integer> s : vertice.vecinos()){
            //Revisamos que no esté en el conjunto
            if (!V.contiene(s)){
                //Lo conectamos con el vertice que ya agregamos
                graficaGeneraLaberinto.conecta(n, s.get());
            }
            //System.out.println(s.get());
        }

         */







        //System.out.println("Las coordenadas X de 11 :" + coordenadasX(20));
        //System.out.println("Las coordenadas Y de 11 :" + coordenadasY(20));




    }

    /*
    Metodo que actualiza el char de charLaberinto
     */

    private void actualizaChar(Integer elemento, char charNuevo){

        //Integer cols1 = charLaberinto[0].length;
        //Integer rows1 = charLaberinto.length;

        Integer contador = 0;

        //System.out.println("Actualizando elemento: " + elemento);

        for (int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){

                if (contador == elemento){
                    charLaberinto[rows][columnas] = charNuevo;
                }

                contador ++;
            }

        }

        //imprimeLaberinto();

        //Vamos a recorrer el arrayChar, si el contador es igual al elemento se actualiza


        /*

        imprimeLaberinto();
        System.out.println("El elemento: " + elemento);
        System.out.println("Las coordenadas X = " + coordenadasX(elemento));
        System.out.println("Las coordenadas Y = " + coordenadasY(elemento));

        this.charLaberinto[coordenadasX(elemento)][coordenadasY(elemento)] = charNuevo;

         */
    }

        /*
    Metodo que devuelve un numero aleatorio que se encuentre en un Integer
     */

    private Integer numeroAleatorioConjuntoInteger (Conjunto<Integer> conjunto){
        Integer [] elementos = new Integer[conjunto.getElementos()];
        Integer contador = 0;
        for (Integer vertice : conjunto){
            elementos[contador] = vertice;
            contador++;
        }

        ///Vamos a generar un numero aleatorio dentro del index de elementos, el número en
        // ese index es el que devolveremos
        int indexRandom = (int) (Math.random() * elementos.length);
        if (indexRandom == elementos.length){
            indexRandom = 0;
        }

        return elementos[indexRandom];
    }


    /*
    Metodo que devuelve un numero aleatorio que se encuentre en un conjunto Vertice
     */

    private Integer numeroAleatorioConjuntoVertice (Conjunto<VerticeGrafica<Integer>> conjunto){
        Integer [] elementos = new Integer[conjunto.getElementos()];
        Integer contador = 0;
        for (VerticeGrafica<Integer> vertice : conjunto){
            elementos[contador] = vertice.get();
            contador++;
        }

        ///Vamos a generar un numero aleatorio dentro del index de elementos, el número en
        // ese index es el que devolveremos
        int indexRandom = (int) (Math.random() * elementos.length);
        if (indexRandom == elementos.length){
            indexRandom = 0;
        }

        return elementos[indexRandom];
    }

    /*
    Metodo que recibe un integer que se encuentre en la gráfica y calcula las coordenadas X (rows) que tiene en
    arrayLaberinto y charLaberinto
     */

    private Integer coordenadasX (Integer elemento){

        if (elemento == 0){
            return 0;
        }

        ///El ncols nos dice cuantos elementos caben por row, asi que hacemos un contador para calcular el row
        //correspondiente

        //System.out.println("El nCols es: " + nCols);

        int contador = 0;
        int acumulado = 0;

        for (int i = 0; i < nRows; i++){

            //acumulado += nCols - 1;
            acumulado += nCols ;
            //System.out.println("El acumulado es : " + acumulado);

            if ( acumulado >= elemento){
                //System.out.println("El i es: " + i);
                break;
            } else {
                contador +=1;
            }

        }

        return contador;


        /*

        int contador = 0;

        for(int rows = 0; rows < (nRows * nCols) - 1; rows ++){

            if (rows != 1 & rows % nRows == 1 ){
                //System.out.println("El rows i es: " + rows);
                contador += 1;
            }

            if (rows == elemento){
                break;
            }






        }

        return contador;

         */

    }

    private Integer coordenadasY (Integer elemento){

        if (elemento == 0){
            return 0;
        }

        /*

        //Obtenemos las coordenadas X
        int coordX = coordenadasX(elemento);
        ///Calculamos que elemento está al inicio de ese renglon
        int elemInicio = coordX * nCols;

        ///Finalmente calculamos la diferencia
        int diferencia = elemento- elemInicio;




        return diferencia;

         */

        if (elemento == 0){
            return 0;
        }

        int contador = 0;

        for(int rows = 0; rows < (nRows * nCols) - 1; rows ++){
            //System.out.println("Rows: " + rows + " contador: " + contador);



            if (rows % nRows == 0 ){
                //System.out.println("El rows i de Y es: " + rows);
                contador = 0;
            }

            if (rows == elemento){
                break;
            }

            contador +=1;








        }

        return contador;

    }






    /*
    Metodo que devuelve un número aleatorio que es un válido elemento de la gráfica
     */

    private Integer numeroAleatorio(){

        int maximo = (nRows * nCols) - 1;
        int numero = (int)(Math.random() * maximo);

        return numero;
    }

    /*
   Metodo que inicializa las paredes y los espacios
     */

    private void inicializaParedes(){

        this.conjuntoIntegerEspacios = new Conjunto<>();
        this.conjuntoIntegerParedes = new Conjunto<>();

        int contador = 0;

        for(int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){

                ///Los espacios
                if (rows % 2 !=0 & columnas % 2 != 0 ){
                    actualizaChar(contador, ' ');
                    conjuntoIntegerEspacios.agrega(contador);
                } else {
                    conjuntoIntegerParedes.agrega(contador);
                }
                contador ++;
            }


        }

    }

}
