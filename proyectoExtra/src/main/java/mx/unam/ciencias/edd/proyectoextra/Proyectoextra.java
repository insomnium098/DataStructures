package mx.unam.ciencias.edd.proyectoextra;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import mx.unam.ciencias.edd.*;

import java.io.*;
import javax.swing.JFrame;
import javax.swing.*;



/**
 * Proyecto extra.
 */
public class Proyectoextra {


    private static void uso() {
        //System.err.println("Debes de ingresar al menos un archivo");
        System.out.println("Parametros: ");
        System.out.println("gui         -Inicia la interfaz gráfica ");
        System.out.println("genera n m  -Genera un laberinto de n x m ");
        System.out.println("resuelve r  -Resuelve el laberinto r");


        System.exit(1);
    }



    public static void main(String[] args) throws IOException {





        if (args.length == 0)
            uso();

        String argumento = args[0];

        switch (argumento) {
            case "resuelve":
                if(args.length != 2){
                    uso();
                } else {
                    Lista<String> laberinto = leeLaberinto(args[1]);
                    resuelveLaberinto(laberinto, false);
                }
                break;

            case "genera": {
                if (args.length != 3){
                    uso();
                } else {
                    try {
                        int nRows = Integer.parseInt(args[1]);
                        int nCols = Integer.parseInt(args[2]);

                        if(nRows <= 0 || nCols <=0){
                            uso();
                        }

                        generaLaberinto(nRows, nCols, false);
                    } catch (Exception ex) {
                        System.out.println("ERROR: Las dimensiones del laberinto a generar deben de ser un entero");
                        uso();
                        //ex.printStackTrace();
                    }
                }
                break;
            }

            case "gui":

                //Para resuelve;
                //Lista<String> laberinto = leeLaberinto(args[1]);
                //resuelveLaberinto(laberinto, true);
                menu();


                ///Para genera:
                //generaLaberinto(15, 15,true);
                break;
            default:
                uso();

        }


    }

    /*
    Metodo que lee el laberinto y devuelve una lista doblemente ligada con cada renglon del archivo
     */

    public static Lista<String> leeLaberinto (String nombreArchivo){
        ///leemos el archivo

        Lista<String> laberinto = new Lista<>();
        BufferedReader br;
        String linea;

        try {
            br = new BufferedReader(new FileReader(nombreArchivo));
            while ((linea = br.readLine()) != null){
                laberinto.agrega(linea);
            }


        } catch (Exception e ){
            System.out.println("No se pudo leer el archivo con el laberinto");
            System.exit(1);
        }


        ///Revisamos el laberinto, si no es valido terminamos el programa

        if (!revisaLaberinto(laberinto)){
            System.out.println("El laberinto no es valido, ");
            System.out.println("el número de columnas no es el mismo en todas las rows ó");
            System.out.println("alguna row es vacía. Saliendo del programa");
            System.exit(1);
        }


        return laberinto;


    }

    /*
    Metodo que genera un laberinto
     */
    public static void generaLaberinto(Integer nRows, Integer nCols, boolean GUI){

        if (!GUI){

            Laberinto lab = new Laberinto(nRows, nCols, false);


        } else {

            System.out.println("Cargando....");
            Laberinto lab = new Laberinto(nRows, nCols, true);
            iniciaGUI(lab,false);

        }

    }


    /*
    Metodo que resuelve un laberinto
     */

    public static void resuelveLaberinto(Lista<String> laberinto, boolean GUI){

        if(!GUI){
            Laberinto lab = new Laberinto(laberinto, false);

        } else{
            Laberinto lab = new Laberinto(laberinto, true);
            iniciaGUI(lab, true);

        }

    }

    /*
    Metodo que revisa si el laberinto ingresado tiene el mismo número de cols en cada linea y si alguna linea
    no es vacia
     */

    public static boolean revisaLaberinto(Lista<String> laberinto){
        Integer nCols = laberinto.get(0).length();
        ///Las rows serán el numero de strings en la lista que contiene al laberinto
        Integer nRows = laberinto.getLongitud();

        for (String linea : laberinto){
            if (linea.length() != nCols){
                return false;
            }

            if (linea.isEmpty()){
                return false;
            }
        }

        return true;

    }


    /*
    Metodo para inicializar la gui
     */

    public static void iniciaGUI(Laberinto laberinto, boolean resuelve){

        /*
        Dibujar panel = new Dibujar();
        JFrame aplicacion = new JFrame();

        aplicacion.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        aplicacion.add(panel);		//agrega el panel al marco
        aplicacion.setSize(800, 800);	//establece tamaño del marco
        aplicacion.setLocationRelativeTo(null);
        aplicacion.setVisible(true);

         */

        ////Definiendo los botones

        JButton botonResuelve = new JButton("Resuelve Laberinto con el algoritmo de Dijkstra");
        JButton botonGuarda = new JButton("Guarda el laberinto");




        DibujaLaberintoGUI panel = new DibujaLaberintoGUI(laberinto);
        JFrame aplicacion = new JFrame();





        aplicacion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        ///Aqui se añade el comportamiento de los botones




        botonResuelve.addMouseListener(new MouseAdapter()  {
            public void mouseClicked(MouseEvent e)  {
                try {
                    panel.guardaLaberinto("temp.txt");
                    Lista<String> laberinto = leeLaberinto("temp.txt");
                    File temp= new File("temp.txt");
                    temp.delete();
                    resuelveLaberinto(laberinto, true);


                } catch (IOException ioException) {
                    System.out.println("Error al guardar el laberinto");
                    ioException.printStackTrace();
                    System.exit(1);
                }


            }
        });


        botonGuarda.addMouseListener(new MouseAdapter()  {
            public void mouseClicked(MouseEvent e)  {

                ///Para abrir en la carpeta donde se ejecuto
                File wd= new File(System.getProperty("user.dir"));

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(wd);
                fileChooser.setDialogTitle("Especifica el archivo para guardar el laberinto");

                int seleccion = fileChooser.showSaveDialog(aplicacion);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File archivoGuardar = fileChooser.getSelectedFile();


                    try {
                        panel.guardaLaberinto(archivoGuardar.getAbsolutePath());
                        Menu.mensaje("Laberinto correctamente guardado en : "+ archivoGuardar.getAbsolutePath(),
                                "Archivo guardado correctamente");


                    } catch (IOException ioException) {

                        Menu.mensaje("Error al guardar el archivo", "Error al guardar");

                    }


                }








            }
        });











        //////////////

        ///Localizacion de los botones

        ////Aqui se agregan los botones

        if(!resuelve){
            panel.add(botonResuelve);
            //panel.add(botonGuarda);

            aplicacion.setLayout(new BorderLayout());
            aplicacion.add(botonResuelve,BorderLayout.SOUTH);
            //aplicacion.add(botonGuarda,BorderLayout.NORTH);

        }

        panel.add(botonGuarda);
        aplicacion.add(botonGuarda,BorderLayout.NORTH);

        //////



        ////Añadir el panel

        aplicacion.add(panel);


        aplicacion.setSize(800, 800);
        aplicacion.setLocationRelativeTo(null);
        aplicacion.setVisible(true);
    }

    public static void menu(){
        Menu panel = new Menu();
        JFrame aplicacion = new JFrame();



        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setSize(800, 300);
        panel.setLocationRelativeTo(null);
        panel.setVisible(true);


    }


}
