package ui.tool;

import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;

public class SceneUtils  {
    public static boolean showConfirm(Stage stage, String title, String message) {
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
        if (result.isPresent()){
            if (result.get().equals("OK"))
                return true;
        }
        return false;
    }

    public static void showDialog(StackPane rootPane, String title, String message) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text(title));
        layout.setBody(new Text(message));

        JFXButton okButton = new JFXButton("OK");
        layout.setActions(okButton);

        JFXDialog dialog = new JFXDialog(rootPane, layout, JFXDialog.DialogTransition.CENTER);

        okButton.setOnAction(e-> dialog.close());

        dialog.show();
    }

    public static ParallelTransition getHideTransition(Node node) {
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
}

