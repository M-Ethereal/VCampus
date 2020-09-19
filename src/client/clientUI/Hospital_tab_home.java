package client.clientUI;

import client.socket.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;
import vo.Doctor;
import vo.Message;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static javafx.animation.Interpolator.EASE_BOTH;

public class Hospital_tab_home {
    private SplitPane tab_home = new SplitPane();
    private ScrollPane scp_left = new ScrollPane();//选项
    private ScrollPane scp_right = new ScrollPane();//列表

    private double pol0 = 0;
    private double pol1 = 0;
    private double pol2 = 0;
    private double pol3 = 0;
    private double pol4 = 0;
    private double pol5 = 0;
    private double pol6 = 0;
    private double wholeHeight = 0;

    private ArrayList<Doctor> doctorArrayList = new ArrayList<Doctor>();

    //0-内科 1-外科 2-皮肤科 3-耳鼻喉科 4-口腔 5-眼科 6-心理咨询
    private String checkList(Integer i){
        if(i == 0) return "内科";
        else if(i == 1) return "外科";
        else if(i == 2) return "皮肤科";
        else if(i == 3) return "耳鼻喉科";
        else if(i == 4) return "口腔";
        else if(i == 5) return "眼科";
        else if(i == 6) return "心理咨询";
        else
            return null;
    }

    //医生列表生成
    public FlowPane init() {
        Client client = new Client();
        Message message = new Message();
        message.setMessageType("DOC_QUERY_ALL");
        Message serverResponse = client.sendRequestToServer(message);
        doctorArrayList = (ArrayList<Doctor>) serverResponse.getData();

        //System.out.println("排序前"+doctorArrayList);
        Collections.sort(doctorArrayList, new Comparator<Doctor>(){
           public int compare(Doctor d1, Doctor d2) {
               return d1.getDept().compareTo(d2.getDept());
           }
        });
        //System.out.println("排序后"+doctorArrayList);

        Integer dept = 0;
        ArrayList<Node> children0 = new ArrayList<>();
        ArrayList<Node> children1 = new ArrayList<>();
        ArrayList<Node> children2 = new ArrayList<>();
        ArrayList<Node> children3 = new ArrayList<>();
        ArrayList<Node> children4 = new ArrayList<>();
        ArrayList<Node> children5 = new ArrayList<>();
        ArrayList<Node> children6 = new ArrayList<>();

        //生成n个框框
        for (int i = 0; i < doctorArrayList.size(); i++) {
            //一个框
            StackPane child = new StackPane();
            //double width = Math.random() * 100 + 100;
            double width = 200;
            child.setPrefWidth(width);
            //double height = Math.random() * 100 + 100;
            double height = 330;
            child.setPrefHeight(height);
            JFXDepthManager.setDepth(child, 1);

            //分页
            Integer dep = Integer.valueOf(doctorArrayList.get(i).getDept());
            //System.out.println();
            if(dep == 0)
            {children0.add(child); System.out.println("Suc 0");}
            if(dep == 1) //children1.add(child);
            {children1.add(child); System.out.println("Suc 1");}
            if(dep == 2) //children2.add(child);
            {children2.add(child); System.out.println("Suc 2");}
            if(dep == 3) //children3.add(child);
            {children3.add(child); System.out.println("Suc 3");}
            if(dep == 4) //children4.add(child);
            {children4.add(child); System.out.println("Suc 4");}
            if(dep == 5) //children5.add(child);
            {children5.add(child); System.out.println("Suc 5");}
            if(dep == 6) //children6.add(child);
            {children6.add(child); System.out.println("Suc 6");}


            //框里面的内容
            StackPane header = new StackPane();
            String headerColor = getDefaultColor(i % 12);
            header.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + headerColor);
            VBox.setVgrow(header, Priority.ALWAYS);
            StackPane body = new StackPane();
            //body.setMinHeight(Math.random() * 20 + 50);
            body.setMinHeight(90);
            //body的内容

            //查看
            //计算评分
            String str = doctorArrayList.get(i).getRenown();
            String[] renownSplit = str.split("/");
            Integer a = Integer.valueOf(renownSplit[0]).intValue();
            Integer b = Integer.valueOf(renownSplit[1]).intValue();
            DecimalFormat df = new DecimalFormat("0.00");//格式
            String renown = df.format((float)a/b);
            Label docInfo = new Label("医生姓名： " + doctorArrayList.get(i).getName() + "   评分： " + renown + "\n医生职位： "+ doctorArrayList.get(i).getPosition());
            docInfo.setPrefSize(180,80);
            docInfo.setLayoutX(5);
            docInfo.setLayoutY(5);
            body.getChildren().add(docInfo);
            VBox content = new VBox();
            content.getChildren().addAll(header, body);
            body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");


            //小飞机按钮
            JFXButton button = new JFXButton("");
            //小飞机按钮响应（页面跳转）
            button.addEventHandler(MouseEvent.MOUSE_PRESSED, e->{
                //要给一个docId的信息

            });

            button.setButtonType(JFXButton.ButtonType.RAISED);
            button.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
            button.setPrefSize(40, 40);
            button.setRipplerFill(Color.valueOf(headerColor));
            button.setScaleX(0);
            button.setScaleY(0);
            SVGGlyph glyph = new SVGGlyph(-1,
                    "test",
                    "M1008 6.286q18.857 13.714 15.429 36.571l-146.286 877.714q-2.857 16.571-18.286 25.714-8 4.571-17.714 4.571-6.286 "
                            + "0-13.714-2.857l-258.857-105.714-138.286 168.571q-10.286 13.143-28 13.143-7.429 "
                            + "0-12.571-2.286-10.857-4-17.429-13.429t-6.571-20.857v-199.429l493.714-605.143-610.857 "
                            + "528.571-225.714-92.571q-21.143-8-22.857-31.429-1.143-22.857 18.286-33.714l950.857-548.571q8.571-5.143 18.286-5.143"
                            + " 11.429 0 20.571 6.286z",
                    Color.WHITE);
            glyph.setSize(20, 20);
            button.setGraphic(glyph);
            button.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
                return header.getBoundsInParent().getHeight() - button.getHeight() / 2;
            }, header.boundsInParentProperty(), button.heightProperty()));
            StackPane.setMargin(button, new Insets(0, 12, 0, 0));
            StackPane.setAlignment(button, Pos.TOP_RIGHT);

            Timeline animation = new Timeline(new KeyFrame(Duration.millis(240),
                    new KeyValue(button.scaleXProperty(),
                            1,
                            EASE_BOTH),
                    new KeyValue(button.scaleYProperty(),
                            1,
                            EASE_BOTH)));
            animation.setDelay(Duration.millis(100 * i + 1000));
            animation.play();
            child.getChildren().addAll(content, button);
        }
        //masonryPane.getChildren().addAll(children);
        //Platform.runLater(() -> scp1.requestLayout());

        //JFXScrollPane.smoothScrolling(scp1);
        //System.out.println("children0.size: "+children0.size());

        Font font = new Font("微软雅黑", 24);
        FlowPane fp0 = new FlowPane();
        Label l0 = new Label("——内科——————————————");
        l0.setFont(font);
        l0.setPrefWidth(720);
        l0.setPrefHeight(50);
        fp0.getChildren().add(l0);
        fp0.getChildren().addAll(children0);
        fp0.setPadding(new Insets(30));
        fp0.setHgap(25);
        fp0.setVgap(50);
        fp0.setPrefWidth(720);
        double temp0 = Math.ceil(children0.size() / 3);
        pol0 = 0;


        FlowPane fp1 = new FlowPane();
        Label l1 = new Label("——外科——————————————");
        l1.setFont(font);
        l1.setPrefWidth(720);
        l1.setPrefHeight(50);
        fp1.getChildren().add(l1);
        fp1.getChildren().addAll(children1);
        fp1.setPadding(new Insets(30));
        fp1.setHgap(25);
        fp1.setVgap(50);
        fp1.setPrefWidth(720);
        double temp1 = Math.ceil(children1.size() / 3);
        pol1 = (temp0+1) * 380 + 50 + 50;

        //wholeHeight = (temp1+1)*380 + 50 + 50;
        FlowPane fp2 = new FlowPane();
        Label l2 = new Label("——皮肤科—————————————");
        l2.setFont(font);
        l2.setPrefWidth(720);
        l2.setPrefHeight(50);
        fp2.getChildren().add(l2);
        fp2.getChildren().addAll(children2);
        fp2.setPadding(new Insets(30));
        fp2.setHgap(25);
        fp2.setVgap(50);
        fp2.setPrefWidth(720);
        double temp2 = Math.ceil(children2.size() / 3);
        System.out.println(children1.size());
        System.out.println(temp1);
        System.out.println(pol1);
        pol2 = pol1 + (temp1+1) * 380 + 50 + 50;

        FlowPane fp3 = new FlowPane();
        Label l3 = new Label("——耳鼻喉科————————————");
        l3.setFont(font);
        l3.setPrefWidth(720);
        l3.setPrefHeight(50);
        fp3.getChildren().add(l3);
        fp3.getChildren().addAll(children3);
        fp3.setPadding(new Insets(30));
        fp3.setHgap(25);
        fp3.setVgap(50);
        fp3.setPrefWidth(720);
        double temp3 = Math.ceil(children3.size() / 3);
        pol3 = pol2 + (temp2+1) * 380 + 50 + 50;

        FlowPane fp4 = new FlowPane();
        Label l4 = new Label("——口腔科—————————————");
        l4.setFont(font);
        l4.setPrefWidth(720);
        l4.setPrefHeight(50);
        fp4.getChildren().add(l4);
        fp4.getChildren().addAll(children4);
        fp4.setPadding(new Insets(30));
        fp4.setHgap(25);
        fp4.setVgap(50);
        fp4.setPrefWidth(720);
        double temp4 = Math.ceil(children4.size() / 3);
        pol4 = pol3 + (temp3+1) * 380 + 50 + 50;

        FlowPane fp5 = new FlowPane();
        Label l5 = new Label("——眼科——————————————");
        l5.setFont(font);
        l5.setPrefWidth(720);
        l5.setPrefHeight(50);
        fp5.getChildren().add(l5);
        fp5.getChildren().addAll(children5);
        fp5.setPadding(new Insets(30));
        fp5.setHgap(25);
        fp5.setVgap(50);
        fp5.setPrefWidth(720);
        double temp5 = Math.ceil(children5.size() / 3);
        pol5 = pol4 + (temp4+1) * 380 + 50 + 50;

        FlowPane fp6 = new FlowPane();
        Label l6 = new Label("——心理咨询————————————");
        l6.setFont(font);
        l6.setPrefWidth(720);
        l6.setPrefHeight(50);
        fp6.getChildren().add(l6);
        fp6.getChildren().addAll(children6);
        fp6.setPadding(new Insets(30));
        fp6.setHgap(25);
        fp6.setVgap(50);
        fp6.setPrefWidth(720);
        double temp6 = Math.ceil(children6.size() / 3);
        pol6 = pol5 + (temp5+1) * 380 + 50 + 50;
        wholeHeight = pol6 + (temp6+1)*380 + 50 + 50 -580;

        FlowPane fp = new FlowPane();
        //fp.setPadding(new Insets(30));
        fp.setPrefWidth(720);
        fp.getChildren().addAll(fp0, fp1, fp2, fp3, fp4, fp5, fp6);

        System.out.println(fp0.heightProperty());
        System.out.println(pol0 +" " +pol1+ " " + pol2 + " "+ pol3);
        return fp;
    }

    //选颜色
    private String getDefaultColor(int i) {
        String color = "#FFFFFF";
        switch (i) {
            case 0:
                color = "#8F3F7E";
                break;
            case 1:
                color = "#B5305F";
                break;
            case 2:
                color = "#CE584A";
                break;
            case 3:
                color = "#DB8D5C";
                break;
            case 4:
                color = "#DA854E";
                break;
            case 5:
                color = "#E9AB44";
                break;
            case 6:
                color = "#FEE435";
                break;
            case 7:
                color = "#99C286";
                break;
            case 8:
                color = "#01A05E";
                break;
            case 9:
                color = "#4A8895";
                break;
            case 10:
                color = "#16669B";
                break;
            case 11:
                color = "#2F65A5";
                break;
            case 12:
                color = "#4E6A9C";
                break;
            default:
                break;
        }
        return color;
    }


    private AnchorPane details(String docId){
        AnchorPane dt = new AnchorPane();


        return dt;
    }


    public SplitPane tab_home_page(){
        //#B2DFEE
        VBox vBox_dept = new VBox();
        //AnchorPane vBox_dept = new AnchorPane();
        JFXButton bN = new JFXButton("内科");
        //vBox_dept.setTopAnchor(bN, 50.0);
        bN.setPrefWidth(300);
        bN.setPrefHeight(100);
        bN.setStyle("-fx-background-color: #234567");
        bN.setTextFill(Paint.valueOf("#FFFFFF"));//白色
        bN.setCursor(Cursor.HAND);
        bN.getStyleClass().add("font-big");
        JFXButton bW = new JFXButton("外科");
        //vBox_dept.setTopAnchor(bW, 150.0);
        bW.setPrefWidth(300);
        bW.setPrefHeight(100);
        bW.setStyle("-fx-background-color: #345678");
        bW.setTextFill(Paint.valueOf("#FFFFFF"));//白色
        bW.setCursor(Cursor.HAND);
        bW.getStyleClass().add("font-big");
        JFXButton bPF = new JFXButton("皮肤科");
        //vBox_dept.setTopAnchor(bPF, 250.0);
        bPF.setPrefWidth(300);
        bPF.setPrefHeight(100);
        bPF.setStyle("-fx-background-color: #456789");
        bPF.setTextFill(Paint.valueOf("#FFFFFF"));//白色
        bPF.setCursor(Cursor.HAND);
        bPF.getStyleClass().add("font-big");
        JFXButton bEBH = new JFXButton("耳鼻喉科");
        //vBox_dept.setTopAnchor(bEBH, 350.0);
        bEBH.setPrefWidth(300);
        bEBH.setPrefHeight(100);
        bEBH.setStyle("-fx-background-color: #56789A");
        bEBH.setTextFill(Paint.valueOf("#FFFFFF"));//白色
        bEBH.setCursor(Cursor.HAND);
        bEBH.getStyleClass().add("font-big");
        JFXButton bKQ = new JFXButton("口腔");
        //vBox_dept.setTopAnchor(bKQ, 450.0);
        bKQ.setPrefWidth(300);
        bKQ.setPrefHeight(100);
        bKQ.setStyle("-fx-background-color: #6789AB");
        bKQ.setTextFill(Paint.valueOf("#FFFFFF"));//白色
        bKQ.setCursor(Cursor.HAND);
        bKQ.getStyleClass().add("font-big");
        JFXButton bY = new JFXButton("眼科");
        //vBox_dept.setTopAnchor(bY, 550.0);
        bY.setPrefWidth(300);
        bY.setPrefHeight(100);
        bY.setStyle("-fx-background-color: #789ABC");
        bY.setTextFill(Paint.valueOf("#FFFFFF"));//白色
        bY.setCursor(Cursor.HAND);
        bY.getStyleClass().add("font-big");
        JFXButton bXL = new JFXButton("心理咨询");
        //vBox_dept.setTopAnchor(bXL, 650.0);
        bXL.setPrefWidth(300);
        bXL.setPrefHeight(100);
        bXL.setStyle("-fx-background-color: #89ABCD");
        bXL.setTextFill(Paint.valueOf("#FFFFFF"));//白色
        //vBox_dept.setTopAnchor(bN, 750.0);
        bXL.setCursor(Cursor.HAND);
        bXL.getStyleClass().add("font-big");


        //double scpHeight = scp_right.getMaxHeight();
        bN.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            double i = pol0/wholeHeight;
            scp_right.setVvalue(i);
        });

        bW.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            double i = pol1/wholeHeight;
            System.out.println(i);
            //System.out.println();
            scp_right.setVvalue(i);
        });

        bPF.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            double i = pol2/wholeHeight;
            scp_right.setVvalue(i);
        });

        bEBH.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            double i = pol3/wholeHeight;
            scp_right.setVvalue(i);
        });

        bKQ.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            double i = pol4/wholeHeight;
            scp_right.setVvalue(i);
        });

        bY.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            double i = pol5/wholeHeight;
            scp_right.setVvalue(i);
        });

        bXL.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            double i = pol6/wholeHeight;
            scp_right.setVvalue(i);
        });


        //左边的滚轮
        vBox_dept.getChildren().addAll(bN, bW, bPF, bEBH, bKQ, bY, bXL);
        vBox_dept.getStylesheets().add(getClass().getResource("MainFrameUI_CSS.css").toExternalForm());
        scp_left.setContent(vBox_dept);

        //右边的滚轮
        //AnchorPane ap = init();
        FlowPane f = init();
        scp_right.setContent(f);
        //scp_right.setPrefWidth(758);

        StackPane sp_left = new StackPane();
        sp_left.getChildren().add(scp_left);
        sp_left.setMaxWidth(315);
        sp_left.setMinWidth(300);
        StackPane sp_right = new StackPane();
        sp_right.getChildren().add(scp_right);

        //合在一起作为一个tab
        tab_home.getItems().addAll(sp_left,sp_right);
        //tab.setLeftAnchor(scp_right, 330.0);

        return tab_home;
    }
}
