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
import orm.column.Column;
import orm.SqlDatabase;
import orm.SqlServer;
import orm.table.Table;
import orm.config.PersistenceConfig;
import ui.generator.*;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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
        sqlDatabase.generate(fileDest);

        List<String> entityClasses = new ArrayList<>();

        sqlDatabase.getTableList().stream().map(Table::getClassName).forEach(entityClasses::add);

        PersistenceConfig persistenceConfig =
                new PersistenceConfig(entityClasses, sqlServer.getUser(), sqlServer.getPassword(), SqlServer.className,
                        sqlServer.getBaseUrl() + "/" + databaseName);
        File metaInfFolder = new File(pathDest + "\\src\\main\\resources\\META-INF");
        metaInfFolder.mkdirs();
        persistenceConfig.generate(metaInfFolder);

        GradleGen gradleGen = new GradleGen(databaseName);
        gradleGen.generate(fileDest);
        sqlDatabase.tableList.remove(sqlDatabase.tableList.size() - 1);

        // Generate UI
        new File(fileDest.getAbsolutePath() + "\\src\\main\\java\\ui").mkdir();
        new File(fileDest.getAbsolutePath() + "\\src\\main\\java\\ui\\scene").mkdir();
        new File(fileDest.getAbsolutePath() + "\\src\\main\\java\\ui\\viewmodel").mkdir();
        new File(fileDest.getAbsolutePath() + "\\src\\main\\java\\ui\\tool").mkdir();
        System.out.println(fileDest.getAbsolutePath() + "\\src\\main\\java\\ui\\viewmodel");
        new ResGenerator().generate(fileDest);
        new ToolGenerator().generate(fileDest);
        new MemberGenerator(sqlDatabase.tableList.get(0).getClassName().toLowerCase()).generate(fileDest);

        List<String> listTableName =
                sqlDatabase.tableList.stream().map(Table::getClassName).collect(Collectors.toList());

        new UIGenerator(sqlDatabase.tableList.get(0).getClassName())
                .generate(new File(fileDest.getAbsoluteFile() + "\\src\\main\\java\\ui\\Main.java"));

        System.out.println(databaseName);
        for (Table table : sqlDatabase.getTableList()) {
            new FXMLGenerator(databaseName, table.getClassName(), listTableName,
                    table.getColumnList().stream().map(Column::getFieldName).collect(Collectors.toList()))
                    .generate(new File(fileDest.getAbsolutePath() + "\\src\\main\\resources\\fxml\\" +
                            table.getClassName().toLowerCase() + "Scene.fxml"));

            Map<String, String> a = new LinkedHashMap<>();
            table.getColumnList().forEach((value) -> a.put(value.fieldName, value.className));

            new SceneGenerator(
                    table.getClassName(),
                    listTableName,
                    a)
                    .generate(new File(
                            fileDest.getAbsoluteFile() + "\\src\\main\\java\\ui\\scene\\" + table.getClassName() +
                                    "Scene.java"));

            new ViewModelGenerator(table.getClassName(), table.getColumnList().stream().map(Column::getFieldName)
                    .collect(Collectors.toList())).generate(new File(
                    fileDest.getAbsolutePath() + "\\src\\main\\java\\ui\\viewmodel\\" + table.getClassName() +
                            "ViewModel.java"));
        }

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
