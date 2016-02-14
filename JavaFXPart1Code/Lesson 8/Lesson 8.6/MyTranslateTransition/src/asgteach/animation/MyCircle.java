// MyCircle.java - Circle with Translate Transition
package asgteach.animation;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MyCircle {
    public static TranslateTransition build(Color color, 
            int toWidth, double delayTime) {
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.GRAY);
        
        RadialGradient radialGradient = new RadialGradient(
                0,                      // focusAngle
                .1,                     // focusDistance
                0,                      // centerX 
                0,                      // centerY 
                85,                     // radius 
                false,                  // proportional 
                CycleMethod.NO_CYCLE,   // cycleMethod 
                new Stop(0, color), new Stop(1, Color.BLACK)); // stops
        Circle circle = new Circle(30);
        circle.setFill(radialGradient);
        circle.setEffect(dropShadow);
        
        TranslateTransition translateTransition = 
                new TranslateTransition(Duration.seconds(2), circle);
        translateTransition.setFromX(0);
        translateTransition.setToX(toWidth);
        translateTransition.setRate(1.0);        // default
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.setDelay(Duration.seconds(delayTime));
        return translateTransition;
    }
}
