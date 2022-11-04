import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class OneToOneTest {

    private static final Logger logger = LoggerFactory.getLogger(OneToOneTest.class);
//    private EntityTransaction tx;
    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    void setUp() {
        String persistenceUnitName = "jpa-hiber-postgres-pu";
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        em = emf.createEntityManager();
//        tx = em.getTransaction();
    }

    @Test
    void executeInTransactionTest() {
        logger.info("start persistBookWithoutAtGeneratedValue");

        String info = "een inmiddels ruim... ";
        executeInTransaction(
                em -> {
                    PublisherInfo publisherInfo = new PublisherInfo(info);
                    Publisher publisher = new Publisher("A'dam", "NL", "OBB");
                    publisherInfo.setPublisher(publisher);
                    em.persist(publisherInfo);
                    em.persist(publisher);
                }
        );
        logger.info("finish persistBookWithoutAtGeneratedValue");
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {

        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Something unexpected went wrong in the test", e);
        }

    }
}
