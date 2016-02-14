// MyColors.java - Creating colors
package asgteach.colors;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyColors extends Application {
    
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        
        Text t1 = doText(50, 50, Color.RED);
        Text t2 = doText(50, 80, new Color(1,0,0,1.0));
        Text t3 = doText(50, 110, Color.color(1,0,0));
        Text t4 = doText(50, 140, Color.rgb(255,0,0));
        Text t5 = doText(50, 170, Color.web("0xFF0000"));
        Text t6 = doText(50, 200, Color.web("#FF0000"));
        Text t7 = doText(50, 230, Color.web("rgb(255,0,0)"));
        
        root.getChildren().addAll(t1, t2, t3, t4, t5, t6, t7);
        
        Scene scene = new Scene(root, 500, 300, Color.LIGHTYELLOW);
        stage.setTitle("Colors");
        stage.setScene(scene);
        stage.show();
    }
    
    private static Text doText(int x, int y, Color c) {
        Text text = new Text(x, y, "One Two Three Four");
        text.setFont(new Font(20));
        text.setFill(c);
        return text;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
