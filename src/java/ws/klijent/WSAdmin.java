package ws.klijent;

import domain.Admin;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:AdminFacadeREST [admin]<br>
 * USAGE:
 * <pre>
 *        WSAdmin client = new WSAdmin();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Lazar Vujadinovic
 */
public class WSAdmin {
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/RESTWSAvioKompanija/rest";

    public WSAdmin() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("admin");
    }

    public String logout(Admin a) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("logout");
        return resource.request().get(String.class);
    }

    public Admin login(Admin a) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("login");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(Admin.class);
    }

    public void close() {
        client.close();
    }
    
}