package client.clientUI;

import client.socket.Client;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.MessageType;
import vo.Message;
import vo.Retailer;
import vo.ShopRecord;
import vo.tempShop;

import java.util.ArrayList;

public class Shop_tab_rec_Retailer {
    Retailer retailer=new Retailer();

    public Shop_tab_rec_Retailer(Retailer retailer){
        this.retailer = retailer;
    }

    public Tab recPage() {
        Tab tab4=new Tab(" 售出记录 ");
        tab4.setClosable(false);
        AnchorPane ap4=new AnchorPane();
        tab4.setContent(ap4);


        Message sendToServer1 = new Message();
        Client client1 = new Client();
        sendToServer1.setMessageType(MessageType.SellingRecordQuery);
        sendToServer1.setExtraMessage(retailer.getId());

        Message serverResponse1 = client1.sendRequestToServer(sendToServer1);

        ArrayList<ShopRecord> recarr=(ArrayList<ShopRecord>) serverResponse1.getData();

        ObservableList<ShopRecord> list= FXCollections.observableArrayList();
        if(recarr != null){
            for(int i=0;i<recarr.size();i++) {
                list.add(recarr.get(i));
            }
        }

        TableView<ShopRecord> recview = new TableView<ShopRecord>(list);
        recview.setLayoutX(100);
        recview.setLayoutY(50);
        recview.setPrefSize(800, 400);

//记录表
        TableColumn<ShopRecord,String> rec_name=new TableColumn<ShopRecord,String>("名称");
        rec_name.setPrefWidth(200);
        TableColumn<ShopRecord,String> rec_bID=new TableColumn<ShopRecord,String>("买家");
        rec_bID.setPrefWidth(200);
        TableColumn<ShopRecord,String> rec_date=new TableColumn<ShopRecord,String>("时间");
        rec_date.setPrefWidth(200);
        TableColumn<ShopRecord,String> rec_num=new TableColumn<ShopRecord,String>("购买数量");
        rec_num.setPrefWidth(200);
        recview.getColumns().addAll(rec_name,rec_bID,rec_date,rec_num);

        rec_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShopRecord,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShopRecord, String> param) {
                SimpleStringProperty name=new SimpleStringProperty(param.getValue().getdName());
                return name;
            }

        });
        rec_bID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShopRecord,String>,ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShopRecord, String> param) {
                SimpleStringProperty sID=new SimpleStringProperty(param.getValue().getdbID());
                return sID;
            }

        });
        rec_date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShopRecord,String>,ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShopRecord, String> param) {
                SimpleStringProperty date=new SimpleStringProperty(param.getValue().getdDate());
                return date;
            }

        });
        rec_num.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ShopRecord,String>,ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ShopRecord, String> param) {
                SimpleStringProperty num=new SimpleStringProperty(param.getValue().getdNum());
                return num;
            }

        });


        ap4.getChildren().add(recview);
        return tab4;
    }
}
