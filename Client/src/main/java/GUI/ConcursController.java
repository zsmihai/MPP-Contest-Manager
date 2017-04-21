package GUI;

import Controller.ClientController;
import Domain.CategorieVarsta;
import Domain.Inscriere;
import Domain.Proba;
import Utils.Observer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import Exceptions.ServiceException;
import javafx.util.Duration;

public class ConcursController implements Observer{

    private ClientController clientController;
    private Stage parentStage;
    private Stage thisStage;
    public ConcursController() {
    }



    @FXML
    public void initialize(){

    }

    public void initializeComponents(final ClientController clientController, Stage parentStage, Stage concursStage){
        this.parentStage=parentStage;
        this.thisStage = concursStage;
        this.clientController=clientController;
        clientController.addObserver(this);


        Timeline tick = TimelineBuilder.create()//creates a new Timeline
                .keyFrames(
                        new KeyFrame(
                                new Duration(30),//This is how often it updates in milliseconds
                                new EventHandler<ActionEvent>() {
                                    public void handle(ActionEvent t) {
                                        clientController.invalidated_update();
                                    }
                                }
                        )
                )
                .cycleCount(Timeline.INDEFINITE)
                .build();



        this.probeTableView.setItems(FXCollections.observableArrayList(clientController.getAllProbe()));
        probaNumeTableColumn.setCellValueFactory(new PropertyValueFactory<Proba, String>("nume"));

        varstaMinimaTableColumn.setCellValueFactory(new PropertyValueFactory<CategorieVarsta, Integer>("minim_varsta"));
        varstaMaximaTableColumn.setCellValueFactory(new PropertyValueFactory<CategorieVarsta, Integer>("maxim_varsta"));

        participantNumeTableColumn.setCellValueFactory(new PropertyValueFactory<Inscriere, String>("participant_nume"));
        participantVarstaTableColumn.setCellValueFactory(new PropertyValueFactory<Inscriere, Integer>("participant_varsta"));

        this.probeTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Proba>() {
            @Override
            public void changed(ObservableValue<? extends Proba> observable, Proba oldValue, Proba newValue) {
                if (newValue == null){
                    categoriiVarstaTableView.setItems(FXCollections.<CategorieVarsta>observableArrayList());
                }
                else{
                    try {
                        categoriiVarstaTableView.setItems(FXCollections.observableArrayList(clientController.getAllCategorieVarstaForProba(newValue.getProba_id())));
                    } catch (ServiceException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Eroare\n" +e.getMessage());
                        alert.showAndWait();
                    }
                }
            }
        });
        this.probeTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Proba>() {
            @Override
            public void changed(ObservableValue<? extends Proba> observable, Proba oldValue, Proba newValue) {
                if(newValue == null)
                    participantCountTextBox.setText("");
                else{
                    if(categoriiVarstaTableView.getSelectionModel().getSelectedItem()!=null){
                        try {
                            participantCountTextBox.setText(String.valueOf(clientController.countInscrieriByProbaCategorie(newValue, categoriiVarstaTableView.getSelectionModel().getSelectedItem() )));
                        } catch (ServiceException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        this.categoriiVarstaTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CategorieVarsta>() {
            @Override
            public void changed(ObservableValue<? extends CategorieVarsta> observable, CategorieVarsta oldValue, CategorieVarsta newValue) {
                if(newValue == null)
                    participantCountTextBox.setText("");
                else{
                    if(probeTableView.getSelectionModel().getSelectedItem()!=null){
                        try {
                            participantCountTextBox.setText(String.valueOf(clientController.countInscrieriByProbaCategorie(probeTableView.getSelectionModel().getSelectedItem(), newValue)));
                        } catch (ServiceException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        this.categoriiVarstaTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CategorieVarsta>() {
            @Override
            public void changed(ObservableValue<? extends CategorieVarsta> observable, CategorieVarsta oldValue, CategorieVarsta newValue) {
                if (newValue==null){
                    participantTableView.setItems(FXCollections.<Inscriere>observableArrayList());
                }
                else{
                    try {
                        participantTableView.setItems(FXCollections.observableArrayList(clientController.getInscriereByProbaCategorie(probeTableView.getSelectionModel().getSelectedItem(), newValue)));
                    } catch (ServiceException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        tick.play();
    }

    @FXML
    private TableView<CategorieVarsta> categoriiVarstaTableView;

    @FXML
    private TableColumn<CategorieVarsta, Integer> varstaMinimaTableColumn;

    @FXML
    private TableColumn<CategorieVarsta, Integer> varstaMaximaTableColumn;

    @FXML
    private TableView<Proba> probeTableView;

    @FXML
    private TableColumn<Proba, String> probaNumeTableColumn;

    @FXML
    private TextField participantCountTextBox;

    @FXML
    private TableColumn<Inscriere, String> participantNumeTableColumn;

    @FXML
    private Button participantAdaugaButton;

    @FXML
    private TableColumn<Inscriere, Integer> participantVarstaTableColumn;

    @FXML
    private TableView<Inscriere> participantTableView;

    @FXML
    private TextField participantVarstaTextBox;

    @FXML
    private TextField participantNumeTextBox;

    @FXML
    private Button logOutButton;


    void updateT(){

        if( probeTableView.getSelectionModel().getSelectedItem()==null) {
            participantCountTextBox.setText("");
        }
        else{
            if(categoriiVarstaTableView.getSelectionModel().getSelectedItem()!=null){
                try {
                    participantCountTextBox.setText(String.valueOf(clientController.countInscrieriByProbaCategorie(probeTableView.getSelectionModel().getSelectedItem(), categoriiVarstaTableView.getSelectionModel().getSelectedItem() )));
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        }

        if (categoriiVarstaTableView.getSelectionModel().getSelectedItem()==null){
            participantTableView.setItems(FXCollections.<Inscriere>observableArrayList());
        }
        else{
            try {
                participantTableView.setItems(FXCollections.observableArrayList(clientController.getInscriereByProbaCategorie(probeTableView.getSelectionModel().getSelectedItem(), categoriiVarstaTableView.getSelectionModel().getSelectedItem())));
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }

        if(categoriiVarstaTableView.getSelectionModel().getSelectedItem() == null)
            participantCountTextBox.setText("");
        else{
            if(probeTableView.getSelectionModel().getSelectedItem()!=null){
                try {
                    participantCountTextBox.setText(String.valueOf(clientController.countInscrieriByProbaCategorie(probeTableView.getSelectionModel().getSelectedItem(), categoriiVarstaTableView.getSelectionModel().getSelectedItem())));
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void participantAdaugaButtonHandler(ActionEvent event) {
        try {
            clientController.addInscriere(probeTableView.getSelectionModel().getSelectedItem(), participantNumeTextBox.getText(), Integer.valueOf(participantVarstaTextBox.getText()));

        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Dati varsta ca un numar!");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void logOutButtonHandler(ActionEvent event) {
        try {
            clientController.logout();
        } catch (ServiceException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        parentStage.show();
        thisStage.close();
    }


    @Override
    public void update() {
        updateT();
    }
}
