package ui.scene;

import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

public class SceneUtils  {
    // Singleton pattern
    private static SceneUtils instance;

    private SceneUtils() {}

    public static synchronized SceneUtils getInstance() {
        if (instance == null) {
            instance = new SceneUtils();
        }
        return instance;
    }

    public boolean showConfirm(Stage stage, String title, String message) {
        JFXAlert<String> alert = new JFXAlert<>(stage);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text(title));
        layout.setBody(new Text(message));

        JFXButton okButton = new JFXButton("OK");
        okButton.setDefaultButton(true);
        okButton.setOnAction(addEvent -> {
            alert.setResult("OK");
            alert.hideWithAnimation();
        });

        JFXButton cancelButton = new JFXButton("CANCEL");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(closeEvent -> alert.hideWithAnimation());

        layout.setActions(okButton, cancelButton);
        alert.setContent(layout);

        Optional<String> result = alert.showAndWait();
        return result.map(s -> s.equals("OK")).orElse(false);
    }

    public void showDialog(StackPane rootPane, String title, String message) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text(title));
        layout.setBody(new Text(message));

        JFXButton okButton = new JFXButton("OK");
        layout.setActions(okButton);

        JFXDialog dialog = new JFXDialog(rootPane, layout, JFXDialog.DialogTransition.CENTER);

        okButton.setOnAction(e-> dialog.close());

        dialog.show();
    }

    public ParallelTransition getHideTransition(Node node) {
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(node);
        scaleTransition.setDuration(new Duration(500));
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setByX(-1.0);
        scaleTransition.setByY(-1.0);
        scaleTransition.play();
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(node);
        fadeTransition.setDuration(new Duration(500));
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(scaleTransition, fadeTransition);
        return parallelTransition;
    }

    public Stage getStage(Node node) {
        return (Stage) node.getScene().getWindow();
    }

    public Scene getScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        return scene;
    }

    public void switchScreen(StackPane rootPane, String fxmlPath, long timeout) {
        new Thread(() -> {
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                SceneUtils.getInstance().showDialog(rootPane, "Something wrong", "Please again try later");
                System.err.println(e.getMessage());
            }
            Platform.runLater(() -> {
                try {
                    Scene scene = SceneUtils.getInstance().getScene(fxmlPath);
                    SceneUtils.getInstance().getStage(rootPane).setScene(scene);
                } catch (IOException e) {
                    SceneUtils.getInstance().showDialog(rootPane, "Something wrong", "Please again try later");
                    System.err.println(e.getMessage());
                }
            });
        }).start();
    }
}

