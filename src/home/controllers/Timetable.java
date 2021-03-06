package home.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.MonthEntryView;
import com.calendarfx.view.MonthView;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class Timetable  implements Initializable{


    private CalendarView calendar;


    @FXML
    private GridPane pnlHost;

    //ap dung cac ham co san trong thu vien Calendar
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCalendar();

    }

    private void loadCalendar() {
        calendar = new CalendarView();

        Calendar classes = new Calendar("Classes");
        Calendar meetings = new Calendar("Meetings");

        classes.setStyle(Calendar.Style.STYLE2);
        meetings.setStyle(Calendar.Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("Timetable");
        myCalendarSource.getCalendars().addAll(classes, meetings);

        calendar.getCalendarSources().addAll(myCalendarSource);

        calendar.setRequestedTime(LocalTime.now());


        //Update time hien tai
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendar.setToday(LocalDate.now());
                        calendar.setTime(LocalTime.now());
                    });

                    try {

                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        calendar.showMonthPage();
        pnlHost.getChildren().add(calendar);
    }

}




