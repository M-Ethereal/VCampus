package client.clientUI;

import client.socket.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import util.MessageType;
import vo.Book;
import vo.Message;

import java.util.ArrayList;

public class Library_tab_add_Admin {

    public Tab tabAdd()
    {
        Tab tab1=new Tab("图书增加");
        tab1.setClosable(false);

        AnchorPane ap1=new AnchorPane();
        tab1.setContent(ap1);

        JFXTextField text11=new JFXTextField();//设置查询信息输入框
        text11.setPrefWidth(300);
        text11.setPromptText("请输入书号（标准8位）"); //设置灰色字的提示
        Tooltip tip11=new Tooltip("注意：请注意书号排布是否正确以及是否重复");//设置鼠标移到此处后的提示
        text11.setTooltip(tip11);
        ap1.getChildren().add(text11);//添加到ap1
        ap1.setTopAnchor(text11, 80.0);
        ap1.setLeftAnchor(text11, 20.0);

        JFXTextField text12=new JFXTextField();//设置查询信息输入框
        text12.setPrefWidth(300);
        text12.setPromptText("请输入书名"); //设置灰色字的提示
        Tooltip tip12=new Tooltip("注意：请注意书名是否正确");//设置鼠标移到此处后的提示
        text12.setTooltip(tip12);
        ap1.getChildren().add(text12);//添加到ap1
        ap1.setTopAnchor(text12, 120.0);
        ap1.setLeftAnchor(text12, 20.0);

        JFXTextField text13=new JFXTextField();//设置查询信息输入框
        text13.setPrefWidth(300);
        text13.setPromptText("请输入该书作者"); //设置灰色字的提示
        Tooltip tip13=new Tooltip("注意：请注意作者名是否正确");//设置鼠标移到此处后的提示
        text13.setTooltip(tip13);
        ap1.getChildren().add(text13);//添加到ap1
        ap1.setTopAnchor(text13, 160.0);
        ap1.setLeftAnchor(text13, 20.0);


        ToggleGroup tg11=new ToggleGroup();//设置单选box
        JFXRadioButton r111=new JFXRadioButton("在馆");
        JFXRadioButton r112=new JFXRadioButton("不在");
        r111.setToggleGroup(tg11);//单选按钮成组
        r112.setToggleGroup(tg11);
        ap1.setTopAnchor(r111, 360.0);
        ap1.setTopAnchor(r112, 360.0);
        ap1.setLeftAnchor(r111, 10.0);
        ap1.setLeftAnchor(r112, 80.0);

        HBox hbox1=new HBox(10);//设置单选box
        hbox1.getChildren().addAll(r111,r112);



        ToggleGroup tg12=new ToggleGroup();//设置单选box
        JFXRadioButton r121=new JFXRadioButton("可借");
        JFXRadioButton r122=new JFXRadioButton("不借");
        r121.setToggleGroup(tg12);//单选按钮成组
        r122.setToggleGroup(tg12);
        ap1.setTopAnchor(r121, 400.0);
        ap1.setTopAnchor(r122, 400.0);
        ap1.setLeftAnchor(r121, 10.0);
        ap1.setLeftAnchor(r122, 80.0);

        HBox hbox2=new HBox(10);//设置单选box
        hbox1.getChildren().addAll(r121,r122);


        JFXTextField text15=new JFXTextField();//设置查询信息输入框
        text15.setPrefWidth(300);
        text15.setPromptText("请输入该书所属出版社"); //设置灰色字的提示
        Tooltip tip15=new Tooltip("注意：请注意出版社名称是否正确");//设置鼠标移到此处后的提示
        text15.setTooltip(tip15);
        ap1.getChildren().add(text15);//添加到ap1
        ap1.setTopAnchor(text15, 200.0);
        ap1.setLeftAnchor(text15, 20.0);

        JFXTextField text16=new JFXTextField();//设置查询信息输入框
        text16.setPrefWidth(300);
        text16.setPromptText("请输入该书所属标签"); //设置灰色字的提示
        Tooltip tip16=new Tooltip("注意：请注意标签名称是否符合规定");//设置鼠标移到此处后的提示
        text16.setTooltip(tip16);
        ap1.getChildren().add(text16);//添加到ap1
        ap1.setTopAnchor(text16, 240.0);
        ap1.setLeftAnchor(text16, 20.0);


        JFXDatePicker checkInDatePicker=new JFXDatePicker();
        ap1.getChildren().add(checkInDatePicker);
        ap1.setTopAnchor(checkInDatePicker, 280.0);
        ap1.setLeftAnchor(checkInDatePicker, 20.0);


        JFXButton b1=new JFXButton("添加");
        b1.setStyle("-fx-font-size: 14px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-text-fill: WHITE;");
        ap1.getChildren().add(b1);
        ap1.setTopAnchor(b1, 80.0);
        ap1.setLeftAnchor(b1, 400.0);



        b1.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //System.out.println("日期 "+checkInDatePicker.getValue().toString());
//				BookDao BookDao=new BookDao();


                Message mes=new Message();
                mes.setData(text11.getText());
                mes.setMessageType(MessageType.outputbybookidsearch);//表明操作类型
                Message serveResponse=new Message();
                Client client=new Client();
                serveResponse=client.sendRequestToServer(mes);
                ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();

                if(arr.size()==0)
                {
                    DialogPane dpane=new DialogPane();
                    dpane.setContentText("确定要添加"+"吗？");//弹窗提示
                    dpane.getButtonTypes().add(ButtonType.OK);
                    Button ok=(Button)dpane.lookupButton(ButtonType.OK);
                    Dialog <ButtonType>dia=new Dialog<ButtonType>();
                    dia.setDialogPane(dpane);
                    dia.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                    dia.show();
                    ok.setOnAction(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event)
                        {
                            boolean site=false;
                            if(tg11.getSelectedToggle()==r111)
                            {
                                site=true;
                            }
                            if(tg11.getSelectedToggle()==r112)
                            {
                                site=false;
                            }

                            boolean status=false;
                            if(tg12.getSelectedToggle()==r121)
                            {
                                status=true;
                            }
                            if(tg12.getSelectedToggle()==r122)
                            {
                                status=false;
                            }
                            Book lib=new Book();
                            lib.setBookID(text11.getText());
                            lib.setBookName(text12.getText());
                            lib.setBookAuthor(text13.getText());
                            lib.setBookTag(text16.getText());
                            lib.setBookPublisher(text15.getText());
                            lib.setBookStar(0);
                            lib.setBookPubDate(checkInDatePicker.getValue().toString());
                            lib.setBookStatus(status);
                            lib.setBookSite(site);

                            Message mes=new Message();
                            mes.setData(lib);
                            mes.setMessageType(MessageType.insertbook);//表明操作类型
                            Message serveResponse=new Message();
                            Client client=new Client();
                            serveResponse=client.sendRequestToServer(mes);
                            //ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();

                            //BookDao.Insert(lib);

                        }
                    });

                }
                else
                {

                    DialogPane dpane=new DialogPane();
                    dpane.setContentText("书号重复，请重新设置");//弹窗提示
                    Dialog <ButtonType>dia=new Dialog<ButtonType>();
                    dia.setDialogPane(dpane);
                    dia.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                    dia.show();

                }

            }
        });




        return tab1;
    }

}
