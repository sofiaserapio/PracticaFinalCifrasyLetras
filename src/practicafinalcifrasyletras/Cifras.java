package practicafinalcifrasyletras;

import java.util.Random;

public class Cifras {

    static LT lt = new LT();
    static leerFicheros leer = new leerFicheros();

    public static void menuCifras() {

        System.out.println("elije una opcion:");

    }

    public static int[] LetrasJuego() throws Exception {
        int[] NuemrosFichero = leer.leerCifras();
        int[] NuemrosJuego = new int[6];
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int indice = random.nextInt(NuemrosFichero.length);
            NuemrosJuego[i] = NuemrosFichero[indice];

        }
        return NuemrosJuego;
    }

    public static boolean Existe(int numero, int[] num) throws Exception {
        boolean esPosible = false;
        for (int i = 0; i < num.length; i++) {
            if (numero == num[i]) {
                esPosible = true;
            }
        }

        return esPosible;
    }

    public static int Suma(int num1, int num2) {
        int suma = num1 + num2;
        return suma;
    }

    public static int resta(int num1, int num2) {
        int resta = 0;
        if (num1 < num2) {
            System.out.println("no es posible");
        } else {
            resta = num1 - num2;
        }

        return resta;
    }

    public static int multiplicacion(int num1, int num2) {
        int mult = num1 * num2;

        return mult;
    }

    public static int division(int num1, int num2) {
        int division = 0;
        if (num2 == 0 && num1 % num2 != 0) {
            System.out.println("no es posible realizar la division");
        } else {

            division = num1 / num2;
        }

        return division;
    }

    public static int[] sustituir(int resultado, int num1, int num2, int[] num) { 
        int[] nuevo= new int[num.length-1];
        
        while(nuevo.length!=1){
            for (int i = 0; i < 10; i++) {
                
            }
        
        
        }
        
        
        
        
        return nuevo;
    }

}
