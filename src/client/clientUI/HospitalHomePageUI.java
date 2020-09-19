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

    //子页面生成
    public AnchorPane HospitalHomePage(){
        //hhp.setStyle("-fx-background-color: #D0D0D0");
        hhp.setPrefSize(1058, 580);


        //4个tab
        SplitPane tab_home_page = new SplitPane();
        Hospital_tab_home t1 = new Hospital_tab_home();
        tab_home_page = t1.tab_home_page();



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



        tab_home.setContent(tab_home_page);
//        tab_query.setContent();
//        tab_res.setContent();
//        tab_med.setContent();

        tabPane.getTabs().addAll(tab_home, tab_query, tab_res, tab_med);

        hhp.getChildren().addAll(tabPane);

        return hhp;
    }
}
