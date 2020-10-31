package api.models;


import api.config.Database;
import api.interfaces.IBook;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class Book implements Serializable {
    private String isbn;
    private String name;
    private String author;
    private int copies;

    public Book() {
    }

    public Book(String isbn, String name, String author, int copies) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.copies = copies;
    }

    public Book(String isbn, int copies) {
        this.isbn = isbn;
        this.copies = copies;
    }

    public Book(String isbn, String name, String author) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
    }

    public Book(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
}
