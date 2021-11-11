package client.clientUI;

import client.socket.Client;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
import javafx.util.Callback;
import util.MessageType;
import vo.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Course_tab_grade_Teacher {
    private Teacher teacher;
    private ArrayList<Course> myCourses;
    private ArrayList<Grade> grades = new ArrayList<Grade>();
    private String thisGrade_CourseID = "";
    private String search = "";

    //构造函数
    public Course_tab_grade_Teacher(Teacher teacher, ArrayList<Course> myCourses) {
        this.teacher = teacher;
        this.myCourses = myCourses;
    }

    private void reProduce(AnchorPane ap){
        Client client = new Client();
        Message sendToServer = new Message();
        sendToServer.setMessageType(MessageType.course_studentList_query);
        sendToServer.setExtraMessage(thisGrade_CourseID);
        Message serverResponse = client.sendRequestToServer(sendToServer);
        grades = (ArrayList<Grade>) serverResponse.getData();

        tableGenerate(ap,grades);
    }

    private void tableGenerate(AnchorPane ap, ArrayList<Grade> grades){
        //下面为学生信息打表，此部分应该放在选择label以后的消息响应函数中
        // data
        ObservableList<stu> stus = FXCollections.observableArrayList();
        stus.clear();

        if(grades != null)
            for (int i = 0; i < grades.size(); i++){
                String grade = "";
                if (grades.get(i).getGrade() == -1) grade = "--";
                else grade = String.valueOf(grades.get(i).getGrade());
                stus.add(new stu(grades.get(i).getStuID(), grade, grades.get(i).getStuName()));
            }

        JFXTreeTableColumn<stu, String> stuNumberColumn = new JFXTreeTableColumn<>("学生id");
        stuNumberColumn.setPrefWidth(250);
        stuNumberColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
            if (stuNumberColumn.validateValue(param)) {
                return param.getValue().getValue().stuNumber;
            } else {
                return stuNumberColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<stu, String> stuNameColumn = new JFXTreeTableColumn<>("学生姓名");
        stuNameColumn.setPrefWidth(250);
        stuNameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
            if (stuNameColumn.validateValue(param)) {
                return param.getValue().getValue().stuName;
            } else {
                return stuNameColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<stu, String> gradeColumn = new JFXTreeTableColumn<>("学生成绩");
        gradeColumn.setPrefWidth(250);
        gradeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
            if (gradeColumn.validateValue(param)) {
                return param.getValue().getValue().stuGrade;
            } else {
                return gradeColumn.getComputedValue(param);
            }
        });


        gradeColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        gradeColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().stuGrade.set(t.getNewValue()));

        stuNameColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        stuNameColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().stuName.set(t.getNewValue()));

        stuNumberColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        stuNumberColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().stuNumber.set(t.getNewValue()));



        // build tree
        final TreeItem<stu> root = new RecursiveTreeItem<>(stus, RecursiveTreeObject::getChildren);
        JFXTreeTableView<stu> treeView = new JFXTreeTableView<>(root);

        treeView.setShowRoot(false);
        treeView.getColumns().setAll(stuNumberColumn, stuNameColumn, gradeColumn);

        treeView.setPrefHeight(250);
        treeView.setLayoutX(150);
        treeView.setLayoutY(100);

        JFXTextField filterField = new JFXTextField();
        filterField.setText(search);
        filterField.textProperty().addListener((o, oldVal, newVal) -> {
            treeView.setPredicate(gradeProp -> {
                final stu stu = gradeProp.getValue();
                return stu.stuNumber.get().contains(newVal)
                        || stu.stuName.get().contains(newVal)
                        || stu.stuGrade.get().contains(newVal);
            });
        });

        if(ap.getChildren().contains(treeView)) ap.getChildren().remove(treeView);
        ap.getChildren().add(treeView);
    }

    public Tab TabGrade(){
        Tab tab = new Tab();
        tab.setText("登成绩");
        AnchorPane ap = new AnchorPane();

        //init
        reProduce(ap);
//        HashMap<String, Course> courseInfo;
//        for(int i = 0; i < myCourses.size(); i++){
//            courseInfo.
//        }

//        ObservableList<Course> CourseList = FXCollections.observableArrayList();

        //下拉选择框循环添加老师课程名

        JFXComboBox<Course> combo = new JFXComboBox<Course>();
        if(myCourses != null) combo.setValue(myCourses.get(0));

        for(int i = 0; i < myCourses.size(); i++){
            combo.getItems().add(myCourses.get(i));
//            CourseList.add(myCourses.get(i));
        }
//        combo.setItems(FXCollections.observableArrayList());

        Callback<ListView<Course>, ListCell<Course>> cellCallback = new Callback<ListView<Course>, ListCell<Course>>() {
            @Override
            public ListCell<Course> call(ListView<Course> param) {
                final ListCell<Course> cell = new ListCell<Course>() {
                    {
                        super.setPrefWidth(100);
                    }

                    @Override
                    public void updateItem(Course item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            System.out.println(item.getCourseName());
                            setText(item.getCourseName());
                            thisGrade_CourseID = item.getCourseNumber();
                            reProduce(ap);

                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        combo.setCellFactory(cellCallback);
        combo.setButtonCell(cellCallback.call(null));


        combo.setPromptText("请选择课程");
        combo.setLayoutX(30);
        combo.setLayoutY(30);
        combo.setPrefWidth(105);


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

        Label Title = new Label("课程成绩单");
        Title.setStyle("-fx-font-size:20px");
        Title.setLayoutX(140);
        Title.setLayoutY(30);

//        //下面为学生信息打表，此部分应该放在选择label以后的消息响应函数中
//        // data
//        ObservableList<stu> stus = FXCollections.observableArrayList();
//        stus.clear();
//        stus.add(new stu("09018117","男","殷摸鱼"));
//
//        JFXTreeTableColumn<stu, String> stuNumberColumn = new JFXTreeTableColumn<>("学生id");
//        stuNumberColumn.setPrefWidth(250);
//        stuNumberColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
//            if (stuNumberColumn.validateValue(param)) {
//                return param.getValue().getValue().stuNumber;
//            } else {
//                return stuNumberColumn.getComputedValue(param);
//            }
//        });
//
//        JFXTreeTableColumn<stu, String> stuNameColumn = new JFXTreeTableColumn<>("学生姓名");
//        stuNameColumn.setPrefWidth(250);
//        stuNameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
//            if (stuNameColumn.validateValue(param)) {
//                return param.getValue().getValue().stuName;
//            } else {
//                return stuNameColumn.getComputedValue(param);
//            }
//        });
//
//        JFXTreeTableColumn<stu, String> sexColumn = new JFXTreeTableColumn<>("学生成绩");
//        sexColumn.setPrefWidth(250);
//        sexColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<stu, String> param) -> {
//            if (sexColumn.validateValue(param)) {
//                return param.getValue().getValue().stuGrade;
//            } else {
//                return sexColumn.getComputedValue(param);
//            }
//        });
//
//
//        sexColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
//                new TextFieldEditorBuilder()));
//        sexColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
//                .getTreeItem(t.getTreeTablePosition()
//                        .getRow())
//                .getValue().stuGrade.set(t.getNewValue()));
//
//        stuNameColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
//                new TextFieldEditorBuilder()));
//        stuNameColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
//                .getTreeItem(t.getTreeTablePosition()
//                        .getRow())
//                .getValue().stuName.set(t.getNewValue()));
//
//        stuNumberColumn.setCellFactory((TreeTableColumn<stu, String> param) -> new GenericEditableTreeTableCell<>(
//                new TextFieldEditorBuilder()));
//        stuNumberColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<stu, String> t) -> t.getTreeTableView()
//                .getTreeItem(t.getTreeTablePosition()
//                        .getRow())
//                .getValue().stuNumber.set(t.getNewValue()));
//
//
//
        // build tree
        //final TreeItem<stu> root = new RecursiveTreeItem<>(stus, RecursiveTreeObject::getChildren);

//        JFXTreeTableView<stu> treeView = new JFXTreeTableView<>(root);
//
//        treeView.setShowRoot(false);
//        treeView.getColumns().setAll(stuNumberColumn, sexColumn, stuNameColumn);
//
//        treeView.setPrefHeight(250);
//        treeView.setLayoutX(150);
//        treeView.setLayoutY(100);


        Label t = new Label("添加成绩：");
        t.setStyle("-fx-font-size:20px");
        t.setLayoutX(80);
        t.setLayoutY(400);

        JFXTextField field1 = new JFXTextField();
        field1.setLabelFloat(true);
        field1.setPromptText("请输入学生学号");
        field1.setLayoutX(250);
        field1.setLayoutY(490);

        JFXTextField field2 = new JFXTextField();
        field2.setLabelFloat(true);
        field2.setPromptText("请输入学生课程成绩");
        field2.setLayoutX(470);
        field2.setLayoutY(490);

        JFXButton btn = new JFXButton("确认提交");
        btn.setStyle("    -fx-padding: 0.7em 0.57em;\n" +
                "    -fx-font-size: 14px;\n" +
                "    -jfx-button-type: RAISED;\n" +
                "    -fx-background-color: rgb(77,102,204);\n" +
                "    -fx-text-fill: WHITE;");
        btn.setLayoutX(690);
        btn.setLayoutY(480);
        btn.setPrefWidth(100);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String stuId = field1.getText();
                Integer grade = Integer.valueOf(field2.getText());
                Grade stuGrade = new Grade();
                search = stuId;

                for (int i = 0; i < grades.size(); i++){
                    if(grades.get(i).getStuID().equals(stuId)) {
                        stuGrade = grades.get(i);
                        stuGrade.setGrade(grade);
                    }
                }

                Client client_addGrade = new Client();
                Message sendToServer = new Message();
                sendToServer.setMessageType(MessageType.course_grade_add);
                sendToServer.setData(stuGrade);
                Message serverResponse = client_addGrade.sendRequestToServer(sendToServer);

                reProduce(ap);
            }
        });

        ap.getChildren().addAll(combo,Title,t,field1,field2,btn);

        tab.setContent(ap);
        return tab;
    }

    private static final class stu extends RecursiveTreeObject<stu> {
        final StringProperty stuName;
        //final StringProperty sex;
        final StringProperty stuNumber;
        final StringProperty stuGrade;

        stu(String stuNumber, String grade, String stuName) {
            this.stuNumber = new SimpleStringProperty(stuNumber);
            this.stuName = new SimpleStringProperty(stuName);
            //this.sex = new SimpleStringProperty(sex);
            this.stuGrade = new SimpleStringProperty(grade);
        }
    }


}
