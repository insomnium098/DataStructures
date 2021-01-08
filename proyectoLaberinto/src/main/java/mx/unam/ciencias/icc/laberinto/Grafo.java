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

    
    ////El recorrido de la grafica se hará por BFS y se calculará la trayectoria minima

    public LinkedList<Integer> bfsTrayectoria (Integer nodoOrigen, Integer nodoDestino){
        ///Hacemos una array boolean para agregar a los nodos que ya han sido visitados
        //LinkedList<Integer> visitados = new LinkedList<>();
        boolean nodosVisitados[] = new boolean[Nodos];
        ///Array para guardar las distancias y los nodos anteriores del camino
        Double distancias [] = new Double[Nodos];
        Integer nodosAnteriores [] = new Integer[Nodos];


        ////Se hacen todos los nodos no visitados, los predecesores nulos (-1) y las distancias como infinito
        for (int i = 0; i < Nodos; i++){
            nodosVisitados[i] = false;
            distancias[i] = Double.POSITIVE_INFINITY;
            nodosAnteriores[i] = -1;
        }





        ///Hacemos una cola para recorrer por BFS
        Queue<Integer> cola = new PriorityQueue<>();

        ///Añadimos el nodo origen a la cola y añadimos la distancia a si mismo como 0
        cola.add(nodoOrigen);
        nodosVisitados[nodoOrigen] = true;
        distancias[nodoOrigen] = 0.0;

        int nodoAux;

        while(!cola.isEmpty()){
            nodoAux = cola.poll();
            //Obtenemos los vecinos del nodo y los procesamos
            for (int nodo : listaEdges[nodoAux]){
                ///Revisamos si ya fue visitado
                if(!nodosVisitados[nodo]){
                    nodosVisitados[nodo] = true;
                    ///Actualizamos las distancias
                    distancias[nodo] = distancias[nodoAux] + 1;
                    nodosAnteriores[nodo] = nodoAux;
                    ///Lo añadimos a la cola
                    cola.add(nodo);

                }
            }

        }


        LinkedList<Integer> trayectoria = new LinkedList<>();

        ///Si la distancia del destino es infinito, son inconexas, devolver lista vacia
        if(distancias[nodoDestino] == Double.POSITIVE_INFINITY){
            return trayectoria;
        }



        /// En caso contrario, Reconstruimos la distancia minima
        nodoAux = nodoDestino;

        ///Iniciando desde el nodo destino
        trayectoria.add(nodoAux);

        while (nodosAnteriores[nodoAux] != -1){
            trayectoria.add(nodosAnteriores[nodoAux]);
            nodoAux = nodosAnteriores[nodoAux];
        }



        return trayectoria;

    }


}
