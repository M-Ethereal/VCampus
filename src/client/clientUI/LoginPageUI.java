package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vo.*;

import javax.swing.*;
import java.net.Socket;

//import server.biz.UserManagementImp;

public class LoginPageUI {

    Socket socket;
    private User user;
    private Integer userType;




    public AnchorPane LoginPage(Scene scene) {
        //	用户
        Client client = new Client();

    	AnchorPane pane = new AnchorPane();

        //Logo
        Image MainLogo = new Image("/client/Image/MainFrameTitle.png",200, 120,true,true);
        ImageView MainLogoView = new ImageView(MainLogo);

        Image MainLogo_white = new Image("/client/Image/MainFrameTitle_white.png",200, 120,true,true);
        ImageView MainLogoView_white = new ImageView(MainLogo_white);

        //加载页
        //动图
//        Image img_loading = new Image("/client/Image/loading.gif",350, 350,true,true);
//        ImageView loading_view = new ImageView(img_loading);
        //黑色
//        Image img_loading = new Image("/client/Image/loadingPage2.png",450, 200,true,true);
//        ImageView loading_view = new ImageView(img_loading);
        //白色
        Image img_loading = new Image("/client/Image/loadingPage2_white.png",450, 200,true,true);
        ImageView loading_view = new ImageView(img_loading);
        VBox loadingVBox = new VBox();
        loadingVBox.getChildren().addAll(MainLogoView_white, loading_view);
        loadingVBox.setAlignment(Pos.CENTER);
        loadingVBox.setSpacing(20);
        loadingVBox.setStyle("-fx-background-color: #004d40");


    	
    	//背景轮播
//        StackPane stackPane = new StackPane();
		Button buttonTest = new Button("");
		buttonTest.setPrefSize(900, 556);
//        Label label = new Label("");
//        label.setOpacity(0.0);
//        stackPane.getChildren().addAll(buttonTest, label);
		//用线程实现轮播图
		new Thread(new Runnable() {
            Integer counter = 0;
//			String text="";
			@Override
			public void run(){
				while(true){
					//用button上文字的长度来实现循环，缺点是轮播图上会多几个小点
                    // （阅：大可不必，设个counter不就行了
//	                if (buttonTest.getText().trim().length() == 0){
                    if (counter == 0){
	                    //text=".";
	                    counter = (counter+1) % 5;
	                    buttonTest.setStyle("-fx-background-image:url('/client/Image/bg1.jpg')");
	                   
	                }
//	                else if(buttonTest.getText().trim().length() == 1){
                    else if(counter == 1){
                        counter = (counter+1) % 5;
	                	//text="..";
	                    buttonTest.setStyle("-fx-background-image:url('/client/Image/bg2.jpg')");
	                }
//	                else if(buttonTest.getText().trim().length() == 2){
                    else if(counter == 2){
                        counter = (counter+1) % 5;
	                	//text="...";
	                    buttonTest.setStyle("-fx-background-image:url('/client/Image/bg3.jpg')");
	                }
//	                else if(buttonTest.getText().trim().length() == 3){
                    else if(counter == 3){
                        counter = (counter+1) % 5;
	                	//text="....";
	                    buttonTest.setStyle("-fx-background-image:url('/client/Image/bg4.jpg')");
	                }
	                else{
                        counter = (counter+1) % 5;
	                	//text="";
	                    buttonTest.setStyle("-fx-background-image:url('/client/Image/bg5.jpg')");
	                }
	                
	                Platform.runLater(new Runnable(){
	                    @Override
	                    public void run(){
//	                    	buttonTest.setText(text);
	                    }
	                });
	                try {
	                    Thread.sleep(3500);	
	                } catch (InterruptedException ex) {
	                }
	            }
	        }
	    }).start();

        //背景框
        Label bg = new Label();
        bg.setPrefSize(320, 580);
        bg.setStyle("-fx-background-color:WHITE;");
        bg.setOpacity(0.8);
        bg.setLayoutX(650);

  /////
        //添加一个登录按钮
        JFXButton LoginBtn = new JFXButton("登录");
        LoginBtn.setPrefWidth(80);
        LoginBtn.setPrefHeight(30);
        LoginBtn.setLayoutX(790);
        LoginBtn.setLayoutY(365);//295
        LoginBtn.setStyle("-fx-font-size: 14px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-text-fill: WHITE;");

        //注册按钮
        JFXButton SignUpBtn = new JFXButton("注册");
        SignUpBtn.setPrefWidth(80);
        SignUpBtn.setPrefHeight(30);
        SignUpBtn.setLayoutX(680);
        SignUpBtn.setLayoutY(365);//295
        SignUpBtn.setStyle("-fx-font-size: 14px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-text-fill: WHITE;");

        JFXTextField UserNametf = new JFXTextField();
        UserNametf.setLabelFloat(true);
        UserNametf.setPromptText("用户名");
        UserNametf.setLayoutX(720);
        UserNametf.setLayoutY(240);//190
        UserNametf.setStyle("-jfx-focus-color:#50A1F0;");

        JFXPasswordField PassWordtf = new JFXPasswordField();
        PassWordtf.setLabelFloat(true);
        PassWordtf.setPromptText("密码");
        PassWordtf.setLayoutX(720);
        PassWordtf.setLayoutY(300);//240
        PassWordtf.setStyle("-jfx-focus-color:#50A1F0;");

        ImageView iv1 = new ImageView("/client/Image/user.png");
        iv1.setFitHeight(25);
        iv1.setFitWidth(25);
        iv1.setLayoutX(685);
        iv1.setLayoutY(240);
        ImageView iv2 = new ImageView("/client/Image/psw.png");
        iv2.setFitHeight(25);
        iv2.setFitWidth(25);
        iv2.setLayoutX(685);
        iv2.setLayoutY(300);

        MainLogoView.setLayoutX(675);
        MainLogoView.setLayoutY(120);

        pane.getChildren().addAll(buttonTest,bg,UserNametf,PassWordtf,LoginBtn,SignUpBtn, MainLogoView, iv1, iv2);

        //////
    	
      //设计单击响应事件
      LoginBtn.setOnAction(new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent event) {

              System.out.println("***用户登录***");
              String idtext = UserNametf.getText();
              String idstr = new String(idtext);
              System.out.println("用户ID：" + idstr);

              String pwdtext = PassWordtf.getText();
              String pwdstr = new String(pwdtext);
              System.out.println("密码：" + pwdstr);

//							异常事件处理，弹出对话框
              if(idtext.equals("")) {
                  JOptionPane.showMessageDialog(null, "用户名不得为空，请重新输入。");
                  return;
              }else if(pwdtext.equals("")) {
                  JOptionPane.showMessageDialog(null, "密码不得为空，请重新输入。");
                  return;
              }

//							登录检验
              Message mes = new Message();
              int type = -1 ;
              //用用户名确认身份
              if(idstr.length()<5) {
            	  JOptionPane.showMessageDialog(null, "不符合规范的用户名！");
                  return;
              }
              System.out.println("检查中-用户名前四位：" + idstr.subSequence(0, 4));
              if(idstr.subSequence(0, 4).equals("2131"))
            	  type = 0;
              else if(idstr.subSequence(0, 2).equals("00"))
            	  type = 1;
              else if(Character.isUpperCase(idstr.charAt(1)))
            	  type = 2;
              else if(idstr.subSequence(0, 2).equals("bz"))
            	  type = 3;
              else if(idstr.subSequence(0, 5).equals("admin"))
            	  type = 4;
              else {
            	  JOptionPane.showMessageDialog(null, "不符合规范的用户名！");
                  return;
              }
              System.out.println("检查中-用户类型：" + type);

              mes.setUserType(type);
              Login login = new Login();
              login.setId(idstr);
              login.setPwd(pwdstr);
              login.setUserType(type);
              mes.setData(login);
              mes.setMessageType("USER_LOGIN");

              Message serverResponse = client.sendRequestToServer(mes);

              System.out.println("密码检查中"+serverResponse.isLastOperState());
              //待加，根据不同身份进入不同窗口
              //已加
              //0-student  1-teacher  2-doctor  3-retailer  4-admin


              //加载页设置
              AnchorPane loading = new AnchorPane(loadingVBox);
              JFXDialogLayout layout_loading = new JFXDialogLayout();
              layout_loading.setBody(loading);
              DialogPane dp = new DialogPane();
              dp.setContent(loadingVBox);
//								若登录成功
              if(serverResponse.isLastOperState()){
                  switch (type){
                      case 0://学生
//                          //加载动画
//                          AnchorPane loading = new AnchorPane(loadingVBox);
//                          JFXDialogLayout layout_loading = new JFXDialogLayout();
//                          layout_loading.setBody(loading);
//                          DialogPane dp = new DialogPane();
//                          dp.setContent(loadingVBox);
                          Alert alert = new Alert(Alert.AlertType.INFORMATION);
                          alert.initStyle(StageStyle.UNDECORATED);
                          alert.setHeaderText(null);
                          alert.setContentText(null);
                          alert.setDialogPane(dp);
                          alert.getDialogPane().getStylesheets()
                                  .add(getClass().getResource("Main.css").toExternalForm());
                          alert.show();

                          Student student = (Student) serverResponse.getData();
                          Platform.runLater(() -> {
                              //创建主界面窗口
                              try {
                                  ((Stage) LoginBtn.getScene().getWindow()).hide();
                                  new MainFrameUI(student).start(new Stage());
                              } catch (Exception e) {
                                  e.printStackTrace();
                              }
                              //关闭登陆窗口和加载页
                              alert.hide();
//                              ((Stage) LoginBtn.getScene().getWindow()).hide();
                          });
//                          try{
//                              System.out.println("*******************14*****************");
//                              psg = mf.start(psg);
//                              System.out.println("*******************15*****************");
//
//
//
//                          } catch (Exception e) {
//                              e.printStackTrace();
//                          }
//                          System.out.println("*******************done*****************");
//                          psg.show();

                          break;

                      case 1://老师
                          Alert alert_teacher = new Alert(Alert.AlertType.INFORMATION);
                          alert_teacher.initStyle(StageStyle.UNDECORATED);
                          alert_teacher.setHeaderText(null);
                          alert_teacher.setContentText(null);
                          alert_teacher.setDialogPane(dp);
                          alert_teacher.getDialogPane().getStylesheets()
                                  .add(getClass().getResource("Main.css").toExternalForm());
                          alert_teacher.show();

                          Teacher teacher = (Teacher) serverResponse.getData();
                          Platform.runLater(() -> {
                              //创建主界面窗口
                              try {
                                  ((Stage) LoginBtn.getScene().getWindow()).hide();
                                  new MainFrame_Teacher(teacher).start(new Stage());
                              } catch (Exception e) {
                                  e.printStackTrace();
                              }
                              //关闭登陆窗口和加载页
                              alert_teacher.hide();
//                              ((Stage) LoginBtn.getScene().getWindow()).hide();
                          });

                          break;
                      case 2://医生
                          Alert alert_doctor = new Alert(Alert.AlertType.INFORMATION);
                          alert_doctor.initStyle(StageStyle.UNDECORATED);
                          alert_doctor.setHeaderText(null);
                          alert_doctor.setContentText(null);
                          alert_doctor.setDialogPane(dp);
                          alert_doctor.getDialogPane().getStylesheets()
                                  .add(getClass().getResource("Main.css").toExternalForm());
                          alert_doctor.show();

                          Doctor doctor = (Doctor) serverResponse.getData();
                          Platform.runLater(() -> {
                              //创建主界面窗口
                              try {
                                  ((Stage) LoginBtn.getScene().getWindow()).hide();
                                  new MainFrame_Doctor(doctor).start(new Stage());
                              } catch (Exception e) {
                                  e.printStackTrace();
                              }
                              //关闭登陆窗口和加载页
                              alert_doctor.hide();
//                              ((Stage) LoginBtn.getScene().getWindow()).hide();
                          });
                          break;

                      case 3://商人
                          Alert alert_retailer = new Alert(Alert.AlertType.INFORMATION);
                          alert_retailer.initStyle(StageStyle.UNDECORATED);
                          alert_retailer.setHeaderText(null);
                          alert_retailer.setContentText(null);
                          alert_retailer.setDialogPane(dp);
                          alert_retailer.getDialogPane().getStylesheets()
                                  .add(getClass().getResource("Main.css").toExternalForm());
                          alert_retailer.show();

                          Retailer retailer = (Retailer) serverResponse.getData();
                          Platform.runLater(() -> {
                              //创建主界面窗口
                              try {
                                  ((Stage) LoginBtn.getScene().getWindow()).hide();
                                  new MainFrame_Retailer(retailer).start(new Stage());
                              } catch (Exception e) {
                                  e.printStackTrace();
                              }
                              //关闭登陆窗口和加载页
                              alert_retailer.hide();
//                              ((Stage) LoginBtn.getScene().getWindow()).hide();
                          });
                          break;

                      case 4://管理员
//                          //加载动画
//                          AnchorPane loading = new AnchorPane(loadingVBox);
//                          JFXDialogLayout layout_loading = new JFXDialogLayout();
//                          layout_loading.setBody(loading);
//                          DialogPane dp = new DialogPane();
//                          dp.setContent(loadingVBox);
                          Alert alert_admin = new Alert(Alert.AlertType.INFORMATION);
                          alert_admin.initStyle(StageStyle.UNDECORATED);
                          alert_admin.setHeaderText(null);
                          alert_admin.setContentText(null);
                          alert_admin.setDialogPane(dp);
                          alert_admin.getDialogPane().getStylesheets()
                                  .add(getClass().getResource("Main.css").toExternalForm());
                          alert_admin.show();

                          Platform.runLater(() -> {
                              //创建主界面窗口
                              try {
                                  ((Stage) LoginBtn.getScene().getWindow()).hide();
                                  new MainFrame_Admin().start(new Stage());
                              } catch (Exception e) {
                                  e.printStackTrace();
                              }
                              //关闭登陆窗口和加载页
                              alert_admin.hide();
//                              ((Stage) LoginBtn.getScene().getWindow()).hide();
                          });
                          break;
                  }
              }
//								登陆不成功
              else {
//            	  JOptionPane.showMessageDialog(null, serverResponse.getErrorMessage());
                  JFXDialogLayout layout_f = new JFXDialogLayout();
                  layout_f.setBody(new Label("      登陆失败：" + serverResponse.getErrorMessage()));
                  JFXAlert<Void> alert_f = new JFXAlert<>((Stage) LoginBtn.getScene().getWindow());
                  alert_f.setOverlayClose(true);
                  alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                  alert_f.setContent(layout_f);
                  alert_f.initModality(Modality.NONE);
                  alert_f.show();
              }
          }
      });


      SignUpBtn.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              String idtext = UserNametf.getText();
              String idstr = new String(idtext);
              System.out.println("用户ID：" + idstr);

              String pwdtext = PassWordtf.getText();
              String pwdstr = new String(pwdtext);
              System.out.println("密码：" + pwdstr);

//							异常事件处理，弹出对话框
              if(idtext.equals("")) {
//                  JOptionPane.showMessageDialog(null, "用户名不得为空，请重新输入。");
                  JFXDialogLayout layout_f = new JFXDialogLayout();
                  layout_f.setBody(new Label("      用户名不得为空，请重新输入。"));
                  JFXAlert<Void> alert_f = new JFXAlert<>((Stage) SignUpBtn.getScene().getWindow());
                  alert_f.setOverlayClose(true);
                  alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                  alert_f.setContent(layout_f);
                  alert_f.initModality(Modality.NONE);
                  alert_f.show();
                  return;
              }else if(pwdtext.equals("")) {
//                  JOptionPane.showMessageDialog(null, "密码不得为空，请重新输入。");
                  JFXDialogLayout layout_f = new JFXDialogLayout();
                  layout_f.setBody(new Label("      密码不得为空，请重新输入。"));
                  JFXAlert<Void> alert_f = new JFXAlert<>((Stage) SignUpBtn.getScene().getWindow());
                  alert_f.setOverlayClose(true);
                  alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                  alert_f.setContent(layout_f);
                  alert_f.initModality(Modality.NONE);
                  alert_f.show();
                  return;
              }

//							注册检验
              Message mes = new Message();
              int type = -1 ;
              //用用户名确认身份
              if(idstr.length()<5) {
            	  JOptionPane.showMessageDialog(null, "不符合规范的用户名！");
                  return;
              }
              if(idstr.subSequence(0, 4).equals("2131"))
            	  type = 0;
              else if(idstr.subSequence(0, 2).equals("00"))
            	  type = 1;
              else if(Character.isUpperCase(idstr.charAt(1)))
            	  type = 2;
              else if(idstr.subSequence(0, 2).equals("bz"))
            	  type = 3;
              else if(idstr.subSequence(0, 5).equals("admin"))
            	  type = 4;
              else {
            	  JOptionPane.showMessageDialog(null, "不符合规范的用户名！");
                  return;
              }
              mes.setUserType(type);
              SignUp signup = new SignUp();
              signup.setId(idstr);
              signup.setPwd(pwdstr);
              signup.setUserType(type);
              mes.setData(signup);
              mes.setMessageType("REGISTER");

              Message serverResponse = client.sendRequestToServer(mes);
              System.out.println("注册中"+serverResponse.isLastOperState());

//								若注册成功
              if(serverResponse.isLastOperState()){
//            	  JOptionPane.showMessageDialog(null, "注册成功");
                  JFXDialogLayout layout_suc = new JFXDialogLayout();
                  layout_suc.setBody(new Label("      注册成功！"));
                  JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) SignUpBtn.getScene().getWindow());
                  alert_suc.setOverlayClose(true);
                  alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                  alert_suc.setContent(layout_suc);
                  alert_suc.initModality(Modality.NONE);
                  alert_suc.show();
              }
//								注册不成功
              else {
//            	  JOptionPane.showMessageDialog(null, serverResponse.getErrorMessage());
                  JFXDialogLayout layout_f = new JFXDialogLayout();
                  layout_f.setBody(new Label("      注册失败：" + serverResponse.getErrorMessage()));
                  JFXAlert<Void> alert_f = new JFXAlert<>((Stage) SignUpBtn.getScene().getWindow());
                  alert_f.setOverlayClose(true);
                  alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                  alert_f.setContent(layout_f);
                  alert_f.initModality(Modality.NONE);
                  alert_f.show();
              }
          }
      });
    	return pane;


    }
}