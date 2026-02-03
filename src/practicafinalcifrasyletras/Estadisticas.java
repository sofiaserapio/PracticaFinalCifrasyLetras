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

    private int partidas;
    private String tipodejuego;
    private String fecha;
    private String NombreJugador1;
    private String NombreJugador2;
    private int puntuacion1;
    private int puntuacion2;
    

    private String fecha() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        fecha = ahora.format(formato);
        return fecha;
    }

    public int getPartidas() {
        return partidas;
    }

    public void setPartidas(int partidas) {
        this.partidas = partidas;
    }

    public String getTipodejuego() {
        return tipodejuego;
    }

    public void setTipodejuego(String tipodejuego) {
        this.tipodejuego = tipodejuego;
    }

    public String getNombreJugador1() {
        return NombreJugador1;
    }

    public void setNombreJugador1(String NombreJugador1) {
        this.NombreJugador1 = NombreJugador1;
    }

    public String getNombreJugador2() {
        return NombreJugador2;
    }

    public void setNombreJugador2(String NombreJugador2) {
        this.NombreJugador2 = NombreJugador2;
    }

    public int getPuntuacion1() {
        return puntuacion1;
    }

    public void setPuntuacion1(int puntuacion1) {
        this.puntuacion1 = puntuacion1;
    }

    public int getPuntuacion2() {
        return puntuacion2;
    }

    public void setPuntuacion2(int puntuacion2) {
        this.puntuacion2 = puntuacion2;
    }

    @Override
    public String toString() {

        return fecha() + "#"
                + getTipodejuego() + "#"
                + getNombreJugador1() + "#"
                + getNombreJugador2() + "#"
                + getPartidas() + "#"
                + getPuntuacion1() + "#"
                + getPuntuacion2();
    }
    

    public void guardarPartida() {

        FileWriter estadisticas = null;

        try {
            estadisticas = new FileWriter("partidas.txt", true);
            estadisticas.write(this.toString() + "\n");
            estadisticas.flush();

        } catch (Exception e) {
            System.out.println("Error escribiendo el fichero de partidas.");
            e.printStackTrace();

        } finally {
            try {
                if (estadisticas != null) {
                    estadisticas.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
