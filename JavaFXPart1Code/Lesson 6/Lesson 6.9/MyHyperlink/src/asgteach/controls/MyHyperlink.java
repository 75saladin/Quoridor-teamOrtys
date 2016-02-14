// MyHyperlink.java - Hyperlink control
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyHyperlink extends Application {
    
    private final ImageView selectedImage = new ImageView();

    @Override
    public void start(Stage stage) {

        DropShadow dropShadow = new DropShadow();                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        Label label = new Label("Select an Icon");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        
        Hyperlink firstLink = makeHyperlink("Yellow");
        Hyperlink secondLink = makeHyperlink("Cyan");
        
        Button clearButton = new Button("Clear Links");
        clearButton.setEffect(dropShadow);
        clearButton.setOnAction(event -> {
            firstLink.setVisited(false);
            secondLink.setVisited(false);
            selectedImage.setImage(null);
        });
        
        VBox vbox = new VBox(10, label, firstLink, secondLink, clearButton);
        vbox.setAlignment(Pos.CENTER_LEFT);
        
        HBox hbox = new HBox(vbox, selectedImage);
        hbox.setPadding(new Insets(50, 50, 50, 100));
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setStyle("-fx-background-color: palegreen");

        Scene scene = new Scene(hbox, 400, 200);
        stage.setTitle("Hyperlink Control"); 

        stage.setScene(scene);
        stage.show();
    }
    
    private Hyperlink makeHyperlink(String name) {
        Image image = new Image("resources/" + name + ".png");
        Hyperlink hyperlink = new Hyperlink(name);
        hyperlink.setOnAction(event -> selectedImage.setImage(image));
        return hyperlink;
    }

    public static void main(String[] args) {
        launch();
    }
}
