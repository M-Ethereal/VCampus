package client.clientUI;

import client.socket.Client;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import util.BorderedTitledPane;
import util.MessageType;
import vo.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
咨询记录页：
需求：对咨询表 queryBystuId，打表，点击该按钮时页面跳转
 */

public class Hospital_tab_query_Doctor {
    Doctor doctor = new Doctor();
    ArrayList<MedicalAdvice> advices = new ArrayList<MedicalAdvice>();//所有咨询记录
    private AnchorPane jumpPage = new AnchorPane();//所有跳转页都放这里
    private AnchorPane adviceDetailPage = new AnchorPane();//一跳跳转页
    private AnchorPane adviceQueryPage = new AnchorPane();//二跳跳转页

    public Hospital_tab_query_Doctor(Doctor doctor){
        this.doctor = doctor;
    }

    public void Hospital_jump_control(AnchorPane anchorPane){
        jumpPage.getChildren().clear();
        jumpPage.getChildren().add(anchorPane);
        jumpPage.setVisible(true);
    }

    //0-内科 1-外科 2-皮肤科 3-耳鼻喉科 4-口腔 5-眼科 6-心理咨询
    private String checkList(Integer i){
        if(i == 0) return "内科";
        else if(i == 1) return "外科";
        else if(i == 2) return "皮肤科";
        else if(i == 3) return "耳鼻喉科";
        else if(i == 4) return "口腔";
        else if(i == 5) return "眼科";
        else if(i == 6) return "心理咨询";
        else
            return null;
    }

    //关闭页面
    public void jumpTool_close(){
        this.jumpPage.setVisible(false);
    }

    //开启页面
    public void jumpTool_open(){
        this.jumpPage.setVisible(true);
    }

    //打表函数，生成装有button阵列的列表(医生名字，科室，问题--跳转：医疗咨询详情（参数：MAd
    public FlowPane advicesListGenerate(ArrayList<MedicalAdvice> adviceList ) {
        FlowPane fakeVBox = new FlowPane();
        for (int i = 0; i < adviceList.size(); i++) {
            //System.out.println(adviceList.get(i).getCourseName());
            AnchorPane Qbutton = new AnchorPane();
            MedicalAdvice advice = adviceList.get(i);
            Qbutton.setPrefSize(700, 100);
            Label docName = new Label(advice.getStuName());
            Label queryTime = new Label(advice.getQueryTime());
            Label ques = new Label(advice.getqQuestion());//2-1;4-3
            Label queryDate = new Label(advice.getQueryDate());//1-16


//            Label CID = new Label(courseList.get(i).getCourseNumber());
//            Label CName = new Label(courseList.get(i).getCourseName());
//            CID.setVisible(false);
//            CName.setVisible(false);

            docName.setStyle("-fx-font-size:25px");
            docName.setLayoutX(50);
            docName.setLayoutY(35);

            queryDate.setStyle("-fx-font-size:15px");
            queryDate.setLayoutX(150);
            queryDate.setLayoutY(43);

            queryTime.setStyle("-fx-font-size:15px");
            queryTime.setLayoutX(250);
            queryTime.setLayoutY(43);


            ques.setStyle("-fx-font-size:15px");
            ques.setPrefWidth(350);
            ques.setLayoutX(350);
            ques.setLayoutY(43);

            Qbutton.getChildren().addAll(docName, queryDate, queryTime, ques);
            JFXButton b1 = new JFXButton();
            b1.setStyle("-jfx-button-type: RAISED;-fx-background-color: rgb(245,245,245);");
            b1.setGraphic(Qbutton);
            fakeVBox.getChildren().add(b1);

            Qbutton.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {

                adviceDetailPage = new Hospital_jump_MedicalAdviceInfo_Doctor(advice, this).medicalAdviceInfo();
                adviceDetailPage.setVisible(true);
                jumpPage.getChildren().clear();
                jumpPage.getChildren().add(adviceDetailPage);
                jumpPage.setVisible(true);

            });
        }
        return fakeVBox;
    }

    //主函数
    public Tab TabAdviceList(){
        Tab tab = new Tab();
        tab.setText("咨询记录");
        ScrollPane scp = new ScrollPane();

        //表格初始化
        Client client_q = new Client();
        Message msgSendToServer_q = new Message();
        msgSendToServer_q.setMessageType(MessageType.stu_advice_query_doc);
        msgSendToServer_q.setExtraMessage(doctor.getId());
        Message serverResponse = client_q.sendRequestToServer(msgSendToServer_q);
        ArrayList<MedicalAdvice> tempTable = new ArrayList<MedicalAdvice>();
        if (serverResponse.getData()!=null) tempTable = (ArrayList<MedicalAdvice>) serverResponse.getData();
        advices = tempTable;

        //主标题
        Label mainTitle = new Label("点击查看医疗咨询记录");
        mainTitle.setStyle("-fx-font-size:30px");

        //表初始化
        Collections.sort(advices, new Comparator<MedicalAdvice>(){
            public int compare(MedicalAdvice ma1, MedicalAdvice ma2) {
                if(ma1.getQueryDate().equals(ma2.getQueryDate()))
                {
                    return ma1.getQueryTime().compareTo(ma2.getQueryTime());
                }
                else return ma1.getQueryDate().compareTo(ma2.getQueryDate());
            }
        });

        AnchorPane ap_inner = new AnchorPane();
        FlowPane fp = new FlowPane();
        fp = advicesListGenerate(advices);
        fp.setVgap(10);
        ap_inner.getChildren().add(fp);
        ap_inner.setTopAnchor(fp,10.0);
        ap_inner.setLeftAnchor(fp,10.0);

        JFXButton commit = new JFXButton("刷新");
        commit.setStyle("-jfx-button-type: RAISED;-fx-background-color: #114376;");
        commit.setTextFill(Paint.valueOf("#FFFFFF"));
        commit.setPrefSize(40, 15);

//        FlowPane fakeVBox = new FlowPane();
//
//        VBox list = new VBox();
        //初始化

//        FlowPane fp1 = advicesListGenerate(advices);
//        fp1.setVgap(10);
//        if (advices!=null) {
//            list.getChildren().add( fp1 );
//        }


//        commit.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
//            myCourses_update.clear();
//            Client client_refresh = new Client();
//            Message messageSendToServer_refresh = new Message();
//            messageSendToServer_refresh.setMessageType(MessageType.course_my_table);
//            messageSendToServer_refresh.setData(student);
//            messageSendToServer_refresh.setExtraMessage("20-21-2");//当前学期
//            Message serverResponse_refresh = client_refresh.sendRequestToServer(messageSendToServer_refresh);
//            myCourses_update = (ArrayList<Course>) serverResponse_refresh.getData();
//
//            courseListGenerate(myCourses_update);
//
//            FlowPane fptemp = new FlowPane();
//            if (myCourses_update!=null) fptemp = courseListGenerate(myCourses_update);
//            else fptemp.getChildren().add(new Label("本学期您已经没有课程了哦"));
//            list.getChildren().clear();
//            list.getChildren().add(fptemp);
//        });

//        fakeVBox.getChildren().add(list);

        //框
        BorderedTitledPane btp = new BorderedTitledPane("我的提问", ap_inner);
        btp.getStylesheets().add(getClass().getResource("BorderedTitledPane_CSS.css").toExternalForm());
        btp.setTitleAlignment(Pos.TOP_LEFT);

        //主页面布局
        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(btp, mainTitle, commit);
        ap.setTopAnchor(mainTitle, 50.0);
        ap.setLeftAnchor(mainTitle, 50.0);
        ap.setTopAnchor(commit, 105.0);
        ap.setLeftAnchor(commit, 180.0);
        ap.setTopAnchor(btp, 160.0);
        ap.setLeftAnchor(btp, 40.0);

        //滚轮
        scp.setContent(ap);

        StackPane sp = new StackPane();//图层
        sp.getChildren().addAll(scp,jumpPage);
        //ap.getChildren().addAll(tab_home,jumpPage);
        jumpPage.setVisible(false);

        tab.setContent(sp);
        return tab;
    }
}
