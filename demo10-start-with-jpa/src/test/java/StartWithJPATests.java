import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StartWithJPATests {
    @Test
    void persistBookWithoutAtGeneratedValue() {
        String persistenceUnitName = "jpa-hiber-postgres-pu";
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(new Book(1, "John", "Surviving in the Java Logging Hell"));
        tx.commit();
    }

//    @Test
//    @DisplayName("Select all the entities of type Book")
//    void createJPQLQueryOnEntityBook() {
//
//        var numberOfBooksSaved = 2;
//
//        addBooksToTable(numberOfBooksSaved);
//
//        var queryString = "SELECT b FROM Book b";
//        var jpqlQuery = em.createQuery(queryString, Book.class);
//        var books = jpqlQuery.getResultList();
//        var numberOfBooksRetrieved = books.size();
//
//        assertThat(numberOfBooksRetrieved).isEqualTo(numberOfBooksSaved);
//    }
}
