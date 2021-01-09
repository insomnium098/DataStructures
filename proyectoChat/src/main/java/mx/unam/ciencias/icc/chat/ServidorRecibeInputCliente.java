package mx.unam.ciencias.icc.chat;
import java.util.*;
import java.io.*;
import java.net.*;

public class ServidorRecibeInputCliente {
    DataInputStream input;
    String nombreCliente;

    ServidorRecibeInputCliente( DataInputStream input, String nombreCliente){
        this.input = input;
        this.nombreCliente = nombreCliente;
    }


    public void run(){
        //System.out.println("Iniciando ServidorRecibeinputCliente de " + nombreCliente );

        //Scanner scanner = new Scanner(System.in);
        while(true){
            //System.out.println("Primer parte de test ServidorRecibeinputCliente");
            try {
                String mensajeRecibido = input.readUTF();
                //System.out.println("Mensaje recibido del cliente: " + nombreCliente);
                System.out.println(mensajeRecibido);

            } catch (Exception e){
                //System.out.println("Nada enviado");

            }

        }
    }


}
