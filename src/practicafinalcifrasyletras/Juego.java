/*
clase que controla los tipos de juego 
 */
package practicafinalcifrasyletras;

public class Juego {

    static LT lt = new LT();
    static Principal p = new Principal();
    Estadisticas e = new Estadisticas();
    private int partidas;

    public static String TipodeJuego() {
        String tipodejuego = "";
        int tp = p.op;
        if (tp == 1) {
            tipodejuego = "Vs CPU";
        } else if (tp == 2) {
            tipodejuego = "Vs humano";

        }

        return tipodejuego;
    }

// pedimos las partidas que quieren jugar, controlando que sea un numero par positivo
    public int NumeroDePartidas() {

        int rondas = -1;

        while (rondas < 0 || rondas % 2 != 0) {
            System.out.println("Cuantas rondas quieres jugar (numero par y positivo, 0 para salir): ");
            rondas = lt.llegirEnter();

            if (rondas < 0 || rondas % 2 != 0) {
                System.out.println("Ha de ser un numero par y positivo.");
            }
        }

        if (rondas == 0) {
            System.out.println("Muchas gracias por jugar!!");
        }

        return rondas;
    }

    public void setPartidas(int numPartidas) {
        this.partidas = numPartidas;
    }

    public int getPartidas() {
        return this.partidas;
    }
// metodo jugador vs jugador 

    public void contraOtroJugador() throws Exception {
        int inicio = p.opcion;
        System.out.println("Jugador 1");
        String nombre1 = Jugador.nombre();
        Jugador jugador1 = new Jugador(nombre1);

        System.out.println("Jugador 2");
        String nombre2 = Jugador.nombre();
        Jugador jugador2 = new Jugador(nombre2);
        int NumeroPartidas = NumeroDePartidas();
        setPartidas(NumeroPartidas);
        int vueltas = NumeroPartidas;

        while (vueltas > 0) {

            if (inicio == 1) {
                // Cifras → Letras
                System.out.print("ronda de cifras");
                System.out.println();

                System.out.print("turno de " + nombre1);
                System.out.println();

                jugador1.JuegoCifras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                jugador2.JuegoCifras();
                System.out.println();
                vueltas--;

                System.out.println("ronda de letras ");
                System.out.println();

                System.out.print("turno de " + nombre1);
                System.out.println();

                jugador1.JuegoLetras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                jugador2.JuegoLetras();
                System.out.println();
                vueltas--;

            } else {
                // Letras → Cifras
                System.out.println("ronda de letras");
                System.out.println();

                System.out.print("turno de " + nombre1);
                System.out.println();

                jugador1.JuegoLetras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                jugador2.JuegoLetras();

                vueltas--;
                System.out.println("ronda de cifras");

                System.out.print("turno de " + nombre1);
                System.out.println();

                jugador1.JuegoCifras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                jugador2.JuegoCifras();
                vueltas--;
            }

        }

        // Calcular puntuaciones finales 
        jugador1.Calcularapuntuacion();
        jugador2.Calcularapuntuacion();

        // Mostrar ganador
        ganador(jugador1, jugador2);
        System.out.println();
        System.out.println();
        System.out.println();
        e.guardarPartidaVsJugador(jugador1, jugador2, getPartidas());
        p.menu();
    }
// metodo jugador vs cpu

    public void contralaCpu() throws Exception {
        int inicio = p.opcion;
        System.out.println("Jugador 1");
        String nombre1 = Jugador.nombre();
        Jugador jugador1 = new Jugador(nombre1);

        System.out.println("Jugador 2");
        String nombre2 = CPU.nombre();
        CPU cpu = new CPU(nombre2);
        int rondas = NumeroDePartidas();
        setPartidas(rondas);
        int vueltas = rondas;
        while (vueltas > 0) {

            if (inicio == 1) {

                // Cifras → Letras
                System.out.print("ronda de cifras");
                System.out.println();

                System.out.print("turno de " + nombre1);
                System.out.println();

                jugador1.JuegoCifras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                cpu.Numeros();
                vueltas--;

                System.out.println(rondas);

                System.out.println("ronda de letras ");
                System.out.println();

                System.out.print("turno de " + nombre1);
                System.out.println();

                jugador1.JuegoLetras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                cpu.Letras();
                vueltas--;

            } else {
                // Letras → Cifras
                System.out.println("ronda de letras");
                System.out.println();

                System.out.print("turno de " + nombre1);
                System.out.println();

                jugador1.JuegoLetras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                cpu.Letras();

                vueltas--;
                System.out.println("ronda de cifras");

                System.out.print("turno de " + nombre1);
                System.out.println();

                jugador1.JuegoCifras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                cpu.Numeros();

                vueltas--;
            }

        }

        cpu.CalcularapuntuacionCPU();
        jugador1.Calcularapuntuacion();

        ganadorcpu(jugador1, cpu);
        System.out.println();
        System.out.println();
        System.out.println();
        e.guardarPartidaVsCPU(jugador1, cpu, getPartidas());
        p.menu();
    }
// miramos al ganador entre dos jugadores 

    private void ganador(Jugador j1, Jugador j2) {

        int puntos1 = j1.getPuntuacion();
        int puntos2 = j2.getPuntuacion();

        System.out.println("Resultado final:");
        System.out.println(j1.getNombre() + ": " + puntos1 + " puntos");
        System.out.println(j2.getNombre() + ": " + puntos2 + " puntos");

        if (puntos1 > puntos2) {
            System.out.println("Ha ganado " + j1.getNombre());
        } else if (puntos2 > puntos1) {
            System.out.println("Ha ganado " + j2.getNombre());
        } else {
            System.out.println("Empate");
        }
    }
// miramos el ganador entre el jugador y la cpu

    private void ganadorcpu(Jugador j1, CPU cp) {

        int puntos1 = j1.getPuntuacion();
        int puntos2 = cp.getPuntuacionCPU();

        System.out.println("Resultado final:");
        System.out.println(j1.getNombre() + ": " + puntos1 + " puntos");
        System.out.println("CPU" + ": " + puntos2 + " puntos");

        if (puntos1 > puntos2) {
            System.out.println("Ha ganado " + j1.getNombre());
        } else if (puntos2 > puntos1) {
            System.out.println("Ha ganado " + "CPU");
        } else {
            System.out.println("Empate");
        }
    }

}
