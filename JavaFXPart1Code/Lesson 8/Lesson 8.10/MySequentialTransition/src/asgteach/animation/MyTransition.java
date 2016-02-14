// MyTransition.java - Build TransitionX, TransitionY
package asgteach.animation;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class MyTransition {
    public static TranslateTransition buildX(Node node, double deltaX) {
        TranslateTransition translateTransition = 
                new TranslateTransition(Duration.seconds(2), node);
        translateTransition.setByX(deltaX);
        return translateTransition;
    }
    
    public static TranslateTransition buildY(Node node, double deltaY) {
        TranslateTransition translateTransition = 
                new TranslateTransition(Duration.seconds(1.5), node);
        translateTransition.setByY(deltaY);
        return translateTransition;
    }
}
