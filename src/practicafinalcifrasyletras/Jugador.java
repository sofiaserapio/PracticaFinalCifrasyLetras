/*

 */
package practicafinalcifrasyletras;

public class Jugador {

    static LT lt = new LT();
    static Cifras c = new Cifras();
    static Letras l = new Letras();
    static Estadisticas e = new Estadisticas();

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        int opcion;

        System.out.println(
                "Elige una opcion:\n"
                + "1. Cifras\n"
                + "2. Letra\n"
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
                c.menuCifras();
                break;

            case 2:
                l.menuLetras();
                break;

            case 3:
                System.out.println("GRACIAS POR JUGAR!!!!"
                        + "FINALIZANDO...");
                break;

            case 4:
                e.LeerEstadisticas();
                break;

            default:
                System.out.println("Esta opcion no existe");
                menu();
                break;
        }

    }
}
        
