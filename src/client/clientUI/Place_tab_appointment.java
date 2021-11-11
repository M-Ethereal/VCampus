package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import vo.*;

import java.util.ArrayList;
import java.util.Optional;

public class Place_tab_appointment {
    private Student student = new Student();
    private ArrayList<PlaceAppointmentRecord> placeAppointmentRecords = new ArrayList<PlaceAppointmentRecord>();
    private FlowPane fp1 = new FlowPane();

    //图标
    Image img1 = new Image("client/Image/back1.png",150, 150,true,true);
    ImageView iv1 = new ImageView(img1);

    public Place_tab_appointment(Student student){
        this.student = student;
    }

    public void tool_add_a_record(PlaceAppointmentRecord placeAppointmentRecord){
        addAppRecord(fp1, placeAppointmentRecord);
    }

    //确认窗口
    private void showConfirmation(Pane btn, JFXButton button, FlowPane flowPane, PlaceAppointmentRecord placeAppointmentRecord)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setContentText("确定要取消预约吗？");
        alert.setGraphic(iv1);

        Optional result = alert.showAndWait();

        //点击确定
        if (result.get() == ButtonType.OK) {
            Client client_del = new Client();
            Message sendToServer_del = new Message();
            sendToServer_del.setMessageType(MessageType.place_ap_delete);
            sendToServer_del.setData(placeAppointmentRecord);
            Message serverResponse_del = client_del.sendRequestToServer(sendToServer_del);

            if(serverResponse_del.isLastOperState()==true){
                JFXDialogLayout layout_suc = new JFXDialogLayout();
                layout_suc.setBody(new Label("      取消预约成功"));
                JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) btn.getScene().getWindow());
                alert_suc.setOverlayClose(true);
                alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert_suc.setContent(layout_suc);
                alert_suc.initModality(Modality.NONE);
                alert_suc.show();

                flowPane.getChildren().remove(button);
            }
            else {
                JFXDialogLayout layout_f = new JFXDialogLayout();
                layout_f.setBody(new Label("      取消预约失败：请检查您的网络设置"));
                JFXAlert<Void> alert_f = new JFXAlert<>((Stage) btn.getScene().getWindow());
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

    //在表中添加一项: 预约日期，开始时间，结束时间，场馆名
    private void addAppRecord(FlowPane flowPane, PlaceAppointmentRecord placeAppointmentRecord){
        System.out.println(placeAppointmentRecord.getPlaceName());

        Client client_queryPlace = new Client();
        Message message_queryPlace = new Message();
        message_queryPlace.setMessageType(MessageType.place_query_by_name);
        message_queryPlace.setExtraMessage(placeAppointmentRecord.getPlaceName());
        Message serverResponse_queryPlace = client_queryPlace.sendRequestToServer(message_queryPlace);
        Place place = (Place) serverResponse_queryPlace.getData();

        AnchorPane coursebutton = new AnchorPane();
        coursebutton.setPrefSize(750, 100);
        Label PlaceName = new Label(placeAppointmentRecord.getPlaceName());
        Label PlacePos = new Label(place.getpCampus() + " " + place.getPlacePosition());
        Label ApDate = new Label("预约日期：\n" + placeAppointmentRecord.getStartDate());
        Label StartTime = new Label("开始时间：\n" + placeAppointmentRecord.getStartTime());
        Label EndTime = new Label("结束时间：\n" + placeAppointmentRecord.getEndTime());

//        Label CID = new Label(courseList.get(i).getCourseNumber());
//        Label CName = new Label(courseList.get(i).getCourseName());
//        CID.setVisible(false);
//        CName.setVisible(false);

        PlaceName.setStyle("-fx-font-size:25px");
        PlaceName.setLayoutX(50);
        PlaceName.setLayoutY(22);

        PlacePos.setStyle("-fx-font-size:15px");
        PlacePos.setLayoutX(50);
        PlacePos.setLayoutY(55);

        ApDate.setStyle("-fx-font-size:15px");
        ApDate.setLayoutX(360);
        ApDate.setLayoutY(34);

        StartTime.setStyle("-fx-font-size:15px");
        StartTime.setLayoutX(480);
        StartTime.setLayoutY(34);

        EndTime.setStyle("-fx-font-size:15px");
        EndTime.setLayoutX(600);
        EndTime.setLayoutY(34);

        coursebutton.getChildren().addAll(PlaceName, PlacePos, ApDate, StartTime, EndTime);

        JFXButton b1 = new JFXButton();
        b1.setStyle("-jfx-button-type: RAISED;-fx-background-color: rgb(245,245,245);");
        b1.setGraphic(coursebutton);
        flowPane.getChildren().add(b1);

        coursebutton.addEventHandler(MouseEvent.MOUSE_PRESSED, e-> {
            showConfirmation(coursebutton,b1,flowPane, placeAppointmentRecord);
        });
    }


    public ScrollPane tab_ap() {

        Client client = new Client();
        Message sendToServer = new Message();
        sendToServer.setMessageType(MessageType.stu_ap_query);
        sendToServer.setExtraMessage(student.getId());
        Message serverResponse = client.sendRequestToServer(sendToServer);
        placeAppointmentRecords = (ArrayList<PlaceAppointmentRecord>) serverResponse.getData();

        ScrollPane scp = new ScrollPane();

        //主标题
        Label mainTitle = new Label("点击取消预约");
        mainTitle.setStyle("-fx-font-size:30px");

        //初始化
        //FlowPane fp1 = new FlowPane();
        fp1.setVgap(10);

        for (int i = 0; i < placeAppointmentRecords.size(); i ++)
        {
            addAppRecord(fp1, placeAppointmentRecords.get(i));
        }


        //框
        BorderedTitledPane btp = new BorderedTitledPane("我的预约", fp1);
        btp.getStylesheets().add(getClass().getResource("BorderedTitledPane_CSS.css").toExternalForm());
        btp.setTitleAlignment(Pos.TOP_LEFT);


        //主页面布局
        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(btp, mainTitle);
        ap.setTopAnchor(mainTitle, 50.0);
        ap.setLeftAnchor(mainTitle, 50.0);
        ap.setTopAnchor(btp, 160.0);
        ap.setLeftAnchor(btp, 40.0);

        //滚轮
        scp.setContent(ap);

        return scp;
    }
}
