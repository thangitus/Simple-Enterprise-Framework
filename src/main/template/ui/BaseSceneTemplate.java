package ui.scene;

import javafx.animation.ParallelTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ui.tool.SceneUtils;

public class BaseSceneTemplate {
    @FXML
    protected StackPane rootPane;

    // Custom status bar
    private double x, y;

    @FXML
    protected void handleDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - 6 - x);
        stage.setY(event.getScreenY() - 6 - y);
    }

    @FXML
    protected void handlePressed(MouseEvent event) {
        x = event.getX();
        y = event.getY();
    }

    @FXML
    protected void handleReleased(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (stage.getY() < 0) {
            stage.setY(0);
        }
    }

    @FXML
    protected void close() {
        Stage stage = SceneUtils.getInstance().getStage(this.rootPane);
        ParallelTransition transition = SceneUtils.getInstance().getHideTransition(this.rootPane);
        transition.play();
        transition.setOnFinished(e-> stage.close());
    }

    @FXML
    protected void minimize() {
        Stage stage = SceneUtils.getInstance().getStage(this.rootPane);
        ParallelTransition transition = SceneUtils.getInstance().getHideTransition(this.rootPane);
        transition.play();
        transition.setOnFinished(e->{
            stage.setIconified(true);
            this.rootPane.setScaleX(1.0);
            this.rootPane.setScaleY(1.0);
            this.rootPane.setOpacity(1.0);
        });
    }
}
