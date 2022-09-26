package it.uniba.app.utility;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * <p>TimeAPI class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class TimeAPI{
    /**
     * <p>getTime.</p>
     * RestAPI to get the current time. (https://timezonedb.com/api)
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getTime() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://worldtimeapi.org/api/timezone/Europe");
        Response resp = target.path("Rome")
                .request(MediaType.APPLICATION_JSON).get();
        String risposta = resp.readEntity(String.class);
        String[] rispostaSplit = new String[5];
        for (int i=0; i<=4; i++) {
            rispostaSplit = risposta.split(",");
        }
        risposta = rispostaSplit[2];
        String[] tempo = risposta.split("T");
        String[] tempo1 = tempo[1].split("\\.");
        return tempo1 [0];
    }


}
