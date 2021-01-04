package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainTemplate extends Application {

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

    private Map<String, Class> getDao(){
        HashMap<String, Class> res = new HashMap<>();
        // test get dao
        File folderDao = new File("C:\\Users\\nghia\\Desktop\\KHTN-4-1\\OOP\\hibernate\\src\\main\\java\\dao");

        for (String element: folderDao.list()) {
            System.out.println(element.substring(0,element.length() - 5));
//            try{
//
//                Class a = Class.forName(element.substring(0,element.length() - 5));
//                System.out.println(a.getFields().length);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
        }
        return res;
    };

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainTemplate.fxml"));
        primaryStage.setTitle("CRUD Framework version 1.0");
        primaryStage.centerOnScreen();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public static void main(String[] args) {
//        launch(args);
        new MainTemplate().getDao();
    }
}
