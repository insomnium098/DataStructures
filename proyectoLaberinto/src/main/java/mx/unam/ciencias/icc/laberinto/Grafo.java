package mx.unam.ciencias.icc.laberinto;
import java.util.*;

public class Grafo {
    ////Los grafos contendran unicamente integers como nodos, para facilitar varios metodos
    ///Nodos representa el numero de nodos que contendrá el grafo
    private Integer Nodos;
    private LinkedList<Integer>[] listaEdges;

    ///Constructor
    Grafo (Integer numNodos){
        ///El grafo recibe el numero de nodos que va a contener
        Nodos = numNodos;

        ///Inicializamos la lista de edges con el numero de nodos
        listaEdges = new LinkedList[Nodos];
        ///Recorremos la lista y a cada nodo le hacemos una lista con los nodos que tiene como vecinos
        for (int i = 0; i < Nodos; i++){
            listaEdges[i] = new LinkedList<>();
        }

    }


    public void imprimeGrafo(){

        System.out.println(Nodos);


        for (LinkedList<Integer> i : listaEdges){
            System.out.println(i.toString());
        }
    }

    public void conecta (Integer a, Integer b){
        ///Se tienen que agregar en ambas listas
        listaEdges[a].add(b);
        listaEdges[b].add(a);
    }


    ////El recorrido de la grafica se hará por BFS

    public LinkedList<Integer> bfs (Integer nodoOrigen, Integer nodoDestino){
        ///Hacemos una lista para agregar a los nodos que ya han sido visitados
        LinkedList<Integer> visitados = new LinkedList<>();

        ///Hacemos una cola para recorrer por BFS
        Queue<Integer> cola = new PriorityQueue<>();

        ///Añadimos el nodo origen a la lista de visitados y a la cola
        visitados.add(nodoOrigen);
        cola.add(nodoOrigen);

        while(!cola.isEmpty()){
            ///Procesamos el nodo

            nodoOrigen = cola.poll();
            //System.out.println(nodoOrigen + " ");

            ///Obtenemos los vecinos del nodo y los procesamos
            for (int nodo : listaEdges[nodoOrigen]) {

                ///Revisamos si ya es el nodo destino
                if(nodo == nodoDestino)
                    break;

                ///Revisamos si ya ha sido visitado
                if (!visitados.contains(nodo)) {
                    visitados.add(nodo);
                    cola.add(nodo);
                }
            }

        }



        ////Revisamos si la trayectoria contiene al origen y al destino,
        ///Si no los tiene la vaciamos

        if(!visitados.contains(nodoOrigen) || !visitados.contains(nodoDestino)){
            visitados.clear();
        }

        return visitados;

        ///Imprimimos la ruta

        /*

        System.out.println("La ruta final es");
        for (Integer i  : visitados){
            System.out.println(i);
        }

         */

    }


}
