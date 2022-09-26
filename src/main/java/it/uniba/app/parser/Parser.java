package it.uniba.app.parser;

import it.uniba.app.utility.Utils;
import it.uniba.app.command.Command;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

/**
 * <p>Parser class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class Parser {

    private final Set<String> stopwords;

    /**
     * <p>Constructor for Parser.</p>
     *
     * @param stopwords a {@link java.util.Set} object.
     */
    public Parser(Set<String> stopwords) {
        this.stopwords = stopwords;
    }

    private int checkForCommand(String token, @NotNull List<Command> commands) {
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).getName().equals(token) || commands.get(i).getAlias().contains(token)) {
                return i;
            }
        }
        return -1;
    }


    /* ATTENZIONE: il parser è implementato in modo abbastanza indipendente dalla lingua, ma riconosce solo
     * frasi semplici del tipo <azione> <oggetto> <oggetto>. Eventuali articoli o preposizioni vengono semplicemente
     * rimossi. 1.0 Ora se vengono inserite più azioni, non viene più lanciata eccezione, verrà riconosciuto solo il primo comando inserito.
     */

    /**
     * <p>parse.</p>
     * This method parses a string and returns a {@link ParserOutput} object.
     *
     * @param command  a {@link java.lang.String} object.
     * @param commands a {@link java.util.List} object.
     * @return a {@link ParserOutput} object.
     */
    public ParserOutput parse(String command, List<Command> commands) {
        List<String> tokens = Utils.parseString(command, stopwords);
        if (!tokens.isEmpty()) {
            int ic = checkForCommand(tokens.get(0), commands);
            if (ic > -1) {
                return new ParserOutput(commands.get(ic));
            } else {
                return new ParserOutput(null);
            }
        } else {
            return null;
        }
    }
}
