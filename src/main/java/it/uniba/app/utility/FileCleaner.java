package it.uniba.app.utility;

import java.io.File;
import java.nio.file.Files;

/**
 * <p>FileCleaner class.</p>
 *
 * @author mvene
 * @version $Id: $Id
 */
public class FileCleaner {
    /**
     * <p>clean.</p>
     */
    public static void clean() {
        try {
        File file = new File("./ServerMap.txt");
        file.deleteOnExit();

    } catch (Exception e) {
    }
    }
}
