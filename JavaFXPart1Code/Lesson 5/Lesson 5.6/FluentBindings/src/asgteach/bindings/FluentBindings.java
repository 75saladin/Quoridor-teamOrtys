// FluentBindings.java - Fluent API Bindings
package asgteach.bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;

public class FluentBindings {

    public static void main(String[] args) {
        DoubleProperty a = new SimpleDoubleProperty(3.5);
        DoubleProperty b = new SimpleDoubleProperty(4.5);
        Circle circle = new Circle(10);
        
        NumberBinding sum = a.add(b);
        circle.radiusProperty().bind(sum);
        System.out.println("radius = " + circle.getRadius());
        a.set(5.5);
        System.out.println("radius = " + circle.getRadius());
        
        NumberBinding value = new When(a.greaterThan(b)).then(a).otherwise(b);
        circle.strokeWidthProperty().bind(value);
        System.out.println("strokeWidth = " + circle.getStrokeWidth());
        b.set(6.5);
        System.out.println("strokeWidth = " + circle.getStrokeWidth());
        
        BooleanBinding show = a.isEqualTo(b);
        circle.visibleProperty().bind(show);
        System.out.println("visible = " + circle.isVisible());
        a.set(6.5);
        System.out.println("visible = " + circle.isVisible());
    }
}

/****************************************************

$ java FluentBindings
radius = 8.0
radius = 10.0
strokeWidth = 5.5
strokeWidth = 6.5
visible = false
visible = true

*/

