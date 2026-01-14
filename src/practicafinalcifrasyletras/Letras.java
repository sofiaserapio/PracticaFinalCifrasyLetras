/*
clase que conrola todo lo que tenga que vercon el juego de letras 
*/


package practicafinalcifrasyletras;

import java.util.Random;

public class Letras {

    public static int PuntuacionLetras = 0;
    static leerFicheros lf = new leerFicheros();
    static Letras l = new Letras();

    // escoge diez letras aleatorias de las proporcionadas para el juego
    public static char[] LetrasJuego()  {
        char[] letrasFichero = lf.ListadeLetras();
        char[] letrasJuego = new char[10];
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int indice = random.nextInt(letrasFichero.length);
            letrasJuego[i] = letrasFichero[indice];

        }
        return letrasJuego;
    }

    /* mira que sea posible formar la palabra que ha introduciod el usuario con las letras 
    que se le ha proporcionado */
    public boolean esPosible(String PalabarUsuario, char[] aux)  {
        // creamos dos arrays, una con las letras proporcionadas(aux)
        // y otra con la palabra del usuario 
        char[] usuario = PalabarUsuario.toCharArray();
        boolean esPosible = false;
        // miramos que la palabra del usuario no tenga mas letras que las posibles 
        if (usuario.length > aux.length) {
            return esPosible;
            // en caso de que no tenga mas de 10 letras, comprobamos que las letras usadas
            // se encuentran en las proporcionadas al principio del juego

        } else {
            // miramos si coincide
            for (int i = 0; i < usuario.length; i++) {
                esPosible = false;
                for (int j = 0; j < aux.length; j++) {
                    if (usuario[i] == aux[j] && esPosible == false) {
                        // en caso de que coincidan se sustituye por un '*' y se rompe el flujo 
                        // para que en caso de repeticion no se tachen dos letras, si solo se ha usado 1
                        esPosible = true;
                        aux[j] = '*';
                        

                    }

                }
                // si alguna letra no se ha encontrado, se devuelve false y se termina
                if (esPosible == false) {
                    return esPosible;
                }

            }
        }
        return esPosible;
    }
// puntuación del juego de letras 
    public int puntuacionLetras(char[] usuario, int puntuacion) {

        puntuacion = puntuacion + usuario.length;

        return puntuacion;
    }



}
