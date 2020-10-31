package api.interfaces;


import api.models.Book;
import api.models.Reader;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

public interface ReaderService{
    //CRUD
    Response create(Reader reader) throws Exception;
    Response read(int readerId) throws Exception;
    Response update(Reader reader) throws Exception;
    Response delete(int readerId) throws Exception;

    //All readers
    Response getReaders() throws Exception;

    //Interaction with Book
    Response addBook(int readerId, String isbn) throws Exception;
    Response removeBook(int readerId, String isbn) throws Exception;
    Response getBooks(int readerId) throws Exception;

    //Password changing
    Response changePassword(int readerId, String oldPassword, String newPassword) throws Exception;
}
