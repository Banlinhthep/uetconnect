package home.controllers;

import home.util.ConnectionUtil;
import home.util.CourseViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jpedal.PdfDecoder;
import org.jpedal.examples.viewer.Viewer;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class studentCourseView implements Initializable {
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
    private Button viewFileBtn;
    @FXML
    private Button refreshBtn;
    @FXML
    private Hyperlink textbook;

    private static String st;

    private static String file;

    ObservableList<CourseViewModel> listCourses = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            Connection con = ConnectionUtil.connectdb();
            PreparedStatement preSt = con.prepareStatement("SELECT sc.week, sc.nameDoc FROM uetcourse.subject_content as sc where sc.subjectId = ?");
            preSt.setString(1, Mycourse.getclassID());
            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {
                String nameDoc = rs.getString("nameDoc");
                listCourses.add(new CourseViewModel(rs.getString(1), setDate(rs.getString("week")), nameDoc));
            }
            tableView.setItems(listCourses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        week.setCellValueFactory(new PropertyValueFactory<>("Week"));
        week.setMinWidth(50);
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        date.setMinWidth(200);
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileName.setMinWidth(300);
    }

    public void handleTable(ActionEvent actionEvent) {
        int idx = tableView.getSelectionModel().getSelectedIndex();
        if (idx != -1) {
            if (actionEvent.getSource() == viewFileBtn) {
                st = tableView.getColumns().get(0).getCellObservableValue(idx).getValue().toString();
                System.out.println(st);
                file = tableView.getColumns().get(2).getCellObservableValue(idx).getValue().toString();
                File file = ConnectionUtil.readBlob(st, Mycourse.getclassID());
                PDFreader pdFreader = new PDFreader(file.getAbsolutePath());
            }
        } else if (actionEvent.getSource() == textbook) {

            goLink("https://drive.google.com/drive/folders/1ITQgyB2FW-LHBO4EM2MaO9kyhFXkyVrh");
        } else if (actionEvent.getSource() == refreshBtn){
            tableView.refresh();
        } else {
            Login.infoBox("Please select a class", null, "Error");
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

    public static void goLink(String link){
        try {
            Desktop.getDesktop().browse(new URI(link));
        }catch (Exception ex){
            ex.getStackTrace();
        }
    }

    public static String getSt(){
        return st;
    }

    public static String getFile(){
        return file;
    }
}
