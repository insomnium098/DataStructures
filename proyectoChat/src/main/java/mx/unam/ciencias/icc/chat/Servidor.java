package mx.unam.ciencias.icc.chat;



import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Servidor para Examen de ICC
 */



// Server class
public class Servidor {

    static ServidorMensajes sm;


    public static void main(String[] args) throws IOException {
        // Establecemos el socket y el puerto 6666
        //ServerSocket socketServidor = new ServerSocket(6666);
        ///Ejecutar el servidor en loop infinito



        ///Definir los MENSAJES DE CLIENTES
        MensajeCliente mensajeDEclientes = new MensajeCliente();


        //System.out.println("Funciona");
        //System.out.println("Bienvenido al servidor del chat, ingresa mensaje a enviar");
        //Obtenemos la lista de mensajes del servidor, son el origen para enviar a los clientes
        //Mensajes mensajes = serv.getMensajes();


        //Crear servidor mensajes

        //ServidorMensajes sm = new ServidorMensajes(mensajes, serv);
        //ServidorMensajes sm = new ServidorMensajes(mensajeDEclientes,6666);
        sm = new ServidorMensajes(mensajeDEclientes,6667);


        ///Registramos al servidor como mensajesdeclientes
        mensajeDEclientes.addObserver(sm);

        sm.run();




        }



    }
//}

// Clase para manejar a los clientes del chat
class manejaCliente extends Thread implements Observer {

    DataInputStream input;
    DataOutputStream output;
    Socket socket;
    String nombreCliente;
    MensajeCliente mensajeDECliente;


    // Construir el manejador del cliente
    public manejaCliente(Socket socket, DataInputStream input, DataOutputStream output,
                         String nombreCliente, MensajeCliente mensajeDECliente)
    {
        this.socket = socket;
        this.input = input;
        this.output = output;
        this.nombreCliente = nombreCliente;
        this.mensajeDECliente = mensajeDECliente;
    }

    public String getNombre(){
        return this.nombreCliente;
    }

    @Override
    public void update(Observable obs, Object mensajes) {
        System.out.println( mensajes.toString());

        try {
            //output.writeUTF("Este mensaje proviene del servidor, clase ManejaCliente");
            output.writeUTF(mensajes.toString());

        } catch (Exception e){
            System.out.println("No se pudo imprimir el mensaje que proviene del servidor");
        }

    }

    ///Metodo publico para devolver el mensajeCliente
    public MensajeCliente getMensajeCliente(){
        return this.mensajeDECliente;
    }





    /*
    Metodo para iniciar el manejadorCliente
     */
    @Override
    public void run()
    {
        ///Inicializamos los mensajes que reciben y que salen del manejador

        ServidorRecibeInputCliente cliente = new ServidorRecibeInputCliente(input,nombreCliente, mensajeDECliente);
        cliente.run();

    }


}


