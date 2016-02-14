// MyEventHandler.java - Event handlers
package asgteach.controls;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyEventHandler extends Application {
    
    private void buttonHandler(ActionEvent event) {
        System.out.println("Method Reference");
    }
    
    @Override
    public void start(Stage stage) {
               
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        Button button1 = new Button("Anonymous Class");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Anonymous Class");
            }
        });
        
        Button button2 = new Button("Lambda");
        button2.setOnAction(event -> {
            System.out.println("Lambda");
        });
        //button2.setOnAction(event -> System.out.println("Lambda"));
        
        Button button3 = new Button("Method Reference");
        button3.setOnAction(this::buttonHandler);
        
        HBox buttons = new HBox(10, button1, button2, button3); 
        buttons.setAlignment(Pos.CENTER);
        buttons.setEffect(dropShadow);
        
        StackPane stackPane = new StackPane(buttons);
        stackPane.setStyle("-fx-background-color: palegreen");
        
        Scene scene = new Scene(stackPane, 400, 200);
        stage.setTitle("Event Handlers");
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
