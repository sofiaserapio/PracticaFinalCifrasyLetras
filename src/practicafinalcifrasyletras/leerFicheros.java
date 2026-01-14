/*
 clase encargada de leer ficheros 
 */

package practicafinalcifrasyletras;

import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class leerFicheros {

    // lee la lista de letras proporcionada y la mete en un array para facilitar el uso
    public static char[] ListadeLetras() {

    

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
  public static boolean existePalabra(char[] palabra)  {

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
    public static int contarNumeros() {

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
    public static int[] leerCifras() {

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


    public static void leerEstadisticas() {

    FileReader estadisticas = null;
    String estadistica = "";

    try {
        estadisticas = new FileReader("partidas.txt");
        int valor;

        while ((valor = estadisticas.read()) != -1) {
            estadistica = estadistica + (char) valor;
        }

        // aquí ya tienes TODO el fichero en el String
        System.out.println(estadistica);

    } catch (IOException e) {
        System.out.println("Error al leer el fichero partidas.txt");
        e.printStackTrace();

    } finally {
        try {
            if (estadisticas != null) {
                estadisticas.close();
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar el fichero");
        }
    }
}


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
