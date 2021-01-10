package mx.unam.ciencias.icc.chat;



import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Servidor para Examen de ICC
 */



// Server class
public class Servidor {


    static String mensajeDeCliente;
    ///Hacemos una lista de clientes para mandarles el mensaje
    static LinkedList<manejaCliente> listaClientes = new LinkedList<>();
    //Hacemos una cola para los mensajes
    //private PriorityQueue<String> listaMensajes = new PriorityQueue<>();
    static List<Observer> subscribers = new ArrayList<>();

    static PriorityQueue<String> listaMensajes = new PriorityQueue<>();

    static ServidorMensajes sm;


    public static void main(String[] args) throws IOException {
        // Establecemos el socket y el puerto 6666
        //ServerSocket socketServidor = new ServerSocket(6666);
        ///Ejecutar el servidor en loop infinito



        ///Definir los MENSAJES DE CLIENTES
        MensajeCliente mensajeDEclientes = new MensajeCliente();

       // ServidorThread serv = new ServidorThread(6666);



        //System.out.println("Funciona");
        //System.out.println("Bienvenido al servidor del chat, ingresa mensaje a enviar");
        Scanner scanner = new Scanner(System.in);
        //Obtenemos la lista de mensajes del servidor, son el origen para enviar a los clientes
        //Mensajes mensajes = serv.getMensajes();


        //Crear servidor mensajes

        //ServidorMensajes sm = new ServidorMensajes(mensajes, serv);
        //ServidorMensajes sm = new ServidorMensajes(mensajeDEclientes,6666);
        sm = new ServidorMensajes(mensajeDEclientes,6667);


        ///Registramos al servidor como mensajesdeclientes
        mensajeDEclientes.addObserver(sm);

        sm.run();






        //Aqui se envian los mensajes a todos los clientes


        /*





        while(scanner.hasNextLine()){
            String mensajeEnviarClientes = scanner.nextLine();
            mensajes.añadeMensaje(mensajeEnviarClientes);

        }

         */






        }


        /*
        public void update(Observable blog, Object blogPostTitle, Mensajes mensajes){
            mensajes.añadeMensaje(blogPostTitle.toString());
        }

         */

    }
//}

// Clase para manejar a los clientes del chat
class manejaCliente extends Thread implements Observer {

    DataInputStream input;
    DataOutputStream output;
    Socket socket;
    String mensajeDeServidor;
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
    public void update(Observable blog, Object blogPostTitle) {
        //System.out.println("SERVIDOR");
        System.out.println( (String) blogPostTitle );

        try {
            output.writeUTF("Este mensaje proviene del servidor, clase ManejaCliente");
            output.writeUTF(blogPostTitle.toString());

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
        String mensajeRecibido;
        //String mensajeEnviar;
        //String nickName = "";
        boolean tieneNickname = false;
        boolean conexionActiva = true;
        //MensajeCliente mensajeCliente = new MensajeCliente();
        ServidorRecibeInputCliente cliente = new ServidorRecibeInputCliente(input,nombreCliente, mensajeDECliente);
        cliente.run();

        while (conexionActiva) {
            System.out.println("PRIMER PRUEBA DE MANEJACLIENTE");
            try {

                /*

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

                 */

                //output.writeUTF("Ya tienes un nickname:" + nickName);
                output.writeUTF(" ");


                //Hacer clase para recibir mensajes

                //mensajeRecibido = input.readUTF();




                //Servidor.listaMensajes.add(mensajeRecibido);

                ///Si el mensaje del servidor es distinto de nulo, se imprime y despues se vuelve nulo

                if (mensajeDeServidor != null){
                    output.writeUTF("Servidor dice: "+ mensajeDeServidor);
                    mensajeDeServidor = null;
                }

                //output.writeUTF(nombreCliente + ": " + mensajeRecibido);


            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("El cliente " + nombreCliente + " se desconecto");
                conexionActiva = false;
            }
        }

    }


}


