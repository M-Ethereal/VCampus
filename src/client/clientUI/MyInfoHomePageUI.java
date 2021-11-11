package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vo.Message;
import vo.Student;

import java.io.*;

public class MyInfoHomePageUI {
    private Student student;
    private MainFrameUI mainFrame;

    public MyInfoHomePageUI( Student student, MainFrameUI mainFrame ){
        this.student = student;
        this.mainFrame = mainFrame;
    }

    //上传图片
    private void savePic(InputStream inputStream, String fileName) {
        OutputStream os = null;
        try {
            File root2=new File(".");//获得当前文件夹（即工程目录），结果：
            String rootDir2=root2.getCanonicalPath();
            System.out.println("当前工程所在文件夹："+rootDir2);

            String path = "./src/client/Image/";// 保存路径名
            File tempFile = new File(path);
//            if (!tempFile.exists()) {
//                tempFile.mkdirs();
//            }

            byte[] bs = new byte[1024];// 1K的数据缓冲
            int len;// 读取到的数据长度

            // 输出的文件流保存到本地文件
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);

            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public AnchorPane infoHomePage()
    {
        AnchorPane an=new AnchorPane();

        Client client = new Client();
        String id = student.getId();

        int l_x = 400;
        int l_y = 50;
        int l_spacing = 54;
        int t_x = 520;
        int t_y = 50;
        int t_spacing = 52;
        Circle cir = new Circle();

        //换头像系统
        File file = new File("./src/client/Image/" + id + ".jpg");
        String Path = "";
        if(file.exists()==true){
            Path = "/client/Image/" + id + ".jpg";
        }
        else {
            Path = "/client/Image/HeadPortrait.png";
        }

        Image image=new Image(Path);
        ImageView iv=new ImageView(image);
        iv.setPreserveRatio(false);
        iv.setFitWidth(130);
        iv.setFitHeight(130);
        iv.setLayoutX(180);
        iv.setLayoutY(20);
        cir.setCenterX(65);
        cir.setCenterY(65);
        cir.setRadius(60);
        iv.setClip(cir);

        JFXButton headPChange = new JFXButton("上传新头像");
        headPChange.setStyle("-fx-font-size: 14px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-text-fill: WHITE;");
        headPChange.setLayoutX(196);
        headPChange.setLayoutY(200);
        headPChange.setPrefWidth(100);

        headPChange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.jpg");
                fileChooser.getExtensionFilters().add(pngFilter);
                File file = fileChooser.showOpenDialog( headPChange.getScene().getWindow() );

                Image image1 = new Image(file.toURI().toString());
                iv.setImage(image1);
                iv.setPreserveRatio(false);
                iv.setFitWidth(130);
                iv.setFitHeight(130);
                iv.setLayoutX(180);
                iv.setLayoutY(20);
                cir.setCenterX(65);
                cir.setCenterY(65);
                cir.setRadius(60);
                iv.setClip(cir);

                Image image2 = new Image(file.toURI().toString(),70, 70,false,true);
                mainFrame.tool_changeHeadP(image2);

                try {
                    savePic(new FileInputStream(file),id+".jpg");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });



        Label l1=new Label("姓名");
        l1.setFont(new Font(14));
        JFXTextField tx1 = new JFXTextField();
        tx1.setText(student.getName());
        tx1.setLabelFloat(false);
        tx1.setEditable(false);
        tx1.setAlignment(Pos.CENTER);
        tx1.setMouseTransparent(true);
        tx1.setFocusTraversable(false);
        l1.setLayoutX(l_x);
        l1.setLayoutY(l_y+0*l_spacing);
        tx1.setLayoutX(t_x);
        tx1.setLayoutY(t_y+0*t_spacing);


        Label l2=new Label("学号");
        l2.setFont(new Font(14));
        JFXTextField tx2 = new JFXTextField();
        tx2.setText(student.getNumber());
        tx2.setLabelFloat(false);
        tx2.setEditable(false);
        tx2.setAlignment(Pos.CENTER);
        tx2.setMouseTransparent(true);
        tx2.setFocusTraversable(false);
        l2.setLayoutX(l_x);
        l2.setLayoutY(l_y+1*l_spacing);
        tx2.setLayoutX(t_x);
        tx2.setLayoutY(t_y+1*t_spacing);


        Label l3=new Label("绩点");
        l3.setFont(new Font(14));
        JFXTextField tx3 = new JFXTextField();
        tx3.setText(String.valueOf(student.getGPA()));
        tx3.setLabelFloat(false);
        tx3.setEditable(false);
        tx3.setAlignment(Pos.CENTER);
        tx3.setMouseTransparent(true);
        tx3.setFocusTraversable(false);
        l3.setLayoutX(l_x);
        l3.setLayoutY(l_y+2*l_spacing);
        tx3.setLayoutX(t_x);
        tx3.setLayoutY(t_y+2*t_spacing);


        Label l4=new Label("SRTP");
        l4.setFont(new Font(14));
        JFXTextField tx4 = new JFXTextField();
        tx4.setText(String.valueOf(student.getSTRP()));
        tx4.setLabelFloat(false);
        tx4.setEditable(false);
        tx4.setAlignment(Pos.CENTER);
        tx4.setMouseTransparent(true);
        tx4.setFocusTraversable(false);
        l4.setLayoutX(l_x);
        l4.setLayoutY(l_y+3*l_spacing);
        tx4.setLayoutX(t_x);
        tx4.setLayoutY(t_y+3*t_spacing);


        Label l5=new Label("已修学分");
        l5.setFont(new Font(14));
        JFXTextField tx5 = new JFXTextField();
        tx5.setText(String.valueOf(student.getCredit()));
        tx5.setLabelFloat(false);
        tx5.setEditable(false);
        tx5.setAlignment(Pos.CENTER);
        tx5.setMouseTransparent(true);
        tx5.setFocusTraversable(false);
        l5.setLayoutX(l_x);
        l5.setLayoutY(l_y+4*l_spacing);
        tx5.setLayoutX(t_x);
        tx5.setLayoutY(t_y+4*t_spacing);



        Label l6=new Label("电话(可修改)");
        l6.setFont(new Font(14));
        JFXTextField tx6 = new JFXTextField();
        tx6.setPromptText(student.getPhoneNmuber());
        tx6.setFocusTraversable(false);
        tx6.setAlignment(Pos.CENTER);
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        tx6.getValidators().add(validator);
        tx6.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tx6.validate();
        });
        l6.setLayoutX(l_x);
        l6.setLayoutY(l_y+5*l_spacing);
        tx6.setLayoutX(t_x);
        tx6.setLayoutY(t_y+5*t_spacing);


        Label l7=new Label("宿舍(可修改)");
        l7.setFont(new Font(14));
        JFXTextField tx7 = new JFXTextField();
        tx7.setPromptText(student.getDormNum());
        tx7.setFocusTraversable(false);
        tx7.setAlignment(Pos.CENTER);
        RequiredFieldValidator validatorDOM = new RequiredFieldValidator();
        validatorDOM.setMessage("Input Required");
        tx7.getValidators().add(validatorDOM);
        tx7.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tx6.validate();
        });
        l7.setLayoutX(l_x);
        l7.setLayoutY(l_y+6*l_spacing);
        tx7.setLayoutX(t_x);
        tx7.setLayoutY(t_y+6*t_spacing);



        Student ss = new Student();
        ss = student;
        JFXButton bt = new JFXButton("提交");
        bt.setStyle("-fx-font-size: 14px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-text-fill: WHITE;");
        bt.setLayoutX(455);
        bt.setLayoutY(450);
        bt.setPrefWidth(100);
        bt.setFocusTraversable(false);
        Student finalSs = ss;
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                if(!tx6.getText().equals(""))
                {
                    finalSs.setPhoneNmuber(tx6.getText());
                }
                if(!tx7.getText().equals(""))
                {
                    finalSs.setDormNum(tx7.getText());
                }
                Message mes=new Message();
                mes.setData(finalSs);
                mes.setMessageType("UPDATE_STUDENT");
                Message serverResponse = client.sendRequestToServer(mes);

                if (serverResponse.isLastOperState() == true){
                    JFXDialogLayout layout_suc = new JFXDialogLayout();
                    layout_suc.setBody(new Label("      个人信息维护成功"));
                    JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) bt.getScene().getWindow());
                    alert_suc.setOverlayClose(true);
                    alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_suc.setContent(layout_suc);
                    alert_suc.initModality(Modality.NONE);
                    alert_suc.show();
                }
            }
        });
        an.getChildren().addAll(l1,headPChange, tx1,l2,tx2,l3,tx3,l4,tx4,l5,tx5,l6,tx6,l7,tx7,bt,iv);

        return an;
    }

}
