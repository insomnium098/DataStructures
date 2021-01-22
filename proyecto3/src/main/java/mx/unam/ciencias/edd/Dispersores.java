package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        // Aquí va su código.
        //Caso donde el numero total de bytes es no es multiplo de 4
        //Se debe de completar a un multiplo de 4 agregando 0s al final del mismo
        if(llave.length % 4 != 0){
            ///Calculamos cual será la longitud del nuevo arreglo
            int lengthNueva = 4 - (llave.length % 4);
            byte [] nueva = new byte[llave.length + lengthNueva];
            ///Tenemos que copiar todos los elementos de la llave a la nueva y los restantes llenarlos de 0
            for (int i = 0; i < nueva.length; i++){
                if(i < llave.length){
                    nueva[i] = llave[i];
                } else {
                    nueva[i] = (byte)0;
                }
            }

            llave = nueva;
        }

        int r = 0;
        int n;

        //Por cada cuatro bytes del arreglo los combinaremos con n de 32 bits siguiendo el esquema de bigEndian
        for(int i = 0; i < llave.length; i+=4){
            n = combinaBigEndian(llave[i], llave[i+1], llave[i+2], llave[i+3]);
            //Actualizamos r a r XOR n
            r = r ^ n;

        }

        return r;
    }

    public static int combinaBigEndian(byte a, byte b, byte c, byte d){
        return ((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((c & 0xFF) << 8) | ((d & 0xFF));

    }

    public static int combinaLittleEndian(byte a, byte b, byte c, byte d){
        return ((a & 0xFF)) | ((b & 0xFF) << 8) | ((c & 0xFF) << 16) | ((d & 0xFF) << 24);

    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        // Aquí va su código.


        int a,b,c;
        a=b=0x9E3779B9;
        c=0xFFFFFFFF;
        int [] array;


        ///Mientras haya disponibles 12 bytes en el arreglo formamos 3 enteros de 32 bits
        /// usando littleendian y aumentamos a,b y c con dichos enteros. Despues los mezclamos y repetimos
        int i = 0;
        while (i < llave.length - (llave.length%12) && llave.length>=12){
            a = a + combinaLittleEndian(llave[i], llave[i+1], llave[i+2], llave[i+3]);
            b = b + combinaLittleEndian(llave[i+4], llave[i+5], llave[i+6], llave[i+7]);
            c = c + combinaLittleEndian(llave[i+8], llave[i+9], llave[i+10], llave[i+11]);

            array = mezclaBJ(a,b,c);
            a = array[0];
            b = array[1];
            c = array[2];
            //aumentamos la i en 12
            i = i + 12;
        }


        ///// Debemos ver cuantos enteros debemos de crear cuando sean menos de 12 bytes
        ///A cada byte se le hace AND con FF para hacer entero de 32 bits
        //Se hará en cascada para ir agregando cada byte al entero correspondiente
        ///y cubrir todos los casos

        int enteros = llave.length % 12;

        switch(enteros){
            case 11 : c = c + ((llave[i+10] & 0xFF) << 24);
            case 10 : c = c + ((llave[i+9] & 0xFF) << 16);
            case 9 : c = c + ((llave[i+8] & 0xFF) << 8);
            case 8 : b = b + ((llave[i+7] & 0xFF) << 24);
            case 7 : b = b + ((llave[i+6] & 0xFF) << 16);
            case 6 : b = b + ((llave[i+5] & 0xFF) << 8);
            case 5 : b = b + (llave[i+4] & 0xFF);
            case 4 : a = a + ((llave[i+3] & 0xFF) << 24);
            case 3 : a = a + ((llave[i+2] & 0xFF) << 16);
            case 2 : a = a + ((llave[i+1] & 0xFF) << 8);
            case 1 : a = a + (llave[i] & 0xFF);
        }


        ///Aumentamos c en el tamaño del arreglo
        c += llave.length;


        array = mezclaBJ(a,b,c);
        ///Regresamos c

        return array[2];




    }

    public static int [] mezclaBJ (int a, int b, int c){
        /*
        Este algoritmo ha sido de los mas castrosos del curso
         */
        a-= b;
        a-= c;
        a^= (c >>> 13);
        b-= c;
        b-= a;
        b^= (a << 8);
        c-= a;
        c-= b;
        c^= (b >>> 13);
        a-= b;
        a-= c;
        a^= (c >>> 12);
        b-= c;
        b-= a;
        b^= (a << 16);
        c-= a;
        c-= b;
        c^= (b >>> 5);
        a-= b;
        a-= c;
        a^= (c >>> 3);
        b-= c;
        b-= a;
        b^= (a << 10);
        c-= a;
        c-= b;
        c^= (b >>> 15);

        ///Finalmente hacemos el arreglo para devolver los ints

        return new int[] {a,b,c};






    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        // Aquí va su código.
        int h = 5381;
        for (byte b : llave) {
            h = (h *= 33) + (b & 0xFF);
        }

        return h;
    }
}
