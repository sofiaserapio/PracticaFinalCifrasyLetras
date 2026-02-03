/*
 clase encargada de leer ficheros 
 */
package practicafinalcifrasyletras;

import java.io.FileReader;
import java.io.IOException;

public class leerFicheros {

    // lee la lista de letras proporcionada y la mete en un array para facilitar el uso
    public char[] ListadeLetras() {

        FileReader ficheroLetras = null;
        String letras = "";

        try {
            ficheroLetras = new FileReader("letras_es.txt");
            int valor;

            // leemos el fichero y rellenamos el string
            while ((valor = ficheroLetras.read()) != -1) {
                letras = letras + (char) valor;
            }

        } catch (IOException e) {
            System.out.println("Error al leer el fichero de letras");
            e.printStackTrace();
        } finally {
            try {
                if (ficheroLetras != null) {
                    ficheroLetras.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el fichero");
            }
        }

        // convertimos el string a array para despues poder hacer el random
        char[] bolsaDeLetras = letras.toCharArray();
        return bolsaDeLetras;
    }

    // comprueba que la palabra proporcionada por el usuario exista en el diccionario indicado
    // por el profesor
    public boolean existePalabra(char[] palabra) {

        FileReader diccionario = null;

        try {
            diccionario = new FileReader("dic_es.txt");

            int indice = 0;
            boolean coincide = true;

            int valor;

            while ((valor = diccionario.read()) != -1) {

                // ignoramos \r por si el fichero tiene saltos Windows (\r\n)
                if (valor == '\r') {
                    continue;
                }

                // fin de palabra en el diccionario
                if (valor == '\n') {

                    if (coincide && indice == palabra.length) {
                        return true;
                    }

                    // reset para la siguiente palabra
                    indice = 0;
                    coincide = true;

                } else {
                    // comparamos letra a letra
                    if (indice >= palabra.length || valor != palabra[indice]) {
                        coincide = false;
                    }
                    indice++;
                }
            }

            // por si la última palabra no termina con salto de línea
            return (coincide && indice == palabra.length);

        } catch (IOException e) {
            System.out.println("Error leyendo dic_es.txt");
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (diccionario != null) {
                    diccionario.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el diccionario");
            }
        }
    }


    /* cuenta los numeros que hay en la bolsa numerica proporcionada, teniendo en cuenta que cada 
    numero esta separado por espacios */
    public int contarNumeros() {

        FileReader ficheroCifras = null;
        int contador = 0;
        boolean dentroNumero = false;

        try {
            ficheroCifras = new FileReader("cifras.txt");
            int valor;

            while ((valor = ficheroCifras.read()) != -1) {

                // si es un dígito
                if (valor >= '0' && valor <= '9') {
                    dentroNumero = true;

                    // si es separador y estábamos dentro de un número
                } else if ((valor == ' ' || valor == '\n' || valor == '\r') && dentroNumero) {
                    contador++;
                    dentroNumero = false;
                }
            }

            // último número si no termina en separador
            if (dentroNumero) {
                contador++;
            }

        } catch (IOException e) {
            System.out.println("Error al leer el fichero cifras.txt");
            e.printStackTrace();

        } finally {
            try {
                if (ficheroCifras != null) {
                    ficheroCifras.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el fichero");
            }
        }

        return contador;
    }


    /*  usando el contador del metodo anterior, creamos una array en donde cada posicion
    sera uno de los numeros de la lista que disponemos  */
    public int[] leerCifras() {

        int total = contarNumeros();
        int[] bolsaDeCifras = new int[total];
        int numeroActual = 0;
        int indice = 0;

        FileReader ficheroCifras = null;

        try {
            ficheroCifras = new FileReader("cifras.txt");
            int valor;

            while ((valor = ficheroCifras.read()) != -1) {

                /*
             * Para crear el número:
             * multiplicamos por 10 y sumamos el valor del dígito
                 */
                if (valor >= '0' && valor <= '9') {
                    numeroActual = numeroActual * 10 + (valor - '0');

                    // separador de números
                } else if ((valor == ' ' || valor == '\n' || valor == '\r') && indice < total) {
                    bolsaDeCifras[indice] = numeroActual;
                    indice++;
                    numeroActual = 0;
                }
            }

            // último número si no acaba en separador
            if (indice < total) {
                bolsaDeCifras[indice] = numeroActual;
            }

        } catch (IOException e) {
            System.out.println("Error al leer el fichero cifras.txt");
            e.printStackTrace();

        } finally {
            try {
                if (ficheroCifras != null) {
                    ficheroCifras.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el fichero");
            }
        }

        return bolsaDeCifras;
    }

// lee el fichero de estadisticas y lo muestra por pantalla 
    public void leerEstadisticas() {

        FileReader fr = null;
        String modoImprimir;

        try {
            fr = new FileReader("partidas.txt");
// inicializa un string con todos los campos posibles de las estadisiticas 
            String[] campos = new String[7];
            int nCampos = 0;

            String campoActual = "";
            int numPartida = 0;

            int v;
            // cada vez que encuentra un # es una separacion de campos
            // comprueba que todos los campos esten rellenados 

            while ((v = fr.read()) != -1) {

                char ch = (char) v;

                if (ch == '#') {
                    if (nCampos < 7) {
                        campos[nCampos] = campoActual;
                        nCampos++;
                    }
                    campoActual = "";
                } else if (ch == '\n') {

                    if (nCampos < 7) {
                        campos[nCampos] = campoActual;
                        nCampos++;
                    }
                    campoActual = "";

                    if (nCampos == 7) {
                        String fecha = campos[0];
                        String modo = campos[1];
                        String nombreJ1 = campos[2];
                        String nombreJ2 = campos[3];
                        // mira el numero de rondas
                        int rondas = 0;
                        for (int i = 0; i < campos[4].length(); i++) {
                            char c = campos[4].charAt(i);
                            if (c >= '0' && c <= '9') {
                                rondas = rondas * 10 + (c - '0');
                            }
                        }
                        // mira la puntuacion del jugador 1
                        int p1 = 0;
                        for (int i = 0; i < campos[5].length(); i++) {
                            char c = campos[5].charAt(i);
                            if (c >= '0' && c <= '9') {
                                p1 = p1 * 10 + (c - '0');
                            }
                        }
                        // mira la puntuacion del jugador 2
                        int p2 = 0;
                        for (int i = 0; i < campos[6].length(); i++) {
                            char c = campos[6].charAt(i);
                            if (c >= '0' && c <= '9') {
                                p2 = p2 * 10 + (c - '0');
                            }
                        }

                        // mira que modo de juego se ha usado
                        boolean esCpu = false;

                        char[] modoChars = modo.toCharArray();
                        char[] refChars = {'V', 'S', ' ', 'C', 'P', 'U'};

                        if (modoChars.length == refChars.length) {
                            esCpu = true;
                            for (int i = 0; i < refChars.length; i++) {
                                if (modoChars[i] != refChars[i]) {
                                    esCpu = false;
                                }
                            }
                        }
                        if (esCpu) {
                            modoImprimir = "vs CPU";
                        } else {
                            modoImprimir = "vs humano";
                        }

                        // compara las puntuaciones y mira quien ha sido el ganador
                        String ganadorNombre;
                        if (p1 > p2) {
                            ganadorNombre = nombreJ1;
                        } else if (p2 > p1) {
                            ganadorNombre = nombreJ2;
                        } else {
                            ganadorNombre = "Empate";
                        }

                        numPartida++;
                        // imprime las estadisticas 
                        System.out.println("************************************************************");
                        System.out.println();
                        System.out.println("Partida " + numPartida + " (" + fecha + "). Modo \"" + modoImprimir + "\", " + rondas + " rondas,");
                        System.out.println("ganador: \"" + ganadorNombre + "\".");
                        System.out.println(" - Jugador 1 \"" + nombreJ1 + "\": " + p1 + " puntos.");
                        System.out.println(" - Jugador 2 \"" + nombreJ2 + "\": " + p2 + " puntos.");
                        System.out.println();
                    }
                    // compruea la ultima linea dle fichero que no esta seguida de un salto de linea
                    nCampos = 0;
                    for (int i = 0; i < 7; i++) {
                        campos[i] = null;
                    }

                } else if (ch != '\r') {
                    campoActual = campoActual + ch;
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el fichero partidas.txt");
            e.printStackTrace();

        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el fichero");
            }
        }
    }

// cuenta cuantas palabras hay en el fichero diccionario, para inicializar posteriormente un array 
    private static int contarPalabrasDiccionario() {

        FileReader diccionario = null;
        int palabras = 0;
        boolean hayCaracteres = false;

        try {
            diccionario = new FileReader("dic_es.txt");
            int valor;

            while ((valor = diccionario.read()) != -1) {
                hayCaracteres = true;

                // cada salto de línea indica una palabra
                if (valor == '\n') {
                    palabras++;
                }
            }

            // última palabra si el fichero no acaba en salto de línea
            if (hayCaracteres) {
                palabras++;
            }

        } catch (IOException e) {
            System.out.println("Error al leer dic_es.txt");
            e.printStackTrace();

        } finally {
            try {
                if (diccionario != null) {
                    diccionario.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el diccionario");
            }
        }

        return palabras;
    }

// copia todo el dccionario en una array 
    public static String[] diccionario() {

        int tamaño = contarPalabrasDiccionario();
        String[] dic = new String[tamaño];
        String palabra = "";
        int indice = 0;

        FileReader diccionario = null;

        try {
            diccionario = new FileReader("dic_es.txt");
            int valor;

            while ((valor = diccionario.read()) != -1) {

                // ignoramos \r (Windows)
                if (valor == '\r') {
                    continue;
                }

                if (valor == '\n') {
                    if (indice < tamaño) {
                        dic[indice] = palabra;
                        indice++;
                    }
                    palabra = "";
                } else {
                    palabra = palabra + (char) valor;
                }
            }

            // última palabra si no termina en salto de línea
            if (!palabra.equals("") && indice < tamaño) {
                dic[indice] = palabra;
            }

        } catch (IOException e) {
            System.out.println("Error al leer dic_es.txt");
            e.printStackTrace();

        } finally {
            try {
                if (diccionario != null) {
                    diccionario.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el diccionario");
            }
        }

        return dic;
    }

}
