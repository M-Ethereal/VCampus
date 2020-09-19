//package client.clientUI;
//
//import client.socket.Client;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.stage.Stage;
//import javafx.scene.Cursor;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyCodeCombination;
//import javafx.scene.input.KeyCombination;
//import javafx.scene.input.Mnemonic;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import server.biz.UserManagement;
////import server.biz.UserManagementImp;
//import vo.Login;
//import vo.Message;
//import vo.User;
//
//import javax.swing.*;
//import java.io.*;
//import java.net.*;
//
//public class LoginPageUI extends Application{
//
//    Socket socket;
//    private class LoginInfo{
//        LoginInfo(String u,String p){
//            username = u;
//            password = p;
//        }
//        public String username;
//        public String password;
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        //	用户
//        Client client = new Client();
//
////	User
//        User user;
//        try {
//            //程序名为VCampus
//            primaryStage.setTitle("VCampus");
//            //设置界面大小(黄金分割比xs)
//            primaryStage.setWidth(900);
//            primaryStage.setHeight(556);
//            //界面大小固定
//            primaryStage.setResizable(false);
//            //页面初始在中间
//            primaryStage.setX(300);
//            primaryStage.setY(220);
//
//            //scene根节点
//            Group root = new Group();
//            Scene scene = new Scene(root,400,400);
//            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//
//            //添加一个登录按钮
//            Button LoginBtn = new Button("登录");
//            LoginBtn.setPrefWidth(80);
//            LoginBtn.setPrefHeight(30);
//            LoginBtn.setLayoutX(150);
//            LoginBtn.setLayoutY(110);
//
//
//            //提示文字
//            Label lb1 = new Label("用户名");
//            Label lb2 = new Label(" 密码");
//
//            //添加用户名框
//            TextField UserNametf = new TextField();
//            UserNametf.setPromptText("用户名");
//
//            //添加密码框
//            PasswordField PassWordtf = new PasswordField();
//            PassWordtf.setPromptText("密码");
//
//            //组合标签与文本框
//            HBox hb1 = new HBox();
//            hb1.getChildren().addAll(lb1,UserNametf);
//            hb1.setSpacing(10);
//            hb1.setLayoutX(100);
//            hb1.setLayoutY(10);
//            HBox hb2 = new HBox();
//            hb2.getChildren().addAll(lb2,PassWordtf);
//            hb2.setSpacing(19);
//            hb2.setLayoutX(100);
//            hb2.setLayoutY(60);
//
//
//            //设计单击响应事件
//            LoginBtn.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    String idtext = UserNametf.getText();
//                    String idstr = new String(idtext);
//
//                    String pwdtext = PassWordtf.getText();
//                    String pwdstr = new String(pwdtext);
//
////								异常事件处理，弹出对话框
//                    if(idtext.equals("")) {
//                        JOptionPane.showMessageDialog(null, "用户名不得为空，请重新输入。");
//                    }else if(pwdtext.equals("")) {
//                        JOptionPane.showMessageDialog(null, "密码不得为空，请重新输入。");
//                    }
//
////								登录检验
//                    Message mes = new Message();
//                    mes.setUserType(0); // student: 0
//                    Login login = new Login();
//                    login.setId(idstr);
//                    login.setPwd(pwdstr);
//                    mes.setData(login);
//                    mes.setMessageType("USER_LOGIN");
//
//                    Message serverResponse = client.sendRequestToServer(mes);
//                    System.out.println("密码检查中"+serverResponse.isLastOperState());
//
////									若登录成功
//                    if(serverResponse.isLastOperState()){
//                        System.out.println("登录成功");
//                    }
////									登陆不成功
//                    else {
//                        System.out.println("登录失败");
//                    }
//                }
//            });
//
//
////			//添加登录快捷键(Enter键自动登录)
////			KeyCombination kc = new KeyCodeCombination(KeyCode.ENTER);
////			scene.getAccelerators().put(kc, new Runnable() {
////				@Override
////				public void run() {
////					Login(new LoginInfo(UserNametf.getText(),PassWordtf.getText()));
////				}
////			}
////			);
//
//            root.getChildren().addAll(LoginBtn,hb1,hb2);
//
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//
////	//登陆操作
////	private void Login(LoginInfo user) {
////		try {
////			System.out.println("正在向服务端发送登录请求");
////			socket = new Socket("");
////
////			BufferedReader br = new BufferedReader(user.username);
////
////			PrintWriter write = new PrintWriter(socket.getOutputStream());
////			// 由Socket对象得到输出流，并构造PrintWriter对象
////            //3、获取输入流，并读取服务器端的响应信息
////    //        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
////            // 由Socket对象得到输入流，并构造相应的BufferedReader对象
////            String readline;
////            readline = br.readLine();
////
////
////
////            while (!readline.equals("end")) {
////                // 若从标准输入读入的字符串为 "end"则停止循环
////                write.println(readline);
////                // 将从系统标准输入读入的字符串输出到Server
////                write.flush();
////                // 刷新输出流，使Server马上收到该字符串
////                System.out.println("Client:" + readline);
////                // 在系统标准输出上打印读入的字符串
////  //              System.out.println("Server:" + in.readLine());
////                // 从Server读入一字符串，并打印到标准输出上
////                readline = br.readLine(); // 从系统标准输入读入一字符串
////            } // 继续循环
////            //4、关闭资源
////            write.close(); // 关闭Socket输出流
////  //          in.close(); // 关闭Socket输入流
////            socket.close(); // 关闭Socket
////
////			System.out.println("完成连接");
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////
////
////	}
////
////
////	public static void main(String[] args) {
////		launch(args);
////	}
//}
