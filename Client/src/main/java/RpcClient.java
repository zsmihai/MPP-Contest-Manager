
import Controller.ClientController;
import GUI.LogInController;
import Networking.rpcprotocol.ContestServerRpcProxy;
import Service.IServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.Properties;
/**
 * Created by Utilizator on 11-Apr-17.
 */
public class RpcClient extends Application{
    private static int defaultPort = 55556;

     private static String defaultServer = "localhost";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties clientProperties = new Properties();

        try{
            clientProperties.load(RpcClient.class.getResourceAsStream("/client.properties"));
        } catch (IOException e) {
            System.err.println("Can't find properties file");
            return;
        }

        String serverIp = clientProperties.getProperty("server.host");
        int serverPort = defaultPort;
        try{
            serverPort = Integer.parseInt(clientProperties.getProperty("server.port"));
        } catch (NumberFormatException ex){
            System.out.println("Invalid port. Using default instead.");
        }

        System.out.println("Using ip " + serverIp);
        System.out.println("Using port " + serverPort);

        IServer server = new ContestServerRpcProxy(serverIp, serverPort);
        ClientController clientController = new ClientController(server);

        FXMLLoader loader = new FXMLLoader(RpcClient.class.getResource("GUI/LoginView.fxml"));
        GridPane loginPane = loader.load();
        LogInController controller = loader.getController();
        controller.initializeComponents(clientController, primaryStage);

        Scene scene = new Scene(loginPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Concurs");
        primaryStage.show();

    }
}
