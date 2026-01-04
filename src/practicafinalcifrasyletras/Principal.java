/*   

 */
package practicafinalcifrasyletras;

public class Principal {

    static LT lt = new LT();
    static Juego j = new Juego();
    static leerFicheros l = new leerFicheros();
    public static int opcion;

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {

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
                j.tipoDeJuego();
                break;
                
            case 2:
                j.tipoDeJuego();
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

}
