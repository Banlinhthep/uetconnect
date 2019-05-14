package home.util;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;

public class CourseModel {

    private SimpleIntegerProperty week;
    private SimpleStringProperty nameDoc;
    private File files;

   public CourseModel(int week, File file){
          this.week = new SimpleIntegerProperty(week);
          this.files = file;
    }

    public int getWeek() {
        return week.get();
    }

    public SimpleIntegerProperty weekProperty() {
        return week;
    }

    public void setWeek(int week) {
        this.week.set(week);
    }

    public String getNameDoc() {
        return nameDoc.get();
    }

    public SimpleStringProperty nameDocProperty() {
        return nameDoc;
    }

    public void setNameDoc(String nameDoc) {
        this.nameDoc.set(nameDoc);
    }

    public File getFiles() {
        return files;
    }

    public void setFiles(File files) {
        this.files = files;
    }
}
