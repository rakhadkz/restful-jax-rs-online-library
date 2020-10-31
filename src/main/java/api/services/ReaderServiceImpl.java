package api.services;


import api.config.Database;
import api.interfaces.ReaderService;
import api.models.Book;
import api.models.Reader;
import api.models.ResponseModel;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ReaderServiceImpl implements ReaderService {
    @Override
    public Response create(Reader reader) throws Exception {
        String query = "insert into reader (email, password, firstName, lastName) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
        preparedStatement.setString(1, reader.getEmail());
        preparedStatement.setString(2, reader.getPassword());
        preparedStatement.setString(3, reader.getFirstName());
        preparedStatement.setString(4, reader.getLastName());
        preparedStatement.executeUpdate();
        Database.getConnection().close();
        preparedStatement.close();
        return Response.ok(new ResponseModel("Reader is created successfully"), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response read(int readerId) throws Exception {
        Database.getConnection().close();
        Statement statement = Database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Reader where id = " + readerId);
        Reader reader = null;
        while (resultSet.next()){
            reader = new Reader(
                    resultSet.getInt("id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
        }
        resultSet.close();
        statement.close();
        Database.getConnection().close();
        return Response.ok(reader, MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response update(Reader reader) throws Exception {
        String query = "update reader set firstName = ?, lastName = ?, email = ? where id = ?";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
        preparedStatement.setString(1, reader.getFirstName());
        preparedStatement.setString(2, reader.getLastName());
        preparedStatement.setString(3, reader.getEmail());
        preparedStatement.setInt(4, reader.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return Response.ok(new ResponseModel("Reader is updated successfully"), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response delete(int readerId) throws Exception {
        if (!hasBooks(readerId)){
            String query = "DELETE FROM reader WHERE id = ?";
            PreparedStatement st = Database.getConnection().prepareStatement(query);
            st.setInt(1, readerId);
            st.executeUpdate();
            st.close();
            Database.getConnection().close();
            return Response.ok(new ResponseModel("Reader is deleted successfully"), MediaType.APPLICATION_JSON).build();
        }
        throw new Exception("The reader has borrowed books");
    }

    @Override
    public Response getReaders() throws Exception {
        ArrayList<Reader> list = new ArrayList<>();
        Statement statement = Database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Reader");
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            list.add(new Reader(id, firstName, lastName, email, password));
        }
        resultSet.close();
        statement.close();
        Database.getConnection().close();
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response addBook(int readerId, String isbn) throws Exception {
        BookServiceImpl bookService = new BookServiceImpl();
        int copies = bookService.getCopies(isbn);
        if (bookService.isBorrowable(isbn, copies, readerId)){
            String query = "insert into BorrowedBooks (borrowedBookIsbn, reader_id) values (?, ?)";
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
            preparedStatement.setString(1, isbn);
            preparedStatement.setInt(2, readerId);
            preparedStatement.executeUpdate();
            Database.getConnection().close();
            preparedStatement.close();
            return Response.ok(new ResponseModel("Added successfully"), MediaType.APPLICATION_JSON).build();
        }
        throw new Exception("All copies are borrowed");
    }

    @Override
    public Response removeBook(int readerId, String isbn) throws Exception {
        String query = "DELETE FROM BorrowedBooks WHERE borrowedBookIsbn = ? and reader_id = ?";
        PreparedStatement st = Database.getConnection().prepareStatement(query);
        st.setString(1, isbn);
        st.setInt(2, readerId);
        st.executeUpdate();
        st.close();
        Database.getConnection().close();
        return Response.ok(new ResponseModel("Removed successfully"), MediaType.APPLICATION_JSON).build();
    }

    private boolean hasBooks(int readerId) throws Exception {
        String query = "select * from BorrowedBooks where reader_id = " + readerId;
        Statement statement = Database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet.isBeforeFirst();
    }

    @Override
    public Response getBooks(int readerId) throws Exception {
        ArrayList<Book> list = new ArrayList<>();
        String query = "select * from BorrowedBooks bb inner join Book b on " +
                "bb.borrowedBookIsbn = b.isbn where bb.reader_id = " + readerId;
        Statement statement = Database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            String isbn = resultSet.getString("isbn");
            String name = resultSet.getString("name");
            String author = resultSet.getString("author");
            list.add(new Book(isbn, name, author));
        }
        resultSet.close();
        statement.close();
        Database.getConnection().close();
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response changePassword(int readerId, String oldPassword, String newPassword) throws Exception {
        if (isCorrectPassword(readerId, oldPassword)){
            String query = "update reader set password = ? where id = ?";
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, readerId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return Response.ok(new ResponseModel("Password is changed successfully"), MediaType.APPLICATION_JSON).build();
        }
        throw new Exception("Incorrect current password");
    }

    private boolean isCorrectPassword(int readerId, String password) throws Exception {
        String query = "select * from reader where id = " + readerId + " and password = '" + password + "'";
        Statement statement = Database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet.isBeforeFirst();
    }
}
