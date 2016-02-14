// MySplitPane.java - SplitPane control
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MySplitPane extends Application {

    @Override
    public void start(Stage stage) {
        
        final String myText = 
                "The quick brown fox jumped over the lazy dogs.";
        
        DropShadow dropShadow = new DropShadow();                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        StackPane sp1 = makeTextArea(myText, "Tahoma", 14);
        StackPane sp2 = makeTextArea(myText, "Verdana", 18);
        StackPane sp3 = makeTextArea(myText, "Cambria", 20);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(sp1, sp2, sp3);
        splitPane.setDividerPositions(0.3, 0.65, 0.9 );
        splitPane.setEffect(dropShadow);
        //splitPane.setOrientation(Orientation.VERTICAL);

        Label label = new Label("Multiple Text Areas");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));

        VBox vbox = new VBox(20, label, splitPane);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));
        vbox.setStyle("-fx-background-color: palegreen");
        
        Scene scene = new Scene(vbox, 500, 300);
        stage.setTitle("SplitPane Control"); 
        
        vbox.prefWidthProperty().bind(scene.widthProperty());
        vbox.prefHeightProperty().bind(scene.heightProperty());

        stage.setScene(scene);
        stage.show();
    }
    
    private StackPane makeTextArea(String text, String fontName, int pointSize) {
        TextArea textArea = new TextArea(text);
        textArea.setStyle("-fx-font-family: " +
                fontName + ";" + " -fx-font-size: " + pointSize + ";");
        textArea.setWrapText(true);
        return new StackPane(textArea);
    }

    public static void main(String[] args) {
        launch();
    }
}
