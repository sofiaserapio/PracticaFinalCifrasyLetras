/*
clase que se encarga de controlar todo lo relacionado a las cifras: el objetivo, 
los numeros posibles, las operaciones, si es posible realizarlas y su puntuación 
 */
package practicafinalcifrasyletras;

import java.util.Random;

public class Cifras {

    static LT lt = new LT();
    static leerFicheros leer = new leerFicheros();

    // escoge de forma aleatororia 6 numeros de la bolsa de cifras 
    public static int[] NumerosJuego() {
        int[] NuemrosFichero = leer.leerCifras();
        int[] NuemrosJuego = new int[6];
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int indice = random.nextInt(NuemrosFichero.length);
            NuemrosJuego[i] = NuemrosFichero[indice];

        }
        return NuemrosJuego;
    }
// comprueba que el numero introducido este en los numeros proporcionados

    public static boolean Existe(int numero, int[] num) {
        boolean esPosible = false;
        for (int i = 0; i < num.length; i++) {
            if (numero == num[i]) {
                esPosible = true;
            }
        }

        return esPosible;
    }
// pedir los numeros por pantalla 

    public static int pedirNumeroValido(int[] numerosJuegos) {

        int numero = 0;
        boolean valido = false;

        while (!valido) {

            System.out.println("Introduce un numero: ");

            // Si no es '=', debe ser un número
            try {
                numero = lt.llegirEnter();

                if (Existe(numero, numerosJuegos)) {
                    valido = true;
                } else {
                    System.out.println("No existe, vuelve a intentarlo.");
                }

            } catch (Exception e) {
                System.out.println("Entrada no valida. Debes introducir un numero.");
            }
        }

        return numero;
    }
// operaciones 

    public static int Suma(int num1, int num2) {
        int suma = num1 + num2;
        return suma;
    }
// controlamos que el resultado sea positivo 

    public static int resta(int num1, int num2) {

        if (num1 < num2) {
            return -1;
        }
        int resta = num1 - num2;

        return resta;
    }

    public static int multiplicacion(int num1, int num2) {
        int mult = num1 * num2;

        return mult;
    }
// controlamos que no se divida entre 0 y que la division sea exacta 

    public static int division(int num1, int num2) {

        if (num2 == 0) {
            return -1;
        }

        if (num1 % num2 != 0) {
            return -1;
        }
        int division = num1 / num2;

        return division;
    }
// sustituimos el resultado y elimimamos los numeros utilizados 

    public static int[] sustituir(int resultado, int num1, int num2, int[] num) {
        int[] nuevo = new int[num.length - 1];
        int indiceNuevo = 0;
        boolean eliminadoA = false;
        boolean eliminadoB = false;

        for (int i = 0; i < num.length; i++) {

            if (num[i] == num1 && !eliminadoA) {
                eliminadoA = true;
            } else if (num[i] == num2 && !eliminadoB) {
                eliminadoB = true;
            } else {
                nuevo[indiceNuevo] = num[i];
                indiceNuevo++;
            }
        }

        // Añadimos el resultado
        nuevo[indiceNuevo] = resultado;

        return nuevo;
    }
// puntuación 

    public static int puntuacion(int resultado, int Numaleatorio) {
        int puntuacion = 0;
        int r = Numaleatorio - resultado;

        int diferencia = valorAbsoluto(r);

        if (resultado == Numaleatorio) {
            puntuacion = 10;
        } else if (diferencia >= 1 && diferencia <= 5) {
            puntuacion = 7;
        } else if (diferencia >= 6 && diferencia <= 10) {
            puntuacion = 5;
        } else {
            puntuacion = 0;
        }

        return puntuacion;
    }
// puntuacion final 

    public static int puntuacionNumeros(int puntos, int puntuacionfinal) {

        puntuacionfinal = puntuacionfinal + puntos;

        return puntuacionfinal;
    }
// metodo para cañcular el valor absoluto 

    public static int valorAbsoluto(int x) {
        if (x < 0) {
            return -x;
        } else {
            return x;
        }

    }
// elegimos el objetivo( un numero entero entre 100 y 999)

    public static int Numeroaleatorio() {
        Random random = new Random();
        int num = random.nextInt(100, 999);

        return num;
    }

}
