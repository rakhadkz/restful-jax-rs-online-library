package api.edu;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloWorld {
    @Path("/world")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayHello(){
        return "Hello";
    }
}
