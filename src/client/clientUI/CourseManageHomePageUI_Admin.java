package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.MessageType;
import vo.Course;
import vo.Message;

public class CourseManageHomePageUI_Admin {
    private Course cs = new Course();


    //子页面生成
    public AnchorPane CourseHomePageUI(){
        AnchorPane pane = new AnchorPane();

        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setPrefSize(1058, 580);
        Tab tab_course_manage = new Tab("添加课程");
        Tab tab_exam_add = new Tab("发布考试信息");

        tab_course_manage.setContent(Course_tab_management());
        tab_exam_add = new Course_tab_exam_Admin().TabExam();

        tabPane.getTabs().addAll(tab_course_manage,tab_exam_add);

        pane.getChildren().add(tabPane);

        return pane;
    }

    public ScrollPane Course_tab_management(){
        AnchorPane pane = new AnchorPane();
        ScrollPane scp = new ScrollPane();

        Label lb1 = new Label("课程号");
        lb1.setLayoutX(300);
        lb1.setLayoutY(25);

        Label lb2 = new Label("课程名");
        lb2.setLayoutX(300);
        lb2.setLayoutY(75);

        Label lb3 = new Label("开课学期");
        lb3.setLayoutX(300);
        lb3.setLayoutY(130);

        Label lb4 = new Label("授课教师");
        lb4.setLayoutX(300);
        lb4.setLayoutY(190);

        Label lb5 = new Label("授课教室");
        lb5.setLayoutX(300);
        lb5.setLayoutY(250);

        Label lb6 = new Label("授课周次");
        lb6.setLayoutX(300);
        lb6.setLayoutY(310);

        Label lb7 = new Label("授课时间");
        lb7.setLayoutX(300);
        lb7.setLayoutY(370);

        Label lb8 = new Label("学生容纳量");
        lb8.setLayoutX(300);
        lb8.setLayoutY(430);

        Label lb9 = new Label("学生类型");
        lb9.setLayoutX(300);
        lb9.setLayoutY(485);

        Label lb10 = new Label("课程学分");
        lb10.setLayoutX(300);
        lb10.setLayoutY(535);

        JFXTextField t1 = new JFXTextField();
        t1.setPromptText("请输入课程号");
        t1.setLayoutX(500);
        t1.setLayoutY(25);

        JFXTextField t2 = new JFXTextField();
        t2.setPromptText("请输入课程名");
        t2.setLayoutX(500);
        t2.setLayoutY(75);

        JFXTextField t3 = new JFXTextField();
        t3.setPromptText("请输入开课学期");
        t3.setLayoutX(500);
        t3.setLayoutY(130);

        JFXTextField t4 = new JFXTextField();
        t4.setPromptText("请输入授课教师");
        t4.setLayoutX(500);
        t4.setLayoutY(190);

        JFXTextField t5 = new JFXTextField();
        t5.setPromptText("请输入授课教室");
        t5.setLayoutX(500);
        t5.setLayoutY(250);

        JFXTextField t6 = new JFXTextField();
        t6.setPromptText("请输入授课周次");
        t6.setLayoutX(500);
        t6.setLayoutY(310);

        JFXTextField t7 = new JFXTextField();
        t7.setPromptText("请输入授课时间");
        t7.setLayoutX(500);
        t7.setLayoutY(370);

        JFXTextField t8 = new JFXTextField();
        t8.setPromptText("请输入学生容纳量");
        t8.setLayoutX(500);
        t8.setLayoutY(430);

        JFXTextField t9 = new JFXTextField();
        t9.setPromptText("请输入学生类型");
        t9.setLayoutX(500);
        t9.setLayoutY(485);

        JFXTextField t10 = new JFXTextField();
        t10.setPromptText("请输入课程学分");
        t10.setLayoutX(500);
        t10.setLayoutY(535);

        JFXButton btn = new JFXButton("提交");
        btn.setStyle("-fx-font-size: 14px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-text-fill: WHITE;");
        btn.setLayoutX(700);
        btn.setLayoutY(500);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                cs.setCourseSemester(t3.getText());
                cs.setCourseCredit(t10.getText());
                cs.setMaximumStudents(Integer.parseInt(t8.getText()));
                cs.setEnrolledStudents(0);
                cs.setCourseLecturer(t4.getText());
                cs.setCourseName(t2.getText());
                cs.setCourseNumber(t1.getText());
                cs.setCoursePlace(t5.getText());
                cs.setCourseTerm(t6.getText());
                cs.setCourseType(t9.getText());
                cs.setCourseTime(t7.getText());
                cs.setGradeAdded(false);
                cs.setExam(false);
                cs.setExamTime("");
                cs.setExamPlace("");


                Client client = new Client();
                Message sendToServer = new Message();
                sendToServer.setMessageType(MessageType.course_add);
                sendToServer.setData(cs);
                Message serverResponse = client.sendRequestToServer(sendToServer);

                if(serverResponse.isLastOperState() == true) {
                    //提交成功
                    JFXDialogLayout layout_suc = new JFXDialogLayout();
                    layout_suc.setBody(new Label("      加课成功"));
                    JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) btn.getScene().getWindow());
                    alert_suc.setOverlayClose(true);
                    alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_suc.setContent(layout_suc);
                    alert_suc.initModality(Modality.NONE);
                    alert_suc.show();
                }
                else {
                    String er = serverResponse.getErrorMessage();
                    JFXDialogLayout layout_f = new JFXDialogLayout();
                    layout_f.setBody(new Label("      选课失败：" + er));
                    JFXAlert<Void> alert_f = new JFXAlert<>((Stage) btn.getScene().getWindow());
                    alert_f.setOverlayClose(true);
                    alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_f.setContent(layout_f);
                    alert_f.initModality(Modality.NONE);
                    alert_f.show();
                }
            }
        });

        pane.getChildren().addAll(t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,btn,lb1,lb2,lb3,lb4,lb5,lb6,lb7,lb8,lb9,lb10);
        scp.setContent(pane);
        return scp;
    }

}
