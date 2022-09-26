package it.uniba.app.utility;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Utils class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class Utils {

    /**
     * <p>loadFileListInSet.</p>
     *
     * @param file a {@link java.io.File} object.
     * @return a {@link java.util.Set} object.
     * @throws java.io.IOException if any.
     */
    public static Set<String> loadFileListInSet(File file) throws IOException {
        Set<String> set = new HashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (reader.ready()) {
            set.add(reader.readLine().trim().toLowerCase());
        }
        reader.close();
        return set;
    }

    /**
     * <p>parseString.</p>
     *
     * @param string a {@link java.lang.String} object.
     * @param stopwords a {@link java.util.Set} object.
     * @return a {@link java.util.List} object.
     */
    public static List<String> parseString(String string, Set<String> stopwords) {
        List<String> tokens = new ArrayList<>();
        String[] split = string.toLowerCase().split("\\s+");
        for (String t : split) {
            if (!stopwords.contains(t)) {
                tokens.add(t);
            }
        }
        return tokens;
    }

}
