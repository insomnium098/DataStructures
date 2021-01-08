package mx.unam.ciencias.icc.chat;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Servidor para Examen de ICC
 */



// Server class
public class Servidor
{
    public static void main(String[] args) throws IOException
    {
        // Establecemos el socket y el puerto 6666
        ServerSocket socketServidor = new ServerSocket(6666);


        ///Ejecutar el servidor en loop infiniro

        while (true)
        {
            //Definimos un socket para el cliente nulo en cada iteracion para poder recibir nuevos clientes
            Socket socketCliente = null;

            try
            {
                // El socket del cliente acepta la conexion
                socketCliente = socketServidor.accept();

                System.out.println("Un nuevo cliente se ha conectado : " + socketCliente);

                // Se generan los streams para recibir y enviar mensajes entre servidor y cliente
                DataInputStream input = new DataInputStream(socketCliente.getInputStream());
                DataOutputStream output = new DataOutputStream(socketCliente.getOutputStream());

                ////Asignar un hilo nuevo a cada cliente
                System.out.println("Se asignara un nuevo thread para este cliente");

                Thread hiloNuevo = new manejaCliente(socketCliente, input, output,null);

                // Iniciamos el hilo
                hiloNuevo.start();

            }
            catch (Exception e){
                socketCliente.close();
                e.printStackTrace();
            }
        }
    }
}

// Clase para manejar a los clientes del chat
class manejaCliente extends Thread
{

    final DataInputStream input;
    final DataOutputStream output;
    final Socket socket;
    String mensajeDeServidor;


    // Construir el manejador del cliente
    public manejaCliente(Socket socket, DataInputStream input, DataOutputStream output, String mensajeDeServidor )
    {
        this.socket = socket;
        this.input = input;
        this.output = output;
    }

    /*
    Metodo para iniciar el manejadorCliente
     */
    @Override
    public void run()
    {

        ///Inicializamos los mensajes que reciben y que salen del manejador
        String mensajeRecibido;
        //String mensajeEnviar;
        String nickName = "";
        boolean tieneNickname = false;
        boolean conexionActiva = true;
        while (conexionActiva)
        {
            try {

                if(!tieneNickname){

                    // Recibimos la respuesta del cliente
                    mensajeRecibido = input.readUTF();
                    nickName = mensajeRecibido;
                    tieneNickname = true;
                    // Escribimos el mensaje que recibira la primera conexion
                    output.writeUTF("Bienvenido al Chat \n"+ "Tu nickname es: " +nickName);
                    //output.writeUTF("Perfecto, tu nickname es: \n"+ nickName);
                    System.out.println("El nickname del cliente es: " + nickName);

                }

                //output.writeUTF("Ya tienes un nickname:" + nickName);
                output.writeUTF(" ");

                mensajeRecibido = input.readUTF();

                ///Si el mensaje del servidor es distinto de nulo, se imprime y despues se vuelve nulo

                if (mensajeDeServidor != null){
                    output.writeUTF("Servidor dice: "+ mensajeDeServidor);
                    mensajeDeServidor = null;
                }



                //output.writeUTF(nickName + ":2 " + mensajeRecibido);


            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("El cliente " + nickName + " se desconecto");
                conexionActiva = false;
            }
        }

    }
}


