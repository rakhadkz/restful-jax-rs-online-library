package api.services;

import api.config.Database;
import api.interfaces.BookService;
import api.models.Book;
import api.models.Reader;
import api.models.ResponseModel;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class BookServiceImpl implements BookService {

    @Override
    public Response create(Book book) throws Exception {
        String query = "insert into book (isbn, name, author, copies) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
        preparedStatement.setString(1, book.getIsbn());
        preparedStatement.setString(2, book.getName());
        preparedStatement.setString(3, book.getAuthor());
        preparedStatement.setInt(4, book.getCopies());
        preparedStatement.executeUpdate();
        Database.getConnection().close();
        preparedStatement.close();
        return Response.ok(new ResponseModel("Book is created successfully"), MediaType.APPLICATION_JSON).build();
    }

    public int getCopies(String isbn) throws Exception{
        Database.getConnection().close();
        Statement statement = Database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Book where isbn = '" + isbn + "'");
        if (!resultSet.isBeforeFirst()){
            throw new Exception("No book with such ISBN");
        }
        resultSet.next();
        return resultSet.getInt("copies");
    }

    @Override
    public Response read(String isbn) throws Exception {
        Database.getConnection().close();
        Statement statement = Database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Book where isbn = '" + isbn + "'");
        Book book = null;
        if (!resultSet.isBeforeFirst()){
            throw new Exception("No book with such ISBN");
        }
        while (resultSet.next()){
            book = new Book(
                    resultSet.getString("isbn"),
                    resultSet.getString("name"),
                    resultSet.getString("author"),
                    resultSet.getInt("copies")
            );
        }
        resultSet.close();
        statement.close();
        Database.getConnection().close();
        return Response.ok(book, MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response update(Book book) throws Exception {
        String query = "update book set name = ?, author = ? where isbn = ?";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
        preparedStatement.setString(1, book.getName());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getIsbn());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return Response.ok(new ResponseModel("Book is updated successfully"), "application/json").build();
    }

    @Override
    public Response delete(String isbn) throws Exception {
        String query = "DELETE FROM Book WHERE isbn = ?";
        PreparedStatement st = Database.getConnection().prepareStatement(query);
        st.setString(1, isbn);
        st.executeUpdate();
        st.close();
        Database.getConnection().close();
        return Response.ok(new ResponseModel("Book is deleted successfully"), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public boolean isBorrowable(String isbn, int copies, int readerId) throws Exception {
        Statement statement = Database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from BorrowedBooks where borrowedBookIsbn = '" + isbn + "'");
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        if (copies > resultSetMetaData.getColumnCount()){
            while (resultSet.next()){
                if (readerId == resultSet.getInt("reader_id")){
                    throw new Exception("You have already borrowed this book");
                }
            }
            return true;
        }
        statement.close();
        resultSet.close();
        Database.getConnection().close();
        return false;
    }

    @Override
    public Response getReaders(String isbn) throws Exception {
        ArrayList<Reader> list = new ArrayList<>();
        String query = "select * from BorrowedBooks b inner join Reader r on " +
                "b.reader_id = r.id where b.borrowedBookIsbn = '" + isbn + "'";
        Statement statement = Database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            list.add(new Reader(id, firstName, lastName));
        }
        resultSet.close();
        statement.close();
        Database.getConnection().close();
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response getBooks() throws Exception {
        ArrayList<Book> list = new ArrayList<>();
        Statement statement = Database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Book");
        while (resultSet.next()){
            String isbn = resultSet.getString("isbn");
            String name = resultSet.getString("name");
            String author = resultSet.getString("author");
            int copies = resultSet.getInt("copies");
            list.add(new Book(isbn, name, author, copies));
        }
        resultSet.close();
        statement.close();
        Database.getConnection().close();
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }
}
