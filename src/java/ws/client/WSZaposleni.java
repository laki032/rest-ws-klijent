package ws.client;

import domain.Aviomehanicar;
import domain.Pilot;
import domain.Zaposleni;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * @author Lazar Vujadinovic
 */
public class WSZaposleni {

    private final WebTarget webTarget;
    private final Client client;
    private static final String BASE_URI = "http://localhost:8080/WS/rest";

    public WSZaposleni() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("zaposleni");
    }

    public <T> T findAllPilot(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("piloti");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public String edit(Zaposleni novi) throws ClientErrorException {
        if (novi instanceof Pilot) {
            return editPilot(novi);
        }
        if (novi instanceof Aviomehanicar) {
            return editMehanicar(novi);
        }
        return "error";
    }

    private String editPilot(Object requestEntity) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("edit/pilot", (Object) null)).
                request(javax.ws.rs.core.MediaType.APPLICATION_JSON).
                post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), String.class);
    }

    private String editMehanicar(Object requestEntity) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("edit/mehanicar", (Object) null)).
                request(javax.ws.rs.core.MediaType.APPLICATION_JSON).
                post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), String.class);
    }

    public <T> T find(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public String createAll(Zaposleni[] requestEntity) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("createAll", (Object) null)).
                request(javax.ws.rs.core.MediaType.APPLICATION_JSON).
                post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), String.class);
    }

    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findAllAvioMehanicar(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("mehanicari");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public String remove(String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("delete/{0}", new Object[]{id}));
        return resource.request().get(String.class);
    }

    public void close() {
        client.close();
    }

}
