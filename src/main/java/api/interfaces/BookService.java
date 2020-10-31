package api.interfaces;

import api.models.Book;
import api.models.Reader;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

public interface BookService {
    //CRUD
    Response create(Book book) throws Exception;
    Response read(String isbn) throws Exception;
    Response update(Book book) throws Exception;
    Response delete(String isbn) throws Exception;

    //All books
    Response getBooks() throws Exception;

    //Interaction with reader
    boolean isBorrowable(String isbn, int copies, int readerId) throws Exception;
    Response getReaders(String isbn) throws Exception;
}
