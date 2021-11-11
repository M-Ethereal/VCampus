package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.MessageType;
import vo.Goods;
import vo.Message;
import vo.Retailer;
import vo.tempShop;

import java.io.*;
import java.util.ArrayList;

public class Shop_tab_add_Retailer {
    Retailer retailer=new Retailer();
    ArrayList<tempShop> myshop=new ArrayList<tempShop>();

    public Shop_tab_add_Retailer(Retailer retailer){
        this.retailer = retailer;
    }

    //上传图片
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

    public Tab addPage(){
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
                        good.setgsID(retailer.getId());

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

            if(goodarr.get(a).getgsID().equals(retailer.getId())==true) {
                myshop.add(tempshop);
            }
        }

        for(int i=0;i<myshop.size();i++) {
            addGoods(myshop.get(i), ishop, myshop.get(i).getgPic());
        }

        return tab3;
    }

}
