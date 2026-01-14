/*
clase que controla todos los atributos de un jugador, el juego letras, el juego cifras, el nombre y su puntyuuación 
 */
package practicafinalcifrasyletras;

public class Jugador {

    static LT lt = new LT();
    static Cifras cifras = new Cifras();
    static Letras letras = new Letras();
    static leerFicheros leer = new leerFicheros();
    private String nombre;
    private int puntuacion;
    private int puntosLetras;
    private int puntosNumeros;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntuacion = 0;
        this.puntosLetras = 0;
        this.puntosNumeros = 0;
    }
// pide el nombre 
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

    public String getNombre() {
        return nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public int getPuntuacionLetras() {
        return puntosLetras;
    }

    public int getPuntuacionNumeros() {
        return puntosNumeros;
    }

    public void Calcularapuntuacion() {
        int puntos = getPuntuacionNumeros() + getPuntuacionLetras();
        this.puntuacion = puntos;
    }

    public void JuegoLetras() throws Exception {

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
                        puntosLetras = letras.puntuacionLetras(palabraUsuario, puntosLetras);
                        System.out.println("Correcta!!! Puntuacion: " + palabraUsuario.length);
                        seguir = false;

                    }

                    // y aquí vuelve al while para que lo intente otra vez
                }
            }
        }

    }

    public void JuegoCifras() throws Exception  {
        int Puntuacion = 0;
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
            // pide los numeros 
            num1 = cifras.pedirNumeroValido(numerosJugegos);

            num2 = cifras.pedirNumeroValido(numerosJugegos);

            System.out.println("elije una operacion:   " + " + " + " - " + " * " + " / " + " = ");
            operacion = lt.llegirCaracter();
            // si la operación no es es un igual, la resuelve y reescribe los numeros, con el resultado
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

                            numerosJugegos = cifras.sustituir(resultado, num1, num2, numerosJugegos);
                            reescribir(numerosJugegos);
                        }

                        break;

                    default:
                        System.out.println("no es una operacion valida");
                        break;
                }
            // en caso de que sea un igual, pide que se intoduzca el resultado y se verifica 
            } else {

                System.out.println("introduce el resultado:  ");
                int num = lt.llegirEnter();
                boolean existe = cifras.Existe(num, numerosJugegos);

                if (existe) {
                    Puntuacion = cifras.puntuacion(num1, numInicial);
                    System.out.println("puntuacion: " + Puntuacion);

                    seguir = false;

                } else {
                    System.out.println("no se puede realizar la operacion");
                    System.out.println("vuelve a intentarlo");

                }
            }

        }
        // si solo queda un nmero, ese es el resultado 
        if (numerosJugegos.length == 1) {
            System.out.println("no hay mas numeros posibles");
            resultado = numerosJugegos[0];
            System.out.println("tu resultado es: " + resultado);
            Puntuacion = cifras.puntuacion(resultado, numInicial);
            System.out.println("puntuacion: " + Puntuacion);
        }
        
        puntosNumeros = cifras.puntuacionNumeros(Puntuacion,puntosNumeros);
    }

    private static void reescribir(int[] numerosJugegos) {

        System.out.print("Numeros posibles: ");
        for (int i = 0; i < numerosJugegos.length; i++) {
            System.out.print(numerosJugegos[i] + " ");

        }
        System.out.println();

    }

}
