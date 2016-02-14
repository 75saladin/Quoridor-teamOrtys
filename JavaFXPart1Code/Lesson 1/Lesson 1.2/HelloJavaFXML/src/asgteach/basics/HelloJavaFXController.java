// HelloJavaFXController.java - Controller
package asgteach.basics;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class HelloJavaFXController implements Initializable {
    
    @FXML
    private Rectangle rectangle;
    
    @FXML
    private Text text;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Fade Text node in and out
        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), text);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setCycleCount(Animation.INDEFINITE);
        fade.setAutoReverse(true);
        fade.play();
    }    
}
