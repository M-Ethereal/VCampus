package client.clientUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Course_tab_timetable {
    public Tab TabTimeTable() {
        Tab tab = new Tab();
        tab.setText("学生课表");
        StackPane sp = new StackPane();
        AnchorPane ap = new AnchorPane();
        sp.getChildren().add(ap);

        ImageView Timetable = new ImageView("client/Image/timetable.png");
        Timetable.setPreserveRatio(true);
        Timetable.setFitHeight(500);
        Timetable.setLayoutX(200);
        Timetable.setLayoutY(20);
        ap.getChildren().add(Timetable);

        //课程格子颜色
        ArrayList<String> color = new ArrayList<String>();
        color.add("#FFC0CB");
        color.add("#DDA0DD");
        color.add("#87CEFA");
        color.add("#F0E68C");
        color.add("#FFEBCD");

        //这里遍历从后端返回学生的选课课程信息
        for(int i = 0; i < 1; i++) {
            final double d = Math.random();
            final int j = (int)(d*5);
            JFXButton btn = new JFXButton("计算机组成原理\n\n任爸爸");
            btn.setWrapText(true);
            btn.setPrefSize(70, 140);
            btn.setFont(new Font("SimHei", 15));
            btn.setStyle("-fx-background-color: " +color.get(j)+ ";-fx-background-radius: 10px;-fx-text-alignment:center;");
            btn.setLayoutX(290);
            btn.setLayoutY(60);
            ap.getChildren().add(btn);

            btn.setOnMouseClicked(new EventHandler<MouseEvent>() {


                @Override
                public void handle(MouseEvent event) {
                    JFXDialogLayout dialog = new JFXDialogLayout();
                    dialog.setHeading(new Text("课程详情"));
                    dialog.setBody(new Text("上课时间："+"\n上课地点："+"\n课程学分："));
                    JFXDialog mag=new JFXDialog(sp,dialog,JFXDialog.DialogTransition.CENTER);

                    JFXButton button1=new JFXButton("关闭");
                    button1.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent event) {
                            mag.close();
                        }
                    });
                    dialog.setActions(button1);
                    mag.show();
                }
            });
        }

        tab.setContent(sp);
        return tab;
    }
}
