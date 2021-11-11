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

public class Library_tab_myLend {
//    private Student student;
//    public Library_tab_myLend (Student student){
//        this.student = student;
//    }

    public AnchorPane ap1(String studentID)//这是tab1上的ap1
    {
        AnchorPane ap1=new AnchorPane();
        ObservableList<LendRecord> list1= FXCollections.observableArrayList();
        TableView<LendRecord> tableview1=new TableView<LendRecord>(list1);
        ap1.getChildren().add(tableview1);


//	   LendRecordDao dao1=new LendRecordDao();
        tableview1.setPrefWidth(500);
        ap1.setLeftAnchor(tableview1, 75.0);
        ap1.setTopAnchor(tableview1, 200.0);




        tableview1.setPrefWidth(500);
        ap1.setLeftAnchor(tableview1, 75.0);
        ap1.setTopAnchor(tableview1, 200.0);

        ToggleGroup tg1=new ToggleGroup();//设置单选box
        RadioButton r11=new RadioButton("我的待还");
        RadioButton r12=new RadioButton("我的已还");
        r11.setToggleGroup(tg1);//单选按钮成组
        r12.setToggleGroup(tg1);
        HBox hbox1=new HBox(10);//设置单选box
        hbox1.getChildren().addAll(r11,r12);



        TableColumn<LendRecord,String> tc_lendRecordStartDate1=new TableColumn<LendRecord,String>("借阅时间");
        tableview1.getColumns().add(tc_lendRecordStartDate1);
        tableview1.getItems().removeAll(list1);
        tc_lendRecordStartDate1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LendRecord,String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<LendRecord,String> param)
            {
                SimpleStringProperty recordStartDate=new SimpleStringProperty(param.getValue().getRecordStartDate());
                return recordStartDate;
            }
        });
        TableColumn<LendRecord,String> tc_lendRecordEndDate1=new TableColumn<LendRecord,String>("退还时间");
        tableview1.getColumns().add(tc_lendRecordEndDate1);
        tc_lendRecordEndDate1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LendRecord,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<LendRecord,String> param)
            {
                SimpleStringProperty recordEndDate=new SimpleStringProperty(param.getValue().getRecordEndDate());
                return recordEndDate;
            }
        });
        TableColumn<LendRecord,String> tc_lendRecordBookName1=new TableColumn<LendRecord,String>("书名");
        tableview1.getColumns().add(tc_lendRecordBookName1);
        tc_lendRecordBookName1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LendRecord,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<LendRecord,String> param)
            {
//		    	System.out.println("差不进去的书号="+param.getValue().getRecordBookID());
                Message mes=new Message();
                mes.setUserId(param.getValue().getRecordBookID());
                mes.setMessageType(MessageType.outputbybookidsearch);//表明操作类型
                Message serveResponse=new Message();
                Client client=new Client();
                serveResponse=client.sendRequestToServer(mes);
                ArrayList<Book> arr=(ArrayList<Book>)serveResponse.getData();
                String str=arr.get(0).getBookName();
                SimpleStringProperty recordBookName=new SimpleStringProperty(str);
//		    	LibBookDao dao=new LibBookDao();
//		    	String str=dao.SearchByBookID(param.getValue().getRecordBookID()).get(0).getBookName();
//		    	SimpleStringProperty recordBookName=new SimpleStringProperty(str);
                return recordBookName;
            }
        });

        TableColumn<LendRecord,String> tc_lendRecordID1=new TableColumn<LendRecord,String>("借阅序号");
        tg1.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<?extends Toggle>observable,Toggle oldValue,Toggle newValue)
            {
                RadioButton r=(RadioButton)newValue;
                //System.out.println(r.getText());
                if(r.getText()=="我的待还")
                {
                    //System.out.println("到了我的待还");
                    //
                    tableview1.getItems().removeAll(list1);
                    //

                    tc_lendRecordID1.setVisible(true);
                    tc_lendRecordEndDate1.setVisible(false);


                    Message mes=new Message();
                    //mes.setUserId(studentID);
                    mes.setData(studentID);
//	    			mes.setExtraMessage(studentID);
                    mes.setMessageType(MessageType.outputbysearchbyrecordstudentidnotreturn);//表明操作类型
                    Message serveResponse=new Message();
                    Client client=new Client();
                    serveResponse=client.sendRequestToServer(mes);
                    ArrayList<LendRecord> arr=(ArrayList<LendRecord>)serveResponse.getData();

                    if(arr.size()>0)
                    {
                        for(int i=0;i<arr.size();i++)
                        {
                            LendRecord lendRecord=new LendRecord();
                            lendRecord.setRecordID(arr.get(i).getRecordID());
                            lendRecord.setRecordStudentID(arr.get(i).getRecordStudentID());
                            lendRecord.setRecordBookID(arr.get(i).getRecordBookID());
                            lendRecord.setRecordStartDate(arr.get(i).getRecordStartDate());
                            lendRecord.setRecordEndDate(arr.get(i).getRecordEndDate());
                            lendRecord.setIsReturn(arr.get(i).getIsReturn());
                            list1.add(lendRecord);
                        }
                        //////////
                        tc_lendRecordID1.setCellFactory(new Callback<TableColumn<LendRecord,String>,TableCell<LendRecord,String>>()
                        {

                            @Override
                            public TableCell<LendRecord,String>call(TableColumn<LendRecord,String> param)
                            {
                                TableCell<LendRecord,String>cell=new TableCell<LendRecord,String>()
                                {
                                    @Override
                                    protected void updateItem(String item,boolean empty)
                                    {
                                        super.updateItem(item, empty);

                                        if(empty==false&&item!=null)
                                        {
                                            HBox hbox=new HBox();
                                            hbox.setPrefWidth(100);
                                            Button button=new Button("退还"+item);

                                            button.setOnAction(new EventHandler<ActionEvent>()
                                            {
                                                @Override
                                                public void handle(ActionEvent event)
                                                {

//	    			                                    LendRecordDao lendRecordDao=new LendRecordDao();

                                                    Message mes=new Message();
                                                    mes.setExtraMessage(studentID);
                                                    mes.setMessageType(MessageType.outputbysearchbyrecordstudentidnotreturn);//表明操作类型
                                                    Message serveResponse=new Message();
                                                    Client client=new Client();
                                                    serveResponse=client.sendRequestToServer(mes);
                                                    ArrayList<LendRecord> arr=(ArrayList<LendRecord>)serveResponse.getData();


                                                    RadioButton r1=new RadioButton("1");
                                                    RadioButton r2=new RadioButton("2");
                                                    RadioButton r3=new RadioButton("3");
                                                    RadioButton r4=new RadioButton("4");
                                                    RadioButton r5=new RadioButton("5");
                                                    RadioButton r6=new RadioButton("6");
                                                    RadioButton r7=new RadioButton("7");
                                                    RadioButton r8=new RadioButton("8");
                                                    RadioButton r9=new RadioButton("9");
                                                    RadioButton r10=new RadioButton("10");
                                                    ToggleGroup tg=new ToggleGroup();
                                                    r1.setToggleGroup(tg);
                                                    r2.setToggleGroup(tg);
                                                    r3.setToggleGroup(tg);
                                                    r4.setToggleGroup(tg);
                                                    r5.setToggleGroup(tg);
                                                    r6.setToggleGroup(tg);
                                                    r7.setToggleGroup(tg);
                                                    r8.setToggleGroup(tg);
                                                    r9.setToggleGroup(tg);
                                                    r10.setToggleGroup(tg);
                                                    tg.selectToggle(r1);
                                                    // System.out.println("此时"+tg.getUserData().toString());
                                                    HBox hbox=new HBox(5);//设置单选box
                                                    hbox.getChildren().addAll(r1,r2,r3,r4,r5,r6,r7,r8,r9,r10);


                                                    DialogPane dpane=new DialogPane();
                                                    dpane.setContentText("确定要退还<"+item+">吗？  请为该图书打分吧！");
                                                    dpane.getChildren().add(hbox);
                                                    dpane.setPrefWidth(500);
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
//	    				     	    			    			LibOperate libOperate=new LibOperate();
                                                            LendRecord lendRecord=new LendRecord();
//	    				     	    			    			LendRecordDao lendRecordDao=new LendRecordDao();

//	    				     	    			    			lendRecord=lendRecordDao.SearchByRecordID(item).get(0);
//	    				     	    			    			lendRecord.setIsReturn(true);
//

                                                            Message mes4=new Message();
                                                            mes4.setExtraMessage(item);
                                                            mes4.setMessageType(MessageType.outputbysearchbyrecordid);//表明操作类型
                                                            Message serveResponse4=new Message();
                                                            Client client4=new Client();
                                                            serveResponse4=client4.sendRequestToServer(mes4);
                                                            ArrayList<LendRecord> arr4=(ArrayList<LendRecord>)serveResponse4.getData();

                                                            lendRecord=arr4.get(0);


                                                            Date currentTime = new Date();
                                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                            String dateString = formatter.format(currentTime);
                                                            String recordEndDate=dateString.substring(0, 4)+"-"+dateString.substring(5, 7)+"-"+dateString.substring(8, 10);//当前日期的string表达式
                                                            lendRecord.setRecordEndDate(recordEndDate);
//
                                                            //libOperate.ReturnBook(lendRecord);


                                                            Message mes=new Message();
                                                            mes.setData(lendRecord);
                                                            mes.setMessageType(MessageType.returnbook);//表明操作类型
                                                            Message serveResponse=new Message();
                                                            Client client=new Client();
                                                            serveResponse=client.sendRequestToServer(mes);


                                                            tableview1.refresh();
                                                            try
                                                            {
                                                                tabMyLend( studentID);
                                                            }
                                                            catch(Exception e)
                                                            {
                                                                e.printStackTrace();
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

                    }
                    else
                    {
                        DialogPane dpane=new DialogPane();
                        dpane.setContentText("很棒，你的书已经都还给图书馆了！");
                        dpane.getButtonTypes().add(ButtonType.OK);
                        Button ok=(Button)dpane.lookupButton(ButtonType.OK);
                        Dialog <ButtonType>dia=new Dialog<ButtonType>();
                        dia.setDialogPane(dpane);
                        dia.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                        dia.show();
                    }
                }
                if(r.getText()=="我的已还")
                {
                    //System.out.println("到了我的已还");
                    tc_lendRecordEndDate1.setVisible(true);
                    tc_lendRecordID1.setVisible(false);
                    tableview1.getItems().removeAll(list1);


                    Message mes=new Message();
                    mes.setData(studentID);
                    mes.setMessageType(MessageType.outputbysearchbyrecordstudentidisreturn);//表明操作类型
                    Message serveResponse=new Message();
                    Client client=new Client();
                    serveResponse=client.sendRequestToServer(mes);
                    ArrayList<LendRecord> arr=(ArrayList<LendRecord>)serveResponse.getData();


                    if(arr.size()>0)
                    {
                        for(int i=0;i<arr.size();i++)
                        {
                            LendRecord lendRecord=new LendRecord();
                            lendRecord.setRecordID(arr.get(i).getRecordID());
                            lendRecord.setRecordStudentID(arr.get(i).getRecordStudentID());
                            lendRecord.setRecordBookID(arr.get(i).getRecordBookID());
                            lendRecord.setRecordStartDate(arr.get(i).getRecordStartDate());
                            lendRecord.setRecordEndDate(arr.get(i).getRecordEndDate());
                            lendRecord.setIsReturn(arr.get(i).getIsReturn());
                            list1.add(lendRecord);
                        }
                    }
                    else
                    {
                        DialogPane dpane=new DialogPane();
                        dpane.setContentText("很好，你还从未还过书！");
                        dpane.getButtonTypes().add(ButtonType.OK);
                        Button ok=(Button)dpane.lookupButton(ButtonType.OK);
                        Dialog <ButtonType>dia=new Dialog<ButtonType>();
                        dia.setDialogPane(dpane);
                        dia.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                        dia.show();
                    }
                }
            }
        });
        tableview1.getColumns().add(tc_lendRecordID1);
        tc_lendRecordID1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LendRecord,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<LendRecord,String> param)
            {
                SimpleStringProperty recordID=new SimpleStringProperty(param.getValue().getRecordID());
                return recordID;
            }
        });

        TableColumn<LendRecord,String> tc_lendBookID1=new TableColumn<LendRecord,String>("借阅书号");
        tableview1.getColumns().add(tc_lendBookID1);
        tc_lendBookID1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LendRecord,String>,ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String>call(TableColumn.CellDataFeatures<LendRecord,String> param)
            {
                SimpleStringProperty recordBookID=new SimpleStringProperty(param.getValue().getRecordBookID());
                return recordBookID;
            }
        });



        TableColumn<LendRecord,Boolean> tc_lendRecordIsReturn=new TableColumn<LendRecord,Boolean>("是否已退");
        tableview1.getColumns().add(tc_lendRecordIsReturn);
        tc_lendRecordIsReturn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LendRecord,Boolean>,ObservableValue<Boolean>>()
        {
            @Override
            public ObservableValue<Boolean>call(TableColumn.CellDataFeatures<LendRecord,Boolean> param)
            {
                SimpleBooleanProperty lendRecordIsReturn=new SimpleBooleanProperty(param.getValue().getIsReturn());
                return lendRecordIsReturn;
            }
        });
        ap1.setTopAnchor(hbox1, 50.0);
        ap1.setLeftAnchor(hbox1, 100.0);
        ap1.getChildren().add(hbox1);
        return ap1;

    }



    public Tab tabMyLend(String studentID)
    {
        //面向学生界面的第一个tab 实现个人历史记录查询

        Tab tab1=new Tab("我的图书");
        tab1.setClosable(false);

        tab1.setContent(ap1(studentID));

        return tab1;
    }

}
