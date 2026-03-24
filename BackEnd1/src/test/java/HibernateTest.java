import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.Author;
import org.example.Book;
import org.example.Reader;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Set;

public class HibernateTest {

    private static SessionFactory sessionFactory = null;

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();
        }

        return sessionFactory;
    }


    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        System.out.println("påbörjar uppgift 1: ");

        Author a1 = new Author("Mishu", "Romanian");
        Author a2 = new Author("Långfil", "Swedish");
        Author a3 = new Author("Thatonefrenchie", "French");


        Book b1 = new Book("Pickpocket", "True Crime", 2007);
        Book b2 = new Book("50 Shades Of Cougar", "Romance", 1997);
        Book b3 = new Book("The 1%", "Horror", 2002);
        Book b4 = new Book("Grief", "Self-Help", 1997);
        Book b5 = new Book("The Orphan", "Biography", 2011);

        Reader r1 = new Reader("Sadra", "alex.johnson89@example.com");
        Reader r2 = new Reader("Adam", "sarah.m.lee2026@domain.net");
        Reader r3 = new Reader("Ana", "k.baker.temp@service.io");

        a1.addbooksToAuthor(b1);

        a2.addbooksToAuthor(b4);
        a2.addbooksToAuthor(b5);

        a3.addbooksToAuthor(b2);
        a3.addbooksToAuthor(b3);

        r1.addBookToReader(b1);
        r1.addBookToReader(b2);
        r2.addBookToReader(b3);
        r3.addBookToReader(b4);
        r3.addBookToReader(b5);

        em.persist(a1);
        em.persist(a2);
        em.persist(a3);

        em.persist(r1);
        em.persist(r2);
        em.persist(r3);


        System.out.println("klar!");


        String theBook = "Pickpocket";
        String authorName = "Långfil";
        String Genre = "Biography";


        List<Author> authors1 = em.createNamedQuery("Author.findName", Author.class).setParameter("name", authorName).getResultList();

        for (Author s : authors1) {
            System.out.println(s);
        }


        Author author2 = em.createNamedQuery("Author.findName", Author.class).setParameter("name", authorName).getSingleResult();

        Set<Book> authorbooks = author2.getBooks();

        for (Book s : authorbooks) {
            System.out.println(s);

        }

        System.out.println("klar!");


        System.out.println("påbörjar uppgift 3: ");

        List<Reader> sadred = em.createQuery("SELECT r FROM Reader r JOIN r.books b WHERE b.title = :bookTitle", Reader.class)
                .setParameter("bookTitle", theBook)
                .getResultList();

        for (Reader r : sadred) {
            System.out.println(r);
        }

        Book pickBook = em.find(Book.class, 1);

        System.out.println("klar!");

        System.out.println("påbörjar uppgift 3: ");

        List<Reader> readerContain = em.createNamedQuery("Reader.CheckIfBooksExist", Reader.class).setParameter("bookTitle", pickBook).getResultList();

        for (Reader r : readerContain) {
            System.out.println(r);
        }

        System.out.println("klar!");

        System.out.println("påbörjar uppgift 4: ");

        List<Author> readAuthor = em.createNamedQuery("Author.ifBookHasBeenRead", Author.class).getResultList();

        for (Author a : readAuthor) {
            System.out.println("this author has readers: " + a.getName());


        }

        System.out.println("klar!");

        System.out.println("påbörjar uppgift 5: ");

        List<Object[]> authors = em.createNamedQuery("Author.howManyBooks", Object[].class).getResultList();

        for (Object[] a : authors) {


            System.out.println("författaren: " + a[0] + " har skrivit: " + a[1] + " böcker ");
        }

        System.out.println("klar!");

        System.out.println("påbörjar uppgift 6: ");

        List<Book> books = em.createNamedQuery("searchForGenres", Book.class).setParameter("specificGenre", Genre).getResultList();

        for (Book b : books) {
            System.out.println(b);

        }

        System.out.println("klar!");

        tx.commit();
        em.close();

    }

}