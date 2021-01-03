package gui;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import orm.SqlServer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {
    @FXML
    private StackPane root;

    @FXML
    private JFXTextField edtUsername;

    @FXML
    private JFXPasswordField edtPassword;

    @FXML
    private JFXTextField edtDatabaseUri;

    @FXML
    private JFXButton btnLogin;

    public void connectToDatabase(ActionEvent event) {
        String user = edtUsername.getText();
        String pass = edtPassword.getText();
        String baseUrl = edtDatabaseUri.getText();

        new Thread(() -> {
            SqlServer sqlServer = new SqlServer(user, pass, baseUrl);
            List<String> databases = sqlServer.connectToServer();

            if (databases.isEmpty()) {
                Platform.runLater(() -> {
                    Utils.setAlert(root, "Oops... Something wrong", "Can't connect to database");
                });
            } else {
                Platform.runLater(() -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConfigScreen.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ConfigScreen configScreen = loader.getController();
                    configScreen.setDatabaseList(databases);
                    Scene scene = new Scene(Objects.requireNonNull(root));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                });
            }
        }).start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
