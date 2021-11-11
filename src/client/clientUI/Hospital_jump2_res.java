package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.MessageType;
import vo.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;


/*
挂号页：二跳的界面
需求：
根属于医院主页，由医院主页下的一级跳转页维护，
单入口：从主页下的一跳页面能跳转过来（由一跳页面进行传递
单出口（提交/返回）：结束之后跳转到咨询记录页面（需要得到咨询主页的jumptool函数

静态页面，无刷新操作
*/
public class Hospital_jump2_res {
    private ArrayList<DoctorSchedule> doctorSchedules = new ArrayList<DoctorSchedule>();
    private Hospital_tab_home hospital_tab_home;//父窗口句柄
    private Doctor doctor;
    private Student student;
    RegistrationRecord registrationRecordTemp = new RegistrationRecord();

    //背景
    Image bg = new Image("/client/Image/img2.jpg",1058, 580,false,true);
    ImageView backgroundView = new ImageView(bg);

    //医生图标
    Image img1 = new Image("client/Image/doctor.png",150, 150,true,true);
    ImageView iv1 = new ImageView(img1);


    //构造函数
    public Hospital_jump2_res(Student student, Doctor doctor, Hospital_tab_home hospital_tab_home){
        this.student = student;
        this.doctor = doctor;
        this.hospital_tab_home = hospital_tab_home;
    }

    //时间合法性检查
    public Boolean validityCheck(int time_slct_h, int t_schd){//1：  8：00-12：00 ，2：  14：00-17：00  ， 3：  19：00-23：00
        int startTime, endTime;
        if(t_schd == 1){
            startTime = 8;
            endTime = 12;
        }
        else if(t_schd == 2){
            startTime = 14;
            endTime = 17;
        }
        else
        {
            startTime = 19;
            endTime = 23;
        }
        if(time_slct_h < startTime || time_slct_h >= endTime) return false;
        return true;
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

    //确认窗口
    private void showConfirmation(Button b1, int scheDay)//第二个参数是Integer.valueOf(doctorScheduleTemp.getScheduleInDay()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        //alert.setContentText("请选择预约时间：");
        //alert.setGraphic(layout);
        //alert.setGraphic(iv1);

        //HBox alertHBox = new HBox();

        Label msg = new Label("请选择预约时间：");

        JFXTimePicker timePicker = new JFXTimePicker();
        timePicker.setLayoutX(40);
        timePicker.setLayoutY(40);

        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().addAll(iv1,timePicker);

        VBox vb = new VBox(msg,flowPane);
        vb.setSpacing(10);

        alert.setGraphic(vb);
        //Label errorMsg = new Label("您选择的");

        //alertHBox.getChildren().addAll(msg);

       // VBox alertVBox = new VBox();

       // alertVBox.getChildren().addAll(alertHBox,timePicker);

        //DialogPane dp = new DialogPane();
        //dp.setContent(alertVBox);
        //alert.setDialogPane(dp);

        Optional result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
//            String time = timePicker.getValue().format(dtf);
            Integer hour = timePicker.getValue().getHour();
            Integer min = timePicker.getValue().getMinute();

            //时间合法性检测未通过
            if(validityCheck(hour, scheDay)==false){
                JFXDialogLayout layout = new JFXDialogLayout();
                layout.setBody(new Label("     失败：您的预约时间不在医生出诊时间内"));
                JFXAlert<Void> alert_f = new JFXAlert<>((Stage) b1.getScene().getWindow());
                alert_f.setOverlayClose(true);
                alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert_f.setContent(layout);
                alert_f.initModality(Modality.NONE);
                alert_f.show();
            }
            //时间合法，添加预约
            else {
                String time = "";
                if(timePicker.getValue().getMinute()<=9)
                    time = timePicker.getValue().getHour()+":0"+timePicker.getValue().getMinute();
                else
                    time = timePicker.getValue().getHour()+":"+timePicker.getValue().getMinute();
                //String apTime = String.valueOf(hour) + ":" + String.valueOf(min);
                registrationRecordTemp.setApTime(time);

                Client client_res = new Client();
                Message sendToServer = new Message();
                sendToServer.setData(registrationRecordTemp);
                sendToServer.setMessageType(MessageType.stu_res_insert);
                Message serverResponse = client_res.sendRequestToServer(sendToServer);

                if (serverResponse.isLastOperState()) {
                    JFXDialogLayout layout_suc = new JFXDialogLayout();
                    layout_suc.setBody(new Label("      挂号成功"));
                    JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) b1.getScene().getWindow());
                    alert_suc.setOverlayClose(true);
                    alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_suc.setContent(layout_suc);
                    alert_suc.initModality(Modality.NONE);
                    alert_suc.show();
                } else {
                    JFXDialogLayout layout_f = new JFXDialogLayout();
                    layout_f.setBody(new Label("      挂号失败，请检查您的网络设置"));
                    JFXAlert<Void> alert_f = new JFXAlert<>((Stage) b1.getScene().getWindow());
                    alert_f.setOverlayClose(true);
                    alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_f.setContent(layout_f);
                    alert_f.initModality(Modality.NONE);
                    alert_f.show();
                }
            }

        } else {
            //info.setText(“btn6 点击了取消”);
            // alert.hide();
        }


    }



    private String apType(String docPos){
        String type = "";
        if (docPos.equals("实习医师")) type = "普通号";
        if (docPos.equals("主治医师")) type = "普通号";
        if (docPos.equals("副主任医师")) type = "专家号";
        if (docPos.equals("主任医师")) type = "特需号";

        return type;
    }

    private double apCost(String docPos){
        double cost = 0;
        if (docPos.equals("实习医师")) cost = 10.0;
        if (docPos.equals("主治医师")) cost = 10.0;
        if (docPos.equals("副主任医师")) cost = 50.0;
        if (docPos.equals("主任医师")) cost = 100.0;

        return cost;
    }

    public AnchorPane HospitalResPage(){
        AnchorPane pane = new AnchorPane();

        registrationRecordTemp.setStuId(student.getId());
        registrationRecordTemp.setStuName(student.getName());
        registrationRecordTemp.setDocId(doctor.getId());
        registrationRecordTemp.setDocName(doctor.getName());
        registrationRecordTemp.setApDept(checkList(Integer.valueOf(doctor.getDept())));
        registrationRecordTemp.setApType(apType(doctor.getPosition()));
        registrationRecordTemp.setApCost(apCost(doctor.getPosition()));

        //初始化列表
        Client client_sch = new Client();
        Message sendToServer_sch = new Message();
        sendToServer_sch.setMessageType(MessageType.doc_schedule_query);
        sendToServer_sch.setExtraMessage(doctor.getId());
        Message serverResponse_sch = client_sch.sendRequestToServer(sendToServer_sch);
        doctorSchedules = (ArrayList<DoctorSchedule>) serverResponse_sch.getData();
//        System.out.println(doctorSchedules.size() + " " + doctorSchedules.get(0).getScheduleInWeek() + " " + doctorSchedules.get(0).getScheduleInDay());

        Label label1 = new Label();
        label1.setLayoutX(180);
        label1.setLayoutY(30);
        String DocName = doctor.getName();
        label1.setText(DocName+"医生出诊表");
        label1.setFont(new Font("Microsoft YaHei",26));

        //用户图片
        Circle cir = new Circle();

        String Path="/client/Image/cross.png";
        Image image=new Image(Path);
        ImageView iv=new ImageView(image);
        iv.setPreserveRatio(false);
        iv.setFitWidth(60);
        iv.setFitHeight(60);
        iv.setLayoutX(100);
        iv.setLayoutY(20);
        cir.setCenterX(30);
        cir.setCenterY(30);
        cir.setRadius(28);
        iv.setClip(cir);

        ImageView timetable = new ImageView("/client/Image/RegisterTimeTable.png");
        timetable.setPreserveRatio(true);
        timetable.setFitWidth(750);
        timetable.setLayoutX(70);
        timetable.setLayoutY(100);

        Label lb = new Label("请选择预约时间");
        lb.setLayoutX(850);
        lb.setLayoutY(80);
        lb.setFont(new Font("Microsoft YaHei",15));

        JFXDatePicker datepicker = new JFXDatePicker();
        datepicker.setLayoutX(850);
        datepicker.setLayoutY(110);

        JFXButton b1 = new JFXButton();
        b1.setText("查询");
        b1.setLayoutX(890);
        b1.setLayoutY(180);
        b1.setPrefWidth(100);
        b1.setStyle("-fx-font-size: 14px;\n" +
                "jfx-button-type: RAISED;\n" +
                "-fx-background-color: rgb(77,102,204);\n" +
                "-fx-text-fill: WHITE;");

        AnchorPane picPane = new AnchorPane();
        picPane.setLayoutX(165);
        picPane.setLayoutY(147);

        b1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //未选择时间
                if(datepicker.getValue()==null) {
                    JFXDialogLayout layout = new JFXDialogLayout();
                    layout.setBody(new Label("请选择预约时间！"));
                    JFXAlert<Void> alert = new JFXAlert<>((Stage) b1.getScene().getWindow());
                    alert.setOverlayClose(true);
                    alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert.setContent(layout);
                    alert.initModality(Modality.NONE);
                    alert.showAndWait();
                }
                else {
                    //时间-选取的年月日获取
                    String year = datepicker.getValue().toString().substring(0,4);
                    String month = "";
                    if(datepicker.getValue().toString().substring(5,6).equals("0")){
                        month = datepicker.getValue().toString().substring(6,7);
                    }
                    else{
                        month = datepicker.getValue().toString().substring(5,7);
                    }
                    String day = "";
                    if(datepicker.getValue().toString().substring(8,9).equals("0")){
                        day = datepicker.getValue().toString().substring(9,10);
                    }
                    else{
                        day = datepicker.getValue().toString().substring(8,10);
                    }

                    //预约时间合法性分支-年月日
                    LocalDateTime localDateTime = LocalDateTime.now();
                    if(localDateTime.getYear() > Integer.parseInt(year)){
                        JFXDialogLayout layout = new JFXDialogLayout();
                        layout.setBody(new Label("请选择正确的预约时间！"));
                        JFXAlert<Void> alert = new JFXAlert<>((Stage) b1.getScene().getWindow());
                        alert.setOverlayClose(true);
                        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                        alert.setContent(layout);
                        alert.initModality(Modality.NONE);
                        alert.showAndWait();
                    }
                    else if(localDateTime.getYear()<(Integer.parseInt(year))) {
                        System.out.println(datepicker.getValue().getDayOfWeek());
                        Integer weekday = ChooseDay(picPane, datepicker.getValue().getDayOfWeek());
                        //此处要写一个for循环判断医生所有可预约时间是否在用户预约的这一天，这里只写了打表函数
                        //第一个参数是布局，第二个参数是周几，前两个不用改，第三个应该对应的是1-上午；2-中午；3-下午

                        String date = datepicker.getValue().getYear()+"/"+datepicker.getValue().getMonthValue()+"/" + datepicker.getValue().getDayOfMonth();
                        registrationRecordTemp.setApDate(date);
                        System.out.println(registrationRecordTemp.getApDate());

                        for(int i = 0; i < doctorSchedules.size(); i++){
                            DoctorSchedule doctorScheduleTemp = doctorSchedules.get(i);
                            if (doctorScheduleTemp.getScheduleInWeek() .equals(String.valueOf(weekday))) {
                                System.out.println(datepicker.getValue().getDayOfWeek() + " " + Integer.valueOf(doctorScheduleTemp.getScheduleInDay()));
                                LabelGenerate(picPane, datepicker.getValue().getDayOfWeek(), Integer.valueOf(doctorScheduleTemp.getScheduleInDay()));
                            }
                        }

                    }
                    else {
                        if (localDateTime.getMonthValue() > Integer.parseInt(month)) {
                            JFXDialogLayout layout = new JFXDialogLayout();
                            layout.setBody(new Label("请选择正确的预约时间！"));
                            JFXAlert<Void> alert = new JFXAlert<>((Stage) b1.getScene().getWindow());
                            alert.setOverlayClose(true);
                            alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                            alert.setContent(layout);
                            alert.initModality(Modality.NONE);
                            alert.showAndWait();
                        }
                        else if (localDateTime.getMonthValue() < Integer.parseInt(month)) {
                            System.out.println(datepicker.getValue().getDayOfWeek());
                            Integer weekday = ChooseDay(picPane, datepicker.getValue().getDayOfWeek());
                            //此处要写一个for循环判断医生所有可预约时间是否在用户预约的这一天，这里只写了打表函数
                            //第一个参数是布局，第二个参数是周几，前两个不用改，第三个应该对应的是1-上午；2-中午；3-下午

                            String date = datepicker.getValue().getYear()+"/"+datepicker.getValue().getMonthValue()+"/" +datepicker.getValue().getDayOfMonth();
                            registrationRecordTemp.setApDate(date);
                            System.out.println(registrationRecordTemp.getApDate());

                            for(int i = 0; i < doctorSchedules.size(); i++){
                                DoctorSchedule doctorScheduleTemp = doctorSchedules.get(i);

                                if (doctorScheduleTemp.getScheduleInWeek() .equals(String.valueOf(weekday))) {
                                    System.out.println(datepicker.getValue().getDayOfWeek() + " " + Integer.valueOf(doctorScheduleTemp.getScheduleInDay()));
                                    LabelGenerate(picPane, datepicker.getValue().getDayOfWeek(), Integer.valueOf(doctorScheduleTemp.getScheduleInDay()));
                                }
                            }
                        }
                        else {
                            if (localDateTime.getDayOfMonth() > Integer.parseInt(day)) {
                                JFXDialogLayout layout = new JFXDialogLayout();
                                layout.setBody(new Label("请选择正确的预约时间！"));
                                JFXAlert<Void> alert = new JFXAlert<>((Stage) b1.getScene().getWindow());
                                alert.setOverlayClose(true);
                                alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                                alert.setContent(layout);
                                alert.initModality(Modality.NONE);
                                alert.showAndWait();
                            }
                            else {
                                System.out.println(datepicker.getValue().getDayOfWeek());
                                Integer weekday = ChooseDay(picPane, datepicker.getValue().getDayOfWeek());
                                //此处要写一个for循环判断医生所有可预约时间是否在用户预约的这一天，这里只写了打表函数
                                //第一个参数是布局，第二个参数是周几，前两个不用改，第三个应该对应的是1-上午；2-中午；3-下午
                                String date = datepicker.getValue().getYear()+"/"+datepicker.getValue().getMonthValue()+"/"+datepicker.getValue().getDayOfMonth();
                                registrationRecordTemp.setApDate(date);
                                System.out.println(registrationRecordTemp.getApDate());

                                for(int i = 0; i < doctorSchedules.size(); i++){
                                    DoctorSchedule doctorScheduleTemp = doctorSchedules.get(i);
                                    if (doctorScheduleTemp.getScheduleInWeek() .equals(String.valueOf(weekday))) {
                                        System.out.println(datepicker.getValue().getDayOfWeek() + " " + Integer.valueOf(doctorScheduleTemp.getScheduleInDay()));
                                        LabelGenerate(picPane, datepicker.getValue().getDayOfWeek(), Integer.valueOf(doctorScheduleTemp.getScheduleInDay()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        JFXButton btn_back = new JFXButton("返回");
        btn_back.setStyle("    -fx-padding: 0.7em 0.57em;\r\n" +
                "    -fx-font-size: 14px;\r\n" +
                "    -jfx-button-type: RAISED;\r\n" +
                "    -fx-background-color: rgb(77,102,204);\r\n" +
                "    -fx-pref-width: 50;\r\n" +
                "    -fx-text-fill: WHITE;");
        btn_back.setLayoutX(40);
        btn_back.setLayoutY(40);

        //返回键-调用tool，关闭本窗口
        btn_back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                hospital_tab_home.jumpTool_close();
            }
        });

        pane.getChildren().addAll(backgroundView,label1,lb,iv,timetable,datepicker,b1,picPane,btn_back);



        return pane;
    }

    private Integer ChooseDay(AnchorPane pane, DayOfWeek d){
        Integer weekday = 1;
        switch(d) {
            case MONDAY:
                weekday = 1;
                pane.getChildren().clear();
                Label lb0 = new Label();
                lb0.setPrefSize(93,372);
                lb0.setStyle("-fx-background-color: #666666");
                lb0.setOpacity(0.4);
                pane.getChildren().add(lb0);
                break;
            case TUESDAY:
                weekday = 2;
                pane.getChildren().clear();
                Label lb1 = new Label();
                lb1.setPrefSize(93,372);
                lb1.setLayoutX(93);
                lb1.setStyle("-fx-background-color: #666666");
                lb1.setOpacity(0.4);
                pane.getChildren().add(lb1);
                break;
            case WEDNESDAY:
                weekday = 3;
                pane.getChildren().clear();
                Label lb2 = new Label();
                lb2.setPrefSize(93,372);
                lb2.setLayoutX(187);
                lb2.setStyle("-fx-background-color: #666666");
                lb2.setOpacity(0.4);
                pane.getChildren().add(lb2);
                break;
            case THURSDAY:
                weekday = 4;
                pane.getChildren().clear();
                Label lb3 = new Label();
                lb3.setPrefSize(93,372);
                lb3.setLayoutX(280);
                lb3.setStyle("-fx-background-color: #666666");
                lb3.setOpacity(0.4);
                pane.getChildren().add(lb3);
                break;
            case FRIDAY:
                weekday = 5;
                pane.getChildren().clear();
                Label lb4 = new Label();
                lb4.setPrefSize(93,372);
                lb4.setLayoutX(374);
                lb4.setStyle("-fx-background-color: #666666");
                lb4.setOpacity(0.4);
                pane.getChildren().add(lb4);
                break;
            case SATURDAY:
                weekday = 6;
                pane.getChildren().clear();
                Label lb5 = new Label();
                lb5.setPrefSize(93,372);
                lb5.setLayoutX(468);
                lb5.setStyle("-fx-background-color: #666666");
                lb5.setOpacity(0.4);
                pane.getChildren().add(lb5);
                break;
            case SUNDAY:
                weekday = 7;
                pane.getChildren().clear();
                Label lb6 = new Label();
                lb6.setPrefSize(93,372);
                lb6.setLayoutX(562);
                lb6.setStyle("-fx-background-color: #666666");
                lb6.setOpacity(0.4);
                pane.getChildren().add(lb6);
                break;

        }
        return weekday;
    }

    //一次只打一格
    //需要添加消息响应函数
    private void LabelGenerate(AnchorPane pane, DayOfWeek d, int t){
        //预约格子颜色
        ArrayList<String> color = new ArrayList<String>();
        color.add("#FFC0CB");
        color.add("#DDA0DD");
        color.add("#87CEFA");
        color.add("#F0E68C");
        color.add("#FFEBCD");

        JFXButton btn = new JFXButton("可预约");
        btn.setWrapText(true);
        btn.setPrefSize(70, 110);
        btn.setLayoutX(10);
        btn.setLayoutY(7);
        btn.setFont(new Font("SimHei", 15));
        final double num = Math.random();
        final int j = (int)(num*5);
        btn.setStyle("-fx-background-color: " +color.get(j)+ ";-fx-background-radius: 10px;-fx-text-alignment:center;");

//        RegistrationRecord registrationRecordTemp = new RegistrationRecord();
//        registrationRecordTemp.setStuId(student.getId());
//        registrationRecordTemp.setStuName(student.getName());
//        registrationRecordTemp.setDocId(doctor.getId());
//        registrationRecordTemp.setDocName(doctor.getName());
//        registrationRecordTemp.setApDept(doctor.getDept());

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showConfirmation(btn, t);

            }
        });

        switch(d) {
            case MONDAY:
                btn.setLayoutX(12);
                break;
            case TUESDAY:
                btn.setLayoutX(105);
                break;
            case WEDNESDAY:
                btn.setLayoutX(198);
                break;
            case THURSDAY:
                btn.setLayoutX(292);
                break;
            case FRIDAY:
                btn.setLayoutX(386);
                break;
            case SATURDAY:
                btn.setLayoutX(479);
                break;
            case SUNDAY:
                btn.setLayoutX(573);
                break;
        }

        switch (t){
            case 1:
                btn.setLayoutY(7);
                break;
            case 2:
                btn.setLayoutY(131);
                break;
            case 3:
                btn.setLayoutY(255);
                break;
        }

        pane.getChildren().add(btn);
    }
}
