package client.clientUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartPageUI extends Application{

    public void start(Stage primaryStage) {
        try {
            Stage mainEntrance = new Stage();
            //mainEntrance.setTitle("VCampus");

            //设置界面大小(黄金分割比xs)
            mainEntrance.setWidth(900);
            mainEntrance.setHeight(556);
            //界面大小固定
            mainEntrance.setResizable(false);
            //设定窗口无边框
            mainEntrance.initStyle(StageStyle.UNDECORATED);
            //页面初始在中间
            mainEntrance.centerOnScreen();
            AnchorPane root = new AnchorPane();
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            root.getChildren().add(new LoginPageUI().LoginPage(scene));


//            AnchorPane loading = new AnchorPane();
//            loading.setPrefSize(400,400);
//            Image img_loading = new Image("/client/Image/cross.png",350, 350,true,true);
//            ImageView iv_loading = new ImageView(img_loading);
//            loading.getChildren().add(iv_loading);
//            loading.setTopAnchor(iv_loading,25.0);
//            loading.setLeftAnchor(iv_loading,25.0);
//            Scene sc = new Scene(loading);
//            loadingStage.setScene(sc);
//            loadingStage.initStyle(StageStyle.UNDECORATED);
//            loadingStage.centerOnScreen();
//            loadingStage.show();
//            loadingStage.hide();


            mainEntrance.setScene(scene);
            mainEntrance.showAndWait();



            //loadingPageThread();



        } catch(Exception e) {
            e.printStackTrace();
        }
    }

//    public void loadingPageThread(){
//        Thread loadingPage = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        Stage loadingStage = new Stage();
//                        //加载动画
//                        AnchorPane loading = new AnchorPane();
//                        loading.setPrefSize(400,400);
//                        //Image img1 = new Image("/client/Image/hospital.png",350, 350,true,true);
//                        Image img1 = new Image("/client/Image/loading.gif",350.0,350.0,true,true);
//                        ImageView iv1 = new ImageView(img1);
//                        loading.getChildren().add(iv1);
//                        loading.setTopAnchor(iv1,25.0);
//                        loading.setLeftAnchor(iv1,25.0);
//                        Scene sc = new Scene(loading);
//                        loadingStage.setScene(sc);
//                        loadingStage.initStyle(StageStyle.UNDECORATED);
//                        loadingStage.centerOnScreen();
//
//                        try {
//                            loadingStage.show();
//                        } catch ( Exception e){
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//            }
//        });
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
