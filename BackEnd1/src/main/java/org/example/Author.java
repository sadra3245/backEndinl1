package org.example;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({

        @NamedQuery(name = "Author.findName",
                query = "from Author a where a.name = :name"),
        @NamedQuery(name = "Author.ifBookHasBeenRead",
                query = "SELECT DISTINCT a FROM Author a JOIN a.books b JOIN b.readers r"),
        @NamedQuery(name = "Author.howManyBooks",
        query = "SELECT a.name ,  size(a.books) FROM Author a")
})

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String nationality;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    private Set<Book> books = new HashSet<>();

    public Author(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }

    public Author() {

    }

    public void addbooksToAuthor(Book book) {

        this.books.add(book);

    }

    public Set<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", books=" + books +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }
}
