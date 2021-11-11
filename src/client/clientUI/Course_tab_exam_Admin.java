package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.MessageType;
import vo.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Course_tab_exam_Admin {
    private ArrayList<Course> myCourses;
    private Course this_Course;
    private String this_CourseID = "";
    private String search = "";

    //构造函数
    public Course_tab_exam_Admin () {
    }

    private void reProduce(AnchorPane ap){
        Client client_allCourses = new Client();
        Message sendToServer_allCourses = new Message();
        sendToServer_allCourses.setMessageType(MessageType.course_query_admin);
        sendToServer_allCourses.setExtraMessage("20-21-2");
        Message serverResponse = client_allCourses.sendRequestToServer(sendToServer_allCourses);
        myCourses = (ArrayList<Course>) serverResponse.getData();

        tableGenerate(ap,myCourses);
    }

    private void tableGenerate(AnchorPane ap, ArrayList<Course> courses){
        //下面为学生信息打表，此部分应该放在选择label以后的消息响应函数中
        // data
        ObservableList<stu> stus = FXCollections.observableArrayList();
        stus.clear();

        if(courses != null)
            for (int i = 0; i < courses.size(); i++){
                Course course = courses.get(i);
                stus.add(new stu(course.getCourseNumber(), course.getCourseName(), course.getCoursePlace(), course.getCourseTerm(), course.getCourseTime(), course.isExam(), course.getExamTime(), course.getExamPlace()));
            }

        //列宽需要调整
        JFXTreeTableColumn<stu, String> CourseNumberColumn = new JFXTreeTableColumn<>("课程编号");
        CourseNumberColumn.setPrefWidth(100);
        CourseNumberColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
            if (CourseNumberColumn.validateValue(param)) {
                return param.getValue().getValue().courseNumber;
            } else {
                return CourseNumberColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<stu, String> NameColumn = new JFXTreeTableColumn<>("课程名");
        NameColumn.setPrefWidth(100);
        NameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
            if (NameColumn.validateValue(param)) {
                return param.getValue().getValue().courseName;
            } else {
                return NameColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<stu, String> CoursePlaceColumn = new JFXTreeTableColumn<>("授课地点");
        CoursePlaceColumn.setPrefWidth(100);
        CoursePlaceColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
            if (CoursePlaceColumn.validateValue(param)) {
                return param.getValue().getValue().coursePlace;
            } else {
                return CoursePlaceColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<stu, String> CourseTermColumn = new JFXTreeTableColumn<>("授课学期");
        CourseTermColumn.setPrefWidth(100);
        CourseTermColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
            if (CourseTermColumn.validateValue(param)) {
                return param.getValue().getValue().courseTerm;
            } else {
                return CourseTermColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<stu, String> CourseTimeColumn = new JFXTreeTableColumn<>("上课时间");
        CourseTimeColumn.setPrefWidth(100);
        CourseTimeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
            if (CourseTimeColumn.validateValue(param)) {
                return param.getValue().getValue().courseTime;
            } else {
                return CourseTimeColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<stu, Boolean> IsExamColumn = new JFXTreeTableColumn<>("是否为考试");
        IsExamColumn.setPrefWidth(100);
        IsExamColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, Boolean> param) -> {
            if (IsExamColumn.validateValue(param)) {
                return param.getValue().getValue().isExam;
            } else {
                return IsExamColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<stu, String> ExamTimeColumn = new JFXTreeTableColumn<>("考试时间");
        ExamTimeColumn.setPrefWidth(100);
        ExamTimeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
            if (ExamTimeColumn.validateValue(param)) {
                return param.getValue().getValue().examTime;
            } else {
                return ExamTimeColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<stu, String> ExamPlaceColumn = new JFXTreeTableColumn<>("考试地点");
        ExamPlaceColumn.setPrefWidth(100);
        ExamPlaceColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
            if (ExamPlaceColumn.validateValue(param)) {
                return param.getValue().getValue().examPlace;
            } else {
                return ExamPlaceColumn.getComputedValue(param);
            }
        });


        CourseNumberColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        CourseNumberColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().courseNumber.set(t.getNewValue()));

        CoursePlaceColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        CoursePlaceColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().coursePlace.set(t.getNewValue()));
        NameColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        NameColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().courseName.set(t.getNewValue()));
        CourseTermColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        CourseTermColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().courseTerm.set(t.getNewValue()));
        CourseTimeColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        CourseTimeColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().courseTime.set(t.getNewValue()));
        IsExamColumn.setCellFactory((TreeTableColumn<stu, Boolean> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        IsExamColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, Boolean> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().isExam.set((boolean) t.getNewValue()));
        ExamTimeColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        ExamTimeColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().examTime.set(t.getNewValue()));
        ExamPlaceColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        ExamPlaceColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().examPlace.set(t.getNewValue()));


        // build tree
        final TreeItem<stu> root = new RecursiveTreeItem<>(stus, RecursiveTreeObject::getChildren);

        JFXTreeTableView<stu> treeView = new JFXTreeTableView<>(root);
        treeView.setShowRoot(false);
        treeView.setEditable(false);
        treeView.getColumns().setAll(CourseNumberColumn, CoursePlaceColumn, NameColumn, CourseTermColumn, CourseTimeColumn, IsExamColumn, ExamTimeColumn, ExamPlaceColumn);

        treeView.setPrefHeight(250);
        treeView.setLayoutX(150);
        treeView.setLayoutY(100);

        if(ap.getChildren().contains(treeView)) ap.getChildren().remove(treeView);
        ap.getChildren().add(treeView);
    }

    public Tab TabExam(){
        Tab tab = new Tab();
        tab.setText("发布考试信息");
        AnchorPane ap = new AnchorPane();

        //init
        reProduce(ap);

//        //下拉选择框循环添加课程名
//        JFXComboBox<Course> combo = new JFXComboBox<Course>();
//        if(myCourses != null) combo.setValue(myCourses.get(0));
//        for(int i = 0; i < myCourses.size(); i++){
//            combo.getItems().add(myCourses.get(i));
//        }
//
//        Callback<ListView<Course>, ListCell<Course>> cellCallback = new Callback<ListView<Course>, ListCell<Course>>() {
//            @Override
//            public ListCell<Course> call(ListView<Course> param) {
//                final ListCell<Course> cell = new ListCell<Course>() {
//                    {
//                        super.setPrefWidth(100);
//                    }
//
//                    @Override
//                    public void updateItem(Course item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (item != null) {
//                            System.out.println(item.getCourseName());
//                            setText(item.getCourseName());
//                            this_CourseID = item.getCourseNumber();
//                            reProduce(ap);
//
//                        } else {
//                            setText(null);
//                        }
//                    }
//                };
//                return cell;
//            }
//        };
//        combo.setCellFactory(cellCallback);
//        combo.setButtonCell(cellCallback.call(null));
//
//
//        combo.setPromptText("请选择课程");
//        combo.setLayoutX(30);
//        combo.setLayoutY(30);
//        combo.setPrefWidth(105);


//        combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//
//                Course course = (Course) newValue;
//                thisGrade_CourseID = course.getCourseNumber();
//                reProduce(ap);
//            }
//
//        });

        Label Title = new Label("课程详情");
        Title.setStyle("-fx-font-size:20px");
        Title.setLayoutX(140);
        Title.setLayoutY(30);

        Label t = new Label("发布考试信息：");
        t.setStyle("-fx-font-size:20px");
        t.setLayoutX(80);
        t.setLayoutY(400);

        JFXTextField field1 = new JFXTextField();
        field1.setLabelFloat(true);
        field1.setPromptText("请输入课程号");
        field1.setLayoutX(100);
        field1.setLayoutY(460);

//        JFXTextField field2 = new JFXTextField();
//        field2.setLabelFloat(true);
//        field2.setPromptText();
//        field2.setLayoutX(340);
//        field2.setLayoutY(460);

        JFXDatePicker datePicker = new JFXDatePicker();
        datePicker.setPromptText("请选择考试日期");
        datePicker.setLayoutX(300);
        datePicker.setLayoutY(460);
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        JFXTimePicker timePicker = new JFXTimePicker();
        timePicker.setPromptText("请选择考试时间");
        timePicker.setLayoutX(500);
        timePicker.setLayoutY(460);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");

        JFXTextField field3 = new JFXTextField();
        field3.setLabelFloat(true);
        field3.setPromptText("请输入考试教室");
        field3.setLayoutX(700);
        field3.setLayoutY(460);

        JFXButton btn = new JFXButton("确认提交");
        btn.setStyle("    -fx-padding: 0.7em 0.57em;\n" +
                "    -fx-font-size: 14px;\n" +
                "    -jfx-button-type: RAISED;\n" +
                "    -fx-background-color: rgb(77,102,204);\n" +
                "    -fx-text-fill: WHITE;");
        btn.setLayoutX(900);
        btn.setLayoutY(450);
        btn.setPrefWidth(100);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String date = datePicker.getValue().format(dtf1);
                String time = timePicker.getValue().format(dtf2);
                String examTime = date + ";" + time;
                String courseID = field1.getText();
                String examPlace = field3.getText();

                Course course = new Course();
                for (int i = 0; i < myCourses.size(); i++){
                    if(myCourses.get(i).getCourseNumber().equals(courseID)) {
                        course = myCourses.get(i);
                        course.setExam(true);
                        course.setExamPlace(examPlace);
                        course.setExamTime(examTime);
                    }
                }

                Client client_addGrade = new Client();
                Message sendToServer = new Message();
                sendToServer.setMessageType(MessageType.course_exam_add);
                sendToServer.setData(course);
                Message serverResponse = client_addGrade.sendRequestToServer(sendToServer);

                if(serverResponse.isLastOperState() == true){
                    JFXDialogLayout layout_suc = new JFXDialogLayout();
                    layout_suc.setBody(new Label("      发布成功"));
                    JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) btn.getScene().getWindow());
                    alert_suc.setOverlayClose(true);
                    alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_suc.setContent(layout_suc);
                    alert_suc.initModality(Modality.NONE);
                    alert_suc.show();
                }

                reProduce(ap);
            }
        });

        ap.getChildren().addAll(Title,t,field1,datePicker,timePicker,field3,btn);

        tab.setContent(ap);
        return tab;
    }

    private static final class stu extends RecursiveTreeObject<stu> {
        final StringProperty courseNumber;
        final StringProperty courseName;
        final StringProperty coursePlace;
        final StringProperty courseTerm;
        final StringProperty courseTime;

        final BooleanProperty isExam;
        final StringProperty examTime;
        final StringProperty examPlace;

        stu(String courseNumber, String courseName, String coursePlace, String courseTerm, String courseTime, boolean isExam, String examTime, String examPlace) {
            this.courseNumber = new SimpleStringProperty(courseNumber);
            this.courseName = new SimpleStringProperty(courseName);
            this.coursePlace = new SimpleStringProperty(coursePlace);
            this.courseTerm = new SimpleStringProperty(courseTerm);
            this.courseTime = new SimpleStringProperty(courseTime);
            this.isExam = new SimpleBooleanProperty(isExam);
            this.examTime = new SimpleStringProperty(examTime);
            this.examPlace = new SimpleStringProperty(examPlace);
        }
    }


}
