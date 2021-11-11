package client.clientUI;

import client.socket.Client;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import util.BorderedTitledPane;
import util.MessageType;
import vo.*;

import java.util.ArrayList;

//初始化：查询所有成绩
//显示的信息：学年seme 课程号cid 课程名name 课程性质（选修必修）courseType 学分credit 成绩grade 成绩性质gradeType
//其他需求：按照学期查询成绩,按照科目查询成绩（做个屁，不做了（算了算了，成绩要紧，成绩要紧
public class Course_tab_grade {
    Student student = new Student();
    ArrayList<GradeTable> gradeTables = new ArrayList<GradeTable>();//初始化的表
    ArrayList<GradeTable> gradeTables_updated = new ArrayList<GradeTable>();//新的表

    //构造函数
    public Course_tab_grade(Student student) {
        this.student = student;
    }

    //打表函数 输入list返回布局
    private FlowPane gradeListGenerate(ArrayList<GradeTable> grades){
        FlowPane main = new FlowPane();
        main.setPadding(new Insets(50,0,10,0));
        main.setHgap(10.0);

        JFXTreeTableColumn<Grade1, String> SemeColumn = new JFXTreeTableColumn<>("学年");
        SemeColumn.setPrefWidth(100);
        SemeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Grade1, String> param) -> {
            if (SemeColumn.validateValue(param)) {
                return param.getValue().getValue().seme;
            } else {
                return SemeColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Grade1, String> CidColumn = new JFXTreeTableColumn<>("课程号");
        CidColumn.setPrefWidth(200);
        CidColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Grade1, String> param) -> {
            if (CidColumn.validateValue(param)) {
                return param.getValue().getValue().cid;
            } else {
                return CidColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Grade1, String> NameColumn = new JFXTreeTableColumn<>("课程名");
        NameColumn.setPrefWidth(150);
        NameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Grade1, String> param) -> {
            if (NameColumn.validateValue(param)) {
                return param.getValue().getValue().name;
            } else {
                return NameColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Grade1, String> CourseTypeColumn = new JFXTreeTableColumn<>("课程性质");
        CourseTypeColumn.setPrefWidth(80);
        CourseTypeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Grade1, String> param) -> {
            if (CourseTypeColumn.validateValue(param)) {
                return param.getValue().getValue().CourseType;
            } else {
                return CourseTypeColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Grade1, String> CreditColumn = new JFXTreeTableColumn<>("学分");
        CreditColumn.setPrefWidth(80);
        CreditColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Grade1, String> param) -> {
            if (CreditColumn.validateValue(param)) {
                return param.getValue().getValue().credit;
            } else {
                return CreditColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Grade1, Number> GradeColumn = new JFXTreeTableColumn<>("成绩");
        GradeColumn.setPrefWidth(100);
        GradeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Grade1, Number> param) -> {
            if (GradeColumn.validateValue(param)) {
                return param.getValue().getValue().grade;
            } else {
                return GradeColumn.getComputedValue(param);
            }
        });


        JFXTreeTableColumn<Grade1, Number> GradeTypeColumn = new JFXTreeTableColumn<>("成绩性质");
        GradeTypeColumn.setPrefWidth(80);
        GradeTypeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Grade1, Number> param) -> {
            if (GradeTypeColumn.validateValue(param)) {
                return param.getValue().getValue().gradeType;
            } else {
                return GradeTypeColumn.getComputedValue(param);
            }
        });



        SemeColumn.setCellFactory((TreeTableColumn<Grade1, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        SemeColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Grade1, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().seme.set(t.getNewValue()));

        CidColumn.setCellFactory((TreeTableColumn<Grade1, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        CidColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Grade1, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().cid.set(t.getNewValue()));
        NameColumn.setCellFactory((TreeTableColumn<Grade1, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        NameColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Grade1, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().name.set(t.getNewValue()));
        CourseTypeColumn.setCellFactory((TreeTableColumn<Grade1, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        CourseTypeColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Grade1, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().CourseType.set(t.getNewValue()));
        CreditColumn.setCellFactory((TreeTableColumn<Grade1, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        CreditColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Grade1, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().credit.set(t.getNewValue()));
        GradeColumn.setCellFactory((TreeTableColumn<Grade1, Number> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        GradeColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Grade1, Number> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().grade.set((int) t.getNewValue()));
        GradeTypeColumn.setCellFactory((TreeTableColumn<Grade1, Number> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        GradeTypeColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Grade1, Number> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().gradeType.set((int) t.getNewValue()));

        // data
        ObservableList<Grade1> Grades = FXCollections.observableArrayList();
        for(int i = 0; i < grades.size(); i++)
        {
            GradeTable tempGrade = grades.get(i);
            Grades.add(new Grade1(tempGrade.getCourseSemester(), tempGrade.getCourseID(), tempGrade.getCourseName(), tempGrade.getCourseType(), tempGrade.getCourseCredit(), tempGrade.getGrade(),tempGrade.getGradeType()));
        }


        // build tree
        final TreeItem<Grade1> root = new RecursiveTreeItem<>(Grades, RecursiveTreeObject::getChildren);

        JFXTreeTableView<Grade1> treeView = new JFXTreeTableView<>(root);
        treeView.setShowRoot(false);
        treeView.setEditable(false);
        treeView.getColumns().setAll(SemeColumn, CidColumn, NameColumn, CourseTypeColumn, CreditColumn, GradeColumn, GradeTypeColumn);
        treeView.setPrefWidth(750);//表宽
        main.getChildren().add(treeView);

        JFXTextField filterField = new JFXTextField();
        //main.getChildren().add(filterField);

        Label size = new Label();

        filterField.textProperty().addListener((o, oldVal, newVal) -> {
            treeView.setPredicate(GradeProp -> {
                final Grade1 e = GradeProp.getValue();
                return (e.seme.get().contains(newVal))
                        || (e.cid.get().contains(newVal))
                        || (e.name.get().contains(newVal))
                        || (e.CourseType.get().contains(newVal))
                        || (e.credit.get().contains(newVal));
            });
        });

        size.textProperty()
                .bind(Bindings.createStringBinding(() -> String.valueOf(treeView.getCurrentItemsCount()),
                        treeView.currentItemsCountProperty()));
       //main.getChildren().add(size);

        return main;
    }

//    private Course searchForCourseByCID(Grade grade){
//        String cid = grade.getCourseID();
//        for (int i = 0; i < myCourses.size(); i++){
//            if(myCourses.get(i).getCourseNumber().equals(cid)){
//                System.out.println(myCourses.get(i).getCourseNumber()+myCourses.get(i).getCourseNumber());
//                return myCourses.get(i);
//            }
//        }
//        return null;
//    }


    public Tab TabGrade() {
        Tab tab = new Tab();
        tab.setText("成绩查询");
        ScrollPane scp = new ScrollPane();

        //表格初始化
        Client client_AllGrade = new Client();
        Message msgSendToServer_AllGrade = new Message();
        msgSendToServer_AllGrade.setMessageType(MessageType.course_query_grade);
        msgSendToServer_AllGrade.setExtraMessage(student.getId());
        Message serverResponse = client_AllGrade.sendRequestToServer(msgSendToServer_AllGrade);
        ArrayList<GradeTable> tempTable = new ArrayList<GradeTable>();
        if (serverResponse.getData()!=null) tempTable = (ArrayList<GradeTable>) serverResponse.getData();//没问题
        for (int i = 0; i < tempTable.size(); i++)
        {
            if(tempTable.get(i).getGradeAdded() == true)
                gradeTables.add(tempTable.get(i));
        }

        //主标题
        Label mainTitle = new Label("我的成绩");
        mainTitle.setStyle("-fx-font-size:30px");

        //框内的页面
        AnchorPane fakeVBox = new AnchorPane();
        fakeVBox.getChildren().add(gradeListGenerate(gradeTables));
        //fakeVBox.setPrefWidth(680);

        //框
        BorderedTitledPane btp = new BorderedTitledPane("已经出成绩的科目",fakeVBox);
        btp.getStylesheets().add(getClass().getResource("BorderedTitledPane_CSS.css").toExternalForm());
        btp.setTitleAlignment(Pos.TOP_LEFT);


        //选择学期
        JFXComboBox<String> semesterSelect = new JFXComboBox<String>();
        semesterSelect.setEditable(false);
        semesterSelect.setPromptText("选择学期");
        semesterSelect.getItems().add("18-19-3");
        semesterSelect.getItems().add("19-20-1");
        semesterSelect.getItems().add("19-20-2");
        semesterSelect.getItems().add("19-20-3");
        semesterSelect.getItems().add("20-21-1");
        semesterSelect.getItems().add("20-21-2");
        semesterSelect.getItems().add("20-21-3");

        JFXButton commit = new JFXButton("查询");
        commit.setStyle("-jfx-button-type: RAISED;-fx-background-color: #114376;");
        commit.setTextFill(Paint.valueOf("#FFFFFF"));
        commit.setPrefSize(40, 15);

        //按照学期查询
        commit.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            gradeTables_updated.clear();
            String thisTerm = semesterSelect.getValue();
            for (int i = 0; i < gradeTables.size(); i++)
            {
                if(gradeTables.get(i).getCourseSemester().equals(thisTerm))
                    gradeTables_updated.add(gradeTables.get(i));
            }

            //打表
            FlowPane apTemp = gradeListGenerate(gradeTables_updated);
            fakeVBox.getChildren().clear();
            fakeVBox.getChildren().add(apTemp);
        });

        //选择科目
        JFXComboBox<String> courseNameSelect = new JFXComboBox<String>();
        courseNameSelect.setEditable(false);
        courseNameSelect.setPromptText("选择科目");

        //初始化表
        for (int i = 0; i < gradeTables.size(); i++)
        {
            if(gradeTables.get(i).getGradeAdded() == true) courseNameSelect.getItems().add(gradeTables.get(i).getCourseName());
        }

        JFXButton commit2 = new JFXButton("查询");
        commit2.setStyle("-jfx-button-type: RAISED;-fx-background-color: #114376;");
        commit2.setTextFill(Paint.valueOf("#FFFFFF"));
        commit2.setPrefSize(40, 15);

        //按照科目查询
        commit2.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            gradeTables_updated.clear();
            String thisName = courseNameSelect.getValue();
            for (int i = 0; i < gradeTables.size(); i++)
            {
                if(gradeTables.get(i).getCourseName() == thisName)
                    gradeTables_updated.add(gradeTables.get(i));
            }

            //打表
            FlowPane apTemp = gradeListGenerate(gradeTables_updated);
            fakeVBox.getChildren().clear();
            fakeVBox.getChildren().add(apTemp);
        });



        //主页面布局
        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(btp, mainTitle, semesterSelect, commit, courseNameSelect, commit2);
        ap.setTopAnchor(mainTitle, 50.0);
        ap.setLeftAnchor(mainTitle, 50.0);
        ap.setTopAnchor(semesterSelect, 100.0);
        ap.setLeftAnchor(semesterSelect, 210.0);
        ap.setTopAnchor(commit, 105.0);
        ap.setLeftAnchor(commit, 340.0);
        ap.setTopAnchor(courseNameSelect, 100.0);
        ap.setLeftAnchor(courseNameSelect, 600.0);
        ap.setTopAnchor(commit2, 105.0);
        ap.setLeftAnchor(commit2, 780.0);
        ap.setTopAnchor(btp, 160.0);
        ap.setLeftAnchor(btp, 40.0);


        //滚轮
        scp.setContent(ap);
        tab.setContent(scp);
        return tab;
    }

    private static final class Grade1 extends RecursiveTreeObject<Grade1> {
        final StringProperty seme;
        final StringProperty cid;
        final StringProperty name;
        final StringProperty CourseType;
        final StringProperty credit;
        final IntegerProperty grade;
        final IntegerProperty gradeType;


        Grade1 (String s, String c, String n, String cT, String cr, int g, int gT) {
            this.seme = new SimpleStringProperty(s);
            this.cid = new SimpleStringProperty(c);
            this.name = new SimpleStringProperty(n);
            this.CourseType = new SimpleStringProperty(cT);
            this.credit = new SimpleStringProperty(cr);
            this.grade = new SimpleIntegerProperty(g);
            this.gradeType = new SimpleIntegerProperty(gT);

        }
    }
}
