/*  clase que controla los juegos de la cpu, la cpu esta limitada a 1000 intentos a la hora 
de buscar la mejor forma de llegar a la solucion, cuando se trata del metodo numeros.
en cambio en el de letras, se escoge de forma aleatoria una de las palabras que puede formar 

 */
package practicafinalcifrasyletras;

import java.util.Random;

public class CPU {

    static LT lt = new LT();
    static Cifras cifras = new Cifras();
    static Letras letras = new Letras();
    static leerFicheros leer = new leerFicheros();
    private String nombre;
    private int puntuacion;
    private int puntosLetras;
    private int puntosNumeros;
    private int max = 1000;
    private static int intentosHechos;
    private static int mejorValor;
    private static int mejorDiferencia;
    private static String mejorPasos;

    public CPU(String nombre) {
        this.nombre = nombre;
        this.puntuacion = 0;
        this.puntosLetras = 0;
        this.puntosNumeros = 0;
    }

    public String getNombreCPU() {
        return nombre;
    }

    public int getPuntuacionCPU() {
        return puntuacion;
    }

    public int getPuntuacionLetrasCPU() {
        return puntosLetras;
    }

    public int getPuntuacionNumerosCPU() {
        return puntosNumeros;
    }

    public void CalcularapuntuacionCPU() {
        int puntos = getPuntuacionNumerosCPU() + getPuntuacionLetrasCPU();
        this.puntuacion = puntos;
    }

    public static String nombre() {
        String nombre = "CPU";
        System.out.println("introduce tu nombre: " + nombre);
        return nombre;
    }
    // comprueba todas las palabras del diccionario para ver si se pueden 
    // formar con las letras proporcionadas 

    private static boolean sePuedeFormar(String palabra, char[] letrasPosibles) {

        char[] letras = new char[letrasPosibles.length];
        for (int i = 0; i < letrasPosibles.length; i++) {
            letras[i] = letrasPosibles[i];
        }

        char[] p = palabra.toCharArray();

        for (int i = 0; i < p.length; i++) {
            boolean encontrada = false;

            for (int j = 0; j < letras.length; j++) {
                if (p[i] == letras[j]) {
                    encontrada = true;
                    letras[j] = '*';
                    break;
                }
            }

            if (!encontrada) {
                return false;
            }
        }

        return true;
    }
// selecciona una palabra del diccionario, verificando si es posible escribirla 
// con las letras proporcionadas, si si se puede formar, la mete en un array
// si no, simplemente pasa a la siguiente, y asi hasta el final 

    private static String[] palabrasposibles(char[] letrasPosibles) {

        String[] diccionario = leer.diccionario();
        String Palabra = "";
        int indice = 0;
        // Array auxiliar: como máximo todas las palabras
        String[] aux = new String[diccionario.length];

        for (int i = 0; i < diccionario.length; i++) {
            Palabra = Palabra + diccionario[i];

            if (sePuedeFormar(Palabra, letrasPosibles)) {
                aux[indice] = Palabra;
                indice++;
            }
            Palabra = "";
        }

        // Crear array final del tamaño justo
        String[] posibles = new String[indice];
        for (int i = 0; i < indice; i++) {
            posibles[i] = aux[i];
        }

        return posibles;
    }
//de el array de palabras posibles, elegimos una al azar 

    private String palabraEscogida(String[] Palabras) {
        Random random = new Random();
        int posicion = random.nextInt(0, Palabras.length);
        String palabra = Palabras[posicion];

        return palabra;
    }
// el juego de letras 

    public void Letras() {
        char[] letrasJuego = letras.LetrasJuego();

        // Mostrar letras (porque imprimir char[] directamente no queda bien)
        System.out.print("Letras posibles: ");
        for (int i = 0; i < letrasJuego.length; i++) {
            System.out.print(letrasJuego[i] + " ");
        }

        String[] palabras = palabrasposibles(letrasJuego);
        String pal = palabraEscogida(palabras);

        char[] palabraFinal = pal.toCharArray();

        puntosLetras = letras.puntuacionLetras(palabraFinal, puntosLetras);

        System.out.println();
        System.out.println(pal);
        System.out.println();
        System.out.println("puntuacion CPU:  " + palabraFinal.length);

    }
// imprime la solucion escogida, los pasos y la puntuación 

    private void resolverConBacktracking(int[] numerosIniciales, int objetivo) {

        intentosHechos = 0;

        mejorValor = numerosIniciales[0];
        mejorDiferencia = cifras.valorAbsoluto(mejorValor - objetivo);
        mejorPasos = "";

        for (int i = 0; i < numerosIniciales.length; i++) {
            actualizarMejor(numerosIniciales[i], objetivo, "");
        }

        backtracking(numerosIniciales, objetivo, "");

        System.out.println("Objetivo: " + objetivo);
        System.out.println("Mejor resultado CPU: " + mejorValor
                + " (diferencia " + mejorDiferencia + ")");
        System.out.println("Operaciones:\n" + mejorPasos);

        int puntos = cifras.puntuacion(mejorValor, objetivo);
        System.out.println("Puntuación CPU: " + puntos);

        puntosNumeros = cifras.puntuacionNumeros(puntos, puntosNumeros);
    }
//  escoge dos numeros del array de numeros proporcionado y hace todas las operaciones 

    private void backtracking(int[] numsActuales, int objetivo, String pasosAcumulados) {

        if (mejorDiferencia == 0) {
            return;
        }
        if (numsActuales.length < 2) {
            return;
        }

        // Actualizar mejor con los valores actuales
        for (int i = 0; i < numsActuales.length; i++) {
            actualizarMejor(numsActuales[i], objetivo, pasosAcumulados);
            if (mejorDiferencia == 0) {
                return;
            }
        }

        for (int i = 0; i < numsActuales.length; i++) {
            for (int j = i + 1; j < numsActuales.length; j++) {

                int a = numsActuales[i];
                int b = numsActuales[j];

                probarSuma(numsActuales, objetivo, pasosAcumulados, a, b);
                probarMultiplicacion(numsActuales, objetivo, pasosAcumulados, a, b);
                probarResta(numsActuales, objetivo, pasosAcumulados, a, b);
                probarDivision(numsActuales, objetivo, pasosAcumulados, a, b);

                if (mejorDiferencia == 0) {
                    return;
                }
            }
        }
    }
// operaciones posibles 

    private void probarSuma(int[] nums, int objetivo, String pasos, int a, int b) {
        int res = cifras.Suma(a, b);
        probarOperacion(nums, objetivo, a, b, res,
                pasos + a + " + " + b + " = " + res + "\n");
    }

    private void probarMultiplicacion(int[] nums, int objetivo, String pasos, int a, int b) {
        int res = cifras.multiplicacion(a, b);
        probarOperacion(nums, objetivo, a, b, res,
                pasos + a + " * " + b + " = " + res + "\n");
    }

    private void probarResta(int[] nums, int objetivo, String pasos, int a, int b) {
        int res = cifras.resta(a, b);
        if (res != -1) {
            probarOperacion(nums, objetivo, a, b, res,
                    pasos + a + " - " + b + " = " + res + "\n");
        } else {
            res = cifras.resta(b, a);
            if (res != -1) {
                probarOperacion(nums, objetivo, b, a, res,
                        pasos + b + " - " + a + " = " + res + "\n");
            }
        }
    }

    private void probarDivision(int[] nums, int objetivo, String pasos, int a, int b) {
        int res = cifras.division(a, b);
        if (res != -1) {
            probarOperacion(nums, objetivo, a, b, res,
                    pasos + a + " / " + b + " = " + res + "\n");
        }

        res = cifras.division(b, a);
        if (res != -1) {
            probarOperacion(nums, objetivo, b, a, res,
                    pasos + b + " / " + a + " = " + res + "\n");
        }
    }
// compara todos los resultados y se queda con el mejor 

    private void probarOperacion(int[] numsActuales, int objetivo,
            int num1, int num2, int resultado,
            String pasosNuevos) {

        if (intentosHechos >= max) {
            return;
        }
        intentosHechos++;

        actualizarMejor(resultado, objetivo, pasosNuevos);
        if (mejorDiferencia == 0) {
            return;
        }

        int[] nuevosNumeros = cifras.sustituir(resultado, num1, num2, numsActuales);

        backtracking(nuevosNumeros, objetivo, pasosNuevos);
    }
// mira quie resultado se aleja menos del objetivo 

    private static void actualizarMejor(int valor, int objetivo, String pasos) {

        int diferencia = cifras.valorAbsoluto(valor - objetivo);  // <- IMPORTANTE: usar "valor", no "mejorValor"

        if (diferencia < mejorDiferencia) {
            mejorDiferencia = diferencia;
            mejorValor = valor;
            mejorPasos = pasos;
        }
    }
// juego de numeros 

    public void Numeros() {

        int numInicial = cifras.Numeroaleatorio();
        int[] numerosJugegos = cifras.NumerosJuego();

        System.out.println("objetivo:  " + numInicial);

        System.out.print("Numeros posibles: ");
        for (int i = 0; i < numerosJugegos.length; i++) {
            System.out.print(numerosJugegos[i] + " ");
        }
        System.out.println();

        resolverConBacktracking(numerosJugegos, numInicial);

    }

}
