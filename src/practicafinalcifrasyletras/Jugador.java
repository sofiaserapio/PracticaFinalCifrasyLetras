/*

 */
package practicafinalcifrasyletras;

public class Jugador {

    static LT lt = new LT();
    static Cifras cifras = new Cifras();
    static Letras letras = new Letras();
    static leerFicheros leer = new leerFicheros();
    private String nombre;
    private int puntuacion;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntuacion = 0;
    }

    public static String nombre() {
        String nombre = "";
        char caracter;

        System.out.println("Introduce tu nombre finalizado en punto:");

        caracter = lt.llegirCaracter();
        while (caracter != '.') {
            nombre = nombre + caracter;
            caracter = lt.llegirCaracter();
        }

        return nombre;
    }

    public void calcularPuntuacion() throws Exception {
        int puntos = JuegoCifras() + JuegoLetras();
        this.puntuacion = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public static void main(String[] args) throws Exception {
        JuegoCifras();
    }

    public static int JuegoLetras() throws Exception {

        int puntuacionLetras = 0;

        // Generas una vez las letras del turno
        char[] letrasJuego = letras.LetrasJuego();

        // Mostrar letras (porque imprimir char[] directamente no queda bien)
        System.out.print("Letras posibles: ");
        for (int i = 0; i < letrasJuego.length; i++) {
            System.out.print(letrasJuego[i] + " ");
        }
        System.out.println();

        boolean seguir = true;

        while (seguir) {

            System.out.println("Introduce una palabra terminada en '.' (o espacio para pasar):");

            String palabra = "";
            char caracter = lt.llegirCaracter();

            // Si el primero que mete es espacio, pasa/sale
            if (caracter == ' ') {
                seguir = false;
            } else {

                // Leer hasta '.' o hasta espacio (pasar)
                while (caracter != '.' && caracter != ' ') {
                    palabra = palabra + caracter;
                    caracter = lt.llegirCaracter();
                }

                // Si ha puesto espacio durante la palabra, pasa/sale
                if (caracter == ' ') {
                    seguir = false;
                } else {
                    // caracter == '.' → validar palabra
                    char[] palabraUsuario = palabra.toCharArray();

                    boolean esPosible = letras.esPosible(palabra, letrasJuego);
                    boolean existe = leer.existePalabra(palabraUsuario);

                    if (!esPosible) {
                        System.out.println("La palabra NO se puede formar con las letras proporcionadas.");
                    } else if (!existe) {
                        System.out.println("La palabra NO esta en el diccionario.");
                    } else {
                        puntuacionLetras = puntuacionLetras + palabraUsuario.length;
                        System.out.println("Correcta!!! Puntuacion: " + puntuacionLetras);
                        seguir = false;
                    }

                    // y aquí vuelve al while para que lo intente otra vez
                }
            }
        }

        return puntuacionLetras;
    }

    public static int JuegoCifras() throws Exception {
        int puntacionCifras = 0;
        char operacion;
        int num1;
        int num2;
        int resultado = 0;
        int numInicial = cifras.Numeroaleatorio();
        int[] numerosJugegos = cifras.NumerosJuego();

        System.out.println("objetivo:  " + numInicial);

        System.out.print("Numeros posibles: ");
        for (int i = 0; i < numerosJugegos.length; i++) {
            System.out.print(numerosJugegos[i] + " ");
        }
        System.out.println();

        boolean seguir = true;
        while (seguir && numerosJugegos.length > 1) {

            num1 = cifras.pedirNumeroValido(numerosJugegos);

            num2 = cifras.pedirNumeroValido(numerosJugegos);

            System.out.println("elije una operacion:   " + " + " + " - " + " * " + " / " + " = ");
            operacion = lt.llegirCaracter();

            if (operacion != '=') {
                switch (operacion) {
                    case '+':
                        resultado = cifras.Suma(num1, num2);
                        numerosJugegos = cifras.sustituir(resultado, num1, num2, numerosJugegos);
                        reescribir(numerosJugegos);
                        break;

                    case '-':
                        resultado = cifras.resta(num1, num2);
                        if (resultado == -1) {
                            System.out.println("no se puede realizar la operacion");

                        } else {
                            numerosJugegos = cifras.sustituir(resultado, num1, num2, numerosJugegos);
                            reescribir(numerosJugegos);
                        }

                        break;

                    case '*':
                        resultado = cifras.multiplicacion(num1, num2);
                        numerosJugegos = cifras.sustituir(resultado, num1, num2, numerosJugegos);
                        reescribir(numerosJugegos);

                        break;

                    case '/':
                        resultado = cifras.division(num1, num2);
                        if (resultado == -1) {
                            System.out.println("no se puede realizar la operacion");

                        } else {
                            reescribir(numerosJugegos);
                        }

                        break;

                    default:
                        System.out.println("no es una operacion valida");
                        break;
                }

            } else {

                System.out.println("introduce el resultado:  ");
                int num = lt.llegirEnter();
                boolean existe = cifras.Existe(num, numerosJugegos);

                if (existe) {
                    puntacionCifras = cifras.puntuacion(num1, numInicial);
                    System.out.println("puntuacion: " + puntacionCifras);
                    seguir = false;

                } else {
                    System.out.println("no se puede realizar la operacion");
                    System.out.println("vuelve a intentarlo");

                }
            }

        }
        if (numerosJugegos.length == 1) {
            System.out.println("no hay mas numeros posibles");
            resultado = numerosJugegos[0];
            System.out.println("tu resultado es: " + resultado);
            puntacionCifras = cifras.puntuacion(resultado, numInicial);
            System.out.println("puntuacion: " + puntacionCifras);
        }

        return puntacionCifras;
    }

    private static void reescribir(int[] numerosJugegos) {

        System.out.print("Numeros posibles: ");
        for (int i = 0; i < numerosJugegos.length; i++) {
            System.out.print(numerosJugegos[i] + " ");

        }
        System.out.println();

    }

}
