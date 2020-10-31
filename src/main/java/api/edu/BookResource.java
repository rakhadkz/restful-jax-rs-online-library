package api.edu;



import api.interfaces.BookService;
import api.models.Book;
import api.models.ResponseModel;
import api.services.BookServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/book")
public class BookResource implements BookService{
    BookServiceImpl bookService = new BookServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(){
        try {
            return bookService.getBooks();
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @Override
    public boolean isBorrowable(String isbn, int copies, int readerId) throws Exception {
        return bookService.isBorrowable(isbn, copies, readerId);
    }

    @Path("/test")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test(@FormParam("param1") String param1){
        return Response.ok(new ResponseModel(param1), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Book book){
        try {
            return bookService.create(book);
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @Path("{isbn}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("isbn") String isbn){
        try {
            return bookService.read(isbn);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Response update(Book book){
        try {
            return bookService.update(book);
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type("application/x-www-form-urlencoded").build();
        }
    }

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response update1(@FormParam("isbn") String isbn, @FormParam("name") String name, @FormParam("author") String author){
        try {
            return bookService.update(new Book(isbn, name, author));
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type("application/json").build();
        }
    }


    @DELETE
    @Path("{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("isbn") String isbn){
        try {
            return bookService.delete(isbn);
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Path("{isbn}/readers")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getReaders(@PathParam("isbn") String isbn){
        try {
            return bookService.getReaders(isbn);
        } catch (Exception e) {
            return Response.status(500).entity(new ResponseModel(e.getMessage())).type(MediaType.APPLICATION_JSON).build();
        }
    }
}
