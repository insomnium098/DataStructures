package mx.unam.ciencias.icc.chat;
import java.util.*;

public class MensajeCliente extends Observable {

    private List<Observer> subscriptores = new ArrayList<>();

    public void añadeMensaje (String mensaje){
        notificaObservadores(mensaje);
    }

    public void notificaObservadores(String mensaje){
        for (Observer subscriptor : subscriptores){
            subscriptor.update(this,mensaje);
        }
    }

    public void registrarObservador (Observer nuevoSubscriptor){
        this.subscriptores.add(nuevoSubscriptor);
    }

    public void removerObservador (Observer viejoSubscriptor){
        this.subscriptores.add(viejoSubscriptor);
    }


}
