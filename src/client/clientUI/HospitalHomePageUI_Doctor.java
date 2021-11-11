package client.clientUI;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import vo.Doctor;

public class HospitalHomePageUI_Doctor {
    Doctor doctor = new Doctor();
    public HospitalHomePageUI_Doctor(Doctor doctor){
        this.doctor = doctor;
    }

    public AnchorPane HospitalHomePage(){
        AnchorPane anchorPane = new AnchorPane();

        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setPrefSize(1058, 580);
        Tab tab_query = new Tab();
        Tab tab_res = new Tab();
//        Tab tab_med = new Tab();

        tab_query.setText("咨询记录");
        tab_res.setText("发布出诊时间");
//        tab_med.setText("线上药房");

        Hospital_tab_query_Doctor hospital_tab_query_doctor = new Hospital_tab_query_Doctor(doctor);
        Hospital_tab_res_Doctor hospital_tab_res_doctor = new Hospital_tab_res_Doctor(doctor);

        tab_query = hospital_tab_query_doctor.TabAdviceList();
        tab_res.setContent(hospital_tab_res_doctor.HospitalSchedulePage());
//        tab_res.setContent();
//        tab_med.setContent();

        tabPane.getTabs().addAll(tab_query, tab_res);
        anchorPane.getChildren().addAll(tabPane);

        return anchorPane;
    }
}
