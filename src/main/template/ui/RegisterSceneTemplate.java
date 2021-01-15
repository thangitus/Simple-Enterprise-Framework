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
import static ui.Main.LOGIN_SCENE_FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import ui.tool.SceneUtils;


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

    public void register(ActionEvent event) {
        String strUsername = this.username.getText();
        String strPassword = this.password.getText();
        String strConformPassword = this.confirmPassword.getText();

        // TODO: Handle conform password
        if(!strPassword.equals(strConformPassword)){
            try{
                SceneUtils.getInstance().showDialog(rootPane, "Register", "Conform password is not corrected");
                return;
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        if(strUsername.isEmpty() || strPassword.isEmpty()){
            SceneUtils.getInstance().showDialog(rootPane, "Register", "There are at least one field is empty");
            return;
        }

        new Thread(() -> {
            UsersDao dao = new UsersDao();

            // TODO: create record on database
            Users user = new Users();
            user.setUsername(strUsername);
            user.setPassword(strPassword);

            try{
                dao.insert(user);
                SceneUtils.getInstance().switchScreen(this.rootPane, LOGIN_SCENE_FXML, 100);
            } catch (Exception e){
                Platform.runLater(()->SceneUtils.getInstance().showDialog(this.rootPane, "Register", "Register is failed"));
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
