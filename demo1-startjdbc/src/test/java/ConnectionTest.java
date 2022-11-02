import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.postgresql.util.PSQLException;

import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConnectionTest {
    @Test
    @Order(1)
    void showThatTheConnectionToPostgresIsNotNull() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/cursistdb";
        String username = "cursist";
        String password = "PaSSw0rd";
        Connection connection = DriverManager.getConnection(url, username, password);
        assertThat(connection).isNotNull();
    }

    @Test
    @Order(2)
    void dropTableCursist() throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        boolean execute = statement.execute("DROP TABLE Cursist;");
        assertThat(execute).isEqualTo(false);
        /*
            Returns:
            true if the first result is a ResultSet object;
            false if it is an update count or there are no results
        */
    }

    @Test
    @Order(3)
    void createTable() throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE Cursist " +
                "(ID SERIAL PRIMARY KEY NOT NULL," +
                " NAME TEXT NOT NULL, " +
                " AGE INT NOT NULL, " +
                " COUNTRY CHAR(50) CONSTRAINT country_name_must_be_different UNIQUE " +
                ")";
        int executeUpdate = statement.executeUpdate(sql);
        assertThat(executeUpdate).isEqualTo(0);
    }

    @Test
    @Order(4)
    void insertRows() throws SQLException {
        Connection connection = createConnection();
        connection.setAutoCommit(false);
        System.out.println("Opened database successfully");
        Statement statement = connection.createStatement();

        int numberOfRowsInserted = 0;
        String sql = "INSERT INTO Cursist (NAME,AGE,COUNTRY) "
                + "VALUES('Johan',43,'Nederland');";
        numberOfRowsInserted += statement.executeUpdate(sql);
        sql = "INSERT INTO Cursist (NAME,AGE,COUNTRY) "
                + "VALUES ( 'Allen', 25, 'Texas');";
        numberOfRowsInserted += statement.executeUpdate(sql);
        assertThat(numberOfRowsInserted).isEqualTo(2);
        sql = "INSERT INTO Cursist (NAME,AGE,COUNTRY) "
                + "VALUES('Johan',43,'Spanje') RETURNING *;";
        //Note the RETURNING * clause, it returns the latest issued id value
        try {
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            assertThat(rs.getInt("id")).isEqualTo(3L);
            connection.commit();
        } catch (PSQLException e){
            connection.rollback();
        }

    }

    @Test
    @Order(5)
    void countNumberOfRowsInTableCursist() throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) NumberOfRows FROM Cursist");
        resultSet.next();
        long numberOfRows = resultSet.getLong("NumberOfRows");
        assertThat(numberOfRows).isEqualTo(3);
    }


    private Connection createConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/cursistdb";
        String username = "cursist";
        String password = "PaSSw0rd";
        return DriverManager.getConnection(url, username, password);
    }


}
