package mx.unam.ciencias.icc.chat;
import java.util.*;
import java.io.*;
import java.net.*;

public class RecibeInputCliente {

    DataOutputStream output;
    String nickname;

    RecibeInputCliente(DataOutputStream output, String nickname){
        this.output = output;
        this.nickname = nickname;
    }


    public void run(){
        //System.out.println("Iniciando RecibeInputCliente");

        Scanner scanner = new Scanner(System.in);
        while(true){
             //System.out.println("Primer parte");
            try {
                String mensajeEnviar = scanner.nextLine();
                mensajeEnviar = nickname + ": " + mensajeEnviar;
                output.writeUTF(mensajeEnviar);

            } catch (Exception e){
                //System.out.println("Nada enviado");

            }

        }
    }

}
