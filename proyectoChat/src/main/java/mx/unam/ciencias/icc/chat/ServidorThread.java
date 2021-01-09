package mx.unam.ciencias.icc.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.*;

public class ServidorThread extends Thread {

    private ServerSocket socketServidor;
    public List<manejaCliente> listaClientes;
    private DataInputStream input;
    private DataOutputStream output;
    private Mensajes mensajes;
    private List<MensajeCliente> listaMensajeCliente;



    public ServidorThread(int puerto){

        try {
            this.socketServidor = new ServerSocket(puerto);
            System.out.println("Inicializando servidor del chat");
            listaClientes = Collections.synchronizedList(new ArrayList<manejaCliente>());
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
            //System.out.println("TEST");

            try {

                //Mensajes mensaje = new Mensajes();


                //Definimos un socket para el cliente nulo en cada iteracion para poder recibir nuevos clientes


                //try
                //{
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

                manejaCliente Cliente = new manejaCliente(socketCliente, input, output,nickName);


                Thread hiloNuevo = Cliente;

                //Thread hiloNuevo = new manejaCliente(socketCliente, input, output,null);
                // Iniciamos el hilo
                hiloNuevo.start();

                ///Registramos el cliente en los mensajes que van del servidor al cliente
                mensajes.registerObserver(Cliente);


                ////Obtenemos el mensajeCliente del cliente que acabamos de inicializar
                MensajeCliente mensajeDelCliente = Cliente.getMensajeCliente();

                //La añadimos a la lista de MensajeCliente
                //listaMensajeCliente.add(mensajeDelCliente);

                ///tENEMOS QUE HACERNOS OBSERVADORES DE ESTE MENSAJEDELCLIENTE,
                //Para que el servidor registre los mensajes del cliente y los replique a los demas clientes
                ///Los observadores de mensajeDelCliente replicaran sus mensajes
                //mensajeDelCliente.

                ///Tal vez debemos de hacer una lista de mensajeDelCliente y añadir a cada cliente,
                ///Despues hacer un objeto que observe esa lista y que se la mande al servidor en main


                ///Añadimos al cliente a la lista de clientes
                listaClientes.add(Cliente);

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
