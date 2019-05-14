package home;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    private double height;
    private double width;
    private Stage preloaderStage;

    //load san khau chinh
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.preloaderStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        primaryStage.setTitle("");
        Image icon = new Image(getClass().getResourceAsStream("/home/image/icon.png"));primaryStage.getIcons().add(icon);
        primaryStage.setScene(new Scene(root, 637.0, 649.0));
        primaryStage.setTitle("UETConnect");
        primaryStage.setOnCloseRequest( e -> {
            try {
                stop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        try {
            super.stop(); //To change body of generated methods, choose Tools | Templates.
            System.exit(0);
            Platform.exit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
