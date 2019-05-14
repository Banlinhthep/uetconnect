package home.controllers;

import com.jfoenix.controls.JFXTextField;
import home.util.ConnectionUtil;
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
import javafx.scene.paint.Color;
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

public class DashBoard implements Initializable {


    @FXML
    private Button btnMyhome;

    @FXML
    private Button btnMycourse;

    @FXML
    private Button btntimetable;

    @FXML
    private Button btndoc;

    @FXML
    private Button btnnews;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;
    @FXML
    private Pane pane;
    @FXML
    private AnchorPane homePane;


    @FXML
    private Label info;
    @FXML
    private Label info1;
    @FXML
    private AnchorPane parent;
    @FXML
    protected Button btnTest;
    @FXML
    private JFXTextField txtField;
    @FXML
    WebView webView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        info1.setText("My Home");
        info1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        info1.setTextFill(Color.web("#000000",1));
        info.setText(Login.getInfo());
        info.setTextFill(Color.web("#000000",1));
        WebEngine engine = webView.getEngine();
        engine.load("https://uet.vnu.edu.vn/category/tin-tuc/tin-sinh-vien/");
    }

    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
         //   Image icon = new Image(getClass().getResourceAsStream("/home/image/icon.PNG"));
          //  stage.getIcons().add(icon);
            stage.getIcons().add(new Image("/home/image/icon.png"));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("UETConnect");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnMyhome) {
            this.createPage(parent, "/home/fxml/Home.fxml");
            info1.setText("My Home");
        }
        else if(actionEvent.getSource() == btnMycourse) {
            this.createPage(parent,"/home/fxml/mycourse.fxml");
            info1.setText("My Course");
        }
        else if (actionEvent.getSource() == btntimetable) {
            this.createPage(parent,"/home/fxml/timetable.fxml");
            info1.setText("Time Table");
        }
        else if (actionEvent.getSource()== btnSettings){
            info1.setText("Settings");
        }
        else if(actionEvent.getSource() == btndoc){

        }
        else if(actionEvent.getSource() == btnnews){
            info1.setText("News");
        }
        else if(actionEvent.getSource() == btnSignout){
            Stage stage = (Stage) btnSignout.getScene().getWindow();
            stage.close();
            loadStage("/home/fxml/login.fxml");
        } else if(actionEvent.getSource() == btnTest){
            ConnectionUtil.readBlob("2","INT1002");
        }
    }
    /*
    public class ScreenController {
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    protected void addScreen(String name, Pane pane){
         screenMap.put(name, pane);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    protected void activate(String name){
        main.setRoot( screenMap.get(name) );
    }
}
ScreenController screenController = new ScreenController(scene);
screenController.add("calculator", FXMLLoader.load(getClass().getResource( "calculator.fxml" )));
screenController.add("testSwitch", FXMLLoader.load(getClass().getResource( "TestSwitch.fxml" )));
screenController.activate("calculator");
     */
    public void setNode(Node node){
        pane.getChildren().clear();
        pane.getChildren().add((Node)node);
    }
    public void createPage(AnchorPane home, String path) throws IOException {
        home = FXMLLoader.load(getClass().getResource(path));
        setNode(home);
    }
    @FXML
    void scene1Action(ActionEvent event) throws IOException {
        this.createPage(homePane,"/home/fxml/myhome.fxml");
    }
    @FXML
    void scene2Action(ActionEvent event) throws IOException {
        this.createPage(homePane,"/home/fxml/mycourse.fxml");
    }
}
