package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import server.dao.BankDao;
import server.dao.StudentDao;
import util.MessageType;
import vo.BankRecord;
import vo.Message;
import vo.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class BankHomePageUI {
    double tuition = 0;
    double phone = 0;
    double wifi = 0;
    double service = 0;
    double shop = 0;
    double hospital = 0;
    double day1 = 0;
    double day2 = 0;
    double day3 = 0;
    double day4 = 0;
    double day5 = 0;
    double day6 = 0;
    double day7 = 0;


    public AnchorPane BankHomePageUI(Student stu)
    {
        Client cli = new Client();

        //学生界面
        //交易明细按钮
        Button b2 = new Button();
        b2.setText("交易明细");
        b2.setLayoutX(40);
        b2.setLayoutY(75);
        b2.setPrefHeight(40);
        b2.setPrefWidth(160);
        b2.setFont(Font.font("sans-serf", 15));
        b2.setStyle("-fx-background-radius:10;");
        b2.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                //查询交易记录功能（弹出新界面）
                Message mes1= new Message();
                mes1.setUserId(stu.getId());
                mes1.setMessageType(MessageType.record_Query);
                Message serverResponse = cli.sendRequestToServer(mes1);
                ArrayList<BankRecord> BankRecordList = (ArrayList<BankRecord>) serverResponse.getData();
                ObservableList<BankRecord> list1= FXCollections.observableArrayList();

                for(int i=0;i<BankRecordList.size();i++)
                {
                    list1.add(BankRecordList.get(i));
                }

                TableView<BankRecord> tv=new TableView<BankRecord>(list1);
                tv.setLayoutX(45);
                tv.setLayoutY(20);
                tv.setPrefWidth(500);
                tv.setPrefHeight(700);

                TableColumn<BankRecord,String> tc_record=new TableColumn<BankRecord,String>("交易详情");
                tc_record.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BankRecord,String>,ObservableValue<String>>()
                {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<BankRecord,String> param)
                    {
                        SimpleStringProperty record=new SimpleStringProperty(param.getValue().getRecord());
                        return record;
                    }
                });
                tv.getColumns().add(tc_record);
                TableColumn<BankRecord,Number> tc_amount=new TableColumn<BankRecord,Number>("交易金额");
                tc_amount.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BankRecord,Number>,ObservableValue<Number>>()
                {
                    public ObservableValue<Number> call(TableColumn.CellDataFeatures<BankRecord,Number> param)
                    {
                        SimpleDoubleProperty amount=new SimpleDoubleProperty(param.getValue().getAmount());
                        return amount;
                    }
                });
                tv.getColumns().add(tc_amount);
                TableColumn<BankRecord,String> tc_date=new TableColumn<BankRecord,String>("交易时间");
                tc_date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BankRecord,String>,ObservableValue<String>>()
                {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<BankRecord,String> param)
                    {
                        SimpleStringProperty date=new SimpleStringProperty(param.getValue().getDate());
                        return date;
                    }
                });
                tv.getColumns().add(tc_date);
                tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

                AnchorPane an = new AnchorPane();
                an.getChildren().add(tv);

                Stage stage = new Stage();
                Scene scene = new Scene(an);
                stage.setScene(scene);
                stage.setTitle("支出详情");
                stage.setWidth(600);
                stage.setHeight(800);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
                event.getSource();
            }

        });

        //一个bug按钮
        Button btest = new Button();
        btest.setText("虚空按钮");
        btest.setPrefHeight(50);
        btest.setPrefWidth(150);
        btest.setVisible(false);

        //一卡通充值按钮
        Button b1 = new Button();
        b1.setText("一卡通充值");
        b1.setPrefHeight(50);
        b1.setPrefWidth(150);
        b1.setFont(Font.font("sans-serf", 15));
        b1.setStyle("-fx-background-radius:10;");

        //常用业务按钮
        Button b3 = new Button();
        b3.setText("常用业务");
        b3.setPrefHeight(50);
        b3.setPrefWidth(150);
        b3.setFont(Font.font("sans-serf", 15));
        b3.setStyle("-fx-background-radius:10;");

        //学费支付按钮
        Button b4 = new Button();
        b4.setText("学费缴纳");
        b4.setPrefHeight(50);
        b4.setPrefWidth(150);
        b4.setFont(Font.font("sans-serf", 15));
        b4.setStyle("-fx-background-radius:10;");

        //流量补充包支付界面确认按钮
        Button byes = new Button();
        byes.setText("支付");
        byes.setLayoutX(320);
        byes.setLayoutY(320);
        byes.setPrefHeight(50);
        byes.setPrefWidth(150);
        byes.setFont(Font.font("sans-serf", 15));
        byes.setStyle("-fx-background-radius:10;");

        //校园网套餐支付界面确认按钮
        Button byes1 = new Button();
        byes1.setText("支付");
        byes1.setLayoutX(320);
        byes1.setLayoutY(320);
        byes1.setPrefHeight(50);
        byes1.setPrefWidth(150);
        byes1.setFont(Font.font("sans-serf", 15));
        byes1.setStyle("-fx-background-radius:10;");

        //学费支付界面确认按钮
        Button byes2 = new Button();
        byes2.setText("支付");
        byes2.setLayoutX(320);
        byes2.setLayoutY(330);
        byes2.setPrefHeight(50);
        byes2.setPrefWidth(150);
        byes2.setFont(Font.font("sans-serf", 15));
        byes2.setStyle("-fx-background-radius:10;");

        //流量补充包界面取消按钮
        Button bnot = new Button();
        bnot.setText("取消");
        bnot.setPrefHeight(50);
        bnot.setPrefWidth(150);
        bnot.setLayoutX(580);
        bnot.setLayoutY(320);
        bnot.setFont(Font.font("sans-serf", 15));
        bnot.setStyle("-fx-background-radius:10;");

        //校园网套餐取消按钮
        Button bnot1 = new Button();
        bnot1.setText("取消");
        bnot1.setPrefHeight(50);
        bnot1.setPrefWidth(150);
        bnot1.setLayoutX(580);
        bnot1.setLayoutY(320);
        bnot1.setFont(Font.font("sans-serf", 15));
        bnot1.setStyle("-fx-background-radius:10;");

        //学费支付取消按钮
        Button bnot2 = new Button();
        bnot2.setText("取消");
        bnot2.setPrefHeight(50);
        bnot2.setPrefWidth(150);
        bnot2.setLayoutX(580);
        bnot2.setLayoutY(330);
        bnot2.setFont(Font.font("sans-serf", 15));
        bnot2.setStyle("-fx-background-radius:10;");

        String value = "";
        if (stu.getSex()==0)
            value = "女士";
        else
            value = "先生";

        Label label1 = new Label("欢迎！"+stu.getName()+" "+value);//stu.getName()+stu.getSex()
        label1.setLayoutX(40);
        label1.setLayoutY(20);
        label1.setPrefHeight(40);
        label1.setPrefWidth(200);
        label1.setFont(Font.font("sans-serf", 18));

        Label label2 = new Label("您的一卡通余额为："+stu.getCardBalance()+" 元");//stu.getCardBalance()
        label2.setLayoutX(300);
        label2.setLayoutY(20);
        label2.setPrefHeight(40);
        label2.setPrefWidth(300);
        label2.setFont(Font.font("sans-serf", 18));

        //tab1初始饼状图
        PieChart.Data d1 = new PieChart.Data("商店支出", 1);
        PieChart.Data d2 = new PieChart.Data("药店支出", 1);
        PieChart.Data d3 = new PieChart.Data("其他业务", 1);
        PieChart.Data d4 = new PieChart.Data("学费缴纳", 1);

        ObservableList<PieChart.Data> datalist = FXCollections.observableArrayList();
        datalist.add(d1);
        datalist.add(d2);
        datalist.add(d3);
        datalist.add(d4);

        PieChart piechart = new PieChart(datalist);
        piechart.setLayoutX(520);
        piechart.setLayoutY(30);
        piechart.setPrefWidth(550);
        piechart.setPrefHeight(450);
        piechart.setLegendSide(Side.BOTTOM);
        piechart.setTitle("消费分布一览");
        piechart.setTitleSide(Side.TOP);

        piechart.getData().forEach(new Consumer<PieChart.Data>()
        {
            public void accept(PieChart.Data t)
            {
                Node node = t.getNode();
                Tooltip tip = new Tooltip("￥"+ t.getPieValue());
                tip.setFont(new Font(20));
                Tooltip.install(node, tip);
            }

        });


        //查看最新消费分布
        Button b5 = new Button();
        b5.setText("近期消费总览");
        b5.setPrefHeight(50);
        b5.setPrefWidth(150);
        b5.setLayoutX(720);
        b5.setLayoutY(480);
        b5.setFont(Font.font("sans-serf", 15));
        b5.setStyle("-fx-background-radius:10;");


        //tab1折初始线图
        NumberAxis x = new NumberAxis("",0,7,1);

        NumberAxis y = new NumberAxis("收支金额",-500,500,100);

        LineChart<Number,Number> linechart= new LineChart<Number,Number>(x,y);
        XYChart.Series<Number, Number> xy=new XYChart.Series<Number, Number>();
        xy.setName("一周内收支变化");

        XYChart.Data<Number, Number> dt1 = new XYChart.Data<Number, Number>(1,0);
        XYChart.Data<Number, Number> dt2 = new XYChart.Data<Number, Number>(2,0);
        XYChart.Data<Number, Number> dt3 = new XYChart.Data<Number, Number>(3,0);
        XYChart.Data<Number, Number> dt4 = new XYChart.Data<Number, Number>(4,0);
        XYChart.Data<Number, Number> dt5 = new XYChart.Data<Number, Number>(5,0);
        XYChart.Data<Number, Number> dt6 = new XYChart.Data<Number, Number>(6,0);
        XYChart.Data<Number, Number> dt7 = new XYChart.Data<Number, Number>(7,0);

        xy.getData().addAll(dt1,dt2,dt3,dt4,dt5,dt6,dt7);

        linechart.setLayoutX(10);
        linechart.setLayoutY(140);
        linechart.setPrefHeight(400);
        linechart.setPrefWidth(520);
        linechart.setCreateSymbols(false);
        linechart.setStyle("-fx-opacity:0.8;");
        linechart.getData().add(xy);


//		Image image = new Image("file:C:/Users/LSR/Desktop/软件工程/银行模块的一点内容/image.png");
//		ImageView iv = new ImageView(image);
//		iv.setPreserveRatio(false);
//		iv.setFitHeight(713);;
//		iv.setFitWidth(1180);




        AnchorPane root = new AnchorPane();



        TabPane tabpane = new TabPane();
        tabpane.setPrefWidth(1058);
        tabpane.setPrefHeight(580);


        JFXTabPane bankTab = new JFXTabPane();
        Tab tab1 = new Tab("我的账户");
        Tab tab2 = new Tab("常用业务");
        bankTab.getTabs().addAll(tab1,tab2);














        //tab1初始界面
        AnchorPane fap = new AnchorPane();

        AnchorPane ap1 = new AnchorPane();
        ap1.getChildren().addAll(label1,label2,b2,piechart,linechart,b5);
        ap1.setVisible(false);

        AnchorPane ap100 = new AnchorPane();
        ap100.setVisible(false);

        fap.getChildren().addAll(ap1,ap100);
        ap1.setVisible(true);

        b5.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {

                Message mes1= new Message();
                mes1.setUserId(stu.getId());
                mes1.setExtraMessage("缴纳学费");
                mes1.setMessageType(MessageType.account_Query);
                Message serverResponse1 = cli.sendRequestToServer(mes1);

                Message mes2= new Message();
                mes2.setUserId(stu.getId());
                mes2.setExtraMessage("支付手机流量包");
                mes2.setMessageType(MessageType.account_Query);
                Message serverResponse2 = cli.sendRequestToServer(mes2);

                Message mes3= new Message();
                mes3.setUserId(stu.getId());
                mes3.setExtraMessage("支付校园网套餐");
                mes3.setMessageType(MessageType.account_Query);
                Message serverResponse3 = cli.sendRequestToServer(mes3);

                Message mes4= new Message();
                mes4.setUserId(stu.getId());
                mes4.setExtraMessage("商店消费");
                mes4.setMessageType(MessageType.account_Query);
                Message serverResponse4 = cli.sendRequestToServer(mes4);

                Message mes5= new Message();
                mes5.setUserId(stu.getId());
                mes5.setExtraMessage("药店消费");
                mes5.setMessageType(MessageType.account_Query);
                Message serverResponse5 = cli.sendRequestToServer(mes5);

                ArrayList<BankRecord> BankRecordList1 = (ArrayList<BankRecord>) serverResponse1.getData();
                for(int i=0;i<BankRecordList1.size();i++)
                {
                    tuition  = tuition - BankRecordList1.get(i).getAmount();

                }

                ArrayList<BankRecord> BankRecordList2 = (ArrayList<BankRecord>) serverResponse2.getData();
                for(int i=0;i<BankRecordList2.size();i++)
                {
                    phone  = phone - BankRecordList2.get(i).getAmount();

                }

                ArrayList<BankRecord> BankRecordList3 = (ArrayList<BankRecord>) serverResponse3.getData();
                for(int i=0;i<BankRecordList3.size();i++)
                {
                    wifi  = wifi - BankRecordList3.get(i).getAmount();

                }

                service = phone + wifi;

                ArrayList<BankRecord> BankRecordList4 = (ArrayList<BankRecord>) serverResponse4.getData();
                for(int i=0;i<BankRecordList4.size();i++)
                {
                    shop  = shop - BankRecordList4.get(i).getAmount();

                }

                ArrayList<BankRecord> BankRecordList5 = (ArrayList<BankRecord>) serverResponse5.getData();
                for(int i=0;i<BankRecordList5.size();i++)
                {
                    hospital  = hospital - BankRecordList5.get(i).getAmount();

                }

                System.out.println(tuition);
                System.out.println(phone);
                System.out.println(wifi);
                System.out.println(shop);
                System.out.println(hospital);

                PieChart.Data dd1 = new PieChart.Data("商店支出", shop);
                PieChart.Data dd2 = new PieChart.Data("药店支出", hospital);
                PieChart.Data dd3 = new PieChart.Data("其他业务", service);
                PieChart.Data dd4 = new PieChart.Data("学费", tuition);

                ObservableList<PieChart.Data> datalist1 = FXCollections.observableArrayList();
                datalist1.add(dd1);
                datalist1.add(dd2);
                datalist1.add(dd3);
                datalist1.add(dd4);

                PieChart piechart1 = new PieChart(datalist1);
                piechart1.setLayoutX(520);
                piechart1.setLayoutY(30);
                piechart1.setPrefWidth(550);
                piechart1.setPrefHeight(450);
                piechart1.setLegendSide(Side.BOTTOM);
                piechart1.setTitle("消费分布一览");
                piechart1.setTitleSide(Side.TOP);

                piechart1.getData().forEach(new Consumer<PieChart.Data>()
                {
                    public void accept(PieChart.Data t)
                    {
                        Node node = t.getNode();
                        Tooltip tip = new Tooltip("￥"+ t.getPieValue());
                        tip.setFont(new Font(20));
                        Tooltip.install(node, tip);
                    }

                });


                Message mes12 = new Message();
                mes12.setUserId(stu.getId());
                mes12.setMessageType(MessageType.record_Query);
                Message serverResponse12 = cli.sendRequestToServer(mes12);

                ArrayList<BankRecord> BankRecordList12 = (ArrayList<BankRecord>) serverResponse12.getData();
                for(int i=0;i<BankRecordList12.size();i++)
                {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date date1 = null;
                    try {
                        date1 = sf.parse("2020-09-23 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    Date date2 = null;
                    try {
                        date2 = sf.parse("2020-09-24 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }

                    long beginMillisecond = date1.getTime();
                    long endMillisecond = date2.getTime();

                    long second = 0;
                    try {
                        second = sf.parse(BankRecordList12.get(i).getDate()).getTime();
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    if(beginMillisecond < second & second < endMillisecond)
                    {
                        System.out.println(BankRecordList12.get(i).getAmount());
                        day7 = day7 + BankRecordList12.get(i).getAmount();
                    }

                }
                System.out.println(day7);

                Message mes6 = new Message();
                mes6.setUserId(stu.getId());
                mes6.setMessageType(MessageType.record_Query);
                Message serverResponse6 = cli.sendRequestToServer(mes6);

                ArrayList<BankRecord> BankRecordList6 = (ArrayList<BankRecord>) serverResponse6.getData();
                for(int i=0;i<BankRecordList6.size();i++)
                {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date date1 = null;
                    try {
                        date1 = sf.parse("2020-09-23 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    Date date2 = null;
                    try {
                        date2 = sf.parse("2020-09-24 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }

                    long beginMillisecond = date1.getTime();
                    long endMillisecond = date2.getTime();

                    long second = 0;
                    try {
                        second = sf.parse(BankRecordList6.get(i).getDate()).getTime();
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    if(beginMillisecond < second & second < endMillisecond)
                    {
                        day6 = day6 + BankRecordList6.get(i).getAmount();
                    }

                }
                System.out.println(day6);

                Message mes7 = new Message();
                mes7.setUserId(stu.getId());
                mes7.setMessageType(MessageType.record_Query);
                Message serverResponse7 = cli.sendRequestToServer(mes7);

                ArrayList<BankRecord> BankRecordList7 = (ArrayList<BankRecord>) serverResponse7.getData();
                for(int i=0;i<BankRecordList7.size();i++)
                {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date date1 = null;
                    try {
                        date1 = sf.parse("2020-09-22 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    Date date2 = null;
                    try {
                        date2 = sf.parse("2020-09-23 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }

                    long beginMillisecond = date1.getTime();
                    long endMillisecond = date2.getTime();

                    long second = 0;
                    try {
                        second = sf.parse(BankRecordList7.get(i).getDate()).getTime();
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    if(beginMillisecond < second & second < endMillisecond)
                    {
                        day5 = day5 + BankRecordList7.get(i).getAmount();
                    }

                }
                System.out.println(day5);

                Message mes8 = new Message();
                mes8.setUserId(stu.getId());
                mes8.setMessageType(MessageType.record_Query);
                Message serverResponse8 = cli.sendRequestToServer(mes8);
                ArrayList<BankRecord> BankRecordList8 = (ArrayList<BankRecord>) serverResponse8.getData();
                for(int i=0;i<BankRecordList8.size();i++)
                {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date date1 = null;
                    try {
                        date1 = sf.parse("2020-09-21 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    Date date2 = null;
                    try {
                        date2 = sf.parse("2020-09-22 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }

                    long beginMillisecond = date1.getTime();
                    long endMillisecond = date2.getTime();

                    long second = 0;
                    try {
                        second = sf.parse(BankRecordList8.get(i).getDate()).getTime();
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    if(beginMillisecond < second & second < endMillisecond)
                    {
                        day4 = day4 + BankRecordList8.get(i).getAmount();
                    }

                }
                System.out.println(day4);

                Message mes9 = new Message();
                mes9.setUserId(stu.getId());
                mes9.setMessageType(MessageType.record_Query);
                Message serverResponse9 = cli.sendRequestToServer(mes9);
                ArrayList<BankRecord> BankRecordList9 = (ArrayList<BankRecord>) serverResponse9.getData();
                for(int i=0;i<BankRecordList9.size();i++)
                {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date date1 = null;
                    try {
                        date1 = sf.parse("2020-09-20 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    Date date2 = null;
                    try {
                        date2 = sf.parse("2020-09-21 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }

                    long beginMillisecond = date1.getTime();
                    long endMillisecond = date2.getTime();

                    long second = 0;
                    try {
                        second = sf.parse(BankRecordList9.get(i).getDate()).getTime();
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    if(beginMillisecond < second & second < endMillisecond)
                    {
                        day3 = day3 + BankRecordList9.get(i).getAmount();
                    }

                }
                System.out.println(day3);

                Message mes10 = new Message();
                mes10.setUserId(stu.getId());
                mes10.setMessageType(MessageType.record_Query);
                Message serverResponse10 = cli.sendRequestToServer(mes10);
                ArrayList<BankRecord> BankRecordList10 = (ArrayList<BankRecord>) serverResponse10.getData();
                for(int i=0;i<BankRecordList10.size();i++)
                {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date date1 = null;
                    try {
                        date1 = sf.parse("2020-09-19 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    Date date2 = null;
                    try {
                        date2 = sf.parse("2020-09-20 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }

                    long beginMillisecond = date1.getTime();
                    long endMillisecond = date2.getTime();

                    long second = 0;
                    try {
                        second = sf.parse(BankRecordList10.get(i).getDate()).getTime();
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    if(beginMillisecond < second & second < endMillisecond)
                    {
                        day2 = day2 + BankRecordList10.get(i).getAmount();
                    }

                }
                System.out.println(day2);

                Message mes11 = new Message();
                mes11.setUserId(stu.getId());
                mes11.setMessageType(MessageType.record_Query);
                Message serverResponse11 = cli.sendRequestToServer(mes10);
                ArrayList<BankRecord> BankRecordList11 = (ArrayList<BankRecord>) serverResponse10.getData();
                for(int i=0;i<BankRecordList11.size();i++)
                {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date date1 = null;
                    try {
                        date1 = sf.parse("2020-09-18 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    Date date2 = null;
                    try {
                        date2 = sf.parse("2020-09-19 23:59:59");
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }

                    long beginMillisecond = date1.getTime();
                    long endMillisecond = date2.getTime();

                    long second = 0;
                    try {
                        second = sf.parse(BankRecordList11.get(i).getDate()).getTime();
                    } catch (ParseException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    if(beginMillisecond < second & second < endMillisecond)
                    {
                        day1 = day1 + BankRecordList11.get(i).getAmount();
                    }

                }
                System.out.println(day1);

                NumberAxis x1 = new NumberAxis(0,7,5);

                NumberAxis y1 = new NumberAxis("收支金额",-500,500,100);

                LineChart<Number,Number> linechart1= new LineChart<Number,Number>(x1,y1);
                XYChart.Series<Number, Number> xy1=new XYChart.Series<Number, Number>();
                xy1.setName("一周内收支变化");

                XYChart.Data<Number, Number> dtt1 = new XYChart.Data<Number, Number>(1,day1);
                XYChart.Data<Number, Number> dtt2 = new XYChart.Data<Number, Number>(2,day2);
                XYChart.Data<Number, Number> dtt3 = new XYChart.Data<Number, Number>(3,day3);
                XYChart.Data<Number, Number> dtt4 = new XYChart.Data<Number, Number>(4,day4);
                XYChart.Data<Number, Number> dtt5 = new XYChart.Data<Number, Number>(5,day5);
                XYChart.Data<Number, Number> dtt6 = new XYChart.Data<Number, Number>(6,day6);
                XYChart.Data<Number, Number> dtt7 = new XYChart.Data<Number, Number>(7,day7);


                xy1.getData().addAll(dtt1,dtt2,dtt3,dtt4,dtt5,dtt6,dtt7);

                linechart1.setLayoutX(10);
                linechart1.setLayoutY(140);
                linechart1.setPrefHeight(400);
                linechart1.setPrefWidth(520);
                linechart1.setCreateSymbols(false);
                linechart1.setStyle("-fx-opacity:0.8;");
                linechart1.getData().add(xy1);

                ap100.getChildren().addAll(label1,label2,b2,piechart1,linechart1,b5);
                ap1.setVisible(false);
                ap100.setVisible(true);

                tuition = 0;
                wifi = 0;
                phone = 0;
                hospital = 0;
                shop =0;
                service = 0;
                day1 = 0;
                day2 = 0;
                day3 = 0;
                day4 = 0;
                day5 = 0;
                day6 = 0 ;

            }});

        tab1.setContent(fap);

























        //tab2初始界面
        VBox vbox = new VBox();
        vbox.getChildren().addAll(btest,b1,b3,b4);
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(20);

        AnchorPane ap2 = new AnchorPane();
        ap2.getChildren().addAll(vbox);

        //以下是包含流量补充包的界面ap6
        Label label5 = new Label();
        label5.setText("  您的手机号码为："+stu.getPhoneNmuber()+"\n"+"（1）业务介绍：国内流量补充包15元，"+"\n"
                + "        包含20GB国内移动数据通用流量。"+"\n"+
                "（2）资费标准：15元/次，立即生效并扣除费用。"
                +"\n"+"（3）温馨提醒：该补充包可以无限办理。");
        label5.setLayoutX(300);
        label5.setLayoutY(100);
        AnchorPane ap6 = new AnchorPane();
        label5.setFont(Font.font("sans-serf", 20));

        Image image = new Image("client/image/5G.jpg");
        ImageView iv = new ImageView(image);
        iv.setPreserveRatio(true);
        iv.setFitHeight(270);;
        iv.setFitWidth(270);
        iv.setLayoutX(15);
        iv.setLayoutY(100);
        ap6.getChildren().addAll(label5,byes,bnot,iv);
        ap6.setLayoutX(200);
        ap6.setVisible(false);

        //以下是包含校园网套餐的界面ap7
        Label label6 = new Label();
        label6.setText("（1）业务介绍：校园网用户每月可享2G免费上网流量，"+"\n"+"并享100M带宽，可同时在线4个终端。"+"\n"
                +"（2）资费标准：50元/月，立即生效并扣除费用。"
                +"\n"+"（3）温馨提醒：每月底自动到期。");
        label6.setLayoutX(300);
        label6.setLayoutY(100);
        label6.setFont(Font.font("sans-serf", 20));
        AnchorPane ap7 = new AnchorPane();

        Image image4 = new Image("client/image/wifi.jpg");
        ImageView iv4 = new ImageView(image4);
        iv4.setPreserveRatio(true);
        iv4.setFitHeight(270);;
        iv4.setFitWidth(270);
        iv4.setLayoutX(15);
        iv4.setLayoutY(100);
        ap7.getChildren().addAll(label6,byes1,bnot1,iv4);
        ap7.setLayoutX(200);
        ap7.setVisible(false);


        //以下是只含选择办理业务的界面ap3
        Label label9 = new Label();
        label9.setText("选择需要办理的业务:");
        label9.setLayoutX(100);
        label9.setLayoutY(100);
        label9.setFont(Font.font("sans-serf", 20));

        ChoiceBox<String> cb1 = new ChoiceBox<String>();
        cb1.getItems().addAll("手机流量包","校园网套餐");
        cb1.setLayoutX(300);
        cb1.setLayoutY(100);
        cb1.setPrefWidth(130);

        AnchorPane ap3 = new AnchorPane();
        ap3.getChildren().addAll(cb1,label9);
        ap3.setVisible(false);
        ap3.setLayoutX(200);

        //以下是选择一卡通充值方式与金额的界面ap5
        Label label10 = new Label();
        label10.setText("选择支付方式:");
        label10.setLayoutX(100);
        label10.setLayoutY(100);
        label10.setFont(Font.font("sans-serf", 20));

        ChoiceBox<String> cb2 = new ChoiceBox<String>();
        cb2.getItems().addAll("微信支付","支付宝支付");
        cb2.setLayoutX(300);
        cb2.setLayoutY(100);
        cb2.setPrefWidth(140);

        Label label4 = new Label();
        label4.setText("输入充值金额（元）:");
        label4.setLayoutX(100);
        label4.setLayoutY(180);
        label4.setFont(Font.font("sans-serf", 20));

        //文本区域(输入充值金额)
        TextField  tf1 = new TextField();
        tf1.setFont(Font.font(15));
        tf1.setPrefHeight(10);
        tf1.setPrefWidth(150);
        tf1.setLayoutX(300);
        tf1.setLayoutY(180);
        tf1.setTextFormatter(new TextFormatter<String>(new UnaryOperator<Change>()
        {
            public Change apply(Change t)
            {
                String value = t.getText();
                if(value.matches("[0-9]"))
                {
                    return t;
                }
                else
                    return null;
            }

        }));

        //确定支付金额按钮
        Button byes3 = new Button();
        byes3.setText("确定");
        byes3.setLayoutX(550);
        byes3.setLayoutY(330);
        byes3.setPrefHeight(50);
        byes3.setPrefWidth(150);
        byes3.setFont(Font.font("sans-serf", 15));
        byes3.setStyle("-fx-background-radius:10;");

        AnchorPane ap5 = new AnchorPane();
        ap5.getChildren().addAll(tf1,cb2,label10,label4,byes3);
        ap5.setVisible(false);
        ap5.setLayoutX(200);

//        Label label4 = new Label();
//        label4.setText("学费： 200"+"\n"+"住宿： 100"+"\n"+"共计： 300");
//        label4.setFont(Font.font("sans-serf", 20));
//        label4.setLayoutX(350);
//        label4.setLayoutY(110);

        //以下是“学费支付”的界面ap4
        Image image3 = new Image("client/image/tuition.png");
        ImageView iv3 = new ImageView(image3);
        iv3.setPreserveRatio(true);
        iv3.setFitHeight(250);;
        iv3.setFitWidth(400);
        iv3.setLayoutX(320);
        iv3.setLayoutY(100);

        AnchorPane ap4 = new AnchorPane();
        ap4.getChildren().addAll(iv3,byes2,bnot2);
        ap4.setVisible(false);
        ap4.setLayoutX(200);

        //以下是支付成功的界面ap8
        Image image2 = new Image("client/image/bank_PaySuccess.jpg");
        ImageView iv2 = new ImageView(image2);
        iv2.setPreserveRatio(true);
        iv2.setFitHeight(180);;
        iv2.setFitWidth(180);
        iv2.setLayoutX(150);
        iv2.setLayoutY(150);

        Label label7 = new Label();
        label7.setText("支付成功！");
        label7.setFont(Font.font("sans-serf", 40));
        label7.setLayoutX(360);
        label7.setLayoutY(200);

        AnchorPane ap8 = new AnchorPane();
        ap8.getChildren().addAll(label7,iv2);
        ap8.setVisible(false);
        ap8.setLayoutX(200);

        //二维码界面
        Image image1 = new Image("client/image/bank_QRCode.jpg");
        ImageView iv1 = new ImageView(image1);
        iv1.setPreserveRatio(true);
        iv1.setFitHeight(250);;
        iv1.setFitWidth(250);
        iv1.setLayoutX(200);
        iv1.setLayoutY(150);

        //二维码完成支付
        Button byes4 = new Button();
        byes4.setText("已完成支付");
        byes4.setLayoutX(550);
        byes4.setLayoutY(350);
        byes4.setPrefHeight(50);
        byes4.setPrefWidth(150);
        byes4.setFont(Font.font("sans-serf", 15));
        byes4.setStyle("-fx-background-radius:10;");

        //返回选择一卡通充值方式与金额的界面ap5
        Button bnot4 = new Button();
        bnot4.setText("返回上一步");
        bnot4.setLayoutX(750);
        bnot4.setLayoutY(350);
        bnot4.setPrefHeight(50);
        bnot4.setPrefWidth(150);
        bnot4.setFont(Font.font("sans-serf", 15));
        bnot4.setStyle("-fx-background-radius:10;");

        Label label8 = new Label();
        label8.setText("扫描下方二维码进行支付：");
        label8.setFont(Font.font("sans-serf", 20));
        label8.setLayoutX(200);
        label8.setLayoutY(100);

        AnchorPane ap9 = new AnchorPane();
        ap9.getChildren().addAll(label8,iv1,byes4,bnot4);
        ap9.setVisible(false);
        ap9.setLayoutX(200);

        bnot.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                System.out.println("取消");
                ap8.setVisible(false);
                ap3.setVisible(false);
                ap4.setVisible(false);
                ap5.setVisible(false);
                ap6.setVisible(false);
                ap7.setVisible(false);
                ap9.setVisible(false);

            }
        });

        bnot1.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                System.out.println("取消");
                ap8.setVisible(false);
                ap3.setVisible(false);
                ap4.setVisible(false);
                ap5.setVisible(false);
                ap6.setVisible(false);
                ap7.setVisible(false);
                ap9.setVisible(false);

            }
        });

        bnot2.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                System.out.println("取消");
                ap8.setVisible(false);
                ap3.setVisible(false);
                ap4.setVisible(false);
                ap5.setVisible(false);
                ap6.setVisible(false);
                ap7.setVisible(false);
                ap9.setVisible(false);

            }
        });

        bnot4.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                System.out.println("取消");
                ap8.setVisible(false);
                ap3.setVisible(false);
                ap4.setVisible(false);
                ap5.setVisible(true);
                ap6.setVisible(false);
                ap7.setVisible(false);
                ap9.setVisible(false);

            }
        });



        //支付手机流量包响应函数
        byes.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                BankRecord tempRecord = new BankRecord();
                tempRecord.setuID(stu.getId());
                tempRecord.setAmount(-15);
                tempRecord.setRecord("支付手机流量包");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tempRecord.setDate(formatter.format(new Date()).toString());
                Message mes2 = new Message();
                mes2.setUserId(stu.getId());
                mes2.setData(tempRecord);
                mes2.setMessageType(MessageType.phone_Pay);
                Message serverResponse2 = cli.sendRequestToServer(mes2);

                if (serverResponse2.isLastOperState() == false){
                    //弹窗：
                    ap8.setVisible(false);
                    ap3.setVisible(true);
                    ap4.setVisible(false);
                    ap5.setVisible(false);
                    ap6.setVisible(false);
                    ap7.setVisible(false);
                    ap9.setVisible(false);
                    JFXDialogLayout layout_suc = new JFXDialogLayout();
                    layout_suc.setBody(new Label("支付失败：" + serverResponse2.getErrorMessage()));
                    JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) byes.getScene().getWindow());
                    alert_suc.setOverlayClose(true);
                    alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_suc.setContent(layout_suc);
                    alert_suc.initModality(Modality.NONE);
                    alert_suc.show();
                }
                else {
                    System.out.println("支付");
                    ap8.setVisible(true);
                    ap3.setVisible(false);
                    ap4.setVisible(false);
                    ap5.setVisible(false);
                    ap6.setVisible(false);
                    ap7.setVisible(false);
                    ap9.setVisible(false);
                }
            }
        });

        //支付校园网套餐响应函数
        byes1.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                BankRecord tempRecord = new BankRecord();
                tempRecord.setuID(stu.getId());
                tempRecord.setAmount(-50);
                tempRecord.setRecord("支付校园网套餐");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tempRecord.setDate(formatter.format(new Date()).toString());
                Message mes2 = new Message();
                mes2.setUserId(stu.getId());
                mes2.setData(tempRecord);
                mes2.setMessageType(MessageType.wifi_Pay);
                Message serverResponse2 = cli.sendRequestToServer(mes2);
                if (serverResponse2.isLastOperState() == false){
                    //弹窗：
                    ap8.setVisible(false);
                    ap3.setVisible(true);
                    ap4.setVisible(false);
                    ap5.setVisible(false);
                    ap6.setVisible(false);
                    ap7.setVisible(false);
                    ap9.setVisible(false);
                    JFXDialogLayout layout_suc = new JFXDialogLayout();
                    layout_suc.setBody(new Label("支付失败：" + serverResponse2.getErrorMessage()));
                    JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) byes.getScene().getWindow());
                    alert_suc.setOverlayClose(true);
                    alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_suc.setContent(layout_suc);
                    alert_suc.initModality(Modality.NONE);
                    alert_suc.show();
                }
                else {
                    System.out.println("支付");
                    ap8.setVisible(true);
                    ap3.setVisible(false);
                    ap4.setVisible(false);
                    ap5.setVisible(false);
                    ap6.setVisible(false);
                    ap7.setVisible(false);
                    ap9.setVisible(false);
                }
            }
        });

        //支付学费响应函数
        byes2.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                BankRecord tempRecord3 = new BankRecord();
                tempRecord3.setuID(stu.getId());
                tempRecord3.setAmount(-300);
                tempRecord3.setRecord("缴纳学费");
                SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tempRecord3.setDate(formatter3.format(new Date()).toString());
                Message mes4 = new Message();
                mes4.setUserId(stu.getId());
                mes4.setData(tempRecord3);
                mes4.setMessageType(MessageType.tuition_Pay);
                Message serverResponse4 = cli.sendRequestToServer(mes4);

                if (serverResponse4.isLastOperState() == false){
                    //弹窗：
                    ap8.setVisible(false);
                    ap3.setVisible(true);
                    ap4.setVisible(false);
                    ap5.setVisible(false);
                    ap6.setVisible(false);
                    ap7.setVisible(false);
                    ap9.setVisible(false);
                    JFXDialogLayout layout_suc = new JFXDialogLayout();
                    layout_suc.setBody(new Label("支付失败：" + serverResponse4.getErrorMessage()));
                    JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) byes.getScene().getWindow());
                    alert_suc.setOverlayClose(true);
                    alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_suc.setContent(layout_suc);
                    alert_suc.initModality(Modality.NONE);
                    alert_suc.show();
                }
                else {
                    b4.setOnAction(new EventHandler<ActionEvent>()
                    {
                        public void handle(ActionEvent event)
                        {
                            ap8.setVisible(true);
                            ap3.setVisible(false);
                            ap4.setVisible(false);
                            ap5.setVisible(false);
                            ap6.setVisible(false);
                            ap7.setVisible(false);
                            ap9.setVisible(false);
                        }
                    });

                    System.out.println("支付");
                    ap8.setVisible(true);
                    ap3.setVisible(false);
                    ap4.setVisible(false);
                    ap5.setVisible(false);
                    ap6.setVisible(false);
                    ap7.setVisible(false);
                    ap9.setVisible(false);
                }
            }
        });

        //跳转二维码界面
        byes3.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                tf1.getText();
                System.out.println(tf1.getText());
                ap3.setVisible(false);
                ap4.setVisible(false);
                ap5.setVisible(false);
                ap6.setVisible(false);
                ap7.setVisible(false);
                ap8.setVisible(false);
                ap9.setVisible(true);
            }

        });

        //跳转支付成功界面
        byes4.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                BankRecord tempRecord = new BankRecord();
                tempRecord.setuID(stu.getId());
                tempRecord.setAmount(Integer.valueOf(tf1.getText()));
                tempRecord.setRecord("一卡通充值");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tempRecord.setDate(formatter.format(new Date()).toString());
                Message mes3 = new Message();
                mes3.setUserId(stu.getId());
                mes3.setData(tempRecord);
                mes3.setMessageType(MessageType.card_Invest);
                mes3.setExtraMessage(tf1.getText());
                Message serverResponse2 = cli.sendRequestToServer(mes3);

                ap8.setVisible(true);
                ap3.setVisible(false);
                ap4.setVisible(false);
                ap5.setVisible(false);
                ap6.setVisible(false);
                ap7.setVisible(false);
                ap9.setVisible(false);
            }

        });

        cb1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {
            //下拉选择
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                if(newValue == "手机流量包" )
                {
                    ap3.setVisible(false);
                    ap4.setVisible(false);
                    ap5.setVisible(false);
                    ap6.setVisible(true);
                    ap7.setVisible(false);
                    ap8.setVisible(false);
                    ap9.setVisible(false);
                }
                else if(newValue == "校园网套餐")
                {
                    ap3.setVisible(false);
                    ap4.setVisible(false);
                    ap5.setVisible(false);
                    ap6.setVisible(false);
                    ap7.setVisible(true);
                    ap8.setVisible(false);
                    ap9.setVisible(false);
                }
            }
        });


        b1.setOnAction(new EventHandler<ActionEvent>()
        {
            //充值
            public void handle(ActionEvent event)
            {
                ap3.setVisible(false);
                ap4.setVisible(false);
                ap5.setVisible(true);
                ap6.setVisible(false);
                ap7.setVisible(false);
                ap8.setVisible(false);
                ap9.setVisible(false);
            }

        });

        b3.setOnAction(new EventHandler<ActionEvent>()
        {
            //校园网
            public void handle(ActionEvent event)
            {
                ap3.setVisible(true);
                ap4.setVisible(false);
                ap5.setVisible(false);
                ap6.setVisible(false);
                ap7.setVisible(false);
                ap8.setVisible(false);
                ap9.setVisible(false);
            }

        });

        b4.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                //学费
                ap3.setVisible(false);
                ap4.setVisible(true);
                ap5.setVisible(false);
                ap6.setVisible(false);
                ap7.setVisible(false);
                ap8.setVisible(false);
                ap9.setVisible(false);
            }

        });


        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(ap2,ap3,ap4,ap5,ap6,ap7,ap8,ap9);

        tab2.setContent(ap);
        tab2.setClosable(false);

        //tabpane.getTabs().addAll(tab1,tab2);

        root.getChildren().add(bankTab);

        return root;
    }

}
