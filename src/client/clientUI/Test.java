package client.clientUI;////package client.clientUI;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Test extends Application {
    double x_start = 0;
    double x_end = 0;
    public static void main(String[] args) { Application.launch(args); }
    @Override public void start(Stage stage) throws FileNotFoundException {

//        Task<Void> task = new Task<Void>() {
//            @Override public Void call() {
//                for (int i = 0; i < 10; i++) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        Thread.interrupted();
//                        break;
//                    }
//                    System.out.println(i + 1);
//                    updateProgress(i + 1, 10);
//                }
//                return null;
//            }
//        };
//        ProgressBar updProg = new ProgressBar();
//
//        updProg.progressProperty().bind(task.progressProperty());
//        Thread th = new Thread(task);
//        th.setDaemon(true);
//        th.start();
//        StackPane layout = new StackPane();
//        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
//        layout.getChildren().add(updProg);
//        stage.setScene(new Scene(layout));
//        //加载动画
//        AnchorPane loading = new AnchorPane();
//        loading.setPrefSize(400,400);
//        //Image img1 = new Image("/client/Image/hospital.png",350, 350,true,true);
//        Image img1 = new Image("/client/Image/loading.gif",350.0,350.0,true,true);
////        FileInputStream f1 = new FileInputStream(new File("E:\\zzy\\src\\client\\Image\\loading_.gif"));
////        Image img1 = new Image(f1);
//        ImageView iv1 = new ImageView(img1);
//        loading.getChildren().add(iv1);
//        loading.setTopAnchor(iv1,25.0);
//        loading.setLeftAnchor(iv1,25.0);
//        Scene sc = new Scene(loading);
//        Stage loadingStage = new Stage();
//        loadingStage.setScene(sc);
//        loadingStage.initStyle(StageStyle.UNDECORATED);
//        loadingStage.centerOnScreen();
//        loadingStage.show();


        AnchorPane ap = new AnchorPane();
        Button b6 = new Button("b6");
        b6.setPrefHeight(86);
        b6.setVisible(false);

        Button b2 = new Button("block");
        b2.setPrefSize(50,86);
        b2.setLayoutX(55);

        Button b1 = new Button("b1");
        b1.setStyle("-fx-background-color:#987654");
        b1.setPrefWidth(700);
        b1.setPrefHeight(86);
        b1.setOpacity(0.5);//透明

        b1.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event){
                b1.startFullDrag();
                double t = event.getSceneX();

                x_start = t;
                b6.setVisible(false);
            }
        });

        //拖拽生成图形
        b1.setOnMouseDragOver(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getSceneX());
                double t = event.getSceneX();
                x_end = t;
                b6.setVisible(true);
                b6.setStyle("-fx-background-color:#4876FF55");
                b6.setLayoutX(Math.min(x_end, x_start));
//                b6.setLayoutY(Math.floor((sY-80)/140)*140+80-220);
                b6.setPrefWidth(Math.abs(x_end - x_start));
            }
        });


        ap.getChildren().addAll(b6,b1,b2);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(ap);
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();


    }
}
////
//
//
//
//
//
//
//
//
////
////
////package client.clientUI;
////import client.socket.Client;
////
//import javafx.application.Application;
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.stage.Stage;
//import javafx.util.Callback;
//import javafx.scene.Cursor;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.Tab;
//import javafx.scene.control.TabPane;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableColumn.CellDataFeatures;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyCodeCombination;
//import javafx.scene.input.KeyCombination;
//import javafx.scene.input.Mnemonic;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//import server.dao.DoctorDao;
//import server.dao.StudentDao;
//import server.dao.TeacherDao;
//import server.dao.RetailerDao;
//import util.MessageType;
//import vo.Login;
//import vo.Message;
//import vo.User;
//import server.socket.*;
//
//import javax.swing.*;
//import java.io.*;
//import java.net.*;
//import java.util.ArrayList;
//
//import vo.Student;
//import vo.Teacher;
//import vo.Doctor;
//import vo.Retailer;
////
////public class Test extends Application
////{
////    Socket socket;
////    //////////////////////////////////////
////    @Override
////    public void start(Stage primaryStage) {
////        Client client = new Client();
////        try {
////            //程序名为VCampus
////            primaryStage.setTitle("VCampus");
////            //设置界面大小(黄金分割比xs)
////            primaryStage.setWidth(1058);
////            primaryStage.setHeight(580);
////            //界面大小固定
////            primaryStage.setResizable(false);
////            //页面初始在中间
////            primaryStage.setX(300);
////            primaryStage.setY(220);
////
////            AnchorPane an=new AnchorPane();
////            Scene scene = new Scene(an);
////            TabPane tabpane=new TabPane();
////            tabpane.setTabMinWidth(243);
////            an.getChildren().add(tabpane);
////            Tab tab1=new Tab("学生信息");
////            Tab tab2=new Tab("教师信息");
////            Tab tab3=new Tab("商户信息");
////            Tab tab4=new  Tab("医生信息");
////            tab1.setClosable(false);
////            tab2.setClosable(false);
////            tab3.setClosable(false);
////            tab4.setClosable(false);
////            tabpane.getTabs().addAll(tab1,tab2,tab3,tab4);  //别忘了加入到父亲中去！
////
/////////////////////////////选项1页面内容：
//////            AnchorPane an1=new AnchorPane();
//////            Message mes1 = new Message();
//////            mes1.setMessageType("OUTPUT_ALL_STUDENT");
//////            Message serverResponse1 = client.sendRequestToServer(mes1);
//////            ArrayList<Student> relist1= (ArrayList<Student>) serverResponse1.getData();
//////            ObservableList<Student> list1= FXCollections.observableArrayList();
//////            for(int i=0;i<relist1.size();i++)
//////            {
//////                list1.add(relist1.get(i));
//////            }
//////            TableView<Student>  tv=new TableView<Student>(list1);
//////            tv.setPrefWidth(890);
//////            tv.setPrefHeight(450);
//////            TableColumn<Student,String> tc_uID=new TableColumn<Student,String>("一卡通");
//////            tc_uID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
//////            {
//////                @Override
//////                public ObservableValue<String> call(CellDataFeatures<Student,String> param)
//////                {
//////                    SimpleStringProperty id=new SimpleStringProperty(param.getValue().getId());
//////                    return id;
//////                }
//////            });
//////            tv.getColumns().add(tc_uID);
//////            TableColumn<Student,String> tc_upwd=new TableColumn<Student,String>("密码");
//////            tc_upwd.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
//////            {
//////                @Override
//////                public ObservableValue<String> call(CellDataFeatures<Student,String> param)
//////                {
//////                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getpwd());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_upwd);
//////            TableColumn<Student,String> tc_name=new TableColumn<Student,String>("姓名");
//////            tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
//////            {
//////                @Override
//////                public ObservableValue<String> call(CellDataFeatures<Student,String> param)
//////                {
//////                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getName());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_name);
//////            TableColumn<Student,String> tc_number=new TableColumn<Student,String>("学号");
//////            tc_number.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
//////            {
//////                @Override
//////                public ObservableValue<String> call(CellDataFeatures<Student,String> param)
//////                {
//////                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getNumber());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_number);
//////            TableColumn<Student,Number> tc_major=new TableColumn<Student,Number>("专业");
//////            tc_major.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
//////            {
//////                @Override
//////                public ObservableValue<Number> call(CellDataFeatures<Student,Number> param)
//////                {
//////                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getMajorId());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_major);
//////            TableColumn<Student,Number> tc_GPA=new TableColumn<Student,Number>("绩点");
//////            tc_GPA.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
//////            {
//////                @Override
//////                public ObservableValue<Number> call(CellDataFeatures<Student,Number> param)
//////                {
//////                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getGPA());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_GPA);
//////            TableColumn<Student,Number> tc_credit=new TableColumn<Student,Number>("学分");
//////            tc_credit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
//////            {
//////                @Override
//////                public ObservableValue<Number> call(CellDataFeatures<Student,Number> param)
//////                {
//////                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getCredit());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_credit);
//////            TableColumn<Student,Number> tc_srtp=new TableColumn<Student,Number>("研学");
//////            tc_srtp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
//////            {
//////                @Override
//////                public ObservableValue<Number> call(CellDataFeatures<Student,Number> param)
//////                {
//////                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSTRP());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_srtp);
//////            TableColumn<Student,Number> tc_state=new TableColumn<Student,Number>("状态");
//////            tc_state.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
//////            {
//////                @Override
//////                public ObservableValue<Number> call(CellDataFeatures<Student,Number> param)
//////                {
//////                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getState());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_state);
//////            TableColumn<Student,Number> tc_age=new TableColumn<Student,Number>("年龄");
//////            tc_age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
//////            {
//////                @Override
//////                public ObservableValue<Number> call(CellDataFeatures<Student,Number> param)
//////                {
//////                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getAge());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_age);
//////            TableColumn<Student,Number> tc_sex=new TableColumn<Student,Number>("性别");
//////            tc_sex.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
//////            {
//////                @Override
//////                public ObservableValue<Number> call(CellDataFeatures<Student,Number> param)
//////                {
//////                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSex());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_sex);
//////            TableColumn<Student,String> tc_register=new TableColumn<Student,String>("注册时间");
//////            tc_register.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
//////            {
//////                @Override
//////                public ObservableValue<String> call(CellDataFeatures<Student,String> param)
//////                {
//////                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getRegister());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_register);
//////            TableColumn<Student,Number> tc_money=new TableColumn<Student,Number>("余额宝");
//////            tc_money.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
//////            {
//////                @Override
//////                public ObservableValue<Number> call(CellDataFeatures<Student,Number> param)
//////                {
//////                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getCardBalance());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_money);
//////            TableColumn<Student,String> tc_renown=new TableColumn<Student,String>("声誉");
//////            tc_renown.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
//////            {
//////                @Override
//////                public ObservableValue<String> call(CellDataFeatures<Student,String> param)
//////                {
//////                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getRenown());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_renown);
//////            TableColumn<Student,Number> tc_campusposition=new TableColumn<Student,Number>("校区");
//////            tc_campusposition.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
//////            {
//////                @Override
//////                public ObservableValue<Number> call(CellDataFeatures<Student,Number> param)
//////                {
//////                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getCampusPosition());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_campusposition);
//////            TableColumn<Student,Number> tc_punishment=new TableColumn<Student,Number>("惩罚");
//////            tc_punishment.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
//////            {
//////                @Override
//////                public ObservableValue<Number> call(CellDataFeatures<Student,Number> param)
//////                {
//////                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getPunishment());
//////                    return pw;
//////                }
//////            });
//////            tv.getColumns().add(tc_punishment);
//////            tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
//////            Button add1=new Button("增加记录");
//////            add1.setLayoutX(938);
//////            add1.setLayoutY(110);
//////            Button delete1=new Button("删除记录");
//////            delete1.setLayoutX(938);
//////            delete1.setLayoutY(170);
//////            Button modify1=new Button("修改记录");
//////            modify1.setLayoutX(938);
//////            modify1.setLayoutY(230);
//////
//////            add1.setOnAction(new EventHandler<ActionEvent>() {
//////                @Override
//////                public void handle(ActionEvent event)
//////                {
//////                    AnchorPane an11=new AnchorPane();
//////                    ScrollPane sc=new ScrollPane();
//////                    Text t11=new Text("请输入待增加的学生信息：");
//////                    Label l1=new Label("一卡通");
//////                    TextField tf1=new TextField();
//////                    HBox hb1=new HBox();
//////                    hb1.getChildren().addAll(l1,tf1);
//////                    Label l2=new Label("密码");
//////                    TextField tf2=new TextField();
//////                    HBox hb2=new HBox();
//////                    hb2.getChildren().addAll(l2,tf2);
//////                    Label l3=new Label("姓名");
//////                    TextField tf3=new TextField();
//////                    HBox hb3=new HBox();
//////                    hb3.getChildren().addAll(l3,tf3);
//////                    Label l4=new Label("用户类型");
//////                    TextField tf4=new TextField();
//////                    HBox hb4=new HBox();
//////                    hb4.getChildren().addAll(l4,tf4);
//////                    Label l5=new Label("学号");
//////                    TextField tf5=new TextField();
//////                    HBox hb5=new HBox();
//////                    hb5.getChildren().addAll(l5,tf5);
//////                    Label l6=new Label("专业");
//////                    TextField tf6=new TextField();
//////                    HBox hb6=new HBox();
//////                    hb6.getChildren().addAll(l6,tf6);
//////                    Label l7=new Label("绩点");
//////                    TextField tf7=new TextField();
//////                    HBox hb7=new HBox();
//////                    hb7.getChildren().addAll(l7,tf7);
//////                    Label l8=new Label("学分");
//////                    TextField tf8=new TextField();
//////                    HBox hb8=new HBox();
//////                    hb8.getChildren().addAll(l8,tf8);
//////                    Label l9=new Label("研学");
//////                    TextField tf9=new TextField();
//////                    HBox hb9=new HBox();
//////                    hb9.getChildren().addAll(l9,tf9);
//////                    Label l10=new Label("状态");
//////                    TextField tf10=new TextField();
//////                    HBox hb10=new HBox();
//////                    hb10.getChildren().addAll(l10,tf10);
//////                    Label l11=new Label("电话");
//////                    TextField tf11=new TextField();
//////                    HBox hb11=new HBox();
//////                    hb11.getChildren().addAll(l11,tf11);
//////                    Label l12=new Label("年龄");
//////                    TextField tf12=new TextField();
//////                    HBox hb12=new HBox();
//////                    hb12.getChildren().addAll(l12,tf12);
//////                    Label l13=new Label("性别");
//////                    TextField tf13=new TextField();
//////                    HBox hb13=new HBox();
//////                    hb13.getChildren().addAll(l13,tf13);
//////                    Label l14=new Label("注册时间");
//////                    TextField tf14=new TextField();
//////                    HBox hb14=new HBox();
//////                    hb14.getChildren().addAll(l14,tf14);
//////                    Label l15=new Label("宿舍");
//////                    TextField tf15=new TextField();
//////                    HBox hb15=new HBox();
//////                    hb15.getChildren().addAll(l15,tf15);
//////                    Label l16=new Label("余额宝");
//////                    TextField tf16=new TextField();
//////                    HBox hb16=new HBox();
//////                    hb16.getChildren().addAll(l16,tf16);
//////                    Label l17=new Label("借书数目");
//////                    TextField tf17=new TextField();
//////                    HBox hb17=new HBox();
//////                    hb17.getChildren().addAll(l17,tf17);
//////                    Label l18=new Label("声誉");
//////                    TextField tf18=new TextField();
//////                    HBox hb18=new HBox();
//////                    hb18.getChildren().addAll(l18,tf18);
//////                    Label l19=new Label("校区");
//////                    TextField tf19=new TextField();
//////                    HBox hb19=new HBox();
//////                    hb19.getChildren().addAll(l19,tf19);
//////                    Label l20=new Label("惩罚");
//////                    TextField tf20=new TextField();
//////                    HBox hb20=new HBox();
//////                    hb20.getChildren().addAll(l20,tf20);
//////                    hb1.setSpacing(11);
//////                    hb2.setSpacing(30);
//////                    hb3.setSpacing(30);
//////                    hb4.setSpacing(10);
//////                    hb5.setSpacing(30);
//////                    hb6.setSpacing(30);
//////                    hb7.setSpacing(30);
//////                    hb8.setSpacing(30);
//////                    hb9.setSpacing(30);
//////                    hb10.setSpacing(30);
//////                    hb11.setSpacing(30);
//////                    hb12.setSpacing(30);
//////                    hb13.setSpacing(30);
//////                    hb14.setSpacing(10);
//////                    hb15.setSpacing(30);
//////                    hb16.setSpacing(11);
//////                    hb17.setSpacing(9);
//////                    hb18.setSpacing(30);
//////                    hb19.setSpacing(30);
//////                    hb20.setSpacing(30);
//////                    Button con1=new Button("确定");
//////                    Button ret1=new Button("返回上页");
//////                    HBox hb21=new HBox();
//////                    hb21.getChildren().addAll(con1,ret1);
//////                    hb21.setSpacing(30);
//////                    /////////////////////////////////添加页面的两个响应函数：
//////                    ret1.setOnAction(new EventHandler<ActionEvent>() {
//////                        @Override
//////                        public void handle(ActionEvent event)
//////                        {
//////                            tab1.setContent(an1);
//////                        }
//////                    });
//////                    con1.setOnAction(new EventHandler<ActionEvent>() {
//////                        @Override
//////                        public void handle(ActionEvent event)
//////                        {
//////                            Student ss=new Student();
//////                            ss.setId(tf1.getText());
//////                            ss.setpwd(tf2.getText());
//////                            ss.setName(tf3.getText());
//////                            ss.setUserType(Integer.parseInt(tf4.getText()));
//////                            ss.setNumber(tf5.getText());
//////                            ss.setMajorId(Integer.parseInt(tf6.getText()));
//////                            ss.setGPA(Double.parseDouble(tf7.getText()));
//////                            ss.setCredit(Double.parseDouble(tf8.getText()));
//////                            ss.setSTRP(Double.parseDouble(tf9.getText()));
//////                            ss.setState(Integer.parseInt(tf10.getText()));
//////                            ss.setPhoneNmuber(tf11.getText());
//////                            ss.setAge(Integer.parseInt(tf12.getText()));
//////                            ss.setSex(Integer.parseInt(tf13.getText()));
//////                            ss.setRegister(tf14.getText());
//////                            ss.setDormNum(tf15.getText());
//////                            ss.setCardBalance(Double.parseDouble(tf16.getText()));
//////                            ss.setLendBooksNum(Integer.parseInt(tf17.getText()));
//////                            ss.setRenown(tf18.getText());
//////                            ss.setCampusPosition(Integer.parseInt(tf19.getText()));
//////                            ss.setPunishment(Integer.parseInt(tf20.getText()));
//////                            Message mes = new Message();
//////                            mes.setMessageType("ADD_STUDENT");
//////                            mes.setData(ss);
//////                            Message serverResponse = client.sendRequestToServer(mes);
//////                            tab1.setContent(sc);
//////
//////                        }
//////                    });
//////                    VBox vb1=new VBox();
//////                    vb1.getChildren().addAll(t11,hb1,hb2,hb3,hb4,hb5,hb6,hb7,hb8,hb9,hb10,hb11,hb12,hb13,hb14,hb15,hb16,hb17,hb18,hb19,hb20,hb21);
//////                    vb1.setSpacing(25);
//////                    an11.getChildren().add(vb1);
//////                    an11.setLeftAnchor(vb1, 350.0);
//////                    an11.setTopAnchor(vb1, 20.0);
//////                    sc.setContent(an11);
//////                    tab1.setContent(sc);
//////                }
//////            });
//////
//////            delete1.setOnAction(new EventHandler<ActionEvent>() {
//////                @Override
//////                public void handle(ActionEvent event)
//////                {
//////                    AnchorPane an12=new AnchorPane();
//////                    Text t12=new Text("请输入待删除的学生信息：");
//////                    Label l21=new Label("一卡通");
//////                    TextField tf21=new TextField();
//////                    HBox hb22=new HBox();
//////                    hb22.getChildren().addAll(l21,tf21);
//////                    VBox vb2=new VBox();
//////                    Button con12=new Button("确定");
//////                    Button ret12=new Button("返回上页");
//////                    HBox hb23=new HBox();
//////                    hb23.getChildren().addAll(con12,ret12);
//////                    hb22.setSpacing(30);
//////                    hb23.setSpacing(30);
//////                    vb2.getChildren().addAll(t12,hb22,hb23);
//////                    an12.setLeftAnchor(vb2, 350.0);
//////                    an12.setTopAnchor(vb2, 20.0);
//////                    vb2.setSpacing(26);
//////                    an12.getChildren().addAll(vb2);
//////                    tab1.setContent(an12);
//////                    /////////////////////////////////添加页面的两个响应函数：
//////                    ret12.setOnAction(new EventHandler<ActionEvent>() {
//////                        @Override
//////                        public void handle(ActionEvent event)
//////                        {
//////                            tab1.setContent(an1);
//////                        }
//////                    });
//////                    con12.setOnAction(new EventHandler<ActionEvent>() {
//////                        @Override
//////                        public void handle(ActionEvent event)
//////                        {
//////                            String temp=tf21.getText();
//////                            Message mes = new Message();
//////                            mes.setMessageType("DELETE_STUDENT");
//////                            mes.setUserId(temp);
//////                            Message serverResponse = client.sendRequestToServer(mes);
//////                            tab1.setContent(an12);
//////
//////                        }
//////                    });
//////                }
//////            });
//////
//////
//////            modify1.setOnAction(new EventHandler<ActionEvent>() {
//////                @Override
//////                public void handle(ActionEvent event)
//////                {
//////                    AnchorPane an13=new AnchorPane();
//////                    Text t13=new Text("请输入待修改的学生信息：");
//////                    Label l22=new Label("一卡通");
//////                    TextField tf22=new TextField();
//////                    HBox hb24=new HBox();
//////                    hb24.getChildren().addAll(l22,tf22);
//////                    Label l23=new Label("选择修改项：");
//////                    ChoiceBox cb=new ChoiceBox();
//////                    cb.getItems().addAll("密码","学号","专业","总绩点","总学分","课外研学","惩罚情况");
//////                    cb.getSelectionModel().selectFirst();
//////                    HBox hb26=new HBox();
//////                    hb26.getChildren().addAll(l23,cb);
//////                    HBox hb27=new HBox();
//////                    Label l24=new Label("修改为：");
//////                    TextField tf23=new TextField();
//////                    hb27.getChildren().addAll(l24,tf23);
//////                    Button con13=new Button("修改");
//////                    Button ret13=new Button("返回上页");
//////                    HBox hb25=new HBox();
//////                    hb25.getChildren().addAll(con13,ret13);
//////                    hb25.setSpacing(30);
//////                    hb24.setSpacing(30);
//////                    hb26.setSpacing(15);
//////                    hb27.setSpacing(30);
//////                    VBox vb3=new VBox();
//////                    vb3.getChildren().addAll(t13,hb24,hb26,hb27,hb25);
//////                    an13.setLeftAnchor(vb3, 350.0);
//////                    an13.setTopAnchor(vb3, 20.0);
//////                    vb3.setSpacing(26);
//////                    an13.getChildren().addAll(vb3);
//////                    tab1.setContent(an13);
//////                    /////////////////////////////////添加页面的两个响应函数：
//////                    ret13.setOnAction(new EventHandler<ActionEvent>() {
//////                        @Override
//////                        public void handle(ActionEvent event)
//////                        {
//////                            tab1.setContent(an1);
//////                        }
//////                    });
//////                    con13.setOnAction(new EventHandler<ActionEvent>() {
//////                        @Override
//////                        public void handle(ActionEvent event)
//////                        {
//////                            Student temp=new Student();
//////                            Message mes = new Message();
//////                            mes.setMessageType("QUERY_STUDENT");
//////                            mes.setUserId(tf22.getText());
//////                            Message serverResponse = client.sendRequestToServer(mes);
//////                            temp=(Student) serverResponse.getData();
//////                            if(cb.getValue().equals("密码"))
//////                            {
//////                                temp.setpwd(tf23.getText());
//////                            }
//////                            if(cb.getValue().equals("学号"))
//////                            {
//////                                temp.setNumber(tf23.getText());
//////                            }
//////                            if(cb.getValue().equals("专业"))
//////                            {
//////                                temp.setMajorId(Integer.parseInt(tf23.getText()));
//////                            }
//////                            if(cb.getValue().equals("总绩点"))
//////                            {
//////                                temp.setGPA(Double.parseDouble(tf23.getText()));
//////                            }
//////                            if(cb.getValue().equals("总学分"))
//////                            {
//////                                temp.setCredit(Double.parseDouble(tf23.getText()));
//////                            }
//////                            if(cb.getValue().equals("课外研学"))
//////                            {
//////                                temp.setSTRP(Double.parseDouble(tf23.getText()));
//////                            }
//////                            if(cb.getValue().equals("惩罚情况"))
//////                            {
//////                                temp.setPunishment(Integer.parseInt(tf23.getText()));
//////                            }
//////                            mes.setMessageType("UPDATE_STUDENT");
//////                            mes.setData(temp);
//////                            serverResponse = client.sendRequestToServer(mes);
//////                            tab1.setContent(an13);
//////
//////                        }
//////                    });
//////
//////                }
//////            });
//////            an1.getChildren().addAll(tv,add1,delete1,modify1);
//////            an1.setLeftAnchor(tv,  20.0);
//////            an1.setTopAnchor(tv, 20.0);
//////            tab1.setContent(an1);
//////
//////
//////
//////
//////            /////27个hbox，24个label，23个tf
///////////////////////////////////////////////选项2页面内容：
//            AnchorPane an2=new AnchorPane();
//            Message mes2=new Message();
//            mes2.setMessageType("OUTPUT_ALL_TEACHER");
//            Message serverResponse2 = client.sendRequestToServer(mes2);
//            ArrayList<Teacher> relist2 = (ArrayList<Teacher>) serverResponse2.getData();
//            ObservableList<Teacher> list2= FXCollections.observableArrayList();
//            for(int i=0;i<relist2.size();i++)
//            {
//                list2.add(relist2.get(i));
//            }
//            TableView<Teacher>  tv2=new TableView<Teacher>(list2);
//            tv2.setPrefWidth(890);
//            tv2.setPrefHeight(450);
//            TableColumn<Teacher,String> tc_uID2=new TableColumn<Teacher,String>("一卡通");
//            tc_uID2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Teacher,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getId());
//                    return pw;
//                }
//            });
//            tv2.getColumns().add(tc_uID2);
//            TableColumn<Teacher,String> tc_upwd2=new TableColumn<Teacher,String>("密码");
//            tc_upwd2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Teacher,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getpwd());
//                    return pw;
//                }
//            });
//            tv2.getColumns().add(tc_upwd2);
//            TableColumn<Teacher,String> tc_name2=new TableColumn<Teacher,String>("姓名");
//            tc_name2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Teacher,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getName());
//                    return pw;
//                }
//            });
//            tv2.getColumns().add(tc_name2);
//            TableColumn<Teacher,Number> tc_age2=new TableColumn<Teacher,Number>("年龄");
//            tc_age2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,Number>,ObservableValue<Number>>()
//            {
//                @Override
//                public ObservableValue<Number> call(CellDataFeatures<Teacher,Number> param)
//                {
//                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getAge());
//                    return pw;
//                }
//            });
//            tv2.getColumns().add(tc_age2);
//            TableColumn<Teacher,Number> tc_sex2=new TableColumn<Teacher,Number>("性别");
//            tc_sex2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,Number>,ObservableValue<Number>>()
//            {
//                @Override
//                public ObservableValue<Number> call(CellDataFeatures<Teacher,Number> param)
//                {
//                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSex());
//                    return pw;
//                }
//            });
//            tv2.getColumns().add(tc_sex2);
//            TableColumn<Teacher,Number> tc_major2=new TableColumn<Teacher,Number>("专业");
//            tc_major2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,Number>,ObservableValue<Number>>()
//            {
//                @Override
//                public ObservableValue<Number> call(CellDataFeatures<Teacher,Number> param)
//                {
//                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getMajorId());
//                    return pw;
//                }
//            });
//            tv2.getColumns().add(tc_major2);
//            TableColumn<Teacher,Number> tc_title2=new TableColumn<Teacher,Number>("头衔");
//            tc_title2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,Number>,ObservableValue<Number>>()
//            {
//                @Override
//                public ObservableValue<Number> call(CellDataFeatures<Teacher,Number> param)
//                {
//                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getTitle());
//                    return pw;
//                }
//            });
//            tv2.getColumns().add(tc_title2);
//            TableColumn<Teacher,Number> tc_lendbook2=new TableColumn<Teacher,Number>("借书数目");
//            tc_lendbook2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,Number>,ObservableValue<Number>>()
//            {
//                @Override
//                public ObservableValue<Number> call(CellDataFeatures<Teacher,Number> param)
//                {
//                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getLendBooksNum());
//                    return pw;
//                }
//            });
//            tv2.getColumns().add(tc_lendbook2);
//            tv2.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
//            Button add2=new Button("增加记录");
//            add2.setLayoutX(938);
//            add2.setLayoutY(110);
//            Button delete2=new Button("删除记录");
//            delete2.setLayoutX(938);
//            delete2.setLayoutY(170);
//            Button modify2=new Button("修改记录");
//            modify2.setLayoutX(938);
//            modify2.setLayoutY(230);
//
//            add2.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event)
//                {
//                    AnchorPane an21=new AnchorPane();
//                    ScrollPane sc2=new ScrollPane();
//                    Text t21=new Text("请输入待增加的教师信息：");
//                    Label l25=new Label("一卡通");
//                    TextField tf24=new TextField();
//                    HBox hb28=new HBox();
//                    hb28.getChildren().addAll(l25,tf24);
//                    Label l26=new Label("密码");
//                    TextField tf25=new TextField();
//                    HBox hb29=new HBox();
//                    hb29.getChildren().addAll(l26,tf25);
//                    Label l27=new Label("姓名");
//                    TextField tf26=new TextField();
//                    HBox hb30=new HBox();
//                    hb30.getChildren().addAll(l27,tf26);
//                    Label l28=new Label("用户类型");
//                    TextField tf27=new TextField();
//                    HBox hb31=new HBox();
//                    hb31.getChildren().addAll(l28,tf27);
//                    Label l29=new Label("年龄");
//                    TextField tf28=new TextField();
//                    HBox hb32=new HBox();
//                    hb32.getChildren().addAll(l29,tf28);
//                    Label l30=new Label("性别");
//                    TextField tf29=new TextField();
//                    HBox hb33=new HBox();
//                    hb33.getChildren().addAll(l30,tf29);
//                    Label l31=new Label("专业");
//                    TextField tf30=new TextField();
//                    HBox hb34=new HBox();
//                    hb34.getChildren().addAll(l31,tf30);
//                    Label l32=new Label("头衔");
//                    TextField tf31=new TextField();
//                    HBox hb35=new HBox();
//                    hb35.getChildren().addAll(l32,tf31);
//                    Label l33=new Label("借书数目");
//                    TextField tf32=new TextField();
//                    HBox hb36=new HBox();
//                    hb36.getChildren().addAll(l33,tf32);
//
//                    hb28.setSpacing(11);
//                    hb29.setSpacing(30);
//                    hb30.setSpacing(30);
//                    hb31.setSpacing(3);
//                    hb32.setSpacing(30);
//                    hb33.setSpacing(30);
//                    hb34.setSpacing(30);
//                    hb35.setSpacing(30);
//                    hb36.setSpacing(3);
//
//                    Button con21=new Button("确定");
//                    Button ret21=new Button("返回上页");
//                    HBox hb37=new HBox();
//                    hb37.getChildren().addAll(con21,ret21);
//                    hb37.setSpacing(30);
//                    /////////////////////////////////添加页面的两个响应函数：
//                    ret21.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            tab2.setContent(an2);
//                        }
//                    });
//                    con21.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            Teacher tt=new Teacher();
//                            tt.setId(tf24.getText());
//                            tt.setpwd(tf25.getText());
//                            tt.setName(tf26.getText());
//                            tt.setUserType(Integer.parseInt(tf27.getText()));
//                            tt.setAge(Integer.parseInt(tf28.getText()));
//                            tt.setSex(Integer.parseInt(tf29.getText()));
//                            tt.setMajorId(Integer.parseInt(tf30.getText()));
//                            tt.setTitle(Integer.parseInt(tf31.getText()));
//                            tt.setLendBooksNum(Integer.parseInt(tf32.getText()));
//                            Message mes = new Message();
//                            mes.setMessageType("ADD_TEACHER");
//                            mes.setData(tt);
//                            Message serverResponse = client.sendRequestToServer(mes);
//                            tab2.setContent(sc2);
//
//                        }
//                    });
//                    VBox vb4=new VBox();
//                    vb4.getChildren().addAll(t21,hb28,hb29,hb30,hb31,hb32,hb33,hb34,hb35,hb36,hb37);
//                    vb4.setSpacing(25);
//                    an21.getChildren().add(vb4);
//                    an21.setLeftAnchor(vb4, 350.0);
//                    an21.setTopAnchor(vb4, 20.0);
//                    sc2.setContent(an21);
//                    tab2.setContent(sc2);
//                }
//            });
//
//            delete2.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event)
//                {
//                    AnchorPane an22=new AnchorPane();
//                    Text t22=new Text("请输入待删除的教师信息：");
//                    Label l34=new Label("一卡通");
//                    TextField tf33=new TextField();
//                    HBox hb38=new HBox();
//                    hb38.getChildren().addAll(l34,tf33);
//                    VBox vb5=new VBox();
//                    Button con22=new Button("确定");
//                    Button ret22=new Button("返回上页");
//                    HBox hb39=new HBox();
//                    hb39.getChildren().addAll(con22,ret22);
//                    hb38.setSpacing(30);
//                    hb39.setSpacing(30);
//                    vb5.getChildren().addAll(t22,hb38,hb39);
//                    an22.setLeftAnchor(vb5, 350.0);
//                    an22.setTopAnchor(vb5, 20.0);
//                    vb5.setSpacing(26);
//                    an22.getChildren().addAll(vb5);
//                    tab2.setContent(an22);
//                    /////////////////////////////////添加页面的两个响应函数：
//                    ret22.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            tab2.setContent(an2);
//                        }
//                    });
//                    con22.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            String temp=tf33.getText();
//                            Message mes = new Message();
//                            mes.setMessageType("DELETE_TEACHER");
//                            mes.setUserId(temp);
//                            Message serverResponse = client.sendRequestToServer(mes);
//                            tab2.setContent(an22);
//
//                        }
//                    });
//                }
//            });
//
//
//            modify2.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event)
//                {
//                    AnchorPane an23=new AnchorPane();
//                    Text t23=new Text("请输入待修改的学生信息：");
//                    Label l35=new Label("一卡通");
//                    TextField tf34=new TextField();
//                    HBox hb40=new HBox();
//                    hb40.getChildren().addAll(l35,tf34);
//                    Label l36=new Label("选择修改项：");
//                    ChoiceBox cb2=new ChoiceBox();
//                    cb2.getItems().addAll("密码","姓名","年龄","专业","借书数目","头衔");
//                    cb2.getSelectionModel().selectFirst();
//                    HBox hb41=new HBox();
//                    hb41.getChildren().addAll(l36,cb2);
//                    HBox hb42=new HBox();
//                    Label l37=new Label("修改为：");
//                    TextField tf35=new TextField();
//                    hb42.getChildren().addAll(l37,tf35);
//                    Button con23=new Button("修改");
//                    Button ret23=new Button("返回上页");
//                    HBox hb43=new HBox();
//                    hb43.getChildren().addAll(con23,ret23);
//                    hb40.setSpacing(30);
//                    hb41.setSpacing(30);
//                    hb42.setSpacing(15);
//                    hb43.setSpacing(30);
//                    VBox vb6=new VBox();
//                    vb6.getChildren().addAll(t23,hb40,hb41,hb42,hb43);
//                    an23.setLeftAnchor(vb6, 350.0);
//                    an23.setTopAnchor(vb6, 20.0);
//                    vb6.setSpacing(26);
//                    an23.getChildren().addAll(vb6);
//                    tab2.setContent(an23);
//                    /////////////////////////////////添加页面的两个响应函数：
//                    ret23.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            tab2.setContent(an2);
//                        }
//                    });
//                    con23.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            Teacher temp=new Teacher();
//                            Message mes = new Message();
//                            mes.setMessageType("QUERY_TEACHER");
//                            mes.setUserId(tf34.getText());
//                            Message serverResponse = client.sendRequestToServer(mes);
//                            temp=(Teacher) serverResponse.getData();
//                            if(cb2.getValue().equals("密码"))
//                            {
//                                temp.setpwd(tf35.getText());
//                            }
//                            if(cb2.getValue().equals("姓名"))
//                            {
//                                temp.setName(tf35.getText());
//                            }
//                            if(cb2.getValue().equals("专业"))
//                            {
//                                temp.setMajorId(Integer.parseInt(tf35.getText()));
//                            }
//                            if(cb2.getValue().equals("年龄"))
//                            {
//                                temp.setAge(Integer.parseInt(tf35.getText()));;
//                            }
//                            if(cb2.getValue().equals("借书数目"))
//                            {
//                                temp.setLendBooksNum(Integer.parseInt(tf35.getText()));
//                            }
//                            if(cb2.getValue().equals("头衔"))
//                            {
//                                temp.setTitle(Integer.parseInt(tf35.getText()));
//                            }
//
//                            mes.setMessageType("UPDATE_TEACHER");
//                            mes.setData(temp);
//                            serverResponse = client.sendRequestToServer(mes);
//                            tab2.setContent(an23);
//
//                        }
//                    });
//
//                }
//            });
//
//
//            an2.getChildren().addAll(tv2,add2,delete2,modify2);
//            an2.setLeftAnchor(tv2,  20.0);
//            an2.setTopAnchor(tv2, 20.0);
//            tab2.setContent(an2);
//////
//////
//////
//////
//////
//////
//////
//////
//////
//////            ///////////截至目前，43hbox，37label，35tf；
//////////////////选项3页面内容：
//////
//            AnchorPane an3=new AnchorPane();
//            Message mes3=new Message();
//            mes3.setMessageType("OUTPUT_ALL_RETAILER");
//            Message serverResponse3 = client.sendRequestToServer(mes3);
//            ArrayList<Retailer> relist3 = (ArrayList<Retailer>) serverResponse3.getData();
//            ObservableList<Retailer> list3= FXCollections.observableArrayList();
//            for(int i=0;i<relist3.size();i++)
//            {
//                list3.add(relist3.get(i));
//            }
//            TableView<Retailer>  tv3=new TableView<Retailer>(list3);
//            tv3.setPrefWidth(890);
//            tv3.setPrefHeight(450);
//            TableColumn<Retailer,String> tc_uID3=new TableColumn<Retailer,String>("一卡通");
//            tc_uID3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Retailer,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getId());
//                    return pw;
//                }
//            });
//            tv3.getColumns().add(tc_uID3);
//            TableColumn<Retailer,String> tc_upwd3=new TableColumn<Retailer,String>("密码");
//            tc_upwd3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Retailer,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getpwd());
//                    return pw;
//                }
//            });
//            tv3.getColumns().add(tc_upwd3);
//            TableColumn<Retailer,String> tc_name3=new TableColumn<Retailer,String>("姓名");
//            tc_name3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Retailer,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getName());
//                    return pw;
//                }
//            });
//            tv3.getColumns().add(tc_name3);
//            TableColumn<Retailer,Number> tc_age3=new TableColumn<Retailer,Number>("年龄");
//            tc_age3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,Number>,ObservableValue<Number>>()
//            {
//                @Override
//                public ObservableValue<Number> call(CellDataFeatures<Retailer,Number> param)
//                {
//                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getAge());
//                    return pw;
//                }
//            });
//            tv3.getColumns().add(tc_age3);
//            TableColumn<Retailer,Number> tc_sex3=new TableColumn<Retailer,Number>("性别");
//            tc_sex3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,Number>,ObservableValue<Number>>()
//            {
//                @Override
//                public ObservableValue<Number> call(CellDataFeatures<Retailer,Number> param)
//                {
//                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSex());
//                    return pw;
//                }
//            });
//            tv3.getColumns().add(tc_sex3);
//            TableColumn<Retailer,String> tc_renown3=new TableColumn<Retailer,String>("声誉");
//            tc_renown3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Retailer,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getRenown());
//                    return pw;
//                }
//            });
//            tv3.getColumns().add(tc_renown3);
//            TableColumn<Retailer,String> tc_shopname=new TableColumn<Retailer,String>("商店名称");
//            tc_shopname.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Retailer,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getShopName());
//                    return pw;
//                }
//            });
//            tv3.getColumns().add(tc_shopname);
//            TableColumn<Retailer,String> tc_position=new TableColumn<Retailer,String>("商店位置");
//            tc_position.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Retailer,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getPosition());
//                    return pw;
//                }
//            });
//            tv3.getColumns().add(tc_position);
//            tv3.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
//            Button add3=new Button("增加记录");
//            add3.setLayoutX(938);
//            add3.setLayoutY(110);
//            Button delete3=new Button("删除记录");
//            delete3.setLayoutX(938);
//            delete3.setLayoutY(170);
//            Button modify3=new Button("修改记录");
//            modify3.setLayoutX(938);
//            modify3.setLayoutY(230);
//
//            add3.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event)
//                {
//                    AnchorPane an31=new AnchorPane();
//                    ScrollPane sc3=new ScrollPane();
//                    Text t31=new Text("请输入待增加的商户信息：");
//                    Label l38=new Label("一卡通");
//                    TextField tf36=new TextField();
//                    HBox hb44=new HBox();
//                    hb44.getChildren().addAll(l38,tf36);
//                    Label l39=new Label("密码");
//                    TextField tf37=new TextField();
//                    HBox hb45=new HBox();
//                    hb45.getChildren().addAll(l39,tf37);
//                    Label l40=new Label("姓名");
//                    TextField tf38=new TextField();
//                    HBox hb46=new HBox();
//                    hb46.getChildren().addAll(l40,tf38);
//                    Label l41=new Label("用户类型");
//                    TextField tf39=new TextField();
//                    HBox hb47=new HBox();
//                    hb47.getChildren().addAll(l41,tf39);
//                    Label l42=new Label("职业类型");
//                    TextField tf40=new TextField();
//                    HBox hb48=new HBox();
//                    hb48.getChildren().addAll(l42,tf40);
//                    Label l43=new Label("年龄");
//                    TextField tf41=new TextField();
//                    HBox hb49=new HBox();
//                    hb49.getChildren().addAll(l43,tf41);
//                    Label l44=new Label("性别");
//                    TextField tf42=new TextField();
//                    HBox hb50=new HBox();
//                    hb50.getChildren().addAll(l44,tf42);
//                    Label l45=new Label("声誉");
//                    TextField tf43=new TextField();
//                    HBox hb51=new HBox();
//                    hb51.getChildren().addAll(l45,tf43);
//                    Label l46=new Label("商店名称");
//                    TextField tf44=new TextField();
//                    HBox hb52=new HBox();
//                    hb52.getChildren().addAll(l46,tf44);
//                    Label l47=new Label("商店位置");
//                    TextField tf45=new TextField();
//                    HBox hb53=new HBox();
//                    hb53.getChildren().addAll(l47,tf45);
//                    hb44.setSpacing(11);
//                    hb45.setSpacing(30);
//                    hb46.setSpacing(30);
//                    hb47.setSpacing(30);
//                    hb48.setSpacing(3);
//                    hb49.setSpacing(30);
//                    hb50.setSpacing(30);
//                    hb51.setSpacing(30);
//                    hb52.setSpacing(3);
//                    hb53.setSpacing(3);
//                    Button con31=new Button("确定");
//                    Button ret31=new Button("返回上页");
//                    HBox hb54=new HBox();
//                    hb54.getChildren().addAll(con31,ret31);
//                    hb54.setSpacing(30);
//                    /////////////////////////////////添加页面的两个响应函数：
//                    ret31.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            tab3.setContent(an3);
//                        }
//                    });
//                    con31.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            Retailer tt=new Retailer();
//                            tt.setId(tf36.getText());
//                            tt.setpwd(tf37.getText());
//                            tt.setName(tf38.getText());
//                            tt.setUserType(Integer.parseInt(tf39.getText()));
//                            tt.setJobType(Integer.parseInt(tf40.getText()));
//                            tt.setAge(Integer.parseInt(tf41.getText()));
//                            tt.setSex(Integer.parseInt(tf42.getText()));
//                            tt.setRenown(tf43.getText());
//                            tt.setShopName(tf44.getText());
//                            tt.setPosition(tf45.getText());
//                            Message mes = new Message();
//                            mes.setMessageType("ADD_RETAILER");
//                            mes.setData(tt);
//                            Message serverResponse = client.sendRequestToServer(mes);
//                            tab3.setContent(sc3);
//
//                        }
//                    });
//                    VBox vb7=new VBox();
//                    vb7.getChildren().addAll(t31,hb44,hb45,hb46,hb47,hb48,hb49,hb50,hb51,hb52,hb53,hb54);
//                    vb7.setSpacing(25);
//                    an31.getChildren().add(vb7);
//                    an31.setLeftAnchor(vb7, 350.0);
//                    an31.setTopAnchor(vb7, 20.0);
//                    sc3.setContent(an31);
//                    tab3.setContent(sc3);
//                }
//            });
//
//
//            delete3.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event)
//                {
//                    AnchorPane an32=new AnchorPane();
//                    Text t32=new Text("请输入待删除的商户信息：");
//                    Label l48=new Label("一卡通");
//                    TextField tf46=new TextField();
//                    HBox hb55=new HBox();
//                    hb55.getChildren().addAll(l48,tf46);
//                    VBox vb8=new VBox();
//                    Button con32=new Button("确定");
//                    Button ret32=new Button("返回上页");
//                    HBox hb56=new HBox();
//                    hb56.getChildren().addAll(con32,ret32);
//                    hb55.setSpacing(30);
//                    hb56.setSpacing(30);
//                    vb8.getChildren().addAll(t32,hb55,hb56);
//                    an32.setLeftAnchor(vb8, 350.0);
//                    an32.setTopAnchor(vb8, 20.0);
//                    vb8.setSpacing(26);
//                    an32.getChildren().addAll(vb8);
//                    tab3.setContent(an32);
//                    /////////////////////////////////添加页面的两个响应函数：
//                    ret32.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            tab3.setContent(an3);
//                        }
//                    });
//                    con32.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            String temp=tf46.getText();
//                            Message mes = new Message();
//                            mes.setMessageType("DELETE_RETAILER");
//                            mes.setUserId(temp);
//                            Message serverResponse = client.sendRequestToServer(mes);
//                            tab3.setContent(an32);
//
//                        }
//                    });
//                }
//            });
//
//
//            modify3.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event)
//                {
//                    AnchorPane an33=new AnchorPane();
//                    Text t33=new Text("请输入待修改的商户信息：");
//                    Label l49=new Label("一卡通");
//                    TextField tf47=new TextField();
//                    HBox hb57=new HBox();
//                    hb57.getChildren().addAll(l49,tf47);
//                    Label l50=new Label("选择修改项：");
//                    ChoiceBox cb3=new ChoiceBox();
//                    cb3.getItems().addAll("密码","姓名","年龄","声誉","商店名称","商店位置");
//                    cb3.getSelectionModel().selectFirst();
//                    HBox hb58=new HBox();
//                    hb58.getChildren().addAll(l50,cb3);
//                    HBox hb59=new HBox();
//                    Label l51=new Label("修改为：");
//                    TextField tf48=new TextField();
//                    hb59.getChildren().addAll(l51,tf48);
//                    Button con33=new Button("修改");
//                    Button ret33=new Button("返回上页");
//                    HBox hb60=new HBox();
//                    hb60.getChildren().addAll(con33,ret33);
//                    hb57.setSpacing(30);
//                    hb58.setSpacing(30);
//                    hb59.setSpacing(15);
//                    hb60.setSpacing(30);
//                    VBox vb9=new VBox();
//                    vb9.getChildren().addAll(t33,hb57,hb58,hb59,hb60);
//                    an33.setLeftAnchor(vb9, 350.0);
//                    an33.setTopAnchor(vb9, 20.0);
//                    vb9.setSpacing(26);
//                    an33.getChildren().addAll(vb9);
//                    tab3.setContent(an33);
//                    /////////////////////////////////添加页面的两个响应函数：
//                    ret33.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            tab3.setContent(an3);
//                        }
//                    });
//                    con33.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            Retailer temp=new Retailer();
//                            Message mes = new Message();
//                            mes.setMessageType("QUERY_RETAILER");
//                            mes.setUserId(tf47.getText());
//                            Message serverResponse = client.sendRequestToServer(mes);
//                            temp=(Retailer) serverResponse.getData();
//                            if(cb3.getValue().equals("密码"))
//                            {
//                                temp.setpwd(tf48.getText());
//                            }
//                            if(cb3.getValue().equals("姓名"))
//                            {
//                                temp.setName(tf48.getText());
//                            }
//                            if(cb3.getValue().equals("年龄"))
//                            {
//                                temp.setAge(Integer.parseInt(tf48.getText()));
//                            }
//                            if(cb3.getValue().equals("声誉"))
//                            {
//                                temp.setRenown(tf48.getText());
//                            }
//                            if(cb3.getValue().equals("商店名称"))
//                            {
//                                temp.setShopName(tf48.getText());
//                            }
//                            if(cb3.getValue().equals("商店位置"))
//                            {
//                                temp.setPosition(tf48.getText());
//                            }
//
//                            mes.setMessageType("UPDATE_RETAILER");
//                            mes.setData(temp);
//                            serverResponse = client.sendRequestToServer(mes);
//                            tab3.setContent(an33);
//
//                        }
//                    });
//
//                }
//            });
//
//
//            an3.getChildren().addAll(tv3,add3,delete3,modify3);
//            an3.setLeftAnchor(tv3,  20.0);
//            an3.setTopAnchor(tv3, 20.0);
//            tab3.setContent(an3);
//////
////
////
////
////
////
////
////
////
////            /////////截至目前，60个hbox，51个label，48个tf；
///////////////////////////////////////////////////////选项4页面内容：
////
//            Client client12 = new Client();
//            AnchorPane an4=new AnchorPane();
//            Message mes4=new Message();
//            mes4.setMessageType(MessageType.outputAllDoctor);
//            Message serverResponse4 = client12.sendRequestToServer(mes4);
//            ArrayList<Doctor> relist4 = (ArrayList<Doctor>) serverResponse4.getData();
//            ObservableList<Doctor> list4= FXCollections.observableArrayList();
//            for(int i=0;i<relist4.size();i++)
//            {
//                list4.add(relist4.get(i));
//            }
//            TableView<Doctor>  tv4=new TableView<Doctor>(list4);
//            tv4.setPrefWidth(890);
//            tv4.setPrefHeight(450);
//            TableColumn<Doctor,String> tc_uID4=new TableColumn<Doctor,String>("一卡通");
//            tc_uID4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Doctor,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getPosition());
//                    return pw;
//                }
//            });
//            tv4.getColumns().add(tc_uID4);
//            TableColumn<Doctor,String> tc_upwd4=new TableColumn<Doctor,String>("密码");
//            tc_upwd4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Doctor,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getpwd());
//                    return pw;
//                }
//            });
//            tv4.getColumns().add(tc_upwd4);
//            TableColumn<Doctor,String> tc_name4=new TableColumn<Doctor,String>("姓名");
//            tc_name4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Doctor,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getName());
//                    return pw;
//                }
//            });
//            tv4.getColumns().add(tc_name4);
//            TableColumn<Doctor,Number> tc_age4=new TableColumn<Doctor,Number>("年龄");
//            tc_age4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,Number>,ObservableValue<Number>>()
//            {
//                @Override
//                public ObservableValue<Number> call(CellDataFeatures<Doctor,Number> param)
//                {
//                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getAge());
//                    return pw;
//                }
//            });
//            tv4.getColumns().add(tc_age4);
//            TableColumn<Doctor,Number> tc_sex4=new TableColumn<Doctor,Number>("性别");
//            tc_sex4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,Number>,ObservableValue<Number>>()
//            {
//                @Override
//                public ObservableValue<Number> call(CellDataFeatures<Doctor,Number> param)
//                {
//                    SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSex());
//                    return pw;
//                }
//            });
//            tv4.getColumns().add(tc_sex4);
//            TableColumn<Doctor,String> tc_renown4=new TableColumn<Doctor,String>("声誉");
//            tc_renown4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Doctor,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getRenown());
//                    return pw;
//                }
//            });
//            tv4.getColumns().add(tc_renown4);
//            TableColumn<Doctor,String> tc_dept=new TableColumn<Doctor,String>("科室");
//            tc_dept.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Doctor,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getDept());
//                    return pw;
//                }
//            });
//            tv4.getColumns().add(tc_dept);
//            TableColumn<Doctor,String> tc_position4=new TableColumn<Doctor,String>("门诊位置");
//            tc_position4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,String>,ObservableValue<String>>()
//            {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<Doctor,String> param)
//                {
//                    SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getPosition());
//                    return pw;
//                }
//            });
//            tv4.getColumns().add(tc_position4);
//            tv4.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
//            Button add4=new Button("增加记录");
//            add4.setLayoutX(938);
//            add4.setLayoutY(110);
//            Button delete4=new Button("删除记录");
//            delete4.setLayoutX(938);
//            delete4.setLayoutY(170);
//            Button modify4=new Button("修改记录");
//            modify4.setLayoutX(938);
//            modify4.setLayoutY(230);
//
//            add4.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event)
//                {
//                    AnchorPane an41=new AnchorPane();
//                    ScrollPane sc4=new ScrollPane();
//                    Text t41=new Text("请输入待增加的医生信息：");
//                    Label l52=new Label("一卡通");
//                    TextField tf49=new TextField();
//                    HBox hb61=new HBox();
//                    hb61.getChildren().addAll(l52,tf49);
//                    Label l53=new Label("密码");
//                    TextField tf50=new TextField();
//                    HBox hb62=new HBox();
//                    hb62.getChildren().addAll(l53,tf50);
//                    Label l54=new Label("姓名");
//                    TextField tf51=new TextField();
//                    HBox hb63=new HBox();
//                    hb63.getChildren().addAll(l54,tf51);
//                    Label l55=new Label("用户类型");
//                    TextField tf52=new TextField();
//                    HBox hb64=new HBox();
//                    hb64.getChildren().addAll(l55,tf52);
//                    Label l56=new Label("职业类型");
//                    TextField tf53=new TextField();
//                    HBox hb65=new HBox();
//                    hb65.getChildren().addAll(l56,tf53);
//                    Label l57=new Label("年龄");
//                    TextField tf54=new TextField();
//                    HBox hb66=new HBox();
//                    hb66.getChildren().addAll(l57,tf54);
//                    Label l58=new Label("性别");
//                    TextField tf55=new TextField();
//                    HBox hb67=new HBox();
//                    hb67.getChildren().addAll(l58,tf55);
//                    Label l59=new Label("声誉");
//                    TextField tf56=new TextField();
//                    HBox hb68=new HBox();
//                    hb68.getChildren().addAll(l59,tf56);
//                    Label l60=new Label("科室");
//                    TextField tf57=new TextField();
//                    HBox hb69=new HBox();
//                    hb69.getChildren().addAll(l60,tf57);
//                    Label l61=new Label("门诊位置");
//                    TextField tf58=new TextField();
//                    HBox hb74=new HBox();
//                    hb74.getChildren().addAll(l61,tf58);
//                    HBox hb70=new HBox();
//                    hb70.getChildren().addAll(l60,tf57);
//                    hb61.setSpacing(11);
//                    hb62.setSpacing(30);
//                    hb63.setSpacing(30);
//                    hb64.setSpacing(3);
//                    hb65.setSpacing(3);
//                    hb66.setSpacing(30);
//                    hb67.setSpacing(30);
//                    hb68.setSpacing(30);
//                    hb69.setSpacing(30);
//                    hb70.setSpacing(30);
//                    hb74.setSpacing(3);
//                    Button con41=new Button("确定");
//                    Button ret41=new Button("返回上页");
//                    HBox hb71=new HBox();
//                    hb71.getChildren().addAll(con41,ret41);
//                    hb71.setSpacing(30);
//                    /////////////////////////////////添加页面的两个响应函数：
//                    ret41.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            tab4.setContent(an4);
//                        }
//                    });
//                    con41.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            Doctor tt=new Doctor();
//                            tt.setId(tf49.getText());
//                            tt.setpwd(tf50.getText());
//                            tt.setName(tf51.getText());
//                            tt.setUserType(Integer.parseInt(tf52.getText()));
//                            tt.setJobType(Integer.parseInt(tf53.getText()));
//                            tt.setAge(Integer.parseInt(tf54.getText()));
//                            tt.setSex(Integer.parseInt(tf55.getText()));
//                            tt.setRenown(tf56.getText());
//                            tt.setDept(tf57.getText());
//                            tt.setPosition(tf58.getText());
//                            Message mes = new Message();
//                            mes.setMessageType(MessageType.addDoctor);
//                            mes.setData(tt);
//                            Message serverResponse = client.sendRequestToServer(mes);
//                            tab4.setContent(sc4);
//
//                        }
//                    });
//                    VBox vb10=new VBox();
//                    vb10.getChildren().addAll(t41,hb61,hb62,hb63,hb64,hb65,hb66,hb67,hb68,hb69,hb70,hb74,hb71);
//                    vb10.setSpacing(25);
//                    an41.getChildren().add(vb10);
//                    an41.setLeftAnchor(vb10, 350.0);
//                    an41.setTopAnchor(vb10, 20.0);
//                    sc4.setContent(an41);
//                    tab4.setContent(sc4);
//                }
//            });
//
//
//            delete4.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event)
//                {
//                    AnchorPane an42=new AnchorPane();
//                    Text t42=new Text("请输入待删除的医生信息：");
//                    Label l62=new Label("一卡通");
//                    TextField tf59=new TextField();
//                    HBox hb72=new HBox();
//                    hb72.getChildren().addAll(l62,tf59);
//                    VBox vb11=new VBox();
//                    Button con42=new Button("确定");
//                    Button ret42=new Button("返回上页");
//                    HBox hb73=new HBox();
//                    hb73.getChildren().addAll(con42,ret42);
//                    hb72.setSpacing(30);
//                    hb73.setSpacing(30);
//                    vb11.getChildren().addAll(t42,hb72,hb73);
//                    an42.setLeftAnchor(vb11, 350.0);
//                    an42.setTopAnchor(vb11, 20.0);
//                    vb11.setSpacing(26);
//                    an42.getChildren().addAll(vb11);
//                    tab4.setContent(an42);
//                    /////////////////////////////////添加页面的两个响应函数：
//                    ret42.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            tab4.setContent(an4);
//                        }
//                    });
//                    con42.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            String temp=tf59.getText();
//                            Message mes = new Message();
//                            mes.setMessageType("DELETE_DOCTOR");
//                            mes.setUserId(temp);
//                            Message serverResponse = client.sendRequestToServer(mes);
//                            tab4.setContent(an42);
//
//                        }
//                    });
//                }
//            });
//
//
//            modify4.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event)
//                {
//                    AnchorPane an43=new AnchorPane();
//                    Text t43=new Text("请输入待修改的医生信息：");
//                    Label l63=new Label("一卡通");
//                    TextField tf60=new TextField();
//                    HBox hb75=new HBox();
//                    hb75.getChildren().addAll(l63,tf60);
//                    Label l64=new Label("选择修改项：");
//                    ChoiceBox cb4=new ChoiceBox();
//                    cb4.getItems().addAll("密码","姓名","年龄","声誉","科室");
//                    cb4.getSelectionModel().selectFirst();
//                    HBox hb76=new HBox();
//                    hb76.getChildren().addAll(l64,cb4);
//                    HBox hb77=new HBox();
//                    Label l65=new Label("修改为：");
//                    TextField tf61=new TextField();
//                    hb77.getChildren().addAll(l65,tf61);
//                    Button con43=new Button("修改");
//                    Button ret43=new Button("返回上页");
//                    HBox hb78=new HBox();
//                    hb78.getChildren().addAll(con43,ret43);
//                    hb75.setSpacing(30);
//                    hb76.setSpacing(30);
//                    hb77.setSpacing(15);
//                    hb78.setSpacing(30);
//                    VBox vb12=new VBox();
//                    vb12.getChildren().addAll(t43,hb75,hb76,hb77,hb78);
//                    an43.setLeftAnchor(vb12, 350.0);
//                    an43.setTopAnchor(vb12, 20.0);
//                    vb12.setSpacing(26);
//                    an43.getChildren().addAll(vb12);
//                    tab4.setContent(an43);
//                    /////////////////////////////////添加页面的两个响应函数：
//                    ret43.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            tab4.setContent(an4);
//                        }
//                    });
//                    con43.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event)
//                        {
//                            Doctor temp=new Doctor();
//                            Message mes = new Message();
//                            mes.setMessageType("QUERY_DOCTOR");
//                            mes.setUserId(tf60.getText());
//                            Message serverResponse = client.sendRequestToServer(mes);
//                            temp=(Doctor) serverResponse.getData();
//                            if(cb4.getValue().equals("密码"))
//                            {
//                                temp.setpwd(tf61.getText());
//                            }
//                            if(cb4.getValue().equals("姓名"))
//                            {
//                                temp.setName(tf61.getText());
//                            }
//                            if(cb4.getValue().equals("年龄"))
//                            {
//                                temp.setAge(Integer.parseInt(tf61.getText()));
//                            }
//                            if(cb4.getValue().equals("声誉"))
//                            {
//                                temp.setRenown(tf61.getText());
//                            }
//                            if(cb4.getValue().equals("科室"))
//                            {
//                                temp.setDept(tf61.getText());
//                            }
//
//                            mes.setMessageType("UPDATE_DOCTOR");
//                            mes.setData(temp);
//                            serverResponse = client.sendRequestToServer(mes);
//                            tab4.setContent(an43);
//
//                        }
//                    });
//
//                }
//            });
//
//            an4.getChildren().addAll(tv4,add4,delete4,modify4);
//            an4.setLeftAnchor(tv4,  20.0);
//            an4.setTopAnchor(tv4, 20.0);
//            tab4.setContent(an4);
////
////
////
////
////
////
////
////            /////////////////////////////////////展示操作
////            primaryStage.setScene(scene);
////            primaryStage.show();
////        } catch(Exception e) {
////            e.printStackTrace();
////        }
////    }
////    ///////////////////主方法
////    public static void main(String[] args)
////    {
////        launch(args);
////    }
////
////}
//
//
////
////import vo.ShopRecord;
////
////import java.lang.reflect.Array;
////import java.text.DecimalFormat;
////import java.util.ArrayList;
////import java.util.Scanner;
////public class Test {
////    public static void main(String[] args) {
////        // TODO Auto-generated method stub
////        String s = "#004D40";//十六进制
//////        int ans = (int) HexToDec(s) + 19000;//十进制
//////        String ans_hex = DecToHex(ans);//十六进制
//////        System.out.println(ans_hex);
////        String ans = barColor(s);
////        System.out.println(ans);
////    }
////
////    private static String DecToHex(int n) {
////        //StringBuffer s = new StringBuffer();
////        StringBuilder sb = new StringBuilder(8);
////        String a;
////        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
////        while(n != 0){
////            sb = sb.append(b[n%16]);
////            n = n/16;
////        }
////        a = sb.reverse().toString();
////        return a;
////    }
//////
//////    private static double HexToDec(String string){
//////        double sum=0;
//////        for(int i=0;i<string.length();i++)
//////        {
//////            int m=string.charAt(i);//将输入的十六进制字符串转化为单个字符
//////            int num=m>='A'?m-'A'+10:m-'0';//将字符对应的ASCII值转为数值
//////            sum+=Math.pow(16, string.length()-1-i)*num;
//////        }
//////        System.out.println(sum);
//////        return sum;
//////    }
//////
////
////    private static String barColor(String str){//输入16进制颜色码
////        String str2 = str.substring(1,3);
////        String str3 = str.substring(3,5);
////        String str4 = str.substring(5,7);
////        int red = Integer.parseInt(str2,16);
////        int green = Integer.parseInt(str3,16);
////        int blue = Integer.parseInt(str4,16);
//////        ArrayList<Integer> RGB = new ArrayList<Integer>();
//////        RGB.add(red);
//////        RGB.add(green);
//////        RGB.add(blue);
////
////        String ans = "#";
////        if(red != 0) red = red + 30;
////        if(green != 0) green = green + 30;
////        if(blue != 0) blue = blue + 30;
////
////        String r,g,b;
////        if(red == 0) r="00";
////        else if(red > 255) r="FF";
////        else r=String.valueOf(DecToHex(red));
////        if(green == 0) g="00";
////        else if(green > 255) g="FF";
////        else g=String.valueOf(DecToHex(green));
////        if(blue == 0) b="00";
////        else if(blue > 255) b="FF";
////        else b=String.valueOf(DecToHex(blue));
////        ans = ans + r + g + b;
////
////        return ans;
////    }
////}
////
////
