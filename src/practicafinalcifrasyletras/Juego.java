/*
clase que controla los tipos de juego 
 */
package practicafinalcifrasyletras;

public class Juego {

    static LT lt = new LT();
    static Principal p = new Principal();
    Estadisticas e = new Estadisticas();

    public void tipoDeJuego() throws Exception {
        String tipo;
        System.out.println(
                "Elige una opcion:\n"
                + "1. Contra la CPU\n"
                + "2. Contra otro jugador\n"
                + "3. Volver al menu principal\n"
        );

        try {
            int op = lt.llegirEnter();

            switch (op) {
                case 1:
                    tipo = "VS CPU";
                    e.setTipodejuego(tipo);
                    contralaCpu();

                    break;

                case 2:
                    tipo = "Vs humano";
                    e.setTipodejuego(tipo);
                    contraOtroJugador();

                    break;

                case 3:
                    p.menu();
                    break;

                default:
                    System.out.println("Opcion no valida. Intentalo de nuevo.");
            }

        } catch (Exception e) {
            System.out.println("No se permiten letras. Introduce una opcion numerica.");
            return;
        }
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

// metodo jugador vs jugador 
    private void contraOtroJugador() throws Exception {
        int inicio = p.opcion;
        System.out.println("Jugador 1");
        String nombre1 = Jugador.nombre();
        Jugador j1 = new Jugador(nombre1);
        e.setNombreJugador1(nombre1);

        System.out.println("Jugador 2");
        String nombre2 = Jugador.nombre();
        Jugador j2 = new Jugador(nombre2);
        e.setNombreJugador2(nombre2);

        int NumeroPartidas = NumeroDePartidas();
        int vueltas = NumeroPartidas;
        e.setPartidas(NumeroPartidas);

        while (vueltas > 0) {

            if (inicio == 1) {
                // Cifras → Letras
                System.out.print("ronda de cifras");
                System.out.println();

                System.out.print("turno de " + nombre1);
                System.out.println();

                j1.JuegoCifras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                j2.JuegoCifras();
                System.out.println();
                vueltas--;

                System.out.println("ronda de letras ");
                System.out.println();

                System.out.print("turno de " + nombre1);
                System.out.println();

                j1.JuegoLetras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                j2.JuegoLetras();
                System.out.println();
                vueltas--;

            } else {
                // Letras → Cifras
                System.out.println("ronda de letras");
                System.out.println();

                System.out.print("turno de " + nombre1);
                System.out.println();

                j1.JuegoLetras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                j2.JuegoLetras();

                vueltas--;
                System.out.println("ronda de cifras");

                System.out.print("turno de " + nombre1);
                System.out.println();

                j1.JuegoCifras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                j2.JuegoCifras();
                vueltas--;
            }

        }

        // Calcular puntuaciones finales 
        j1.Calcularapuntuacion();
        j2.Calcularapuntuacion();

        // Mostrar ganador
        ganador(j1, j2);
        System.out.println();
        System.out.println();
        System.out.println();
        e.guardarPartida();
        p.menu();
    }
// metodo jugador vs cpu

    private void contralaCpu() throws Exception {

        int inicio = p.opcion;
        System.out.println("Jugador 1");
        String nombre1 = Jugador.nombre();
        Jugador j1 = new Jugador(nombre1);
        e.setNombreJugador1(nombre1);

        System.out.println("Jugador 2");
        String nombre2 = CPU.nombre();
        CPU c = new CPU(nombre2);
        e.setNombreJugador2(nombre2);

        int rondas = NumeroDePartidas();
        int vueltas = rondas;
        e.setPartidas(rondas);
        while (vueltas > 0) {

            if (inicio == 1) {

                // Cifras → Letras
                System.out.print("ronda de cifras");
                System.out.println();

                System.out.print("turno de " + nombre1);
                System.out.println();

                j1.JuegoCifras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                c.Numeros();
                vueltas--;

                System.out.println(rondas);

                System.out.println("ronda de letras ");
                System.out.println();

                System.out.print("turno de " + nombre1);
                System.out.println();

                j1.JuegoLetras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                c.Letras();
                vueltas--;

            } else {
                // Letras → Cifras
                System.out.println("ronda de letras");
                System.out.println();

                System.out.print("turno de " + nombre1);
                System.out.println();

                j1.JuegoLetras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                c.Letras();

                vueltas--;
                System.out.println("ronda de cifras");

                System.out.print("turno de " + nombre1);
                System.out.println();

                j1.JuegoCifras();

                System.out.print("turno de " + nombre2);
                System.out.println();

                c.Numeros();

                vueltas--;
            }

        }

        c.CalcularapuntuacionCPU();
        j1.Calcularapuntuacion();

        ganadorcpu(j1, c);
        System.out.println();
        System.out.println();
        System.out.println();
        e.guardarPartida();
        p.menu();
    }
// miramos al ganador entre dos jugadores 

    private void ganador(Jugador j1, Jugador j2) {

        int puntos1 = j1.getPuntuacion();
        e.setPuntuacion1(puntos1);
        int puntos2 = j2.getPuntuacion();
        e.setPuntuacion2(puntos2);

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
        e.setPuntuacion1(puntos1);
        int puntos2 = cp.getPuntuacionCPU();
        e.setPuntuacion2(puntos2);

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
