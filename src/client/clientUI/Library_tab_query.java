package client.clientUI;

import client.socket.Client;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.MessageType;
import vo.Book;
import vo.LendRecord;
import vo.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Library_tab_query {

    public AnchorPane ap3(String stuID)
    {

        AnchorPane ap3=new AnchorPane();
        Button b31=new Button("查询");//查询按钮
        b31.setPrefWidth(100);

        ap3.getChildren().addAll(b31);//添加查询按钮


        HBox hbox3=new HBox(10);//设置单选box

        ToggleGroup tg3=new ToggleGroup();//设置单选box
        RadioButton r31=new RadioButton("模糊查询");
        RadioButton r32=new RadioButton("按书号查");
        RadioButton r33=new RadioButton("按书名查");
        RadioButton r34=new RadioButton("按作者查");

        r31.setToggleGroup(tg3);//单选按钮成组
        r32.setToggleGroup(tg3);
        r33.setToggleGroup(tg3);
        r34.setToggleGroup(tg3);

        hbox3.getChildren().addAll(r31,r32,r33,r34);

        ap3.getChildren().add(hbox3);


        TextField text31=new TextField();//设置查询信息输入框
        text31.setPrefWidth(300);
        text31.setPromptText("请输入查询信息"); //设置灰色字的提示
        Tooltip tip31=new Tooltip("注意：请先选择查询方式");//设置鼠标移到此处后的提示
        text31.setTooltip(tip31);
        ap3.getChildren().add(text31);//添加到ap3

        ap3.setTopAnchor(hbox3, 50.0);//设置参数
        ap3.setLeftAnchor(hbox3, 100.0);//设置参数
        ap3.setTopAnchor(text31, 100.0);//设置参数
        ap3.setLeftAnchor(text31,100.0);//设置参数
        ap3.setTopAnchor(b31, 100.0);//设置参数
        ap3.setLeftAnchor(b31,400.0);//设置参数



//	    BookDao dao3=new BookDao();
        ObservableList<Book> list3=FXCollections.observableArrayList();
        TableView<Book> tableview3=new TableView<Book>(list3);

        tg3.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<?extends Toggle>observable,Toggle oldValue,Toggle newValue)
            {
                RadioButton r=(RadioButton)newValue;
                System.out.println(r.getText());
                if(r.getText()=="模糊查询")
                {
                    b31.setOnAction(new EventHandler<ActionEvent>()
                    {
                        @Override
                        //Message sent
                        public void handle(ActionEvent event)
                        {
                            //
                            tableview3.getItems().removeAll(list3);
                            tableview3.refresh();
                            //
                            Message mes=new Message();
                            mes.setData(text31.getText());
                            mes.setMessageType(MessageType.outputbyindistinctsearch);//表明操作类型
                            Message serveResponse=new Message();
                            Client client=new Client();
                            serveResponse=client.sendRequestToServer(mes);
                            ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();

                            if(arr.size()>0)
                            {
                                for(int i=0;i<arr.size();i++)
                                {
                                    Book Book=new Book();
                                    Book.setBookID(arr.get(i).getBookID());
                                    Book.setBookName(arr.get(i).getBookName());
                                    Book.setBookAuthor(arr.get(i).getBookAuthor());
                                    Book.setBookSite(arr.get(i).getBookSite());
                                    Book.setBookStatus(arr.get(i).getBookStatus());
                                    Book.setBookTag(arr.get(i).getBookTag());
                                    Book.setBookStar(arr.get(i).getBookStar());
                                    Book.setBookPublisher(arr.get(i).getBookPublisher());
                                    Book.setBookPubDate(arr.get(i).getBookPubDate());
                                    list3.add(Book);
                                }
                            }
                            else
                            {
                                DialogPane dpane=new DialogPane();
                                dpane.setContentText("找不到匹配项，"
                                        + "    请重新输入关键词进行检索！");
                                dpane.getButtonTypes().add(ButtonType.OK);
                                Button ok=(Button)dpane.lookupButton(ButtonType.OK);
                                Dialog <ButtonType>dia=new Dialog<ButtonType>();
                                dia.setDialogPane(dpane);
                                dia.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                                dia.show();

                            }
                        }
                    });
                }
                if(r.getText()=="按书号查")
                {
                    b31.setOnAction(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event)
                        {
                            tableview3.getItems().removeAll(list3);
                            tableview3.refresh();
                            tableview3.getItems().removeAll(list3);

                            Message mes=new Message();
                            mes.setData(text31.getText());
                            mes.setMessageType(MessageType.outputbybookidsearch);//表明操作类型
                            Message serveResponse=new Message();
                            Client client=new Client();
                            serveResponse=client.sendRequestToServer(mes);
                            ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();


                            if(arr.size()>0)
                            {
                                for(int i=0;i<arr.size();i++)
                                {
                                    Book Book=new Book();
                                    Book.setBookID(arr.get(i).getBookID());
                                    Book.setBookName(arr.get(i).getBookName());
                                    Book.setBookAuthor(arr.get(i).getBookAuthor());
                                    Book.setBookSite(arr.get(i).getBookSite());
                                    Book.setBookStatus(arr.get(i).getBookStatus());
                                    Book.setBookTag(arr.get(i).getBookTag());
                                    Book.setBookStar(arr.get(i).getBookStar());
                                    Book.setBookPublisher(arr.get(i).getBookPublisher());
                                    Book.setBookPubDate(arr.get(i).getBookPubDate());
                                    list3.add(Book);
                                }
                            }
                            else
                            {
                                DialogPane dpane=new DialogPane();
                                dpane.setContentText("找不到匹配项，"
                                        + "    请重新输入关键词进行检索！");
                                dpane.getButtonTypes().add(ButtonType.OK);
                                Button ok=(Button)dpane.lookupButton(ButtonType.OK);
                                Dialog <ButtonType>dia=new Dialog<ButtonType>();
                                dia.setDialogPane(dpane);
                                dia.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                                dia.show();
                            }
                        }
                    });
                }
                if(r.getText()=="按书名查")
                {
                    b31.setOnAction(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event)
                        {
                            tableview3.getItems().removeAll(list3);
                            tableview3.refresh();
                            tableview3.getItems().removeAll(list3);

                            Message mes=new Message();
                            mes.setData(text31.getText());
                            mes.setMessageType(MessageType.outputbybooknamesearch);//表明操作类型
                            Message serveResponse=new Message();
                            Client client=new Client();
                            serveResponse=client.sendRequestToServer(mes);
                            ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();

                            if(arr.size()>0)
                            {
                                for(int i=0;i<arr.size();i++)
                                {
                                    Book Book=new Book();
                                    Book.setBookID(arr.get(i).getBookID());
                                    Book.setBookName(arr.get(i).getBookName());
                                    Book.setBookAuthor(arr.get(i).getBookAuthor());
                                    Book.setBookSite(arr.get(i).getBookSite());
                                    Book.setBookStatus(arr.get(i).getBookStatus());
                                    Book.setBookTag(arr.get(i).getBookTag());
                                    Book.setBookStar(arr.get(i).getBookStar());
                                    Book.setBookPublisher(arr.get(i).getBookPublisher());
                                    Book.setBookPubDate(arr.get(i).getBookPubDate());
                                    list3.add(Book);
                                }
                            }
                            else
                            {


                                DialogPane dpane=new DialogPane();
                                dpane.setContentText("找不到匹配项，"
                                        + "    请重新输入关键词进行检索！");
                                dpane.getButtonTypes().add(ButtonType.OK);
                                Button ok=(Button)dpane.lookupButton(ButtonType.OK);
                                Dialog <ButtonType>dia=new Dialog<ButtonType>();
                                dia.setDialogPane(dpane);
                                dia.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                                dia.show();

                                ////

                                ok.setOnAction(new EventHandler<ActionEvent>()
                                {
                                    @Override
                                    public void handle(ActionEvent event)
                                    {

                                    }
                                });

                            }
                        }
                    });
                }
                if(r.getText()=="按作者查")
                {
                    b31.setOnAction(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event)
                        {
                            tableview3.getItems().removeAll(list3);
                            tableview3.refresh();
                            tableview3.getItems().removeAll(list3);

                            Message mes=new Message();
                            mes.setData(text31.getText());
                            mes.setMessageType(MessageType.outputbybookauthorsearch);//表明操作类型
                            Message serveResponse=new Message();
                            Client client=new Client();
                            serveResponse=client.sendRequestToServer(mes);
                            ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();

                            if(arr.size()>0)
                            {
                                for(int i=0;i<arr.size();i++)
                                {
                                    Book Book=new Book();
                                    Book.setBookID(arr.get(i).getBookID());
                                    Book.setBookName(arr.get(i).getBookName());
                                    Book.setBookAuthor(arr.get(i).getBookAuthor());
                                    Book.setBookSite(arr.get(i).getBookSite());
                                    Book.setBookStatus(arr.get(i).getBookStatus());
                                    Book.setBookTag(arr.get(i).getBookTag());
                                    Book.setBookStar(arr.get(i).getBookStar());
                                    Book.setBookPublisher(arr.get(i).getBookPublisher());
                                    Book.setBookPubDate(arr.get(i).getBookPubDate());
                                    list3.add(Book);
                                }
                            }
                            else
                            {
                                DialogPane dpane=new DialogPane();
                                dpane.setContentText("找不到匹配项，"
                                        + "    请重新输入关键词进行检索！");
                                dpane.getButtonTypes().add(ButtonType.OK);
                                Button ok=(Button)dpane.lookupButton(ButtonType.OK);
                                Dialog <ButtonType>dia=new Dialog<ButtonType>();
                                dia.setDialogPane(dpane);
                                dia.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                                dia.show();
                            }
                        }
                    });
                }
            }
        });
        TableColumn<Book,String> tc_bookID3=new TableColumn<Book,String>("书号");
        tableview3.getColumns().add(tc_bookID3);


        //自定义单元格  点击书号一列上的按钮 实现借书功能
        tc_bookID3.setPrefWidth(135);
        tc_bookID3.setCellFactory(new Callback<TableColumn<Book,String>,TableCell<Book,String>>()
        {

            @Override
            public TableCell<Book,String>call(TableColumn<Book,String> param)
            {
                TableCell<Book,String>cell=new TableCell<Book,String>()
                {
                    @Override
                    protected void updateItem(String item,boolean empty)
                    {
                        super.updateItem(item, empty);
                        if(empty==false&&item!=null)
                        {
                            HBox hbox=new HBox();
                            hbox.setPrefWidth(100);
                            Button button=new Button("借阅"+item);

                            button.setOnAction(new EventHandler<ActionEvent>()
                            {
                                @Override
                                public void handle(ActionEvent event)
                                {
//		                			BookDao BookDao=new BookDao();	
                                    Message mes=new Message();
                                    mes.setData(item);
                                    mes.setMessageType(MessageType.outputbybookidsearch);//表明操作类型
                                    Message serveResponse=new Message();
                                    Client client=new Client();
                                    serveResponse=client.sendRequestToServer(mes);
                                    ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();


                                    DialogPane dpane=new DialogPane();
                                    dpane.setContentText("确定要借走<"+item+">吗？");
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
                                            Message mes2=new Message();
                                            mes2.setData(item);
                                            mes2.setMessageType(MessageType.outputbybookidsearch);//表明操作类型
                                            Message serveResponse2=new Message();
                                            Client client2=new Client();
                                            serveResponse2=client2.sendRequestToServer(mes2);
                                            ArrayList<Book> arr=(ArrayList<Book>)serveResponse2.getData();
                                            Book Book=new Book();
                                            Book=arr.get(0);
                                            if(Book.getBookSite()==true&&Book.getBookStatus()==true)
                                            {
                                                Date currentTime = new Date();
                                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                String dateString = formatter.format(currentTime);
                                                String recordID=dateString.substring(0, 4)+dateString.substring(5, 7)
                                                        +dateString.substring(8, 10)+dateString.substring(11,13)
                                                        +dateString.substring(14, 16)+dateString.substring(17,19);
                                                String recordStartDate=dateString.substring(0, 4)+"-"+dateString.substring(5, 7)+"-"+dateString.substring(8, 10);//当前日期的string表达式
                                                String recordEndDate=" ";//没有还书 还书日期也 未定 所以缺省
//	 	     	    			    			LibOperate libOperate=new LibOperate();
//	 	     	    			    			LendRecord lendRecord=new LendRecord();

                                                Message mes=new Message();
                                                mes.setData(item);
                                                mes.setMessageType(MessageType.outputbybookidsearch);//表明操作类型
                                                Message serveResponse=new Message();
                                                Client client=new Client();
                                                serveResponse=client.sendRequestToServer(mes);
                                                ArrayList<Book> arr3=(ArrayList<Book>)serveResponse.getData();
                                                String bookName=arr3.get(0).getBookName();

//	 	     	    			    			String bookName=BookDao.SearchByBookID(item).get(0).getBookName();
                                                System.out.println("书名="+bookName);
                                                LendRecord lendRecord=new LendRecord();
                                                lendRecord.setRecordBookName(bookName);
                                                lendRecord.setRecordStudentID(stuID);
                                                lendRecord.setRecordBookID(item);
                                                lendRecord.setRecordID(recordID);
                                                lendRecord.setRecordStartDate(recordStartDate);
                                                lendRecord.setRecordEndDate(recordEndDate);
                                                lendRecord.setIsReturn(false);

                                                Message mes4=new Message();
                                                mes4.setData(lendRecord);
                                                mes4.setMessageType(MessageType.lendbook);//表明操作类型
                                                Message serveResponse4=new Message();
                                                Client client4=new Client();
                                                serveResponse4=client4.sendRequestToServer(mes);

//	 	     	    			    			libOperate.LendBook(lendRecord);

                                            }
                                            else
                                            {
                                                DialogPane dpane=new DialogPane();
                                                dpane.setContentText("该书<"+item+">不在馆或不可借，请再换一本把");
                                                Dialog <ButtonType>dia=new Dialog<ButtonType>();
                                                dia.setDialogPane(dpane);
                                                dia.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                                                dia.show();
                                            }
                                        }
                                    });
                                }
                            });
                            hbox.getChildren().add(button);
                            this.setGraphic(hbox);
                        }

                    }
                };
                return cell;
            }
        });
        tc_bookID3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<Book,String> param)
            {
                SimpleStringProperty bookID=new SimpleStringProperty(param.getValue().getBookID());
                return bookID;
            }
        });


        TableColumn<Book,String> tc_bookName3=new TableColumn<Book,String>("书名");
        tableview3.getColumns().add(tc_bookName3);
        tc_bookName3.setPrefWidth(80);
        tc_bookName3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<Book,String> param)
            {
                SimpleStringProperty bookName=new SimpleStringProperty(param.getValue().getBookName());
                return bookName;
            }
        });

        TableColumn<Book,String> tc_bookAuthor3=new TableColumn<Book,String>("作者");
        tableview3.getColumns().add(tc_bookAuthor3);
        tc_bookAuthor3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<Book,String> param)
            {
                SimpleStringProperty bookAuthor=new SimpleStringProperty(param.getValue().getBookAuthor());
                return bookAuthor;
            }
        });

        TableColumn<Book,String> tc_bookTag3=new TableColumn<Book,String>("标签");
        tableview3.getColumns().add(tc_bookTag3);
        tc_bookTag3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<Book,String> param)
            {
                SimpleStringProperty bookTag=new SimpleStringProperty(param.getValue().getBookTag());
                return bookTag;
            }
        });

        TableColumn<Book,String> tc_bookPublisher3=new TableColumn<Book,String>("出版社");
        tableview3.getColumns().add(tc_bookPublisher3);
        tc_bookPublisher3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<Book,String> param)
            {
                SimpleStringProperty bookPublisher=new SimpleStringProperty(param.getValue().getBookPublisher());
                return bookPublisher;
            }
        });

        TableColumn<Book,String> tc_bookPubDate3=new TableColumn<Book,String>("出版日期");
        tableview3.getColumns().add(tc_bookPubDate3);
        tc_bookPubDate3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<Book,String> param)
            {
                SimpleStringProperty bookPubDate=new SimpleStringProperty(param.getValue().getBookPubDate());
                return bookPubDate;
            }
        });

        TableColumn<Book,Boolean> tc_bookSite3=new TableColumn<Book,Boolean>("在馆？");
        tableview3.getColumns().add(tc_bookSite3);
        tc_bookSite3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,Boolean>,ObservableValue<Boolean>>()
        {
            @Override
            public ObservableValue<Boolean>call(TableColumn.CellDataFeatures<Book,Boolean> param)
            {
                SimpleBooleanProperty bookSite=new SimpleBooleanProperty(param.getValue().getBookSite());
                return bookSite;
            }
        });

        TableColumn<Book,Boolean> tc_bookStatus3=new TableColumn<Book,Boolean>("可借？");
        tableview3.getColumns().add(tc_bookStatus3);
        //加载数据
        tc_bookStatus3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,Boolean>,ObservableValue<Boolean>>()
        {
            @Override
            public ObservableValue<Boolean>call(TableColumn.CellDataFeatures<Book,Boolean> param)
            {
                SimpleBooleanProperty bookStatus=new SimpleBooleanProperty(param.getValue().getBookStatus());
                return bookStatus;
            }
        });

        tableview3.setPrefWidth(900);
        ap3.setLeftAnchor(tableview3, 75.0);
        ap3.getChildren().add(tableview3);
        ap3.setTopAnchor(tableview3, 200.0);
        return ap3;
    }


    public Tab tabQuery(String stuID)
    {
        //面向学生的第三个tab  实现图书查询
        Tab tab3=new Tab("图书查询");
        tab3.setClosable(false);
        tab3.setContent(ap3(stuID));
        return tab3;
    }
}
