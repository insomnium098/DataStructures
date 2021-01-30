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

        //this.algoritmoPrim();
        this.algoritmoPrim2();


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

        //System.out.println("La graficaGeneraLaberinto:");

        //System.out.println(this.graficaGeneraLaberinto.toString());

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

                if (contador.equals(elemento)){
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

        if (elemento.equals(0)){
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
        System.out.println("Los nRows : " + nRows);
        System.out.println("Los nCols : " + nCols);

        Conjunto<Integer> bordes = new Conjunto<>();


        int contador = 0;

        for(int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){

                ///Los espacios
                if (rows % 2 !=0 & columnas % 2 != 0 ){
                    actualizaChar(contador, '+');
                    System.out.println("El char:" + contador + " Rows: " + rows + " columnas: " + columnas);
                    conjuntoIntegerEspacios.agrega(contador);
                } else {
                    conjuntoIntegerParedes.agrega(contador);
                }


                ///(0,b), (a,0)
                if (rows == 0 || columnas == 0 || rows == nRows - 1 || columnas == nCols -1){
                    bordes.agrega(contador);
                    //System.out.println("El borde: " + contador);
                }

                contador ++;
            }


        }


        ////Al azar elegiremos dos elementos de bordes y serán eliminados de los bordes, serán la entrada y salida
        Integer azar1 = numeroAleatorioConjuntoInteger(bordes);
        bordes.elimina(azar1);

        Integer azar2 = numeroAleatorioConjuntoInteger(bordes);
        bordes.elimina(azar2);

        ///Los restantes serán eliminados de las paredes y de la grafica
        for (Integer i : bordes){
            this.conjuntoIntegerParedes.elimina(i);
            this.grafica.elimina(i);
            this.conjuntoIntegerEspacios.elimina(i);
        }

        actualizaChar(azar1, '¿');
        actualizaChar(azar2, '?');

        ///Los bordes

        /*
        System.out.println("Los bordes:" );

        for (Integer l : bordes){
            System.out.println(bordes);
        }

         */





    }

    private void algoritmoPrim2(){


        System.out.println("La grafica:");

        System.out.println(this.grafica.toString());

        //Conjunto con los espacios visitados
        Conjunto<Integer> espaciosVisitados = new Conjunto<>();
        //Creamos al conjunto listaFronteras que contiene a los espacios frontera del laberinto
        Conjunto<Integer> listaFronteras = new Conjunto<>();
        ///Conjunto con las celdas que son espacios y parte del laberinto
        Conjunto<Integer> espaciosDelMazo = new Conjunto<>();
        ///Conjunto con las paredes visitadas
        Conjunto<Integer> paredesVisitadas = new Conjunto<>();
        ///Conjunto con las paredes de solo los NODOS DEL MAZO
        Conjunto<Integer> listaParedes = new Conjunto<>();



        /////////////       PASO 1          //////////////////////////////
        ///Elegimos al azar un elemento de los espacios de la gráfica, obtenemos el vertice
        // correspondiente, lo agregamos al mazo y metemos a listaFronteras los espacios adyacentes del vertice


        Integer n = numeroAleatorioConjuntoInteger(conjuntoIntegerEspacios);
        //System.out.println("El espacio aleatorio es: " + n);
        //System.out.println("La grafica: " + grafica.toString());
        //VerticeGrafica<Integer> vertice = grafica.vertice(n);


        VerticeGrafica<Integer> celda = grafica.vertice(n);


        //System.out.println("La celda aleatoria es: " + celda.get());
        //Agregamos esta celda al mazo y a los espacios visitados
        espaciosDelMazo.agrega(n);
        espaciosVisitados.agrega(n);
        ///Obtenemos sus paredes y las agregamos a listaParedes
        Conjunto<Integer> paredes = getParedes(celda);
        for (Integer i : paredes){
            listaParedes.agrega(i);
        }
        paredes.limpia();





        ///Obtenemos a sus nodos espacios adyacentes y los agregamos a listaFronteras
        Conjunto<Integer> fronteras = getFronterasEspacio(celda);


        //System.out.println("Las fronteras de la celda aleatoria son:");

        for (Integer Ve : fronteras){
            VerticeGrafica<Integer> vertice = grafica.vertice(Ve);
            //System.out.println(Ve);
            listaFronteras.agrega(Ve);

            /*
            paredes = getParedes(vertice);
            for (Integer par : paredes){
                listaParedes.agrega(par);
            }
            paredes.limpia();

             */
        }

        //System.out.println("Las paredes de estas fronteras son: ");

        /*
        for (Integer i : listaParedes){
            System.out.println(i);
        }

         */



        imprimeLaberinto();


        ////Aqui comienza el loop
        while (!listaFronteras.esVacia()){
            ///Elegimos un espacio al azar de las fronteras
            Integer espacioAzar = numeroAleatorioConjuntoInteger(listaFronteras);
            //Lo eliminamos del conjunto
            listaFronteras.elimina(espacioAzar);
            ///Lo agregamos a los espaciosVisitados
            espaciosVisitados.agrega(espacioAzar);
            //Determinamos el vertice correspondiente del espacio
            VerticeGrafica<Integer> verticeEspacioAzar = grafica.vertice(espacioAzar);
            ///Debemos de encontrar una pared del verticeEspacioAzar que se comparta con alguna pared en la listaParedes
            ///Esta pared se actualiza su char, se elimina de listaParedes y se agrega a paredesVisitadas
            Conjunto<Integer> paredesEspacioAzar = getParedes(verticeEspacioAzar);

            for (Integer pared : paredesEspacioAzar){
                if (listaParedes.contiene(pared)){
                    actualizaChar(pared, ' ');
                    listaParedes.elimina(pared);
                    paredesVisitadas.agrega(pared);
                    break;
                }
            }


            ///Despues agregamos todas las paredes del verticeEspacioAzar a la listaParedes, solo aquellas que
            //no estén en lista paredes y que no hayan sido visitadas
            for (Integer paredVerticeAzar : paredesEspacioAzar){
                if (!listaParedes.contiene(paredVerticeAzar) && !paredesVisitadas.contiene(paredVerticeAzar)){
                    listaParedes.agrega(paredVerticeAzar);
                }
            }



            ///El espacioAzar se agrega a los espacios del Mazo y a los espaciosVisitados y se actualiza su char
            espaciosDelMazo.agrega(espacioAzar);
            espaciosVisitados.agrega(espacioAzar);
            actualizaChar(espacioAzar, ' ');

            //////Despues tenemos que buscar a sus espaciosFrontera del espacioAzar que no se encuentren ya
            ////// en espaciosDelMazo ni en espaciosVisitados y agregarlos a listaFronteras

            Conjunto<Integer> fronterasEspacioAzar = getFronterasEspacio(verticeEspacioAzar);



            for (Integer Ve : fronterasEspacioAzar){
                VerticeGrafica<Integer> vertice = grafica.vertice(Ve);

                if(!espaciosDelMazo.contiene(Ve) && !espaciosVisitados.contiene(Ve)){
                    listaFronteras.agrega(Ve);
                    //Conjunto<Integer> paredesAzar = getParedes(vertice);

                    /*

                    for (Integer par: paredesAzar){
                        if(!paredesVisitadas.contiene(par)){
                            listaParedes.agrega(par);
                        }
                    }

                     */

                }

            }




        }








        imprimeLaberinto();


        /*



        ///Aqui comienza el loop
        while (!listaParedes.esVacia() && espaciosDelMazo.getElementos() < conjuntoIntegerEspacios.getElementos()){
            ///Elegimos una pared al azar de listaParedes
            Integer paredAzar = numeroAleatorioConjuntoInteger(listaParedes);
            ///La eliminamos del conjunto
            listaParedes.elimina(paredAzar);
            //La agregamos a las paredes visitadas
            paredesVisitadas.agrega(paredAzar);

            ///Obtenemos el vertice correspondiente de la pared en la grafica
            VerticeGrafica<Integer> pared = grafica.vertice(paredAzar);
            ////Obtenemos los espacios adyacentes a la pared
            Conjunto<Integer> espaciosAdyacentes = getEspacios(pared);
            ///Debemos de recorrer los espaciosAdyacentes y ver si uno de ellos es parte del mazo, si es así
            /// se agrega al mazo el otro espacio y se elimina la pared. Tambien se agregan las paredes de estos espacios
            ///a la lista paredes si es que no han sido visitadas
            boolean espacioEnMazo = false;
            Integer espacioAdyacente = null;

            for (Integer espacio : espaciosAdyacentes){
                VerticeGrafica<Integer> espacioAdy = grafica.vertice(espacio);
                ///Obtenemos sus paredes
                Conjunto<Integer> paredesEspacioAdy = getParedes(espacioAdy);
                for (Integer par : paredesEspacioAdy){
                    if (!listaParedes.contiene(par) && !paredesVisitadas.contiene(par)){
                        listaParedes.agrega(par);
                    }
                }

                 espacioAdyacente = espacio;
                if(espaciosDelMazo.contiene(espacioAdyacente)){
                 espacioEnMazo = true;
                }

            }

            ////Si existe un espacio en el mazo la pared se cambia de char y se agregan las paredes del espacio adyacente
            //// a la lista paredes, si no han sido visitadas. Y se agrega el espacio al mazo
            if (espacioEnMazo){
                actualizaChar(paredAzar, ' ');
                if (espacioAdyacente != null){
                    VerticeGrafica<Integer> verticeEspacio = grafica.vertice(espacioAdyacente);
                    espaciosDelMazo.agrega(espacioAdyacente);
                    actualizaChar(espacioAdyacente, ' ');
                    Conjunto<Integer> paredesAgregar = getParedes(verticeEspacio);
                    for (Integer paredAgregar : paredesAgregar){
                        if (!paredesVisitadas.contiene(paredAgregar)){
                            listaParedes.agrega(paredAgregar);
                        }
                    }

                }


            }

            ///Finalmente se agrega la pared en la paredesVisitadas
            paredesVisitadas.agrega(paredAzar);


        }

        System.out.println("Los espacios del Mazo");
        for (Integer i : espaciosDelMazo){
            System.out.println(i);
        }

        System.out.println("El laberinto final");
        imprimeLaberinto();

        System.out.println("La longitud de espaciosMazo " + espaciosDelMazo.getElementos());
        System.out.println("La longitud del conjuntoEspacios" + conjuntoIntegerEspacios.getElementos());

         */







        /*





        //for (int x = 0; x < 4; x++){
        //while (!listaCeldas.esVacia()){
        while (!listaCeldas.esVacia()){
            ///Elegimos una espacio de los ya agregados al azar
            Integer nAzar = numeroAleatorioConjuntoInteger(listaCeldas);
            System.out.println("El nAzar espacio es: " + nAzar);
            //Lo extraemos de la grafica para saber que elementos adyacentes tiene
            VerticeGrafica<Integer> espacioAzar = grafica.vertice(nAzar);
            System.out.println("Los vecinos paredes de nAzar "+ nAzar +" son:");
            //Recorremos los vecinos de los vecinos (paredes) que estan adyacentes al nAzar
            V.agrega(nAzar);
            for (VerticeGrafica<Integer> pared : espacioAzar.vecinos()){
                System.out.println(pared.get());
                //Al primero que tenga como vecino a un nodo en nodosMazo y que sea espacio se le hará espacio tambien,
                //Y que no haya sido visitado ----
                V.agrega(pared.get());
                for (VerticeGrafica<Integer> vecinoPared : pared.vecinos()){
                    Integer i = vecinoPared.get();
                    if (nodosMazo.contiene(i)){
                        System.out.println("La pared elegida es: " + pared.get());
                        actualizaChar(nAzar, ' ');
                        actualizaChar(pared.get(), ' ');
                        nodosMazo.agrega(nAzar);
                        nodosMazo.agrega(pared.get());

                        listaCeldas.elimina(nAzar);
                        listaCeldas.elimina(pared.get());
                        ///Agregamos todos los adyacentes de pared a listaceldas
                        for (VerticeGrafica<Integer> adyacentesPared : pared.vecinos()){
                            if (!V.contiene(adyacentesPared.get())){
                                listaCeldas.agrega(adyacentesPared.get());
                            }

                        }

                        break;
                    }


                }

            }

            //Removemos la pared de listaParedes y la agregamos a las borradas
            listaCeldas.elimina(nAzar);

            System.out.println("listaCeldas:");
            for (Integer i : listaCeldas){
                System.out.println(i);
                //if (paredesBorradas.contiene(i)){
                //    listaParedes.elimina(i);
                //}
            }

            System.out.println("V:");

            for (Integer i : V){
                System.out.println(i);
            }




            imprimeLaberinto();




            //listaParedes.limpia();




        }

         */







        //System.out.println("El laberinto final ANTES");

        //imprimeLaberinto();

        ////En este punto hay que añadir los bordes al laberinto


        //Integer contador = 0;

        //System.out.println("Actualizando elemento: " + elemento);



        //System.out.println("El laberinto final DESPUES");
        //añadeBordes();

        //imprimeLaberinto();










    }

    private void añadeBordes(){
        for (int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){

                if (rows == 0){
                    charLaberinto[rows][columnas] = '+';
                }

                if (columnas == 0){
                    charLaberinto[rows][columnas] = '+';
                }

                if ( columnas == nCols-1){
                    charLaberinto[rows][columnas] = '+';

                }

                if ( rows == nRows-1){
                    charLaberinto[rows][columnas] = '+';

                }

            }

        }

    }

    /*
    Metodo que recibe un vertice espacio del laberinto y delvuelve una conjunto con sus paredes
     */

    private Conjunto<Integer> getParedes(VerticeGrafica<Integer> vertice){

        Conjunto<Integer> paredes = new Conjunto<>();
        for (VerticeGrafica<Integer> vecino : vertice.vecinos()){
            Integer pared = vecino.get();
            if (conjuntoIntegerParedes.contiene(pared) & !paredes.contiene(pared)){
                paredes.agrega(pared);
            }
        }

        return paredes;


    }


        /*
    Metodo que recibe un vertice pared del laberinto y delvuelve un conjunto con sus espacios
     */

    private Conjunto<Integer> getEspacios(VerticeGrafica<Integer> vertice){

        Conjunto<Integer> espacios = new Conjunto<>();

        for (VerticeGrafica<Integer> vecino : vertice.vecinos()){
            Integer espacio = vecino.get();
            if (conjuntoIntegerEspacios.contiene(espacio) & !espacios.contiene(espacio) & !espacio.equals(vertice.get())){
                espacios.agrega(espacio);
            }
        }

        return espacios;


    }

    /*
    Metodo que recibe un espacio del laberinto y devuelve sus nodos espacios adyacentes
     */

    private Conjunto<Integer> getFronterasEspacio(VerticeGrafica<Integer> vertice){
        Conjunto<Integer> fronteras = new Conjunto<>();
        for (VerticeGrafica<Integer> ve : vertice.vecinos()){
            for (VerticeGrafica<Integer> vi : ve.vecinos()){
                Integer front = vi.get();
                if(conjuntoIntegerEspacios.contiene(front)){
                    fronteras.agrega(front);
                }
            }
        }


        return fronteras;




    }

}
