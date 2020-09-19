package client.clientUI;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CourseManageHomePageUI {
    public AnchorPane CourseHomePageUI() {

        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setPrefSize(1058, 580);

        Course_tab_select course_tab_select = new Course_tab_select();
        Course_tab_timetable course_tab_timetable = new Course_tab_timetable();
        Course_tab_grade course_tab_grade = new Course_tab_grade();

        //设置Tab
        Tab tab_SelectCourse = course_tab_select.TabSelectCourse();
        Tab tab_Timetable = course_tab_timetable.TabTimeTable();
        Tab tab_Grade = course_tab_grade.TabGrade();

        tabPane.getTabs().addAll(tab_SelectCourse,tab_Timetable,tab_Grade);

        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(tabPane);

        return pane;
    }
}
