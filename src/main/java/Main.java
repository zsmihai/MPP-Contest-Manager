
import GUI.LogInController;
import Repository.CategoriiVarstaDBRepository;
import Repository.InscrieriDBRepository;
import Repository.ProbeDBRepository;
import Repository.UsersDBRepository;
import Service.CategoriiVarstaService;
import Service.InscriereService;
import Service.ProbaService;
import Service.UsersService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Utilizator on 12-Mar-17.
 */
public class Main extends Application {

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties serverProps = new Properties(System.getProperties());
        try {
            serverProps.load(new FileReader("bd.config"));
            System.setProperties(serverProps);

            Class.forName(System.getProperty("lab.jdbc.driver"));


            String url = System.getProperty("lab.jdbc.url");
            String user = System.getProperty("lab.jdbc.user");
            String password  = System.getProperty("lab.jdbc.pass");
            Connection connection = DriverManager.getConnection(url, user, password);


            CategoriiVarstaDBRepository categoriiVarstaDBRepository=new CategoriiVarstaDBRepository(connection);
            InscrieriDBRepository inscrieriDBRepository = new InscrieriDBRepository(connection);
            ProbeDBRepository probeDBRepository = new ProbeDBRepository(connection);
            UsersDBRepository usersDBRepository = new UsersDBRepository(connection);

            CategoriiVarstaService categoriiVarstaService = new CategoriiVarstaService(categoriiVarstaDBRepository);
            InscriereService inscriereService = new InscriereService(inscrieriDBRepository, categoriiVarstaDBRepository);
            ProbaService probaService=new ProbaService(probeDBRepository);
            UsersService usersService = new UsersService(usersDBRepository);


            FXMLLoader loginLoader = new FXMLLoader(Main.class.getResource("/LogInView.fxml"));

            Stage stage = new Stage();

            Scene scene = new Scene(loginLoader.load());
            LogInController logInController = loginLoader.getController();
            logInController.initializeComponents(categoriiVarstaService, inscriereService, probaService, usersService, stage);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
