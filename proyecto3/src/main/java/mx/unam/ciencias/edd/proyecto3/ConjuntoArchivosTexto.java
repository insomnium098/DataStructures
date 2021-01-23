package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.*;

public class ConjuntoArchivosTexto {
    private Diccionario<String, ArchivoTexto> diccionario;
    private Grafica<String> grafica;

    public ConjuntoArchivosTexto(Diccionario<String, ArchivoTexto> diccionario){
        this.diccionario = diccionario;
        this.grafica = new Grafica<>();
    }

    public Grafica<String> getGrafica() {
        return grafica;
    }

    /*
    Metodo para generar el grafo
     */
    private void generaGrafo(){
        //Conjunto<String>
    }
}
