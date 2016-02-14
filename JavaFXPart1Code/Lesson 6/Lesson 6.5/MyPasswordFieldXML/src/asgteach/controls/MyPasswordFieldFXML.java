// MyPaswordFieldFXML.java - main class for MyPasswordFieldFXML project
package asgteach.controls;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyPasswordFieldFXML extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MyPasswordField.fxml"));
        
        Scene scene = new Scene(root, 400, 230);
        
        stage.setScene(scene);
        stage.setTitle("PasswordField Control");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}