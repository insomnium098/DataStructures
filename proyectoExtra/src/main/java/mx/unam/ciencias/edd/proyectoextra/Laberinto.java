package mx.unam.ciencias.edd.proyectoextra;
import mx.unam.ciencias.edd.*;
import java.io.*;

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


    /////////Cola que irá guardando los cambios que va sufriendo el laberinto para la interfaz grafica
    private Cola<char[][]> colaLaberinto;

    ////// bordes para generaLaberinto
    private Integer borde1;
    private Integer borde2;



    /*
    El laberinto tendrá dos constructores, uno para inicializarlo desde generaLaberinto
    y otro para recibir una lista desde resuelveLaberinto
     */


    //Constructor para generaLaberinto
    public Laberinto(Integer nRows, Integer nCols, Boolean GUI){
        //System.out.println("Constructor GeneraLaberinto");
        this.nRows = nRows;
        this.nCols = nCols;
        this.laberintoOriginal = construyeLaberintoOriginal(nRows,nCols);
        this.colaLaberinto = new Cola<>();

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
        //System.out.println("Imprimiendo laberinto");
        //this.imprimeLaberinto();
        //System.out.println("Imprimiendo grafica");
        //this.imprimeGrafica();
        ///

        /*
        Aqui va el algoritmo que genere el laberinto
         */


        this.algoritmoPrim2();
        if(!GUI){
            imprimeLaberinto();
            //imprimeColaLaberinto();
            //stringLaberinto();
        }



    }


    ///


    //Constructor para resuelveLaberinto
    public Laberinto (Lista<String> laberintoOriginal, boolean GUI){
        this.laberintoOriginal = laberintoOriginal;
        this.grafica = new Grafica<>();
        this.trayectoria = new Lista<>();
        this.colaLaberinto = new Cola<>();
        this.iniciaArray();
        this.iniciaChar();
        this.construyeEdges(true);
        this.calculaTrayectoria();






        if(!GUI){
            this.imprimeLaberinto();
        }




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
            Integer elemento = elem.get();
            //System.out.println(elem.get());
            trayectoria.agrega(elemento);
            actualizaChar(elemento,'*', true);
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

        if(m == 0 || n == 0 || m.equals(nRows-1) || n.equals(nCols-1)){
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
    Metodo para guardar el charLaberinto en un txt
     */

    public void guardaLaberinto(String nombreArchivo) throws IOException {
        //String nombreArchivo = "temp.txt";
        FileWriter fw = new FileWriter(new File(nombreArchivo));

        StringBuilder s = new StringBuilder("");



        ///Vamos a recorrer el laberinto con un contador, y si el valor del contador está
        // en la trayectoria vamos a imprimir un asterisco

        Integer nCols = charLaberinto[0].length;
        Integer nRows = charLaberinto.length;


        Integer contador = 0;

        for(int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){

                /*

                if(trayectoria.contiene(contador)){
                    System.out.print('*');
                } else {
                    System.out.print(charLaberinto[rows][columnas]);

                }

                 */

                s.append(charLaberinto[rows][columnas]);



                contador ++;
            }
            //System.out.println();
            s.append("\n");

        }

        fw.write(s.toString());
        fw.close();
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

    private void actualizaChar(Integer elemento, char charNuevo, Boolean meteCola){

        //Integer cols1 = charLaberinto[0].length;
        //Integer rows1 = charLaberinto.length;



        ////Hacemos una copia del char laberinto

        char [][] nuevLaberinto = new char[nRows][nCols];



        Integer contador = 0;

        //System.out.println("Actualizando elemento: " + elemento);

        for (int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){
                nuevLaberinto[rows][columnas] = charLaberinto[rows][columnas];

                if (contador.equals(elemento)){
                    charLaberinto[rows][columnas] = charNuevo;
                    nuevLaberinto[rows][columnas] = charNuevo;
                }

                contador ++;
            }




        }

        if(meteCola){
            colaLaberinto.mete(nuevLaberinto);
        }



        //this.colaLaberinto.mete(this.charLaberinto);

        //imprimeLaberinto();



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


        Conjunto<Integer> bordes = new Conjunto<>();
        Conjunto<Integer> esquinas = new Conjunto<>();

        ///Para hacer mas interesantes los laberintos, los bordes estaran en lados opuestos
        Conjunto<Integer> bordeSuperior = new Conjunto<>();
        Conjunto<Integer> bordeInferior = new Conjunto<>();
        Conjunto<Integer> bordeIzquierdo= new Conjunto<>();
        Conjunto<Integer> bordeDerecho = new Conjunto<>();


        int contador = 0;

        for(int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){

                ///Los espacios
                if (rows % 2 !=0 & columnas % 2 != 0 ){
                    actualizaChar(contador, '+',false);
                    //System.out.println("El char:" + contador + " Rows: " + rows + " columnas: " + columnas);
                    conjuntoIntegerEspacios.agrega(contador);
                } else {
                    conjuntoIntegerParedes.agrega(contador);
                }


                ///(0,b), (a,0)
                if (rows == 0 || columnas == 0 || rows == nRows - 1 || columnas == nCols -1){
                    ///Revisar que no sean los bordes del laberinto
                    bordes.agrega(contador);


                    if(rows == 0){
                        bordeSuperior.agrega(contador);
                    } else if ( columnas == 0){
                        bordeIzquierdo.agrega(contador);
                    } else if (rows == nRows - 1){
                        bordeInferior.agrega(contador);
                    } else if ( columnas == nCols -1){
                        bordeDerecho.agrega(contador);
                    }




                    if (sonEsquinas(rows, columnas)){
                        esquinas.agrega(contador);
                    }

                }

                contador ++;
            }


        }


        ////Al azar elegiremos dos elementos de bordes y serán eliminados de los bordes, serán la entrada y salida

        for (Integer e : esquinas){
            bordes.elimina(e);
            bordeIzquierdo.elimina(e);
            bordeSuperior.elimina(e);
            bordeDerecho.elimina(e);
            bordeInferior.elimina(e);

            this.conjuntoIntegerEspacios.elimina(e);
            this.conjuntoIntegerParedes.elimina(e);
            this.grafica.elimina(e);

        }



        Integer azar1 = numeroAleatorioConjuntoInteger(bordes);
        bordes.elimina(azar1);
        this.borde1 = azar1;


        ////Determinamos el borde del azar 1 y sacamos el borde azar 2 del contrario
        Integer azar2 = null;
        if(bordeIzquierdo.contiene(azar1)){
            bordeIzquierdo.elimina(azar1);
            azar2 = numeroAleatorioConjuntoInteger(bordeDerecho);

        } else if (bordeSuperior.contiene(azar1)){
            bordeSuperior.elimina(azar1);
            azar2 = numeroAleatorioConjuntoInteger(bordeInferior);

        } else if (bordeDerecho.contiene(azar1)){
            bordeDerecho.elimina(azar1);
            azar2 = numeroAleatorioConjuntoInteger(bordeIzquierdo);

        } else if (bordeInferior.contiene(azar1)){
            bordeInferior.elimina(azar1);
            azar2 = numeroAleatorioConjuntoInteger(bordeSuperior);

        }



        //Integer azar2 = numeroAleatorioConjuntoInteger(bordes);
        bordes.elimina(azar2);
        this.borde2 = azar2;

        ///Los restantes serán eliminados de las paredes y de la grafica
        for (Integer i : bordes){
            this.conjuntoIntegerParedes.elimina(i);
            this.grafica.elimina(i);
            this.conjuntoIntegerEspacios.elimina(i);
        }

        this.conjuntoIntegerEspacios.agrega(azar1);
        this.conjuntoIntegerEspacios.agrega(azar2);
        actualizaChar(azar1, ' ',false);
        actualizaChar(azar2, ' ',false);






    }

    private void algoritmoPrim2(){


        //System.out.println("La grafica:");

        //System.out.println(this.grafica.toString());

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



        ///Buscaremos un numero aleatorio que no sea alguno de los bordes

        Integer n = null;
        boolean borde = true;

        while (borde){
            n = numeroAleatorioConjuntoInteger(conjuntoIntegerEspacios);
            if (!n.equals(borde1) && !n.equals(borde2)){
                borde = false;
            }

        }




        //Integer n = numeroAleatorioConjuntoInteger(conjuntoIntegerEspacios);
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

            actualizaChar(Ve, '?',true);

        }




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
                    actualizaChar(pared, ' ',true);


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
            actualizaChar(espacioAzar, ' ',true);
            //this.colaLaberinto.agrega(this.charLaberinto);

            //////Despues tenemos que buscar a sus espaciosFrontera del espacioAzar que no se encuentren ya
            ////// en espaciosDelMazo ni en espaciosVisitados y agregarlos a listaFronteras

            Conjunto<Integer> fronterasEspacioAzar = getFronterasEspacio(verticeEspacioAzar);



            for (Integer Ve : fronterasEspacioAzar){
                VerticeGrafica<Integer> vertice = grafica.vertice(Ve);

                if(!espaciosDelMazo.contiene(Ve) && !espaciosVisitados.contiene(Ve)){
                    listaFronteras.agrega(Ve);
                    actualizaChar(Ve, '?',true);


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

    /*
    Metodo que devuelve si son esquinas
     */

    private Boolean sonEsquinas (Integer rows, Integer columnas){

        if (rows.equals(0) && columnas.equals(0)){
            return true;
        }

        if (rows.equals(0) && columnas.equals(nCols-1)){
            return true;
        }

        if (rows.equals(nRows-1) && columnas.equals(0)){
            return true;
        }

        return rows.equals(nRows - 1) && columnas.equals(nCols - 1);


    }

    /*
    Metodo para obtener el charLaberinto
     */

    public char[][] getCharLaberinto() {
        return charLaberinto;
    }

    public Cola<char[][]> getColaLaberinto() {
        return colaLaberinto;
    }



    private void imprimeColaLaberinto(){
        Integer Cols = laberintoOriginal.get(0).length();
        ///Las rows serán el numero de strings en la lista que contiene al laberinto
        Integer Rows = laberintoOriginal.getLongitud();


        while (!colaLaberinto.esVacia()){

            char [] [] lab = colaLaberinto.saca();
            for(int rows = 0; rows < nRows; rows ++){
                for (int columnas = 0; columnas < nCols; columnas ++){

                    System.out.print(lab[rows][columnas]);
                }
                System.out.println();

            }
        }








    }

    public Lista<String> getLaberintoOriginal() {
        return laberintoOriginal;
    }



}
