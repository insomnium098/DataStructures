package mx.unam.ciencias.icc.chat;

import java.util.*;
import java.io.*;
import java.net.*;



/**
 * Cliente para Examen de ICC
 */

public class Cliente
{
    public static void main(String[] args) throws IOException
    {
        try
        {
            Scanner scanner = new Scanner(System.in);

            // obtenemos la ip del localhost
            InetAddress ip = InetAddress.getByName("localhost");

            // establecemos la conexion con el puerto 6666
            Socket socket = new Socket(ip, 6666);

            // Creamos el stream para el input y output
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            // Creamos un loop para intercambiar mensajes con el servidor
            while (true)
            {
                System.out.println(input.readUTF());
                String mensajeEnviar = scanner.nextLine();
                output.writeUTF(mensajeEnviar);

                // Si el cliente manda salir se corta el loop y se termina el cliente
                if(mensajeEnviar.equals("Exit"))
                {
                    System.out.println("Cerrando la conexion con el chat : " + socket);
                    socket.close();
                    System.out.println("La conexion se cerro, terminando chat");
                    break;
                }

                // Imprimir el mensaje recibido por el servidor
                String mensajeRecibido = input.readUTF();
                System.out.println(mensajeRecibido);
            }

            // Cerrando las conexiones para poder seguir recibiendo y enviando nuevos mensajes
            scanner.close();
            input.close();
            output.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
