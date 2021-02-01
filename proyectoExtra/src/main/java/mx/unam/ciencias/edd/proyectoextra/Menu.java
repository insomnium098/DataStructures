package mx.unam.ciencias.edd.proyectoextra;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Menu extends JFrame {
    JPanel panelTextos, panelBotones, panelInputs, panel;
    JLabel label1, label2, label3;
    JTextField columnas, renglones, delay;
    JButton botonGenera, botonResuelve, botonBorra;
    final JFileChooser fc = new JFileChooser("~");

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
        label3 = new JLabel("Ingresa los segundos de delay");

        renglones = new JTextField(10);
        renglones.setMinimumSize(new Dimension(150, 20));
        renglones.setMaximumSize(new Dimension(15,20));
        columnas = new JTextField(10);
        columnas.setMinimumSize(new Dimension(50, 25));
        columnas.setMaximumSize(new Dimension(15,20));
        delay = new JTextField(10);
        delay.setMinimumSize(new Dimension(50, 25));
        delay.setMaximumSize(new Dimension(15, 20));

        botonGenera = new JButton("Genera un laberinto");
        botonResuelve = new JButton("Resuelve el laberinto");
        botonBorra = new JButton("Borra el laberinto");

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
                abrirExplorador();
            }
        });
        botonBorra.addMouseListener(new MouseAdapter()  {
            public void mouseClicked(MouseEvent e)  {
                //---- Añade lo que quieras que haga el boton para borrar -----
            }
        });

        panelTextos.add(Box.createHorizontalGlue());
        panelTextos.add(label1);
        panelTextos.add(Box.createHorizontalGlue());
        panelTextos.add(label2);
        panelTextos.add(Box.createHorizontalGlue());
        panelTextos.add(label3);
        panelTextos.add(Box.createHorizontalGlue());

        panelInputs.add(Box.createHorizontalGlue());
        panelInputs.add(renglones);
        panelInputs.add(Box.createHorizontalGlue());
        panelInputs.add(columnas);
        panelInputs.add(Box.createHorizontalGlue());
        panelInputs.add(delay);
        panelInputs.add(Box.createHorizontalGlue());

        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(botonGenera);
        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(botonResuelve);
        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(botonBorra);
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

    private void abrirExplorador(){

        int returnVal = fc.showOpenDialog(this);
        System.out.println(returnVal);
    }


}
