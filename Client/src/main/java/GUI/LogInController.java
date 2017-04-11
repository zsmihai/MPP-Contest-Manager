package GUI;

import Controller.ClientController;
import Service.CategoriiVarstaService;
import Service.InscriereService;
import Service.ProbaService;
import Service.UsersService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sun.applet.Main;

import java.io.IOException;
import Exceptions.ServiceException;
public class LogInController {

    private ClientController clientController;
    private Stage thisStage;

    @FXML
    private TextField parolaTextField;

    @FXML
    private TextField utilizatorTextField;

    @FXML
    private Button okButton;

    public LogInController() {
    }

    public void initializeComponents(ClientController clientController, Stage stage) {
        this.clientController = clientController;
        this.thisStage = stage;
    }

    @FXML
    void okButtonPressed(ActionEvent event) {

        try {
            clientController.login(utilizatorTextField.getText(), parolaTextField.getText());
            FXMLLoader concursLoader = new FXMLLoader(Main.class.getResource("/GUI/ConcursView.fxml"));
            GridPane pane = concursLoader.load();
            ConcursController concursController = concursLoader.getController();
            Stage concursStage = new Stage();
            concursController.initializeComponents(clientController, thisStage, concursStage);
            thisStage.hide();
            Scene concursScene = new Scene(pane);

            concursStage.setScene(concursScene);
            concursStage.show();

        } catch (ServiceException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Operation could not be done.");
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Operation could not be done.");
            alert.setContentText("IO exception");
            alert.show();
        }
    }


}
