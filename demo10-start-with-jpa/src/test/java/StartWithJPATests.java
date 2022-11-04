import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class StartWithJPATests {

    private static final Logger logger = LoggerFactory.getLogger(StartWithJPATests.class);
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
    void persistBookWithoutAtGeneratedValue() {
        logger.info("start persistBookWithoutAtGeneratedValue");
        // abbreviated in here is your working code

//        String persistenceUnitName = "jpa-hiber-postgres-pu";
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
//        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(new Book("John", "Surviving in the Java Logging Hell"));
        em.persist(new Book("Joseph Heller", "Catch 22"));
        em.persist(new Book("Ernst Hemingway", "A Moveable Feast"));
        tx.commit();

        logger.info("finish persistBookWithoutAtGeneratedValue");

    }

}
