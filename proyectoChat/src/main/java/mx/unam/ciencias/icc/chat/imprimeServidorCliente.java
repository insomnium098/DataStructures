package mx.unam.ciencias.icc.chat;
import java.util.*;
import java.io.*;
import java.net.*;

public class imprimeServidorCliente {

    private DataInputStream input;

    imprimeServidorCliente(DataInputStream input){
        this.input = input;
    }

    public void run(){

        System.out.println("Iniciando imprimeServidorCliente");

        while(true){
            //System.out.println("Primer parte");
            try {
                String mensajeRecibido = input.readUTF();
                System.out.println(mensajeRecibido);

            } catch (Exception e){
                //System.out.println("Nada recibido");

            }

        }

    }




}
