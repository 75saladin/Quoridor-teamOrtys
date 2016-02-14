// MyHTMLEditor.java - HTMLEditor control
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class MyHTMLEditor extends Application {

    @Override
    public void start(Stage stage) {
        
        final String myString =
                "The quick brown fox jumped over the lazy dogs.";
        
        HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setHtmlText(myString);
        htmlEditor.setPrefHeight(300);
        
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        
        Button button = new Button("HTML Code");
        button.setStyle("-fx-background-color: palegreen");
        button.setOnAction(event -> textArea.setText(htmlEditor.getHtmlText()));
        
        VBox vbox = new VBox(30, htmlEditor, button, textArea);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #e6e6e6");

        Scene scene = new Scene(vbox, 650, 400);
        stage.setTitle("HTMLEditor Control"); 
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
