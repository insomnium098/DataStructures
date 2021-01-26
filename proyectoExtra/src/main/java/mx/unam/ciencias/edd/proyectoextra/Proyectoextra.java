package mx.unam.ciencias.edd.proyectoextra;

import java.io.IOException;
import mx.unam.ciencias.edd.*;
import java.io.File;

/**
 * Proyecto extra.
 */
public class Proyectoextra {
    
    private static void uso() {
        System.err.println("Debes de ingresar al menos un archivo");
        System.exit(1);
    }



    public static void main(String[] args) throws IOException {

        if (args.length == 0)
            uso();

        System.out.println("ProyectoExtra");
    }



}
