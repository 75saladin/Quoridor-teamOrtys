// MyTextFlow.java - TextFlow layout
package asgteach.layouts;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class MyTextFlow extends Application {

    @Override
    public void start(Stage stage) {

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        Text text = new Text("My TextFlow Layout ");
        text.setFont(Font.font("Verdana", FontPosture.ITALIC, 48));
        text.setFill(Color.BLUE);
        
        Arc arc = new Arc(80, 100, 40, 40, 225, 270);
        arc.setFill(Color.YELLOW);
        arc.setStroke(Color.BLUE);
        arc.setType(ArcType.ROUND);
               
        TextFlow textFlow = new TextFlow(text, arc);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        textFlow.setLineSpacing(10);
        
        VBox vbox = new VBox(textFlow);
        vbox.setAlignment(Pos.CENTER);
        vbox.setEffect(dropShadow);

        Scene scene = new Scene(vbox, 600, 200, Color.LIGHTGREEN);
        stage.setTitle("TextFlow Layout");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
}
