package ui.scene;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static ui.MainTemplate.LOGIN_SCENE_FXML;

public class RegisterSceneTemplate extends BaseSceneTemplate implements Initializable {
    @FXML
    StackPane rootPane;
    @FXML
    JFXTextField username;
    @FXML
    JFXPasswordField password;
    @FXML
    JFXPasswordField confirmPassword;
    @FXML
    JFXButton loginButton;
    @FXML
    JFXButton registerButton;

    @FXML
    private void login() {
        SceneUtils.getInstance().switchScreen(this.rootPane, LOGIN_SCENE_FXML, 100);
    }

    @FXML
    private void register() {
        SceneUtils.getInstance().switchScreen(this.rootPane, LOGIN_SCENE_FXML, 100);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(()->username.requestFocus());
    }
}
