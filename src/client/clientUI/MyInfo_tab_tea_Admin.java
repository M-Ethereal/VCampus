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
import vo.Teacher;

import java.util.ArrayList;

public class MyInfo_tab_tea_Admin {

    public Tab MyInfo_tab_tea(){
        Tab tab2 = new Tab("教师信息");
        Client client = new Client();

        AnchorPane an2=new AnchorPane();
        Message mes2=new Message();
        mes2.setMessageType("OUTPUT_ALL_TEACHER");
        Message serverResponse2 = client.sendRequestToServer(mes2);
        ArrayList<Teacher> relist2 = (ArrayList<Teacher>) serverResponse2.getData();
        ObservableList<Teacher> list2= FXCollections.observableArrayList();
        for(int i=0;i<relist2.size();i++)
        {
            list2.add(relist2.get(i));
        }
        TableView<Teacher>  tv2=new TableView<Teacher>(list2);
        tv2.setPrefWidth(890);
        tv2.setPrefHeight(450);
        TableColumn<Teacher,String> tc_uID2=new TableColumn<Teacher,String>("一卡通");
        tc_uID2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Teacher,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getId());
                return pw;
            }
        });
        tv2.getColumns().add(tc_uID2);
        TableColumn<Teacher,String> tc_upwd2=new TableColumn<Teacher,String>("密码");
        tc_upwd2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Teacher,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getpwd());
                return pw;
            }
        });
        tv2.getColumns().add(tc_upwd2);
        TableColumn<Teacher,String> tc_name2=new TableColumn<Teacher,String>("姓名");
        tc_name2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Teacher,String> param)
            {
                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getName());
                return pw;
            }
        });
        tv2.getColumns().add(tc_name2);
        TableColumn<Teacher,Number> tc_age2=new TableColumn<Teacher,Number>("年龄");
        tc_age2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(CellDataFeatures<Teacher,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getAge());
                return pw;
            }
        });
        tv2.getColumns().add(tc_age2);
        TableColumn<Teacher,Number> tc_sex2=new TableColumn<Teacher,Number>("性别");
        tc_sex2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(CellDataFeatures<Teacher,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSex());
                return pw;
            }
        });
        tv2.getColumns().add(tc_sex2);
        TableColumn<Teacher,Number> tc_major2=new TableColumn<Teacher,Number>("专业");
        tc_major2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(CellDataFeatures<Teacher,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getMajorId());
                return pw;
            }
        });
        tv2.getColumns().add(tc_major2);
        TableColumn<Teacher,Number> tc_title2=new TableColumn<Teacher,Number>("头衔");
        tc_title2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(CellDataFeatures<Teacher,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getTitle());
                return pw;
            }
        });
        tv2.getColumns().add(tc_title2);
        TableColumn<Teacher,Number> tc_lendbook2=new TableColumn<Teacher,Number>("借书数目");
        tc_lendbook2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Teacher,Number>,ObservableValue<Number>>()
        {
            @Override
            public ObservableValue<Number> call(CellDataFeatures<Teacher,Number> param)
            {
                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getLendBooksNum());
                return pw;
            }
        });
        tv2.getColumns().add(tc_lendbook2);
        tv2.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        Button add2=new Button("增加记录");
        add2.setLayoutX(938);
        add2.setLayoutY(110);
        Button delete2=new Button("删除记录");
        delete2.setLayoutX(938);
        delete2.setLayoutY(170);
        Button modify2=new Button("修改记录");
        modify2.setLayoutX(938);
        modify2.setLayoutY(230);

        add2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                AnchorPane an21=new AnchorPane();
                ScrollPane sc2=new ScrollPane();
                Text t21=new Text("请输入待增加的教师信息：");
                Label l25=new Label("一卡通");
                TextField tf24=new TextField();
                HBox hb28=new HBox();
                hb28.getChildren().addAll(l25,tf24);
                Label l26=new Label("密码");
                TextField tf25=new TextField();
                HBox hb29=new HBox();
                hb29.getChildren().addAll(l26,tf25);
                Label l27=new Label("姓名");
                TextField tf26=new TextField();
                HBox hb30=new HBox();
                hb30.getChildren().addAll(l27,tf26);
                Label l28=new Label("用户类型");
                TextField tf27=new TextField();
                HBox hb31=new HBox();
                hb31.getChildren().addAll(l28,tf27);
                Label l29=new Label("年龄");
                TextField tf28=new TextField();
                HBox hb32=new HBox();
                hb32.getChildren().addAll(l29,tf28);
                Label l30=new Label("性别");
                TextField tf29=new TextField();
                HBox hb33=new HBox();
                hb33.getChildren().addAll(l30,tf29);
                Label l31=new Label("专业");
                TextField tf30=new TextField();
                HBox hb34=new HBox();
                hb34.getChildren().addAll(l31,tf30);
                Label l32=new Label("头衔");
                TextField tf31=new TextField();
                HBox hb35=new HBox();
                hb35.getChildren().addAll(l32,tf31);
                Label l33=new Label("借书数目");
                TextField tf32=new TextField();
                HBox hb36=new HBox();
                hb36.getChildren().addAll(l33,tf32);

                hb28.setSpacing(11);
                hb29.setSpacing(30);
                hb30.setSpacing(30);
                hb31.setSpacing(3);
                hb32.setSpacing(30);
                hb33.setSpacing(30);
                hb34.setSpacing(30);
                hb35.setSpacing(30);
                hb36.setSpacing(3);

                Button con21=new Button("确定");
                Button ret21=new Button("返回上页");
                HBox hb37=new HBox();
                hb37.getChildren().addAll(con21,ret21);
                hb37.setSpacing(30);
                /////////////////////////////////添加页面的两个响应函数：
                ret21.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        tab2.setContent(an2);
                    }
                });
                con21.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        Teacher tt=new Teacher();
                        tt.setId(tf24.getText());
                        tt.setpwd(tf25.getText());
                        tt.setName(tf26.getText());
                        tt.setUserType(Integer.parseInt(tf27.getText()));
                        tt.setAge(Integer.parseInt(tf28.getText()));
                        tt.setSex(Integer.parseInt(tf29.getText()));
                        tt.setMajorId(Integer.parseInt(tf30.getText()));
                        tt.setTitle(Integer.parseInt(tf31.getText()));
                        tt.setLendBooksNum(Integer.parseInt(tf32.getText()));
                        Message mes = new Message();
                        mes.setMessageType("ADD_TEACHER");
                        mes.setData(tt);
                        Message serverResponse = client.sendRequestToServer(mes);
                        tab2.setContent(sc2);

                    }
                });
                VBox vb4=new VBox();
                vb4.getChildren().addAll(t21,hb28,hb29,hb30,hb31,hb32,hb33,hb34,hb35,hb36,hb37);
                vb4.setSpacing(25);
                an21.getChildren().add(vb4);
                an21.setLeftAnchor(vb4, 350.0);
                an21.setTopAnchor(vb4, 20.0);
                sc2.setContent(an21);
                tab2.setContent(sc2);
            }
        });

        delete2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                AnchorPane an22=new AnchorPane();
                Text t22=new Text("请输入待删除的教师信息：");
                Label l34=new Label("一卡通");
                TextField tf33=new TextField();
                HBox hb38=new HBox();
                hb38.getChildren().addAll(l34,tf33);
                VBox vb5=new VBox();
                Button con22=new Button("确定");
                Button ret22=new Button("返回上页");
                HBox hb39=new HBox();
                hb39.getChildren().addAll(con22,ret22);
                hb38.setSpacing(30);
                hb39.setSpacing(30);
                vb5.getChildren().addAll(t22,hb38,hb39);
                an22.setLeftAnchor(vb5, 350.0);
                an22.setTopAnchor(vb5, 20.0);
                vb5.setSpacing(26);
                an22.getChildren().addAll(vb5);
                tab2.setContent(an22);
                /////////////////////////////////添加页面的两个响应函数：
                ret22.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        tab2.setContent(an2);
                    }
                });
                con22.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        String temp=tf33.getText();
                        Message mes = new Message();
                        mes.setMessageType("DELETE_TEACHER");
                        mes.setUserId(temp);
                        Message serverResponse = client.sendRequestToServer(mes);
                        tab2.setContent(an22);

                    }
                });
            }
        });


        modify2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                AnchorPane an23=new AnchorPane();
                Text t23=new Text("请输入待修改的学生信息：");
                Label l35=new Label("一卡通");
                TextField tf34=new TextField();
                HBox hb40=new HBox();
                hb40.getChildren().addAll(l35,tf34);
                Label l36=new Label("选择修改项：");
                ChoiceBox cb2=new ChoiceBox();
                cb2.getItems().addAll("密码","姓名","年龄","专业","借书数目","头衔");
                cb2.getSelectionModel().selectFirst();
                HBox hb41=new HBox();
                hb41.getChildren().addAll(l36,cb2);
                HBox hb42=new HBox();
                Label l37=new Label("修改为：");
                TextField tf35=new TextField();
                hb42.getChildren().addAll(l37,tf35);
                Button con23=new Button("修改");
                Button ret23=new Button("返回上页");
                HBox hb43=new HBox();
                hb43.getChildren().addAll(con23,ret23);
                hb40.setSpacing(30);
                hb41.setSpacing(30);
                hb42.setSpacing(15);
                hb43.setSpacing(30);
                VBox vb6=new VBox();
                vb6.getChildren().addAll(t23,hb40,hb41,hb42,hb43);
                an23.setLeftAnchor(vb6, 350.0);
                an23.setTopAnchor(vb6, 20.0);
                vb6.setSpacing(26);
                an23.getChildren().addAll(vb6);
                tab2.setContent(an23);
                /////////////////////////////////添加页面的两个响应函数：
                ret23.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        tab2.setContent(an2);
                    }
                });
                con23.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        Teacher temp=new Teacher();
                        Message mes = new Message();
                        mes.setMessageType("QUERY_TEACHER");
                        mes.setUserId(tf34.getText());
                        Message serverResponse = client.sendRequestToServer(mes);
                        temp=(Teacher) serverResponse.getData();
                        if(cb2.getValue().equals("密码"))
                        {
                            temp.setpwd(tf35.getText());
                        }
                        if(cb2.getValue().equals("姓名"))
                        {
                            temp.setName(tf35.getText());
                        }
                        if(cb2.getValue().equals("专业"))
                        {
                            temp.setMajorId(Integer.parseInt(tf35.getText()));
                        }
                        if(cb2.getValue().equals("年龄"))
                        {
                            temp.setAge(Integer.parseInt(tf35.getText()));;
                        }
                        if(cb2.getValue().equals("借书数目"))
                        {
                            temp.setLendBooksNum(Integer.parseInt(tf35.getText()));
                        }
                        if(cb2.getValue().equals("头衔"))
                        {
                            temp.setTitle(Integer.parseInt(tf35.getText()));
                        }

                        mes.setMessageType("UPDATE_TEACHER");
                        mes.setData(temp);
                        serverResponse = client.sendRequestToServer(mes);
                        tab2.setContent(an23);

                    }
                });

            }
        });


        an2.getChildren().addAll(tv2,add2,delete2,modify2);
        an2.setLeftAnchor(tv2,  20.0);
        an2.setTopAnchor(tv2, 20.0);
        tab2.setContent(an2);



        return tab2;
    }
}
