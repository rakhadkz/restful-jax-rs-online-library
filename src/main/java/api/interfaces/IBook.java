package api.interfaces;


import api.models.Reader;

import java.util.ArrayList;

public interface IBook {
    void deleteReader(int id) throws Exception;
    void addReader(int id) throws Exception;
    ArrayList<Reader> getReaders() throws Exception;
    void create() throws Exception;
    void delete() throws Exception;
    void update() throws Exception;
    void borrow(int readerID) throws Exception;
    boolean isBorrowable(int readerID) throws Exception;
}
