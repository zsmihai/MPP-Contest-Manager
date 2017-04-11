package Server;

import Networking.utils.AbstractServer;
import Networking.utils.ContestRpcConcurrentServer;
import Networking.utils.ServerException;
import Repository.CategoriiVarstaDBRepository;
import Repository.InscrieriDBRepository;
import Repository.ProbeDBRepository;
import Repository.UsersDBRepository;
import Service.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Utilizator on 05-Apr-17.
 */
public class ServerMain {

    private static int defaultPort=55555;

    public static void main(String[] args) {
        Properties serverProps = new Properties(System.getProperties());
        try {
            serverProps.load(new FileReader("bd.config"));

            System.setProperties(serverProps);

            Class.forName(System.getProperty("lab.jdbc.driver"));


            String url = System.getProperty("lab.jdbc.url");
            String user = System.getProperty("lab.jdbc.user");
            String password = System.getProperty("lab.jdbc.pass");
            Connection connection = DriverManager.getConnection(url, user, password);


            CategoriiVarstaDBRepository categoriiVarstaDBRepository = new CategoriiVarstaDBRepository(connection);
            InscrieriDBRepository inscrieriDBRepository = new InscrieriDBRepository(connection);
            ProbeDBRepository probeDBRepository = new ProbeDBRepository(connection);
            UsersDBRepository usersDBRepository = new UsersDBRepository(connection);

            CategoriiVarstaService categoriiVarstaService = new CategoriiVarstaService(categoriiVarstaDBRepository);
            InscriereService inscriereService = new InscriereService(inscrieriDBRepository, categoriiVarstaDBRepository);
            ProbaService probaService = new ProbaService(probeDBRepository);
            UsersService usersService = new UsersService(usersDBRepository);

            IServer concursServer = new ContestServer(categoriiVarstaService, inscriereService, usersService, probaService);

            int chatServerPort = defaultPort;
            try{
                chatServerPort = Integer.parseInt(serverProps.getProperty("server.port"));
            } catch (NumberFormatException ex){
                System.out.println("Wrong port format. Using default port instead " + defaultPort);
            }
            AbstractServer server = new ContestRpcConcurrentServer(chatServerPort, concursServer);

            server.start();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Can't find the properties file");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            System.out.println("Error starting server");
        }

    }

}
