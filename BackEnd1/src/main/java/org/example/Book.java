package org.example;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String genre;
    private int publicationYear;
    @ManyToMany(mappedBy = "books", cascade = CascadeType.PERSIST)
    private Set<Reader> readers;

    public Book(String title, String genre, int publicationYear) {
        this.title = title;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.readers = new HashSet<Reader>();
    }

    public Book() {

    }


    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public Set<Reader> getReaders() {
        return readers;
    }

    public void addReadersToBook(Reader reader) {

        this.readers.add(reader);

        reader.getBooks().add(this);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", publicationYear=" + publicationYear +
                '}';
    }


}
