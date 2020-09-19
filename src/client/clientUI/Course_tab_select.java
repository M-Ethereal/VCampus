package client.clientUI;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import util.BorderedTitledPane;

public class Course_tab_select {

    public Tab TabSelectCourse() {
        Tab tab = new Tab();
        tab.setText("学生选课");
        ScrollPane scp = new ScrollPane();
//        scp.setStyle("-fx-padding: 50;" + "-fx-border-style: dotted inside;"
//                + "-fx-border-width: 5;" + "-fx-border-insets: 5;"
//                + "-fx-border-radius: 10;" + "-fx-border-color: black;");


        //这里后端回来的是一个课程list，循环次数为list大小，这里假设取10
        VBox vbox = new VBox();
//        vbox.setSpacing(40);
//        vbox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
//                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
//                + "-fx-border-radius: 5;" + "-fx-border-color: black;");

        for(int i = 0; i < 10; i++) {
            AnchorPane coursebutton = new AnchorPane();
            coursebutton.setPrefSize(700, 200);
            Label CourseName = new Label("计算机组成原理");
            Label TeacherName = new Label("授课教师：国林爸爸");
            Label CourseCredit = new Label("学分：4 学分");
            Label CourseTime = new Label("时间：1-16周，星期一下午6~8节");
            Label CoursePlace = new Label("地点：J6-101");
            CourseName.setStyle("-fx-font-size:30px");
            CourseName.setLayoutX(50);
            CourseName.setLayoutY(50);

            TeacherName.setStyle("-fx-font-size:15px");
            TeacherName.setLayoutX(50);
            TeacherName.setLayoutY(110);

            CourseTime.setStyle("-fx-font-size:15px");
            CourseTime.setLayoutX(400);
            CourseTime.setLayoutY(50);

            CourseCredit.setStyle("-fx-font-size:15px");
            CourseCredit.setLayoutX(400);
            CourseCredit.setLayoutY(90);

            CoursePlace.setStyle("-fx-font-size:15px");
            CoursePlace.setLayoutX(400);
            CoursePlace.setLayoutY(130);

            JFXButton button = new JFXButton();

            TitledPane test = new TitledPane();
            //FlowPane
            //button.getStyleClass().add("button-raised");
            button.setStyle("-jfx-button-type: RAISED;-fx-background-color: rgb(245,245,245);");
            button.setPrefSize(700, 200);

            JFXButton positiveBtn = new JFXButton("确定");
            JFXButton negativeBtn = new JFXButton("取消");
            //选择课程
            button.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
                JFXDialogLayout layout = new JFXDialogLayout();
                layout.setBody(new Label("确定选择 " + CourseName.getText() +" 吗？"));
                JFXAlert<Void> alert = new JFXAlert<>();
                alert.setHeight(100);
                alert.setOverlayClose(true);
                alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert.setContent(layout);
                alert.initModality(Modality.NONE);
                negativeBtn.addEventHandler(MouseEvent.MOUSE_PRESSED,e1->{
                    alert.hide();
                });
                positiveBtn.addEventHandler(MouseEvent.MOUSE_PRESSED,e2->{
                    System.out.println("选择这门课");
                    alert.hide();
                });
                alert.show();

                layout.setActions(negativeBtn, positiveBtn);
            });

            coursebutton.getChildren().addAll(button,CourseName,TeacherName,CourseTime,CourseCredit,CoursePlace);
            vbox.getChildren().add(coursebutton);
        }
        vbox.setSpacing(50);
        vbox.setLayoutX(179);
        vbox.setLayoutY(20);
        //AnchorPane apInner = new AnchorPane();
        //apInner.getChildren().add(vbox);
        //apInner.setTopAnchor(vbox, 20.0);
        BorderedTitledPane btp = new BorderedTitledPane("可选课程",vbox);
        btp.getStylesheets().add(getClass().getResource("BorderedTitledPane_CSS.css").toExternalForm());
        btp.setTitleAlignment(Pos.TOP_LEFT);
        AnchorPane ap = new AnchorPane();
        ap.setTopAnchor(btp, 70.0);
        ap.setLeftAnchor(btp, 40.0);
        ap.getChildren().add(btp);
        scp.setContent(ap);
        tab.setContent(scp);
        return tab;
    }
}
