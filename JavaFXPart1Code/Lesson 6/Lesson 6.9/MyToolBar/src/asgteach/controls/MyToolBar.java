// MyToolBar.java - ToolBar control
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyToolBar extends Application {

    private TextArea textArea;
    private final String style = "-fx-font-family: Tahoma; -fx-font-size: 18;";

    @Override
    public void start(Stage stage) {

        final String myString =
                "The quick brown fox jumped over the lazy dogs.";

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        ToolBar toolBar = makeToolBar("Red", "Blue", "Green", "Copy", "Paste");
        toolBar.setMaxWidth(200);
        toolBar.setEffect(dropShadow);
        
        textArea = new TextArea(myString);
        textArea.setStyle(style);
        textArea.setWrapText(true);
        textArea.setEffect(dropShadow);
   
        VBox vbox = new VBox(20, toolBar, textArea);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));
        vbox.setStyle("-fx-background-color: palegreen");

        Scene scene = new Scene(vbox, 400, 300);
        stage.setTitle("ToolBar Control");

        stage.setScene(scene);
        stage.show();
    }
    
    private ToolBar makeToolBar(String... names) {
        ToolBar toolBar = new ToolBar();
        for (String name : names) {
            Button button = new Button();
            button.setTooltip(new Tooltip(name));
            toolBar.getItems().add(button);
            Image image = new Image("resources/" + name + ".png");
            button.setGraphic(new ImageView(image));
            button.setOnAction(event -> {
                switch (name) {
                    case "Copy":
                        textArea.copy();
                        break;
                    case "Paste":
                        textArea.paste();
                        break;
                    default:
                        textArea.setStyle(style + " -fx-text-fill: " + name);
                        break;
                }
            });
        }
        return toolBar;
    }

    public static void main(String[] args) {
        launch();
    }
}
