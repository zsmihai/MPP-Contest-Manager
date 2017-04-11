package Repository;

import Domain.Proba;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * Created by Utilizator on 14-Mar-17.
 */
@SuppressWarnings("Since15")
public class ProbeDBRepositoryTest {




    @Test
    public void add() throws Exception {
        assertEquals(repo.getAll().size(), 0);
        repo.add(new Proba(1, "triatlon"));
        assertEquals(repo.getAll().size(), 1);
        Proba new_proba = repo.add(new Proba(1, "triatlon"));
        assertEquals(repo.getAll().size(), 2);
        for(Proba proba:repo.getAll()){
            System.out.println(proba.getProba_id());
        }

    }



    @Test
    public void getAll() throws Exception {

        assertEquals(repo.getAll().size(), 0);
        Proba new_proba1 = repo.add(new Proba(1, "triatlon"));
        assertEquals(repo.getAll().size(), 1);
        Proba new_proba2 = repo.add(new Proba(2, "triatlon"));

        assertEquals(repo.getAll().size(), 2);
        ArrayList<Proba> list = new ArrayList<Proba>();
        list.add(new_proba1);
        list.add(new_proba2);
        assert(repo.getAll().containsAll(list)  && list.containsAll(repo.getAll()));
    }

    @Test
    public void delete() throws Exception {
        assertEquals(repo.getAll().size(), 0);
        Proba new_proba1 = repo.add(new Proba(1, "triatlon"));
        assertEquals(repo.getAll().size(), 1);
        Proba new_proba = repo.add(new Proba(2, "triatlon"));
        assertEquals(repo.getAll().size(), 2);
        
        repo.delete(new_proba1.getID());
        repo.delete(new_proba.getID());
        assertEquals(repo.getAll().size(), 0);

    }

    @Test
    public void update() throws Exception {

        assertEquals(repo.getAll().size(), 0);
        Proba proba= repo.add(new Proba(1, "triatlon"));
        assertEquals(repo.getAll().size(), 1);

        repo.update(proba.getID(), new Proba(1, "inot") );
        assertEquals(repo.getAll().get(0).getNume(), "inot");


        Proba new_proba = repo.add(new Proba(1, "triatlon"));
        assertEquals(repo.getAll().size(), 2);



    }

    ProbeDBRepository repo;


    @SuppressWarnings("Since15")
    @BeforeClass
    public static void beforeClass() throws ClassNotFoundException, IOException, SQLException {


        Properties serverProps = new Properties(System.getProperties());
        serverProps.load(new FileReader("bd.config"));
        System.setProperties(serverProps);
        Class.forName(System.getProperty("lab.jdbc.driver"));
        String url = System.getProperty("lab.jdbc.test_url");
        String user = System.getProperty("lab.jdbc.user");
        String password  = System.getProperty("lab.jdbc.pass");
        Connection connection = DriverManager.getConnection(url, user, password);
        ProbeDBRepository repo = new ProbeDBRepository(connection);
        for(Proba proba:repo.getAll()) {
            repo.delete(proba.getID());
        }
        assertEquals(0, repo.getAll().size());
    }


    @Before
    public void setUp() throws Exception {
        Properties serverProps = new Properties(System.getProperties());
        serverProps.load(new FileReader("bd.config"));
        System.setProperties(serverProps);
        Class.forName(System.getProperty("lab.jdbc.driver"));
        String url = System.getProperty("lab.jdbc.test_url");
        String user = System.getProperty("lab.jdbc.user");
        String password  = System.getProperty("lab.jdbc.pass");
        Connection connection = DriverManager.getConnection(url, user, password);
        repo = new ProbeDBRepository(connection);

    }

    @After
    public void tearDown() throws Exception {
        for(Proba proba:repo.getAll()){
            repo.delete(proba.getID());
        }
    }

}