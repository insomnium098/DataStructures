package mx.unam.ciencias.edd.proyectoextra;
import mx.unam.ciencias.edd.Lista;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Menu extends JFrame {
    JPanel panelTextos, panelBotones, panelInputs, panel;
    JLabel label1, label2, label3;
    JTextField columnas, renglones;
    JButton botonGenera, botonResuelve;


    public Menu(){
        super("Proyecto Extra de Estructuras de Datos. Autor: Antonio Daniel Martinez Gutierrez");

        panelTextos = new JPanel();
        panelTextos.setLayout(new BoxLayout(panelTextos, BoxLayout.LINE_AXIS));

        panelInputs = new JPanel();
        panelInputs.setLayout(new BoxLayout(panelInputs, BoxLayout.LINE_AXIS));

        panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.LINE_AXIS));

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        label1 = new JLabel("Ingresa el número de renglones");
        label2 = new JLabel("Ingresa el número de columnas");


        renglones = new JTextField(10);
        renglones.setMinimumSize(new Dimension(150, 20));
        renglones.setMaximumSize(new Dimension(15,20));
        columnas = new JTextField(10);
        columnas.setMinimumSize(new Dimension(50, 25));
        columnas.setMaximumSize(new Dimension(15,20));

        botonGenera = new JButton("Genera un laberinto");
        botonResuelve = new JButton("Resuelve el laberinto");


        botonGenera.setEnabled(false);



        ///////////////

        ////Listener para renglones
        renglones.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {



                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                    renglones.setEditable(true);

                    botonGenera.setEnabled(true);


                } else {

                    renglones.setText("");
                    botonGenera.setEnabled(false);

                }
            }
        });


        ///////

        /// Listener para botonGenera
        botonGenera.addMouseListener(new MouseAdapter()  {
            public void mouseClicked(MouseEvent e)  {

                String rowsS=renglones.getText();
                String colsS=columnas.getText();

                if (rowsS.matches("-?\\d+") && colsS.matches("-?\\d+")){
                    botonGenera.setEnabled(true);
                } else {
                    botonGenera.setEnabled(false);
                }

            }
        });


        ////





        ///////////////

        ////Listener para columnas
        columnas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {



                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                    columnas.setEditable(true);
                    botonGenera.setEnabled(true);


                } else {
                    columnas.setText("");
                    botonGenera.setEnabled(false);

                }
            }
        });


        ///////

        botonGenera.addMouseListener(new MouseAdapter()  {
            public void mouseClicked(MouseEvent e)  {

                String rowsS=renglones.getText();
                String colsS=columnas.getText();
                if (rowsS == null || colsS == null){
                    mensaje("Selecciona un número de renglones y columnas", "Error");
                }

                try{
                    Integer rows = Integer.parseInt(rowsS);
                    Integer cols = Integer.parseInt(colsS);

                    if (rows == 0 || cols == 0){
                        mensaje("Selecciona un número válido de renglones y columnas", "Error");
                    } else {
                        Proyectoextra.generaLaberinto(rows,cols,true);
                    }



                } catch (Exception ex){
                    mensaje("Selecciona un número válido de renglones y columnas", "Error");

                }






            }
        });
        botonResuelve.addMouseListener(new MouseAdapter()  {
            public void mouseClicked(MouseEvent e)  {

                String archivo = abrirExplorador();
                ////Verificamos que el laberinto sea valido

                boolean esValido = leeLaberinto(archivo);

                if(esValido){
                    Lista<String> laberinto = Proyectoextra.leeLaberinto(archivo);
                    Proyectoextra.resuelveLaberinto(laberinto, true);

                } else {
                    mensaje("El laberinto está corrupto","Laberinto corrupto");

                }






            }
        });


        panelTextos.add(Box.createHorizontalGlue());
        panelTextos.add(label1);
        panelTextos.add(Box.createHorizontalGlue());
        panelTextos.add(label2);
        panelTextos.add(Box.createHorizontalGlue());


        panelInputs.add(Box.createHorizontalGlue());
        panelInputs.add(renglones);
        panelInputs.add(Box.createHorizontalGlue());
        panelInputs.add(columnas);
        panelInputs.add(Box.createHorizontalGlue());


        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(botonGenera);
        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(botonResuelve);
        panelBotones.add(Box.createHorizontalGlue());

        panel.add(Box.createVerticalGlue());
        panel.add(panelTextos);
        panel.add(panelInputs);
        panel.add(Box.createVerticalGlue());
        panel.add(panelBotones);
        panel.add(Box.createVerticalGlue());



        ///////////


        this.add(panel);



    }

    private String abrirExplorador(){

        ///Para abrir en la carpeta donde se ejecuto
        File wd= new File(System.getProperty("user.dir"));

        ////
        JFileChooser fc = new JFileChooser("~");


        fc.setCurrentDirectory(wd);

        int returnVal = fc.showOpenDialog(getParent());





        if (returnVal == JFileChooser.APPROVE_OPTION){
            String archivo = fc.getSelectedFile().getAbsolutePath();
            return archivo;
        }

        return "";




    }


    public static void mensaje(String infoMessage, String titleBar){
        JOptionPane.showMessageDialog(null, infoMessage, "Mensaje: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }


    public static Boolean leeLaberinto (String nombreArchivo){
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
            //System.exit(1);
            return false;
        }




        if (!revisaLaberinto(laberinto)){
            return false;


        }


        return true;


    }


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


}
