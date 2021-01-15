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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static ui.tool.SceneUtils.*;

public class LoginSceneTemplate implements Initializable {
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

    public void login(ActionEvent event) {
        String strUsername = this.username.getText();
        String strPassword = this.password.getText();

        // Handle not null
        if(strUsername.isEmpty() || strPassword.isEmpty()){
            showDialog(rootPane, "Login", "There are at least one field is empty");
            return;
        }

        new Thread(() -> {
            try{
                UsersDao dao = new UsersDao();
                Users user = dao.getById(strUsername);
                System.out.println((user!=null)?user.getPassword():"null");
                if(user == null || !user.getPassword().equals(strPassword)){
                    Platform.runLater(()->showDialog(rootPane, "Login", "Username or Password are incorrect"));
                } else {
                    UserManagement.getInstance().login(user);
                    Platform.runLater(() -> {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/donphuckhaoScene.fxml"));
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
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(()->showDialog(rootPane, "Login", "Login failed"));
            }
        }).start();
    }

    public void register(ActionEvent event) {
        new Thread(() -> {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterSceneTemplate.fxml"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}