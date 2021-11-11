package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
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

import java.util.ArrayList;


//可以使用父窗中的jumpCon函数帮助跳转
public class Hospital_jump_MedicalAdviceInfo {
    private MedicalAdvice medicalAdvice = new MedicalAdvice();
    private ArrayList<MedicalAdvice> relevantList = new ArrayList<MedicalAdvice>();
    private Hospital_tab_query hospital_tab_query;//父窗的句柄

    //背景
    Image bg = new Image("/client/Image/img2.jpg",1058, 580,false,true);
    ImageView backgroundView = new ImageView(bg);

    public Hospital_jump_MedicalAdviceInfo (MedicalAdvice medicalAdvice, Hospital_tab_query hospital_tab_query){
        this.medicalAdvice = medicalAdvice;
        this.hospital_tab_query = hospital_tab_query;
    }

    public AnchorPane medicalAdviceInfo(){
        AnchorPane ap = new AnchorPane();

        Label label1 = new Label();
        label1.setLayoutX(150);
        label1.setLayoutY(90);
        label1.setText("我的问题：");
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
        label2.setText(medicalAdvice.getDocName()+"医生的回复：");//显示姓名
        label2.setFont(new Font("Microsoft YaHei",20));

        //文本区域(不可修改)
        TextArea  ta2 = new TextArea();
        ta2.setStyle("-fx-background-color: WHITE;");
        ta2.setOpacity(0.8);
        ta2.setWrapText(true);
        if(medicalAdvice.getqAnswer() != null) ta2.setText(medicalAdvice.getqAnswer());
        else ta2.setText("-医生目前还没有回复哦-");
        ta2.setFont(Font.font(15));
        ta2.setPrefHeight(120);
        ta2.setPrefWidth(500);
        ta2.setLayoutX(130);
        ta2.setLayoutY(350);
        ta2.setEditable(false);

        DropShadow dropshadow2 = new DropShadow();// 阴影向外
        dropshadow2.setRadius(10);// 颜色蔓延的距离
        dropshadow2.setOffsetX(0);// 水平方向，0则向左右两侧，正则向右，负则向左
        dropshadow2.setOffsetY(0);// 垂直方向，0则向上下两侧，正则向下，负则向上
        dropshadow2.setSpread(0.1);// 颜色变淡的程度
        dropshadow2.setColor(Color.BLACK);// 设置颜色
        ta2.setEffect(dropshadow2);// 绑定指定窗口控件

        Label label4 = new Label();
        label4.setLayoutX(110);
        label4.setLayoutY(500);
        label4.setText("拖动滑块对该医生进行评分：");
        label4.setFont(new Font("Microsoft YaHei",20));

        JFXSlider rank = new JFXSlider();
        rank.setPrefWidth(180);
        rank.setLayoutX(365);
        rank.setLayoutY(510);

        //文本区域(进行评分，限制0~5)
//	    TextArea  ta3 = new TextArea();
//	    ta3.setFont(Font.font(15));
//	    ta3.setPrefHeight(20);
//	    ta3.setPrefWidth(100);
//	    ta3.setLayoutX(430);
//	    ta3.setLayoutY(590);
//	    ta3.textProperty().addListener(new ChangeListener<String>()
//	    {
//	    	public void changed(ObservableValue<? extends String> observable,String oldvalue,String newvalue)
//	    	{
//	    		if(newvalue.length()>1)
//	    		{
//	    			ta3.setText(oldvalue);
//	    		}
//	    	}
//	    });
//
//	    ta3.setTextFormatter(new TextFormatter<String>(new UnaryOperator<Change>()
//	    {
//	    	public Change apply(Change t)
//	    	{
//	    		System.out.println(t.getText());
//	    		String value = t.getText();
//	    		if(value.matches("[0-5]"))
//	    		{
//	    			return t;
//	    		}
//
//	    		return null;
//	    	}
//	    }));


        JFXButton b1 = new JFXButton();
        b1.setText("提交评分");
        b1.setLayoutX(550);
        b1.setLayoutY(500);
        b1.setPrefWidth(100);
        b1.setStyle("-fx-font-size: 14px;\n" +
                "jfx-button-type: RAISED;\n" +
                "-fx-background-color: rgb(77,102,204);\n" +
                "-fx-text-fill: WHITE;");

        //提交按钮,点击跳出弹框
        b1.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event) {
                double newRank = rank.getValue()/20;

                JFXDialogLayout layout_suc = new JFXDialogLayout();

                if(medicalAdvice.getRank() == false){
                    Client client_rank = new Client();
                    Message sendToServer = new Message();
                    sendToServer.setMessageType(MessageType.doc_rank);
                    sendToServer.setExtraMessage(String.valueOf(newRank));
                    sendToServer.setData(medicalAdvice);
                    medicalAdvice.setRank(true);//数据库已经改好了，现在这个存在本地的只要改一下值就可以
                    Message serverResponse_rank = client_rank.sendRequestToServer(sendToServer);

                    if(serverResponse_rank.isLastOperState() ==true)
                        layout_suc.setBody(new Label("      打分成功！"));
                    else
                        layout_suc.setBody(new Label("      打分失败：请检查网络设置"));
                }
                else{
                    layout_suc.setBody(new Label("      已经提交过打分啦~"));
                }

                JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) b1.getScene().getWindow());
                alert_suc.setOverlayClose(true);
                alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert_suc.setContent(layout_suc);
                alert_suc.initModality(Modality.NONE);
                alert_suc.show();
            }

        });

        JFXButton b2 = new JFXButton();
        b2.setText("再次提问");
        b2.setLayoutX(850);
        b2.setLayoutY(500);
        b2.setPrefWidth(100);
        b2.setStyle("-fx-font-size: 14px;\n" +
                "jfx-button-type: RAISED;\n" +
                "-fx-background-color: rgb(77,102,204);\n" +
                "-fx-text-fill: WHITE;");

        //跳转按钮-去2跳页面
	    b2.setOnAction(new EventHandler<ActionEvent>()
	    {
	    	public void handle(ActionEvent event) {
	    	    AnchorPane hospital_query = new Hospital_jump2_MedicalAdviceQuery(medicalAdvice,hospital_tab_query).HospitalQueryPage();
	    	    hospital_tab_query.Hospital_jump_control(hospital_query);
	    	}

	    });

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
                hospital_tab_query.jumpTool_close();
            }
        });


        ap.getChildren().addAll(backgroundView,label1,label2,label3,label4,ta1,ta2,b1,b2,iv1,iv2,list,rank,btn_back);

        return ap;
    }
}

