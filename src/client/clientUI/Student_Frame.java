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
//import server.biz.UserManagementImp;
import vo.Login;
import vo.Message;
import vo.User;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class Student_Frame extends Application
{
	Socket socket;
	//////////////////////////////////////
	@Override
	public void start(Stage primaryStage) {
		try {
			//程序名为VCampus
			primaryStage.setTitle("VCampus");
			//设置界面大小(黄金分割比xs)
			primaryStage.setWidth(900);
			primaryStage.setHeight(556);
			//界面大小固定
			primaryStage.setResizable(false);
			//页面初始在中间
			primaryStage.setX(300);
			primaryStage.setY(220);
			
			//scene根节点
			Group root = new Group();
			Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//添加一个学生查询按钮
			Button QueryBtn = new Button("查看我的信息");
			QueryBtn.setPrefWidth(600);
			QueryBtn.setPrefHeight(30);
			QueryBtn.setLayoutX(150);
			QueryBtn.setLayoutY(110);
			//添加一个学生修改按钮
			Button ModifyBtn = new Button("个人信息维护");
			ModifyBtn.setPrefWidth(600);
			ModifyBtn.setPrefHeight(30);
			ModifyBtn.setLayoutX(150);
			ModifyBtn.setLayoutY(200);
			//////消息响应函数两个：
			/*QueryBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Message mes = new Message();
					mes.setUserType(0);     // student: 0
					mes.setData(?????????);
					mes.setMessageType("Student_QueryInfo");
					Message serverResponse = client.sendRequestToServer(mes);
					
				}
			});
			
			ModifyBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Message mes = new Message();
					mes.setUserType(0);     // student: 0
					mes.setData(?????????);
					mes.setMessageType("Student_QueryInfo");
					Message serverResponse = client.sendRequestToServer(mes);
					
				}
			}); */
			
			/////////////////////////////////////展示操作
			root.getChildren().addAll(QueryBtn,ModifyBtn);
			primaryStage.setScene(scene);
			primaryStage.show();	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	///////////////////主方法
	public static void main(String[] args) 
	{
		launch(args);
	}
}
