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
import javafx.util.Callback;
import util.MessageType;
import vo.Book;
import vo.LendRecord;
import vo.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Library_tab_lib {
//    private Student student;
//    public Library_tab_lib(Student student){
//        this.student = student;
//    }

    public Tab tabLib(String stuID)
    {
        //面向学生的第二个tab  实现整馆书的浏览和借阅 好书推荐
        Tab tab2=new Tab("馆藏图书");
        tab2.setClosable(false);
        AnchorPane ap2=new AnchorPane();
        tab2.setContent(ap2);

//		BookDao dao2=new BookDao();
        ObservableList<Book> list2= FXCollections.observableArrayList();
        TableView<Book> tableview2=new TableView<Book>(list2);
        ToggleGroup tg2=new ToggleGroup();//设置单选box
        RadioButton r21=new RadioButton("图书总览");
        RadioButton r22=new RadioButton("学业推荐");
        RadioButton r23=new RadioButton("休闲推荐");
        r21.setToggleGroup(tg2);//单选按钮成组
        r22.setToggleGroup(tg2);
        r23.setToggleGroup(tg2);
        HBox hbox2=new HBox(10);//设置单选box  
        hbox2.getChildren().addAll(r21,r22,r23);
        ap2.setTopAnchor(hbox2, 50.0);
        ap2.setLeftAnchor(hbox2, 100.0);
        ap2.getChildren().add(hbox2);
        ap2.getChildren().add(tableview2);
        tableview2.setPrefWidth(500);
        ap2.setLeftAnchor(tableview2, 75.0);
        ap2.setTopAnchor(tableview2, 200.0);


        TableColumn<Book,String> tc_BookID2=new TableColumn<Book,String>("书号");
        tableview2.getColumns().add(tc_BookID2);

        TableColumn<Book,String> tc_BookName2=new TableColumn<Book,String>("书名");
        tableview2.getColumns().add(tc_BookName2);

        TableColumn<Book,String> tc_BookAuthor2=new TableColumn<Book,String>("作者");
        tableview2.getColumns().add(tc_BookAuthor2);

        TableColumn<Book,Boolean> tc_BookSite2=new TableColumn<Book,Boolean>("在馆？");
        tableview2.getColumns().add(tc_BookSite2);

        TableColumn<Book,Boolean> tc_BookStatus2=new TableColumn<Book,Boolean>("可借？");
        tableview2.getColumns().add(tc_BookStatus2);

        TableColumn<Book,String> tc_BookTag2=new TableColumn<Book,String>("标签");
        tableview2.getColumns().add(tc_BookTag2);

        TableColumn<Book,String> tc_BookPublisher2=new TableColumn<Book,String>("出版社");
        tableview2.getColumns().add(tc_BookPublisher2);

        TableColumn<Book,String> tc_BookPubDate2=new TableColumn<Book,String>("出版日期");
        tableview2.getColumns().add(tc_BookPubDate2);

        //String stuId = stuID;
        tg2.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<?extends Toggle> observable, Toggle oldValue, Toggle newValue)
            {
                String temp = stuID;
                RadioButton r=(RadioButton)newValue;
                System.out.println(r.getText());
                if(r.getText()=="图书总览")
                {
                    Message mes=new Message();
                    mes.setMessageType(MessageType.outputallbooks);//表明操作类型
                    Message serveResponse=new Message();
                    Client client=new Client();
                    serveResponse=client.sendRequestToServer(mes);
                    ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();


                    tableview2.refresh();
                    tableview2.getItems().removeAll(list2);

//	    			arr=dao2.SearchIndistinct("");
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
                        list2.add(Book);
                    }

                }
                if(r.getText()=="学业推荐")
                {
                    String stuID=temp;//给推荐函数留的口子
                    Message mes=new Message();
                    mes.setData(stuID);
                    mes.setMessageType(MessageType.outputallbooksbystudy);//表明操作类型
                    Message serveResponse=new Message();
                    Client client=new Client();
                    serveResponse=client.sendRequestToServer(mes);
                    ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();

//	    			LibOperate libOperate=new LibOperate();
                    tableview2.refresh();
                    tableview2.getItems().removeAll(list2);
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
                        list2.add(Book);
                    }
                }
                if(r.getText()=="休闲推荐")
                {


                    Message mes=new Message();
                    mes.setMessageType(MessageType.outputallbooksbyhappy);//表明操作类型
                    Message serveResponse=new Message();
                    Client client=new Client();
                    serveResponse=client.sendRequestToServer(mes);
                    ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();

                    //tableview2.getColumns().remove(1);
                    tableview2.refresh();
                    tableview2.getItems().removeAll(list2);
                    //System.out.println("大小="+arr.size());
                    for(int i=0;i<arr.size();i++)
                    {
                        String str1="人文";String str2="小说";String str3="杂志";
                        if(arr.get(i).getBookStar()>5&&(arr.get(i).getBookTag().equals(str1)==true
                                ||arr.get(i).getBookTag().equals(str2)==true||arr.get(i).getBookTag().equals(str3)==true))
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
                            list2.add(Book);
                        }
                    }

                }
            }



        });

        tc_BookID2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<Book,String> param)
            {
                SimpleStringProperty bookID=new SimpleStringProperty(param.getValue().getBookID());
                return bookID;
            }
        });
        tc_BookName2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<Book,String> param)
            {
                SimpleStringProperty bookName=new SimpleStringProperty(param.getValue().getBookName());
                return bookName;
            }
        });
        tc_BookAuthor2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<Book,String> param)
            {
                SimpleStringProperty bookAuthor=new SimpleStringProperty(param.getValue().getBookAuthor());
                return bookAuthor;
            }
        });

        tc_BookSite2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,Boolean>,ObservableValue<Boolean>>()
        {
            @Override
            public ObservableValue<Boolean>call(TableColumn.CellDataFeatures<Book,Boolean> param)
            {
                SimpleBooleanProperty bookSite=new SimpleBooleanProperty(param.getValue().getBookSite());
                return bookSite;
            }
        });
        tc_BookStatus2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,Boolean>,ObservableValue<Boolean>>()
        {
            @Override
            public ObservableValue<Boolean>call(TableColumn.CellDataFeatures<Book,Boolean> param)
            {
                SimpleBooleanProperty bookStatus=new SimpleBooleanProperty(param.getValue().getBookSite());
                return bookStatus;
            }
        });
        tc_BookTag2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<Book,String> param)
            {
                SimpleStringProperty bookTag=new SimpleStringProperty(param.getValue().getBookTag());
                return bookTag;
            }
        });

        tc_BookPublisher2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<Book,String> param)
            {
                SimpleStringProperty bookPublisher=new SimpleStringProperty(param.getValue().getBookPublisher());
                return bookPublisher;
            }
        });

        tc_BookPubDate2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<Book,String> param)
            {
                SimpleStringProperty bookPubDate=new SimpleStringProperty(param.getValue().getBookPubDate());
                return bookPubDate;
            }
        });

        tc_BookID2.setCellFactory(new Callback<TableColumn<Book,String>,TableCell<Book,String>>()
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

//                                    BookDao BookDao=new BookDao();


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





                                            Message mes=new Message();
                                            mes.setMessageType(MessageType.outputbybookidsearch);//表明操作类型
                                            mes.setData(item);
                                            Message serveResponse=new Message();
                                            Client client=new Client();
                                            serveResponse=client.sendRequestToServer(mes);
                                            ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();


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
                                                LendRecord lendRecord=new LendRecord();
                                                lendRecord.setRecordStudentID(stuID);
                                                lendRecord.setRecordBookID(item);

                                                Message mes2=new Message();
                                                mes2.setData(item);
                                                mes2.setMessageType(MessageType.outputbybookidsearch);//表明操作类型
                                                Message serveResponse3=new Message();
                                                Client client2=new Client();
                                                serveResponse3=client2.sendRequestToServer(mes);
                                                ArrayList<Book> arr2=(ArrayList<Book>)serveResponse3.getData();
                                                String bookName=arr2.get(0).getBookName();

                                                lendRecord.setRecordBookName(bookName);
                                                lendRecord.setRecordID(recordID);
                                                lendRecord.setRecordStartDate(recordStartDate);
                                                lendRecord.setRecordEndDate(recordEndDate);
                                                lendRecord.setIsReturn(false);

                                                Message mes3=new Message();
                                                mes3.setData(lendRecord);
                                                mes3.setMessageType(MessageType.lendbook);//表明操作类型
                                                Message serveResponse4=new Message();

                                                Client client3=new Client();
                                                serveResponse4=client3.sendRequestToServer(mes3);
//		    					    			ArrayList<LendRecord> arr2=(ArrayList<LendRecord>)serveResponse.getData();
//		    					    			String bookName=arr2.get(0).getRecordBookName();

//	 	     	    			    			libOperate.LendBook(lendRecord);

                                            }
                                            else
                                            {
                                                DialogPane dpane=new DialogPane();
                                                dpane.setContentText("该书<"+item+">不在馆或不可借，请再换一本吧");
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

                            });

                            hbox.getChildren().add(button);

                            this.setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        });
        tableview2.setPrefWidth(700);
        return tab2;
    }

}
