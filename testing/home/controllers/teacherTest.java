package home.controllers;

import home.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.robot.SleepRobot;

import static home.controllers.myCourseTeacher.ListCoTea;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class teacherTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("fxml/login.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void TestTeach() {

        clickOn("#textEmail");
        write("dolp");
        clickOn("#textPassword");
        write("12345");
        clickOn("#loginButton");
        assertEquals(Login.getInfo(), "Le Phe Do");

        clickOn("#myCourseBtn");
        String[] test = new String[10];
        test[0] = "(INT1002-23) Giai Tich 1 - GV: Le Phe Do";
        test[1] = "(INT1002-5) Giai Tich 1 - GV: Le Phe Do";
        test[2] = "(INT1003-23) Giai tich 2 - GV: Le Phe Do";
        test[3] = "(INT1003-3) Giai tich 2 - GV: Le Phe Do";
        test[4] = "(INT2002-2) Phuong phap tinh - GV: Le Phe Do";
        assertArrayEquals(ListCoTea, test);

        clickOn(ListCoTea[0]);
        clickOn("#viewCoursebtn");
        clickOn("4");
        clickOn("View File");
        this.sleep(1234);


    }
}
