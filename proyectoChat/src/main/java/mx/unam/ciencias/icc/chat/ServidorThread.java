package mx.unam.ciencias.icc.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.*;

public class ServidorThread extends Thread {

    private ServerSocket socketServidor;
    private DataInputStream input;
    private DataOutputStream output;
    public static Mensajes mensajes;
    private List<MensajeCliente> listaMensajeCliente;
    private MensajeCliente mensajeDECliente;



    public ServidorThread(int puerto, MensajeCliente mensajeDECliente){

        try {
            this.socketServidor = new ServerSocket(puerto);
            System.out.println("Inicializando servidor del chat");
            mensajes = new Mensajes();
            this.start();

        } catch (Exception e){
            System.out.println("No se puede crear el socket del servidor");
            e.printStackTrace();
        }

    }


    public void run(){
        while (true){
            Socket socketCliente = null;

            try {

                // El socket del cliente acepta la conexion
                socketCliente = socketServidor.accept();

                System.out.println("Un nuevo cliente se ha conectado");

                // Se generan los streams para recibir y enviar mensajes entre servidor y cliente
                DataInputStream input = new DataInputStream(socketCliente.getInputStream());
                DataOutputStream output = new DataOutputStream(socketCliente.getOutputStream());

                // Recibimos la respuesta del cliente
                String nickName = input.readUTF();
                System.out.println("El nombre del cliente es: " + nickName);
                ////Asignar un hilo nuevo a cada cliente
                System.out.println("Se asignara un nuevo thread para este cliente");

                manejaCliente Cliente = new manejaCliente(socketCliente, input, output,nickName, mensajeDECliente);


                Thread hiloNuevo = Cliente;

                // Iniciamos el hilo
                hiloNuevo.start();

                ///Registramos el cliente en los mensajes que van del servidor al cliente
                mensajes.registraObservadores(Cliente);

                ///

                ///Imprimimos un par de mensajes de Bienvenida
                ///Este mensaje se imprime en el servidor
                mensajes.añadeMensaje("Servidor: Hola " + nickName +" Bienvenido al chat");


            } catch (Exception e){
                System.out.println("Error en servidor");
                e.printStackTrace();
            }

        }
    }


    /*
    Metodo para acceder a los mensajes del servidor
     */

    public Mensajes getMensajes (){
        return this.mensajes;
    }


}
