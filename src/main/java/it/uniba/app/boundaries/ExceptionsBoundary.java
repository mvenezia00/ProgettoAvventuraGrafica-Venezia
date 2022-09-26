package it.uniba.app.boundaries;

/**
 * <p>ExceptionsBoundary class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class ExceptionsBoundary {
        /**
         * <p>fileNotFound.</p>
         * Exception for file not found
         */
        public static void fileNotFound() {
            String FILE_NOT_FOUND_EXCEPTION = "File not found";
            System.out.println(FILE_NOT_FOUND_EXCEPTION);
        }

        /**
         * <p>emptyMapException.</p>
         * Exception for empty map.
         */
        public static void emptyMapException() {
            String COMMAND_NOT_EXIST_EXCEPTION = "Il file MiaMappa.txt è vuoto.\n";
            System.out.println(COMMAND_NOT_EXIST_EXCEPTION);
        }

        /**
         * <p>fileCreated.</p>
         */
        public static void fileCreated() {
            String FILE_NOT_FOUND_EXCEPTION = "File not found. File created";
            System.out.println(FILE_NOT_FOUND_EXCEPTION);
        }

        /**
         * <p>serverError.</p>
         * Exception for server error.
         */
        public static void serverError() {
            String SERVER_ERROR = "Errore: Il server potrebbe essere offline o connessione assente.";
            System.out.println(SERVER_ERROR);
        }

}
