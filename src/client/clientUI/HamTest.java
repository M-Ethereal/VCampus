package client.clientUI;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HamTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        JFXHamburger h2 = new JFXHamburger();
        HamburgerBasicCloseTransition burgerTask1 = new HamburgerBasicCloseTransition(h2);
        burgerTask1.setRate(-1);
        h2.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask1.setRate(burgerTask1.getRate() * -1);
            burgerTask1.play();
        });


        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(h2);
        Scene scene = new Scene(ap);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        //primaryStage.setTitle("VCampus - MainFrameDemo");
        primaryStage.show();
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}
