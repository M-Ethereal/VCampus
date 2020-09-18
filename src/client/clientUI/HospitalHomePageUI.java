package client.clientUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;

import static javafx.animation.Interpolator.EASE_BOTH;

public class HospitalHomePageUI {
    private AnchorPane hhp = new AnchorPane();
    //private JFXMasonryPane masonryPane;

    public FlowPane init() {
        ArrayList<Node> children = new ArrayList<>();
        //生成n个框框
        for (int i = 0; i < 40; i++) {
            //一个框
            StackPane child = new StackPane();
            //double width = Math.random() * 100 + 100;
            double width = 200;
            child.setPrefWidth(width);
            //double height = Math.random() * 100 + 100;
            double height = 330;
            child.setPrefHeight(height);
            JFXDepthManager.setDepth(child, 1);
            children.add(child);

            //框里面的内容
            StackPane header = new StackPane();
            String headerColor = getDefaultColor(i % 12);
            header.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + headerColor);
            VBox.setVgrow(header, Priority.ALWAYS);
            StackPane body = new StackPane();
            //body.setMinHeight(Math.random() * 20 + 50);
            body.setMinHeight(70);
            VBox content = new VBox();
            content.getChildren().addAll(header, body);
            body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");

            //小飞机按钮
            JFXButton button = new JFXButton("");
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
        FlowPane fp = new FlowPane();
        fp.getChildren().addAll(children);
        fp.setHgap(30);
        fp.setVgap(50);
        fp.setPrefWidth(750);
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

    public AnchorPane HospitalHomePage(){
        //hhp.setStyle("-fx-background-color: #D0D0D0");
        hhp.setPrefSize(1058, 580);
        ScrollPane scp_left = new ScrollPane();//选项
        ScrollPane scp_right = new ScrollPane();//列表
        SplitPane tab = new SplitPane();

        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setPrefSize(1058, 580);
        Tab tab_home = new Tab();
        Tab tab_query = new Tab();
        Tab tab_res = new Tab();
        Tab tab_med = new Tab();

        tab_home.setText("医院主页");
        tab_query.setText("咨询记录");
        tab_res.setText("挂号记录");
        tab_med.setText("线上药房");

        //#B2DFEE
        VBox vBox_dept = new VBox();
        //AnchorPane vBox_dept = new AnchorPane();
        JFXButton bN = new JFXButton("内科");
        //vBox_dept.setTopAnchor(bN, 50.0);
        bN.setPrefWidth(300);
        bN.setPrefHeight(100);
        bN.setStyle("-fx-background-color: #B2DFEE");
        bN.setCursor(Cursor.HAND);
        bN.getStyleClass().add("font-big");
        JFXButton bW = new JFXButton("外科");
        //vBox_dept.setTopAnchor(bW, 150.0);
        bW.setPrefWidth(300);
        bW.setPrefHeight(100);
        bW.setStyle("-fx-background-color: #B2DFEE");
        bW.setCursor(Cursor.HAND);
        bW.getStyleClass().add("font-big");
        JFXButton bPF = new JFXButton("皮肤科");
        //vBox_dept.setTopAnchor(bPF, 250.0);
        bPF.setPrefWidth(300);
        bPF.setPrefHeight(100);
        bPF.setStyle("-fx-background-color: #B2DFEE");
        bPF.setCursor(Cursor.HAND);
        bPF.getStyleClass().add("font-big");
        JFXButton bEBH = new JFXButton("耳鼻喉科");
        //vBox_dept.setTopAnchor(bEBH, 350.0);
        bEBH.setPrefWidth(300);
        bEBH.setPrefHeight(100);
        bEBH.setStyle("-fx-background-color: #B2DFEE");
        bEBH.setCursor(Cursor.HAND);
        bEBH.getStyleClass().add("font-big");
        JFXButton bKQ = new JFXButton("口腔");
        //vBox_dept.setTopAnchor(bKQ, 450.0);
        bKQ.setPrefWidth(300);
        bKQ.setPrefHeight(100);
        bKQ.setStyle("-fx-background-color: #B2DFEE");
        bKQ.setCursor(Cursor.HAND);
        bKQ.getStyleClass().add("font-big");
        JFXButton bY = new JFXButton("眼科");
        //vBox_dept.setTopAnchor(bY, 550.0);
        bY.setPrefWidth(300);
        bY.setPrefHeight(100);
        bY.setStyle("-fx-background-color: #B2DFEE");
        bY.setCursor(Cursor.HAND);
        bY.getStyleClass().add("font-big");
        JFXButton bXL = new JFXButton("心理咨询");
        //vBox_dept.setTopAnchor(bXL, 650.0);
        bXL.setPrefWidth(300);
        bXL.setPrefHeight(100);
        bXL.setStyle("-fx-background-color: #B2DFEE");
        //vBox_dept.setTopAnchor(bN, 750.0);
        bXL.setCursor(Cursor.HAND);
        bXL.getStyleClass().add("font-big");


        bN.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            scp_right.setVvalue(0.0);
        });

        bW.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            scp_right.setVvalue(0.1);
        });

        bPF.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            scp_right.setVvalue(0.2);
        });

        bEBH.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            scp_right.setVvalue(0.3);
        });

        bKQ.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            scp_right.setVvalue(0.4);
        });

        bY.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            scp_right.setVvalue(0.5);
        });

        bXL.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            scp_right.setVvalue(0.6);
        });


        //左边的滚轮
        vBox_dept.getChildren().addAll(bN, bW, bPF, bEBH, bKQ, bY, bXL);
        vBox_dept.getStylesheets().add(getClass().getResource("TestUI_CSS.css").toExternalForm());
        scp_left.setContent(vBox_dept);

        //右边的滚轮
        //AnchorPane ap = init();
        FlowPane f = init();
        scp_right.setContent(f);
        //scp_right.setPrefWidth(758);

        StackPane sp_left = new StackPane();
        sp_left.getChildren().add(scp_left);
        sp_left.setMaxWidth(300);
        StackPane sp_right = new StackPane();
        sp_right.getChildren().add(scp_right);

        //合在一起作为一个tab
        tab.getItems().addAll(sp_left,sp_right);
        //tab.setLeftAnchor(scp_right, 330.0);

        tab_home.setContent(tab);
        tabPane.getTabs().addAll(tab_home, tab_query, tab_res, tab_med);

        hhp.getChildren().addAll(tabPane);

        return hhp;
    }
}
