package home.controllers;

import home.util.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Mycourse implements Initializable {
    @FXML
    private ListView<String> list;
    @FXML
    private AnchorPane rootpane;
    @FXML
    private PieChart pie;
    @FXML
    private Button viewCourse;
    @FXML
    private Button leaveCourse;
    @FXML
    private Label attended;

    @FXML
    private Label absent;

    private int i=0;
    public static String[] ListCo=new String[10];
    private String str1="";

    private static String classID;
    private ObservableList<String> items = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Connection Conn = ConnectionUtil.connectdb();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = Conn.createStatement();
            rs = stmt.executeQuery("SELECT distinct concat('(',c.classID,') ', s1.subjectName, '- So tin chi: ', s1.creditsNum, '- GV: ', l.lecturerName) \n" +
                    "FROM uetcourse.students_subjects s \n" +
                    "INNER JOIN uetcourse.classes c on s.classID = c.classID \n" +
                    "INNER JOIN uetcourse.Subjects as s1 \n" +
                    "Inner join uetcourse.Lecturers as l on s1.subjectID = c.subjectID and l.lecturerId = c.lecturerId\n");
            while (rs.next()) {
                String str1 = rs.getString(1);
                str1 = rs.getString(1);
                ListCo[i]=str1;
                i++;
                items.add(str1);
            }
        } catch (SQLException e) {
                  e.printStackTrace();
        }
        PieChart.Data slice = new PieChart.Data("Attendance", 13);
        PieChart.Data slice1 = new PieChart.Data("Absent", 0);
        pie.getData().add(slice);
        pie.getData().add(slice1);
        list.setItems(items);


    }

    public void handleMouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        int index = list.getSelectionModel().getSelectedIndex();
        String item = list.getSelectionModel().getSelectedItem();
        if(index != -1) {
            classID = item.substring(1,11);
            pie.getData().clear();
            int attend = 0;
            try {
                String stm = "SELECT attendance FROM uetcourse.students_subjects WHERE studentsId = ? and classId = ?";
                System.out.println(Login.getUserID() + " ");
                ResultSet rs = null;
                Connection conn = ConnectionUtil.connectdb();
                PreparedStatement prepStatement = conn.prepareStatement(stm);
                prepStatement.setString(1, Login.getUserID());
                prepStatement.setString(2, classID);
                rs = prepStatement.executeQuery();
                while (rs.next()) {
                    attend = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.getStackTrace();
            }
            PieChart.Data slice = new PieChart.Data("Attendance", attend);
            PieChart.Data slice1 = new PieChart.Data("Absent", 15 - attend);
            pie.getData().add(slice);
            pie.getData().add(slice1);
            attended.setText("Attend: "+attend);
            absent.setText("Absent: "+(15-attend));
            pie.setLegendSide(Side.BOTTOM);
            pie.setStartAngle(30);
            if(mouseEvent.getSource() == viewCourse){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/studentCourseView.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.getIcons().add(new Image("/home/image/icon.png"));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setTitle(item);
                    stage.show();
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else if(mouseEvent.getSource() == leaveCourse){
                try{
                    Connection Conn = ConnectionUtil.connectdb();
                    PreparedStatement stmt = Conn.prepareStatement("DELETE FROM uetcourse.students_subjects Where classId =? and studentsId = ?");
                    stmt.setString(1, classID);
                    stmt.setString(2,Login.getUserID());
                    stmt.executeUpdate();
                } catch (SQLException e ){
                    e.printStackTrace();
                }
                list.getItems().remove(index);
            }
        } else {
            Login.infoBox("Please select a class", null, "Error");
        }
    }

    public static String getclassID(){
        return classID;
    }

    public ListView<String> getList() {
        return list;
    }
}
