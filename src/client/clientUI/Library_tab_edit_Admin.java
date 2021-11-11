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
import util.MessageType;
import vo.Book;
import vo.Message;

import java.util.ArrayList;

public class Library_tab_edit_Admin {

    public Tab tabEdit()
    {
        Tab tab2=new Tab("图书修改");
        tab2.setClosable(false);
        AnchorPane ap2=new AnchorPane();
        tab2.setContent(ap2);

        JFXTextField text21=new JFXTextField();//设置查询信息输入框
        text21.setPrefWidth(300);
        text21.setPromptText("请输入要修改的书本书号后点击开始进行查询"); //设置灰色字的提示
        Tooltip tip21=new Tooltip("注意：请注意书本书号排布是否正确");//设置鼠标移到此处后的提示
        text21.setTooltip(tip21);
        ap2.getChildren().add(text21);//添加到ap1
        ap2.setTopAnchor(text21, 80.0);
        ap2.setLeftAnchor(text21, 20.0);

//		BookDao BookDao=new BookDao();



        JFXButton b2=new JFXButton("开始");
        b2.setStyle("-fx-font-size: 14px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-text-fill: WHITE;");
        ap2.getChildren().add(b2);
        ap2.setTopAnchor(b2, 360.0);
        ap2.setLeftAnchor(b2, 350.0);

        JFXButton b3=new JFXButton("修改");
        b3.setStyle("-fx-font-size: 14px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-text-fill: WHITE;");
        ap2.getChildren().add(b3);
        ap2.setTopAnchor(b3,400.0);
        ap2.setLeftAnchor(b3,350.0);

        JFXTextField text22=new JFXTextField();//书名
        text22.setPromptText("请输入要修改的书名");
        text22.setPrefWidth(300);
        ap2.getChildren().add(text22);//添加到ap1
        ap2.setTopAnchor(text22, 120.0);
        ap2.setLeftAnchor(text22, 20.0);

        JFXTextField text23=new JFXTextField();//作者
        text23.setPromptText("请输入要修改的作者");
        text23.setPrefWidth(300);
        ap2.getChildren().add(text23);
        ap2.setTopAnchor(text23, 160.0);
        ap2.setLeftAnchor(text23, 20.0);

        JFXTextField text24=new JFXTextField();//标签
        text24.setPromptText("请输入要修改的标签");
        text24.setPrefWidth(300);
        ap2.getChildren().add(text24);
        ap2.setTopAnchor(text24, 200.0);
        ap2.setLeftAnchor(text24, 20.0);

        JFXTextField text25=new JFXTextField();//出版社
        text25.setPromptText("请输入要修改的出版社");
        text25.setPrefWidth(300);
        ap2.getChildren().add(text25);
        ap2.setTopAnchor(text25, 240.0);
        ap2.setLeftAnchor(text25, 20.0);

        JFXTextField text26=new JFXTextField();//出版日期
        text26.setPromptText("请在下框选择修改出版日期");
        text26.setPrefWidth(300);
        ap2.getChildren().add(text26);
        ap2.setTopAnchor(text26, 280.0);
        ap2.setLeftAnchor(text26, 20.0);

        JFXDatePicker checkInDatePicker=new JFXDatePicker();
        ap2.getChildren().add(checkInDatePicker);
        ap2.setTopAnchor(checkInDatePicker, 320.0);
        ap2.setLeftAnchor(checkInDatePicker, 20.0);

        ToggleGroup tg21=new ToggleGroup();//设置单选box
        JFXRadioButton r211=new JFXRadioButton("在馆");
        JFXRadioButton r212=new JFXRadioButton("不在");
        ap2.getChildren().addAll(r211,r212);
        ap2.setTopAnchor(r211, 360.0);
        ap2.setTopAnchor(r212, 360.0);
        ap2.setLeftAnchor(r211, 10.0);
        ap2.setLeftAnchor(r212, 80.0);
        r211.setToggleGroup(tg21);//单选按钮成组
        r212.setToggleGroup(tg21);

        ToggleGroup tg22=new ToggleGroup();
        JFXRadioButton r221=new JFXRadioButton("可借");
        JFXRadioButton r222=new JFXRadioButton("不借");
        ap2.getChildren().addAll(r221,r222);
        ap2.setTopAnchor(r221, 400.0);
        ap2.setTopAnchor(r222, 400.0);
        ap2.setLeftAnchor(r221, 10.0);
        ap2.setLeftAnchor(r222, 80.0);
        r221.setToggleGroup(tg22);//单选按钮成组
        r222.setToggleGroup(tg22);

//		BookDao dao2=new BookDao();
        b2.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {

                Message mes=new Message();
                mes.setData(text21.getText());
                mes.setMessageType(MessageType.outputbybookidsearch);//表明操作类型
                Message serveResponse=new Message();
                Client client=new Client();
                serveResponse=client.sendRequestToServer(mes);
                ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();


                if(arr.size()==0)
                {
                    DialogPane dpane=new DialogPane();
                    dpane.setContentText("找不到匹配项，"
                            + "    请重新输入书号进行检索！");


                    Dialog <ButtonType>dia=new Dialog<ButtonType>();
                    dia.setDialogPane(dpane);
                    dia.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                    dia.show();

                }
                else
                {
                    text22.setText(arr.get(0).getBookName());
                    text23.setText(arr.get(0).getBookAuthor());
                    text24.setText(arr.get(0).getBookTag());
                    text25.setText(arr.get(0).getBookPublisher());
                    text26.setText(arr.get(0).getBookPubDate());

                    if(arr.get(0).getBookSite()==true)
                    {
                        tg21.selectToggle(r211);

                    }
                    else
                    {
                        tg21.selectToggle(r212);
                    }
                    if(arr.get(0).getBookStatus()==true)
                    {
                        tg22.selectToggle(r221);

                    }
                    else
                    {
                        tg22.selectToggle(r222);
                    }

                }
            }
        });
        b3.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {

                Message mes=new Message();
                mes.setData(text21.getText());
                mes.setMessageType(MessageType.outputbybookidsearch);//表明操作类型
                Message serveResponse=new Message();
                Client client=new Client();
                serveResponse=client.sendRequestToServer(mes);
                ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();


                Book lib=arr.get(0);
                lib.setBookName(text22.getText());
                lib.setBookAuthor(text23.getText());
                lib.setBookTag(text24.getText());
                lib.setBookPublisher(text25.getText());
                if(checkInDatePicker.getValue()!=null)
                {
                    text26.setText(checkInDatePicker.getValue().toString());
                    lib.setBookPubDate(checkInDatePicker.getValue().toString());

                    Message mes4=new Message();
                    mes4.setData(lib);
                    mes4.setMessageType(MessageType.lendbook);//表明操作类型
                    Message serveResponse4=new Message();
                    Client client4=new Client();
                    serveResponse4=client4.sendRequestToServer(mes4);


//	    			BookDao.UpDate(lib);
                }


                boolean site=false;
                if(tg21.getSelectedToggle()==r211)
                {
                    site=true;
                }
                if(tg21.getSelectedToggle()==r212)
                {
                    site=false;
                }

                boolean status=false;
                if(tg22.getSelectedToggle()==r221)
                {
                    status=true;
                }
                if(tg22.getSelectedToggle()==r222)
                {
                    status=false;
                }

                lib.setBookSite(site);
                lib.setBookStatus(status);

                Message mes4=new Message();
                mes4.setData(lib);
                mes4.setMessageType(MessageType.updatebook);//表明操作类型
                Message serveResponse4=new Message();
                Client client4=new Client();
                serveResponse4=client4.sendRequestToServer(mes4);

//				BookDao.UpDate(lib);

            }
        });




        return tab2;
    }

}
