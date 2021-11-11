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
import vo.Message;
import vo.Retailer;

import java.util.ArrayList;

public class MyInfo_tab_ret_Admin {

    public Tab MyInfo_tab_ret(){
        Tab tab3 = new Tab("职工-商户信息");
        Client client = new Client();


        AnchorPane an3=new AnchorPane();
        Message mes3=new Message();
        mes3.setMessageType("OUTPUT_ALL_RETAILER");
        Message serverResponse3 = client.sendRequestToServer(mes3);
        ArrayList<Retailer> relist3 = (ArrayList<Retailer>) serverResponse3.getData();
        ObservableList<Retailer> list3= FXCollections.observableArrayList();
        for(int i=0;i<relist3.size();i++)
        {
            list3.add(relist3.get(i));
        }
        TableView<Retailer> tv3=new TableView<Retailer>(list3);
        tv3.setPrefWidth(890);
        tv3.setPrefHeight(450);
        TableColumn<Retailer,String> tc_uID3=new TableColumn<Retailer,String>("一卡通");
        tc_uID3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Retailer,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getId());
                return pw;
            }
        });
        tv3.getColumns().add(tc_uID3);
        TableColumn<Retailer,String> tc_upwd3=new TableColumn<Retailer,String>("密码");
        tc_upwd3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Retailer,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getpwd());
                return pw;
            }
        });
        tv3.getColumns().add(tc_upwd3);
        TableColumn<Retailer,String> tc_name3=new TableColumn<Retailer,String>("姓名");
        tc_name3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Retailer,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getName());
                return pw;
            }
        });
        tv3.getColumns().add(tc_name3);
        TableColumn<Retailer,Number> tc_age3=new TableColumn<Retailer,Number>("年龄");
        tc_age3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(CellDataFeatures<Retailer,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getAge());
                return pw;
            }
        });
        tv3.getColumns().add(tc_age3);
        TableColumn<Retailer,Number> tc_sex3=new TableColumn<Retailer,Number>("性别");
        tc_sex3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(CellDataFeatures<Retailer,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSex());
                return pw;
            }
        });
        tv3.getColumns().add(tc_sex3);
        TableColumn<Retailer,String> tc_renown3=new TableColumn<Retailer,String>("声誉");
        tc_renown3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Retailer,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getRenown());
                return pw;
            }
        });
        tv3.getColumns().add(tc_renown3);
        TableColumn<Retailer,String> tc_shopname=new TableColumn<Retailer,String>("商店名称");
        tc_shopname.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Retailer,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getShopName());
                return pw;
            }
        });
        tv3.getColumns().add(tc_shopname);
        TableColumn<Retailer,String> tc_position=new TableColumn<Retailer,String>("商店位置");
        tc_position.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Retailer,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Retailer,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getPosition());
                return pw;
            }
        });
        tv3.getColumns().add(tc_position);
        tv3.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        Button add3=new Button("增加记录");
        add3.setLayoutX(938);
        add3.setLayoutY(110);
        Button delete3=new Button("删除记录");
        delete3.setLayoutX(938);
        delete3.setLayoutY(170);
        Button modify3=new Button("修改记录");
        modify3.setLayoutX(938);
        modify3.setLayoutY(230);

        add3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                AnchorPane an31=new AnchorPane();
                ScrollPane sc3=new ScrollPane();
                Text t31=new Text("请输入待增加的商户信息：");
                Label l38=new Label("一卡通");
                TextField tf36=new TextField();
                HBox hb44=new HBox();
                hb44.getChildren().addAll(l38,tf36);
                Label l39=new Label("密码");
                TextField tf37=new TextField();
                HBox hb45=new HBox();
                hb45.getChildren().addAll(l39,tf37);
                Label l40=new Label("姓名");
                TextField tf38=new TextField();
                HBox hb46=new HBox();
                hb46.getChildren().addAll(l40,tf38);
                Label l41=new Label("用户类型");
                TextField tf39=new TextField();
                HBox hb47=new HBox();
                hb47.getChildren().addAll(l41,tf39);
                Label l42=new Label("职业类型");
                TextField tf40=new TextField();
                HBox hb48=new HBox();
                hb48.getChildren().addAll(l42,tf40);
                Label l43=new Label("年龄");
                TextField tf41=new TextField();
                HBox hb49=new HBox();
                hb49.getChildren().addAll(l43,tf41);
                Label l44=new Label("性别");
                TextField tf42=new TextField();
                HBox hb50=new HBox();
                hb50.getChildren().addAll(l44,tf42);
                Label l45=new Label("声誉");
                TextField tf43=new TextField();
                HBox hb51=new HBox();
                hb51.getChildren().addAll(l45,tf43);
                Label l46=new Label("商店名称");
                TextField tf44=new TextField();
                HBox hb52=new HBox();
                hb52.getChildren().addAll(l46,tf44);
                Label l47=new Label("商店位置");
                TextField tf45=new TextField();
                HBox hb53=new HBox();
                hb53.getChildren().addAll(l47,tf45);
                hb44.setSpacing(11);
                hb45.setSpacing(30);
                hb46.setSpacing(30);
                hb47.setSpacing(30);
                hb48.setSpacing(3);
                hb49.setSpacing(30);
                hb50.setSpacing(30);
                hb51.setSpacing(30);
                hb52.setSpacing(3);
                hb53.setSpacing(3);
                Button con31=new Button("确定");
                Button ret31=new Button("返回上页");
                HBox hb54=new HBox();
                hb54.getChildren().addAll(con31,ret31);
                hb54.setSpacing(30);
                /////////////////////////////////添加页面的两个响应函数：
                ret31.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        tab3.setContent(an3);
                    }
                });
                con31.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        Retailer tt=new Retailer();
                        tt.setId(tf36.getText());
                        tt.setpwd(tf37.getText());
                        tt.setName(tf38.getText());
                        tt.setUserType(Integer.parseInt(tf39.getText()));
                        tt.setJobType(Integer.parseInt(tf40.getText()));
                        tt.setAge(Integer.parseInt(tf41.getText()));
                        tt.setSex(Integer.parseInt(tf42.getText()));
                        tt.setRenown(tf43.getText());
                        tt.setShopName(tf44.getText());
                        tt.setPosition(tf45.getText());
                        Message mes = new Message();
                        mes.setMessageType("ADD_RETAILER");
                        mes.setData(tt);
                        Message serverResponse = client.sendRequestToServer(mes);
                        tab3.setContent(sc3);

                    }
                });
                VBox vb7=new VBox();
                vb7.getChildren().addAll(t31,hb44,hb45,hb46,hb47,hb48,hb49,hb50,hb51,hb52,hb53,hb54);
                vb7.setSpacing(25);
                an31.getChildren().add(vb7);
                an31.setLeftAnchor(vb7, 350.0);
                an31.setTopAnchor(vb7, 20.0);
                sc3.setContent(an31);
                tab3.setContent(sc3);
            }
        });


        delete3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                AnchorPane an32=new AnchorPane();
                Text t32=new Text("请输入待删除的商户信息：");
                Label l48=new Label("一卡通");
                TextField tf46=new TextField();
                HBox hb55=new HBox();
                hb55.getChildren().addAll(l48,tf46);
                VBox vb8=new VBox();
                Button con32=new Button("确定");
                Button ret32=new Button("返回上页");
                HBox hb56=new HBox();
                hb56.getChildren().addAll(con32,ret32);
                hb55.setSpacing(30);
                hb56.setSpacing(30);
                vb8.getChildren().addAll(t32,hb55,hb56);
                an32.setLeftAnchor(vb8, 350.0);
                an32.setTopAnchor(vb8, 20.0);
                vb8.setSpacing(26);
                an32.getChildren().addAll(vb8);
                tab3.setContent(an32);
                /////////////////////////////////添加页面的两个响应函数：
                ret32.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        tab3.setContent(an3);
                    }
                });
                con32.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        String temp=tf46.getText();
                        Message mes = new Message();
                        mes.setMessageType("DELETE_RETAILER");
                        mes.setUserId(temp);
                        Message serverResponse = client.sendRequestToServer(mes);
                        tab3.setContent(an32);

                    }
                });
            }
        });


        modify3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                AnchorPane an33=new AnchorPane();
                Text t33=new Text("请输入待修改的商户信息：");
                Label l49=new Label("一卡通");
                TextField tf47=new TextField();
                HBox hb57=new HBox();
                hb57.getChildren().addAll(l49,tf47);
                Label l50=new Label("选择修改项：");
                ChoiceBox cb3=new ChoiceBox();
                cb3.getItems().addAll("密码","姓名","年龄","声誉","商店名称","商店位置");
                cb3.getSelectionModel().selectFirst();
                HBox hb58=new HBox();
                hb58.getChildren().addAll(l50,cb3);
                HBox hb59=new HBox();
                Label l51=new Label("修改为：");
                TextField tf48=new TextField();
                hb59.getChildren().addAll(l51,tf48);
                Button con33=new Button("修改");
                Button ret33=new Button("返回上页");
                HBox hb60=new HBox();
                hb60.getChildren().addAll(con33,ret33);
                hb57.setSpacing(30);
                hb58.setSpacing(30);
                hb59.setSpacing(15);
                hb60.setSpacing(30);
                VBox vb9=new VBox();
                vb9.getChildren().addAll(t33,hb57,hb58,hb59,hb60);
                an33.setLeftAnchor(vb9, 350.0);
                an33.setTopAnchor(vb9, 20.0);
                vb9.setSpacing(26);
                an33.getChildren().addAll(vb9);
                tab3.setContent(an33);
                /////////////////////////////////添加页面的两个响应函数：
                ret33.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        tab3.setContent(an3);
                    }
                });
                con33.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        Retailer temp=new Retailer();
                        Message mes = new Message();
                        mes.setMessageType("QUERY_RETAILER");
                        mes.setUserId(tf47.getText());
                        Message serverResponse = client.sendRequestToServer(mes);
                        temp=(Retailer) serverResponse.getData();
                        if(cb3.getValue().equals("密码"))
                        {
                            temp.setpwd(tf48.getText());
                        }
                        if(cb3.getValue().equals("姓名"))
                        {
                            temp.setName(tf48.getText());
                        }
                        if(cb3.getValue().equals("年龄"))
                        {
                            temp.setAge(Integer.parseInt(tf48.getText()));
                        }
                        if(cb3.getValue().equals("声誉"))
                        {
                            temp.setRenown(tf48.getText());
                        }
                        if(cb3.getValue().equals("商店名称"))
                        {
                            temp.setShopName(tf48.getText());
                        }
                        if(cb3.getValue().equals("商店位置"))
                        {
                            temp.setPosition(tf48.getText());
                        }

                        mes.setMessageType("UPDATE_RETAILER");
                        mes.setData(temp);
                        serverResponse = client.sendRequestToServer(mes);
                        tab3.setContent(an33);

                    }
                });

            }
        });


        an3.getChildren().addAll(tv3,add3,delete3,modify3);
        an3.setLeftAnchor(tv3,  20.0);
        an3.setTopAnchor(tv3, 20.0);
        tab3.setContent(an3);


        return tab3;
    }
}
