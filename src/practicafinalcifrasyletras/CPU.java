/*  clase que controla los juegos de la cpu, la cpu esta limitada a 1000 intentos a la hora 
de buscar la mejor forma de llegar a la solucion, cuando se trata del metodo numeros.
en cambio en el de letras, se escoge de forma aleatoria una de las palabras que puede formar 

 */
package practicafinalcifrasyletras;

public class CPU {

    static Cifras cifras = new Cifras();
    static Letras letras = new Letras();
    static leerFicheros leer = new leerFicheros();
    private String nombre;
    private int puntuacion;
    private int puntosLetras;
    private int puntosNumeros;
    private static final int MAX = 1000;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // comprueba todas las palabras del diccionario para ver si se pueden 
    // formar con las letras proporcionadas 
    private boolean sePuedeFormar(String palabra, char[] letrasPosibles) {

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

    private String[] palabrasposibles(char[] letrasPosibles) {

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
//de el array de palabras posibles, elegimos la mas larga

    private String palabraEscogida(String[] palabras) {

        String mejor = palabras[0];

        for (int i = 1; i < palabras.length; i++) {

            String actual = palabras[i];

            char[] mejorChars = mejor.toCharArray();
            char[] actualChars = actual.toCharArray();

            if (actualChars.length > mejorChars.length) {
                mejor = actual;
            }
        }

        return mejor;
    }

// el juego de letras 
    public void Letras() {
        char[] letrasJuego = letras.LetrasJuego();

        // Mostrar letras 
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
// inicializa el array dede esatdos, los estados son los numeros posibles y las operaciones realizadas para llegar a esos numeros  
// el array tienen una capacidad maxima igual al numero de intentos que puede hacer la cpu
// aparte inicializa el numero de pendientes a zero al finalizar 

    private void resolver(int[] numerosIniciales, int objetivo) {

        inicializarBusqueda(numerosIniciales, objetivo);

        int capacidad = MAX; // número máximo de estados
        int[][] pendientesNums = new int[capacidad][];
        String[] pendientesPasos = new String[capacidad];

        int nPendientes = 0;

        // estado inicial
        nPendientes = meterPendiente(pendientesNums, pendientesPasos, nPendientes,
                copiarArray(numerosIniciales), "");

        // búsqueda iterativa
        while (nPendientes > 0 && !deboParar()) {

            // sacamos el último (profundidad)
            nPendientes--;
            int[] numsActuales = pendientesNums[nPendientes];
            String pasosAcumulados = pendientesPasos[nPendientes];

            nPendientes = procesarEstado(numsActuales, pasosAcumulados, objetivo,
                    pendientesNums, pendientesPasos, nPendientes);
        }

        imprimirResultado(objetivo);
    }

// inicializa los intentos y busca, de los numeros posibles el que mas se acerque al objetivo 
    private void inicializarBusqueda(int[] numerosIniciales, int objetivo) {

        intentosHechos = 0;

        mejorValor = numerosIniciales[0];
        mejorDiferencia = cifras.valorAbsoluto(mejorValor - objetivo);
        mejorPasos = "";

        for (int i = 0; i < numerosIniciales.length; i++) {
            actualizarMejor(numerosIniciales[i], objetivo, "");
        }
    }
// mira si ya ha llegado al objetivo o a los intentos maximos 

    private boolean deboParar() {
        return mejorDiferencia == 0 || intentosHechos >= MAX;
    }


    /* encargado de trabajar con un estado del juego.Recibe los números que puede usar y
    Comprueba si alguno de ellos se acerca más al objetivo, aparte 
    prueba todas las combinaciones posibles de dos números y opera con ellos.
     */
    private int procesarEstado(int[] numsActuales, String pasosAcumulados, int objetivo,
            int[][] pendientesNums, String[] pendientesPasos, int nPendientes) {

        if (numsActuales.length < 2) {
            return nPendientes;
        }

        // actualizar mejor con números actuales
        for (int i = 0; i < numsActuales.length; i++) {
            actualizarMejor(numsActuales[i], objetivo, pasosAcumulados);
            if (mejorDiferencia == 0) {
                return nPendientes;
            }
        }

        for (int i = 0; i < numsActuales.length; i++) {
            for (int j = i + 1; j < numsActuales.length; j++) {

                if (deboParar()) {
                    return nPendientes;
                }

                int a = numsActuales[i];
                int b = numsActuales[j];

                nPendientes = probarPareja(numsActuales, pasosAcumulados, objetivo,
                        a, b, pendientesNums, pendientesPasos, nPendientes);

                if (nPendientes >= pendientesNums.length) {
                    return pendientesNums.length;
                }
            }
        }

        return nPendientes;
    }

    // prueba si es posible hacer la operacion y el resultado de esa operacion, para ver cual es el mas optimo
    private int probarPareja(int[] numsActuales, String pasosAcumulados, int objetivo,
            int a, int b,
            int[][] pendientesNums, String[] pendientesPasos, int nPendientes) {

        int res;

        // suma
        res = cifras.Suma(a, b);
        nPendientes = pushPendienteOperacion(res, numsActuales, objetivo, a, b,
                pasosAcumulados + a + " + " + b + " = " + res + "\n",
                pendientesNums, pendientesPasos, nPendientes);
        if (deboParar()) {
            return nPendientes;
        }

        // multiplicacion
        res = cifras.multiplicacion(a, b);
        nPendientes = pushPendienteOperacion(res, numsActuales, objetivo, a, b,
                pasosAcumulados + a + " * " + b + " = " + res + "\n",
                pendientesNums, pendientesPasos, nPendientes);
        if (deboParar()) {
            return nPendientes;
        }

        // resta 
        res = cifras.resta(a, b);
        if (res != -1) {
            nPendientes = pushPendienteOperacion(res, numsActuales, objetivo, a, b,
                    pasosAcumulados + a + " - " + b + " = " + res + "\n",
                    pendientesNums, pendientesPasos, nPendientes);
            // si restando a-b da negativo prueba arestar b-a
        } else {
            res = cifras.resta(b, a);
            if (res != -1) {
                nPendientes = pushPendienteOperacion(res, numsActuales, objetivo, b, a,
                        pasosAcumulados + b + " - " + a + " = " + res + "\n",
                        pendientesNums, pendientesPasos, nPendientes);
            }
        }
        if (deboParar()) {
            return nPendientes;
        }

        // divison 
        res = cifras.division(a, b);
        if (res != -1) {
            nPendientes = pushPendienteOperacion(res, numsActuales, objetivo, a, b,
                    pasosAcumulados + a + " / " + b + " = " + res + "\n",
                    pendientesNums, pendientesPasos, nPendientes);
        }
        if (deboParar()) {
            return nPendientes;
        }
        // si a/b no da exacto prueba b/a 
        res = cifras.division(b, a);
        if (res != -1) {
            nPendientes = pushPendienteOperacion(res, numsActuales, objetivo, b, a,
                    pasosAcumulados + b + " / " + a + " = " + res + "\n",
                    pendientesNums, pendientesPasos, nPendientes);
        }

        return nPendientes;
    }

    /* Gestiona todo lo que pasa cuando haces una operacion, augmenta los intentos, mira que no se haya llegado al objetivo
    actualiza el mejor resultado y devuelve la nueva array para que se pueda seguir operando sin repetir numeros 
    
     */
    private int pushPendienteOperacion(int resultado, int[] numsActuales, int objetivo,
            int num1, int num2, String pasosNuevos,
            int[][] pendientesNums, String[] pendientesPasos,
            int nPendientes) {

        if (intentosHechos >= MAX) {
            return nPendientes;
        }
        intentosHechos++;

        if (resultado == -1) {
            return nPendientes;
        }

        actualizarMejor(resultado, objetivo, pasosNuevos);
        if (mejorDiferencia == 0) {
            return nPendientes;
        }

        int[] nuevosNumeros = cifras.sustituir(resultado, num1, num2, numsActuales);

        int num = meterPendiente(pendientesNums, pendientesPasos,
                nPendientes, nuevosNumeros, pasosNuevos);
        return num;
    }
// guarda los estados 

    private int meterPendiente(int[][] pendientesNums, String[] pendientesPasos,
            int nPendientes, int[] nums, String pasos) {

        if (nPendientes >= pendientesNums.length) {
            return nPendientes;
        }

        pendientesNums[nPendientes] = nums;
        pendientesPasos[nPendientes] = pasos;

        return nPendientes + 1;
    }
// copia una nueva array para no manipular la original

    private int[] copiarArray(int[] a) {
        int[] c = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i];
        }
        return c;
    }
// imprime los resultados obtenidos 

    private void imprimirResultado(int objetivo) {

        System.out.println("Objetivo: " + objetivo);
        System.out.println("Mejor resultado CPU: " + mejorValor
                + " (diferencia " + mejorDiferencia + ")");
        System.out.println("Operaciones:\n" + mejorPasos);

        int puntos = cifras.puntuacion(mejorValor, objetivo);
        System.out.println("Puntuación CPU: " + puntos);

        puntosNumeros = cifras.puntuacionNumeros(puntos, puntosNumeros);
    }
// mira quie resultado se aleja menos del objetivo 

    private static void actualizarMejor(int valor, int objetivo, String pasos) {

        int diferencia = cifras.valorAbsoluto(valor - objetivo);

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

        resolver(numerosJugegos, numInicial);

    }

}
