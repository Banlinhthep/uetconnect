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

import static home.controllers.Mycourse.ListCo;
import static org.junit.Assert.*;

public class studentTest extends ApplicationTest {
    @Override
    public void start (Stage stage) throws Exception {
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
    public void TestStu() {

        clickOn("#textEmail");
        write("17020229");
        clickOn("#textPassword");
        write("An123");
        clickOn("#loginButton");
        assertEquals(Login.getInfo(), "Nguyen Viet An");


        String[] test=new String[10];
        test[0]="(INT1002-23) Giai Tich 1- So tin chi: 4- GV: Le Phe Do";
        test[1]="(INT1003-23) Giai tich 2- So tin chi: 3- GV: Le Phe Do";
        test[2]="(INT2006-23) Marketing- So tin chi: 3- GV: Tran Binh Trong";
        test[3]="(INT4003-23) Cong Nghe Phan Mem- So tin chi: 3- GV: Tran Binh Trong";
        clickOn("#btnMycourse");
        assertArrayEquals(test,ListCo);

        lookup(test[0]);
        clickOn(ListCo[0]);
        clickOn("#viewCourse");
        lookup("Text Book and References");
        clickOn("Text Book and References");
        this.sleep(1234);

    }
}