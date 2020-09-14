package client.clientUI;

import client.socket.Client;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import server.biz.UserManagement;
import server.biz.UserManagementImp;
import vo.Login;
import vo.Message;
import vo.User;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class Main_Frame extends Application{

	Socket socket;
	private class LoginInfo{
		LoginInfo(String u,String p){
			username = u;
			password = p;
		}
		public String username;
		public String password;
	}
	
	@Override
	public void start(Stage primaryStage) {
		//	鐢ㄦ埛
		Client client = new Client();

//	User
		User user;
		try {
		     //这是一行新的注释！！！！！！
			primaryStage.setTitle("VCampus");
			//璁剧疆鐣岄潰澶у皬(榛勯噾鍒嗗壊姣攛s)
			primaryStage.setWidth(900);
			primaryStage.setHeight(556);
			//鐣岄潰澶у皬鍥哄畾
			primaryStage.setResizable(false);
			//椤甸潰鍒濆鍦ㄤ腑闂�
			primaryStage.setX(300);
			primaryStage.setY(220);
			
			//scene鏍硅妭鐐�
			Group root = new Group();
			Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//娣诲姞涓�涓櫥褰曟寜閽�
			Button LoginBtn = new Button("鐧诲綍");
			LoginBtn.setPrefWidth(80);
			LoginBtn.setPrefHeight(30);
			LoginBtn.setLayoutX(150);
			LoginBtn.setLayoutY(110);
			
			
			//鎻愮ず鏂囧瓧
			Label lb1 = new Label("鐢ㄦ埛鍚�");
			Label lb2 = new Label(" 瀵嗙爜");
			
			//娣诲姞鐢ㄦ埛鍚嶆
			TextField UserNametf = new TextField();
			UserNametf.setPromptText("鐢ㄦ埛鍚�");
			
			//娣诲姞瀵嗙爜妗�
			PasswordField PassWordtf = new PasswordField();
			PassWordtf.setPromptText("瀵嗙爜");
			
			//缁勫悎鏍囩涓庢枃鏈
			HBox hb1 = new HBox();
			hb1.getChildren().addAll(lb1,UserNametf);
			hb1.setSpacing(10);
			hb1.setLayoutX(100);
			hb1.setLayoutY(10);
			HBox hb2 = new HBox();
			hb2.getChildren().addAll(lb2,PassWordtf);
			hb2.setSpacing(19);
			hb2.setLayoutX(100);
			hb2.setLayoutY(60);
			
			
			//璁捐鍗曞嚮鍝嶅簲浜嬩欢
			LoginBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String idtext = UserNametf.getText();
					String idstr = new String(idtext);

					String pwdtext = PassWordtf.getText();
					String pwdstr = new String(pwdtext);

//								寮傚父浜嬩欢澶勭悊锛屽脊鍑哄等会我就会好的哈哈
					if(idtext.equals("")) {
						JOptionPane.showMessageDialog(null, "鐢ㄦ埛鍚嶄笉寰椾负绌猴紝璇烽噸鏂拌緭鍏ャ��");
					}else if(pwdtext.equals("")) {
						JOptionPane.showMessageDialog(null, "瀵嗙爜涓嶅緱涓虹┖锛岃閲嶆柊杈撳叆銆�");
					}

//								鐧诲綍妫�楠�
					Message mes = new Message();
					mes.setUserType(0); // student: 0
					Login login = new Login();
					login.setId(idstr);
					login.setPwd(pwdstr);
					mes.setData(login);
					mes.setMessageType("USER_LOGIN");

					Message serverResponse = client.sendRequestToServer(mes);
					System.out.println("瀵嗙爜妫�鏌ヤ腑"+serverResponse.isLastOperState());

//									鑻ョ櫥褰曟垚鍔�
					if(serverResponse.isLastOperState()){
						System.out.println("鐧诲綍鎴愬姛");
					}
//									鐧婚檰涓嶆垚鍔�
					else {
						System.out.println("鐧诲綍澶辫触");
					}
				}
			});
			
			
//			//娣诲姞鐧诲綍蹇嵎閿�(Enter閿嚜鍔ㄧ櫥褰�)
//			KeyCombination kc = new KeyCodeCombination(KeyCode.ENTER);
//			scene.getAccelerators().put(kc, new Runnable() {
//				@Override
//				public void run() {
//					Login(new LoginInfo(UserNametf.getText(),PassWordtf.getText()));
//				}
//			}
//			);
					
			root.getChildren().addAll(LoginBtn,hb1,hb2);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	//鐧婚檰鎿嶄綔
//	private void Login(LoginInfo user) {
//		try {
//			System.out.println("姝ｅ湪鍚戞湇鍔＄鍙戦�佺櫥褰曡姹�");
//			socket = new Socket("");
//
//			BufferedReader br = new BufferedReader(user.username);
//
//			PrintWriter write = new PrintWriter(socket.getOutputStream());
//			// 鐢盨ocket瀵硅薄寰楀埌杈撳嚭娴侊紝骞舵瀯閫燩rintWriter瀵硅薄
//            //3銆佽幏鍙栬緭鍏ユ祦锛屽苟璇诲彇鏈嶅姟鍣ㄧ鐨勫搷搴斾俊鎭�
//    //        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            // 鐢盨ocket瀵硅薄寰楀埌杈撳叆娴侊紝骞舵瀯閫犵浉搴旂殑BufferedReader瀵硅薄
//            String readline;
//            readline = br.readLine();
//
//
//
//            while (!readline.equals("end")) {
//                // 鑻ヤ粠鏍囧噯杈撳叆璇诲叆鐨勫瓧绗︿覆涓� "end"鍒欏仠姝㈠惊鐜�
//                write.println(readline);
//                // 灏嗕粠绯荤粺鏍囧噯杈撳叆璇诲叆鐨勫瓧绗︿覆杈撳嚭鍒癝erver
//                write.flush();
//                // 鍒锋柊杈撳嚭娴侊紝浣縎erver椹笂鏀跺埌璇ュ瓧绗︿覆
//                System.out.println("Client:" + readline);
//                // 鍦ㄧ郴缁熸爣鍑嗚緭鍑轰笂鎵撳嵃璇诲叆鐨勫瓧绗︿覆
//  //              System.out.println("Server:" + in.readLine());
//                // 浠嶴erver璇诲叆涓�瀛楃涓诧紝骞舵墦鍗板埌鏍囧噯杈撳嚭涓�
//                readline = br.readLine(); // 浠庣郴缁熸爣鍑嗚緭鍏ヨ鍏ヤ竴瀛楃涓�
//            } // 缁х画寰幆
//            //4銆佸叧闂祫婧�
//            write.close(); // 鍏抽棴Socket杈撳嚭娴�
//  //          in.close(); // 鍏抽棴Socket杈撳叆娴�
//            socket.close(); // 鍏抽棴Socket
//
//			System.out.println("瀹屾垚杩炴帴");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//
//	}
//
//
//	public static void main(String[] args) {
//		launch(args);
//	}
}
