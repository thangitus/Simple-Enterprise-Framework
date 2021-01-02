package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ConfigScreen implements Initializable {
    @FXML
    private StackPane root;

    @FXML
    private AnchorPane pneCenter;

    @FXML
    private JFXButton generateButton;

    @FXML
    private JFXComboBox<String> databaseComboBox;

    @FXML
    private JFXTextField destinationInput;

    @FXML
    private JFXButton browseButton;

    @FXML
    void chooseFolder(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            destinationInput.setText(selectedDirectory.getPath());
        }
    }

    @FXML
    void generate(ActionEvent event) {
        // Do something
        Utils.setAlert(root, "Oops... Something wrong", "Can't process database");
    }

    ObservableList<String> databaseList;

    public void setDatabaseList(List<String> databaseList) {
        databaseComboBox.setItems(FXCollections.observableArrayList(databaseList));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseComboBox.setItems(databaseList);
    }
}
