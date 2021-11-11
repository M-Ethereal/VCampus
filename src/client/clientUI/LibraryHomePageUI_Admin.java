package client.clientUI;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

//管理员界面-增加-修改-查询/删除
public class LibraryHomePageUI_Admin {
    private AnchorPane hhp = new AnchorPane();

    //子页面生成
    public AnchorPane LibHomePage(){
        //hhp.setStyle("-fx-background-color: #D0D0D0");
        hhp.setPrefSize(1058, 580);

        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setPrefSize(1058, 580);
        Tab tab_add = new Tab();
        Tab tab_edit = new Tab();
        Tab tab_query = new Tab();

//        tab_myLend.setText("我的图书");
//        tab_lib.setText("馆藏");
//        tab_query.setText("图书查询");
//        tab_med.setText("线上药房");

        Library_tab_add_Admin library_tab_add_admin = new Library_tab_add_Admin();
        Library_tab_edit_Admin library_tab_edit_admin = new Library_tab_edit_Admin();
        Library_tab_query_Admin library_tab_query_admin = new Library_tab_query_Admin();

        tab_add = library_tab_add_admin.tabAdd();
        tab_edit = library_tab_edit_admin.tabEdit();
        tab_query = library_tab_query_admin.tabQuery();

        tabPane.getTabs().addAll(tab_add, tab_edit, tab_query);

        hhp.getChildren().addAll(tabPane);

        return hhp;
    }
}
