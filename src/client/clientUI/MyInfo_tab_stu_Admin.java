package client.clientUI;

import client.socket.Client;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import vo.Message;
import vo.Student;

import java.util.ArrayList;

public class MyInfo_tab_stu_Admin {

    public Tab MyInfo_tab_stu(){
        Tab tab1 = new Tab("学生信息");
        Client client = new Client();
        AnchorPane an1=new AnchorPane();
        Message mes1 = new Message();
        mes1.setMessageType("OUTPUT_ALL_STUDENT");
        Message serverResponse1 = client.sendRequestToServer(mes1);
        ArrayList<Student> relist1= (ArrayList<Student>) serverResponse1.getData();
        ObservableList<Student> list1= FXCollections.observableArrayList();
        for(int i=0;i<relist1.size();i++)
        {
            list1.add(relist1.get(i));
        }
        TableView<Student>  tv=new TableView<Student>(list1);
        tv.setPrefWidth(890);
        tv.setPrefHeight(450);
        TableColumn<Student,String> tc_uID=new TableColumn<Student,String>("一卡通");
        tc_uID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
            {
                SimpleStringProperty id=new SimpleStringProperty(param.getValue().getId());
                return id;
            }
        });
        tv.getColumns().add(tc_uID);
        TableColumn<Student,String> tc_upwd=new TableColumn<Student,String>("密码");
        tc_upwd.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getpwd());
                return pw;
            }
        });
        tv.getColumns().add(tc_upwd);
        TableColumn<Student,String> tc_name=new TableColumn<Student,String>("姓名");
        tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getName());
                return pw;
            }
        });
        tv.getColumns().add(tc_name);
        TableColumn<Student,String> tc_number=new TableColumn<Student,String>("学号");
        tc_number.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getNumber());
                return pw;
            }
        });
        tv.getColumns().add(tc_number);
        TableColumn<Student,Number> tc_major=new TableColumn<Student,Number>("专业");
        tc_major.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getMajorId());
                return pw;
            }
        });
        tv.getColumns().add(tc_major);
        TableColumn<Student,Number> tc_GPA=new TableColumn<Student,Number>("绩点");
        tc_GPA.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getGPA());
                return pw;
            }
        });
        tv.getColumns().add(tc_GPA);
        TableColumn<Student,Number> tc_credit=new TableColumn<Student,Number>("学分");
        tc_credit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getCredit());
                return pw;
            }
        });
        tv.getColumns().add(tc_credit);
        TableColumn<Student,Number> tc_srtp=new TableColumn<Student,Number>("研学");
        tc_srtp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSTRP());
                return pw;
            }
        });
        tv.getColumns().add(tc_srtp);
        TableColumn<Student,Number> tc_state=new TableColumn<Student,Number>("状态");
        tc_state.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getState());
                return pw;
            }
        });
        tv.getColumns().add(tc_state);
        TableColumn<Student,Number> tc_age=new TableColumn<Student,Number>("年龄");
        tc_age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getAge());
                return pw;
            }
        });
        tv.getColumns().add(tc_age);
        TableColumn<Student,Number> tc_sex=new TableColumn<Student,Number>("性别");
        tc_sex.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSex());
                return pw;
            }
        });
        tv.getColumns().add(tc_sex);
        TableColumn<Student,String> tc_register=new TableColumn<Student,String>("注册时间");
        tc_register.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getRegister());
                return pw;
            }
        });
        tv.getColumns().add(tc_register);
        TableColumn<Student,Number> tc_money=new TableColumn<Student,Number>("余额宝");
        tc_money.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getCardBalance());
                return pw;
            }
        });
        tv.getColumns().add(tc_money);
        TableColumn<Student,String> tc_renown=new TableColumn<Student,String>("声誉");
        tc_renown.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getRenown());
                return pw;
            }
        });
        tv.getColumns().add(tc_renown);
        TableColumn<Student,Number> tc_campusposition=new TableColumn<Student,Number>("校区");
        tc_campusposition.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getCampusPosition());
                return pw;
            }
        });
        tv.getColumns().add(tc_campusposition);
        TableColumn<Student,Number> tc_punishment=new TableColumn<Student,Number>("惩罚");
        tc_punishment.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getPunishment());
                return pw;
            }
        });
        tv.getColumns().add(tc_punishment);
        tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        Button add1=new Button("增加记录");
        add1.setLayoutX(938);
        add1.setLayoutY(110);
        Button delete1=new Button("删除记录");
        delete1.setLayoutX(938);
        delete1.setLayoutY(170);
        Button modify1=new Button("修改记录");
        modify1.setLayoutX(938);
        modify1.setLayoutY(230);

        add1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                AnchorPane an11=new AnchorPane();
                ScrollPane sc=new ScrollPane();
                Text t11=new Text("请输入待增加的学生信息：");
                Label l1=new Label("一卡通");
                TextField tf1=new TextField();
                HBox hb1=new HBox();
                hb1.getChildren().addAll(l1,tf1);
                Label l2=new Label("密码");
                TextField tf2=new TextField();
                HBox hb2=new HBox();
                hb2.getChildren().addAll(l2,tf2);
                Label l3=new Label("姓名");
                TextField tf3=new TextField();
                HBox hb3=new HBox();
                hb3.getChildren().addAll(l3,tf3);
                Label l4=new Label("用户类型");
                TextField tf4=new TextField();
                HBox hb4=new HBox();
                hb4.getChildren().addAll(l4,tf4);
                Label l5=new Label("学号");
                TextField tf5=new TextField();
                HBox hb5=new HBox();
                hb5.getChildren().addAll(l5,tf5);
                Label l6=new Label("专业");
                TextField tf6=new TextField();
                HBox hb6=new HBox();
                hb6.getChildren().addAll(l6,tf6);
                Label l7=new Label("绩点");
                TextField tf7=new TextField();
                HBox hb7=new HBox();
                hb7.getChildren().addAll(l7,tf7);
                Label l8=new Label("学分");
                TextField tf8=new TextField();
                HBox hb8=new HBox();
                hb8.getChildren().addAll(l8,tf8);
                Label l9=new Label("研学");
                TextField tf9=new TextField();
                HBox hb9=new HBox();
                hb9.getChildren().addAll(l9,tf9);
                Label l10=new Label("状态");
                TextField tf10=new TextField();
                HBox hb10=new HBox();
                hb10.getChildren().addAll(l10,tf10);
                Label l11=new Label("电话");
                TextField tf11=new TextField();
                HBox hb11=new HBox();
                hb11.getChildren().addAll(l11,tf11);
                Label l12=new Label("年龄");
                TextField tf12=new TextField();
                HBox hb12=new HBox();
                hb12.getChildren().addAll(l12,tf12);
                Label l13=new Label("性别");
                TextField tf13=new TextField();
                HBox hb13=new HBox();
                hb13.getChildren().addAll(l13,tf13);
                Label l14=new Label("注册时间");
                TextField tf14=new TextField();
                HBox hb14=new HBox();
                hb14.getChildren().addAll(l14,tf14);
                Label l15=new Label("宿舍");
                TextField tf15=new TextField();
                HBox hb15=new HBox();
                hb15.getChildren().addAll(l15,tf15);
                Label l16=new Label("余额宝");
                TextField tf16=new TextField();
                HBox hb16=new HBox();
                hb16.getChildren().addAll(l16,tf16);
                Label l17=new Label("借书数目");
                TextField tf17=new TextField();
                HBox hb17=new HBox();
                hb17.getChildren().addAll(l17,tf17);
                Label l18=new Label("声誉");
                TextField tf18=new TextField();
                HBox hb18=new HBox();
                hb18.getChildren().addAll(l18,tf18);
                Label l19=new Label("校区");
                TextField tf19=new TextField();
                HBox hb19=new HBox();
                hb19.getChildren().addAll(l19,tf19);
                Label l20=new Label("惩罚");
                TextField tf20=new TextField();
                HBox hb20=new HBox();
                hb20.getChildren().addAll(l20,tf20);
                hb1.setSpacing(11);
                hb2.setSpacing(30);
                hb3.setSpacing(30);
                hb4.setSpacing(10);
                hb5.setSpacing(30);
                hb6.setSpacing(30);
                hb7.setSpacing(30);
                hb8.setSpacing(30);
                hb9.setSpacing(30);
                hb10.setSpacing(30);
                hb11.setSpacing(30);
                hb12.setSpacing(30);
                hb13.setSpacing(30);
                hb14.setSpacing(10);
                hb15.setSpacing(30);
                hb16.setSpacing(11);
                hb17.setSpacing(9);
                hb18.setSpacing(30);
                hb19.setSpacing(30);
                hb20.setSpacing(30);
                Button con1=new Button("确定");
                Button ret1=new Button("返回上页");
                HBox hb21=new HBox();
                hb21.getChildren().addAll(con1,ret1);
                hb21.setSpacing(30);
                /////////////////////////////////添加页面的两个响应函数：
                ret1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        tab1.setContent(an1);
                    }
                });
                con1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        Student ss=new Student();
                        ss.setId(tf1.getText());
                        ss.setpwd(tf2.getText());
                        ss.setName(tf3.getText());
                        ss.setUserType(Integer.parseInt(tf4.getText()));
                        ss.setNumber(tf5.getText());
                        ss.setMajorId(Integer.parseInt(tf6.getText()));
                        ss.setGPA(Double.parseDouble(tf7.getText()));
                        ss.setCredit(Double.parseDouble(tf8.getText()));
                        ss.setSTRP(Double.parseDouble(tf9.getText()));
                        ss.setState(Integer.parseInt(tf10.getText()));
                        ss.setPhoneNmuber(tf11.getText());
                        ss.setAge(Integer.parseInt(tf12.getText()));
                        ss.setSex(Integer.parseInt(tf13.getText()));
                        ss.setRegister(tf14.getText());
                        ss.setDormNum(tf15.getText());
                        ss.setCardBalance(Double.parseDouble(tf16.getText()));
                        ss.setLendBooksNum(Integer.parseInt(tf17.getText()));
                        ss.setRenown(tf18.getText());
                        ss.setCampusPosition(Integer.parseInt(tf19.getText()));
                        ss.setPunishment(Integer.parseInt(tf20.getText()));
                        Message mes = new Message();
                        mes.setMessageType("ADD_STUDENT");
                        mes.setData(ss);
                        Message serverResponse = client.sendRequestToServer(mes);
                        tab1.setContent(sc);

                    }
                });
                VBox vb1=new VBox();
                vb1.getChildren().addAll(t11,hb1,hb2,hb3,hb4,hb5,hb6,hb7,hb8,hb9,hb10,hb11,hb12,hb13,hb14,hb15,hb16,hb17,hb18,hb19,hb20,hb21);
                vb1.setSpacing(25);
                an11.getChildren().add(vb1);
                an11.setLeftAnchor(vb1, 350.0);
                an11.setTopAnchor(vb1, 20.0);
                sc.setContent(an11);
                tab1.setContent(sc);
            }
        });

        delete1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                AnchorPane an12=new AnchorPane();
                Text t12=new Text("请输入待删除的学生信息：");
                Label l21=new Label("一卡通");
                TextField tf21=new TextField();
                HBox hb22=new HBox();
                hb22.getChildren().addAll(l21,tf21);
                VBox vb2=new VBox();
                Button con12=new Button("确定");
                Button ret12=new Button("返回上页");
                HBox hb23=new HBox();
                hb23.getChildren().addAll(con12,ret12);
                hb22.setSpacing(30);
                hb23.setSpacing(30);
                vb2.getChildren().addAll(t12,hb22,hb23);
                an12.setLeftAnchor(vb2, 350.0);
                an12.setTopAnchor(vb2, 20.0);
                vb2.setSpacing(26);
                an12.getChildren().addAll(vb2);
                tab1.setContent(an12);
                /////////////////////////////////添加页面的两个响应函数：
                ret12.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        tab1.setContent(an1);
                    }
                });
                con12.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        String temp=tf21.getText();
                        Message mes = new Message();
                        mes.setMessageType("DELETE_STUDENT");
                        mes.setUserId(temp);
                        Message serverResponse = client.sendRequestToServer(mes);
                        tab1.setContent(an12);

                    }
                });
            }
        });


        modify1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                AnchorPane an13=new AnchorPane();
                Text t13=new Text("请输入待修改的学生信息：");
                Label l22=new Label("一卡通");
                TextField tf22=new TextField();
                HBox hb24=new HBox();
                hb24.getChildren().addAll(l22,tf22);
                Label l23=new Label("选择修改项：");
                ChoiceBox cb=new ChoiceBox();
                cb.getItems().addAll("密码","学号","专业","总绩点","总学分","课外研学","惩罚情况");
                cb.getSelectionModel().selectFirst();
                HBox hb26=new HBox();
                hb26.getChildren().addAll(l23,cb);
                HBox hb27=new HBox();
                Label l24=new Label("修改为：");
                TextField tf23=new TextField();
                hb27.getChildren().addAll(l24,tf23);
                Button con13=new Button("修改");
                Button ret13=new Button("返回上页");
                HBox hb25=new HBox();
                hb25.getChildren().addAll(con13,ret13);
                hb25.setSpacing(30);
                hb24.setSpacing(30);
                hb26.setSpacing(15);
                hb27.setSpacing(30);
                VBox vb3=new VBox();
                vb3.getChildren().addAll(t13,hb24,hb26,hb27,hb25);
                an13.setLeftAnchor(vb3, 350.0);
                an13.setTopAnchor(vb3, 20.0);
                vb3.setSpacing(26);
                an13.getChildren().addAll(vb3);
                tab1.setContent(an13);
                /////////////////////////////////添加页面的两个响应函数：
                ret13.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        tab1.setContent(an1);
                    }
                });
                con13.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        Student temp=new Student();
                        Message mes = new Message();
                        mes.setMessageType("QUERY_STUDENT");
                        mes.setUserId(tf22.getText());
                        Message serverResponse = client.sendRequestToServer(mes);
                        temp=(Student) serverResponse.getData();
                        if(cb.getValue().equals("密码"))
                        {
                            temp.setpwd(tf23.getText());
                        }
                        if(cb.getValue().equals("学号"))
                        {
                            temp.setNumber(tf23.getText());
                        }
                        if(cb.getValue().equals("专业"))
                        {
                            temp.setMajorId(Integer.parseInt(tf23.getText()));
                        }
                        if(cb.getValue().equals("总绩点"))
                        {
                            temp.setGPA(Double.parseDouble(tf23.getText()));
                        }
                        if(cb.getValue().equals("总学分"))
                        {
                            temp.setCredit(Double.parseDouble(tf23.getText()));
                        }
                        if(cb.getValue().equals("课外研学"))
                        {
                            temp.setSTRP(Double.parseDouble(tf23.getText()));
                        }
                        if(cb.getValue().equals("惩罚情况"))
                        {
                            temp.setPunishment(Integer.parseInt(tf23.getText()));
                        }
                        mes.setMessageType("UPDATE_STUDENT");
                        mes.setData(temp);
                        serverResponse = client.sendRequestToServer(mes);
                        tab1.setContent(an13);

                    }
                });

            }
        });
        an1.getChildren().addAll(tv,add1,delete1,modify1);
        an1.setLeftAnchor(tv,  20.0);
        an1.setTopAnchor(tv, 20.0);
        tab1.setContent(an1);

        return tab1;
    }
}
