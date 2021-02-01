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

        char[][] charLaberinto;

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
                //System.out.println(charLaberinto[i][j]);


                colorearRecuadro(j * intervalo_altura, (j + 1) * val2,
                        i * intervalo_anchura, val1 * (i + 1), charLaberinto[i][j], g);


            }
            //System.out.println();
        }




        /*



        Cola<char[][]> colaLaberinto = laberinto.getColaLaberinto();


        while (!colaLaberinto.esVacia()){
            char[][] cambiosLaberinto = colaLaberinto.saca();
            imprimeLaberinto(cambiosLaberinto, g);

            for (int i = 1; i < columnas; i++){
                g.drawLine(i * intervalo_altura, 0, i * intervalo_altura, altura);
            }
            for (int i = 1; i < renglones; i++){
                g.drawLine(0, i * intervalo_anchura, anchura, i * intervalo_anchura);
            }



            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            repaint();

         */










        /*
        imprimeLaberinto(charLaberinto, g);

        for (int i = 1; i < columnas; i++){
            g.drawLine(i * intervalo_altura, 0, i * intervalo_altura, altura);
        }
        for (int i = 1; i < renglones; i++){
            g.drawLine(0, i * intervalo_anchura, anchura, i * intervalo_anchura);
        }

         */

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
            g.setColor(new Color(0, 255, 239));
        }
        for (int i = y1; i < y2; i++){
            g.drawLine(x1, i, x2, i);
        }
    }


    /*
    Metodo para ir imprimiendo el laberinto conforme se haya generado o resuelto por pasos
     */


    private void imprimeLaberinto(char[][] charLaberinto, Graphics g){
        int anchura = getWidth();
        int altura = getHeight();


        int columnas = charLaberinto[0].length;
        int renglones = charLaberinto.length;

        int intervalo_anchura = altura / renglones;
        int intervalo_altura = anchura / columnas;

        int dif1 = ((intervalo_altura* columnas) - anchura) / 2;
        int dif2 = ((intervalo_anchura* renglones) - altura) / 2;

        int val1;
        int val2;
        int contador = 0;

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
                //System.out.println(charLaberinto[i][j]);

                /*
                if(trayectoria.contains(contador)) {
                    colorearRecuadro(j * intervalo_altura, (j+1) * val2,
                            i * intervalo_anchura, val1 * (i+1), '*', g);
                } else {
                    colorearRecuadro(j * intervalo_altura, (j + 1) * val2,
                            i * intervalo_anchura, val1 * (i + 1), charLaberinto[i][j], g);
                }

                 */

                colorearRecuadro(j * intervalo_altura, (j + 1) * val2,
                        i * intervalo_anchura, val1 * (i + 1), charLaberinto[i][j], this.getComponentGraphics(g));

                contador++;

            }
        }




    }

    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == timer && !this.colaLaberinto.esVacia()) {
            repaint();
        }
    }

    public void BorraLaberinto(){

        Graphics g = this.getGraphics();

        for (int i = 1; i < cols; i++){
            g.drawLine(i * iA, 0, i * iA, alt);
        }
        for (int i = 1; i < rows; i++){
            g.drawLine(0, i * interAnchura, Anch, i * interAnchura);
        }
        System.out.println("BORA");

        repaint();



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
