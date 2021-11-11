package client.clientUI;

import client.socket.Client;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.MessageType;
import vo.Message;
import vo.Place;
import vo.PlaceAppointmentRecord;
import vo.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceHomePageUI {
    private Student student = new Student();

    public PlaceHomePageUI(Student student){
        this.student = student;
    }

    public AnchorPane PlaceAppointment(){
        AnchorPane pane = new AnchorPane();
        ArrayList<Place> lib = new ArrayList<Place>();
        ArrayList<Place> gym = new ArrayList<Place>();
        ArrayList<Place> classroom = new ArrayList<Place>();
        ArrayList<Place> bath = new ArrayList<Place>();

        Client client = new Client();
        Message sendToServer = new Message();
        sendToServer.setMessageType(MessageType.place_query_all);

        sendToServer.setExtraMessage("图书");
        Message serverResponse_lib = client.sendRequestToServer(sendToServer);
        lib = (ArrayList<Place>) serverResponse_lib.getData();

        sendToServer.setExtraMessage("体育");
        Message serverResponse_gym = client.sendRequestToServer(sendToServer);
        gym = (ArrayList<Place>) serverResponse_gym.getData();

        sendToServer.setExtraMessage("教学");
        Message serverResponse_cr = client.sendRequestToServer(sendToServer);
        classroom = (ArrayList<Place>) serverResponse_cr.getData();

        sendToServer.setExtraMessage("浴室");
        Message serverResponse_bath = client.sendRequestToServer(sendToServer);
        bath = (ArrayList<Place>) serverResponse_bath.getData();


        JFXTabPane tabpane = new JFXTabPane();
        tabpane.setPrefSize(1058,580);

        Tab place_tab_ap = new Tab("预约记录");
        Place_tab_appointment place_tab_appointment = new Place_tab_appointment(student);
        place_tab_ap.setContent(place_tab_appointment.tab_ap());

        Tab place_tab_library = new Tab("图书馆预约");
        Place_tab_library p_tab_library = new Place_tab_library(student, lib, place_tab_appointment);
        place_tab_library.setContent(p_tab_library.tab_library());

        Tab Place_tab_gym = new Tab("体育馆预约");
        Place_tab_gym p_tab_gym = new Place_tab_gym(student, gym, place_tab_appointment);
        Place_tab_gym.setContent(p_tab_gym.tab_gym());

        Tab Place_tab_classroom = new Tab("教室预约");
        Place_tab_classroom p_tab_classroom = new Place_tab_classroom(student, classroom, place_tab_appointment);
        Place_tab_classroom.setContent(p_tab_classroom.tab_classroom());

        Tab Place_tab_bathroom = new Tab("浴室预约");
        Place_tab_bathroom p_tab_bathroom = new Place_tab_bathroom(student, bath, place_tab_appointment);
        Place_tab_bathroom.setContent(p_tab_bathroom.tab_bathroom());


        tabpane.getTabs().addAll(place_tab_library,Place_tab_gym,Place_tab_classroom,Place_tab_bathroom, place_tab_ap);

        pane.getChildren().add(tabpane);

        return pane;
    }

}
