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
挂号页-医生：
需求：
每个格子内放一个按钮，每个按钮有两种操作，由按钮上的文字进行区分
“可添加：”-点击后弹窗确认：是否添加出诊时间：xxxx-确定：后端insert
“可取消：”-点击后弹窗确认：是否取消出诊时间：xxxx-取消：后端delete
button不要彩色的，可添加按钮是绿色，可取消是红色
动态页面，有刷新操作-绑定查询键的响应
*/
public class Hospital_tab_res_Doctor {
    private ArrayList<DoctorSchedule> doctorSchedules = new ArrayList<DoctorSchedule>();
    private Doctor doctor;
    DoctorSchedule doctorSchedule = new DoctorSchedule();
    //一个数组，3个格子（即早中晚），代表此处是否已经放置过button了（用于防止二重循环）
    boolean[] scheduleTable = new boolean[4];

    //背景
    Image bg = new Image("/client/Image/img2.jpg",1058, 580,false,true);
    ImageView backgroundView = new ImageView(bg);

    //医生图标
    Image img1 = new Image("client/Image/doctor.png",150, 150,true,true);
    ImageView iv1 = new ImageView(img1);


    //构造函数
    public Hospital_tab_res_Doctor( Doctor doctor ){
        this.doctor = doctor;
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

    private String checkTimeInDay(Integer y){
        switch (y){
            case 7:
                return "1";
            case 131:
                return "2";
            case 255:
                return "3";
        }
        return null;
    }

    //确认窗口
    private void showConfirmation(Button btn, String weekDay, String dayTime, boolean isAdd)//第二个参数是Integer.valueOf(doctorScheduleTemp.getScheduleInDay()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);

        String mes = "";
        if(isAdd == true){
            mes = "要添加该出诊时间段吗？\n" + weekDay + "\n" + dayTime + "\n";
        } else {
            mes = "要取消该出诊时间段吗？\n" + weekDay + "\n" + dayTime + "\n";
        }
        alert.setContentText(mes);

        Optional result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Client client_res = new Client();
            Message sendToServer = new Message();
            doctorSchedule.setScheduleInDay(checkTimeInDay((int) btn.getLayoutY()));
            System.out.println(doctorSchedule.getScheduleInDay());
            sendToServer.setData(doctorSchedule);

            if (isAdd == true){
                sendToServer.setMessageType(MessageType.doc_schedule_add);
                Message serverResponse = client_res.sendRequestToServer(sendToServer);

                if (serverResponse.isLastOperState()) {
                    JFXDialogLayout layout_suc = new JFXDialogLayout();
                    layout_suc.setBody(new Label("      添加成功"));
                    JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) btn.getScene().getWindow());
                    alert_suc.setOverlayClose(true);
                    alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_suc.setContent(layout_suc);
                    alert_suc.initModality(Modality.NONE);
                    alert_suc.show();
                    btn.setText("可取消该出诊时段");
                    btn.setStyle("-fx-background-color: #ff9966;-fx-background-radius: 10px;-fx-text-alignment:center;");
                    btn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            showConfirmation(btn, weekDay, dayTime, false);
                        }
                    });
                } else {
                    JFXDialogLayout layout_f = new JFXDialogLayout();
                    layout_f.setBody(new Label("      添加失败，" + serverResponse.getErrorMessage()));
                    JFXAlert<Void> alert_f = new JFXAlert<>((Stage) btn.getScene().getWindow());
                    alert_f.setOverlayClose(true);
                    alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_f.setContent(layout_f);
                    alert_f.initModality(Modality.NONE);
                    alert_f.show();
                }
            }
            else {
                sendToServer.setMessageType(MessageType.doc_schedule_del);
                Message serverResponse = client_res.sendRequestToServer(sendToServer);

                if (serverResponse.isLastOperState()) {
                    JFXDialogLayout layout_suc = new JFXDialogLayout();
                    layout_suc.setBody(new Label("      取消成功"));
                    JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) btn.getScene().getWindow());
                    alert_suc.setOverlayClose(true);
                    alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_suc.setContent(layout_suc);
                    alert_suc.initModality(Modality.NONE);
                    alert_suc.show();
                    btn.setText("可添加该出诊时段");
                    btn.setStyle("-fx-background-color: #99ff66;-fx-background-radius: 10px;-fx-text-alignment:center;");
                    btn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            showConfirmation(btn, weekDay, dayTime, true);
                        }
                    });
                } else {
                    JFXDialogLayout layout_f = new JFXDialogLayout();
                    layout_f.setBody(new Label("      取消失败，请检查您的网络设置"));
                    JFXAlert<Void> alert_f = new JFXAlert<>((Stage) btn.getScene().getWindow());
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



    public AnchorPane HospitalSchedulePage(){
        AnchorPane pane = new AnchorPane();
        doctorSchedule.setDocId(doctor.getId());

        //初始化列表
        Client client_sch = new Client();
        Message sendToServer_sch = new Message();
        sendToServer_sch.setMessageType(MessageType.doc_schedule_query);
        sendToServer_sch.setExtraMessage(doctor.getId());
        Message serverResponse_sch = client_sch.sendRequestToServer(sendToServer_sch);
        doctorSchedules = (ArrayList<DoctorSchedule>) serverResponse_sch.getData();
        System.out.println(doctorSchedules.size() + " " + doctorSchedules.get(0).getScheduleInWeek() + " " + doctorSchedules.get(0).getScheduleInDay());

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

        //查询医生出诊表
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
                        layout.setBody(new Label("请不要选择已经结束的时间！"));
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
                        //重新初始化
                        Client client_sch = new Client();
                        Message sendToServer_sch = new Message();
                        sendToServer_sch.setMessageType(MessageType.doc_schedule_query);
                        sendToServer_sch.setExtraMessage(doctor.getId());
                        Message serverResponse_sch = client_sch.sendRequestToServer(sendToServer_sch);
                        doctorSchedules = (ArrayList<DoctorSchedule>) serverResponse_sch.getData();

                        String mm = String.valueOf(datepicker.getValue().getMonthValue());
                        String dd = String.valueOf(datepicker.getValue().getDayOfMonth());
                        if (datepicker.getValue().getMonthValue() < 10)
                            mm = "0" + datepicker.getValue().getMonthValue();
                        if (datepicker.getValue().getDayOfMonth() < 10)
                            dd = "0" + datepicker.getValue().getDayOfMonth();
                        String date = datepicker.getValue().getYear() + "/" + mm + "/" + dd;
                        doctorSchedule.setScheduleDate(date);
                        System.out.println(doctorSchedule.getScheduleDate());

                        for(int i = 0; i < 4; i++){
                            scheduleTable[i] = false;
                        }

                        //打印已有项
                        for(int i = 0; i < doctorSchedules.size(); i++){
                            DoctorSchedule doctorScheduleTemp = doctorSchedules.get(i);
                            if (doctorScheduleTemp.getScheduleInWeek() .equals(String.valueOf(weekday))) {
                                System.out.println(datepicker.getValue().getDayOfWeek() + " " + Integer.valueOf(doctorScheduleTemp.getScheduleInDay()));
                                LabelGenerate(picPane, datepicker.getValue().getDayOfWeek(), Integer.valueOf(doctorScheduleTemp.getScheduleInDay()), false);
                                //已经打印的
                                scheduleTable[Integer.valueOf(doctorScheduleTemp.getScheduleInDay())] = true;
                            }
                        }

                        //打印剩下的
                        for(int i = 1; i <= 3; i++){
                            if(scheduleTable[i]==false){
                                LabelGenerate(picPane, datepicker.getValue().getDayOfWeek(), i, true);
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

                            //重新初始化
                            Client client_sch = new Client();
                            Message sendToServer_sch = new Message();
                            sendToServer_sch.setMessageType(MessageType.doc_schedule_query);
                            sendToServer_sch.setExtraMessage(doctor.getId());
                            Message serverResponse_sch = client_sch.sendRequestToServer(sendToServer_sch);
                            doctorSchedules = (ArrayList<DoctorSchedule>) serverResponse_sch.getData();

                            String mm = String.valueOf(datepicker.getValue().getMonthValue());
                            String dd = String.valueOf(datepicker.getValue().getDayOfMonth());
                            if (datepicker.getValue().getMonthValue() < 10)
                                mm = "0" + datepicker.getValue().getMonthValue();
                            if (datepicker.getValue().getDayOfMonth() < 10)
                                dd = "0" + datepicker.getValue().getDayOfMonth();
                            String date = datepicker.getValue().getYear() + "/" + mm + "/" + dd;
                            doctorSchedule.setScheduleDate(date);
                            System.out.println(doctorSchedule.getScheduleDate());

                            for(int i = 0; i < 4; i++){
                                scheduleTable[i] = false;
                            }

                            //打印已有项
                            for(int i = 0; i < doctorSchedules.size(); i++){
                                DoctorSchedule doctorScheduleTemp = doctorSchedules.get(i);
                                if (doctorScheduleTemp.getScheduleInWeek() .equals(String.valueOf(weekday))) {
                                    System.out.println(datepicker.getValue().getDayOfWeek() + " " + Integer.valueOf(doctorScheduleTemp.getScheduleInDay()));
                                    LabelGenerate(picPane, datepicker.getValue().getDayOfWeek(), Integer.valueOf(doctorScheduleTemp.getScheduleInDay()), false);
                                    //已经打印的
                                    scheduleTable[Integer.valueOf(doctorScheduleTemp.getScheduleInDay())] = true;
                                }
                            }

                            //打印剩下的
                            for(int i = 1; i <= 3; i++){
                                if(scheduleTable[i]==false){
                                    LabelGenerate(picPane, datepicker.getValue().getDayOfWeek(), i, true);
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
                                //重新初始化
                                Client client_sch = new Client();
                                Message sendToServer_sch = new Message();
                                sendToServer_sch.setMessageType(MessageType.doc_schedule_query);
                                sendToServer_sch.setExtraMessage(doctor.getId());
                                Message serverResponse_sch = client_sch.sendRequestToServer(sendToServer_sch);
                                doctorSchedules = (ArrayList<DoctorSchedule>) serverResponse_sch.getData();

                                String mm = String.valueOf(datepicker.getValue().getMonthValue());
                                String dd = String.valueOf(datepicker.getValue().getDayOfMonth());
                                if (datepicker.getValue().getMonthValue() < 10)
                                    mm = "0" + datepicker.getValue().getMonthValue();
                                if (datepicker.getValue().getDayOfMonth() < 10)
                                    dd = "0" + datepicker.getValue().getDayOfMonth();
                                String date = datepicker.getValue().getYear() + "/" + mm + "/" + dd;
                                doctorSchedule.setScheduleDate(date);
                                System.out.println(doctorSchedule.getScheduleDate());

                                for(int i = 0; i < 4; i++){
                                    scheduleTable[i] = false;
                                }

                                //打印已有项
                                for(int i = 0; i < doctorSchedules.size(); i++){
                                    DoctorSchedule doctorScheduleTemp = doctorSchedules.get(i);
                                    if (doctorScheduleTemp.getScheduleInWeek() .equals(String.valueOf(weekday))) {
                                        System.out.println(datepicker.getValue().getDayOfWeek() + " " + Integer.valueOf(doctorScheduleTemp.getScheduleInDay()));
                                        LabelGenerate(picPane, datepicker.getValue().getDayOfWeek(), Integer.valueOf(doctorScheduleTemp.getScheduleInDay()), false);
                                        //已经打印的
                                        scheduleTable[Integer.valueOf(doctorScheduleTemp.getScheduleInDay())] = true;
                                    }
                                }

                                //打印剩下的
                                for(int i = 1; i <= 3; i++){
                                    if(scheduleTable[i]==false){
                                        LabelGenerate(picPane, datepicker.getValue().getDayOfWeek(), i, true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        pane.getChildren().addAll(backgroundView,label1,lb,iv,timetable,datepicker,b1,picPane);

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
    private void LabelGenerate(AnchorPane pane, DayOfWeek d, int t, boolean isAdd){

        JFXButton btn = new JFXButton();
        btn.setWrapText(true);
        btn.setPrefSize(70, 110);
        btn.setLayoutX(10);
        btn.setLayoutY(7);
        btn.setFont(new Font("SimHei", 15));

        if(isAdd == true){
            btn.setText("可添加该出诊时段");
            btn.setStyle("-fx-background-color: #99ff66;-fx-background-radius: 10px;-fx-text-alignment:center;");

        }

        if(isAdd == false) {
            btn.setText("可取消该出诊时段");
            btn.setStyle("-fx-background-color: #ff9966;-fx-background-radius: 10px;-fx-text-alignment:center;");
        }


        String weekday = "";
        switch(d) {
            case MONDAY:
                btn.setLayoutX(12);
                weekday = "周一";
                doctorSchedule.setScheduleInWeek("1");
                break;
            case TUESDAY:
                btn.setLayoutX(105);
                weekday = "周二";
                doctorSchedule.setScheduleInWeek("2");
                break;
            case WEDNESDAY:
                btn.setLayoutX(198);
                weekday = "周三";
                doctorSchedule.setScheduleInWeek("3");
                break;
            case THURSDAY:
                btn.setLayoutX(292);
                weekday = "周四";
                doctorSchedule.setScheduleInWeek("4");
                break;
            case FRIDAY:
                btn.setLayoutX(386);
                weekday = "周五";
                doctorSchedule.setScheduleInWeek("5");
                break;
            case SATURDAY:
                btn.setLayoutX(479);
                weekday = "周六";
                doctorSchedule.setScheduleInWeek("6");
                break;
            case SUNDAY:
                btn.setLayoutX(573);
                weekday = "周日";
                doctorSchedule.setScheduleInWeek("7");
                break;
        }

        String dayTime = "";
        switch (t){
            case 1:
                btn.setLayoutY(7);
                dayTime = "上午";
                //doctorSchedule.setScheduleInDay("1");
                break;
            case 2:
                btn.setLayoutY(131);
                dayTime = "下午";
                //doctorSchedule.setScheduleInDay("2");
                break;
            case 3:
                btn.setLayoutY(255);
                dayTime = "晚上";
                //doctorSchedule.setScheduleInDay("3");
                break;
        }

        String finalWeekday = weekday;
        String finalDayTime = dayTime;
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showConfirmation(btn, finalWeekday, finalDayTime, isAdd);
            }
        });

        pane.getChildren().add(btn);
    }
}
