package client.clientUI;

import client.socket.Client;
import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import util.MessageType;
import vo.Course;
import vo.Message;
import vo.Teacher;

import java.util.ArrayList;

//老师的课程管理
//需求：给学生登成绩，查询自己的课表
public class CourseManageHomePageUI_Teacher {
    private Teacher teacher;
    private ArrayList<Course> myCourses = new ArrayList<Course>();//初始化的时候，你的当前学期的当前课表

    public  CourseManageHomePageUI_Teacher(Teacher teacher){
        this.teacher = teacher;
    }

    public AnchorPane CourseHomePageUI() {
        Client client_querySeme = new Client();
        Message messageSendToServer_querySeme = new Message();
        messageSendToServer_querySeme.setMessageType(MessageType.course_teacher_table);
        messageSendToServer_querySeme.setData(teacher);
        messageSendToServer_querySeme.setExtraMessage("20-21-2");
        Message serverResponse_querySeme = client_querySeme.sendRequestToServer(messageSendToServer_querySeme);
        myCourses = (ArrayList<Course>) serverResponse_querySeme.getData();

        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setPrefSize(1058, 580);

//        Course_tab_select course_tab_select = new Course_tab_select(teacher);
        Course_tab_timetable_Teacher course_tab_timetable_teacher = new Course_tab_timetable_Teacher(teacher,myCourses);
        Course_tab_grade_Teacher course_tab_grade_teacher = new Course_tab_grade_Teacher(teacher,myCourses);

        //设置Tab
//        Tab tab_SelectCourse = course_tab_select.TabSelectCourse();
        Tab tab_Timetable = course_tab_timetable_teacher.TabTimeTable();
        Tab tab_Grade = course_tab_grade_teacher.TabGrade();

//        tabPane.getTabs().addAll(tab_SelectCourse,tab_Timetable,tab_Grade);
//
        tabPane.getTabs().addAll(tab_Timetable,tab_Grade);
        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(tabPane);

        return pane;
    }
}
