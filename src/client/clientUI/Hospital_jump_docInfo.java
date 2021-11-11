package client.clientUI;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import vo.Doctor;
import vo.MedicalAdvice;
import vo.Student;

//医院主页下的一级跳转页
public class Hospital_jump_docInfo {
    private Student student;
    private Doctor doctor;
    private TabPane tabPane;
    private Hospital_tab_home hospital_tab_home;//父窗的句柄
    private Hospital_tab_query hospital_tab_query;//父窗的句柄（需要维护一个另一个tag的
    Image bg = new Image("/client/Image/img2.jpg",1058, 580,false,true);
    ImageView backgroundView = new ImageView(bg);

    public Hospital_jump_docInfo(Student student, Doctor doctor, Hospital_tab_home hospital_tab_home, Hospital_tab_query hospital_tab_query, TabPane tabPane){
        this.student = student;
        this.doctor = doctor;
        this.hospital_tab_home = hospital_tab_home;
        this.hospital_tab_query = hospital_tab_query;
        this.tabPane = tabPane;
    }

    //0-内科 1-外科 2-皮肤科 3-耳鼻喉科 4-口腔 5-眼科 6-心理咨询
    private String checkList(Integer i){
        if(i == 0) return "内科";
        else if(i == 1) return "外科";
        else if(i == 2) return "皮肤科";
        else if(i == 3) return "耳鼻喉科";
        else if(i == 4) return "口腔";
        else if(i == 5) return "眼科";
        else if(i == 6) return "心理咨询";
        else
            return null;
    }

    public AnchorPane docInfo(){
        AnchorPane ap = new AnchorPane();

        //根据最后效果调整disp即可
        int disp1 = 50;

        //医生图片应该是后端返回的，现阶段先放在前端
        ImageView doc_img = new ImageView("/client/Image/cross.png");
        doc_img.setPreserveRatio(true);
        doc_img.setFitWidth(200);
        doc_img.setLayoutX(200+disp1);
        doc_img.setLayoutY(120);

        Label doc_name = new Label("医师姓名： ");
        doc_name.setFont(new Font("Microsoft YaHei",20));
        doc_name.setLayoutX(450+disp1);
        doc_name.setLayoutY(140);

        Label doc_pos = new Label("医师职位： ");
        doc_pos.setFont(new Font("Microsoft YaHei",20));
        doc_pos.setLayoutX(450+disp1);
        doc_pos.setLayoutY(200);

        Label doc_type = new Label("所属科室： ");
        doc_type.setFont(new Font("Microsoft YaHei",20));
        doc_type.setLayoutX(450+disp1);
        doc_type.setLayoutY(260);

        //这里以后得根据前端逻辑来
        Label doc_name_lb = new Label(doctor.getName());
        doc_name_lb.setFont(new Font("Microsoft YaHei", 20));
        doc_name_lb.setLayoutX(560+disp1);
        doc_name_lb.setLayoutY(138);

        Label doc_pos_lb = new Label(doctor.getPosition());
        doc_pos_lb.setFont(new Font("Microsoft YaHei",20));
        doc_pos_lb.setLayoutX(560+disp1);
        doc_pos_lb.setLayoutY(198);

        Label doc_type_lb = new Label(checkList(Integer.valueOf(doctor.getDept())));
        doc_type_lb.setFont(new Font("Microsoft YaHei",20));
        doc_type_lb.setLayoutX(560+disp1);
        doc_type_lb.setLayoutY(258);

        JFXButton btn_ques = new JFXButton("向他提问");
        btn_ques.setStyle("    -fx-padding: 0.7em 0.57em;\r\n" +
                "    -fx-font-size: 14px;\r\n" +
                "    -jfx-button-type: RAISED;\r\n" +
                "    -fx-background-color: rgb(77,102,204);\r\n" +
                "    -fx-pref-width: 100;\r\n" +
                "    -fx-text-fill: WHITE;");
        btn_ques.setLayoutX(470+disp1);
        btn_ques.setLayoutY(350);

        JFXButton btn_queue = new JFXButton("挂号");
        btn_queue.setStyle("    -fx-padding: 0.7em 0.57em;\r\n" +
                "    -fx-font-size: 14px;\r\n" +
                "    -jfx-button-type: RAISED;\r\n" +
                "    -fx-background-color: rgb(77,102,204);\r\n" +
                "    -fx-pref-width: 100;\r\n" +
                "    -fx-text-fill: WHITE;");
        btn_queue.setLayoutX(650+disp1);
        btn_queue.setLayoutY(350);

        JFXButton btn_back = new JFXButton("返回");
        btn_back.setStyle("    -fx-padding: 0.7em 0.57em;\r\n" +
                "    -fx-font-size: 14px;\r\n" +
                "    -jfx-button-type: RAISED;\r\n" +
                "    -fx-background-color: rgb(77,102,204);\r\n" +
                "    -fx-pref-width: 100;\r\n" +
                "    -fx-text-fill: WHITE;");
        btn_back.setLayoutX(40);
        btn_back.setLayoutY(40);

        MedicalAdvice medicalAdvice = new MedicalAdvice();
        medicalAdvice.setQueryDept(checkList(Integer.valueOf(doctor.getDept())));
        medicalAdvice.setDocId(doctor.getId());
        medicalAdvice.setDocName(doctor.getName());
        medicalAdvice.setStuId(student.getId());
        medicalAdvice.setStuName(student.getName());

        //消息响应函数
        //提问键-去2跳页面提问-跨tab跳转
        btn_ques.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                AnchorPane hospital_query = new Hospital_jump2_MedicalAdviceQuery(medicalAdvice,hospital_tab_query).HospitalQueryPage();
                hospital_tab_query.Hospital_jump_control(hospital_query);
                tabPane.getSelectionModel().select(tabPane.getTabs().get(1));
            }
        });

        //挂号键-去2跳页面挂号
        btn_queue.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                AnchorPane hospital_res = new Hospital_jump2_res(student, doctor, hospital_tab_home).HospitalResPage();
                hospital_tab_home.Hospital_jump_control(hospital_res);
            }
        });

        //返回键-调用tool，关闭本窗口
        btn_back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                hospital_tab_home.jumpTool_close();
            }
        });

        ap.getChildren().addAll(backgroundView,doc_img,doc_name,doc_name_lb,doc_pos,doc_pos_lb,doc_type,doc_type_lb,btn_back,btn_ques,btn_queue);


        return ap;
    }
}
