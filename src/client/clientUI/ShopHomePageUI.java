package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import util.MessageType;
import vo.*;

import java.io.*;
import java.security.cert.CertificateNotYetValidException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;

public class ShopHomePageUI {
    private Student stu = new Student();
    private double Total;
    ArrayList<tempShop> shop_AllGoods = new ArrayList<tempShop>();//初始化-所有商品
//    ArrayList<tempShop> shop_food = new ArrayList<tempShop>();//食品饮料
//    ArrayList<tempShop> shop_daily = new ArrayList<tempShop>();//生活用品
//    ArrayList<tempShop> shop_elec = new ArrayList<tempShop>();//电子产品
//    ArrayList<tempShop> shop_book = new ArrayList<tempShop>();//图书文具
//    ArrayList<tempShop> shop_sport = new ArrayList<tempShop>();//运动健康
    ArrayList<tempShop> myCart = new ArrayList<tempShop>();//购物车
    ArrayList<tempShop> myshop = new ArrayList<tempShop>();//我的商铺
    double renown = 0;
    DecimalFormat df = new DecimalFormat("#.00");

    //星星蒙版
    Image img1 = new Image("client/Image/starMask.png",450, 200,true,true);
    ImageView starMask = new ImageView(img1);

    Image img2 = new Image("client/Image/stars.png",150, 150,true,true);
    ImageView stars = new ImageView(img2);

    private void savePic(InputStream inputStream, String fileName) {
      OutputStream os = null;
        try {
            File root2=new File(".");//获得当前文件夹（即工程目录），结果：
            String rootDir2=root2.getCanonicalPath();
            System.out.println("当前工程所在文件夹："+rootDir2);

            String path = "./src/client/Image/";// 保存路径名
            File tempFile = new File(path);
//            if (!tempFile.exists()) {
//                tempFile.mkdirs();
//            }

            byte[] bs = new byte[1024];// 1K的数据缓冲
            int len;// 读取到的数据长度

            // 输出的文件流保存到本地文件
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);

            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //确认窗口
    private void showConfirmation(JFXButton score,ShopRecord shopRecord)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setContentText("\n\n\n\n\n\n   确定要给卖家"+ shopRecord.getdsID() + "\n   评分"+ df.format(renown) +"吗？");
        alert.setGraphic(stars);

        Optional result = alert.showAndWait();

        //点击确定
        if (result.get() == ButtonType.OK) {
            Message sendToServer5 = new Message();
            Client client5 = new Client();
            sendToServer5.setMessageType(MessageType.Score);
            sendToServer5.setExtraMessage(String.valueOf(renown));
            sendToServer5.setData(shopRecord);
            Message serverResponse5 = client5.sendRequestToServer(sendToServer5);


            JFXDialogLayout layout_suc = new JFXDialogLayout();
            layout_suc.setBody(new Label("      打分成功"));
            JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) score.getScene().getWindow());
            alert_suc.setOverlayClose(true);
            alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
            alert_suc.setContent(layout_suc);
            alert_suc.initModality(Modality.NONE);
            alert_suc.show();

        } else {
            //info.setText(“btn6 点击了取消”);
            // alert.hide();
        }
    }


    //评分计算函数：str是直接getRenown的返回值
    private String renownCalculate(String str){
        String[] renownSplit = str.split("/");
        Double a = Double.valueOf(renownSplit[0]);
        Double b = Double.valueOf(renownSplit[1]);
        DecimalFormat df = new DecimalFormat("0.00");//格式
        String renown = df.format((double)a/b);
        return renown;
    }


    //打窗格函数
    private void addGoods(tempShop product, FlowPane ishop, Image image){
        AnchorPane good1=new AnchorPane();
        ishop.getChildren().add(good1);
        good1.setPrefSize(300,300);

        Image goodBackground = new Image("/client/Image/goodBackground.png",360, 300,false,true);
        ImageView good_bg = new ImageView(goodBackground);
        good1.getChildren().add(good_bg);

        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(40);
        imageView.setLayoutY(40);

        Label name=new Label(product.getgName());
        name.setLayoutX(230);
        name.setLayoutY(30);

        Label price=new Label(product.getgPrice());
        price.setLayoutX(230);
        price.setLayoutY(70);

        Label Yuan=new Label("元");
        Yuan.setLayoutX(260);
        Yuan.setLayoutY(70);

        Label ins=new Label(product.getgIntroduction());
        ins.setLayoutX(230);
        ins.setLayoutY(110);

        Label sto=new Label(product.getgStore());
        sto.setLayoutX(270);
        sto.setLayoutY(150);

        Label stor=new Label("库存：");
        stor.setLayoutX(230);
        stor.setLayoutY(150);

        good1.getChildren().addAll(imageView, name,ins,price,Yuan,sto,stor);
    }

    public ShopHomePageUI(Student student){
        this.stu = student;
    }

    public AnchorPane shopPage(){

        //tab1
        Tab tab1=new Tab("    商品     ");
        tab1.setClosable(false);

        AnchorPane ap1=new AnchorPane();
        TextField text=new TextField();//新建查询文本框
        text.setPrefSize(700,40);
        text.setLayoutX(120);
        text.setLayoutY(20);
        text.setFont(Font.font(20));//字体大小
        text.setPromptText("搜索商品");
        text.setFocusTraversable(false);
        ap1.getChildren().add(text);

        Button b=new Button("查询");//最好能用图标
        b.setPrefSize(100, 40);
        b.setLayoutX(830);
        b.setLayoutY(20);
        b.setFont(Font.font("KaiTi", FontWeight.EXTRA_BOLD,20));
        b.setTextFill(Paint.valueOf("#FFFFFF"));//设置按钮形状，颜色
        BackgroundFill bgf=new BackgroundFill(Paint.valueOf("#50A1F0"),new CornerRadii(20),new Insets(0));
        Background bg= new Background(bgf);
        b.setBackground(bg);//改变按钮形状
        ap1.getChildren().add(b);

        RadioButton r1=new RadioButton("全部商品");
        RadioButton r2=new RadioButton("食品饮料");
        RadioButton r3=new RadioButton("生活用品");
        RadioButton r4=new RadioButton("电子产品");
        RadioButton r5=new RadioButton("图书文具");
        RadioButton r6=new RadioButton("运动健康");
        ToggleGroup tg=new ToggleGroup();
        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);
        r3.setToggleGroup(tg);
        r4.setToggleGroup(tg);
        r5.setToggleGroup(tg);
        r6.setToggleGroup(tg);
        r1.setFont(Font.font(15));
        r2.setFont(Font.font(15));
        r3.setFont(Font.font(15));
        r4.setFont(Font.font(15));
        r5.setFont(Font.font(15));
        r6.setFont(Font.font(15));
        HBox hb=new HBox(40);
        hb.setLayoutX(120);
        hb.setLayoutY(80);
        hb.getChildren().addAll(r1,r2,r3,r4,r5,r6);
        tg.selectToggle(r1);
        ap1.getChildren().add(hb);
        tab1.setContent(ap1);

        ScrollPane scr1=new ScrollPane();
        scr1.setLayoutX(120);
        scr1.setLayoutY(120);
        scr1.setPrefSize(800, 380);
        scr1.setStyle("-fx-background-color:#426ab3");
        FlowPane fp=new FlowPane();
        scr1.setContent(fp);
        fp.setPrefWidth(780);
        fp.setHgap(25);
        fp.setVgap(20);

        Button add_cart=new Button("加入购物车");
        add_cart.setLayoutX(940);
        add_cart.setLayoutY(480);

        Button refresh=new Button("刷新");//用图标？？？
        add_cart.setLayoutX(940);
        add_cart.setLayoutY(480);

        ap1.getChildren().addAll(scr1,add_cart);















//tab2
        Tab tab2=new Tab("   购物车   ");
        tab2.setClosable(false);
        AnchorPane ap2=new AnchorPane();
        tab2.setContent(ap2);
        ScrollPane scr2=new ScrollPane();
        scr2.setLayoutX(100);
        scr2.setLayoutY(50);
        scr2.setPrefSize(818, 380);
        scr2.setStyle("-fx-background-color:#426ab3");
        ap2.getChildren().add(scr2);

        Button button=new Button("结算");
        TextField total=new TextField();


        FlowPane cartfp=new FlowPane();
        cartfp.setPrefWidth(780);
        cartfp.setHgap(40);

        Label lab=new Label("总计：");
        lab.setFont(Font.font(15));
        lab.setLayoutX(660);
        lab.setLayoutY(460);
        total.setLayoutX(710);
        total.setLayoutY(460);
        button.setLayoutX(890);
        button.setLayoutY(460);
        ap2.getChildren().addAll(button,total,lab);










//tab3
        Tab tab3=new Tab(" 我的商铺 ");
        tab3.setClosable(false);
        AnchorPane ap3=new AnchorPane();
        tab3.setContent(ap3);

        Button add_button=new Button("+ 添加商品");
        add_button.setFont(Font.font(50));
        add_button.setTextFill(Paint.valueOf("#50A1F0"));
        add_button.setLayoutX(100);
        add_button.setLayoutY(50);
        add_button.setPrefHeight(70);
        add_button.setPrefWidth(858);

        BackgroundFill bgf1=new BackgroundFill(Paint.valueOf("#FFFFFF"),new CornerRadii(0),new Insets(6));
        Background bg1= new Background(bgf1);
        add_button.setBackground(bg1);//改变按钮形状
        BorderStroke bos=new BorderStroke(Paint.valueOf("#50A1F0"),BorderStrokeStyle.DASHED,new CornerRadii(0),new BorderWidths(2));
        Border bo=new Border(bos);
        add_button.setBorder(bo);
        ap3.getChildren().add(add_button);

        ScrollPane scr3=new ScrollPane();
        scr3.setLayoutX(100);
        scr3.setLayoutY(180);
        scr3.setPrefSize(858, 300);
        scr3.setStyle("-fx-background-color:#426ab3");
        FlowPane ishop=new FlowPane();
        ishop.setPrefWidth(780);
        ishop.setHgap(60);
        scr3.setContent(ishop);
        ap3.getChildren().add(scr3);















//tab4
        Tab tab4=new Tab(" 购买记录 ");
        tab4.setClosable(false);
        AnchorPane ap4=new AnchorPane();
        tab4.setContent(ap4);



        Message sendToServer1 = new Message();
        Client client1 = new Client();
        sendToServer1.setMessageType(MessageType.PurchaseRecordQuery);
        sendToServer1.setExtraMessage(stu.getId());

        Message serverResponse1 = client1.sendRequestToServer(sendToServer1);

        ArrayList<ShopRecord> recarr=(ArrayList<ShopRecord>) serverResponse1.getData();

        ObservableList<ShopRecord> list= FXCollections.observableArrayList();
        if(recarr != null){
            for(int i=0;i<recarr.size();i++) {
                list.add(recarr.get(i));
            }
        }

        TableView<ShopRecord> recview=new TableView<ShopRecord>(list);
        recview.setLayoutX(100);
        recview.setLayoutY(50);
        recview.setPrefSize(800, 400);

//记录表
        TableColumn<ShopRecord,String> rec_name=new TableColumn<ShopRecord,String>("名称");
        rec_name.setPrefWidth(200);
        TableColumn<ShopRecord,String> rec_sID=new TableColumn<ShopRecord,String>("卖家");
        rec_sID.setPrefWidth(200);
        TableColumn<ShopRecord,String> rec_date=new TableColumn<ShopRecord,String>("时间");
        rec_date.setPrefWidth(200);
        TableColumn<ShopRecord,String> rec_num=new TableColumn<ShopRecord,String>("购买数量");
        rec_num.setPrefWidth(200);
        recview.getColumns().addAll(rec_name,rec_sID,rec_date,rec_num);

        rec_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShopRecord,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(CellDataFeatures<ShopRecord, String> param) {
                SimpleStringProperty name=new SimpleStringProperty(param.getValue().getdName());
                return name;
            }

        });
        rec_sID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShopRecord,String>,ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(CellDataFeatures<ShopRecord, String> param) {
                SimpleStringProperty sID=new SimpleStringProperty(param.getValue().getdsID());
                return sID;
            }

        });
        rec_date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShopRecord,String>,ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(CellDataFeatures<ShopRecord, String> param) {
                SimpleStringProperty date=new SimpleStringProperty(param.getValue().getdDate());
                return date;
            }

        });
        rec_num.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShopRecord,String>,ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(CellDataFeatures<ShopRecord, String> param) {
                SimpleStringProperty num=new SimpleStringProperty(param.getValue().getdNum());
                return num;
            }

        });

        rec_sID.setCellFactory(new Callback<TableColumn<ShopRecord,String>,TableCell<ShopRecord,String>>(){

            @Override
            public TableCell<ShopRecord, String> call(TableColumn<ShopRecord, String> arg0) {
                TableCell<ShopRecord, String> cell=new TableCell<ShopRecord,String>(){
                    protected void updateItem(String item,boolean empty) {
                        super.updateItem(item, empty);
                        if(empty==false&&item!=null){
                            StackPane stackPane = new StackPane();
                            stackPane.setAlignment(Pos.CENTER);
                            JFXButton score=new JFXButton(item);
                            stackPane.getChildren().add(score);
                            this.setGraphic(stackPane);
                            ShopRecord shopRecord = list.get(getIndex());
                            if(shopRecord.isRenown()==true) score.setDisable(false);

                            score.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent arg0) {
                                    Stage st=new Stage();
                                    st.setWidth(450);
                                    st.setHeight(140);
                                    st.setTitle("给卖家评分");

                                    StackPane sap=new StackPane();
                                    AnchorPane anchorPane = new AnchorPane();

                                    Label starColor = new Label();
                                    starColor.setPrefHeight(140);
                                    starColor.setPrefWidth(1);
                                    starColor.setLayoutX(0);
                                    starColor.setLayoutY(0);
                                    starColor.setStyle("-fx-background-color: YELLOW");

                                    anchorPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event) {
                                            System.out.println(event.getX() + " " + event.getY());
                                            starColor.setPrefWidth(event.getX());
                                        }
                                    });

                                    anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event) {
                                            System.out.println(event.getX() + " " + 5* event.getX()/350.0);
                                            if(5* event.getX()/350.0 > 5) renown = 5;
                                            else renown = 5* event.getX()/350.0;
                                            System.out.println(renown);

                                            //获取list列表中的位置，进而获取列表对应的信息数据
                                            ShopRecord shopRecord = list.get(getIndex());

                                            if(shopRecord.isRenown() == true)
                                            {
                                                JFXDialogLayout layout_f = new JFXDialogLayout();
                                                layout_f.setBody(new Label("      已经提交过打分啦~"));
                                                JFXAlert<Void> alert_f = new JFXAlert<>((Stage) score.getScene().getWindow());
                                                alert_f.setOverlayClose(true);
                                                alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                                                alert_f.setContent(layout_f);
                                                alert_f.initModality(Modality.NONE);
                                                alert_f.show();

                                            }
                                            else {
                                                showConfirmation(score,shopRecord);
                                            }

                                            st.close();
                                        }
                                    });

                                    anchorPane.setPrefSize(250,100);
                                    anchorPane.getChildren().add(starColor);
                                    sap.getChildren().add(anchorPane);

                                    sap.getChildren().add(starMask);
                                    sap.setAlignment(Pos.CENTER);
                                    Scene scsc=new Scene(sap);

//                                    HBox hbs=new HBox(5);
//                                    Button s1=new Button();
//                                    Button s2=new Button();
//                                    Button s3=new Button();
//                                    Button s4=new Button();
//                                    Button s5=new Button();
//                                    hbs.getChildren().addAll(s1,s2,s3,s4,s5);

//                                    JFXButton con=new JFXButton("确认");
//                                    con.setLayoutX(45);
//                                    con.setLayoutY(35);
//
//                                    sap.getChildren().addAll(con);
//                                    sap.setAlignment(con, Pos.BASELINE_RIGHT);

                                    st.setScene(scsc);

                                    st.show();
                                }
                            });
                        }
                    }
                };
                return cell;
            }

        });
        ap4.getChildren().add(recview);












//-----初始界面-----

        Message sendToServer = new Message();
        Client client = new Client();
        sendToServer.setMessageType(MessageType.shopInit);
        Message serverResponse = client.sendRequestToServer(sendToServer);
        ArrayList<Goods> goodarr=new ArrayList<Goods>();
        goodarr=(ArrayList<Goods>) serverResponse.getData();
        int size=goodarr.size();
        for(int a=0;a<size;a++) {

            tempShop tempshop=new tempShop();
            tempshop.setgName(goodarr.get(a).getgName());
            tempshop.setgPrice(goodarr.get(a).getgPrice());
            tempshop.setgTag(goodarr.get(a).getgTag());
            tempshop.setgIntroduction(goodarr.get(a).getgIntroduction());
            tempshop.setgStore(goodarr.get(a).getgStore());
            tempshop.setgsID(goodarr.get(a).getgsID());
            tempshop.setgAddNum(0);
            //自己卖的东西不会显示在可购买的商品列表里
            if(goodarr.get(a).getgsID().equals(stu.getId())==false) {
                shop_AllGoods.add(tempshop);
                myCart.add(tempshop);//这个逻辑是，让购物车中的物品跟所有商品保持一致，然后通过“购买数量”来决定在结算时要购买哪些商品
            }
            else myshop.add(tempshop);

        }

        for(int i=0;i<shop_AllGoods.size();i++) {
            AnchorPane good1=new AnchorPane();
            fp.getChildren().add(good1);
            good1.setPrefSize(300,300);

            Image goodBackground = new Image("/client/Image/goodBackground.png",360, 300,false,true);
            ImageView good_bg = new ImageView(goodBackground);
            good1.getChildren().add(good_bg);

//    		FileInputStream file=null;
//    		ImageView iv = null;
//			try {
//				file = new FileInputStream(new File("C:/Users/25751/Desktop/waitforupload.jpg"));
//				Image ima=new Image(file,200,250,false,true);
//				iv=new ImageView(ima);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
            ImageView imageView = new ImageView(shop_AllGoods.get(i).getgPic());
            imageView.setLayoutX(40);
            imageView.setLayoutY(40);

            Button minus=new Button("-");
            minus.setLayoutX(230);
            minus.setLayoutY(180);

            Button plus=new Button("+");
            plus.setLayoutX(300);
            plus.setLayoutY(180);

            TextField num1=new TextField(String.valueOf(shop_AllGoods.get(i).getgAddNum()));
            num1.setPrefSize(40, 10);
            num1.setLayoutX(255);
            num1.setLayoutY(180);

            Label name=new Label(shop_AllGoods.get(i).getgName());
            name.setLayoutX(230);
            name.setLayoutY(30);

            Label price=new Label(shop_AllGoods.get(i).getgPrice());
            price.setLayoutX(230);
            price.setLayoutY(70);

            Label Yuan=new Label("元");
            Yuan.setLayoutX(260);
            Yuan.setLayoutY(70);

            Label ins=new Label(shop_AllGoods.get(i).getgIntroduction());
            ins.setLayoutX(230);
            ins.setLayoutY(110);

            String strR = "";
            Message sendToServer6 = new Message();
            Client client6 = new Client();
            //卖家是商人还是学生
            if(shop_AllGoods.get(i).getgsID().contains("bz")) {
                sendToServer6.setMessageType(MessageType.shop_queryRetailer);
                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                System.out.println(shop_AllGoods.get(i).getgsID());
                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                Retailer retailer = new Retailer();
                if(serverResponse6.getData() != null) retailer = (Retailer) serverResponse6.getData();
                strR = retailer.getRenown();
            }
            else {
                sendToServer6.setMessageType(MessageType.shop_queryStudent);
                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                System.out.println(shop_AllGoods.get(i).getgsID());
                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                Student sss = new Student();
                if(serverResponse6.getData() != null) sss = (Student) serverResponse6.getData();
                strR = sss.getRenown();
            }


            Label score=new Label("卖家信誉：" + renownCalculate(strR));
            score.setLayoutX(230);
            score.setLayoutY(150);

            good1.getChildren().addAll(imageView, minus,plus,num1,name,ins,score,price,Yuan);

            int j=i;
            plus.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    int n= myCart.get(j).getgAddNum();
                    n++;
                    myCart.get(j).setgAddNum(n);
                    num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                }
            });
            minus.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    int n=myCart.get(j).getgAddNum();
                    if(n>0) {
                        n--;
                        myCart.get(j).setgAddNum(n);
                        num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                    }else
                    {
                        myCart.get(j).setgAddNum(0);
                        num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                    }

                }
            });

        }

        for(int i=0;i<myshop.size();i++) {
            AnchorPane good1=new AnchorPane();
            ishop.getChildren().add(good1);
            good1.setPrefSize(300,300);

            Image goodBackground = new Image("/client/Image/goodBackground.png",360, 300,false,true);
            ImageView good_bg = new ImageView(goodBackground);
            good1.getChildren().add(good_bg);

//	FileInputStream file=null;
//	ImageView iv = null;
//	try {
//		file = new FileInputStream(new File("C:/Users/25751/Desktop/waitforupload.jpg"));
//		Image ima=new Image(file,200,250,false,true);
//		iv=new ImageView(ima);
//	} catch (FileNotFoundException e) {
//		e.printStackTrace();
//	}
            ImageView imageView = new ImageView(myshop.get(i).getgPic());
            imageView.setLayoutX(40);
            imageView.setLayoutY(40);

            Label name=new Label(myshop.get(i).getgName());
            name.setLayoutX(230);
            name.setLayoutY(30);

            Label price=new Label(myshop.get(i).getgPrice());
            price.setLayoutX(230);
            price.setLayoutY(70);

            Label Yuan=new Label("元");
            Yuan.setLayoutX(260);
            Yuan.setLayoutY(70);

            Label ins=new Label(myshop.get(i).getgIntroduction());
            ins.setLayoutX(230);
            ins.setLayoutY(110);

            Label sto=new Label(myshop.get(i).getgStore());
            sto.setLayoutX(270);
            sto.setLayoutY(150);

            Label stor=new Label("库存：");
            stor.setLayoutX(230);
            stor.setLayoutY(150);

            good1.getChildren().addAll(imageView, name,ins,price,Yuan,sto,stor);



        }


//添加商品
        add_button.setOnAction( new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {

                Stage addP=new Stage();
                addP.setTitle("添加商品");
                addP.setHeight(400);
                addP.setWidth(350);
                addP.show();
                AnchorPane addap=new AnchorPane();

                Label name=new Label("名称：");
                name.setFont(Font.font(20));
                name.setLayoutX(60);
                name.setLayoutY(40);

                TextField gname=new TextField();
                gname.setPrefSize(120,20);
                gname.setLayoutX(140);
                gname.setLayoutY(40);
                gname.setFont(Font.font(15));//字体大小
                gname.setPromptText("添加商品");
                gname.setFocusTraversable(false);


                Label price=new Label("价格：");
                price.setFont(Font.font(20));
                price.setLayoutX(60);
                price.setLayoutY(80);

                TextField gprice=new TextField();
                gprice.setPrefSize(120,20);
                gprice.setLayoutX(140);
                gprice.setLayoutY(80);
                gprice.setFont(Font.font(15));
                gprice.setPromptText("设置价格");
                gprice.setFocusTraversable(false);

                Label Tag=new Label("标签：");
                Tag.setFont(Font.font(20));
                Tag.setLayoutX(60);
                Tag.setLayoutY(120);

                ComboBox<String> gTag=new ComboBox<String>();
                gTag.getItems().addAll("食品饮料","生活用品","电子产品","图书文具","运动健康");
                gTag.setLayoutX(140);
                gTag.setLayoutY(120);
                gTag.setPromptText("设置标签        ");
                gTag.setVisibleRowCount(3);

                Label intro=new Label("描述：");
                intro.setFont(Font.font(20));
                intro.setLayoutX(60);
                intro.setLayoutY(160);

                TextField gintro=new TextField();
                gintro.setPrefSize(120,20);
                gintro.setLayoutX(140);
                gintro.setLayoutY(160);
                gintro.setFont(Font.font(15));
                gintro.setPromptText("输入商品描述");
                gintro.setFocusTraversable(false);

                Label store=new Label("数量：");
                store.setFont(Font.font(20));
                store.setLayoutX(60);
                store.setLayoutY(200);

                TextField gstore=new TextField();
                gstore.setPrefSize(120,20);
                gstore.setLayoutX(140);
                gstore.setLayoutY(200);
                gstore.setFont(Font.font(15));
                gstore.setPromptText("输入商品数量");
                gstore.setFocusTraversable(false);

                Button OK=new Button("确定");
                Button cancel=new Button("取消");
                OK.setLayoutX(220);
                OK.setLayoutY(320);
                cancel.setLayoutX(280);
                cancel.setLayoutY(320);

                Label picture=new Label("图片：");
                picture.setFont(Font.font(20));
                picture.setLayoutX(60);
                picture.setLayoutY(240);

                Button pic=new Button("上传图片");
                pic.setPrefSize(80, 60);
                pic.setLayoutX(140);
                pic.setLayoutY(240);
                pic.setStyle("-fx-background-color:#FFFFFF");

                final Image[] image = new Image[1];
                final File[] file = new File[1];

                pic.setOnAction(new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent event) {
                        FileChooser fileChooser = new FileChooser();
                        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.jpg");
                        fileChooser.getExtensionFilters().add(pngFilter);
                        file[0] = fileChooser.showOpenDialog(addP);

                        image[0] = new Image(file[0].toURI().toString(),180, 220, false, true);

                    }

                });

                pic.setBorder(bo);

                addap.getChildren().addAll(name,gname,price,gprice,intro,gintro,store,gstore,OK,cancel,Tag,gTag,picture,pic);
                Scene addsc=new Scene(addap);
                addP.setScene(addsc);


                OK.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        Goods good = new Goods();
                        good.setgName(gname.getText());
                        good.setgPrice(gprice.getText());
                        good.setgTag(gTag.getValue());
                        good.setgIntroduction(gintro.getText());
                        good.setgStore(gstore.getText());
                        good.setgsID(stu.getId());
                        Message sendToServer3=new Message();
                        Client client3 = new Client();
                        sendToServer3.setMessageType(MessageType.add_product);
                        sendToServer3.setData(good);
                        System.out.println(good.getgName() + " " + good.getgPrice() + " " + good.getgIntroduction());
                        Message serverResponse3= client3.sendRequestToServer(sendToServer3);

                        if(serverResponse3.isLastOperState()==true)
                        {
                            JFXDialogLayout layout_suc = new JFXDialogLayout();
                            layout_suc.setBody(new Label("      添加商品成功"));
                            JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) add_button.getScene().getWindow());
                            alert_suc.setOverlayClose(true);
                            alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                            alert_suc.setContent(layout_suc);
                            alert_suc.initModality(Modality.NONE);
                            alert_suc.show();
                        }

                        try {
                            savePic(new FileInputStream(file[0]), gname.getText()+".jpg");

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        tempShop temp = new tempShop();
                        //temp.setgName(good.getgName());
                        temp.setgName_pure(good.getgName());
                        temp.setgPrice(good.getgPrice());
                        temp.setgTag(good.getgTag());
                        temp.setgIntroduction(good.getgIntroduction());
                        temp.setgStore(good.getgStore());

                        addGoods(temp, ishop , image[0]);

                        addP.close();
                    }
                });
                cancel.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        addP.close();

                    }

                });
            }
        });

//显示全部商品
        r1.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue==true)
                {
                    FlowPane fp=new FlowPane();
                    fp.setPrefWidth(780);
                    fp.setHgap(25);
                    fp.setVgap(20);
                    scr1.setContent(fp);


                    for(int i=0;i<shop_AllGoods.size();i++) {
                        AnchorPane good1=new AnchorPane();
                        fp.getChildren().add(good1);
                        good1.setPrefSize(300,300);

                        Image goodBackground = new Image("/client/Image/goodBackground.png",360, 300,false,true);
                        ImageView good_bg = new ImageView(goodBackground);
                        good1.getChildren().add(good_bg);

//			    		FileInputStream file=null;
//			    		ImageView iv = null;
//						try {
//							file = new FileInputStream(new File("C:/Users/25751/Desktop/waitforupload.jpg"));
//							Image ima=new Image(file,200,250,false,true);
//							iv=new ImageView(ima);
//						} catch (FileNotFoundException e) {
//							e.printStackTrace();
//						}

                        ImageView imageView = new ImageView(shop_AllGoods.get(i).getgPic());
                        imageView.setLayoutX(40);
                        imageView.setLayoutY(40);

                        Button minus=new Button("-");
                        minus.setLayoutX(230);
                        minus.setLayoutY(180);

                        Button plus=new Button("+");
                        plus.setLayoutX(300);
                        plus.setLayoutY(180);

                        TextField num1=new TextField(String.valueOf(shop_AllGoods.get(i).getgAddNum()));
                        num1.setPrefSize(40, 10);
                        num1.setLayoutX(255);
                        num1.setLayoutY(180);

                        Label name=new Label(shop_AllGoods.get(i).getgName());
                        name.setLayoutX(230);
                        name.setLayoutY(30);

                        Label price=new Label(shop_AllGoods.get(i).getgPrice());
                        price.setLayoutX(230);
                        price.setLayoutY(70);

                        Label Yuan=new Label("元");
                        Yuan.setLayoutX(260);
                        Yuan.setLayoutY(70);

                        Label ins=new Label(shop_AllGoods.get(i).getgIntroduction());
                        ins.setLayoutX(230);
                        ins.setLayoutY(110);

                        String strR = "";
                        Message sendToServer6 = new Message();
                        Client client6 = new Client();
                        //卖家是商人还是学生
                        if(shop_AllGoods.get(i).getgsID().contains("bz")) {
                            sendToServer6.setMessageType(MessageType.shop_queryRetailer);
                            sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                            System.out.println(shop_AllGoods.get(i).getgsID());
                            Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                            Retailer retailer = new Retailer();
                            if(serverResponse6.getData() != null) retailer = (Retailer) serverResponse6.getData();
                            strR = retailer.getRenown();
                        }
                        else {
                            sendToServer6.setMessageType(MessageType.shop_queryStudent);
                            sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                            System.out.println(shop_AllGoods.get(i).getgsID());
                            Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                            Student sss = new Student();
                            if(serverResponse6.getData() != null) sss = (Student) serverResponse6.getData();
                            strR = sss.getRenown();
                        }


                        Label score=new Label("卖家信誉：" + renownCalculate(strR));
                        score.setLayoutX(230);
                        score.setLayoutY(150);

                        good1.getChildren().addAll(imageView, minus,plus,num1,name,ins,score,price,Yuan);

                        int j=i;
                        plus.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent arg0) {
                                int n=myCart.get(j).getgAddNum();
                                n++;
                                myCart.get(j).setgAddNum(n);
                                num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                            }
                        });
                        minus.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent arg0) {
                                int n=myCart.get(j).getgAddNum();
                                if(n>0) {
                                    n--;
                                    myCart.get(j).setgAddNum(n);
                                    num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                }else
                                {
                                    myCart.get(j).setgAddNum(0);
                                    num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                }

                            }
                        });
                    }
                }
            }
        });

//食品饮料
        r2.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

                if(arg2==true)
                {

                    FlowPane fp=new FlowPane();
                    scr1.setContent(fp);
                    fp.setPrefWidth(780);
                    fp.setHgap(25);
                    fp.setVgap(20);

                    for(int i=0;i<shop_AllGoods.size();i++) {
                        if(shop_AllGoods.get(i).getgTag().equals(r2.getText())==true) {
                            AnchorPane good1=new AnchorPane();
                            fp.getChildren().add(good1);
                            good1.setPrefSize(300,300);

                            Image goodBackground = new Image("/client/Image/goodBackground.png",360, 300,false,true);
                            ImageView good_bg = new ImageView(goodBackground);
                            good1.getChildren().add(good_bg);

//				    		FileInputStream file=null;
//				    		ImageView iv = null;
//							try {
//								file = new FileInputStream(new File("C:/Users/25751/Desktop/waitforupload.jpg"));
//								Image ima=new Image(file,200,250,false,true);
//								iv=new ImageView(ima);
//							} catch (FileNotFoundException e) {
//								e.printStackTrace();
//							}
                            ImageView imageView = new ImageView(shop_AllGoods.get(i).getgPic());
                            imageView.setLayoutX(40);
                            imageView.setLayoutY(40);

                            Button minus=new Button("-");
                            minus.setLayoutX(230);
                            minus.setLayoutY(180);

                            Button plus=new Button("+");
                            plus.setLayoutX(300);
                            plus.setLayoutY(180);

                            TextField num1=new TextField(String.valueOf(shop_AllGoods.get(i).getgAddNum()));
                            num1.setPrefSize(40, 10);
                            num1.setLayoutX(255);
                            num1.setLayoutY(180);

                            Label name=new Label(shop_AllGoods.get(i).getgName());
                            name.setLayoutX(230);
                            name.setLayoutY(30);

                            Label price=new Label(shop_AllGoods.get(i).getgPrice());
                            price.setLayoutX(230);
                            price.setLayoutY(70);

                            Label Yuan=new Label("元");
                            Yuan.setLayoutX(260);
                            Yuan.setLayoutY(70);

                            Label ins=new Label(shop_AllGoods.get(i).getgIntroduction());
                            ins.setLayoutX(230);
                            ins.setLayoutY(110);

                            String strR = "";
                            Message sendToServer6 = new Message();
                            Client client6 = new Client();
                            //卖家是商人还是学生
                            if(shop_AllGoods.get(i).getgsID().contains("bz")) {
                                sendToServer6.setMessageType(MessageType.shop_queryRetailer);
                                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                                System.out.println(shop_AllGoods.get(i).getgsID());
                                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                                Retailer retailer = new Retailer();
                                if(serverResponse6.getData() != null) retailer = (Retailer) serverResponse6.getData();
                                strR = retailer.getRenown();
                            }
                            else {
                                sendToServer6.setMessageType(MessageType.shop_queryStudent);
                                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                                System.out.println(shop_AllGoods.get(i).getgsID());
                                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                                Student sss = new Student();
                                if(serverResponse6.getData() != null) sss = (Student) serverResponse6.getData();
                                strR = sss.getRenown();
                            }


                            Label score=new Label("卖家信誉：" + renownCalculate(strR));
                            score.setLayoutX(230);
                            score.setLayoutY(150);

                            good1.getChildren().addAll(imageView, minus,plus,num1,name,ins,score,price,Yuan);

                            int j=i;
                            plus.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent arg0) {
                                    int n=myCart.get(j).getgAddNum();
                                    n++;
                                    myCart.get(j).setgAddNum(n);
                                    num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                }
                            });
                            minus.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent arg0) {
                                    int n=myCart.get(j).getgAddNum();
                                    if(n>0) {
                                        n--;
                                        myCart.get(j).setgAddNum(n);
                                        num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                    }else
                                    {
                                        myCart.get(j).setgAddNum(0);
                                        num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                    }

                                }});
                        }
                    }
                }
            }});

//生活用品
        r3.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

                if(arg2==true)
                {

                    FlowPane fp=new FlowPane();
                    scr1.setContent(fp);
                    fp.setPrefWidth(780);
                    fp.setHgap(25);
                    fp.setVgap(20);

                    for(int i=0;i<shop_AllGoods.size();i++) {
                        if(shop_AllGoods.get(i).getgTag().equals(r3.getText())==true) {
                            AnchorPane good1=new AnchorPane();
                            fp.getChildren().add(good1);
                            good1.setPrefSize(300,300);

                            Image goodBackground = new Image("/client/Image/goodBackground.png",360, 300,false,true);
                            ImageView good_bg = new ImageView(goodBackground);
                            good1.getChildren().add(good_bg);

//				    		FileInputStream file=null;
//				    		ImageView iv = null;
//							try {
//								file = new FileInputStream(new File("C:/Users/25751/Desktop/waitforupload.jpg"));
//								Image ima=new Image(file,200,250,false,true);
//								iv=new ImageView(ima);
//							} catch (FileNotFoundException e) {
//								e.printStackTrace();
//							}

                            ImageView imageView = new ImageView(shop_AllGoods.get(i).getgPic());
                            imageView.setLayoutX(40);
                            imageView.setLayoutY(40);

                            Button minus=new Button("-");
                            minus.setLayoutX(230);
                            minus.setLayoutY(180);

                            Button plus=new Button("+");
                            plus.setLayoutX(300);
                            plus.setLayoutY(180);

                            TextField num1=new TextField(String.valueOf(shop_AllGoods.get(i).getgAddNum()));
                            num1.setPrefSize(40, 10);
                            num1.setLayoutX(255);
                            num1.setLayoutY(180);

                            Label name=new Label(shop_AllGoods.get(i).getgName());
                            name.setLayoutX(230);
                            name.setLayoutY(30);

                            Label price=new Label(shop_AllGoods.get(i).getgPrice());
                            price.setLayoutX(230);
                            price.setLayoutY(70);

                            Label Yuan=new Label("元");
                            Yuan.setLayoutX(260);
                            Yuan.setLayoutY(70);

                            Label ins=new Label(shop_AllGoods.get(i).getgIntroduction());
                            ins.setLayoutX(230);
                            ins.setLayoutY(110);

                            String strR = "";
                            Message sendToServer6 = new Message();
                            Client client6 = new Client();
                            //卖家是商人还是学生
                            if(shop_AllGoods.get(i).getgsID().contains("bz")) {
                                sendToServer6.setMessageType(MessageType.shop_queryRetailer);
                                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                                System.out.println(shop_AllGoods.get(i).getgsID());
                                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                                Retailer retailer = new Retailer();
                                if(serverResponse6.getData() != null) retailer = (Retailer) serverResponse6.getData();
                                strR = retailer.getRenown();
                            }
                            else {
                                sendToServer6.setMessageType(MessageType.shop_queryStudent);
                                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                                System.out.println(shop_AllGoods.get(i).getgsID());
                                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                                Student sss = new Student();
                                if(serverResponse6.getData() != null) sss = (Student) serverResponse6.getData();
                                strR = sss.getRenown();
                            }


                            Label score=new Label("卖家信誉：" + renownCalculate(strR));
                            score.setLayoutX(230);
                            score.setLayoutY(150);

                            good1.getChildren().addAll(imageView, minus,plus,num1,name,ins,score,price,Yuan);

                            int j=i;
                            plus.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent arg0) {
                                    int n=myCart.get(j).getgAddNum();
                                    n++;
                                    myCart.get(j).setgAddNum(n);
                                    num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                }
                            });
                            minus.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent arg0) {
                                    int n=myCart.get(j).getgAddNum();
                                    if(n>0) {
                                        n--;
                                        myCart.get(j).setgAddNum(n);
                                        num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                    }else
                                    {
                                        myCart.get(j).setgAddNum(0);
                                        num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                    }

                                }});
                        }
                    }
                }
            }});

//电子产品
        r4.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

                if(arg2==true)
                {

                    FlowPane fp=new FlowPane();
                    scr1.setContent(fp);
                    fp.setPrefWidth(780);
                    fp.setHgap(25);
                    fp.setVgap(20);

                    for(int i=0;i<shop_AllGoods.size();i++) {
                        if(shop_AllGoods.get(i).getgTag().equals(r4.getText())==true) {
                            AnchorPane good1=new AnchorPane();
                            fp.getChildren().add(good1);
                            good1.setPrefSize(300,300);

                            Image goodBackground = new Image("/client/Image/goodBackground.png",360, 300,false,true);
                            ImageView good_bg = new ImageView(goodBackground);
                            good1.getChildren().add(good_bg);

//				    		FileInputStream file=null;
//				    		ImageView iv = null;
//							try {
//								file = new FileInputStream(new File("C:/Users/25751/Desktop/waitforupload.jpg"));
//								Image ima=new Image(file,200,250,false,true);
//								iv=new ImageView(ima);
//							} catch (FileNotFoundException e) {
//								e.printStackTrace();
//							}

                            ImageView imageView = new ImageView(shop_AllGoods.get(i).getgPic());
                            imageView.setLayoutX(40);
                            imageView.setLayoutY(40);

                            Button minus=new Button("-");
                            minus.setLayoutX(230);
                            minus.setLayoutY(180);

                            Button plus=new Button("+");
                            plus.setLayoutX(300);
                            plus.setLayoutY(180);

                            TextField num1=new TextField(String.valueOf(shop_AllGoods.get(i).getgAddNum()));
                            num1.setPrefSize(40, 10);
                            num1.setLayoutX(255);
                            num1.setLayoutY(180);

                            Label name=new Label(shop_AllGoods.get(i).getgName());
                            name.setLayoutX(230);
                            name.setLayoutY(30);

                            Label price=new Label(shop_AllGoods.get(i).getgPrice());
                            price.setLayoutX(230);
                            price.setLayoutY(70);

                            Label Yuan=new Label("元");
                            Yuan.setLayoutX(260);
                            Yuan.setLayoutY(70);

                            Label ins=new Label(shop_AllGoods.get(i).getgIntroduction());
                            ins.setLayoutX(230);
                            ins.setLayoutY(110);

                            String strR = "";
                            Message sendToServer6 = new Message();
                            Client client6 = new Client();
                            //卖家是商人还是学生
                            if(shop_AllGoods.get(i).getgsID().contains("bz")) {
                                sendToServer6.setMessageType(MessageType.shop_queryRetailer);
                                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                                System.out.println(shop_AllGoods.get(i).getgsID());
                                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                                Retailer retailer = new Retailer();
                                if(serverResponse6.getData() != null) retailer = (Retailer) serverResponse6.getData();
                                strR = retailer.getRenown();
                            }
                            else {
                                sendToServer6.setMessageType(MessageType.shop_queryStudent);
                                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                                System.out.println(shop_AllGoods.get(i).getgsID());
                                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                                Student sss = new Student();
                                if(serverResponse6.getData() != null) sss = (Student) serverResponse6.getData();
                                strR = sss.getRenown();
                            }


                            Label score=new Label("卖家信誉：" + renownCalculate(strR));
                            score.setLayoutX(230);
                            score.setLayoutY(150);

                            good1.getChildren().addAll(imageView, minus,plus,num1,name,ins,score,price,Yuan);

                            int j=i;
                            plus.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent arg0) {
                                    int n=myCart.get(j).getgAddNum();
                                    n++;
                                    myCart.get(j).setgAddNum(n);
                                    num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                }
                            });
                            minus.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent arg0) {
                                    int n=myCart.get(j).getgAddNum();
                                    if(n>0) {
                                        n--;
                                        myCart.get(j).setgAddNum(n);
                                        num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                    }else
                                    {
                                        myCart.get(j).setgAddNum(0);
                                        num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                    }

                                }});
                        }
                    }
                }
            }});

//图书文具
        r5.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

                if(arg2==true)
                {

                    FlowPane fp=new FlowPane();
                    scr1.setContent(fp);
                    fp.setPrefWidth(780);
                    fp.setHgap(25);
                    fp.setVgap(20);

                    for(int i=0;i<shop_AllGoods.size();i++) {
                        if(shop_AllGoods.get(i).getgTag().equals(r5.getText())==true) {
                            AnchorPane good1=new AnchorPane();
                            fp.getChildren().add(good1);
                            good1.setPrefSize(300,300);

                            Image goodBackground = new Image("/client/Image/goodBackground.png",360, 300,false,true);
                            ImageView good_bg = new ImageView(goodBackground);
                            good1.getChildren().add(good_bg);

//				    		FileInputStream file=null;
//				    		ImageView iv = null;
//							try {
//								file = new FileInputStream(new File("C:/Users/25751/Desktop/waitforupload.jpg"));
//								Image ima=new Image(file,200,250,false,true);
//								iv=new ImageView(ima);
//							} catch (FileNotFoundException e) {
//								e.printStackTrace();
//							}

                            ImageView imageView = new ImageView(shop_AllGoods.get(i).getgPic());
                            imageView.setLayoutX(40);
                            imageView.setLayoutY(40);

                            Button minus=new Button("-");
                            minus.setLayoutX(230);
                            minus.setLayoutY(180);

                            Button plus=new Button("+");
                            plus.setLayoutX(300);
                            plus.setLayoutY(180);

                            TextField num1=new TextField(String.valueOf(shop_AllGoods.get(i).getgAddNum()));
                            num1.setPrefSize(40, 10);
                            num1.setLayoutX(255);
                            num1.setLayoutY(180);

                            Label name=new Label(shop_AllGoods.get(i).getgName());
                            name.setLayoutX(230);
                            name.setLayoutY(30);

                            Label price=new Label(shop_AllGoods.get(i).getgPrice());
                            price.setLayoutX(230);
                            price.setLayoutY(70);

                            Label Yuan=new Label("元");
                            Yuan.setLayoutX(260);
                            Yuan.setLayoutY(70);

                            Label ins=new Label(shop_AllGoods.get(i).getgIntroduction());
                            ins.setLayoutX(230);
                            ins.setLayoutY(110);

                            String strR = "";
                            Message sendToServer6 = new Message();
                            Client client6 = new Client();
                            //卖家是商人还是学生
                            if(shop_AllGoods.get(i).getgsID().contains("bz")) {
                                sendToServer6.setMessageType(MessageType.shop_queryRetailer);
                                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                                System.out.println(shop_AllGoods.get(i).getgsID());
                                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                                Retailer retailer = new Retailer();
                                if(serverResponse6.getData() != null) retailer = (Retailer) serverResponse6.getData();
                                strR = retailer.getRenown();
                            }
                            else {
                                sendToServer6.setMessageType(MessageType.shop_queryStudent);
                                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                                System.out.println(shop_AllGoods.get(i).getgsID());
                                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                                Student sss = new Student();
                                if(serverResponse6.getData() != null) sss = (Student) serverResponse6.getData();
                                strR = sss.getRenown();
                            }


                            Label score=new Label("卖家信誉：" + renownCalculate(strR));
                            score.setLayoutX(230);
                            score.setLayoutY(150);

                            good1.getChildren().addAll(imageView, minus,plus,num1,name,ins,score,price,Yuan);

                            int j=i;
                            plus.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent arg0) {
                                    int n=myCart.get(j).getgAddNum();
                                    n++;
                                    myCart.get(j).setgAddNum(n);
                                    num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                }
                            });
                            minus.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent arg0) {
                                    int n=myCart.get(j).getgAddNum();
                                    if(n>0) {
                                        n--;
                                        myCart.get(j).setgAddNum(n);
                                        num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                    }else
                                    {
                                        myCart.get(j).setgAddNum(0);
                                        num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                    }

                                }});
                        }
                    }
                }
            }});

//运动健康
        r6.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

                if(arg2==true)
                {

                    FlowPane fp=new FlowPane();
                    scr1.setContent(fp);
                    fp.setPrefWidth(780);
                    fp.setHgap(25);
                    fp.setVgap(20);

                    for(int i=0;i<shop_AllGoods.size();i++) {
                        if(shop_AllGoods.get(i).getgTag().equals(r6.getText())==true) {
                            AnchorPane good1=new AnchorPane();
                            fp.getChildren().add(good1);
                            good1.setPrefSize(300,300);

                            Image goodBackground = new Image("/client/Image/goodBackground.png",360, 300,false,true);
                            ImageView good_bg = new ImageView(goodBackground);
                            good1.getChildren().add(good_bg);

//				    		FileInputStream file=null;
//				    		ImageView iv = null;
//							try {
//								file = new FileInputStream(new File("C:/Users/25751/Desktop/waitforupload.jpg"));
//								Image ima=new Image(file,200,250,false,true);
//								iv=new ImageView(ima);
//							} catch (FileNotFoundException e) {
//								e.printStackTrace();
//							}

                            ImageView imageView = new ImageView(shop_AllGoods.get(i).getgPic());
                            imageView.setLayoutX(40);
                            imageView.setLayoutY(40);

                            Button minus=new Button("-");
                            minus.setLayoutX(230);
                            minus.setLayoutY(180);

                            Button plus=new Button("+");
                            plus.setLayoutX(300);
                            plus.setLayoutY(180);

                            TextField num1=new TextField(String.valueOf(shop_AllGoods.get(i).getgAddNum()));
                            num1.setPrefSize(40, 10);
                            num1.setLayoutX(255);
                            num1.setLayoutY(180);

                            Label name=new Label(shop_AllGoods.get(i).getgName());
                            name.setLayoutX(230);
                            name.setLayoutY(30);

                            Label price=new Label(shop_AllGoods.get(i).getgPrice());
                            price.setLayoutX(230);
                            price.setLayoutY(70);

                            Label Yuan=new Label("元");
                            Yuan.setLayoutX(260);
                            Yuan.setLayoutY(70);

                            Label ins=new Label(shop_AllGoods.get(i).getgIntroduction());
                            ins.setLayoutX(230);
                            ins.setLayoutY(110);

                            String strR = "";
                            Message sendToServer6 = new Message();
                            Client client6 = new Client();
                            //卖家是商人还是学生
                            if(shop_AllGoods.get(i).getgsID().contains("bz")) {
                                sendToServer6.setMessageType(MessageType.shop_queryRetailer);
                                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                                System.out.println(shop_AllGoods.get(i).getgsID());
                                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                                Retailer retailer = new Retailer();
                                if(serverResponse6.getData() != null) retailer = (Retailer) serverResponse6.getData();
                                strR = retailer.getRenown();
                            }
                            else {
                                sendToServer6.setMessageType(MessageType.shop_queryStudent);
                                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                                System.out.println(shop_AllGoods.get(i).getgsID());
                                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                                Student sss = new Student();
                                if(serverResponse6.getData() != null) sss = (Student) serverResponse6.getData();
                                strR = sss.getRenown();
                            }


                            Label score=new Label("卖家信誉：" + renownCalculate(strR));
                            score.setLayoutX(230);
                            score.setLayoutY(150);

                            good1.getChildren().addAll(imageView, minus,plus,num1,name,ins,score,price,Yuan);

                            int j=i;
                            plus.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent arg0) {
                                    int n=myCart.get(j).getgAddNum();
                                    n++;
                                    myCart.get(j).setgAddNum(n);
                                    num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                }
                            });
                            minus.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent arg0) {
                                    int n=myCart.get(j).getgAddNum();
                                    if(n>0) {
                                        n--;
                                        myCart.get(j).setgAddNum(n);
                                        num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                    }else
                                    {
                                        myCart.get(j).setgAddNum(0);
                                        num1.setText(String.valueOf(myCart.get(j).getgAddNum()));
                                    }

                                }});
                        }
                    }
                }
            }});

//查询
        b.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {

                Message sendToServer2 = new Message();
                Client client2 = new Client();
                sendToServer2.setMessageType(MessageType.shopQuery);
                sendToServer2.setExtraMessage(text.getText());
                Message serverResponse2 = client2.sendRequestToServer(sendToServer2);
                ArrayList<Goods> goodarr2=new ArrayList<Goods>();
                goodarr2=(ArrayList<Goods>) serverResponse2.getData();

                FlowPane fp=new FlowPane();
                scr1.setContent(fp);
                fp.setPrefWidth(780);
                fp.setHgap(25);
                fp.setVgap(20);
                for(int i=0;i<goodarr2.size();i++) {
                    for(int j=0;j<shop_AllGoods.size();j++) {
                        if(goodarr2.get(i).getgName().equals(shop_AllGoods.get(j).getgName())==true) {
                            AnchorPane good1=new AnchorPane();
                            fp.getChildren().add(good1);
                            good1.setPrefSize(300,300);

                            Image goodBackground = new Image("/client/Image/goodBackground.png",360, 300,false,true);
                            ImageView good_bg = new ImageView(goodBackground);
                            good1.getChildren().add(good_bg);

//				    		FileInputStream file=null;
//				    		ImageView iv = null;
//							try {
//								file = new FileInputStream(new File("C:/Users/25751/Desktop/waitforupload.jpg"));
//								Image ima=new Image(file,200,250,false,true);
//								iv=new ImageView(ima);
//							} catch (FileNotFoundException e) {
//								e.printStackTrace();
//							}

                            ImageView imageView = new ImageView(shop_AllGoods.get(j).getgPic());
                            imageView.setLayoutX(40);
                            imageView.setLayoutY(40);

                            Button minus=new Button("-");
                            minus.setLayoutX(230);
                            minus.setLayoutY(180);

                            Button plus=new Button("+");
                            plus.setLayoutX(300);
                            plus.setLayoutY(180);

                            TextField num1=new TextField(String.valueOf(shop_AllGoods.get(j).getgAddNum()));
                            num1.setPrefSize(40, 10);
                            num1.setLayoutX(255);
                            num1.setLayoutY(180);

                            Label name=new Label(shop_AllGoods.get(j).getgName());
                            name.setLayoutX(230);
                            name.setLayoutY(30);

                            Label price=new Label(shop_AllGoods.get(j).getgPrice());
                            price.setLayoutX(230);
                            price.setLayoutY(70);

                            Label Yuan=new Label("元");
                            Yuan.setLayoutX(260);
                            Yuan.setLayoutY(70);

                            Label ins=new Label(shop_AllGoods.get(j).getgIntroduction());
                            ins.setLayoutX(230);
                            ins.setLayoutY(110);

                            String strR = "";
                            Message sendToServer6 = new Message();
                            Client client6 = new Client();
                            //卖家是商人还是学生
                            if(shop_AllGoods.get(i).getgsID().contains("bz")) {
                                sendToServer6.setMessageType(MessageType.shop_queryRetailer);
                                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                                System.out.println(shop_AllGoods.get(i).getgsID());
                                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                                Retailer retailer = new Retailer();
                                if(serverResponse6.getData() != null) retailer = (Retailer) serverResponse6.getData();
                                strR = retailer.getRenown();
                            }
                            else {
                                sendToServer6.setMessageType(MessageType.shop_queryStudent);
                                sendToServer6.setExtraMessage(shop_AllGoods.get(i).getgsID());
                                System.out.println(shop_AllGoods.get(i).getgsID());
                                Message serverResponse6 = client6.sendRequestToServer(sendToServer6);

                                Student sss = new Student();
                                if(serverResponse6.getData() != null) sss = (Student) serverResponse6.getData();
                                strR = sss.getRenown();
                            }


                            Label score=new Label("卖家信誉：" + renownCalculate(strR));
                            score.setLayoutX(230);
                            score.setLayoutY(150);

                            good1.getChildren().addAll(imageView, minus,plus,num1,name,ins,score,price,Yuan);

                            int m=j;
                            plus.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent arg0) {
                                    int n=myCart.get(m).getgAddNum();
                                    n++;
                                    myCart.get(m).setgAddNum(n);
                                    num1.setText(String.valueOf(myCart.get(m).getgAddNum()));
                                }
                            });
                            minus.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent arg0) {
                                    int n=myCart.get(m).getgAddNum();
                                    if(n>0) {
                                        n--;
                                        myCart.get(m).setgAddNum(n);
                                        num1.setText(String.valueOf(myCart.get(m).getgAddNum()));
                                    } else
                                    {
                                        myCart.get(m).setgAddNum(0);
                                        num1.setText(String.valueOf(myCart.get(m).getgAddNum()));
                                    }

                                }});
                        }
                    }
                }


            }
        });

//加入购物车
        add_cart.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                for(int l=0;l<myCart.size();l++) {
                    double p=Double.valueOf(myCart.get(l).getgPrice());
                    Total=Total + p * myCart.get(l).getgAddNum();
                    total.setText(String.valueOf(Total));
                }
                scr2.setContent(cartfp);
                cartfp.getChildren().clear();

                for(int l=0;l<myCart.size();l++) {

                    if(myCart.get(l).getgAddNum()!=0) {
                        AnchorPane cartap=new AnchorPane();
                        cartap.setPrefSize(300, 300);
                        cartfp.getChildren().add(cartap);

                        Image goodBackground = new Image("/client/Image/goodBackground.png",360, 300,false,true);
                        ImageView good_bg = new ImageView(goodBackground);
                        cartap.getChildren().add(good_bg);

                        ImageView imageView = new ImageView(myCart.get(l).getgPic());
                        imageView.setLayoutX(40);
                        imageView.setLayoutY(40);

                        Button minus1=new Button("-");
                        minus1.setLayoutX(230);
                        minus1.setLayoutY(110);

                        Button plus1=new Button("+");
                        plus1.setLayoutX(300);
                        plus1.setLayoutY(110);

                        TextField num2=new TextField(String.valueOf(myCart.get(l).getgAddNum()));
                        num2.setPrefSize(40, 10);
                        num2.setLayoutX(255);
                        num2.setLayoutY(110);

                        Label name1=new Label(myCart.get(l).getgName());
                        name1.setLayoutX(230);
                        name1.setLayoutY(30);


                        Label price1=new Label(myCart.get(l).getgPrice());
                        price1.setLayoutX(230);
                        price1.setLayoutY(70);

                        Label Yuan1=new Label("元");
                        Yuan1.setLayoutX(260);
                        Yuan1.setLayoutY(70);

                        Button delete=new Button("删除");
                        delete.setLayoutX(230);
                        delete.setLayoutY(150);

                        cartap.getChildren().addAll(imageView, minus1,plus1,num2,name1,price1,Yuan1,delete);

                        int m=l;
                        plus1.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent arg0) {
                                int n=myCart.get(m).getgAddNum();
                                n++;
                                myCart.get(m).setgAddNum(n);
                                num2.setText(String.valueOf(myCart.get(m).getgAddNum()));
                                double p=Double.valueOf(myCart.get(m).getgPrice());
                                Total=Total+p;
                                total.setText(String.valueOf(Total));
                            }
                        });
                        minus1.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent arg0) {
                                int n=myCart.get(m).getgAddNum();
                                if(n>1) {
                                    n--;
                                    myCart.get(m).setgAddNum(n);
                                    num2.setText(String.valueOf(myCart.get(m).getgAddNum()));
                                    double p=Double.valueOf(myCart.get(m).getgPrice());
                                    Total=Total-p;

                                }else
                                {
                                    myCart.get(m).setgAddNum(1);
                                    num2.setText(String.valueOf(myCart.get(m).getgAddNum()));
                                }
                                total.setText(String.valueOf(Total));
                            }
                        });

                        delete.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent arg0) {
                                double p = Double.valueOf(myCart.get(m).getgPrice());
                                Total= Total - p*myCart.get(m).getgAddNum();
                                myCart.get(m).setgAddNum(0);
                                total.setText(String.valueOf(Total));

                                cartap.getChildren().clear();
                                cartap.setManaged(false);
                            }
                        });


                    }
                }

                JFXDialogLayout layout_suc = new JFXDialogLayout();
                layout_suc.setBody(new Label("      添加成功！快去购物车里看看吧~"));
                JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) add_cart.getScene().getWindow());
                alert_suc.setOverlayClose(true);
                alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert_suc.setContent(layout_suc);
                alert_suc.initModality(Modality.NONE);
                alert_suc.show();
            }});

//结算响应
        button.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {

                boolean sucFlag = true;

                for(int l=0;l<myCart.size();l++) {

                    if (myCart.get(l).getgAddNum() != 0){

                        Goods good=new Goods();
                        good.setgIntroduction(myCart.get(l).getgIntroduction());
                        good.setgName(myCart.get(l).getgName());
                        good.setgPrice(myCart.get(l).getgPrice());
                        good.setgsID(myCart.get(l).getgsID());
                        good.setgStore(myCart.get(l).getgStore());
                        good.setgTag(myCart.get(l).getgTag());


                        Message sendToServer4 = new Message();
                        Client client4 = new Client();
                        sendToServer4.setUserId(stu.getId());
                        sendToServer4.setMessageType(MessageType.Buy);
                        sendToServer4.setExtraMessage(String.valueOf(myCart.get(l).getgAddNum()));
                        sendToServer4.setData(good);
                        Message serverResponse4= client4.sendRequestToServer(sendToServer4);

                    if ( serverResponse4.isLastOperState() == false )
                    {
                        sucFlag = false;
                        JFXDialogLayout layout_f = new JFXDialogLayout();
                        layout_f.setBody(new Label("      " + good.getgName() + "购买失败：" + serverResponse4.getErrorMessage()));
                        System.out.println(serverResponse4.getErrorMessage());
                        //layout_f.setBody(new Label("      选课失败"));
                        JFXAlert<Void> alert_f = new JFXAlert<>((Stage) button.getScene().getWindow());
                        alert_f.setOverlayClose(true);
                        alert_f.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                        alert_f.setContent(layout_f);
                        alert_f.initModality(Modality.NONE);
                        alert_f.show();
                    }

                        myCart.get(l).setgAddNum(0);

                    }

                }

                if(sucFlag == true){
                    JFXDialogLayout layout_suc = new JFXDialogLayout();
                    layout_suc.setBody(new Label("      购买成功~"));
                    JFXAlert<Void> alert_suc = new JFXAlert<>((Stage) button.getScene().getWindow());
                    alert_suc.setOverlayClose(true);
                    alert_suc.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert_suc.setContent(layout_suc);
                    alert_suc.initModality(Modality.NONE);
                    alert_suc.show();

                    total.setText("0");
                    Total = 0;
                    cartfp.getChildren().clear();
                }

            }

        });


        AnchorPane ap=new AnchorPane();
        JFXTabPane tab=new JFXTabPane();
        tab.setPrefWidth(1058);

        tab.getTabs().addAll(tab1,tab2,tab3,tab4);
        ap.getChildren().add(tab);

        return ap;
    }
}
