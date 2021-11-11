package client.clientUI;

import client.socket.Client;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import util.BorderedTitledPane;
import util.MessageType;
import vo.Message;
import vo.RegistrationRecord;
import vo.Student;

import java.util.ArrayList;

public class Hospital_tab_res {
    private Student student;
    private ArrayList<RegistrationRecord> regs = new ArrayList<RegistrationRecord>();

    public Hospital_tab_res(Student student){
        this.student = student;
    }
    //打表函数
    private AnchorPane regListGenerate(ArrayList<RegistrationRecord> regs) {
        AnchorPane pane = new AnchorPane();

        JFXTreeTableColumn<Record, String> IDColumn = new JFXTreeTableColumn<>("医生ID");
        IDColumn.setPrefWidth(100);
        IDColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Record, String> param) -> {
            if (IDColumn.validateValue(param)) {
                return param.getValue().getValue().id;
            } else {
                return IDColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Record, String> NameColumn = new JFXTreeTableColumn<>("医生姓名");
        NameColumn.setPrefWidth(90);
        NameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Record, String> param) -> {
            if (NameColumn.validateValue(param)) {
                return param.getValue().getValue().name;
            } else {
                return NameColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Record, String> DateColumn = new JFXTreeTableColumn<>("挂号日期");
        DateColumn.setPrefWidth(100);
        DateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Record, String> param) -> {
            if (DateColumn.validateValue(param)) {
                return param.getValue().getValue().date;
            } else {
                return DateColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Record, String> timeColumn = new JFXTreeTableColumn<>("挂号时间");
        timeColumn.setPrefWidth(100);
        timeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Record, String> param) -> {
            if (timeColumn.validateValue(param)) {
                return param.getValue().getValue().time;
            } else {
                return timeColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Record, Number> CostColumn = new JFXTreeTableColumn<>("挂号金额");
        CostColumn.setPrefWidth(90);
        CostColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Record, Number> param) -> {
            if (CostColumn.validateValue(param)) {
                return param.getValue().getValue().cost;
            } else {
                return CostColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Record, String> DeptColumn = new JFXTreeTableColumn<>("挂号科室");
        DeptColumn.setPrefWidth(90);
        DeptColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Record, String> param) -> {
            if (DeptColumn.validateValue(param)) {
                return param.getValue().getValue().dept;
            } else {
                return DeptColumn.getComputedValue(param);
            }
        });


        JFXTreeTableColumn<Record, String> TypeColumn = new JFXTreeTableColumn<>("挂号类型");
        TypeColumn.setPrefWidth(90);
        TypeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Record, String> param) -> {
            if (TypeColumn.validateValue(param)) {
                return param.getValue().getValue().type;
            } else {
                return TypeColumn.getComputedValue(param);
            }
        });

        IDColumn.setCellFactory((TreeTableColumn<Record, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        IDColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Record, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().id.set(t.getNewValue()));

        NameColumn.setCellFactory((TreeTableColumn<Record, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        NameColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Record, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().name.set(t.getNewValue()));
        DateColumn.setCellFactory((TreeTableColumn<Record, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        DateColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Record, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().date.set(t.getNewValue()));
        timeColumn.setCellFactory((TreeTableColumn<Record, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        timeColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Record, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().time.set(t.getNewValue()));
        CostColumn.setCellFactory((TreeTableColumn<Record, Number> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        CostColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Record, Number> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().cost.set((double) t.getNewValue()));
        DeptColumn.setCellFactory((TreeTableColumn<Record, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        DeptColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Record, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().dept.set(t.getNewValue()));
        TypeColumn.setCellFactory((TreeTableColumn<Record, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        TypeColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Record, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().type.set(t.getNewValue()));

        // data
        ObservableList<Record> Records = FXCollections.observableArrayList();
        //此处添加数据，数据参数次序依次为医生ID，医生姓名，挂号日期，挂号时间，挂号金额，挂号科室，挂号类型
        for(int i = 0; i < regs.size(); i++){
            RegistrationRecord reg = regs.get(i);
            Records.add(new Record(reg.getDocId(), reg.getDocName(), reg.getApDate(), reg.getApTime(), reg.getApCost(), reg.getApDept(), reg.getApType()));
        }

        // build tree
        final TreeItem<Record> root = new RecursiveTreeItem<>(Records, RecursiveTreeObject::getChildren);

        JFXTreeTableView<Record> treeView = new JFXTreeTableView<>(root);
        treeView.setShowRoot(false);
        treeView.setEditable(false);
        treeView.getColumns().setAll(IDColumn, NameColumn, DateColumn, timeColumn, CostColumn, DeptColumn, TypeColumn);

        AnchorPane main = new AnchorPane();
        treeView.setLayoutX(40);
        treeView.setLayoutY(110);
        main.getChildren().add(treeView);

        Label info = new Label("模糊搜索：");
        info.setFont(new Font("Microsoft YaHei",18));
        info.setLayoutX(735);
        info.setLayoutY(150);
        JFXTextField filterField = new JFXTextField();
        filterField.setLayoutX(830);
        filterField.setLayoutY(150);
        filterField.setPromptText("请输入检索关键字");
        main.getChildren().addAll(info, filterField);

        Label lb1 = new Label("共检索到");
        lb1.setFont(new Font("Microsoft YaHei",15));
        lb1.setLayoutX(770);
        lb1.setLayoutY(230);
        Label lb2 = new Label("条信息");
        lb2.setFont(new Font("Microsoft YaHei",15));
        lb2.setLayoutX(870);
        lb2.setLayoutY(230);
        main.getChildren().addAll(lb1,lb2);

        Label size = new Label();
        size.setFont(new Font(15));
        size.setLayoutX(845);
        size.setLayoutY(230);
        Label lb = new Label("挂号记录");
        lb.setFont(new Font("Microsoft YaHei",30));
        lb.setLayoutX(40);
        lb.setLayoutY(40);
        main.getChildren().add(lb);
        filterField.textProperty().addListener((o, oldVal, newVal) -> {
            treeView.setPredicate(RecordProp -> {
                final Record e = RecordProp.getValue();
                return (e.date.get().contains(newVal))
                        || (e.id.get().contains(newVal))
                        || (e.name.get().contains(newVal))
                        || (e.time.get().contains(newVal))
                        || (e.dept.get().contains(newVal))
                        || (e.type.get().contains(newVal));
            });
        });

        size.textProperty()
                .bind(Bindings.createStringBinding(() -> String.valueOf(treeView.getCurrentItemsCount()),
                        treeView.currentItemsCountProperty()));
        main.getChildren().add(size);

        pane.getChildren().add(main);

        return pane;
    }

    //主函数
    public Tab TabDocRegistert() {
        Tab tab = new Tab();
        tab.setText("挂号记录");
        ScrollPane scp = new ScrollPane();

//        //主标题
//        Label mainTitle = new Label("点击查看医疗咨询记录");
//        mainTitle.setStyle("-fx-font-size:30px");

//        //表
//        AnchorPane ap_inner = new AnchorPane();
//        FlowPane fp = new FlowPane();
//        fp = courseListGenerate(myCourses);
//        fp.setVgap(10);
//        ap_inner.getChildren().add(fp);
//        ap_inner.setTopAnchor(fp,10.0);
//        ap_inner.setLeftAnchor(fp,10.0);

        //表格初始化
        Client client_res = new Client();
        Message msgSendToServer_res = new Message();
        msgSendToServer_res.setMessageType(MessageType.stu_res_query);
        msgSendToServer_res.setExtraMessage(student.getId());
        Message serverResponse = client_res.sendRequestToServer(msgSendToServer_res);
        ArrayList<RegistrationRecord> tempTable = new ArrayList<RegistrationRecord>();
        if (serverResponse.getData()!=null) tempTable = (ArrayList<RegistrationRecord>) serverResponse.getData();
        regs = tempTable;

        JFXButton commit = new JFXButton("刷新");
        commit.setStyle("-jfx-button-type: RAISED;-fx-background-color: #114376;");
        commit.setTextFill(Paint.valueOf("#FFFFFF"));
        commit.setPrefSize(40, 15);

        FlowPane fakeVBox = new FlowPane();

        VBox list = new VBox();
        //初始化
        AnchorPane fp1 = regListGenerate(regs);
//        if (regs!=null) {
//            list.getChildren().add( fp1 );
//        }


        fakeVBox.getChildren().add(list);

//        //框
//        BorderedTitledPane btp = new BorderedTitledPane("我的医疗咨询", fakeVBox);
//        btp.getStylesheets().add(getClass().getResource("BorderedTitledPane_CSS.css").toExternalForm());
//        btp.setTitleAlignment(Pos.TOP_LEFT);

        //主页面布局
        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(fp1);
//        ap.setTopAnchor(mainTitle, 50.0);
//        ap.setLeftAnchor(mainTitle, 50.0);
//        ap.setTopAnchor(commit, 105.0);
//        ap.setLeftAnchor(commit, 180.0);
//        ap.setTopAnchor(btp, 160.0);
//        ap.setLeftAnchor(btp, 40.0);

        //滚轮
        scp.setContent(ap);

        tab.setContent(scp);
        return tab;
    }

    //前端Record函数
    private static final class Record extends RecursiveTreeObject<Record> {
        //医生ID
        final StringProperty id;
        //医生姓名
        final StringProperty name;
        //挂号日期
        final StringProperty date;
        //挂号时间
        final StringProperty time;
        //挂号金额
        final DoubleProperty cost;
        //挂号科室
        final StringProperty dept;
        //挂号类型
        final StringProperty type;


        Record(String i, String n, String da, String t,double c,String d,String ty) {
            this.id = new SimpleStringProperty(i);
            this.name = new SimpleStringProperty(n);
            this.date = new SimpleStringProperty(da);
            this.time = new SimpleStringProperty(t);
            this.cost = new SimpleDoubleProperty(c);
            this.dept = new SimpleStringProperty(d);
            this.type = new SimpleStringProperty(ty);

        }
    }
}
