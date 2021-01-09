package mx.unam.ciencias.icc.chat;
import java.util.*;
import java.io.*;
import java.net.*;

public class ObservableMensajesClientes extends Observable {

    private List<MensajeCliente> Servidor;

    ObservableMensajesClientes(List<MensajeCliente> listaMensajes){
        this.Servidor = listaMensajes;
    }

    public void agregaCliente(MensajeCliente cliente){
        this.Servidor.add(cliente);
    }

    public void eliminaCliente(MensajeCliente cliente){
        this.Servidor.remove(cliente);
    }

    /*


    public void clienteEnvioMensaje(String mensaje){
        for (Observer cliente : Servidor){


        }
    }

     */









}
