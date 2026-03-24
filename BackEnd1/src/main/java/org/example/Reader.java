package org.example;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({

        @NamedQuery(name = "Reader.readBooks",
                query = "Select b from Reader r join r.books b where  b.title = :bookTitle"),
        @NamedQuery(name = "Reader.CheckIfBooksExist",
                query = "FROM Reader r where :bookTitle  member of r.books")
})
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_readers")
    private Set<Book> books;

    public Reader(String name, String email) {
        this.name = name;
        this.email = email;
        this.books = new HashSet<Book>();
    }

    public void addBookToReader(Book book) {

        this.books.add(book);

        book.getReaders().add(this);
    }

    public Reader() {

    }

    public Set<Book> getBooks() {
        return books;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", books=" + books +
                '}';
    }
}
