import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JPATest {

    /* The common players we need for every test. */
    private EntityTransaction tx;
    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    void setUp() {
        String persistenceUnitName = "jpa-hiber-postgres-pu";
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @Test
    @DisplayName("Select all the entities of type Book")
    void createJPQLQueryOnEntityBook() {

        var numberOfBooksSaved = 3;

        addBooksToTable();

        var queryString = "SELECT b FROM Book b";
        var jpqlQuery = em.createQuery(queryString, Book.class);
        var books = jpqlQuery.getResultList(); //verstuurt query naar de db
        var numberOfBooksRetrieved = books.size();

        assertThat(numberOfBooksRetrieved).isEqualTo(numberOfBooksSaved);
    }

    private void addBooksToTable() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(new Book("John", "Surviving in the Java Logging Hell"));
        em.persist(new Book("Joseph Heller", "Catch 22"));
        em.persist(new Book("Ernst Hemingway", "A Moveable Feast"));
        tx.commit();
    }
    
    @Test
    @DisplayName("select title of books")
    void selectTitleOfBooks() {
        addBooksToTable();

        var queryString = "SELECT b.title FROM Book b";
        var jpqlQuery = em.createQuery(queryString, String.class);
        var titles =  jpqlQuery.getResultList();

        assertThat(titles).containsExactlyInAnyOrder("Surviving in the Java Logging Hell", "Catch 22", "A Moveable Feast");

    }

    @Test
    @DisplayName("select favorite book")
    void selectFavoriteBookTest() {
        addBooksToTable();

        var queryString = "SELECT b FROM Book b WHERE b.title='A Moveable Feast'";
        var jpqlQuery = em.createQuery(queryString, Book.class);
        var book = jpqlQuery.getSingleResult();
        var title = book.getTitle();

        assertThat(title).isEqualTo("A Moveable Feast");


    }

}
