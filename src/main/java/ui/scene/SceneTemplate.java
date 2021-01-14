package ui.scene;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui.viewmodel.ViewModelTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static ui.scene.SceneUtils.*;

public class SceneTemplate implements Initializable {
    @FXML
    JFXTreeTableView table;
    @FXML
    BorderPane mainContent;
    @FXML
    VBox centerPane;
    @FXML
    ScrollPane scrollPane;
    @FXML
    VBox fieldContainer;

    @FXML
    JFXTextField name;
    @FXML
    JFXTextField classroom;
    @FXML
    JFXTextField id;
    @FXML
    StackPane rootPane;
    @FXML
    JFXButton deleteButton;
    @FXML
    VBox rightPane;
    @FXML
    JFXButton addButton;
    @FXML
    JFXButton approveButton;
    @FXML
    JFXButton settingButton;
    @FXML
    JFXButton searchButton;

    private ObservableList<ViewModelTemplate> data = FXCollections.observableArrayList();

    boolean isUpdate = true;

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

    public void handleMousePressed(MouseEvent event) {
        int index = this.table.getSelectionModel().getSelectedIndex();
        this.name.setText(data.get(index).getField1());
        this.classroom.setText(data.get(index).getField2());
        this.id.setText(data.get(index).getField3());

        if (this.table.getSelectionModel().getSelectedIndex() != -1) {
            this.isUpdate = true;
            this.rightPane.setDisable(false);
            this.deleteButton.setVisible(true);
            this.approveButton.setText("Edit");
        }
    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ParallelTransition transition = getHideTransition(this.rootPane);
        transition.play();
        transition.setOnFinished(e->{
            stage.close();
        });
    }

    public void minimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ParallelTransition transition = getHideTransition(this.rootPane);
        transition.play();
        transition.setOnFinished(e->{
            stage.setIconified(true);
            this.rootPane.setScaleX(1.0);
            this.rootPane.setScaleY(1.0);
            this.rootPane.setOpacity(1.0);
        });
    }

    public void deleteRow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        boolean result = showConfirm(stage, "Confirmation", "Are you sure to delete this row?");
        if (result) {
            clearFields();
            this.rightPane.setDisable(true);
        }
    }

    public void addRow(ActionEvent event) {
        this.isUpdate = false;
        this.rightPane.setDisable(false);
        clearFields();
        this.deleteButton.setVisible(false);
        this.approveButton.setText("Add");
    }

    private void clearFields() {
        ObservableList<Node> textFields = this.fieldContainer.getChildren();
        for (Node node : textFields) {
            JFXTextField textField = (JFXTextField) node;
            textField.setText("");
        }
    }

    public void approveModify(ActionEvent event) {
        // Add or update
        if (isUpdate) {
            // update database
            showDialog(this.rootPane, "Success", "Update database success");
        } else {
            // add database
            showDialog(this.rootPane, "Success", "Add database success");
        }
    }

    public void switchScene1(MouseEvent event) {
        new Thread(() -> {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                System.out.println("Switch to scene 1");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SceneTemplate.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert root != null;
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            });
        }).start();
    }

    public void switchScene2(MouseEvent event) {
        new Thread(() -> {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                System.out.println("Switch to scene 1");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SceneTemplate.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert root != null;
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            });
        }).start();
    }

    public void switchScene3(MouseEvent event) {
        new Thread(() -> {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                System.out.println("Switch to scene 1");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SceneTemplate.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert root != null;
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            });
        }).start();
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
        this.rootPane.setCache(true);

        this.deleteButton.setGraphic(new ImageView("/images/delete.png"));
        this.searchButton.setGraphic(new ImageView("/images/search.png"));
        this.settingButton.setGraphic(new ImageView("/images/settings.png"));
        this.addButton.setGraphic(new ImageView("/images/add.png"));

        TreeTableColumn<ViewModelTemplate, String> col1 = new JFXTreeTableColumn<>("Column 1");
        col1.setSortable(false);
        col1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getField1()));

        TreeTableColumn<ViewModelTemplate, String> col2 = new JFXTreeTableColumn<>("Column 2");
        col2.setSortable(false);
        col2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getField2()));

        TreeTableColumn<ViewModelTemplate, String> col3 = new JFXTreeTableColumn<>("Column 3");
        col3.setSortable(false);
        col3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getField3()));

        TreeItem<ViewModelTemplate> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        table.setEditable(false);
        table.getColumns().setAll(col1, col2, col3);

        if (this.table.getSelectionModel().getSelectedIndex() == -1) {
            this.rightPane.setDisable(true);
        }
    }
}
