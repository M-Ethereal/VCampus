package client.clientUI;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

public class MyInfoHomePageUI_Admin {

    public AnchorPane infoHomePage(){

        AnchorPane an=new AnchorPane();
        JFXTabPane tabpane=new JFXTabPane();
        tabpane.setPrefSize(1058, 580);
        //tabpane.setTabMinWidth(243);
        an.getChildren().add(tabpane);
        Tab tab1=new Tab("学生信息");
        Tab tab2=new Tab("教师信息");
        Tab tab3=new Tab("商户信息");
        Tab tab4=new  Tab("医生信息");
        tab1.setClosable(false);
        tab2.setClosable(false);
        tab3.setClosable(false);
        tab4.setClosable(false);

        MyInfo_tab_stu_Admin myInfo_tab_stu_admin = new MyInfo_tab_stu_Admin();
        MyInfo_tab_tea_Admin myInfo_tab_tea_admin = new MyInfo_tab_tea_Admin();
        MyInfo_tab_ret_Admin myInfo_tab_ret_admin = new MyInfo_tab_ret_Admin();
        MyInfo_tab_doc_Admin myInfo_tab_doc_admin = new MyInfo_tab_doc_Admin();

        tab1 = myInfo_tab_stu_admin.MyInfo_tab_stu();
        tab2 = myInfo_tab_tea_admin.MyInfo_tab_tea();
        tab3 = myInfo_tab_ret_admin.MyInfo_tab_ret();
        tab4 = myInfo_tab_doc_admin.MyInfo_tab_doc();

        tabpane.getTabs().addAll(tab1,tab2,tab3,tab4);  //别忘了加入到父亲中去！

        return an;
    }
}
