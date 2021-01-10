package mx.unam.ciencias.icc.chat;


import java.io.*;


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

        while(true){
            //System.out.println("Primer parte de test ServidorRecibeinputCliente");
            try {
                String mensajeRecibido = input.readUTF();
                System.out.println(mensajeRecibido);

                ServidorThread.mensajes.añadeMensaje(mensajeRecibido);


            } catch (Exception e){
                //System.out.println("Nada enviado");

            }

        }
    }


}
