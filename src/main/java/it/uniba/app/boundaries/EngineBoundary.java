package it.uniba.app.boundaries;

import it.uniba.app.avventura.GameDescription;

/**
 * <p>EngineBoundary class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class EngineBoundary {

    /**
     * <p>get_welcome_message.</p>
     *
     * @param game a {@link GameDescription} object.
     * @return a int.
     */
    public static int get_welcome_message(GameDescription game) {
        try {
            System.out.println();
            game.getCurrentRoom().getName();
            System.out.println(game.getCurrentRoom().getName());
            System.out.println();
            System.out.println(game.getCurrentRoom().getDescription());
            System.out.println();
            System.out.println("Digita Help per vedere cosa posso fare");
        } catch (NullPointerException e) {
            System.out.println();
            return 1;
        }
        return 0;
    }
    /**
     * <p>command_not_exist.</p>
     */
    public static void command_not_exist() {
        System.out.println("Non capisco cosa devo fare...");
    }
    /**
     * <p>exit.</p>
     * This method is used to exit from the game.
     *
     * @throws java.lang.InterruptedException if any.
     */
    public static void exit() throws InterruptedException {
        System.out.println("Sono stanco, è ora di riposarsi.");
        for (int i = 0; i < 2; i++) {
            System.out.println("...");
            Thread.sleep(1000);
        }
        System.out.println("... Buonanotte");
    }

}
