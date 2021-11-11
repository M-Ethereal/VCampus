package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.MessageType;
import vo.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

public class Place_tab_gym {
    private ArrayList<Place> lib = new ArrayList<Place>();
    private ArrayList<PlaceAppointmentRecord> placeAppointmentRecords = new ArrayList<PlaceAppointmentRecord>();//当前查询的场馆的所有预约
    private Student student = new Student();
    private String chosen = "";
    private boolean flag_query = false;
    private boolean flag_drag = false;
    private Place_tab_appointment place_tab_appointment;

    double x_start = 0;
    double x_end = 0;
    //常量-30分钟的长度
    final static double min30 = (700.0-8.0)/8.0;//8是两边的白边+边栏
    //根据查询的是早/中/晚设定时间偏移量
    Integer timeFrame_startTime = 0;
    Integer timeFrame_startTime_next = 0;

    //用于在鼠标拖拽时实时更新坐标
    //方便制作thumb
    double xS = 0;
    double length = 0;
    double xE = 0;

    //图标
    Image img1 = new Image("client/Image/checkMark.png",150, 150,true,true);
    ImageView iv1 = new ImageView(img1);

    //选择框背景格子
    //上午
    Image img_m = new Image("client/Image/timeBlock_m.png",700, 40,true,true);
    ImageView iv_m = new ImageView(img_m);
    //下午
    Image img_a = new Image("client/Image/timeBlock_a.png",700, 40,true,true);
    ImageView iv_a = new ImageView(img_a);
    //晚上
    Image img_n = new Image("client/Image/timeBlock_n.png",700, 40,true,true);
    ImageView iv_n = new ImageView(img_n);

    //格子
    Image img = new Image("client/Image/timeBlock.jpg",700, 100,true,true);
    ImageView iv = new ImageView(img);

    //时间格式转换：转换成分钟
    private Integer timeToMin(String time){
        String[] strTemp = time.split(":");
        Integer h = Integer.valueOf(strTemp[0]);
        Integer m = Integer.valueOf(strTemp[1]);
        Integer time_Min = h * 60 + m;
        System.out.println(h + " " + m + " " + time_Min);
        return time_Min;
    }

    //时间格式转换：判断时段
    private Integer timeTotimeFrame(String time){
        String[] strTemp = time.split(":");
        Integer h = Integer.valueOf(strTemp[0]);
        if(h>=8 && h<12)return 8;
        if(h>=14 && h<18)return 14;
        if(h>=19 && h<23)return 19;
        return -1;
    }

    //冲突判断（既然都显示出来了，那顺便就在前端把冲突判断做了吧（。
    private boolean isConflict(PlaceAppointmentRecord placeAppointmentRecord){
        if(placeAppointmentRecords != null) {
            for (int i = 0; i < placeAppointmentRecords.size(); i++) {
                PlaceAppointmentRecord temp = placeAppointmentRecords.get(i);
                //日期一样,看时段;时段一样,看时间，如果开始时间大于temp的结束时间，或者结束时间小于temp的开始时间，下一个，否则return false
                System.out.println(placeAppointmentRecord.getStartDate() + " " + temp.getStartDate());
                if (placeAppointmentRecord.getStartDate().equals(temp.getStartDate())) {
                    System.out.println(timeTotimeFrame(placeAppointmentRecord.getStartTime()) + " " + timeTotimeFrame(temp.getStartTime()));
                    if (timeTotimeFrame(placeAppointmentRecord.getStartTime()) == timeTotimeFrame(temp.getStartTime())) {
                        String[] strTemp_pr_s = placeAppointmentRecord.getStartTime().split(":");
                        String[] strTemp_temp_s = temp.getStartTime().split(":");
                        Integer h_pr_s = Integer.valueOf(strTemp_pr_s[0]);
                        Integer m_pr_s = Integer.valueOf(strTemp_pr_s[1]);
                        Integer time_Min_pr_s = h_pr_s * 60 + m_pr_s;
                        Integer h_t_s = Integer.valueOf(strTemp_temp_s[0]);
                        Integer m_t_s = Integer.valueOf(strTemp_temp_s[1]);
                        Integer time_Min_t_s = h_t_s * 60 + m_t_s;

                        String[] strTemp_pr_e = placeAppointmentRecord.getEndTime().split(":");
                        String[] strTemp_temp_e = temp.getEndTime().split(":");
                        Integer h_pr_e = Integer.valueOf(strTemp_pr_e[0]);
                        Integer m_pr_e = Integer.valueOf(strTemp_pr_e[1]);
                        Integer time_Min_pr_e = h_pr_e * 60 + m_pr_e;
                        Integer h_t_e = Integer.valueOf(strTemp_temp_e[0]);
                        Integer m_t_e = Integer.valueOf(strTemp_temp_e[1]);
                        Integer time_Min_t_e = h_t_e * 60 + m_t_e;
                        if (time_Min_pr_s > time_Min_t_e || time_Min_pr_e < time_Min_t_s) continue;
                        else return false;
                    }
                }
            }
        }
        return true;
    }

    //构造函数
    public Place_tab_gym(Student student, ArrayList<Place> lib, Place_tab_appointment place_tab_appointment){
        this.lib = lib;
        this.student = student;
        this.place_tab_appointment = place_tab_appointment;

        l_m.setFont(new Font("Arial", 17));//早上
        l_m.setTextFill(Color.web("#000000"));
        l_m.setLayoutX(60);
        l_m.setLayoutY(220);
        l_m.setPrefWidth(800);
        l_m.setPrefHeight(40);
        l_a.setFont(new Font("Arial", 17));//下午
        l_a.setTextFill(Color.web("#000000"));
        l_a.setLayoutX(60);
        l_a.setLayoutY(220);
        l_a.setPrefWidth(800);
        l_a.setPrefHeight(40);
        l_n.setFont(new Font("Arial", 17));//晚上
        l_n.setTextFill(Color.web("#000000"));
        l_n.setLayoutX(60);
        l_n.setLayoutY(220);
        l_n.setPrefWidth(800);
        l_n.setPrefHeight(40);
    }

    //早上、下午、晚上
    private static Label l_m = new Label("08:00         08:30         09:00         09:30         10:00         10:30         11:00         11:30         12:00");
    private static Label l_a = new Label("14:00         14:30         15:00         15:30         16:00         16:30         17:00         17:30         18:00");
    private static Label l_n = new Label("19:00         19:30         20:00         20:30         21:00         21:30         22:00         22:30         23:00");
//    private ArrayList<PlaceAppointmentRecord> placeAppointmentRecordList = new ArrayList<PlaceAppointmentRecord>();

    double finishTime;//选择的开始时间（分钟数）
    double startTime;//选择的结束时间（分钟数）
    String fTime;//选择的开始时间（字符）
    String sTime;//选择的结束时间（字符）
    int Num = 0;

    private static int flag = 0;

//    public double getSX(double time) {//分钟数转换为坐标
//        return (time/60-9)/8*674+250;
//    }

    //坐标转换为分钟数(388-1084)
    //(x-388)/696 * 8
    public double getTime(double sX) {
        return (sX-388)/696 * 8 * 30;
    }

    //确认窗口
    public void BookingPlace(PlaceAppointmentRecord placeAppointmentRecord, Button b4){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setContentText("确定要预约该时间段吗？\n  日期："+placeAppointmentRecord.getStartDate()+"\n  开始时间："+placeAppointmentRecord.getStartTime()+"\n  结束时间："+placeAppointmentRecord.getEndTime()+"\n");
        alert.setGraphic(iv1);

        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //info.setText(“btn6 点击了确定”);

            System.out.println("提示窗口：" + placeAppointmentRecord.getStartDate()+" "+ placeAppointmentRecord.getStartTime()+" "+placeAppointmentRecord.getEndTime());

            if (isConflict(placeAppointmentRecord) == true) {
                Client client_ap = new Client();
                Message sendToServer_ap = new Message();
                sendToServer_ap.setMessageType(MessageType.place_ap_insert);
                sendToServer_ap.setData(placeAppointmentRecord);
                Message serverResponse_ap = client_ap.sendRequestToServer(sendToServer_ap);

                place_tab_appointment.tool_add_a_record(placeAppointmentRecord);

                JFXDialogLayout layout_suc = new JFXDialogLayout();
                layout_suc.setBody(new Label("      预约成功"));
                JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) b4.getScene().getWindow());
                alert_suc.setOverlayClose(true);
                alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert_suc.setContent(layout_suc);
                alert_suc.initModality(Modality.NONE);
                alert_suc.show();
            } else {
                JFXDialogLayout layout_f = new JFXDialogLayout();
                layout_f.setBody(new Label("      预约失败：与已有的预约时间冲突"));
                JFXAlert<Void> alert_f = new JFXAlert<>((Stage) b4.getScene().getWindow());
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
    }


    //页面
    public AnchorPane tab_gym(){
        AnchorPane pane = new AnchorPane();
        pane.setVisible(false);

        AnchorPane ap_words = new AnchorPane();
        ap_words.getChildren().addAll(l_m,l_a,l_n);
        pane.getChildren().add(ap_words);
        l_m.setVisible(false);
        l_a.setVisible(false);
        l_n.setVisible(false);

        JFXDatePicker datePicker = new JFXDatePicker();
        datePicker.setPromptText("选择预约日期");
        datePicker.setLayoutX(100);
        datePicker.setLayoutY(50);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Label label_1 = new Label("场馆名称：");
        label_1.setLayoutX(100);
        label_1.setLayoutY(100);
        Label label_pName = new Label("");
        label_pName.setLayoutX(100);
        label_pName.setLayoutY(130);

        Label label_2 = new Label("场馆所在校区：");
        label_2.setLayoutX(300);
        label_2.setLayoutY(100);
        Label label_pCamp = new Label("");
        label_pCamp.setLayoutX(300);
        label_pCamp.setLayoutY(130);

        Label label_3 = new Label("场馆具体位置：");
        label_3.setLayoutX(500);
        label_3.setLayoutY(100);
        Label label_pPos = new Label("");
        label_pPos.setLayoutX(500);
        label_pPos.setLayoutY(130);


        Collections.sort(lib, new Comparator<Place>(){
            public int compare(Place p1, Place p2) {
                return p1.getPlaceName().compareTo(p2.getPlaceName());
            }
        });

        JFXListView<Label> list = new JFXListView<Label>();
//        list.setLayoutX(0);
//        list.setLayoutY(5);
        list.setPrefWidth(200);
        list.setPrefHeight(530);

        System.out.println(lib.size());
        for(int i = 0; i < lib.size(); i ++){
            System.out.println(lib.get(i).getPlaceName());
            Label temp = new Label(lib.get(i).getPlaceName());
            list.getItems().add(temp);
            Place place = lib.get(i);
            temp.setOnMouseClicked(new EventHandler<MouseEvent>()  {
                public void handle(MouseEvent event) {
                    pane.setVisible(true);
                    chosen = place.getPlaceName();
                    label_pName.setText(place.getPlaceName());
                    label_pCamp.setText(place.getpCampus());
                    label_pPos.setText(place.getPlacePosition());
                }
            });
        }

//        list.setPrefSize(200,515);

        JFXComboBox<String> timeSelect = new JFXComboBox<String>();
        timeSelect.setLayoutX(350);
        timeSelect.setLayoutY(50);
        timeSelect.setEditable(false);
        timeSelect.setPromptText("选择时间段");
        timeSelect.getItems().add("上午");
        timeSelect.getItems().add("下午");
        timeSelect.getItems().add("晚上");

        JFXButton btn = new JFXButton("查询");
        btn.setLayoutX(620);
        btn.setLayoutY(50);
        btn.setStyle("-fx-font-size: 14px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-pref-width: 100;-fx-text-fill: WHITE;");

        //确定按钮
        JFXButton b4 = new JFXButton("确定预约");
        b4.setStyle("-fx-font-size: 14px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-text-fill: WHITE;");
        b4.setLayoutX(365);
        b4.setLayoutY(430);

//        Group an = new Group();

        //可拖选方格
        Button b1 = new Button();
//        b1.setStyle("-fx-background-color:#987654");
        b1.setPrefWidth(698);
        b1.setPrefHeight(86);
        b1.setLayoutX(3);
        b1.setLayoutY(4);
        b1.setOpacity(0.0);//透明

        //拖选后生成方格
        Button b6 = new Button();
//        b6.setStyle("-fx-background-color:#980054");
        b6.setLayoutX(3);
        b6.setLayoutY(4);
        b6.setPrefHeight(86);
        b6.setVisible(false);


        StackPane anStart = new StackPane();
        StackPane anEnd = new StackPane();
        Image img_bubble1 = new Image("client/Image/bubble.png",50, 30,false,true);
        ImageView iv_bubble1 = new ImageView(img_bubble1);
        iv_bubble1.setOpacity(0.7);
        Image img_bubble2 = new Image("client/Image/bubble.png",50, 30,false,true);
        ImageView iv_bubble2 = new ImageView(img_bubble2);
        iv_bubble2.setOpacity(0.7);

        Label startTimeLabel = new Label();
        startTimeLabel.setText(sTime);
        startTimeLabel.setTextFill(Paint.valueOf("#FFFFFF"));
//        startTimeLabel.setLayoutX(3);
//        startTimeLabel.setLayoutY(104);
//        startTimeLabel.setVisible(false);
//        startTimeLabel.setStyle("-fx-background-color:#ADFF2F");
//        startTimeLabel.setPrefWidth(100);
//        startTimeLabel.setPrefHeight(20);


        Label endTimeLabel = new Label();
        endTimeLabel.setText(fTime);
        endTimeLabel.setTextFill(Paint.valueOf("#FFFFFF"));
//        endTimeLabel.setLayoutX(3);
//        endTimeLabel.setLayoutY(104);
//        endTimeLabel.setVisible(false);
//        endTimeLabel.setStyle("-fx-background-color:#00cd05");
//        endTimeLabel.setPrefWidth(100);
//        endTimeLabel.setPrefHeight(20);

        anStart.getChildren().addAll(iv_bubble1,startTimeLabel);
        anStart.setAlignment(startTimeLabel, Pos.CENTER);
        anEnd.getChildren().addAll(iv_bubble2,endTimeLabel);
        anEnd.setAlignment(endTimeLabel,Pos.CENTER);

        Label bubble_start = new Label();
        Label bubble_end = new Label();
        bubble_start.setGraphic(anStart);
        bubble_start.setLayoutX(3);
        bubble_start.setLayoutY(92);
        bubble_start.setVisible(false);

        bubble_end.setGraphic(anEnd);
        bubble_end.setLayoutX(3);
        bubble_end.setLayoutY(92);
        bubble_end.setVisible(false);

        //叠叠乐区域:叠一层背景图，一层灰色button，一层拖拽button
        //StackPane stackPane = new StackPane();
        AnchorPane stackPane = new AnchorPane();
        stackPane.setLayoutX(60);
        stackPane.setLayoutY(260);
//        stackPane.setPrefWidth(800);
//        stackPane.setPrefHeight(40);
        AnchorPane anchorPane = new AnchorPane();
        //stackPane.getChildren().add(anchorPane);
        anchorPane.setPrefSize(700,100);

        AnchorPane dragBlock = new AnchorPane();
        dragBlock.getChildren().addAll(b6, b1,bubble_start,bubble_end);
//        dragBlock.setLayoutX(3);
//        dragBlock.setLayoutY(8);

        stackPane.getChildren().addAll(iv, anchorPane, dragBlock);
        stackPane.setVisible(true);



        //查询按钮响应
        //查出预约表中所有该场馆已经存在的未完成预约（这里需要一个判断函数：若当前时间大于该预约记录的结束时间则判定预约已完成，非已完成的即是未完成预约）
        //显示出这些预约的时间段，用灰色方格表示（又是喜闻乐见的算坐标time————！）
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //先要检查选取情况，上面的两个选择要都填好
                System.out.println(timeSelect.getValue()+" " + datePicker.getValue());
                if (timeSelect.getValue()==null || datePicker.getValue()==null)
                {
                    JFXDialogLayout layout_f = new JFXDialogLayout();
                    layout_f.setBody(new Label("      请填写完整您的查询信息！"));
                    JFXAlert<Void> alert_f = new JFXAlert<>((Stage) btn.getScene().getWindow());
                    alert_f.setOverlayClose(true);
                    alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_f.setContent(layout_f);
                    alert_f.initModality(Modality.NONE);
                    alert_f.show();
                }
                else {
                    flag_query = true;
                    //选中场馆的预约情况
                    Client client_qAP = new Client();
                    Message sendToServer_qAp = new Message();
                    sendToServer_qAp.setMessageType(MessageType.place_ap_query);
                    sendToServer_qAp.setExtraMessage(chosen);
                    Message serverResponse_qAp = client_qAP.sendRequestToServer(sendToServer_qAp);
                    placeAppointmentRecords = (ArrayList<PlaceAppointmentRecord>) serverResponse_qAp.getData();

                    //anchorPane.getChildren().clear();
                    //直接用clear会出问题
                    //？太愚了，突然发现没分图层
//                    System.out.println(anchorPane.getChildren().size());
//                    //鬼鬼，每次循环size会变，
//                    Integer size = anchorPane.getChildren().size();
//                    for (int a = 2; a < size; a++)
//                    {
//                        System.out.println(a + " "+anchorPane.getChildren().get(a));
////                        if(anchorPane.getChildren().get(a).equals(b1)) continue;
////                        if(anchorPane.getChildren().get(a).equals(b6)) continue;
//                        anchorPane.getChildren().remove(anchorPane.getChildren().get(a));
//                    }
                    anchorPane.getChildren().clear();
//                    anchorPane.getChildren().addAll(startTimeLabel, endTimeLabel, b1,b6);

                    if(timeSelect.getValue().equals("上午")){
                        timeFrame_startTime = 8;
                        timeFrame_startTime_next = 14;
                        l_m.setVisible(true);
                        l_a.setVisible(false);
                        l_n.setVisible(false);
//                    iv_m.setVisible(true);
//                    iv_a.setVisible(false);
//                    iv_n.setVisible(false);
                    }
                    else if(timeSelect.getValue().equals("下午")) {
                        timeFrame_startTime = 14;
                        timeFrame_startTime_next = 19;
                        l_a.setVisible(true);
                        l_m.setVisible(false);
                        l_n.setVisible(false);
//                    iv_a.setVisible(true);
//                    iv_m.setVisible(false);
//                    iv_n.setVisible(false);
                    }
                    else if(timeSelect.getValue().equals("晚上")) {
                        timeFrame_startTime = 19;
                        timeFrame_startTime_next = 23;
                        l_n.setVisible(true);
                        l_a.setVisible(false);
                        l_m.setVisible(false);
//                    iv_n.setVisible(true);
//                    iv_a.setVisible(false);
//                    iv_m.setVisible(false);
                    }

                    if(placeAppointmentRecords!=null) {
                        for (int i = 0; i < placeAppointmentRecords.size(); i++) {
                            //根据查询条件筛选
                            PlaceAppointmentRecord PRtemp = placeAppointmentRecords.get(i);
                            System.out.println("预约记录日期：" + PRtemp.getStartDate() + "查询日期：" + datePicker.getValue().format(dtf));

                            if (PRtemp.getStartDate().equals(datePicker.getValue().format(dtf)))//判断日期
                            {
                                Integer temp = Integer.valueOf(PRtemp.getStartTime().split(":")[0]);
//                            System.out.println(temp + " " +timeFrame_startTime + " " +timeFrame_startTime_next );

                                if (temp >= timeFrame_startTime && temp < timeFrame_startTime_next) {
                                    System.out.println("预约记录时间：" + temp + " 查询时间段—开始：" + timeFrame_startTime + " 查询时间段-结束：" + timeFrame_startTime_next);
                                    Label disabled = new Label();
                                    //blockLength:
                                    //总长度700-6，一共8个格子，30分钟的长度是（700-6）/8
                                    //计算预约时长（换算成分钟），除以30再乘常量即为格子长度
                                    //offset_x:
                                    //将开始时间换算成分钟，减当前开头的时间，该时间段除以30再乘常量即为x偏移量
                                    double offset_x = (timeToMin(PRtemp.getStartTime()) - timeFrame_startTime * 60) / 30.0 * min30;
                                    double blockLength = (timeToMin(PRtemp.getEndTime()) - timeToMin(PRtemp.getStartTime())) / 30.0 * min30;
                                    System.out.println(min30 + " " + offset_x + " " + blockLength + " " + (timeToMin(PRtemp.getEndTime()) - timeToMin(PRtemp.getStartTime())));
                                    disabled.setPrefSize(blockLength, 87);
                                    disabled.setLayoutX(3 + offset_x);
                                    disabled.setLayoutY(4);
                                    disabled.setStyle("-fx-background-color: GRAY");
                                    disabled.setOpacity(0.5);

                                    anchorPane.getChildren().add(disabled);
                                }
                            }
                        }
                    }
                }
                //永久置顶
                if(stackPane.getChildren().contains(dragBlock)){
                    stackPane.getChildren().remove(dragBlock);
                    stackPane.getChildren().add(dragBlock);
                }
                //先隐藏起来
                b6.setVisible(false);
                bubble_start.setVisible(false);
                bubble_end.setVisible(false);
            }
        });

        //鼠标拖拽判定开始
        //x轴：390-1088
        b1.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event){
                b1.startFullDrag();
                double t = event.getSceneX();
//                if (t >= 1088) t = 1088;
//                if (t <= 390) t = 390;
                x_start = t;
                startTime = getTime(t)+timeFrame_startTime*60;
//                b6.setVisible(false);
//                startTimeLabel.setVisible(false);
//                endTimeLabel.setVisible(false);
//                startTimeLabel.setLayoutX(xS);
//                endTimeLabel.setLayoutX(xE);
//                bubble_start.setVisible(false);
//                bubble_end.setVisible(false);
            }
        });

        //拖拽生成图形
        b1.setOnMouseDragOver(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getSceneX());
                double t = event.getSceneX();
                //其实不会越界的，因为超出底层button的范围之后就不在监听范围内了
//                if (t >= 1080) t = 1080;
//                if (t <= 390) t = 390;
                x_end = t;
                finishTime = getTime(t)+timeFrame_startTime*60;
                length = Math.abs(x_end - x_start)+7;
                if(Math.min(x_end, x_start)-388>=2){
                    xS = Math.min(x_end, x_start)-388;
                }
                else {
                    length = length + (Math.min(x_end, x_start)-390);
                    xS = 2;
                }
                xE = xS + length;

                System.out.println("dragging");
                System.out.println(xS + " "  + length);
                Integer h1 = (int)Math.floor(startTime/60);
                Integer m1 = (int)Math.floor(startTime%60);
                //finishTime = getTime(b6.getPrefWidth()+getSX(startTime));
                Integer h2 = (int)Math.floor(finishTime/60);
                Integer m2 = (int)Math.floor(finishTime%60);
                if (m1 < 10) {
                    sTime = h1+":0"+m1;
                }
                else {
                    sTime = h1+":"+m1;
                }
                if (m2 < 10) {
                    fTime = h2+":0"+m2;
                }
                else {
                    fTime = h2+":"+m2;
                }
//                System.out.println(sTime + " " + fTime);
//                if (finishTime < startTime){
//                    String Temp = sTime;
//                    sTime = fTime;
//                    fTime = Temp;
//                    double Temp1 = startTime;
//                    startTime = finishTime;
//                    finishTime = Temp1;
//                }
//                System.out.println(startTime + " "  + finishTime);

                b6.setVisible(true);
                b6.setStyle("-fx-background-color:#4876FF55");
                b6.setLayoutX(xS);
                b6.setPrefWidth(length);

                if(finishTime < startTime)
                {
                    startTimeLabel.setText(fTime);
                    endTimeLabel.setText(sTime);
                }
                else
                {
                    startTimeLabel.setText(sTime);
                    endTimeLabel.setText(fTime);
                }
//                startTimeLabel.setVisible(true);
//                startTimeLabel.setStyle("-fx-background-color:#66CD00");
//                startTimeLabel.setLayoutX(xS-5);
//
//                endTimeLabel.setVisible(true);
//                endTimeLabel.setStyle("-fx-background-color:#66CD00");
//                endTimeLabel.setLayoutX(xE-5);
                bubble_start.setVisible(true);
                bubble_start.setLayoutX(xS-20);

                bubble_end.setVisible(true);
                bubble_end.setLayoutX(xE-20);

                flag_drag = true;
            }
        });


        //确定按键
        b4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (flag_query == true && flag_drag == true){
                    Integer h1 = (int)Math.floor(startTime/60);
                    Integer m1 = (int)Math.floor(startTime%60);
                    //finishTime = getTime(b6.getPrefWidth()+getSX(startTime));
                    Integer h2 = (int)Math.floor(finishTime/60);
                    Integer m2 = (int)Math.floor(finishTime%60);
                    if (m1 < 10) {
                        sTime = h1+":0"+m1;
                    }
                    else {
                        sTime = h1+":"+m1;
                    }
                    if (m2 < 10) {
                        fTime = h2+":0"+m2;
                    }
                    else {
                        fTime = h2+":"+m2;
                    }
//                System.out.println(sTime + " " + fTime);
                    if (finishTime < startTime){
                        String Temp = sTime;
                        sTime = fTime;
                        fTime = Temp;
                        double Temp1 = startTime;
                        startTime = finishTime;
                        finishTime = Temp1;
                    }

                    System.out.print(sTime+"---");
                    System.out.println(fTime);
                    System.out.println(sTime + " " + fTime);

                    //在这里包一个PlaceAppointmentRecord对象
                    PlaceAppointmentRecord placeAppointmentRecord = new PlaceAppointmentRecord();
                    placeAppointmentRecord.setStartDate(datePicker.getValue().format(dtf));
                    placeAppointmentRecord.setStartTime(sTime);
                    placeAppointmentRecord.setEndTime(fTime);
                    placeAppointmentRecord.setPlaceName(chosen);
                    placeAppointmentRecord.setPlaceId("");//很麻烦，这条属性我不想要了，空着就可以，反正后面避开它就行了
                    placeAppointmentRecord.setStuName(student.getName());
                    placeAppointmentRecord.setStuId(student.getId());

                    if(isConflict(placeAppointmentRecord) == true)
                        BookingPlace(placeAppointmentRecord,b4);
                    else
                    {
                        JFXDialogLayout layout_f = new JFXDialogLayout();
                        layout_f.setBody(new Label("      请不要选择已经被预约的时段！"));
                        JFXAlert<Void> alert_f = new JFXAlert<>((Stage) btn.getScene().getWindow());
                        alert_f.setOverlayClose(true);
                        alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                        alert_f.setContent(layout_f);
                        alert_f.initModality(Modality.NONE);
                        alert_f.show();
                    }
                }
                else {
                    JFXDialogLayout layout_f = new JFXDialogLayout();
                    layout_f.setBody(new Label("      您还没有选择预约时间！"));
                    JFXAlert<Void> alert_f = new JFXAlert<>((Stage) btn.getScene().getWindow());
                    alert_f.setOverlayClose(true);
                    alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_f.setContent(layout_f);
                    alert_f.initModality(Modality.NONE);
                    alert_f.show();
                }
            }
        });

//取消预约按键
//        b5.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                startTime = 0.0;
//                finishTime = 0.0;
//                sTime = "00:00";
//                fTime = "00:00";
//                b6.setVisible(false);
//                Dialog<ButtonType> dialog = new Dialog<ButtonType>();
//                dialog.setContentText("是否确定取消预约");
//                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
//                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
//                Button ok = (Button)dialog.getDialogPane().lookupButton(ButtonType.OK);
//                ok.setOnAction(new EventHandler<ActionEvent>() {
//                    public void handle(ActionEvent event) {
//
//                    }
//                });
//                dialog.show();
//            }
//        });

        AnchorPane bottom = new AnchorPane();

        pane.getChildren().addAll(datePicker,timeSelect,label_1,label_2,label_3,label_pName,label_pCamp,label_pPos,btn,stackPane,b4);
        pane.setLayoutX(200);
        AnchorPane listPane = new AnchorPane();
        listPane.getChildren().add(list);
//        tempPane.setPrefWidth(230);
//        tempPane.setPrefHeight(530);
        //StackPane layer_bottom = new StackPane();
        bottom.getChildren().addAll(pane,listPane);

        return bottom;
    }

}
