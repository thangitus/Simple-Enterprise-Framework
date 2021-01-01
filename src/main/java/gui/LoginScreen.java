package gui;

import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import orm.SqlServer;

import java.io.IOException;
import java.util.List;

public class LoginScreen extends Application {
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

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/Main.css").toExternalForm());
        primaryStage.setTitle("Database Connection");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void connectToDatabase() {
        String user = edtUsername.getText();
        String pass = edtPassword.getText();
        String baseUrl = edtDatabaseUri.getText();
        SqlServer sqlServer = new SqlServer(user, pass, baseUrl);
        List<String> databases = sqlServer.connectToServer();
        for (String database : databases) {
            System.out.println(database);
        }
        if (databases.isEmpty())
            setAlert("Oops... Something wrong", "Can't connect to database");
    }

    private void setAlert(String heading, String message) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(heading));
        content.setBody(new Text(message));
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("OK");
        button.setOnAction(event -> dialog.close());
        content.setActions(button);
        dialog.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
