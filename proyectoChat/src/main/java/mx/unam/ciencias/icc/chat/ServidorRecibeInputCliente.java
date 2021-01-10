package mx.unam.ciencias.icc.chat;

import java.util.*;
import java.io.*;
import java.net.*;

public class ServidorRecibeInputCliente{
    DataInputStream input;
    String nombreCliente;
    MensajeCliente mensajeDECliente;

    ServidorRecibeInputCliente( DataInputStream input, String nombreCliente, MensajeCliente mensajeDECliente){
        this.input = input;
        this.nombreCliente = nombreCliente;
        this.mensajeDECliente = mensajeDECliente;
    }


    public void run(){
        //System.out.println("Iniciando ServidorRecibeinputCliente de " + nombreCliente );

        //Scanner scanner = new Scanner(System.in);
        ///registrar a sm

        //ServidorThread.mensajes.registerObserver(Servidor.sm);


        while(true){
            //System.out.println("Primer parte de test ServidorRecibeinputCliente");
            try {
                String mensajeRecibido = input.readUTF();
                //System.out.println("Mensaje recibido del cliente: " + nombreCliente);
                System.out.println(mensajeRecibido);
                //Servidor.listaMensajes.add(mensajeRecibido);
                //Servidor.listaMensajes.add(mensajeRecibido);
                //mensajeDECliente.addObserver(Servidor.sm);
                //mensajeDECliente.añadeMensaje(mensajeRecibido);
                ServidorThread.mensajes.añadeMensaje(mensajeRecibido);


            } catch (Exception e){
                //System.out.println("Nada enviado");

            }

        }
    }


}
