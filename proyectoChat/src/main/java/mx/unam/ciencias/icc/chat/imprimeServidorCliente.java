package mx.unam.ciencias.icc.chat;
import java.util.*;
import java.io.*;
import java.net.*;

public class imprimeServidorCliente extends Thread {

    private DataInputStream input;

    imprimeServidorCliente(DataInputStream input){
        this.input = input;
    }

    public void run(){


        while(true){
            try {
                String mensajeRecibido = input.readUTF();
                System.out.println(mensajeRecibido);

            } catch (Exception e){
                //System.out.println("No se pudo imprimir el mensaje");

            }

        }

    }




}
