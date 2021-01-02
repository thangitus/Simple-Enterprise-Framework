package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gradleGenerate.GradleGen;
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
import orm.SqlDatabase;
import orm.SqlServer;
import orm.Table;
import orm.config.PersistenceConfig;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
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

    private SqlServer sqlServer;
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
        String databaseName = databaseComboBox.getSelectionModel().getSelectedItem();
        SqlDatabase sqlDatabase = sqlServer.connectToDatabase(databaseName);
        String pathDest = destinationInput.getText() + databaseName;
        File fileDest = new File(pathDest);
        if (sqlDatabase != null) {
            sqlDatabase.generate(fileDest);
        }

        List<String> entityClasses = new ArrayList<>();

        sqlDatabase.getTableList().stream().map(Table::getClassName).forEach(entityClasses::add);

        PersistenceConfig persistenceConfig =
                new PersistenceConfig(entityClasses, sqlServer.getUser(), sqlServer.getPassword(), SqlServer.className,
                                      sqlServer.getBaseUrl() + "/" + databaseName);
        persistenceConfig.generate(new File(pathDest + "\\persistence.xml"));

        GradleGen gradleGen = new GradleGen();
        gradleGen.generate(fileDest);

    }

    ObservableList<String> databaseList;

    public void setDatabaseList(List<String> databaseList) {
        databaseComboBox.setItems(FXCollections.observableArrayList(databaseList));
    }

    public void setSqlServer(SqlServer sqlServer) {
        this.sqlServer = sqlServer;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseComboBox.setItems(databaseList);
    }
}