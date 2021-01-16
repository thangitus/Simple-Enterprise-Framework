package ui.scene;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.UsersDao;
import entity.Users;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.tool.UserManagement;
import ui.tool.SceneUtils;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static ui.Main.REGISTER_SCENE_FXML;

public class LoginSceneTemplate extends BaseSceneTemplate implements Initializable {
    @FXML
    StackPane rootPane;
    @FXML
    JFXTextField username;
    @FXML
    JFXPasswordField password;
    @FXML
    JFXButton loginButton;
    @FXML
    JFXButton registerButton;

    public void login(ActionEvent event) {
        String strUsername = this.username.getText();
        String strPassword = this.password.getText();

        // Handle not null
        if(strUsername.isEmpty() || strPassword.isEmpty()){
            SceneUtils.getInstance().showDialog(rootPane, "Login", "There are at least one field is empty");
            return;
        }

        new Thread(() -> {
            try{
                UsersDao dao = new UsersDao();
                Users user = dao.getById(strUsername);
                System.out.println((user!=null)?user.getPassword():"null");
                if(user == null || !user.getPassword().equals(strPassword)){
                    Platform.runLater(()->SceneUtils.getInstance().showDialog(rootPane, "Login", "Username or Password are incorrect"));
                } else {
                    UserManagement.getInstance().login(user);
                    SceneUtils.getInstance().switchScreen(this.rootPane, "/fxml/%demo%Scene.fxml", 100);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(()->SceneUtils.getInstance().showDialog(rootPane, "Login", "Login failed"));
            }
        }).start();
    }

    @FXML
    private void register() {
        SceneUtils.getInstance().switchScreen(this.rootPane, REGISTER_SCENE_FXML, 100);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(()->username.requestFocus());
    }
}
