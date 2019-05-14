package home.controllers;

import home.util.ConnectionUtil;
import home.util.StudentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class myCourseTeacher implements Initializable {
    @FXML
    private ListView<String> list1;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    protected Button newCoursebtn;
    @FXML
    protected Button viewCoursebtn;
    @FXML
    protected Button deleteCoursebtn;
    @FXML
    protected Button viewStudentbtn;

    private String fileName = null;

    private static String classID;
    private String strTea="";
    public static String[] ListCoTea=new String[10];
    private int i=0;


    private TableView<StudentModel> tableView = new TableView<>();
    private TableColumn column = new TableColumn();
    private TableColumn<StudentModel, String> column1;
    private TableColumn<StudentModel, String> column2;
    private TableColumn<StudentModel, String> column3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
            String stm = "SELECT concat('(',c.classId, ') ', s.subjectName, ' - GV: ', l.lecturerName) FROM uetcourse.classes as c inner join uetcourse.Subjects as s inner join uetcourse.Lecturers as l where c.lecturerId = l.lecturerId and c.subjectId = s.subjectId and l.lecturerId = ?";
            ResultSet rs = null;
            Connection conn = ConnectionUtil.connectdb();
            PreparedStatement prepStatement = conn.prepareStatement(stm);
            prepStatement.setString(1, Login.getUserID());

            rs = prepStatement.executeQuery();


            while (rs.next()) {
                strTea = rs.getString(1);
                ListCoTea[i]=strTea;
                i++;
                items.add(strTea);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        list1.setItems(items);
    }

    public void delete(String str) {
        Connection Conn = ConnectionUtil.connectdb();
        try {
            PreparedStatement stmt = Conn.prepareStatement("DELETE FROM uetcourse.classes Where classId =?");
            stmt.setString(1, str);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleFunctions(ActionEvent actionEvent) throws IOException, SQLException {
        String str = list1.getSelectionModel().getSelectedItem();
        int index = list1.getSelectionModel().getSelectedIndex();
        if (str != null) {
            String substr = str.substring(1, 11);
            classID = substr;
            System.out.println(substr);
            if (actionEvent.getSource() == viewCoursebtn) {
                Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/coursesView.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image("/home/image/icon.png"));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setTitle(str);
                stage.show();
            } else if (actionEvent.getSource() == deleteCoursebtn) {
                System.out.println(substr);
                list1.getItems().remove(index);
                delete(substr);
            } else if (actionEvent.getSource() == viewStudentbtn) {
                Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/studentListView.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image("/home/image/icon.png"));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setTitle("Student List" + str);
                stage.show();
            }
        } else if (actionEvent.getSource() == newCoursebtn) {
            GridPane grid = newCourse();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(grid,500,300));
            stage1.getIcons().add(new Image("/home/image/icon.png"));
            Label headerLabel = new Label("Add New Course:");
            headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            grid.add(headerLabel, 0,0,2,1);
            GridPane.setHalignment(headerLabel, HPos.CENTER);
            GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

            // Add Name
            Label nameLabel = new Label("Course Name: ");
            grid.add(nameLabel, 0,1);
            TextField nameField = new TextField();
            grid.add(nameField, 1,1);

            //Add Course ID
            Label courseIdLabel = new Label("Course ID : ");
            grid.add(courseIdLabel,0,2);
            TextField courseIdField = new TextField();
            grid.add(courseIdField,1,2);

            //Add CreditNumber
            Label creditNumLabel = new Label("Credit Number : ");
            grid.add(creditNumLabel,0,3);
            TextField creditNumField = new TextField();
            grid.add(creditNumField,1,3);

            // Add Semester
            Label semesterLabel = new Label("Semester : ");
            grid.add(semesterLabel, 0, 4);
            TextField semesterField = new TextField();
            grid.add(semesterField, 1, 4);

            // Add Submit Button
            Button submitButton = new Button("Add");
            submitButton.setPrefHeight(40);
            submitButton.setDefaultButton(true);
            submitButton.setPrefWidth(100);
            grid.add(submitButton, 0, 6, 2, 1);
            GridPane.setHalignment(submitButton, HPos.CENTER);
            GridPane.setMargin(submitButton, new Insets(20, 0,20,0));

            submitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (nameField.getText().isEmpty() == false && semesterField.getText().isEmpty() && false && creditNumField.getText().isEmpty() == false && courseIdField.getText().isEmpty() == false) {
                        String subjectName = nameField.getText();
                        String semester = semesterField.getText();
                        String creNum = creditNumField.getText();
                        String courseId = courseIdField.getText();
                        String subjectId = courseId.substring(0, 7);
                        System.out.println(subjectId);
                        String profId = Login.getUserID();
                        Connection con = ConnectionUtil.connectdb();
                        try {
                            String sql = "INSERT INTO uetcourse.classes (classId, lecturerId, subjectId, semester) VALUES (?,?,?,?)";
                            PreparedStatement preparedStatement = con.prepareStatement(sql);
                            preparedStatement.setString(1, courseId);
                            preparedStatement.setString(2, profId);
                            preparedStatement.setString(3, subjectId);
                            preparedStatement.setString(4, semester);
                            preparedStatement.executeUpdate();
                            list1.getItems().clear();

                            Connection Conn = ConnectionUtil.connectdb();
                            Statement stmt = null;
                            ResultSet rs = null;
                            try {
                                stmt = Conn.createStatement();
                                rs = stmt.executeQuery("SELECT concat('(',c.classId, ') ', s.subjectName, ' - GV: ', l.lecturerName) FROM uetcourse.classes as c inner join uetcourse.Subjects as s inner join uetcourse.Lecturers as l where c.lecturerId = l.lecturerId and c.subjectId = s.subjectId;");

                                while (rs.next()) {
                                    String str1 = rs.getString(1);
                                    items.add(str1);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            list1.setItems(items);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        stage1.close();
                        showAlert(Alert.AlertType.INFORMATION, grid.getScene().getWindow(), "Course Added Successful!", "Click ok button to return");
                    } else {
                        showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(), "Error Form", "Please Fill in empty fields");
                    }
                }
            });
            stage1.show();
        } else {
            Login.infoBox("Please select a class", null, "Error");
        }
    }

    public ListView<String> getList() {
        return list1;
    }

    /*public void buildData(String string) throws SQLException{
        String sql = "SELECT s1.studentId, s1.studentName, s.grade FROM uetcourse.students_subjects as s inner join uetcourse.studentsInfo as s1 where s.studentsId = s1.studentId and s.classId = ?;";
        ResultSet rs = null;
        try{
            Connection con = ConnectionUtil.connectdb();
            PreparedStatement prepState =  con.prepareStatement(sql);
            prepState.setString(1,string);
            rs = prepState.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }

        ObservableList dbData = FXCollections.observableArrayList(dataBaseArrayList(rs));
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++){
            switch (rs.getMetaData().getColumnName(i+1)){
                case "studentId":
                    column.setText("MSSV");
                    break;
                case "studentName":
                    column.setText("Ten SV");
                    break;
                case "grade":
                    column.setText("Diem");
                    break;
            }
            column.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
            tableView.getColumns().add(column);
        }
        tableView.setItems(dbData);
    }

    private ArrayList dataBaseArrayList(ResultSet resultSet) throws SQLException{
        ArrayList<StudentModel> data = new ArrayList<>();
        while (resultSet.next()) {
            StudentModel studMod = new StudentModel();
            studMod.studentId.set(resultSet.getString("studentId"));
            studMod.studentName.set(resultSet.getString("studentName"));
            if(resultSet.getString("grade") != null) {
                studMod.grade.set(resultSet.getString("grade"));
            } else {
                studMod.grade.set("Not yet graded");
            }
            data.add(studMod);
            data.add(new StudentModel(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3)));
        }
        return data;
    } */
    /*
    private void handleUpdate(TableColumn column){
        tableView.setEditable(true);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        column.setCellValueFactory(TextFieldTableCell.forTableColumn());
        column.setCellValueFactory(new PropertyValueFactory<>("grade"));
        column.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<StudentModel, String>>) event -> {
            StudentModel studentMod = event.getRowValue();
            studentMod.setGrade(event.getNewValue());
            ConnectionUtil.updateData("grade",event.getNewValue(),studentMod.getClassId());
        });
    } */
    private GridPane newCourse(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40,40,40,40));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(150,150, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        return gridPane;
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }


    public static String getClassID(){
        return classID;
    }
}
