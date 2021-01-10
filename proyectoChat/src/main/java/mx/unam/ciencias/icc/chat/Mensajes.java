package mx.unam.ciencias.icc.chat;
import java.util.*;

public class Mensajes extends Observable{
    private List<Observer> subscriptores = new ArrayList<>();

    public void añadeMensaje (String mensaje){
        notificaObservadores(mensaje);
    }

    public void notificaObservadores(String mensaje){
        for (Observer subscriber : subscriptores){
            subscriber.update(this,mensaje);
        }
    }

    public void registraObservadores (Observer nuevoSubscriptor){
        this.subscriptores.add(nuevoSubscriptor);
    }

    public void removeObserver (Observer subscriptor){
        this.subscriptores.add(subscriptor);
    }


}
