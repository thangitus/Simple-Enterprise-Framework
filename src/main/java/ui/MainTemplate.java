package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainTemplate extends Application {
    public static final String LOGIN_SCENE_FXML = "/fxml/LoginSceneTemplate.fxml";
    public static final String REGISTER_SCENE_FXML = "/fxml/RegisterSceneTemplate.fxml";
    public static final String SCENE_FXML = "/fxml/SceneTemplate.fxml";

    private static int currentTab = 0;

    public static void setCurrentTab(int currentTab) {
        MainTemplate.currentTab = currentTab;
    }

    public static int getCurrentTab() {
        return MainTemplate.currentTab;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(LOGIN_SCENE_FXML));
        primaryStage.setTitle("CRUD Framework version 1.0");
        primaryStage.centerOnScreen();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
