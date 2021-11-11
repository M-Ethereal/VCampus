package client.clientUI;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import vo.Student;

public class LibraryHomePageUI {
    private AnchorPane hhp = new AnchorPane();
    private Student student;

    public LibraryHomePageUI(Student student){
        this.student = student;
    }

    //子页面生成
    public AnchorPane LibHomePage(){
        //hhp.setStyle("-fx-background-color: #D0D0D0");
        hhp.setPrefSize(1058, 580);

        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setPrefSize(1058, 580);
        Tab tab_myLend = new Tab();
        Tab tab_lib = new Tab();
        Tab tab_query = new Tab();

//        tab_myLend.setText("我的图书");
//        tab_lib.setText("馆藏");
//        tab_query.setText("图书查询");
//        tab_med.setText("线上药房");

        tab_myLend = new Library_tab_myLend().tabMyLend(student.getNumber());
        tab_lib = new Library_tab_lib().tabLib(student.getNumber());
        tab_query = new Library_tab_query().tabQuery(student.getNumber());

        tabPane.getTabs().addAll(tab_myLend, tab_lib, tab_query);

        hhp.getChildren().addAll(tabPane);

        return hhp;
    }

}
