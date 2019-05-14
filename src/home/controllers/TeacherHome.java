package home.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherHome implements Initializable{

    @FXML
    protected Label infoTeacher;
    @FXML
    protected Label infoFunction;
    @FXML
    protected Button myHomeBtn;
    @FXML
    protected Button myCourseBtn;
    @FXML
    protected Button newCourseBtn;
    @FXML
    protected Button timeTableBtn;
    @FXML
    protected Button signOutBtn;
    @FXML
    protected Pane pane1;
    @FXML
    private AnchorPane parent;
    @FXML
    WebView webView;

    //set ten techer sau khi dang nhap thanh cong
    @Override
    public void initialize(URL location, ResourceBundle resources){
        infoTeacher.setText("Lecturer " + Login.getInfo());
        infoFunction.setText("My Home");
        infoFunction.setFont(Font.font("arial", FontWeight.MEDIUM, FontPosture.REGULAR, 40));
        WebEngine engine = webView.getEngine();
        engine.load("https://uet.vnu.edu.vn/category/tin-tuc/tin-sinh-vien/");
    }

    public void handleClicker(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == myHomeBtn) {
            this.createPage1(parent, "/home/fxml/Home.fxml");
            infoFunction.setText("My Home");
        } else if (actionEvent.getSource() == myCourseBtn) {
            this.createPage1(parent, "/home/fxml/teacherMyCourse.fxml");
            infoFunction.setText("My Course");
        } else if (actionEvent.getSource() == timeTableBtn) {
            this.createPage1(parent, "/home/fxml/timetable.fxml");
            infoFunction.setText("Time Table");
        } else if (actionEvent.getSource() == newCourseBtn) {
            infoFunction.setText("New Course");
        } else if (actionEvent.getSource() == signOutBtn) {
            Stage stage = (Stage) signOutBtn.getScene().getWindow();
            stage.close();
            loadStage("/home/fxml/login.fxml");
        }
    }

    public void setNode1(Node node){
        pane1.getChildren().clear();
        pane1.getChildren().add((Node)node);
    }

    public void createPage1(AnchorPane home, String path) throws IOException {
        home = FXMLLoader.load(getClass().getResource(path));
        setNode1(home);
    }

    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/home/image/icon.png"));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
