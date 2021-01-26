package mx.unam.ciencias.edd.proyectoextra;
import mx.unam.ciencias.edd.*;

public class Laberinto {

    private Lista<String> laberintoOriginal;
    private Integer [][] arrayLaberinto;
    private char [][] charLaberinto;
    private Grafica<Integer> grafica;
    private Integer nRows;
    private Integer nCols;

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
        this.grafica = new Grafica<>();
        this.trayectoria = new Lista<>();


        this.iniciaArray();
        this.iniciaChar();

        ////
        System.out.println("Imprimiendo laberinto");
        this.imprimeLaberinto();
        ///

        /*
        Aqui va el algoritmo que genere el laberinto
         */


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

}
