// MyListenerBind.java - Change Listener implementing bind, unbind
package asgteach.bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Circle;

public class MyListenerBind {

    public static void main(String[] args) {
        final Circle circle1 = new Circle(10.5);
        final Circle circle2 = new Circle(10.5);
        
        System.out.println("Circle1: " + circle1.getRadius());
        System.out.println("Circle2: " + circle2.getRadius());
        
        circle2.radiusProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue <? extends Number>
                    observableValue, Number oldValue, Number newValue) {
                        circle1.setRadius(newValue.doubleValue());
                }
        });
        
        circle2.setRadius(20.5);
        
        System.out.println("Circle1: " + circle1.getRadius());
        System.out.println("Circle2: " + circle2.getRadius());
    }
}

/****************************************************

$ java MyListenerBind
Circle1: 10.5
Circle2: 10.5
Circle1: 20.5
Circle2: 20.5

*/

