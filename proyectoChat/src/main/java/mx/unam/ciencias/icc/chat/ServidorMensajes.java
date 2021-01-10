package mx.unam.ciencias.icc.chat;


import java.util.Observer;
import java.util.Scanner;
import java.util.*;

///Esta clase será la encargada de implementar Observer y enviar los mensajes recibidos de los clientes

////Esta clase es la que debe de implementar el servidor
public class ServidorMensajes implements Observer {
    int puerto;

    ///Estos son los mensajes que envia el servidor a los clientes
    Mensajes mensajesAclientes;
    ServidorThread servidor;

    //Estos son los mensajes que envian los clientes al servidor
    MensajeCliente mensajeDEClientes;


    ServidorMensajes(MensajeCliente mensajesDEClientes, int puerto){
        this.mensajeDEClientes = mensajesDEClientes;
        this.puerto = puerto;

        ///El servidor pasara el mensajeDEClientes
        this.servidor = new ServidorThread(puerto, mensajesDEClientes);
    }

    public void run(){
        System.out.println("Bienvenido al servidor del chat, ingresa mensaje a enviar");
        Scanner scanner = new Scanner(System.in);
        mensajesAclientes = servidor.getMensajes();


        while(scanner.hasNextLine()){
            String mensajeEnviarClientes = scanner.nextLine();
            mensajesAclientes.añadeMensaje(mensajeEnviarClientes);

        }


    }

    @Override
    public void update(Observable observable, Object mensajeOBS) {
        mensajesAclientes.añadeMensaje(mensajeOBS.toString());
        System.out.println("FUNCIONA");
        System.out.println(mensajeOBS.toString());
    }



}
