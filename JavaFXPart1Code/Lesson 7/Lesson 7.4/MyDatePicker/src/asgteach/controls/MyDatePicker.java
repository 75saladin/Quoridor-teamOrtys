// MyDatePicker.java - DatePicker control
package asgteach.controls;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyDatePicker extends Application {

    @Override
    public void start(Stage stage) {

        Label label = new Label("Choose a Date");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        
        DatePicker datePicker = new DatePicker();
        datePicker.setOnAction(event -> {
            LocalDate localDate = datePicker.getValue();
            System.out.println(localDate.getMonth() + " "
                + localDate.getDayOfMonth() + ", " + localDate.getYear());
        });
        
        VBox vbox = new VBox(30, label, datePicker);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #e6e6e6");

        Scene scene = new Scene(vbox, 400, 200);
        stage.setTitle("DatePicker Control"); 
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
