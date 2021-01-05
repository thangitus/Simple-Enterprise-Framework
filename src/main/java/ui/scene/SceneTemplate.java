package ui.scene;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.viewmodel.ViewModelTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SceneTemplate implements Initializable {
    @FXML
    JFXTreeTableView table;

    private ObservableList<ViewModelTemplate> data = FXCollections.observableArrayList();

    // Custom status bar
    private double x, y;

    public void handleDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - 6 - x);
        stage.setY(event.getScreenY() - 6 - y);
    }

    public void handlePressed(MouseEvent event) {
        x = event.getX();
        y = event.getY();
    }

    public void handleReleased(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (stage.getY() < 0) {
            stage.setY(0);
        }
    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void minimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void switchScene1(MouseEvent event) {
        Platform.runLater(() -> {
            System.out.println("Switch to scene 1");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SceneTemplate.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(Objects.requireNonNull(root));
            scene.setFill(Color.TRANSPARENT);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        });
    }

    public void switchScene2(MouseEvent event) {
        Platform.runLater(() -> {
            System.out.println("Switch to scene 2");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SceneTemplate.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(Objects.requireNonNull(root));
            scene.setFill(Color.TRANSPARENT);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        });
    }

    public void switchScene3(MouseEvent event) {
        Platform.runLater(() -> {
            System.out.println("Switch to scene 3");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SceneTemplate.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(Objects.requireNonNull(root));
            scene.setFill(Color.TRANSPARENT);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        });
    }

    private void initData() {
        data.add(new ViewModelTemplate("1", "Nguyen Tuan Dat", "10"));
        data.add(new ViewModelTemplate("2", "Ngo Minh Nghia", "10"));
        data.add(new ViewModelTemplate("3", "Nguyen Van Thang", "10"));
        data.add(new ViewModelTemplate("4", "Nguyen Bao Phat", "10"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();

        TreeTableColumn<ViewModelTemplate, String> col1 = new JFXTreeTableColumn<>("Column 1");
        col1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getField1()));

        TreeTableColumn<ViewModelTemplate, String> col2 = new JFXTreeTableColumn<>("Column 2");
        col2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getField2()));

        TreeTableColumn<ViewModelTemplate, String> col3 = new JFXTreeTableColumn<>("Column 3");
        col3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getField3()));

        TreeItem<ViewModelTemplate> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        table.setEditable(false);
        table.getColumns().setAll(col1, col2, col3);

    }
}
