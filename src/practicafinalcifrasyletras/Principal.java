/*   
clase principal, tiene los menus 
 */

package practicafinalcifrasyletras;

public class Principal {

    static LT lt = new LT();
    static Juego j = new Juego();
    static leerFicheros l = new leerFicheros();
    public static int opcion;
    static public int op;

    public static void main(String[] args) throws Exception {
        menu();
    }

    public static void menu() throws Exception {

        System.out.println(
                "Elige una opcion:\n"
                + "1. Cifras\n"
                + "2. Letras\n"
                + "3. Salir\n"
                + "4. Estadisticas"
        );

        try {
            opcion = lt.llegirEnter();
        } catch (Exception e) {
            System.out.println("No se permiten letras. Introduce una opcion numerica.");
            menu();
            return;
        }

        switch (opcion) {

            case 1:
                tipoDeJuego();
                break;

            case 2:
                tipoDeJuego();
                break;

            case 3:
                System.out.println("GRACIAS POR JUGAR!!!!"
                        + "FINALIZANDO...");
                break;

            case 4:
                l.leerEstadisticas();
                break;

            default:
                System.out.println("Esta opcion no existe");
                menu();
                break;
        }

    }

    private static void tipoDeJuego() throws Exception {

        System.out.println(
                "Elige una opcion:\n"
                + "1. Contra la CPU\n"
                + "2. Contra otro jugador\n"
                + "3. Volver al menu principal\n"
        );

        try {
            op = lt.llegirEnter();

            switch (op) {
                case 1:

                    j.contralaCpu();

                    break;

                case 2:

                    j.contraOtroJugador();

                    break;

                case 3:

                    break;

                default:
                    System.out.println("Opcion no valida. Intentalo de nuevo.");
            }

        } catch (Exception e) {
            System.out.println("No se permiten letras. Introduce una opcion numerica.");
            return;
        }
    }

}
