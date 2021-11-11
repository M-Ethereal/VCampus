package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.BorderedTitledPane;
import util.MessageType;
import vo.Course;
import vo.Grade;
import vo.Message;
import vo.Student;

import java.util.ArrayList;
import java.util.Optional;

//只需要显示本学期已经选了的课，并提供退课服务（一共也没多少，因此不提供查询服务）
public class Course_tab_selected {
    ArrayList<Course> myCourses = new ArrayList<Course>();//学生当前学期的当前课表
    ArrayList<Course> myCourses_update = new ArrayList<Course>();
    Student student = new Student();
    private FlowPane fp1 = new FlowPane();
    //图标
    Image img1 = new Image("client/Image/back1.png",150, 150,true,true);
    ImageView iv1 = new ImageView(img1);

    String temp_CID = "";
    String temp_CName = "";

    //构造函数
    public Course_tab_selected(Student student, ArrayList<Course> myCourses) {
        this.myCourses = myCourses;
        this.student = student;
    }

    public void tool_add_a_record(Course course){
        AddCourse(fp1, course);
    }

    //格式转换函数
    private Integer form_time(String term, String time){//1-16, 1-2;2-3
        String[] term_slipt = term.split("-");
        String[] time_slipt = time.split(";");

        Integer week = Integer.valueOf(term_slipt[1]);
        Integer courseTime = Integer.valueOf(time_slipt.length);
        return week*courseTime*2;
    }

    private String form_week(String time){
        //System.out.println(courseTime);
        Integer courseTime = Integer.valueOf(time);
        if(courseTime == 1) return "每周一";
        else if(courseTime == 2) return "每周二";
        else if(courseTime == 3) return "每周三";
        else if(courseTime == 4) return "每周四";
        else if(courseTime == 5) return "每周五";
        else return null;
    }

    private String form_day(String time){
        //System.out.println(courseTime);
        Integer courseTime = Integer.valueOf(time);
        if(courseTime == 1) return "1-2节";
        else if(courseTime == 2) return "3-4节";
        else if(courseTime == 3) return "6-7节";
        else if(courseTime == 4) return "8-9节";
        else if(courseTime == 5) return "11-13节";
        else return null;
    }

    private String form_CourseTime(String courseTime){
        String ans = "";
        String[] str = courseTime.split(";");
        for (int i = 0; i < str.length; i++){
            String temp = str[i];
            String[] week_day = temp.split("-");
            //System.out.println(temp + " " + week_day);
            ans += form_week(week_day[0]) + form_day(week_day[1]) + " ";
        }
        return ans;
    }


    //确认窗口
    private void showConfirmation(Pane b1, JFXButton button, FlowPane flowPane)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setContentText("确定要退课吗？");
        alert.setGraphic(iv1);

        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //info.setText(“btn6 点击了确定”);
            Client client_c = new Client();
            Message messageSendToServer_delete = new Message();
            messageSendToServer_delete.setMessageType(MessageType.course_giveup);
            Grade grade = new Grade();
            grade.setStuID(student.getId());System.out.println("UI:"+grade.getStuID());
            grade.setStuName(student.getName());System.out.println("UI:"+grade.getStuName());
            grade.setCourseID(temp_CID);System.out.println("UI:"+grade.getCourseID());
            grade.setCourseName(temp_CName);System.out.println("UI:"+grade.getCourseName());
            grade.setGrade(-1);
            grade.setGradeType(0);
            messageSendToServer_delete.setData(grade);
            Message serverResponse_choose = client_c.sendRequestToServer(messageSendToServer_delete);
            System.out.println(serverResponse_choose.isLastOperState());

            if (serverResponse_choose.isLastOperState()) {
                JFXDialogLayout layout_suc = new JFXDialogLayout();
                layout_suc.setBody(new Label("      退课成功"));
                JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) b1.getScene().getWindow());
                alert_suc.setOverlayClose(true);
                alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert_suc.setContent(layout_suc);
                alert_suc.initModality(Modality.NONE);
                alert_suc.show();

                flowPane.getChildren().remove(button);

            } else {
                JFXDialogLayout layout_f = new JFXDialogLayout();
                layout_f.setBody(new Label("      退课失败：" + serverResponse_choose.getErrorMessage()));
                System.out.println(serverResponse_choose.getErrorMessage());
                //layout_f.setBody(new Label("      选课失败"));
                JFXAlert<Void> alert_f = new JFXAlert<>((Stage) b1.getScene().getWindow());
                alert_f.setOverlayClose(true);
                alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert_f.setContent(layout_f);
                alert_f.initModality(Modality.NONE);
                alert_f.show();
            }
        } else {
            //info.setText(“btn6 点击了取消”);
            // alert.hide();
        }
    }

    private void AddCourse(FlowPane flowPane, Course course){
        System.out.println(course.getCourseName());
        AnchorPane coursebutton = new AnchorPane();
        coursebutton.setPrefSize(750, 100);
        Label CourseName = new Label(course.getCourseName());
        Label TeacherName = new Label(course.getCourseLecturer());//老师
        Label CourseTerm = new Label(course.getCourseTerm()+"周");//1-16
        Label CourseTime = new Label(form_CourseTime(course.getCourseTime()));//2-1;4-3
        Label CoursePlace = new Label(course.getCoursePlace());
        Label CourseT = new Label(String.valueOf(form_time(course.getCourseTerm(), course.getCourseTime())));//学时
        Label CourseCre = new Label(course.getCourseCredit());

        Label CID = new Label(course.getCourseNumber());
        Label CName = new Label(course.getCourseName());
        CID.setVisible(false);
        CName.setVisible(false);

        CourseName.setStyle("-fx-font-size:25px");
        CourseName.setLayoutX(50);
        CourseName.setLayoutY(35);

        CourseTerm.setStyle("-fx-font-size:15px");
        CourseTerm.setLayoutX(300);
        CourseTerm.setLayoutY(43);

        CourseTime.setStyle("-fx-font-size:15px");
        CourseTime.setLayoutX(400);
        CourseTime.setLayoutY(43);

        TeacherName.setStyle("-fx-font-size:15px");
        TeacherName.setLayoutX(600);
        TeacherName.setLayoutY(43);

        coursebutton.getChildren().addAll(CID,CName, TeacherName,CourseTerm, CourseTime, CourseName);
        JFXButton b1 = new JFXButton();
        b1.setStyle("-jfx-button-type: RAISED;-fx-background-color: rgb(245,245,245);");
        b1.setGraphic(coursebutton);
        flowPane.getChildren().add(b1);

        coursebutton.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            temp_CID = CID.getText();
            temp_CName = CName.getText();
            System.out.println(temp_CID+"  "+temp_CName);

            showConfirmation(coursebutton,b1,flowPane);
        });
    }

    //打表函数
//    public FlowPane courseListGenerate(ArrayList<Course> courseList ) {
//        FlowPane fakeVBox = new FlowPane();
//        for (int i = 0; i < courseList.size(); i++) {
//            System.out.println(courseList.get(i).getCourseName());
//            AnchorPane coursebutton = new AnchorPane();
//            coursebutton.setPrefSize(750, 100);
//            Label CourseName = new Label(courseList.get(i).getCourseName());
//            Label TeacherName = new Label(courseList.get(i).getCourseLecturer());//老师
//            Label CourseTerm = new Label(courseList.get(i).getCourseTerm()+"周");//1-16
//            Label CourseTime = new Label(form_CourseTime(courseList.get(i).getCourseTime()));//2-1;4-3
//            Label CoursePlace = new Label(courseList.get(i).getCoursePlace());
//            Label CourseT = new Label(String.valueOf(form_time(courseList.get(i).getCourseTerm(),courseList.get(i).getCourseTime())));//学时
//            Label CourseCre = new Label(courseList.get(i).getCourseCredit());
//
//            Label CID = new Label(courseList.get(i).getCourseNumber());
//            Label CName = new Label(courseList.get(i).getCourseName());
//            CID.setVisible(false);
//            CName.setVisible(false);
//
//            CourseName.setStyle("-fx-font-size:25px");
//            CourseName.setLayoutX(50);
//            CourseName.setLayoutY(35);
//
//            CourseTerm.setStyle("-fx-font-size:15px");
//            CourseTerm.setLayoutX(300);
//            CourseTerm.setLayoutY(43);
//
//            CourseTime.setStyle("-fx-font-size:15px");
//            CourseTime.setLayoutX(400);
//            CourseTime.setLayoutY(43);
//
//            TeacherName.setStyle("-fx-font-size:15px");
//            TeacherName.setLayoutX(600);
//            TeacherName.setLayoutY(43);
//
//            coursebutton.getChildren().addAll(CID,CName, TeacherName,CourseTerm, CourseTime, CourseName);
//            JFXButton b1 = new JFXButton();
//            b1.setStyle("-jfx-button-type: RAISED;-fx-background-color: rgb(245,245,245);");
//            b1.setGraphic(coursebutton);
//            fakeVBox.getChildren().add(b1);
//
//            coursebutton.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
//                temp_CID = CID.getText();
//                temp_CName = CName.getText();
//                System.out.println(temp_CID+"  "+temp_CName);
//
//                showConfirmation(coursebutton);
//            });
//        }
//        return fakeVBox;
//    }


    public Tab TabSelected() {
        Tab tab = new Tab();
        tab.setText("退课服务");
        ScrollPane scp = new ScrollPane();

        //主标题
        Label mainTitle = new Label("点击退课");
        mainTitle.setStyle("-fx-font-size:30px");

        //初始化
        //FlowPane fp1 = new FlowPane();
        fp1.setVgap(10);

        for (int i = 0; i < myCourses.size(); i++){
            AddCourse(fp1, myCourses.get(i));
        }

        //框
        BorderedTitledPane btp = new BorderedTitledPane("可退课程", fp1);
        btp.getStylesheets().add(getClass().getResource("BorderedTitledPane_CSS.css").toExternalForm());
        btp.setTitleAlignment(Pos.TOP_LEFT);

        //主页面布局
        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(btp, mainTitle);
        ap.setTopAnchor(mainTitle, 50.0);
        ap.setLeftAnchor(mainTitle, 50.0);
        ap.setTopAnchor(btp, 160.0);
        ap.setLeftAnchor(btp, 40.0);

        //滚轮
        scp.setContent(ap);

        tab.setContent(scp);
        return tab;
    }

}
