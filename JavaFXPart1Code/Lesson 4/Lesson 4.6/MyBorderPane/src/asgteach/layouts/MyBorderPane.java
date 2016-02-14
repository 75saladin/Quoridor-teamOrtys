// MyBorderPane.java - BorderPane layout
package asgteach.layouts;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyBorderPane extends Application {

    @Override
    public void start(Stage stage) {
              
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(drawRegion("Top", "#ffff00ff"));
        borderPane.setLeft(drawRegion("Left", "#008000ff")); 
        borderPane.setCenter(drawRegion("Center", "#90ee90ff"));
        borderPane.setRight(drawRegion("Right", "#b22222ff"));
        borderPane.setBottom(drawRegion("Bottom", "#ffa500ff"));

        Scene scene = new Scene(borderPane, 404, 404);
        stage.setTitle("BorderPane Layout");
        stage.setScene(scene);
        stage.show();
    }
    
    private Region drawRegion(String myText, String bgColor) {
        Text text = new Text(myText);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(20, 20, 20, 20));
        stackPane.setStyle("-fx-background-color: " +  bgColor);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(text);
        return stackPane;
    }

    public static void main(String[] args) {
        launch();
    }
}
