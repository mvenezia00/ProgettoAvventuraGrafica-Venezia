package it.uniba.app.boundaries;

/**
 * <p>DungeonBoundary class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class DungeonBoundary {
    /**
     * <p>dungeonMessages.</p>
     *
     * @param Name a {@link java.lang.String} object.
     * @param Description a {@link java.lang.String} object.
     */
    public static void dungeonMessages(String Name, String Description) {
        System.out.println(Name);
        System.out.println(Description);
    }
    /**
     * <p>endMessages.</p>
     *
     * @param r a int.
     */
    public static void endMessages (int r) {
        if (r == 0) {
            System.out.println("Entrando nella stanza hai calpestato una mattonella\n" +
                    "la porta dietro di te si chiude e dal soffitto escono degli spuntoni.\n Muori dissanguato tra atroci sofferenze.\n");
        } else if(r == 1) {
            System.out.println("Sotto di te si apre una voragine. Sprofondi e ti spezzi entrambe le gambe.\n Muori di fame e di dolore.\n");
        } else if(r == 2) {
            System.out.println("La porta dietro di te si chiude improvvisamente. \n Dai muri iniziano ad uscire fiamme.\n Muori incenerito.\n");
        } else if(r == 3) {
            System.out.println("La porta dietro di te si chiude improvvisamente. \n Dai muri inizia ad uscire una nube velenosa.\n Muori avvelenato.\n");
        } else if(r == 4) {
            System.out.println("La porta dietro di te si chiude improvvisamente. \n La stanza è piena di armature che iniziano a muoversi.\n Vieni ucciso.\n");
        } else if(r == 5) {
            System.out.println("La porta dietro di te si chiude improvvisamente. \n Per terra vedi comparire un cerchio magico.\n Esplodi e muori.\n");
        } else if(r == 6) {
            System.out.println("Acciderbolina! Sei morto per una morte violenta.\n");
        } else if (r==8) {
            System.out.println("Congratulazioni!! Hai completato il gioco. \n Adesso puoi andare a dormire tranquillo.\n\n");
        } else {
            System.out.println("La porta dietro di te si chiude improvvisamente. \n Dai muri escono lame giganti.\n Una lama ti taglia la testa e muori.\n");
        }
    }
    /**
     * <p>retryMessages.</p>
     * Prints messages when the user dies.
     *
     * @param a a int.
     */
    public static void retryMessages (int a) {
        if (a == 1) System.out.println("Vuoi continuare? (s/n)");
        else if (a == 2) System.out.println("Grazie per aver giocato!");
        else if (a == 3) System.out.println("Non hai capito, riprova. (s/n)");
        else if (a == 4) System.out.println("Game Over");
    }
    /**
     * <p>actionMessages.</p>
     *Prints the action messages.
     *
     * @param a a int.
     */
    public static void actionMessages (int a) {
        if (a == 1) System.out.println("Non c'è niente di interessante qui.");
        else if (a == 2) System.out.println("Comandi disponibili: \nNord, Sud, Est, Ovest, \nAiuto, Esci, Guarda, Salva");
        else if (a == 3) System.out.println("Il bosco è troppo fitto.\n");
        else if (a == 4) System.out.println("Hey, questo muro non si sposta...\n");
    }
    /**
     * <p>saveMessages.</p>
     *Prints messages for save and load methods.
     *
     * @param a a int.
     */
    public static void saveMessages (int a) {
        if (a == 1) System.out.println("Salvataggio effettuato con successo.");
        else if (a == 3) System.out.println("Vuoi caricare una partita precedente?\n1. Si\n2. No");
    }
    /**
     * <p>loadMessages.</p>
     *Prints a message when the user loads a saved game.
     *
     * @param ora a {@link java.lang.String} object.
     */
    public static void loadMessages(String ora) {
        System.out.println("Partita caricata con successo. Orario Salvataggio: " + ora);
    }

    /**
     * <p>newGameMessage.</p>
     * This method prints a list of options when you open the game.
     */
    public static void newGameMessage () {
        System.out.println("================================");
        System.out.println("*       Dungeon of Doom     *");
        System.out.println("================================");
        System.out.println();
        System.out.println("Benvenuto in Dungeon of Doom!");
        System.out.println("Seleziona tra: ");
        System.out.println("1. Nuova Partita");
        System.out.println("2. Carica Partita");
        System.out.println("3. Salva la tua mappa personalizzata sul server (modifica il file miaMappa.txt nella cartella principale del gioco)");
        System.out.println("    (N.B. l’ultima stanza deve avere uno dei collegamenti alle altre stanze all’ID 99.)");
        System.out.println("4. Carica una mappa creata da un altro utente");
        System.out.println("5. Esci");
    }

}
