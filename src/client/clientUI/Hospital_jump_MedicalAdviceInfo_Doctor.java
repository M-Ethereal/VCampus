package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.MessageType;
import vo.MedicalAdvice;
import vo.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Hospital_jump_MedicalAdviceInfo_Doctor {
    private MedicalAdvice medicalAdvice = new MedicalAdvice();
    private ArrayList<MedicalAdvice> relevantList = new ArrayList<MedicalAdvice>();
    private Hospital_tab_query_Doctor hospital_tab_query_doctor;//父窗的句柄

    //背景
    Image bg = new Image("/client/Image/img2.jpg",1058, 580,false,true);
    ImageView backgroundView = new ImageView(bg);

    public Hospital_jump_MedicalAdviceInfo_Doctor (MedicalAdvice medicalAdvice, Hospital_tab_query_Doctor hospital_tab_query_doctor){
        this.medicalAdvice = medicalAdvice;
        this.hospital_tab_query_doctor = hospital_tab_query_doctor;
    }

    public AnchorPane medicalAdviceInfo(){
        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(backgroundView);

        Label label1 = new Label();
        label1.setLayoutX(150);
        label1.setLayoutY(90);
        label1.setText(medicalAdvice.getStuName() + "的问题：");
        label1.setFont(new Font("Microsoft YaHei",20));

        //用户图片
        Circle cir1 = new Circle();

        String Path1="/client/Image/HeadPortrait.png";
        Image image1=new Image(Path1);
        ImageView iv1=new ImageView(image1);
        iv1.setPreserveRatio(false);
        iv1.setFitWidth(50);
        iv1.setFitHeight(50);
        iv1.setLayoutX(100);
        iv1.setLayoutY(80);
        cir1.setCenterX(25);
        cir1.setCenterY(25);
        cir1.setRadius(20);
        iv1.setClip(cir1);

        //文本区域(不可修改)
        TextArea ta1 = new TextArea();
        ta1.setStyle("-fx-background-color: WHITE;");
        ta1.setOpacity(0.8);
        ta1.setWrapText(true);
        ta1.setText(medicalAdvice.getqQuestion());
        ta1.setFont(Font.font(15));
        ta1.setPrefHeight(120);
        ta1.setPrefWidth(500);
        ta1.setLayoutX(130);
        ta1.setLayoutY(140);
        ta1.setEditable(false);

        DropShadow dropshadow = new DropShadow();// 阴影向外
        dropshadow.setRadius(10);// 颜色蔓延的距离
        dropshadow.setOffsetX(0);// 水平方向，0则向左右两侧，正则向右，负则向左
        dropshadow.setOffsetY(0);// 垂直方向，0则向上下两侧，正则向下，负则向上
        dropshadow.setSpread(0.1);// 颜色变淡的程度
        dropshadow.setColor(Color.BLACK);// 设置颜色
        ta1.setEffect(dropshadow);// 绑定指定窗口控件

        //头像图片2
//		Image image2 = new Image("");
//		ImageView iv2 = new ImageView(image2);
//		iv2.setPreserveRatio(false);
//		iv2.setFitHeight();;
//		iv2.setFitWidth();

        //医生图片
        Circle cir2 = new Circle();

        String Path2="/client/Image/cross.png";
        Image image2=new Image(Path2);
        ImageView iv2=new ImageView(image2);
        iv2.setPreserveRatio(false);
        iv2.setFitWidth(50);
        iv2.setFitHeight(50);
        iv2.setLayoutX(100);
        iv2.setLayoutY(280);
        cir2.setCenterX(25);
        cir2.setCenterY(25);
        cir2.setRadius(20);
        iv2.setClip(cir2);

        Label label2 = new Label();
        label2.setLayoutX(150);
        label2.setLayoutY(290);
        label2.setText("我的回复：");
        label2.setFont(new Font("Microsoft YaHei",20));


        TextArea ta2 = new TextArea();
        ta2.setStyle("-fx-background-color: WHITE;");
        ta2.setOpacity(0.8);
        ta2.setWrapText(true);
        ta2.setFont(Font.font(15));
        ta2.setPrefHeight(120);
        ta2.setPrefWidth(500);
        ta2.setLayoutX(130);
        ta2.setLayoutY(350);

        if(medicalAdvice.getqAnswer() .equals(""))
        {
            ta2.setEditable(true);
            ta2.setPromptText("从这里开始输入...");

            JFXButton b1 = new JFXButton();
            b1.setText("提交回复");
            b1.setLayoutX(350);
            b1.setLayoutY(500);
            b1.setPrefWidth(100);
            b1.setStyle("-fx-font-size: 14px;\n" +
                    "jfx-button-type: RAISED;\n" +
                    "-fx-background-color: rgb(77,102,204);\n" +
                    "-fx-text-fill: WHITE;");

            ap.getChildren().add(b1);

            b1.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent event) {
                    LocalDateTime localDateTime = LocalDateTime.now();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    String[] lt = localDateTime.format(dtf).split(" ");
                    String aDate = lt[0];
                    String aTime = lt[1];
                    medicalAdvice.setqAnswer(ta2.getText());
                    medicalAdvice.setAnsDate(aDate);
                    medicalAdvice.setAnsTime(aTime);

                    Client client_ans = new Client();
                    Message sendToServer = new Message();
                    sendToServer.setMessageType(MessageType.doc_advice_ans);
                    sendToServer.setData(medicalAdvice);
                    Message serverResponse = client_ans.sendRequestToServer(sendToServer);
                    if(serverResponse.isLastOperState()==true)
                    {
                        JFXDialogLayout layout_suc = new JFXDialogLayout();
                        layout_suc.setBody(new Label("      提交成功"));
                        JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) b1.getScene().getWindow());
                        alert_suc.setOverlayClose(true);
                        alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                        alert_suc.setContent(layout_suc);
                        alert_suc.initModality(Modality.NONE);
                        alert_suc.show();
                    }
                }
            });
        }
        else
        {
            //文本区域(不可修改)
            ta2.setText(medicalAdvice.getqAnswer());
            ta2.setEditable(false);

        }


        DropShadow dropshadow2 = new DropShadow();// 阴影向外
        dropshadow2.setRadius(10);// 颜色蔓延的距离
        dropshadow2.setOffsetX(0);// 水平方向，0则向左右两侧，正则向右，负则向左
        dropshadow2.setOffsetY(0);// 垂直方向，0则向上下两侧，正则向下，负则向上
        dropshadow2.setSpread(0.1);// 颜色变淡的程度
        dropshadow2.setColor(Color.BLACK);// 设置颜色
        ta2.setEffect(dropshadow2);// 绑定指定窗口控件




        Label label3 = new Label();
        label3.setLayoutX(750);
        label3.setLayoutY(60);
        label3.setText("历史问题：");
        label3.setFont(new Font("Microsoft YaHei",20));


        Client client_relevant = new Client();
        Message msgSendToServer = new Message();
        msgSendToServer.setMessageType(MessageType.stu_advice_query_relevant);
        msgSendToServer.setData(medicalAdvice);
        Message serverResponse = client_relevant.sendRequestToServer(msgSendToServer);
        relevantList = (ArrayList<MedicalAdvice>) serverResponse.getData();

        JFXListView<Label> list = new JFXListView<Label>();
        //此处设置标签内容和事件响应函数
        for(int i = 0 ; i < relevantList.size() ; i++) {
            String strStu = relevantList.get(i).getStuName()+": "+relevantList.get(i).getqQuestion();
            Tooltip stu = new Tooltip(strStu);
            stu.setWrapText(true);
            stu.setPrefWidth(240);
            Label label11 = new Label(strStu);
            label11.setTooltip(stu);
            list.getItems().add(label11);
            label11.setPrefWidth(200);
            if(relevantList.get(i).getqAnswer() != null) {
                String strDoc = relevantList.get(i).getDocName() + ": " + relevantList.get(i).getqAnswer();
                Tooltip doc = new Tooltip(strDoc);
                doc.setWrapText(true);
                doc.setPrefWidth(240);
                Label label22 = new Label(strDoc);
                label22.setTooltip(doc);
                list.getItems().add(label22);
                label22.setPrefWidth(200);
            }
        }
        list.setLayoutX(750);
        list.setLayoutY(100);
        list.setPrefSize(220,380);

        //tableview 对应表的属性
//		Data dt1 = new Data();
//		Data dt2 = new Data(");
//		ObservableList<Data> list = FXCollections.observableArrayList();
//		list.add(d1);
//		list.add(d2);
//
//		TableView<data> tv = new TableView<Data>(list);
//		TableColumn<Data,String> tc_question = new TableColumn<Data,String>("问题");

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
                hospital_tab_query_doctor.jumpTool_close();
            }
        });


        ap.getChildren().addAll(label1,label2,label3,ta1,ta2,iv1,iv2,list,btn_back);

        return ap;
    }
}
