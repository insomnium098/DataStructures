package mx.unam.ciencias.edd.proyectoextra;
import mx.unam.ciencias.edd.Lista;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Menu extends JFrame {
    JPanel panelTextos, panelBotones, panelInputs, panel;
    JLabel label1, label2, label3;
    JTextField columnas, renglones;
    JButton botonGenera, botonResuelve;
    //final JFileChooser fc = new JFileChooser("~");

    public Menu(){
        super("Menú");

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
        //label3 = new JLabel("Ingresa los segundos de delay");

        renglones = new JTextField(10);
        renglones.setMinimumSize(new Dimension(150, 20));
        renglones.setMaximumSize(new Dimension(15,20));
        columnas = new JTextField(10);
        columnas.setMinimumSize(new Dimension(50, 25));
        columnas.setMaximumSize(new Dimension(15,20));
        //delay = new JTextField(10);
        //delay.setMinimumSize(new Dimension(50, 25));
        //delay.setMaximumSize(new Dimension(15, 20));

        botonGenera = new JButton("Genera un laberinto");
        botonResuelve = new JButton("Resuelve el laberinto");
        //botonBorra = new JButton("Borra el laberinto");

        botonGenera.setEnabled(false);



        ///////////////

        ////Listener para renglones
        renglones.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {



                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                    renglones.setEditable(true);
                    //label.setText("");
                    botonGenera.setEnabled(true);


                } else {
                    //renglones.setEditable(false);
                    renglones.setText("");
                    botonGenera.setEnabled(false);

                }
            }
        });


        ///////

        /// Listener para botonGenera
        botonGenera.addMouseListener(new MouseAdapter()  {
            public void mouseClicked(MouseEvent e)  {
                //---- Añade lo que quieras que haga el boton para generar -----
                String rowsS=renglones.getText();
                String colsS=columnas.getText();

                if (rowsS.matches("-?\\d+") && colsS.matches("-?\\d+")){
                    botonGenera.setEnabled(true);
                } else {
                    botonGenera.setEnabled(false);
                }




                //Proyectoextra.generaLaberinto(Integer.valueOf(rowsS),Integer.valueOf(colsS),true);
            }
        });


        ////





        ///////////////

        ////Listener para columnas
        columnas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {



                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                    columnas.setEditable(true);
                    //label.setText("");
                    botonGenera.setEnabled(true);


                } else {
                    //renglones.setEditable(false);
                    columnas.setText("");
                    botonGenera.setEnabled(false);

                }
            }
        });


        ///////

        botonGenera.addMouseListener(new MouseAdapter()  {
            public void mouseClicked(MouseEvent e)  {
                //---- Añade lo que quieras que haga el boton para generar -----
                String rowsS=renglones.getText();
                String colsS=columnas.getText();

                Proyectoextra.generaLaberinto(Integer.valueOf(rowsS),Integer.valueOf(colsS),true);
            }
        });
        botonResuelve.addMouseListener(new MouseAdapter()  {
            public void mouseClicked(MouseEvent e)  {
                //---- Añade lo que quieras que haga el boton para resolver -----
                String archivo = abrirExplorador();
                Lista<String> laberinto = Proyectoextra.leeLaberinto(archivo);

                Proyectoextra.resuelveLaberinto(laberinto, true);

            }
        });

        /*
        botonBorra.addMouseListener(new MouseAdapter()  {
            public void mouseClicked(MouseEvent e)  {
                //---- Añade lo que quieras que haga el boton para borrar -----
            }
        });

         */

        panelTextos.add(Box.createHorizontalGlue());
        panelTextos.add(label1);
        panelTextos.add(Box.createHorizontalGlue());
        panelTextos.add(label2);
        panelTextos.add(Box.createHorizontalGlue());
        /*
        panelTextos.add(label3);
        panelTextos.add(Box.createHorizontalGlue());

         */

        panelInputs.add(Box.createHorizontalGlue());
        panelInputs.add(renglones);
        panelInputs.add(Box.createHorizontalGlue());
        panelInputs.add(columnas);
        panelInputs.add(Box.createHorizontalGlue());

        /*
        panelInputs.add(delay);
        panelInputs.add(Box.createHorizontalGlue());

         */

        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(botonGenera);
        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(botonResuelve);
        panelBotones.add(Box.createHorizontalGlue());
        /*
        panelBotones.add(botonBorra);
        panelBotones.add(Box.createHorizontalGlue());

         */

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

        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                "Laberintos en txt", "txt");
        fc.setFileFilter(filtro);
        fc.setCurrentDirectory(wd);

        int returnVal = fc.showOpenDialog(getParent());
        //System.out.println(returnVal);

        /*
        String archivo;
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            archivo = fc.getSelectedFile().getName();
        }

         */

        return fc.getSelectedFile().getName();





    }


}
