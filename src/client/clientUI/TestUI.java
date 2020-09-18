package client.clientUI;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class TestUI extends Application{
    boolean open = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //小叉叉
        Button bExit = new Button();
        bExit.setStyle("-fx-background-color: #FF6347");
        //bExit.setStyle("-fx-background-image:url('/Image/blackCross.png');-fx-background-size: 45px;");
        bExit.setPrefHeight(25);
        bExit.setPrefWidth(25);
        bExit.setCursor(Cursor.HAND);

        //蒙板
        Rectangle mask = new Rectangle();
        mask.setHeight(713);
        mask.setWidth(1180);
        mask.setStyle("-fx-background-color: #000000");
        mask.setOpacity(0.2);
        mask.setVisible(false);


        //伸缩栏
        //小憨堡儿
        JFXHamburger h3 = new JFXHamburger();
        HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(h3);
        burgerTask2.setRate(-1);
        h3.setCursor(Cursor.HAND);

        JFXButton bMy = new JFXButton("个人信息");
        JFXButton bHome = new JFXButton("主页");
        JFXButton bCourse = new JFXButton("课程管理");
        JFXButton bShop = new JFXButton("商店");
        JFXButton bBank = new JFXButton("网上银行");
        JFXButton bLib = new JFXButton("图书馆");
        JFXButton bPlace = new JFXButton("场馆预约");
        JFXButton bHospital = new JFXButton("校医院");

        AnchorPane buttonRoot = new AnchorPane();
        buttonRoot.setStyle("-fx-background-color: #50a1f0");//#483D8B
        buttonRoot.setPrefWidth(400);
        buttonRoot.setPrefHeight(580);
        buttonRoot.getChildren().addAll(bMy, bHome, bCourse, bShop, bBank, bLib, bPlace, bHospital, h3);
        AnchorPane.setTopAnchor(buttonRoot, 133.0);
        AnchorPane.setLeftAnchor(buttonRoot, -278.0);

        buttonRoot.setTopAnchor(h3,10.0);
        buttonRoot.setLeftAnchor(h3,325.0);

        buttonRoot.setTopAnchor(bMy, 40.0);
        bMy.setPrefWidth(400);
        bMy.setPrefHeight(71);
        bMy.setStyle("-fx-background-color: #50a1f0");
        bMy.setTextFill(Paint.valueOf("#FFFFFF"));//白色
        bMy.getStyleClass().add("font-large");//特大号字体
        bMy.setCursor(Cursor.HAND);
        buttonRoot.setTopAnchor(bHome, 111.0);
        bHome.setPrefWidth(400);
        bHome.setPrefHeight(67);
        bHome.setStyle("-fx-background-color: #50a1f0");
        bHome.setTextFill(Paint.valueOf("#FFFFFF"));
        bHome.getStyleClass().add("font-big");
        bHome.setCursor(Cursor.HAND);
        buttonRoot.setTopAnchor(bCourse, 178.0);
        bCourse.setPrefWidth(400.0);
        bCourse.setPrefHeight(67);
        bCourse.setStyle("-fx-background-color: #50a1f0");
        bCourse.setTextFill(Paint.valueOf("#FFFFFF"));
        bCourse.getStyleClass().add("font-big");
        bCourse.setCursor(Cursor.HAND);
        buttonRoot.setTopAnchor(bShop, 245.0);
        bShop.setPrefWidth(400.0);
        bShop.setPrefHeight(67);
        bShop.setStyle("-fx-background-color: #50a1f0");
        bShop.setTextFill(Paint.valueOf("#FFFFFF"));
        bShop.getStyleClass().add("font-big");
        bShop.setCursor(Cursor.HAND);
        buttonRoot.setTopAnchor(bBank, 312.0);
        bBank.setPrefWidth(400.0);
        bBank.setPrefHeight(67);
        bBank.setStyle("-fx-background-color: #50a1f0");
        bBank.setTextFill(Paint.valueOf("#FFFFFF"));
        bBank.getStyleClass().add("font-big");
        bBank.setCursor(Cursor.HAND);
        buttonRoot.setTopAnchor(bLib, 379.0);
        bLib.setPrefWidth(400.0);
        bLib.setPrefHeight(67);
        bLib.setStyle("-fx-background-color: #50a1f0");
        bLib.setTextFill(Paint.valueOf("#FFFFFF"));
        bLib.getStyleClass().add("font-big");
        bLib.setCursor(Cursor.HAND);
        buttonRoot.setTopAnchor(bPlace, 446.0);
        bPlace.setPrefWidth(400.0);
        bPlace.setPrefHeight(67);
        bPlace.setStyle("-fx-background-color: #50a1f0");
        bPlace.setTextFill(Paint.valueOf("#FFFFFF"));
        bPlace.getStyleClass().add("font-big");
        bPlace.setCursor(Cursor.HAND);
        buttonRoot.setTopAnchor(bHospital, 513.0);
        bHospital.setPrefWidth(400.0);
        bHospital.setPrefHeight(67);
        bHospital.setStyle("-fx-background-color: #50a1f0");
        bHospital.setTextFill(Paint.valueOf("#FFFFFF"));
        bHospital.getStyleClass().add("font-big");
        bHospital.setStyle("-fx-background-image:url('/Image/hospital.png');-fx-background-size: 65px;");
        bHospital.setCursor(Cursor.HAND);


        //伸缩栏动画
        TranslateTransition openP = new TranslateTransition();
        openP.setDuration(Duration.seconds(0.4));
        openP.setNode(buttonRoot);
        openP.setFromX(0);
        openP.setToX(250);

        TranslateTransition closeP = new TranslateTransition();
        closeP.setDuration(Duration.seconds(0.4));
        closeP.setNode(buttonRoot);
        closeP.setFromX(250);
        closeP.setToX(0);

        //蒙版渐变
        //出现
        FadeTransition ftShow = new FadeTransition();
        ftShow.setNode(mask);
        ftShow.setDuration(Duration.seconds(0.4));
        ftShow.setFromValue(0);
        ftShow.setToValue(0.2);
        //消失
        FadeTransition ftFade = new FadeTransition();
        ftFade.setNode(mask);
        ftFade.setDuration(Duration.seconds(0.4));
        ftFade.setFromValue(0.2);
        ftFade.setToValue(0);



//        bExit.setOnAction(action-> {
//            Stage alertDialog = new Stage();
//            JFXDialogLayout layout = new JFXDialogLayout();
//            layout.setBody(new Label("确定要退出吗？"));
//            JFXAlert<Void> alert = new JFXAlert<>(alertDialog);
//            alert.setOverlayClose(true);
//            alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
//            alert.setContent(layout);
//            alert.initModality(Modality.NONE);
//            alert.showAndWait();
//        });


//        //导航栏
//        AnchorPane Navbar =new AnchorPane();
//        Navbar.setLayoutY(133);
//        Navbar.setPrefSize(122, 580);
//        Navbar.setStyle("-fx-background-color:#50a1f0");

        //上侧栏
        AnchorPane Topbar =new AnchorPane();
        Topbar.setPrefSize(1180, 133);
        Topbar.setStyle("-fx-background-color:#4682B4");
        Topbar.getChildren().addAll(bExit);
        //叉叉
        Topbar.setTopAnchor(bExit, 20.0);
        Topbar.setLeftAnchor(bExit, 1135.0);


        //子页面（8个子页面，
        AnchorPane childPage = new AnchorPane();
        childPage.setStyle("-fx-background-color: #123456");
        //用医院模块测试
        HospitalHomePageUI hospitalHomePage = new HospitalHomePageUI();
        childPage = hospitalHomePage.HospitalHomePage();
        childPage.setPrefSize(1058, 580);//1058*580
        childPage.setLayoutX(122);
        childPage.setLayoutY(133);


        //底板
        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(childPage, mask, buttonRoot, Topbar);



        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("TestUI_CSS.css").toExternalForm());
        primaryStage.initStyle(StageStyle.UNDECORATED);//设定窗口无边框
        primaryStage.setScene(scene);
        //1180*713
        primaryStage.setWidth(1180);
        primaryStage.setHeight(713);
        primaryStage.centerOnScreen();
        //primaryStage.setTitle("VCampus - MainFrameDemo");
        primaryStage.show();



        //小憨堡儿响应
        h3.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask2.setRate(burgerTask2.getRate() * -1);
            burgerTask2.play();
            if(open == false)
            {
                openP.play();
                mask.setVisible(true);
                ftShow.play();
                open = true;
            }
            else
            {
                closeP.play();
                //mask.setVisible(false);
                ftFade.play();
                mask.setVisible(false);
                open = false;
            }
        });


        JFXButton negativeBtn = new JFXButton("取消");
        JFXButton positiveBtn = new JFXButton("确定");

        //退出键（+弹窗确认
        bExit.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            //Stage alertDialog = new Stage();
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setBody(new Label("确定要退出吗？"));
            JFXAlert<Void> alert = new JFXAlert<>(primaryStage);
            alert.setOverlayClose(true);
            alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
            alert.setContent(layout);
            alert.initModality(Modality.NONE);
            negativeBtn.addEventHandler(MouseEvent.MOUSE_PRESSED,e1->{
                alert.hide();
            });
            positiveBtn.addEventHandler(MouseEvent.MOUSE_PRESSED,e2->{
                Platform.exit();
            });
            layout.setActions(negativeBtn, positiveBtn);

            alert.showAndWait();

            //Platform.exit();
        });
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}


