// MyTextArea.java - TextArea control
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyTextArea extends Application {

    private TextArea textArea;

    @Override
    public void start(Stage stage) {

        final String myString =
                "The quick brown fox jumped over the lazy dogs.";

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));

        Label label = new Label("Enter Some Text");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        
        textArea = new TextArea(myString);
        textArea.setStyle("-fx-font-family: Geneva; -fx-font-size: 18;");
        textArea.setWrapText(true);
        textArea.setEffect(dropShadow);
        //textArea.setEditable(false);

        RadioButton arialButton = makeRadioButton("Arial");
        RadioButton calibriButton = makeRadioButton("Calibri");
        RadioButton cambriaButton = makeRadioButton("Cambria");
        
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(arialButton, calibriButton, cambriaButton);

        HBox hbox = new HBox(10, arialButton, calibriButton, cambriaButton);
        hbox.setAlignment(Pos.CENTER);
        
        VBox vbox = new VBox(20, label, textArea, hbox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));
        vbox.setStyle("-fx-background-color: palegreen");

        Scene scene = new Scene(vbox, 400, 300);
        stage.setTitle("TextArea Control");

        stage.setScene(scene);
        stage.show();
    }

    private RadioButton makeRadioButton(String fontName) {
        RadioButton button = new RadioButton(fontName);
        button.setOnAction(event -> textArea.setStyle("-fx-font-family: "
                    + fontName + "; -fx-font-size: 18;"));
        return button;
    }

    public static void main(String[] args) {
        launch();
    }
}
