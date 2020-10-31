package api.edu;

import api.interfaces.ReaderService;
import api.models.Reader;
import api.models.ResponseModel;
import api.services.ReaderServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("reader")
public class ReaderResource implements ReaderService {
    ReaderServiceImpl readerService = new ReaderServiceImpl();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Reader reader){
        try {
            return readerService.create(reader);
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") int id){
        try {
            return readerService.read(id);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Reader reader){
        try {
            return readerService.update(reader);
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id){
        try {
            return readerService.delete(id);
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Override
    public Response getReaders() {
        try {
            return readerService.getReaders();
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(){
        try {
            return readerService.getReaders();
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @Path("/addBook")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response addBook(@FormParam("readerId") int readerId, @FormParam("isbn") String isbn){
        try {
            return readerService.addBook(readerId, isbn);
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @Path("/removeBook")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response removeBook(@FormParam("readerId") int readerId, @FormParam("isbn") String isbn){
        try {
            return readerService.removeBook(readerId, isbn);
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Path("{id}/books")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getBooks(@PathParam("id") int id){
        try {
            return readerService.getBooks(id);
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }


    @PUT
    @Path("/updatePassword")
    @Consumes("application/x-www-form-urlencoded")
    @Override
    public Response changePassword(@FormParam("readerId") int readerId, @FormParam("oldP") String oldPassword, @FormParam("newP") String newPassword){
        try {
            return readerService.changePassword(readerId, oldPassword, newPassword);
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }
}
