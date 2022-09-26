package it.uniba.app.command;

/**
 * <p>CommandList class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class CommandList{
    /**
     * <p>create.</p>
     * Method that creates a list of commands.
     *
     * @param e a int.
     * @return a {@link Command} object.
     */
    public Command create(int e) {
        //Commands
        if (e == 0) {
            Command nord = new Command(CommandType.NORD, "nord");
            nord.setAlias(new String[]{"n", "N", "Nord", "NORD, nord"});
            return nord;
        } else if (e == 1) {
            Command help = new Command(CommandType.HELP, "aiuto");
            help.setAlias(new String[]{"aiuto", "help", "Help", "Aiuto", "AIUTO", "Welp", "welp"});
            return help;
        } else if (e == 2) {
            Command sud = new Command(CommandType.SOUTH, "sud");
            sud.setAlias(new String[]{"s", "S", "Sud", "SUD, sud"});
            return sud;
        } else if (e == 3) {
            Command est = new Command(CommandType.EAST, "est");
            est.setAlias(new String[]{"e", "E", "Est", "EST, est"});
            return est;
        } else if (e == 4) {
            Command ovest = new Command(CommandType.WEST, "ovest");
            ovest.setAlias(new String[]{"o", "O", "Ovest", "OVEST, ovest"});
            return ovest;
        } else if (e == 5) {
            Command end = new Command(CommandType.END, "end");
            end.setAlias(new String[]{"end", "fine", "esci", "muori", "ammazzati", "ucciditi", "suicidati", "exit"});
            return end;
        } else if (e == 6) {
            Command save = new Command(CommandType.SAVE, "save");
            save.setAlias(new String[]{"salva", "save", "salvataggio"});
            return save;
        } else if (e == 7) {
            Command look = new Command(CommandType.LOOK_AT, "osserva");
            look.setAlias(new String[]{"guarda", "vedi", "trova", "cerca", "descrivi"});
            return look;
        }
        return null;
    }
}
