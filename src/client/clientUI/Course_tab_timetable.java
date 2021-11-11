package client.clientUI;

import client.socket.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.sun.xml.internal.ws.server.sei.SEIInvokerTube;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import util.MessageType;
import vo.Course;
import vo.Grade;
import vo.Message;
import vo.Student;

import java.util.ArrayList;

public class Course_tab_timetable {
    Student student = new Student();
    ArrayList<Course> myCourses = new ArrayList<Course>();//初始化的时候，你的当前学期的当前课表
    ArrayList<Course> myCourses_anotherSeme = new ArrayList<Course>();//查询学期的新课表
    Integer week;

    //构造函数
    public Course_tab_timetable(Student student, ArrayList<Course> myCourses) {
        this.student = student;
        this.week = 1;
        this.myCourses = myCourses;
    }

    private class Pos{
        private Integer x;
        private Integer y;

        public Pos(){
            this.x = 0;
            this.y = 0;
        }

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }
    }

    //上课时间-星期几
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
    //上课时间-第几节课
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
    //上课时间-组合
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


    //算坐标
    public Pos getButtonPosition(String time){//time 1-2(周一的第二大节课)
        Pos pos = new Pos();

        String[] temp = time.split("-");
        Integer dayOfTheWeek = Integer.valueOf(temp[0]);
        Integer timeOfTheDay = Integer.valueOf(temp[1]);

        //映射函数(你要填的地方
        switch (dayOfTheWeek)
        {
            case 1://周一
                pos.x = 90;
                break;
            case 2://周二
                pos.x = 170;
                break;
            case 3://周三
                pos.x = 250;
                break;
            case 4://周四
                pos.x = 330;
                break;
            case 5://周五
                pos.x = 410;
                break;
            case 6://周六
                pos.x = 490;
                break;
            case 7://周日
                pos.x = 570;
                break;
            default:
                break;
        }

        switch (timeOfTheDay)
        {
            case 1://1-2节
                pos.y = 41;
                break;
            case 2://3-4节
                pos.y = 107;
                break;
            case 3://6-7节
                pos.y = 218;
                break;
            case 4://8-9节
                pos.y = 283;
                break;
            case 5://11-13节
                pos.y = 398;
                break;
            default:
                break;
        }
        return pos;
    }

    //打表函数
    private AnchorPane TableGenerate(ArrayList<Course> myCourseList, StackPane sp, Integer whichWeek)
    {
        AnchorPane table = new AnchorPane();

        //课程格子颜色
        ArrayList<String> color = new ArrayList<String>();
        color.add("#FFC0CB");
        color.add("#DDA0DD");
        color.add("#87CEFA");
        color.add("#F0E68C");
        color.add("#FFEBCD");

        String courseName = "";
        String courseTeacher = "";
        String courseTime = "";
        String courseCredit = "";
        String courseClassroom = "";

        //这里遍历从后端返回学生的选课课程信息
        for(int i = 0; i < myCourseList.size(); i++) {//遍历课程
            String[] temp = myCourseList.get(i).getCourseTerm().split("-");
            //从第几周开始
            Integer startWeek = Integer.valueOf(temp[0]);
            //第几周结束
            Integer endWeek = Integer.valueOf(temp[1]);

            if (whichWeek < startWeek | whichWeek > endWeek) continue;//当前周没有这门课，直接看别的课了

            final double d = Math.random();
            final int j = (int)(d*5);

            courseName = myCourseList.get(i).getCourseName();
            courseTeacher = myCourseList.get(i).getCourseLecturer();
            courseTime = form_CourseTime(myCourseList.get(i).getCourseTime());
            courseCredit = myCourseList.get(i).getCourseCredit();
            courseClassroom = myCourseList.get(i).getCoursePlace();


            //遍历生成这门课的课表：当前周要生成几个按钮(一周a节课
            String[] time_day = myCourseList.get(i).getCourseTime().split(";");
            for(int a = 0; a < time_day.length; a++)
            {
                JFXButton btn = new JFXButton( courseName+"\n\n"+courseTeacher);
                btn.setWrapText(true);
                btn.setPrefSize(70, 65);//100 65
                String[] strTemp = time_day[a].split("-");
                if(Integer.valueOf(strTemp[1]) == 5)
                    btn.setPrefSize(70, 95);//95 65
                btn.setFont(new Font("SimHei", 10));
                btn.setStyle("-fx-background-color: " +color.get(j)+ ";-fx-background-radius: 10px;-fx-text-alignment:center;");

                btn.setLayoutX(getButtonPosition(time_day[a]).x);
                btn.setLayoutY(getButtonPosition(time_day[a]).y);
                table.getChildren().add(btn);

                String finalCourseTime = courseTime;
                String finalCourseClassroom = courseClassroom;
                String finalCourseCredit = courseCredit;
                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        JFXDialogLayout dialog = new JFXDialogLayout();
                        dialog.setHeading(new Text("课程详情"));
                        dialog.setBody(new Text("上课时间：" + finalCourseTime +"\n上课地点：" + finalCourseClassroom +"\n课程学分：" + finalCourseCredit));
                        JFXDialog mag=new JFXDialog(sp,dialog,JFXDialog.DialogTransition.CENTER);

                        JFXButton button1=new JFXButton("关闭");
                        button1.setOnMouseClicked(new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent event) {
                                mag.close();
                            }
                        });
                        dialog.setActions(button1);
                        mag.show();
                    }
                });
            }

        }
        return table;
    }


    public Tab TabTimeTable() {
//        //初始化信息
//        Client client_init = new Client();
//        Message messageSendToServer_init = new Message();
//        messageSendToServer_init.setMessageType(MessageType.course_my_table);
//        messageSendToServer_init.setData(student);
//        //messageSendToServer.setExtraMessage(semesterSelect.getValue());
//        messageSendToServer_init.setExtraMessage("20-21-2");//当前学期
//        Message serverResponse = client_init.sendRequestToServer(messageSendToServer_init);
//        myCourses = (ArrayList<Course>) serverResponse.getData();

        for(int i =0;i<myCourses.size();i++){
            System.out.println(myCourses.get(i).getCourseNumber());
        }

        //界面
        Tab tab = new Tab();
        tab.setText("学生课表");
        StackPane sp = new StackPane();//底板
        AnchorPane ap = new AnchorPane();//图片层
        AnchorPane selet = new AnchorPane();//控件层
        AnchorPane table = new AnchorPane();//课程表底板层
        AnchorPane table_init = TableGenerate(myCourses,sp,week);//课程表内容（button）（可即时更换

        //TODO
        //加一个翻页用的控件，有个值就行
        //控件响应函数用来改week的值然后重新打表，可以参照下面那个查询函数的响应来写
        ImageView left_img = new ImageView("/client/Image/img_left.png");
        left_img.setPreserveRatio(true);
        left_img.setFitHeight(30);
        left_img.setLayoutX(30);
        left_img.setLayoutY(300);
        selet.getChildren().add(left_img);

        ImageView right_img = new ImageView("/client/Image/img_right.png");
        right_img.setPreserveRatio(true);
        right_img.setFitHeight(30);
        right_img.setLayoutX(120);
        right_img.setLayoutY(300);
        selet.getChildren().add(right_img);

        Label weekNum = new Label("第 "+week+" 周");
        weekNum.setPrefWidth(80);
        weekNum.setFont(new Font("微软雅黑",18));
        weekNum.setStyle("-fx-alignment: CENTER");
        weekNum.setLayoutX(50);
        weekNum.setLayoutY(300);
        selet.getChildren().add(weekNum);

        left_img.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            if(week!=1) {
                week--;
                weekNum.setText("第 "+week+" 周");
                //重新打新表
                AnchorPane table_new = new AnchorPane();
                if (myCourses_anotherSeme!=null) table_new = TableGenerate(myCourses_anotherSeme,sp,week);
                else table_new.getChildren().add(new Label("当前查询学期没有课程哦"));//这里改成弹窗也行
                table.getChildren().clear();
                table.getChildren().add(table_new);
            }
        });

        right_img.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            if(week!=16) {
                week++;
                weekNum.setText("第 "+week+" 周");
                //重新打新表
                AnchorPane table_new = new AnchorPane();
                if (myCourses_anotherSeme!=null) table_new = TableGenerate(myCourses_anotherSeme,sp,week);
                else table_new.getChildren().add(new Label("当前查询学期没有课程哦"));//这里改成弹窗也行
                table.getChildren().clear();
                table.getChildren().add(table_new);
            }
        });

        //初始化课表
        if (myCourses!=null) {
            table.getChildren().add( table_init );
        }

        ImageView Timetable = new ImageView("client/Image/timetable.png");
        Timetable.setPreserveRatio(true);
        Timetable.setFitHeight(500);
        Timetable.setLayoutX(200);//200
        Timetable.setLayoutY(20);//20
        ap.getChildren().add(Timetable);

        //课表的位置
        table.setLayoutX(200);
        table.setLayoutY(20);


        //查询学期功能
        JFXComboBox<String> semesterSelect_2 = new JFXComboBox<String>();
        semesterSelect_2.setEditable(false);
        semesterSelect_2.setPromptText("选择学期");
        semesterSelect_2.getItems().add("18-19-3");
        semesterSelect_2.getItems().add("19-20-1");
        semesterSelect_2.getItems().add("19-20-2");
        semesterSelect_2.getItems().add("19-20-3");
        semesterSelect_2.getItems().add("20-21-1");
        semesterSelect_2.getItems().add("20-21-2");
        semesterSelect_2.getItems().add("20-21-3");
        semesterSelect_2.getItems().add("21-22-3");
        selet.getChildren().add(semesterSelect_2);
        selet.setTopAnchor(semesterSelect_2, 200.0);
        selet.setLeftAnchor(semesterSelect_2, 20.0);

        JFXButton commit = new JFXButton("查询");
        commit.setStyle("-jfx-button-type: RAISED;-fx-background-color: #114376;");
        commit.setTextFill(Paint.valueOf("#FFFFFF"));
        commit.setPrefSize(40, 15);
        selet.getChildren().add(commit);
        selet.setTopAnchor(commit, 202.0);
        selet.setLeftAnchor(commit, 140.0);

        commit.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            Client client_querySeme = new Client();
            Message messageSendToServer_querySeme = new Message();
            messageSendToServer_querySeme.setMessageType(MessageType.course_my_table);
            messageSendToServer_querySeme.setData(student);
            System.out.println(semesterSelect_2.getValue());
            messageSendToServer_querySeme.setExtraMessage(String.valueOf(semesterSelect_2.getValue()));
            Message serverResponse_querySeme = client_querySeme.sendRequestToServer(messageSendToServer_querySeme);
            myCourses_anotherSeme = (ArrayList<Course>) serverResponse_querySeme.getData();

            //重新打新表
            //测试输出
            for(int i =0;i<myCourses_anotherSeme.size();i++){
                System.out.println(myCourses_anotherSeme.get(i).getCourseNumber());
            }
            AnchorPane table_new = new AnchorPane();
            if (myCourses_anotherSeme!=null) table_new = TableGenerate(myCourses_anotherSeme,sp,week);
            else table_new.getChildren().add(new Label("当前查询学期没有课程哦"));//这里改成弹窗也行
            table.getChildren().clear();
            table.getChildren().add(table_new);
        });

        ap.getChildren().add(selet);
        sp.getChildren().add(ap);
        ap.getChildren().add(table);
        tab.setContent(sp);
        return tab;
    }
}
