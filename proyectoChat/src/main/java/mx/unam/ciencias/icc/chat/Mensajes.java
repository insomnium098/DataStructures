package mx.unam.ciencias.icc.chat;
import java.util.*;

public class Mensajes extends Observable{
    private List<Observer> subscriptores = new ArrayList<>();

    public void añadeMensaje (String mensaje){
        notifyObservers(mensaje);
    }

    public void notifyObservers(String mensaje){
        for (Observer subscriber : subscriptores){
            subscriber.update(this,mensaje);
        }
    }

    public void registerObserver (Observer newSubscriber){
        this.subscriptores.add(newSubscriber);
    }

    public void removeObserver (Observer previousSubscriber){
        this.subscriptores.add(previousSubscriber);
    }


}
