package client.clientUI;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingPage {
    public Stage start(Stage primaryStage) throws Exception {
        //加载动画
        Image img_loading = new Image("/client/Image/loading.gif",350, 350,true,true);
        ImageView loading_view = new ImageView(img_loading);

        //加载动画
        AnchorPane loading = new AnchorPane(loading_view);
        loading.setPrefSize(400,300);

        Scene scene = new Scene(loading);
        scene.getStylesheets().add(getClass().getResource("MainFrameUI_CSS.css").toExternalForm());
        primaryStage.initStyle(StageStyle.UNDECORATED);//设定窗口无边框
        primaryStage.setScene(scene);
        //1180*713
        primaryStage.setWidth(1180);
        primaryStage.setHeight(713);
        primaryStage.centerOnScreen();

        return primaryStage;
    }
}
