// MyCircle.java - Build Circle 
package asgteach.animation;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MyCircle {
    public static Group build(Color color, String circleNumber, 
            double centerX, double centerY) {

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.GRAY);

        RadialGradient radialGradient = new RadialGradient(
                0,              // focusAngle
                .1,             // focusDistance
                centerX - 10,   // centerX 
                centerY - 10,   // centerY 
                85,             // radius 
                false,          // proportional 
                CycleMethod.NO_CYCLE,   // cycleMethod 
                new Stop(0, color), new Stop(1, Color.BLACK)); // stops
        
        Circle circle = new Circle(centerX, centerY, 30);
        circle.setFill(radialGradient);
        circle.setEffect(dropShadow);

        Text text = new Text(circleNumber);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        text.setLayoutX(centerX - 5);
        text.setLayoutY(centerY + 2);

        return new Group(circle, text);
    }
}
