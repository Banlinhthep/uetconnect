package home.controllers;

import home.util.ConnectionUtil;
import home.util.CourseViewModel;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.controlsfx.control.PropertySheet;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.*;
import java.util.EventObject;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.awt.Desktop.getDesktop;


public class coursesView implements Initializable {
    @FXML
    private AnchorPane ap;
    @FXML
    private TableView<CourseViewModel> tableView;
    @FXML
    private TableColumn<CourseViewModel,String> week;
    @FXML
    private TableColumn<CourseViewModel, String> date;
    @FXML
    private TableColumn<CourseViewModel, String> fileName;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button viewFileBtn;
    @FXML
    private Button refreshBtn;
    @FXML
    private Hyperlink txtBook;

    private static String res;

    private Stage stage;

    private String classId = null;

    private String nameFile = null;

    ObservableList<CourseViewModel> listCourses = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            Connection con = ConnectionUtil.connectdb();
            PreparedStatement preSt = con.prepareStatement("SELECT sc.week, sc.nameDoc FROM uetcourse.subject_content as sc where sc.subjectId = ?");
            preSt.setString(1, myCourseTeacher.getClassID());
            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {
                String nameDoc = rs.getString("nameDoc");
                System.out.println(nameDoc);
                listCourses.add(new CourseViewModel(rs.getString(1), setDate(rs.getString("week")), nameDoc));
            }
            tableView.setItems(listCourses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        week.setCellValueFactory(new PropertyValueFactory<>("week"));
        week.setMinWidth(50);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        date.setMinWidth(200);
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileName.setMinWidth(300);
    }



    public void handleTableView(ActionEvent actionEvent) {
        int idx = tableView.getSelectionModel().getSelectedIndex();
        if (idx != -1) {
             if (actionEvent.getSource() == viewFileBtn) {
                String str = tableView.getColumns().get(0).getCellObservableValue(idx).getValue().toString();
                System.out.println(str);
                 File file = ConnectionUtil.readBlob(str, myCourseTeacher.getClassID());
                 PDFreader pdFreader = new PDFreader(file.getAbsolutePath());
            }
        } else {
            if (actionEvent.getSource() == refreshBtn) {
                TextInputDialog txtIn = new TextInputDialog();
                txtIn.setTitle("Update Resources Link");
                txtIn.setHeaderText(null);
                txtIn.setContentText("Link: ");
                Optional<String> result = txtIn.showAndWait();
                if(result.isPresent()){
                    res = result.get();
                } else {
                    Login.infoBox("Empty form, please fill in the field!",null, "Error");
                }
            } else if (actionEvent.getSource() == uploadBtn) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                String str = stage.getTitle();
                String classId = stage.getTitle().substring(1, 11);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                    nameFile = selectedFile.getAbsolutePath();
                    System.out.println(nameFile);
                    TextInputDialog txtInDia = new TextInputDialog();
                    txtInDia.setTitle("Enter week number");
                    txtInDia.setHeaderText("Week value >= 1  and <= 13");
                    txtInDia.setContentText("Week no.");
                    Optional<String> result = txtInDia.showAndWait();
                    if (result.isPresent()) {
                        int week1 = Integer.parseInt(result.get());
                        if (week1 >= 1 || week1 <= 13) {
                            ConnectionUtil.uploadBlob(week1, nameFile, classId);
                        } else {
                            Login.infoBox("Invalid Input", null, "Error");
                        }
                    }
                }
            } else if (actionEvent.getSource() == txtBook){
                studentCourseView.goLink("https://drive.google.com/drive/folders/1ITQgyB2FW-LHBO4EM2MaO9kyhFXkyVrh");
            } else {
                Login.infoBox("Please select a class", null, "Error");
            }
        }
    }

    public String setDate(String week1){
        String date1 = "Not available week";
        switch (week1) {
            case "1":
                date1 = "21 January - 27 January";
                return date1;
            case "2":
                date1 = "28 January - 3 February";
                return date1;
            case "3":
                date1 = "4 February - 10 February";
                return date1;
            case "4":
                date1 = "11 February - 17 February";
                return date1;
            case "5":
                date1 = "18 February - 24 February";
                return date1;
            case "6":
                date1 = "25 February - 3 March";
                return date1;
            case "7":
                date1 = "4 March - 10 March";
                return date1;
            case "8":
                date1 = "11 March - 17 March";
                return date1;
            case "9":
                date1 = "18 March - 24 March";
                return date1;
            case "10":
                date1 = "25 March - 31 March";
                return date1;
            case "11":
                date1 = "1 April - 7 April";
                return date1;
            case "12":
                date1 = "15 April - 22 April";
                return date1;
            case "13":
                date1 = "22 April - 28 April";
                return date1;
        }
        return date1;
    }

    public static String getRes(){
        return res;
    }
}
