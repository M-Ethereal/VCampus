package client.clientUI;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import vo.Doctor;

import java.io.File;

/*
Student用户的主页面
功能模块：个人信息查看与维护，主页，课程管理，商店，银行，图书馆，场馆预约，医院
主题色：#00695C #004D40
*/
public class MainFrame_Doctor{
    private Doctor doctor = new Doctor();
    private ImageView iv2;

    public MainFrame_Doctor(Doctor doctor){
        this.doctor = doctor;
    }

    public void tool_changeHeadP(Image newHeadP){
        iv2.setImage(newHeadP);
    }

    boolean open = false;

    //换肤系统-十进制转十六进制
    private static String DecToHex(int n) {
        //StringBuffer s = new StringBuffer();
        StringBuilder sb = new StringBuilder(8);
        String a;
        char[] b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            sb = sb.append(b[n%16]);
            n = n/16;
        }
        a = sb.reverse().toString();
        return a;
    }

    //换肤系统-边栏颜色转换
    private static String barColor(String str){//输入16进制颜色码
        String str2 = str.substring(1,3);
        String str3 = str.substring(3,5);
        String str4 = str.substring(5,7);
        int red = Integer.parseInt(str2,16);
        int green = Integer.parseInt(str3,16);
        int blue = Integer.parseInt(str4,16);
//        ArrayList<Integer> RGB = new ArrayList<Integer>();
//        RGB.add(red);
//        RGB.add(green);
//        RGB.add(blue);

        String ans = "#";
        if(red != 0) red = red + 30;
        if(green != 0) green = green + 30;
        if(blue != 0) blue = blue + 30;

        String r,g,b;
        if(red == 0) r="00";
        else if(red > 255) r="FF";
        else r=String.valueOf(DecToHex(red));
        if(green == 0) g="00";
        else if(green > 255) g="FF";
        else g=String.valueOf(DecToHex(green));
        if(blue == 0) b="00";
        else if(blue > 255) b="FF";
        else b=String.valueOf(DecToHex(blue));
        ans = ans + r + g + b;

        return ans;
    }

    public Stage start(Stage primaryStage) throws Exception {
        //loading animation

        System.out.println("******************START LOADING*******************");

        System.out.println("******************IMAGE LOADING*******************");

        Image MainLogo = new Image("/client/Image/MainFrameTitle_white.png",250, 120,true,true);
        ImageView MainLogoView = new ImageView(MainLogo);

        System.out.println("******************IMAGE DOWN*******************");


        //小叉叉
        Button bExit = new Button();
        bExit.setStyle("-fx-background-color: #FF6347");
        //bExit.setStyle("-fx-background-image:url('/client/Image/blackCross.png');-fx-background-size: 25px;");
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

        JFXButton bMy = new JFXButton();
        JFXButton bHome = new JFXButton();
        JFXButton bHospital = new JFXButton();

        AnchorPane buttonRoot = new AnchorPane();
        buttonRoot.setStyle("-fx-background-color: #50a1f0");//#483D8B-
        buttonRoot.setPrefWidth(400);
        buttonRoot.setPrefHeight(580);
        buttonRoot.getChildren().addAll(bMy, bHome, bHospital, h3);
        AnchorPane.setTopAnchor(buttonRoot, 133.0);
        AnchorPane.setLeftAnchor(buttonRoot, -278.0);

        buttonRoot.setTopAnchor(h3,10.0);
        buttonRoot.setLeftAnchor(h3,325.0);

        buttonRoot.setTopAnchor(bMy, 40.0);
        bMy.setPrefWidth(400);
        bMy.setPrefHeight(71);
        bMy.setStyle("-fx-background-color: #50a1f0");//
        bMy.setTextFill(Paint.valueOf("#FFFFFF"));//白色
        bMy.getStyleClass().add("font-large");//特大号字体
        bMy.setCursor(Cursor.HAND);

        buttonRoot.setTopAnchor(bHome, 111.0);
        bHome.setPrefWidth(400);
        bHome.setPrefHeight(67);
        bHome.setStyle("-fx-background-color: #50a1f0");//
        bHome.setTextFill(Paint.valueOf("#FFFFFF"));
        bHome.getStyleClass().add("font-big");
        bHome.setCursor(Cursor.HAND);

        buttonRoot.setTopAnchor(bHospital, 178.0);
        bHospital.setPrefWidth(400.0);
        bHospital.setPrefHeight(67);
        bHospital.setStyle("-fx-background-color: #50a1f0");//
        bHospital.setTextFill(Paint.valueOf("#FFFFFF"));
        bHospital.getStyleClass().add("font-big");
        //bHospital.setStyle("-fx-background-image:url('/client/Image/hospital.png');-fx-background-size: 85px;");
        bHospital.setCursor(Cursor.HAND);

        //侧栏button上的布局
        //医院
        Circle cir1 = new Circle();
        AnchorPane hap = new AnchorPane();
        hap.setPrefSize(350,67);
        Image img1 = new Image("/client/Image/hospital.png",60, 60,true,true);
//        Image img1 = new Image("client/Image/checkMark.png",60, 60,true,true);
        ImageView iv1 = new ImageView(img1);
        iv1.setLayoutX(295);
        Label hosl = new Label("校医院");
        hosl.setTextFill(Paint.valueOf("#FFFFFF"));
        hosl.getStyleClass().add("font-big");
        hosl.setAlignment(Pos.CENTER);
        hosl.setLayoutX(140);
        hosl.setLayoutY(15);
        cir1.setCenterX(30);
        cir1.setCenterY(30);
        cir1.setRadius(25);
        iv1.setClip(cir1);
        hap.getChildren().addAll(hosl,iv1);
        bHospital.setGraphic(hap);

        //个人信息
        Circle cir2 = new Circle();
        AnchorPane Iap = new AnchorPane();
        Iap.setPrefSize(400,67);

        File file = new File("./src/client/Image/" + doctor.getId() + ".jpg");
        String Path = "";
        if(file.exists()==true){
            Path = "/client/Image/" + doctor.getId() + ".jpg";
        }
        else {
            Path = "/client/Image/HeadPortrait_Doctor.png";
        }

        Image img2 = new Image(Path,70, 70,false,true);

//        Image img2 = new Image("/client/Image/HeadPortrait.png",70, 70,false,true);
//        Image img1 = new Image("client/Image/checkMark.png",60, 60,true,true);
        iv2 = new ImageView(img2);
        iv2.setLayoutX(291);
        Label userl = new Label("职工信息");
        userl.setTextFill(Paint.valueOf("#FFFFFF"));
        userl.getStyleClass().add("font-large");
        userl.setAlignment(Pos.CENTER);
        userl.setLayoutX(118);
        userl.setLayoutY(15);
        cir2.setCenterX(35);
        cir2.setCenterY(33);
        cir2.setRadius(30);
        iv2.setClip(cir2);
        Iap.getChildren().addAll(userl,iv2);
        bMy.setGraphic(Iap);

        //首页
        Circle cir3 = new Circle();
        AnchorPane Hap = new AnchorPane();
        Hap.setPrefSize(400,67);
        Image img3 = new Image("/client/Image/homepage.png",60, 60,true,true);
//        Image img1 = new Image("client/Image/checkMark.png",60, 60,true,true);
        ImageView iv3 = new ImageView(img3);
        iv3.setLayoutX(295);
        Label hellol = new Label("首页");
        hellol.setTextFill(Paint.valueOf("#FFFFFF"));
        hellol.getStyleClass().add("font-big");
        hellol.setAlignment(Pos.CENTER);
        hellol.setLayoutX(150);
        hellol.setLayoutY(15);
        cir3.setCenterX(30);
        cir3.setCenterY(30);
        cir3.setRadius(25);
        iv3.setClip(cir3);
        Hap.getChildren().addAll(hellol,iv3);
        bHome.setGraphic(Hap);



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
        ftShow.setToValue(0.4);
        //消失
        FadeTransition ftFade = new FadeTransition();
        ftFade.setNode(mask);
        ftFade.setDuration(Duration.seconds(0.4));
        ftFade.setFromValue(0.4);
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
        Label welcome = new Label("欢迎！"+ doctor.getName().substring(1) +"医生");
        welcome.getStyleClass().add("font-big");
        welcome.setTextFill(Paint.valueOf("#FFFFFF"));

        JFXButton changeColor = new JFXButton("换肤");
        changeColor.setTextFill(Paint.valueOf("#FFFFFF"));
        changeColor.getStyleClass().add("font-mid");
        changeColor.setPrefSize(80,30);
        AnchorPane Topbar = new AnchorPane();
        Topbar.setPrefSize(1180, 133);
        Topbar.setStyle("-fx-background-color:#4682B4");//#09042f,#4682B4
        Topbar.getChildren().addAll(bExit, changeColor, welcome, MainLogoView);
        //叉叉
        Topbar.setTopAnchor(bExit, 20.0);
        Topbar.setLeftAnchor(bExit, 1135.0);
        //换肤
        Topbar.setTopAnchor(changeColor, 55.0);
        Topbar.setLeftAnchor(changeColor, 1050.0);
        //欢迎字
        Topbar.setTopAnchor(welcome, 60.0);
        Topbar.setLeftAnchor(welcome, 850.0);
        //主Logo
        Topbar.setTopAnchor(MainLogoView, 5.0);
        Topbar.setLeftAnchor(MainLogoView, 40.0);

        FlowPane main = new FlowPane();
        main.setVgap(20);
        main.setHgap(20);

        //javafx.scene.control.ColorPicker picker = new javafx.scene.control.ColorPicker(Color.RED);
        //picker.getStyleClass().add("button");
        //      picker.getStyleClass().add("split-button");
        //main.getChildren().add(picker);
        JFXColorPicker colorPicker = new JFXColorPicker();
        main.getChildren().add(new Label("             选择皮肤颜色："));
        main.getChildren().add(colorPicker);
//换肤系统
        changeColor.addEventHandler(MouseEvent.MOUSE_PRESSED, e ->{
            JFXDialogLayout colorChangeDialog = new JFXDialogLayout();
            colorChangeDialog.setBody(main);
            JFXAlert<Void> alertColor = new JFXAlert<>(primaryStage);
            alertColor.setOverlayClose(true);
            alertColor.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
            alertColor.setContent(colorChangeDialog);
            alertColor.initModality(Modality.NONE);
            alertColor.showAndWait();
            //Topbar.getChildren().add(pane);
            String str = colorPicker.getValue().toString();
            String strC = str.substring(2, 8);//6个数字
            String barC = barColor(str.substring(1,8));
            //System.out.println(str+" "+strC);
            Topbar.setStyle("-fx-background-color: #" + strC);
            buttonRoot.setStyle("-fx-background-color:" + barC);
            bMy.setStyle("-fx-background-color:" + barC);
            bHome.setStyle("-fx-background-color:" + barC);
            bHospital.setStyle("-fx-background-color:" + barC);
        });



        //子页面（3个子页面
        HelloPageUI helloPageUI = new HelloPageUI();
        MyInfoHomePageUI_Doctor myInfoHomePageUI = new MyInfoHomePageUI_Doctor(doctor, this);
        HospitalHomePageUI_Doctor hospitalHomePageUI = new HospitalHomePageUI_Doctor(doctor);


//        AnchorPane childPageHello = new AnchorPane();
//        childPageHello.setStyle("-fx-background-color: #012345");
//        childPageHello = helloPageUI.HelloPage();
//        childPageHello.setPrefSize(1058, 580);//1058*580
//        childPageHello.setLayoutX(122);
//        childPageHello.setLayoutY(133);

        //个人信息
        AnchorPane childPageMy = new AnchorPane();
        childPageMy = myInfoHomePageUI.infoHomePage();
        childPageMy.setStyle("-fx-background-color: #FFFFFF");
        childPageMy.setPrefSize(1058, 580);//1058*580
        childPageMy.setLayoutX(122);
        childPageMy.setLayoutY(133);

        //主页
        AnchorPane childPageHome = new AnchorPane();
        childPageHome = helloPageUI.HelloPage();
        childPageHome.setStyle("-fx-background-color: #FFFFFF");
        childPageHome.setPrefSize(1058, 580);//1058*580
        childPageHome.setLayoutX(122);
        childPageHome.setLayoutY(133);


        //医院
        AnchorPane childPageHos = new AnchorPane();
        childPageHos = hospitalHomePageUI.HospitalHomePage();
        childPageHos.setStyle("-fx-background-color: #FFFFFF");
        childPageHos.setPrefSize(1058, 580);//1058*580
        childPageHos.setLayoutX(122);
        childPageHos.setLayoutY(133);


        AnchorPane finalChildPageMy = childPageMy;
        AnchorPane finalChildPageHome = childPageHome;
        AnchorPane finalChildPageHos = childPageHos;


        bMy.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            finalChildPageMy.setVisible(true);
            finalChildPageHome.setVisible(false);
            finalChildPageHos.setVisible(false);
        });

        bHome.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            finalChildPageMy.setVisible(false);
            finalChildPageHome.setVisible(true);
            finalChildPageHos.setVisible(false);
        });

        bHospital.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            finalChildPageMy.setVisible(false);
            finalChildPageHome.setVisible(false);
            finalChildPageHos.setVisible(true);
        });



        //底板
        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(finalChildPageHos, finalChildPageMy, finalChildPageHome, mask, buttonRoot, Topbar);


        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("MainFrameUI_CSS.css").toExternalForm());
        primaryStage.initStyle(StageStyle.UNDECORATED);//设定窗口无边框
        primaryStage.setScene(scene);
        //1180*713
        primaryStage.setWidth(1180);
        primaryStage.setHeight(713);
        primaryStage.centerOnScreen();
        //primaryStage.setTitle("VCampus - MainFrameDemo");


        System.out.println("***************LOADING SUCCESSFUL*************");
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
                ftFade.setOnFinished(event -> {mask.setVisible(false);});
                open = false;
            }
        });


        JFXButton negativeBtn = new JFXButton("取消");
        JFXButton positiveBtn = new JFXButton("确定");

        //退出键（+弹窗确认
        bExit.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            //Stage alertDialog = new Stage();
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setBody(new Label("      确定要退出吗？"));
            JFXAlert<Void> alert = new JFXAlert<>(primaryStage);
            alert.setOverlayClose(true);
            alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
            alert.setContent(layout);
            alert.initModality(Modality.NONE);
            negativeBtn.addEventHandler(MouseEvent.MOUSE_PRESSED,e1->{
                alert.hide();
            });
            positiveBtn.addEventHandler(MouseEvent.MOUSE_PRESSED,e2->{
                System.exit(0);
            });
            alert.show();

            layout.setActions(negativeBtn, positiveBtn);
        });

        return primaryStage;
    }

//    public static void main(String[] args)
//    {
//        launch(args);
//    }
}


