package mx.unam.ciencias.edd.proyectoextra;
import mx.unam.ciencias.edd.*;

public class Laberinto {

    private Lista<String> laberintoOriginal;
    private Integer [][] arrayLaberinto;
    private char [][] charLaberinto;
    private Grafica<Integer> grafica;

    ///variables para resuelveLaberinto
    private Integer origen;
    private Integer destino;
    private Lista<Integer> trayectoria;


    /*
    El laberinto tendrá dos constructores, uno para inicializarlo desde generaLaberinto
    y otro para recibor una lista desde resuelveLaberinto
     */

    //Constructor para resuelveLaberinto
    public Laberinto (Lista<String> laberintoOriginal){
        this.laberintoOriginal = laberintoOriginal;
        this.grafica = new Grafica<>();
        this.trayectoria = new Lista<>();
        this.iniciaArray();
        this.iniciaChar();
        this.construyeEdges();
        this.calculaTrayectoria();
        this.imprimeLaberinto();

    }

    /*
    Inicializadores para resuelveLaberinto
     */
    private void iniciaArray(){

        ///Primero determinamos la longitud del laberinto
        ///El numero de "columnas" será la longitud de cada string
        Integer nColumnas = laberintoOriginal.get(0).length();
        ///Las rows serán el numero de strings en la lista que contiene al laberinto
        Integer nRows = laberintoOriginal.getLongitud();

        Integer [][] laberintoNum = new Integer[nRows] [nColumnas];

        ///Llenar el laberinto con integers, cada uno representa un char en el string
        //El contador comenzará en 0
        Integer contador = 0;

        for(int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nColumnas; columnas ++){
                laberintoNum[rows][columnas] = contador;
                contador ++;
            }

        }
        this.arrayLaberinto = laberintoNum;

    }

    private void iniciaChar(){
        ///Primero determinamos la longitud del laberinto
        ///El numero de "columnas" será la longitud de cada string
        Integer nColumnas = laberintoOriginal.get(0).length();
        ///Las rows serán el numero de strings en la lista que contiene al laberinto
        Integer nRows = laberintoOriginal.getLongitud();

        char [][] laberintoChar = new char [nRows] [nColumnas];

        ///Debemos convertir cada string de la lista en chars y agregarlas al laberinto
        int contador = 0;

        for(String s : laberintoOriginal){
            char [] character = s.toCharArray();
            for (int columns = 0; columns < nColumnas; columns++){
                laberintoChar[contador][columns] = character[columns];
            }
            contador ++;
        }

        this.charLaberinto = laberintoChar;
    }

    private void construyeEdges(){
        Integer nCols = arrayLaberinto[0].length;
        Integer nRows = arrayLaberinto.length;
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

                if(columnas + 1 == nCols ){
                    if(rows + 1 == nRows){
                        break;
                    } else {
                        siguienteNum = arrayLaberinto[rows+1][0];
                        siguienteChar = charLaberinto[rows+1][0];
                    }

                } else {
                    siguienteNum = arrayLaberinto[rows][columnas+1];
                    siguienteChar = charLaberinto[rows][columnas+1];
                }


                ///Revisar si son el origen y el destino para guardar su
                //localizacion
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




                ///Revisar si estan conectados en el charLaberinto

                if(estanConectados(anteriorChar,siguienteChar)){
                    
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

                    if(estanConectados(anteriorChar,siguienteChar)){

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

        if(a == 'E' && b == ' ' || a == ' ' && b == 'E'){
            return true;
        }

        if(a == 'S' && b == ' ' || a == ' ' && b == 'S'){
            return true;
        }

        return false;

    }

    public Integer getOrigen(){
        return this.origen;
    }

    public Integer getDestino(){
        return this.destino;
    }


    public void calculaTrayectoria(){
        //System.out.println("La trayectoria");
        Lista<VerticeGrafica<Integer>> listaDijkstra = grafica.dijkstra(origen,destino);
        for (VerticeGrafica<Integer> elem : listaDijkstra){
            //System.out.println(elem.get());
            trayectoria.agrega(elem.get());
        }

    }



    /*
    End metodos para resuelve laberinto
     */


    /*
    Metodo para imprimir el laberinto
     */

    public void imprimeLaberinto(){

        ////Removemos de la trayectoria al origen y destino para no sobreescribir su character
        trayectoria.elimina(origen);
        trayectoria.elimina(destino);


        ///Vamos a recorrer el laberinto con un contador, y si el valor del contador está
        // en la trayectoria vamos a imprimir un asterisco

        Integer nCols = charLaberinto[0].length;
        Integer nRows = charLaberinto.length;


        Integer contador = 0;

        for(int rows = 0; rows < nRows; rows ++){
            for (int columnas = 0; columnas < nCols; columnas ++){
                //System.out.print(charLaberinto[rows][columnas]);

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

}
