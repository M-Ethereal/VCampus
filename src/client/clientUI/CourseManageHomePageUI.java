package client.clientUI;

import client.socket.Client;
import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import util.MessageType;
import vo.Course;
import vo.Message;
import vo.Student;

import java.util.ArrayList;

public class CourseManageHomePageUI {
    private Student student;
    private ArrayList<Course> myCourses = new ArrayList<Course>();//初始化的时候，你的当前学期的当前课表

    public  CourseManageHomePageUI(Student student){
        this.student = student;
    }

    public AnchorPane CourseHomePageUI() {
        //初始化信息
        Client client_init = new Client();
        Message messageSendToServer_init = new Message();
        messageSendToServer_init.setMessageType(MessageType.course_my_table);
        messageSendToServer_init.setData(student);
        messageSendToServer_init.setExtraMessage("20-21-2");//当前学期
        Message serverResponse = client_init.sendRequestToServer(messageSendToServer_init);
        myCourses = (ArrayList<Course>) serverResponse.getData();

        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setPrefSize(1058, 580);

        //创建子页面
        Course_tab_selected course_tab_selected = new Course_tab_selected(student, myCourses);
        Course_tab_select course_tab_select = new Course_tab_select(student, course_tab_selected);

        Course_tab_timetable course_tab_timetable = new Course_tab_timetable(student,myCourses);
        Course_tab_exam course_tab_exam = new Course_tab_exam(myCourses);
        Course_tab_grade course_tab_grade = new Course_tab_grade(student);

        //设置Tab
        Tab tab_SelectCourse = course_tab_select.TabSelectCourse();
        Tab tab_Timetable = course_tab_timetable.TabTimeTable();
        Tab tab_Exam = course_tab_exam.TabExam();
        Tab tab_Selected = course_tab_selected.TabSelected();
        Tab tab_Grade = course_tab_grade.TabGrade();

        tabPane.getTabs().addAll(tab_SelectCourse, tab_Selected, tab_Timetable,tab_Exam, tab_Grade);

        AnchorPane pane = new AnchorPane();
        //pane.getStylesheets().add(getClass().getResource("jfoenix-components.css").toExternalForm());
        pane.getChildren().addAll(tabPane);

        return pane;
    }
}
