package mx.unam.ciencias.icc.chat;
import java.util.*;

public class MensajeCliente extends Observable {

    private List<Observer> subscribers = new ArrayList<>();

    public void añadeMensaje (String mensaje){
        notifyObservers(mensaje);
    }

    public void notifyObservers(String mensaje){
        for (Observer subscriber : subscribers){
            subscriber.update(this,mensaje);
        }
    }

    public void registerObserver (Observer newSubscriber){
        this.subscribers.add(newSubscriber);
    }

    public void removeObserver (Observer previousSubscriber){
        this.subscribers.add(previousSubscriber);
    }


}
