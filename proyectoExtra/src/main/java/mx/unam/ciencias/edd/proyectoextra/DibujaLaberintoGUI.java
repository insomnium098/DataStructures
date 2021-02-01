package mx.unam.ciencias.edd.proyectoextra;

import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Lista;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;


public class DibujaLaberintoGUI extends JPanel implements ActionListener {

    private Laberinto laberinto;
    private Cola<char[][]> colaLaberinto;
    Timer timer= new Timer(1, this);

    private int cols;
    private int iA;
    private int alt;
    private int rows;
    private int interAnchura;
    private int Anch;





    public DibujaLaberintoGUI(Laberinto laberinto){
        this.laberinto = laberinto;
        this.colaLaberinto = laberinto.getColaLaberinto();
        timer.start();

    }

    public void paintComponent(Graphics g) {


        super.paintComponent( g );

        char[][] charLaberinto = null;

        if (timer.getDelay() == 0){
            charLaberinto = laberinto.getCharLaberinto();
        } else if (colaLaberinto.esVacia()){
            charLaberinto = laberinto.getCharLaberinto();
        } else {
            charLaberinto = colaLaberinto.saca();
        }

        //char[][] charLaberinto = colaLaberinto.saca();



        int anchura = getWidth();
        Anch = anchura;

        int altura = getHeight();
        alt = altura;




        int columnas = charLaberinto[0].length;
        cols = columnas;

        int renglones = charLaberinto.length;
        rows = renglones;

        int intervalo_anchura = altura / renglones;

        interAnchura = intervalo_anchura;

        int intervalo_altura = anchura / columnas;
        iA = intervalo_altura;





        int val1;
        int val2;




        //System.out.println((intervalo_altura* columnas) - anchura);
        //System.out.println("JALA VERGAS");




        for (int i = 0; i < renglones; i++){
            for (int j = 0; j < columnas; j++){
                // Dividir el tamaño de la ventana entre el numero de columnas devuelve un flotante
                // a la hora de castearlo a entero quedá un valor sobrante, los siguientes if son un intento
                // burdo de resolver esto
                if (i == renglones - 1){
                    val1 = anchura;
                } else {
                    val1 = intervalo_anchura;
                }
                if (j == columnas - 1){
                    val2 = altura;
                } else {
                    val2 = intervalo_altura;
                }


                colorearRecuadro(j * intervalo_altura, (j + 1) * val2,
                        i * intervalo_anchura, val1 * (i + 1), charLaberinto[i][j], g);


            }
        }



        for (int i = 1; i < columnas; i++){
            g.drawLine(i * intervalo_altura, 0, i * intervalo_altura, altura);
        }
        for (int i = 1; i < renglones; i++){
            g.drawLine(0, i * intervalo_anchura, anchura, i * intervalo_anchura);
        }







    }





    private void colorearRecuadro(int x1, int x2, int y1, int y2, char caracter, Graphics g){
        if (caracter == ' '){
            g.setColor(new Color(255, 255, 255));
        }
        else if (caracter == '#'){
            g.setColor(new Color(0, 0, 0));
        } else if (caracter == '*'){
            g.setColor(new Color(0, 0, 255));
        } else if (caracter == '?'){
            g.setColor(new Color(30, 245, 73));
        } else {
            g.setColor(new Color(0, 0, 0));
        }
        for (int i = y1; i < y2; i++){
            g.drawLine(x1, i, x2, i);
        }
    }




    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == timer && !this.colaLaberinto.esVacia()) {



            repaint();
            //this.paintComponent(this.getGraphics());
        }
    }


    public Lista<String> getLaberintoOriginal() {
        return laberinto.getLaberintoOriginal();
    }

    public char[][] getCharLaberinto() {
        return laberinto.getCharLaberinto();
    }

    public void guardaLaberinto(String nombreArchivo) throws IOException {
        laberinto.guardaLaberinto(nombreArchivo);
    }
}
