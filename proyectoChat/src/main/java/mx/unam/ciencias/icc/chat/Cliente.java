package mx.unam.ciencias.icc.chat;

import java.util.*;
import java.io.*;
import java.net.*;



/**
 * Cliente para Examen de ICC
 */

public class Cliente {
    public static void main(String[] args) throws IOException {
        try {


            System.out.println("Bienvenido al cliente del char \n" +
                    "Cual será tu nickname?");
            Scanner scanner = new Scanner(System.in);
            String nickname = scanner.nextLine();
            System.out.println("Tu nickname será: " + nickname);

            // obtenemos la ip del localhost
            InetAddress ip = InetAddress.getByName("localhost");

            // establecemos la conexion con el puerto 6666
            Socket socket = new Socket(ip, 6667);

            // Creamos el stream para el input y output
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());


            ///Si hay conexion se envia el nickname al servidor
            ///Primero enviamos el nickname al servidor
            output.writeUTF(nickname);
            ////Creamos el imprimeServidorCliente

            //imprimeDeServidor(input);
            imprimeServidorCliente imprimeServidor = new imprimeServidorCliente(input);
            //lo inicializamos
            imprimeServidor.start();








            // Creamos un loop para intercambiar mensajes con el servidor
            while (true)
            {



                RecibeInputCliente RC = new RecibeInputCliente(output,nickname);
                RC.run();
                //output.writeUTF("JALANDO?");


                /*
                System.out.println(input.readUTF());
                String mensajeEnviar = scanner.nextLine();
                output.writeUTF(mensajeEnviar);

                 */





                // Imprimir el mensaje recibido por el servidor

                /*
                String mensajeRecibido = input.readUTF();
                System.out.println(mensajeRecibido);

                 */

                //imprimeDeServidor(input);




            }

        }catch(Exception e){
            System.out.println("No se pudo conectar el servidor, saliendo");
            System.exit(1);
        }
    }

    /*
    Metodo para recibir los mensajes del servidor
    DEPRECATED
     */

    public static void imprimeDeServidor(DataInputStream input){
        // Imprimir el mensaje recibido por el servidor
        try {
            String mensajeRecibido = input.readUTF();
            System.out.println(mensajeRecibido);
        } catch (Exception e){

        }

    }
}

