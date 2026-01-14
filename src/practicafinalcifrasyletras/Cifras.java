package practicafinalcifrasyletras;

import java.util.Random;

public class Cifras {

    static LT lt = new LT();
    static leerFicheros leer = new leerFicheros();

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

    public static boolean Existe(int numero, int[] num) {
        boolean esPosible = false;
        for (int i = 0; i < num.length; i++) {
            if (numero == num[i]) {
                esPosible = true;
            }
        }

        return esPosible;
    }

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

    public static int Suma(int num1, int num2) {
        int suma = num1 + num2;
        return suma;
    }

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

    public static int puntuacion(int resultado, int Numaleatorio) {
        int puntuacion = 0;
        int r = Numaleatorio - resultado;

        int diferencia = valorAbsoluto(r);

        if (resultado==Numaleatorio) {
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

    public static int puntuacionNumeros(int puntos, int puntuacionfinal) {

        puntuacionfinal = puntuacionfinal + puntos;

        return puntuacionfinal;
    }

    public static int valorAbsoluto(int x) {
        if (x < 0) {
            return -x;
        } else {
            return x;
        }

    }

    public static int Numeroaleatorio(){
        Random random = new Random();
        int num = random.nextInt(100, 999);

        return num;
    }

}
