package client.clientUI;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import vo.Retailer;
import vo.tempShop;

import java.util.ArrayList;

public class ShopHomePageUI_Retailer {

    Retailer ret=new Retailer();
    ArrayList<tempShop> myshop=new ArrayList<tempShop>();

    public ShopHomePageUI_Retailer(Retailer retailer){
        ret = retailer;
    }

    public AnchorPane shopPage() {

        AnchorPane ap=new AnchorPane();

        JFXTabPane tabpane = new JFXTabPane();
        tabpane.setPrefSize(1058,580);

        Tab tab1 = new Shop_tab_add_Retailer(ret).addPage();
        Tab tab2 = new Shop_tab_rec_Retailer(ret).recPage();

        tabpane.getTabs().addAll(tab1, tab2);
        ap.getChildren().add(tabpane);
        return ap;
    }
}


