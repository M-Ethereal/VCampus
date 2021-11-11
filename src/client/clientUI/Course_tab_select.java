package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

public class Course_tab_select {

    private ArrayList<Course> allCourseList = new ArrayList<Course>();//所有课程
    private ArrayList<Course> courseSelectList = new ArrayList<Course>();//可选课程
    private Course selectedCourse;
    private Student student;
    private Course_tab_selected course_tab_selected;

    //图标
    Image img1 = new Image("client/Image/checkMark.png",150, 150,true,true);
    ImageView iv1 = new ImageView(img1);

    String temp_CID = "";
    String temp_CName = "";
    Boolean choose = false;


    public Course_tab_select(Student student, Course_tab_selected course_tab_selected){
        this.student = student;
        this.course_tab_selected = course_tab_selected;
    }


    //确认窗口
    private void showConfirmation(Pane b1)
    {
//        JFXButton negativeBtn = new JFXButton("确认选课");
//        JFXButton positiveBtn = new JFXButton("取消");
//        JFXDialogLayout layout = new JFXDialogLayout();
//        layout.setBody(new Label("      确定要选课吗？"));

//        JFXAlert<Void> alert = new JFXAlert<>((Stage) b1.getScene().getWindow());
//        alert.setOverlayClose(true);
//        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
//        alert.setContent(layout);
//        alert.initModality(Modality.NONE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setContentText("\n\n     确定要选课吗？");
        //alert.setGraphic(layout);
        alert.setGraphic(iv1);

        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //info.setText(“btn6 点击了确定”);
            Client client_c = new Client();
            Message messageSendToServer_choose = new Message();
            messageSendToServer_choose.setMessageType(MessageType.course_choose);
            Grade grade = new Grade();
            grade.setStuID(student.getId());
            grade.setStuName(student.getName());
            grade.setCourseID(temp_CID);
            grade.setCourseName(temp_CName);
            grade.setGrade(-1);
            grade.setGradeType(0);
            messageSendToServer_choose.setData(grade);
            Message serverResponse_choose = client_c.sendRequestToServer(messageSendToServer_choose);
            System.out.println(serverResponse_choose.isLastOperState());
            if (serverResponse_choose.isLastOperState()) {
                JFXDialogLayout layout_suc = new JFXDialogLayout();
                layout_suc.setBody(new Label("      选课成功"));
                JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) b1.getScene().getWindow());
                alert_suc.setOverlayClose(true);
                alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert_suc.setContent(layout_suc);
                alert_suc.initModality(Modality.NONE);
                alert_suc.show();

                course_tab_selected.tool_add_a_record(selectedCourse);

            } else {
                JFXDialogLayout layout_f = new JFXDialogLayout();
                layout_f.setBody(new Label("      选课失败：" + serverResponse_choose.getErrorMessage()));
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

        //layout.setActions(negativeBtn, positiveBtn);
//        negativeBtn.addEventHandler(MouseEvent.MOUSE_PRESSED,e1->{
//            alert.hide();
//        });
//        positiveBtn.addEventHandler(MouseEvent.MOUSE_PRESSED,e2->{ //选课操作
//            Message messageSendToServer_choose = new Message();
//            messageSendToServer_choose.setMessageType(MessageType.course_choose);
//            Grade grade = new Grade();
//            grade.setStuID(student.getId());
//            grade.setStuName(student.getName());
//            grade.setCourseID(temp_CID);
//            grade.setCourseName(temp_CName);
//            messageSendToServer_choose.setData(grade);
//            Message serverResponse_choose = client.sendRequestToServer(messageSendToServer_choose);
//            if (serverResponse_choose.isLastOperState()) {
//                JFXDialogLayout layout_suc = new JFXDialogLayout();
//                layout_suc.setBody(new Label("      选课成功"));
//                JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) b1.getScene().getWindow());
//                alert_suc.setOverlayClose(true);
//                alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
//                alert_suc.setContent(layout_suc);
//                alert_suc.initModality(Modality.NONE);
//            } else {
//                JFXDialogLayout layout_f = new JFXDialogLayout();
//                layout_f.setBody(new Label(serverResponse_choose.getErrorMessage()));
//                JFXAlert<Void> alert_f = new JFXAlert<>((Stage) b1.getScene().getWindow());
//                alert_f.setOverlayClose(true);
//                alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
//                alert_f.setContent(layout_f);
//                alert_f.initModality(Modality.NONE);
//            }
//            choose = true;
//        });

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

    //打表函数
    public FlowPane courseListGenerate( ArrayList<Course> courseList ){
        FlowPane fakeVBox = new FlowPane();
        for(int i = 0; i < courseList.size();i++){
            System.out.println(courseList.get(i).getCourseName());
        }

        //筛选相同课程名的，放在同一个tp下
        //tp的标题是课程编号，课程名称，可选班级数，课程类型，学时，学分
        //tp的内容是各个按钮，按钮由同名课程的不同课程信息构成

        //排序
        //ArrayList<Course> TcourseList = new ArrayList<Course>();
        Comparator<Course> compareName = Comparator.comparing(Course::getCourseName);//按课程名排
        Comparator<Course> compareSeme = Comparator.comparing(Course::getCourseSemester);//按学期排
        Collections.sort(courseList, compareName.thenComparing(compareSeme));


        String lastCourseName = "";
        String lastCourseSeme = "";

        int counter = 0;//每个栏目里有几块
        int index = 0;//内外循环遍历时的交互用的指针
        for(int i = 0; i < courseList.size(); i++) {
            //里循环生成一个button
            int indexi = index;
            i = indexi;
            counter = 0;
            lastCourseName = courseList.get(i).getCourseName();
            lastCourseSeme = courseList.get(i).getCourseSemester();
            FlowPane content = new FlowPane();
            content.setVgap(10);
            content.setHgap(10);
            Boolean flag_newclass = false;
            for( int j = 0 ; j < courseList.size(); j++){
                if(flag_newclass == false) {
                    j = indexi;
                    flag_newclass = true;
                }

                System.out.println(courseList.size() + "  "+ courseList.get(j).getCourseName() + "  " + lastCourseName + "  " + courseList.get(j).getCourseSemester() + "  " + lastCourseSeme);
                if(!(String.valueOf(courseList.get(j).getCourseName()) .equals(String.valueOf(lastCourseName) ) && String.valueOf(courseList.get(j).getCourseSemester()).equals( String.valueOf(lastCourseSeme)))){
                    System.out.println("break");
                    break;
                }

                AnchorPane coursebutton = new AnchorPane();
                coursebutton.setPrefSize(200, 150);
                Label TeacherName = new Label(courseList.get(j).getCourseLecturer());//老师
                Label CourseTerm = new Label(courseList.get(j).getCourseTerm()+"周");//1-16
                Label CourseTime = new Label(form_CourseTime(courseList.get(j).getCourseTime()));//2-1;4-3
                Label CoursePlace = new Label(courseList.get(j).getCoursePlace());
                Label CourseSelectable = new Label("已选" + String.valueOf(courseList.get(j).getEnrolledStudents()) + "/" + String.valueOf(courseList.get(j).getMaximumStudents()));
                Label CourseFull = new Label("已满");

                Label CID = new Label(courseList.get(j).getCourseNumber());
                Label CName = new Label(courseList.get(j).getCourseName());
                CID.setVisible(false);
                CName.setVisible(false);

                TeacherName.setStyle("-fx-font-size:30px");
                TeacherName.setLayoutX(20);
                TeacherName.setLayoutY(10);

                CourseFull.setStyle("-fx-font-size:20px");
                CourseFull.setLayoutX(160);
                CourseFull.setLayoutY(10);
                CourseFull.setVisible(false);

                CourseTerm.setStyle("-fx-font-size:15px");
                CourseTerm.setLayoutX(20);
                CourseTerm.setLayoutY(60);

                CourseTime.setStyle("-fx-font-size:15px");
                CourseTime.setLayoutX(20);
                CourseTime.setLayoutY(80);

                CoursePlace.setStyle("-fx-font-size:15px");
                CoursePlace.setLayoutX(20);
                CoursePlace.setLayoutY(100);

                CourseSelectable.setStyle("-fx-font-size:15px");
                CourseSelectable.setLayoutX(20);
                CourseSelectable.setLayoutY(120);

                coursebutton.getChildren().addAll(CID,CName, CourseFull, TeacherName,CourseTerm, CourseTime,CoursePlace,CourseSelectable);

                JFXButton b1 = new JFXButton();
                //如果已经满了
                if(Integer.valueOf(courseList.get(j).getEnrolledStudents()).equals(Integer.valueOf(courseList.get(j).getMaximumStudents()))) {
                    b1.setDisable(true);
                    CourseFull.setVisible(true);
                }
                b1.setStyle("-jfx-button-type: RAISED;-fx-background-color: rgb(245,245,245);");
                b1.setGraphic(coursebutton);
                content.getChildren().add(b1);


                int finalJ = j;
                coursebutton.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {

                    selectedCourse = courseList.get(finalJ);

                    temp_CID = CID.getText();
                    temp_CName = CName.getText();
                    System.out.println(temp_CID+"  "+temp_CName);

                    showConfirmation(coursebutton);

                });

                lastCourseName = courseList.get(j).getCourseName();
                lastCourseSeme = courseList.get(j).getCourseSemester();
                index++;
                counter++;
                System.out.println("index:"+index +"counter:"+counter);
            }


            //外循环生成一个title
            HBox title = new HBox();
            Label cId = new Label(courseList.get(i).getCourseNumber());
            cId.setStyle("-fx-font-size:14px");
            Label cName = new Label(courseList.get(i).getCourseName());
            cName.setStyle("-fx-font-size:14px");
            Label cNum = new Label(String.valueOf(counter));
            cNum.setStyle("-fx-font-size:14px");
            Label cType = new Label(courseList.get(i).getCourseType());
            cType.setStyle("-fx-font-size:14px");
            Label cTime = new Label(form_time(courseList.get(i).getCourseTerm(), courseList.get(i).getCourseTime())+"学时");
            cTime.setStyle("-fx-font-size:14px");
            Label cCredit = new Label(courseList.get(i).getCourseCredit()+"学分");
            cCredit.setStyle("-fx-font-size:14px");
            title.getChildren().addAll(cId, cName, cNum, cType, cTime, cCredit);
            title.setAlignment(Pos.CENTER);
            title.setPrefSize(700,50);
            title.setSpacing(45);

            TitledPane temp = new TitledPane();
            temp.setExpanded(false);
            temp.setPrefWidth(700);
            temp.setGraphic(title);
            temp.setContent(content);
            fakeVBox.getChildren().add(temp);

            i = index;
        }
        return fakeVBox;
    }


    public Tab TabSelectCourse() {
        Tab tab = new Tab();
        tab.setText("学生选课");
        ScrollPane scp = new ScrollPane();

        //主标题
        Label mainTitle = new Label("推荐选课");
        mainTitle.setStyle("-fx-font-size:30px");

        //选择学期
        JFXComboBox <String> semesterSelect = new JFXComboBox<String>();
        semesterSelect.setEditable(false);
        semesterSelect.setPromptText("选择学期");
        semesterSelect.getItems().add("18-19-3");
        semesterSelect.getItems().add("19-20-1");
        semesterSelect.getItems().add("19-20-2");
        semesterSelect.getItems().add("19-20-3");
        semesterSelect.getItems().add("20-21-1");
        semesterSelect.getItems().add("20-21-2");
        semesterSelect.getItems().add("20-21-3");
        semesterSelect.getItems().add("21-22-3");

        JFXButton commit = new JFXButton("查询");
        commit.setStyle("-jfx-button-type: RAISED;-fx-background-color: #114376;");
        commit.setTextFill(Paint.valueOf("#FFFFFF"));
        commit.setPrefSize(40, 15);

        Client client = new Client();
        Message messageSendToServer = new Message();
        messageSendToServer.setMessageType(MessageType.course_init);
        messageSendToServer.setUserId(student.getId());
        //messageSendToServer.setExtraMessage(semesterSelect.getValue());
        messageSendToServer.setData(student);
        messageSendToServer.setExtraMessage("20-21-2");
        Message serverResponse = client.sendRequestToServer(messageSendToServer);
        allCourseList = (ArrayList<Course>) serverResponse.getData();

        FlowPane fakeVBox = new FlowPane();

        //表头生成
        HBox tabletitle = new HBox();
        tabletitle.setStyle("-fx-background-color: #339eff");
        Label Id = new Label("课程编号");
        Id.setStyle("-fx-font-size:14px");
        Label Name = new Label("课程名称");
        Name.setStyle("-fx-font-size:14px");
        Label Num = new Label("可选班级数");
        Num.setStyle("-fx-font-size:14px");
        Label Type = new Label("课程类型");
        Type.setStyle("-fx-font-size:14px");
        Label Time = new Label("学时");
        Time.setStyle("-fx-font-size:14px");
        Label Credit = new Label("学分");
        Credit.setStyle("-fx-font-size:14px");
        tabletitle.getChildren().addAll(Id, Name, Num, Type, Time, Credit);
        tabletitle.setAlignment(Pos.CENTER);
        tabletitle.setPrefSize(733,50);
        tabletitle.setMargin(Id, new Insets(25));
        tabletitle.setMargin(Name, new Insets(15));
        tabletitle.setMargin(Credit, new Insets(25));
        tabletitle.setSpacing(25);

        fakeVBox.getChildren().add(tabletitle);

        VBox list = new VBox();
        //初始化
        FlowPane fp = courseListGenerate(allCourseList);
        if (allCourseList!=null) {
            list.getChildren().add( fp );
        }

        fakeVBox.getChildren().add(list);

        BorderedTitledPane btp = new BorderedTitledPane("可选课程",fakeVBox);
        btp.getStylesheets().add(getClass().getResource("BorderedTitledPane_CSS.css").toExternalForm());
        btp.setTitleAlignment(Pos.TOP_LEFT);

        commit.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            Message messageSendToServer_slct = new Message();
            messageSendToServer_slct.setMessageType(MessageType.course_query);
            messageSendToServer_slct.setUserId(student.getId());
            messageSendToServer_slct.setData(student);
            System.out.println(semesterSelect.getValue());
            messageSendToServer_slct.setExtraMessage(String.valueOf(semesterSelect.getValue()));
            Message serverResponse_slct = client.sendRequestToServer(messageSendToServer_slct);
            courseSelectList = (ArrayList<Course>) serverResponse_slct.getData();
            FlowPane fptemp = new FlowPane();
            if (courseSelectList!=null) fptemp = courseListGenerate(courseSelectList);
            else fptemp.getChildren().add(new Label("当前查询学期没有课程哦"));
            list.getChildren().clear();
            list.getChildren().add(fptemp);
        });

        //主页面布局
        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(btp, mainTitle, semesterSelect, commit);
        ap.setTopAnchor(mainTitle, 50.0);
        ap.setLeftAnchor(mainTitle, 50.0);
        ap.setTopAnchor(semesterSelect, 100.0);
        ap.setLeftAnchor(semesterSelect, 70.0);
        ap.setTopAnchor(commit, 105.0);
        ap.setLeftAnchor(commit, 180.0);
        ap.setTopAnchor(btp, 160.0);
        ap.setLeftAnchor(btp, 40.0);


        //滚轮
        scp.setContent(ap);
        //镶入tab
        tab.setContent(scp);
        return tab;
    }
}
