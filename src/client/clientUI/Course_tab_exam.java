package client.clientUI;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import util.BorderedTitledPane;
import vo.Course;
import vo.Grade;
import vo.Student;

import java.util.ArrayList;

public class Course_tab_exam {
    ArrayList<Course> myCourses = new ArrayList<Course>();//学生当前学期的当前课表

    //构造函数
    public Course_tab_exam(ArrayList<Course> myCourses) {
        this.myCourses = myCourses;
    }

    //考试安排查询
    public Tab TabExam() {
        Tab tab = new Tab();
        tab.setText("我的考试");
        //AnchorPane ap = new AnchorPane();
        ScrollPane scp = new ScrollPane();
        AnchorPane ap_inner = new AnchorPane();

        //主标题
        Label mainTitle = new Label("考试安排");
        mainTitle.setStyle("-fx-font-size:30px");

        //框
        BorderedTitledPane btp = new BorderedTitledPane("当前已发布的考试安排",ap_inner);
        btp.getStylesheets().add(getClass().getResource("BorderedTitledPane_CSS.css").toExternalForm());
        btp.setTitleAlignment(Pos.TOP_LEFT);


        //打表，打在ap上了
        JFXTreeTableColumn<Exam, String> NameColumn = new JFXTreeTableColumn<>("考试科目");
        NameColumn.setPrefWidth(150);
        NameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Exam, String> param) -> {
            if (NameColumn.validateValue(param)) {
                return param.getValue().getValue().ExamName;
            } else {
                return NameColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Exam, String> DateColumn = new JFXTreeTableColumn<>("考试时间");
        DateColumn.setPrefWidth(300);
        DateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Exam, String> param) -> {
            if (DateColumn.validateValue(param)) {
                return param.getValue().getValue().ExamDate;
            } else {
                return DateColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Exam, String> PlaceColumn = new JFXTreeTableColumn<>("考试地点");
        PlaceColumn.setPrefWidth(150);
        PlaceColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Exam, String> param) -> {
            if (PlaceColumn.validateValue(param)) {
                return param.getValue().getValue().ExamPlace;
            } else {
                return PlaceColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Exam, String> TeacherColumn = new JFXTreeTableColumn<>("主讲教师");
        TeacherColumn.setPrefWidth(150);
        TeacherColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Exam, String> param) -> {
            if (TeacherColumn.validateValue(param)) {
                return param.getValue().getValue().TeacherName;
            } else {
                return TeacherColumn.getComputedValue(param);
            }
        });


        NameColumn.setCellFactory((TreeTableColumn<Exam, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        NameColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Exam, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().ExamName.set(t.getNewValue()));

        DateColumn.setCellFactory((TreeTableColumn<Exam, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        DateColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Exam, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().ExamDate.set(t.getNewValue()));
        PlaceColumn.setCellFactory((TreeTableColumn<Exam, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        PlaceColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Exam, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().ExamPlace.set(t.getNewValue()));
        TeacherColumn.setCellFactory((TreeTableColumn<Exam, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        TeacherColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Exam, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().TeacherName.set(t.getNewValue()));


        // data
        ObservableList<Exam> exams = FXCollections.observableArrayList();
        for(int i = 0; i < myCourses.size(); i++){
            if(myCourses.get(i).getExamPlace() == null) continue;
            //System.out.println(myCourses.get(i).getExamPlace().equals(""));
            if(myCourses.get(i).isExam() == true ){//&& (!(myCourses.get(i).getExamPlace().equals("")))
                exams.add(new Exam(myCourses.get(i).getCourseName(),myCourses.get(i).getExamTime(),myCourses.get(i).getExamPlace(),myCourses.get(i).getCourseLecturer()));
            }
        }



        // build tree
        final TreeItem<Exam> root = new RecursiveTreeItem<>(exams, RecursiveTreeObject::getChildren);

        JFXTreeTableView<Exam> treeView = new JFXTreeTableView<>(root);
        treeView.setShowRoot(false);
        treeView.setEditable(true);
        treeView.getColumns().setAll(NameColumn, DateColumn, PlaceColumn, TeacherColumn);

        FlowPane main = new FlowPane();
        main.setPadding(new Insets(40,0,10,0));
        main.setPadding(new Insets(10));
        //main.getChildren().add(treeView);


        JFXTextField filterField = new JFXTextField();
        //main.getChildren().add(filterField);

        Label tip = new Label("在此输入您想查询的科目：");

        filterField.textProperty().addListener((o, oldVal, newVal) -> {
            treeView.setPredicate(examProp -> {
                final Exam exam = examProp.getValue();
                return exam.ExamName.get().contains(newVal)
                        || exam.ExamDate.get().contains(newVal)
                        || exam.ExamPlace.get().contains(newVal)
                        || exam.TeacherName.get().contains(newVal);
            });
        });

//        size.textProperty()
//                .bind(Bindings.createStringBinding(() -> String.valueOf(treeView.getCurrentItemsCount()),
//                        treeView.currentItemsCountProperty()));
        main.getChildren().addAll(tip, filterField, treeView);
        ap_inner.getChildren().add(main);
        btp.getChildren().add(ap_inner);

        //FilterableTreeItem rootNode = new FilterableTreeItem("2019-2020-3");

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

    private static final class Exam extends RecursiveTreeObject<Exam> {
        final StringProperty ExamName;
        final StringProperty ExamDate;
        final StringProperty ExamPlace;
        final StringProperty TeacherName;

        Exam(String name, String date, String place, String teacher) {
            this.ExamName = new SimpleStringProperty(name);
            this.ExamDate = new SimpleStringProperty(date);
            this.ExamPlace = new SimpleStringProperty(place);
            this.TeacherName = new SimpleStringProperty(teacher);
        }
    }
}
