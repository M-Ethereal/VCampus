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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import util.MessageType;
import vo.Doctor;
import vo.Message;

import java.util.ArrayList;

public class MyInfo_tab_doc_Admin {

    public Tab MyInfo_tab_doc(){
        Tab tab4 = new Tab("职工-医生信息");
        Client client = new Client();

        Client client12 = new Client();
        AnchorPane an4=new AnchorPane();
        Message mes4=new Message();
        mes4.setMessageType(MessageType.outputAllDoctor);
        Message serverResponse4 = client12.sendRequestToServer(mes4);
        ArrayList<Doctor> relist4 = (ArrayList<Doctor>) serverResponse4.getData();
        ObservableList<Doctor> list4= FXCollections.observableArrayList();
        for(int i=0;i<relist4.size();i++)
        {
            list4.add(relist4.get(i));
        }
        TableView<Doctor>  tv4=new TableView<Doctor>(list4);
        tv4.setPrefWidth(890);
        tv4.setPrefHeight(450);
        TableColumn<Doctor,String> tc_uID4=new TableColumn<Doctor,String>("一卡通");
        tc_uID4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Doctor,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getPosition());
                return pw;
            }
        });
        tv4.getColumns().add(tc_uID4);
        TableColumn<Doctor,String> tc_upwd4=new TableColumn<Doctor,String>("密码");
        tc_upwd4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Doctor,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getpwd());
                return pw;
            }
        });
        tv4.getColumns().add(tc_upwd4);
        TableColumn<Doctor,String> tc_name4=new TableColumn<Doctor,String>("姓名");
        tc_name4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Doctor,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getName());
                return pw;
            }
        });
        tv4.getColumns().add(tc_name4);
        TableColumn<Doctor,Number> tc_age4=new TableColumn<Doctor,Number>("年龄");
        tc_age4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(CellDataFeatures<Doctor,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getAge());
                return pw;
            }
        });
        tv4.getColumns().add(tc_age4);
        TableColumn<Doctor,Number> tc_sex4=new TableColumn<Doctor,Number>("性别");
        tc_sex4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(CellDataFeatures<Doctor,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSex());
                return pw;
            }
        });
        tv4.getColumns().add(tc_sex4);
        TableColumn<Doctor,String> tc_renown4=new TableColumn<Doctor,String>("声誉");
        tc_renown4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Doctor,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getRenown());
                return pw;
            }
        });
        tv4.getColumns().add(tc_renown4);
        TableColumn<Doctor,String> tc_dept=new TableColumn<Doctor,String>("科室");
        tc_dept.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Doctor,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getDept());
                return pw;
            }
        });
        tv4.getColumns().add(tc_dept);
        TableColumn<Doctor,String> tc_position4=new TableColumn<Doctor,String>("门诊位置");
        tc_position4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Doctor,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Doctor,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getPosition());
                return pw;
            }
        });
        tv4.getColumns().add(tc_position4);
        tv4.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        Button add4=new Button("增加记录");
        add4.setLayoutX(938);
        add4.setLayoutY(110);
        Button delete4=new Button("删除记录");
        delete4.setLayoutX(938);
        delete4.setLayoutY(170);
        Button modify4=new Button("修改记录");
        modify4.setLayoutX(938);
        modify4.setLayoutY(230);

        add4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                AnchorPane an41=new AnchorPane();
                ScrollPane sc4=new ScrollPane();
                Text t41=new Text("请输入待增加的医生信息：");
                Label l52=new Label("一卡通");
                TextField tf49=new TextField();
                HBox hb61=new HBox();
                hb61.getChildren().addAll(l52,tf49);
                Label l53=new Label("密码");
                TextField tf50=new TextField();
                HBox hb62=new HBox();
                hb62.getChildren().addAll(l53,tf50);
                Label l54=new Label("姓名");
                TextField tf51=new TextField();
                HBox hb63=new HBox();
                hb63.getChildren().addAll(l54,tf51);
                Label l55=new Label("用户类型");
                TextField tf52=new TextField();
                HBox hb64=new HBox();
                hb64.getChildren().addAll(l55,tf52);
                Label l56=new Label("职业类型");
                TextField tf53=new TextField();
                HBox hb65=new HBox();
                hb65.getChildren().addAll(l56,tf53);
                Label l57=new Label("年龄");
                TextField tf54=new TextField();
                HBox hb66=new HBox();
                hb66.getChildren().addAll(l57,tf54);
                Label l58=new Label("性别");
                TextField tf55=new TextField();
                HBox hb67=new HBox();
                hb67.getChildren().addAll(l58,tf55);
                Label l59=new Label("声誉");
                TextField tf56=new TextField();
                HBox hb68=new HBox();
                hb68.getChildren().addAll(l59,tf56);
                Label l60=new Label("科室");
                TextField tf57=new TextField();
                HBox hb69=new HBox();
                hb69.getChildren().addAll(l60,tf57);
                Label l61=new Label("门诊位置");
                TextField tf58=new TextField();
                HBox hb74=new HBox();
                hb74.getChildren().addAll(l61,tf58);
                HBox hb70=new HBox();
                hb70.getChildren().addAll(l60,tf57);
                hb61.setSpacing(11);
                hb62.setSpacing(30);
                hb63.setSpacing(30);
                hb64.setSpacing(3);
                hb65.setSpacing(3);
                hb66.setSpacing(30);
                hb67.setSpacing(30);
                hb68.setSpacing(30);
                hb69.setSpacing(30);
                hb70.setSpacing(30);
                hb74.setSpacing(3);
                Button con41=new Button("确定");
                Button ret41=new Button("返回上页");
                HBox hb71=new HBox();
                hb71.getChildren().addAll(con41,ret41);
                hb71.setSpacing(30);
                /////////////////////////////////添加页面的两个响应函数：
                ret41.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        tab4.setContent(an4);
                    }
                });
                con41.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        Doctor tt=new Doctor();
                        tt.setId(tf49.getText());
                        tt.setpwd(tf50.getText());
                        tt.setName(tf51.getText());
                        tt.setUserType(Integer.parseInt(tf52.getText()));
                        tt.setJobType(Integer.parseInt(tf53.getText()));
                        tt.setAge(Integer.parseInt(tf54.getText()));
                        tt.setSex(Integer.parseInt(tf55.getText()));
                        tt.setRenown(tf56.getText());
                        tt.setDept(tf57.getText());
                        tt.setPosition(tf58.getText());
                        Message mes = new Message();
                        mes.setMessageType(MessageType.addDoctor);
                        mes.setData(tt);
                        Message serverResponse = client.sendRequestToServer(mes);
                        tab4.setContent(sc4);

                    }
                });
                VBox vb10=new VBox();
                vb10.getChildren().addAll(t41,hb61,hb62,hb63,hb64,hb65,hb66,hb67,hb68,hb69,hb70,hb74,hb71);
                vb10.setSpacing(25);
                an41.getChildren().add(vb10);
                an41.setLeftAnchor(vb10, 350.0);
                an41.setTopAnchor(vb10, 20.0);
                sc4.setContent(an41);
                tab4.setContent(sc4);
            }
        });


        delete4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                AnchorPane an42=new AnchorPane();
                Text t42=new Text("请输入待删除的医生信息：");
                Label l62=new Label("一卡通");
                TextField tf59=new TextField();
                HBox hb72=new HBox();
                hb72.getChildren().addAll(l62,tf59);
                VBox vb11=new VBox();
                Button con42=new Button("确定");
                Button ret42=new Button("返回上页");
                HBox hb73=new HBox();
                hb73.getChildren().addAll(con42,ret42);
                hb72.setSpacing(30);
                hb73.setSpacing(30);
                vb11.getChildren().addAll(t42,hb72,hb73);
                an42.setLeftAnchor(vb11, 350.0);
                an42.setTopAnchor(vb11, 20.0);
                vb11.setSpacing(26);
                an42.getChildren().addAll(vb11);
                tab4.setContent(an42);
                /////////////////////////////////添加页面的两个响应函数：
                ret42.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        tab4.setContent(an4);
                    }
                });
                con42.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        String temp=tf59.getText();
                        Message mes = new Message();
                        mes.setMessageType("DELETE_DOCTOR");
                        mes.setUserId(temp);
                        Message serverResponse = client.sendRequestToServer(mes);
                        tab4.setContent(an42);

                    }
                });
            }
        });


        modify4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                AnchorPane an43=new AnchorPane();
                Text t43=new Text("请输入待修改的医生信息：");
                Label l63=new Label("一卡通");
                TextField tf60=new TextField();
                HBox hb75=new HBox();
                hb75.getChildren().addAll(l63,tf60);
                Label l64=new Label("选择修改项：");
                ChoiceBox cb4=new ChoiceBox();
                cb4.getItems().addAll("密码","姓名","年龄","声誉","科室");
                cb4.getSelectionModel().selectFirst();
                HBox hb76=new HBox();
                hb76.getChildren().addAll(l64,cb4);
                HBox hb77=new HBox();
                Label l65=new Label("修改为：");
                TextField tf61=new TextField();
                hb77.getChildren().addAll(l65,tf61);
                Button con43=new Button("修改");
                Button ret43=new Button("返回上页");
                HBox hb78=new HBox();
                hb78.getChildren().addAll(con43,ret43);
                hb75.setSpacing(30);
                hb76.setSpacing(30);
                hb77.setSpacing(15);
                hb78.setSpacing(30);
                VBox vb12=new VBox();
                vb12.getChildren().addAll(t43,hb75,hb76,hb77,hb78);
                an43.setLeftAnchor(vb12, 350.0);
                an43.setTopAnchor(vb12, 20.0);
                vb12.setSpacing(26);
                an43.getChildren().addAll(vb12);
                tab4.setContent(an43);
                /////////////////////////////////添加页面的两个响应函数：
                ret43.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        tab4.setContent(an4);
                    }
                });
                con43.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        Doctor temp=new Doctor();
                        Message mes = new Message();
                        mes.setMessageType("QUERY_DOCTOR");
                        mes.setUserId(tf60.getText());
                        Message serverResponse = client.sendRequestToServer(mes);
                        temp=(Doctor) serverResponse.getData();
                        if(cb4.getValue().equals("密码"))
                        {
                            temp.setpwd(tf61.getText());
                        }
                        if(cb4.getValue().equals("姓名"))
                        {
                            temp.setName(tf61.getText());
                        }
                        if(cb4.getValue().equals("年龄"))
                        {
                            temp.setAge(Integer.parseInt(tf61.getText()));
                        }
                        if(cb4.getValue().equals("声誉"))
                        {
                            temp.setRenown(tf61.getText());
                        }
                        if(cb4.getValue().equals("科室"))
                        {
                            temp.setDept(tf61.getText());
                        }

                        mes.setMessageType("UPDATE_DOCTOR");
                        mes.setData(temp);
                        serverResponse = client.sendRequestToServer(mes);
                        tab4.setContent(an43);

                    }
                });

            }
        });

        an4.getChildren().addAll(tv4,add4,delete4,modify4);
        an4.setLeftAnchor(tv4,  20.0);
        an4.setTopAnchor(tv4, 20.0);
        tab4.setContent(an4);



        return tab4;
    }
}
