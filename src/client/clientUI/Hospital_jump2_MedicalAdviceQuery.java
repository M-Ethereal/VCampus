package client.clientUI;


import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.MessageType;
import vo.MedicalAdvice;
import vo.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
提问页：二跳的界面
需求：
根属于咨询记录页，由咨询记录主页下的一级跳转页维护，
双入口：从主页下的一跳页面能跳转过来;从咨询记录页下的一跳页面能够跳转过来(check)（由一跳页面进行传递(如果是从主页下的一条页面进入，要自己用stu和doc拼好medicaladvice
单出口（提交/返回）：结束之后跳转到咨询记录页面（需要得到咨询主页的jumptool函数(check)
*/
public class Hospital_jump2_MedicalAdviceQuery {
    private MedicalAdvice medicalAdvice;
    private Hospital_tab_query hospital_tab_query;//父窗的句柄
    //背景
    Image bg = new Image("/client/Image/img2.jpg",1058, 580,false,true);
    ImageView backgroundView = new ImageView(bg);

    public Hospital_jump2_MedicalAdviceQuery(MedicalAdvice medicalAdvice, Hospital_tab_query hospital_tab_query){
        this.medicalAdvice = medicalAdvice;
        this.hospital_tab_query = hospital_tab_query;
    }

    public AnchorPane HospitalQueryPage()
    {
        AnchorPane pane = new AnchorPane();

        Label label1 = new Label();
        label1.setLayoutX(300);
        label1.setLayoutY(65);
        String DocName = medicalAdvice.getDocName();
        label1.setText(DocName+"医生，"+"\n"+"我想向您咨询：");//显示姓名
        label1.setFont(new Font("Microsoft YaHei",25));

        JFXButton b1 = new JFXButton();
        b1.setText("提交");
        b1.setLayoutX(650);;
        b1.setLayoutY(480);
        b1.setPrefHeight(40);
        b1.setPrefWidth(160);
        b1.setFont(Font.font("sans-serf", 15));
        b1.setStyle("-fx-font-size: 14px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-text-fill: WHITE;");

        //医生图片
        Circle cir = new Circle();

        String Path="/client/Image/HeadPortrait.png";
        Image image=new Image(Path);
        ImageView iv=new ImageView(image);
        iv.setPreserveRatio(false);
        iv.setFitWidth(130);
        iv.setFitHeight(130);
        iv.setLayoutX(120);
        iv.setLayoutY(30);
        cir.setCenterX(65);
        cir.setCenterY(65);
        cir.setRadius(63);
        iv.setClip(cir);

        //标签区域
        Label lb1 = new Label("问题标签");
        lb1.setFont(new Font("Microsoft YaHei",15));
        lb1.setLayoutX(150);
        lb1.setLayoutY(190);
        JFXChipView<String> chipView = new JFXChipView<>();
        chipView.setMinHeight(80);
        chipView.setMaxHeight(80);
        chipView.setMinWidth(700);
        chipView.setMaxWidth(700);
        chipView.setLayoutX(220);
        chipView.setLayoutY(185);

        //文本区域
        Label lb2 = new Label("问题正文");
        lb2.setFont(new Font("Microsoft YaHei",15));
        lb2.setLayoutX(150);
        lb2.setLayoutY(235);
        JFXTextArea ta = new JFXTextArea();
        ta.setPromptText("从这里开始输入...");
        ta.setFont(Font.font(15));
        ta.setLayoutX(220);
        ta.setLayoutY(230);


        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        System.out.println("格式化: " + localDateTime.format(dtf));
        // 获取年月日
        System.out.println(localDateTime.toLocalDate());
        // 获取时分秒
        System.out.println(localDateTime.toLocalTime());

        String[] lt = localDateTime.format(dtf).split(" ");
        String qDate = lt[0];
        String qTime = lt[1];
        //提交按钮,点击跳出弹框
        //提交问题
        b1.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                JFXDialogLayout layout_suc = new JFXDialogLayout();
                Client client_query = new Client();
                Message sendToServer = new Message();
                MedicalAdvice ma = medicalAdvice;
                System.out.println(ta.getText());
                ma.setqQuestion(ta.getText());
                ma.setQueryDate(qDate);
                ma.setQueryTime(qTime);
                ma.setAnsDate("");
                ma.setAnsTime("");
                ma.setqAnswer("");
                ma.setRank(false);
                sendToServer.setMessageType(MessageType.stu_advice_insert);
                sendToServer.setData(ma);
                Message serverResponse_query = client_query.sendRequestToServer(sendToServer);
                if(serverResponse_query.isLastOperState() ==true)
                    layout_suc.setBody(new Label("      问题提交成功！"));
                else
                    layout_suc.setBody(new Label("      问题提交失败，请检查您的网络设置"));
                JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) b1.getScene().getWindow());
                alert_suc.setOverlayClose(true);
                alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert_suc.setContent(layout_suc);
                alert_suc.initModality(Modality.NONE);
                alert_suc.showAndWait();

                hospital_tab_query.jumpTool_close();
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
                hospital_tab_query.jumpTool_close();
            }
        });

        //头像图片
//		Image image = new Image("");
//		ImageView iv = new ImageView(image);
//		iv.setPreserveRatio(false);
//		iv.setFitHeight();;
//		iv.setFitWidth();

        pane.getChildren().addAll(backgroundView,label1,b1,ta,lb1,lb2,chipView,iv,btn_back);//+iv

        return pane;
    }
}
