package client.clientUI;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import vo.Student;

public class HospitalHomePageUI {
    private AnchorPane hhp = new AnchorPane();
    private Student student;


    public HospitalHomePageUI(Student student){
        this.student = student;
    }

    //子页面生成
    public AnchorPane HospitalHomePage(){
        //hhp.setStyle("-fx-background-color: #D0D0D0");
        hhp.setPrefSize(1058, 580);


        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setPrefSize(1058, 580);
        Tab tab_home = new Tab();
        Tab tab_query = new Tab();
        Tab tab_res = new Tab();
//        Tab tab_med = new Tab();

        tab_home.setText("医院主页");
        tab_query.setText("咨询记录");
        tab_res.setText("挂号记录");
//        tab_med.setText("线上药房");

        Hospital_tab_query hospital_tab_query = new Hospital_tab_query(student);
        Hospital_tab_home hospital_tab_home = new Hospital_tab_home(student, hospital_tab_query, tabPane);
        Hospital_tab_res hospital_tab_res = new Hospital_tab_res(student);

        tab_home = hospital_tab_home.tab_home_page();
        tab_query = hospital_tab_query.TabAdviceList();
        tab_res = hospital_tab_res.TabDocRegistert();
//        tab_res.setContent();
//        tab_med.setContent();

        tabPane.getTabs().addAll(tab_home, tab_query, tab_res);

        hhp.getChildren().addAll(tabPane);

        return hhp;
    }
}
