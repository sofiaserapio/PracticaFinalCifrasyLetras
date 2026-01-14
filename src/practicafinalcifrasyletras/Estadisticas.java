/*
clase para escribir el fichero de estadisticas 
*/

package practicafinalcifrasyletras;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Estadisticas {


    private String fecha() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String fecha = ahora.format(formato);
        return fecha;
    }

   

public void guardarPartidaVsJugador(Jugador j1, Jugador j2, int partidas) {

    FileWriter estadisticas = null;

    try {
        estadisticas = new FileWriter("partidas.txt", true);

        String linea =
                fecha() + "#"
                + "VS JUGADOR" + "#"
                + j1.getNombre() + "#"
                + j2.getNombre() + "#"
                + partidas + "#"
                + j1.getPuntuacion() + "#"
                + j2.getPuntuacion()
                + "\n";

        estadisticas.write(linea);
        estadisticas.flush();

    } catch (Exception e) {
        System.out.println("Error escribiendo el fichero de partidas (VS JUGADOR).");
        e.printStackTrace();

    } finally {
        try {
            if (estadisticas != null) estadisticas.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public void guardarPartidaVsCPU(Jugador jugador, CPU cpu, int partidas) {

    FileWriter estadisticas = null;

    try {
        
        estadisticas = new FileWriter("partidas.txt", true);

        String linea =
                fecha() + "#"
                + "VS CPU" + "#"
                + jugador.getNombre() + "#"
                + "CPU" + "#"
                + partidas + "#"
                + jugador.getPuntuacion() + "#"
                + cpu.getPuntuacionCPU()
                + "\n";

        estadisticas.write(linea);
        estadisticas.flush();

    } catch (Exception e) {
        System.out.println("Error escribiendo el fichero de partidas (VS CPU).");
        e.printStackTrace();

    } finally {
        try {
            if (estadisticas != null) estadisticas.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

  
   


}
