package mx.unam.ciencias.icc.chat;
import java.util.*;
import java.io.*;
import java.net.*;

public class RecibeInputCliente {

    DataOutputStream output;

    RecibeInputCliente(DataOutputStream output){
        this.output = output;
    }


    public void run(){
        System.out.println("Iniciando RecibeInputCliente");

        Scanner scanner = new Scanner(System.in);
        while(true){
             //System.out.println("Primer parte");
            try {
                String mensajeEnviar = scanner.nextLine();
                output.writeUTF(mensajeEnviar);

            } catch (Exception e){
                //System.out.println("Nada enviado");

            }

        }
    }

}
