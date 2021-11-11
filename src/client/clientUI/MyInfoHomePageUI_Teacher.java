package client.clientUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import client.socket.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import vo.Login;
import vo.Message;
import vo.User;

import javax.swing.*;

import java.awt.TrayIcon.MessageType;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import vo.Student;
import vo.Teacher;
import vo.Doctor;
import vo.Retailer;

public class MyInfoHomePageUI_Teacher {
    Teacher teacher;
    MainFrame_Teacher mainFrame_teacher;
    private XSSFWorkbook workbook;

    public MyInfoHomePageUI_Teacher(Teacher teacher, MainFrame_Teacher mainFrame_teacher){

        this.teacher = teacher;
        this.mainFrame_teacher = mainFrame_teacher;
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


    public void outputExcel(String filename){
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(new File(filename));
            workbook.write(fos);
            fos.close();
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void exportExcel(String fileName, ArrayList<Student> list)
    {
        //workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("Student");
        sheet.setDefaultColumnWidth((short)5);
        XSSFRow row=sheet.createRow(0);
        XSSFCell cell1=row.createCell(0);
        cell1.setCellType(CellType.STRING);
        cell1.setCellValue(new XSSFRichTextString("一卡通"));

        XSSFCell cell2=row.createCell(1);
        cell2.setCellType(CellType.STRING);
        cell2.setCellValue(new XSSFRichTextString("姓名"));

        XSSFCell cell3=row.createCell(2);
        cell3.setCellType(CellType.STRING);
        cell3.setCellValue(new XSSFRichTextString("学号"));

        XSSFCell cell4=row.createCell(3);
        cell4.setCellType(CellType.STRING);
        cell4.setCellValue(new XSSFRichTextString("院系"));

        XSSFCell cell5=row.createCell(4);
        cell5.setCellType(CellType.STRING);
        cell5.setCellValue(new XSSFRichTextString("绩点"));

        XSSFCell cell6=row.createCell(5);
        cell6.setCellType(CellType.STRING);
        cell6.setCellValue(new XSSFRichTextString("总学分"));

        XSSFCell cell7=row.createCell(6);
        cell7.setCellType(CellType.STRING);
        cell7.setCellValue(new XSSFRichTextString("性别"));

        for(int i=0;i<list.size();i++) {
            XSSFRow datarow=sheet.createRow(i+1);
            Student sss=list.get(i);
            XSSFCell datacell1=datarow.createCell(0);
            datacell1.setCellType(CellType.STRING);
            datacell1.setCellValue(sss.getId());

            XSSFCell datacell2=datarow.createCell(1);
            datacell2.setCellType(CellType.STRING);
            datacell2.setCellValue(sss.getName());

            XSSFCell datacell3=datarow.createCell(2);
            datacell3.setCellType(CellType.STRING);
            datacell3.setCellValue(sss.getNumber());

            XSSFCell datacell4=datarow.createCell(3);
            datacell4.setCellType(CellType.STRING);
            datacell4.setCellValue(sss.getMajorId());

            XSSFCell datacell5=datarow.createCell(4);
            datacell5.setCellType(CellType.STRING);
            datacell5.setCellValue(sss.getGPA());

            XSSFCell datacell6=datarow.createCell(5);
            datacell6.setCellType(CellType.STRING);
            datacell6.setCellValue(sss.getCredit());

            XSSFCell datacell7=datarow.createCell(6);
            datacell7.setCellType(CellType.STRING);
            datacell7.setCellValue(sss.getSex());


        }
        outputExcel(fileName);
    }

    private String myTitle(Integer t){
        String title ="";
        if(t == 0) title = "助教";
        else if(t == 1) title = "讲师";
        else if(t == 2) title = "副教授";
        else title = "教授";
        return title;
    }


    public AnchorPane infoHomePage()
    {
        Client client = new Client();
        AnchorPane an=new AnchorPane();
        try {
            JFXTabPane tabpane=new JFXTabPane();
            tabpane.setPrefSize(1058, 580);
            an.getChildren().add(tabpane);
            Tab tab0 = new Tab("个人信息");
            Tab tab1 = new Tab("查询学生信息");
            Tab tab2 = new Tab("导入/导出Excel文件");

            tab0.setClosable(false);
            tab1.setClosable(false);
            tab2.setClosable(false);
            tabpane.getTabs().addAll(tab0,tab1,tab2);


   //////////////////////////////

            AnchorPane an0=new AnchorPane();

            String id = teacher.getId();

            int l_x = 400;
            int l_y = 50;
            int l_spacing = 54;
            int t_x = 520;
            int t_y = 50;
            int t_spacing = 52;
            Circle cir = new Circle();

            //换头像系统
            File file = new File("./src/client/Image/" + id + ".jpg");
            String Path = "";
            if(file.exists()==true){
                Path = "/client/Image/" + id + ".jpg";
            }
            else {
                Path = "/client/Image/HeadPortrait_Teacher.png";
            }

            Image image=new Image(Path);
            ImageView iv=new ImageView(image);
            iv.setPreserveRatio(false);
            iv.setFitWidth(130);
            iv.setFitHeight(130);
            iv.setLayoutX(180);
            iv.setLayoutY(20);
            cir.setCenterX(65);
            cir.setCenterY(65);
            cir.setRadius(60);
            iv.setClip(cir);

            JFXButton headPChange = new JFXButton("上传新头像");
            headPChange.setStyle("-fx-font-size: 14px;-jfx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-text-fill: WHITE;");
            headPChange.setLayoutX(196);
            headPChange.setLayoutY(200);
            headPChange.setPrefWidth(100);

            headPChange.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.jpg");
                    fileChooser.getExtensionFilters().add(pngFilter);
                    File file = fileChooser.showOpenDialog( headPChange.getScene().getWindow() );

                    Image image1 = new Image(file.toURI().toString());
                    iv.setImage(image1);
                    iv.setPreserveRatio(false);
                    iv.setFitWidth(130);
                    iv.setFitHeight(130);
                    iv.setLayoutX(180);
                    iv.setLayoutY(20);
                    cir.setCenterX(65);
                    cir.setCenterY(65);
                    cir.setRadius(60);
                    iv.setClip(cir);

                    Image image2 = new Image(file.toURI().toString(),70, 70,false,true);
                    mainFrame_teacher.tool_changeHeadP(image2);

                    try {
                        savePic(new FileInputStream(file),id+".jpg");

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });


            Label l1=new Label("姓名");
            l1.setFont(new Font(14));
            //temp=ss.getName();
            JFXTextField tex1 = new JFXTextField();
            tex1.setText(teacher.getName());
            tex1.setLabelFloat(false);
            tex1.setEditable(false);
            tex1.setAlignment(Pos.CENTER);
            tex1.setMouseTransparent(true);
            tex1.setFocusTraversable(false);
            l1.setLayoutX(l_x);
            l1.setLayoutY(l_y+0*l_spacing);
            tex1.setLayoutX(t_x);
            tex1.setLayoutY(t_y+0*t_spacing);


            Label l2=new Label("工号");
            l2.setFont(new Font(14));
            JFXTextField tx2 = new JFXTextField();
            tx2.setText(teacher.getId());
            tx2.setLabelFloat(false);
            tx2.setEditable(false);
            tx2.setAlignment(Pos.CENTER);
            tx2.setMouseTransparent(true);
            tx2.setFocusTraversable(false);
            l2.setLayoutX(l_x);
            l2.setLayoutY(l_y+1*l_spacing);
            tx2.setLayoutX(t_x);
            tx2.setLayoutY(t_y+1*t_spacing);


            Label l3=new Label("职称");
            l3.setFont(new Font(14));
            JFXTextField tx3 = new JFXTextField();
            tx3.setText(myTitle(teacher.getTitle()));
            tx3.setLabelFloat(false);
            tx3.setEditable(false);
            tx3.setAlignment(Pos.CENTER);
            tx3.setMouseTransparent(true);
            tx3.setFocusTraversable(false);
            l3.setLayoutX(l_x);
            l3.setLayoutY(l_y+2*l_spacing);
            tx3.setLayoutX(t_x);
            tx3.setLayoutY(t_y+2*t_spacing);


            Label l4=new Label("专业");
            l4.setFont(new Font(14));
            JFXTextField tx4 = new JFXTextField();
            tx4.setText(String.valueOf(teacher.getMajorId()));
            tx4.setLabelFloat(false);
            tx4.setEditable(false);
            tx4.setAlignment(Pos.CENTER);
            tx4.setMouseTransparent(true);
            tx4.setFocusTraversable(false);
            l4.setLayoutX(l_x);
            l4.setLayoutY(l_y+3*l_spacing);
            tx4.setLayoutX(t_x);
            tx4.setLayoutY(t_y+3*t_spacing);




            an0.getChildren().addAll(l1,headPChange, tex1,l2,tx2,l3,tx3,l4,tx4,iv);
            tab0.setContent(an0);



//////////////////////////////选项1页面内容(教师查询)：
            AnchorPane an1=new AnchorPane();
            Text tx1=new Text("说明：若使用精确查询，则不需要选择学院。");
            tx1.setLayoutX(350);
            tx1.setLayoutY(70);
            Text text2=new Text("若选择学院检索，则会输出该学院学生信息。");
            text2.setLayoutX(390);
            text2.setLayoutY(100);
            Label label=new Label("精确查询：");
            TextField tf=new TextField();
            tf.setPromptText("");
            HBox h1=new HBox();
            h1.getChildren().addAll(label,tf);
            h1.setLayoutX(370);
            h1.setLayoutY(200);
            Label label1=new Label("按学院检索：");
            ChoiceBox cb=new ChoiceBox();
            cb.getItems().addAll("能源与环境学院","计算机学院","机械学院","建筑学院");
            cb.getSelectionModel().selectFirst();
            cb.setPrefHeight(40);
            HBox h2=new HBox();
            h2.getChildren().addAll(label1,cb);
            h2.setLayoutX(370);
            h2.setLayoutY(300);
            Button bt=new Button("确认");
            bt.setPrefWidth(100);
            bt.setLayoutX(450);
            bt.setLayoutY(412);
            bt.setPrefHeight(40);
            bt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event)
                {
                    AnchorPane an11=new AnchorPane();
                    Button ret=new Button("返回上页");
                    ret.setPrefWidth(105);
                    ret.setLayoutX(453);
                    ret.setLayoutY(412);
                    tab1.setContent(an11);
                    ret.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event)
                        {
                            tab1.setContent(an1);
                        }
                    });

                    if(!tf.getText().equals("")) //如果输入一卡通号的地方不为空，那不管下面是什么，显示一个人的信息。
                    {
                        Message mes1 = new Message();
                        mes1.setMessageType("QUERY_STUDENT");
                        mes1.setUserId(tf.getText());
                        Message serverResponse1 = client.sendRequestToServer(mes1);
                        Student ss= (Student) serverResponse1.getData();
                        ObservableList<Student> list1= FXCollections.observableArrayList();
                        list1.add(ss);

                        TableView<Student>  tv=new TableView<Student>(list1);
                        tv.setPrefWidth(1035);
                        tv.setPrefHeight(410);
                        TableColumn<Student,String> tc_uID=new TableColumn<Student,String>("一卡通");
                        tc_uID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>, ObservableValue<String>>()
                        {
                            @Override
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
                            {
                                SimpleStringProperty id=new SimpleStringProperty(param.getValue().getId());
                                return id;
                            }
                        });
                        tv.getColumns().add(tc_uID);
                        ///////////////////////////////看不见密码
                        TableColumn<Student,String> tc_name=new TableColumn<Student,String>("姓名");
                        tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
                        {
                            @Override
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
                            {
                                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getName());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_name);
                        TableColumn<Student,String> tc_number=new TableColumn<Student,String>("学号");
                        tc_number.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
                        {
                            @Override
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
                            {
                                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getNumber());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_number);
                        TableColumn<Student,Number> tc_major=new TableColumn<Student,Number>("专业");
                        tc_major.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getMajorId());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_major);
                        TableColumn<Student,Number> tc_GPA=new TableColumn<Student,Number>("绩点");
                        tc_GPA.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getGPA());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_GPA);
                        TableColumn<Student,Number> tc_credit=new TableColumn<Student,Number>("学分");
                        tc_credit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getCredit());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_credit);
                        TableColumn<Student,Number> tc_srtp=new TableColumn<Student,Number>("研学");
                        tc_srtp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSTRP());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_srtp);
                        TableColumn<Student,Number> tc_state=new TableColumn<Student,Number>("状态");
                        tc_state.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getState());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_state);
                        TableColumn<Student,Number> tc_age=new TableColumn<Student,Number>("年龄");
                        tc_age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getAge());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_age);
                        TableColumn<Student,Number> tc_sex=new TableColumn<Student,Number>("性别");
                        tc_sex.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSex());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_sex);
                        TableColumn<Student,String> tc_register=new TableColumn<Student,String>("注册时间");
                        tc_register.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
                        {
                            @Override
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
                            {
                                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getRegister());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_register);
                        TableColumn<Student,Number> tc_money=new TableColumn<Student,Number>("余额宝");
                        tc_money.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getCardBalance());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_money);
                        TableColumn<Student,String> tc_renown=new TableColumn<Student,String>("声誉");
                        tc_renown.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
                        {
                            @Override
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
                            {
                                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getRenown());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_renown);
                        TableColumn<Student,Number> tc_campusposition=new TableColumn<Student,Number>("校区");
                        tc_campusposition.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getCampusPosition());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_campusposition);
                        TableColumn<Student,Number> tc_punishment=new TableColumn<Student,Number>("惩罚");
                        tc_punishment.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getPunishment());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_punishment);
                        tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                        an11.getChildren().addAll(tv,ret);
                        tab1.setContent(an11);

                    }
                    else
                    {
                        Message mes1 = new Message();
                        Student ss=new Student();
                        if(cb.getValue().equals("能源与环境学院"))
                        {
                            ss.setMajorId(3);
                        }
                        if(cb.getValue().equals("计算机学院"))
                        {
                            ss.setMajorId(9);
                        }
                        if(cb.getValue().equals("机械学院"))
                        {
                            ss.setMajorId(2);
                        }
                        if(cb.getValue().equals("建筑学院"))
                        {
                            ss.setMajorId(1);
                        }
                        mes1.setData(ss);
                        mes1.setMessageType("QUERY_STUDENT_BY_MAJOR");
                        Message serverResponse1 = client.sendRequestToServer(mes1);
                        ArrayList<Student> relist1= (ArrayList<Student>) serverResponse1.getData();
                        ObservableList<Student> list1= FXCollections.observableArrayList();
                        for(int i=0;i<relist1.size();i++)
                        {
                            list1.add(relist1.get(i));
                        }
                        TableView<Student>  tv=new TableView<Student>(list1);
                        tv.setPrefWidth(1035);
                        tv.setPrefHeight(410);
                        TableColumn<Student,String> tc_uID=new TableColumn<Student,String>("一卡通");
                        tc_uID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
                        {
                            @Override
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
                            {
                                SimpleStringProperty id=new SimpleStringProperty(param.getValue().getId());
                                return id;
                            }
                        });
                        tv.getColumns().add(tc_uID);
                        ///////////////////////////////看不见密码
                        TableColumn<Student,String> tc_name=new TableColumn<Student,String>("姓名");
                        tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
                        {
                            @Override
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
                            {
                                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getName());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_name);
                        TableColumn<Student,String> tc_number=new TableColumn<Student,String>("学号");
                        tc_number.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
                        {
                            @Override
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
                            {
                                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getNumber());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_number);
                        TableColumn<Student,Number> tc_major=new TableColumn<Student,Number>("专业");
                        tc_major.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getMajorId());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_major);
                        TableColumn<Student,Number> tc_GPA=new TableColumn<Student,Number>("绩点");
                        tc_GPA.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getGPA());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_GPA);
                        TableColumn<Student,Number> tc_credit=new TableColumn<Student,Number>("学分");
                        tc_credit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getCredit());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_credit);
                        TableColumn<Student,Number> tc_srtp=new TableColumn<Student,Number>("研学");
                        tc_srtp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSTRP());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_srtp);
                        TableColumn<Student,Number> tc_state=new TableColumn<Student,Number>("状态");
                        tc_state.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getState());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_state);
                        TableColumn<Student,Number> tc_age=new TableColumn<Student,Number>("年龄");
                        tc_age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getAge());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_age);
                        TableColumn<Student,Number> tc_sex=new TableColumn<Student,Number>("性别");
                        tc_sex.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getSex());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_sex);
                        TableColumn<Student,String> tc_register=new TableColumn<Student,String>("注册时间");
                        tc_register.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
                        {
                            @Override
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
                            {
                                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getRegister());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_register);
                        TableColumn<Student,Number> tc_money=new TableColumn<Student,Number>("余额宝");
                        tc_money.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getCardBalance());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_money);
                        TableColumn<Student,String> tc_renown=new TableColumn<Student,String>("声誉");
                        tc_renown.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>,ObservableValue<String>>()
                        {
                            @Override
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student,String> param)
                            {
                                SimpleStringProperty pw=new SimpleStringProperty(param.getValue().getRenown());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_renown);
                        TableColumn<Student,Number> tc_campusposition=new TableColumn<Student,Number>("校区");
                        tc_campusposition.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getCampusPosition());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_campusposition);
                        TableColumn<Student,Number> tc_punishment=new TableColumn<Student,Number>("惩罚");
                        tc_punishment.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,Number>,ObservableValue<Number>>()
                        {
                            @Override
                            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Student,Number> param)
                            {
                                SimpleDoubleProperty pw=new SimpleDoubleProperty(param.getValue().getPunishment());
                                return pw;
                            }
                        });
                        tv.getColumns().add(tc_punishment);
                        tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                        an11.getChildren().addAll(tv,ret);
                        tab1.setContent(an11);
                    }

                }
            });

            an1.getChildren().addAll(tx1,text2,h1,h2,bt);
            tab1.setContent(an1);




///////////////////////////////////选项2页面内容：

            Text t1=new Text("请选择导入文件路径：");
            Button ifile =new Button("选择");
            HBox h3=new HBox();
            h3.getChildren().addAll(t1,ifile);
            h3.setSpacing(30.0);
            Text t2=new Text("请选择导出文件路径：");
            Button ofile =new Button("浏览");
            HBox h4=new HBox();
            h4.getChildren().addAll(t2,ofile);
            h4.setSpacing(30.0);
            AnchorPane an2=new AnchorPane();
            VBox vb2=new VBox();
            vb2.getChildren().addAll(h3,h4);
            vb2.setSpacing(30.0);
            an2.getChildren().addAll(vb2);
            an2.setTopAnchor(vb2, 150.0);
            an2.setLeftAnchor(vb2, 400.0);
            tab2.setContent(an2);
            ////////////////////////////////关于文件处理的消息响应函数：
            ifile.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event)
                {
                    Stage ss=new Stage();
                    FileChooser fc=new FileChooser();
                    fc.setTitle("请选择Excel文件");
                    fc.setInitialDirectory(new File("D:"));
                    fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Excel", "*.xlsx"),
                            new FileChooser.ExtensionFilter("XLS", "*.xls"), new FileChooser.ExtensionFilter("XLSX", "*.xlsx"));
                    File file=fc.showOpenDialog(ss);
                    if(file==null)
                    {
                        return;
                    }
                    else
                    {
                        Message mes = new Message();
                        try {
                            //1、获取文件输入流
                            InputStream inputStream = new FileInputStream(file);
                            //2、获取Excel工作簿对象
                            Workbook workbook = new XSSFWorkbook(inputStream);
                            //3、得到Excel工作表对象
                            Sheet sheetAt = workbook.getSheetAt(0);
                            //4、循环读取表格数据
                            for (Row row : sheetAt) {
                                //首行（即表头）不读取
                                if (row.getRowNum() == 0) {
                                    continue;
                                }
                                //读取当前行中单元格数据，索引从0开始
                                String id = row.getCell(0).getStringCellValue();
                                String pwd = row.getCell(1).getStringCellValue();
                                String name = row.getCell(2).getStringCellValue();
                                String ut = row.getCell(3).getStringCellValue();
                                String number = row.getCell(4).getStringCellValue();
                                String major = row.getCell(5).getStringCellValue();
                                String gpa = row.getCell(6).getStringCellValue();
                                String credit = row.getCell(6).getStringCellValue();
                                String srtp = row.getCell(8).getStringCellValue();
                                String state = row.getCell(9).getStringCellValue();
                                String phone = row.getCell(10).getStringCellValue();
                                String age = row.getCell(11).getStringCellValue();
                                String sex = row.getCell(12).getStringCellValue();
                                String reg = row.getCell(13).getStringCellValue();
                                String dorm = row.getCell(14).getStringCellValue();
                                String money = row.getCell(15).getStringCellValue();
                                String book = row.getCell(16).getStringCellValue();
                                String renown = row.getCell(17).getStringCellValue();
                                String campus = row.getCell(18).getStringCellValue();
                                String punish = row.getCell(19).getStringCellValue();

                                Student sss = new Student();
                                sss.setId(id);
                                sss.setpwd(pwd);
                                sss.setName(name);
                                sss.setUserType(Integer.parseInt(ut));
                                sss.setNumber(number);
                                sss.setMajorId(Integer.parseInt(major));
                                sss.setGPA(Double.parseDouble(gpa));
                                sss.setCredit(Double.parseDouble(credit));
                                sss.setSTRP(Double.parseDouble(srtp));
                                sss.setState(Integer.parseInt(state));
                                sss.setPhoneNmuber(phone);
                                sss.setAge(Integer.parseInt(age));
                                sss.setSex(Integer.parseInt(sex));
                                sss.setRegister(reg);
                                sss.setDormNum(dorm);
                                sss.setCardBalance(Double.parseDouble(money));
                                sss.setLendBooksNum(Integer.parseInt(book));
                                sss.setRenown(renown);
                                sss.setCampusPosition(Integer.parseInt(campus));
                                sss.setPunishment(Integer.parseInt(punish));

                                mes.setMessageType("ADD_STUDENT");
                                mes.setData(sss);
                                Message serverResponse = client.sendRequestToServer(mes);

                            }
                            //5、关闭流
                            workbook.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });





///////////////////////////////////////////////////////////////下面是下载到本地的操作，先生成，比较麻烦！
            ofile.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event)
                {
                    Stage direct=new Stage();
                    Message mmm=new Message();
                    mmm.setMessageType("OUTPUT_ALL_STUDENT");
                    Message response=new Message();
                    response=client.sendRequestToServer(mmm);
                    ArrayList<Student> arr=(ArrayList<Student>) response.getData();
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("export");
                    fileChooser.setInitialFileName("record.xlsx");
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLSX Files", "*.xlsx"));
                    File file = fileChooser.showSaveDialog(direct);
                    if(file != null)
                    {
                        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
                        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        dialog.getDialogPane().setExpandableContent(new Text(" 保存文件成功了！ "));
                        dialog.getDialogPane().setPrefSize(61.8, 100);
                        dialog.show();
                        exportExcel(file.getAbsolutePath(),arr);
                    }
                }
            });


        } catch(Exception e) {
            e.printStackTrace();
        }
        return an;
    }
}
