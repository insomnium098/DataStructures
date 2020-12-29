package mx.unam.ciencias.edd.proyecto2;

/**
 * Interfaz para realizar acciones sobre vértices de árboles binarios.
 */
@FunctionalInterface
public interface AccionVerticeArbolBinario<T> {

    /**
     * Realiza una acción sobre un vértice de árbol binario.
     * @param vertice el vértice sobre el que se realizará la acción.
     */
    public void actua(VerticeArbolBinario<T> vertice);
}

