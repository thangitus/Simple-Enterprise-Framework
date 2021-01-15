package ui.scene;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.viewmodel.ViewModelTemplate;

import java.net.URL;
import java.util.ResourceBundle;

import static ui.MainTemplate.LOGIN_SCENE_FXML;
import static ui.MainTemplate.SCENE_FXML;

public class SceneTemplate extends BaseSceneTemplate implements Initializable {
    @FXML
    private JFXTreeTableView<ViewModelTemplate> table;
    @FXML
    private VBox fieldContainer;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField classroom;
    @FXML
    private JFXTextField id;
//    @FXML
//    private StackPane rootPane;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private VBox rightPane;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton approveButton;
    @FXML
    private JFXButton settingButton;
    @FXML
    private JFXButton searchButton;
    @FXML
    private ImageView profilePicture;

    private final ObservableList<ViewModelTemplate> data = FXCollections.observableArrayList();

    private final ContextMenu contextMenu = new ContextMenu();

    boolean isUpdate = true;

    // Handle select table
    @FXML
    private void handleMousePressed() {
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

    @FXML
    private void deleteRow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        boolean result = SceneUtils.getInstance().showConfirm(stage, "Confirmation", "Are you sure to delete this row?");
        if (result) {
            clearFields();
            this.rightPane.setDisable(true);
        }
    }

    @FXML
    private void addRow() {
        this.isUpdate = false;
        this.rightPane.setDisable(false);
        this.clearFields();
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

    @FXML
    private void approveModify() {
        // Add or update
        if (isUpdate) {
            // update database
            SceneUtils.getInstance().showDialog(this.rootPane, "Success", "Update database success");
        } else {
            // add database
            SceneUtils.getInstance().showDialog(this.rootPane, "Success", "Add database success");
        }
    }

    @FXML
    private void switchScene1() {
        SceneUtils.getInstance().switchScreen(this.rootPane, SCENE_FXML, 100);
    }

    @FXML
    private void switchScene2() {
        SceneUtils.getInstance().switchScreen(this.rootPane, SCENE_FXML, 100);
    }

    @FXML
    private void switchScene3() {
        SceneUtils.getInstance().switchScreen(this.rootPane, SCENE_FXML, 100);
    }

    @FXML
    private void showContextMenu(MouseEvent event) {
        if (!this.contextMenu.isShowing()) {
            this.contextMenu.show(this.profilePicture, event.getScreenX(), event.getScreenY());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Init context menu
        MenuItem logout = new MenuItem("Logout");
        logout.setOnAction(this::logOut);
        this.contextMenu.getItems().add(logout);

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

    private void initData() {
        data.add(new ViewModelTemplate("1", "Nguyen Tuan Dat", "10"));
        data.add(new ViewModelTemplate("2", "Ngo Minh Nghia", "10"));
        data.add(new ViewModelTemplate("3", "Nguyen Van Thang", "10"));
        data.add(new ViewModelTemplate("4", "Nguyen Bao Phat", "10"));
    }

    private void logOut(ActionEvent event) {
        SceneUtils.getInstance().switchScreen(this.rootPane, LOGIN_SCENE_FXML, 100);
    }
}
