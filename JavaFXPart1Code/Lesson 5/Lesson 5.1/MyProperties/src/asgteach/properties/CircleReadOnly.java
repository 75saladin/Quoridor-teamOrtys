// CircleReadOnly.java - Read-only property
package asgteach.properties;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CircleReadOnly {
    private final DoubleProperty radius = new SimpleDoubleProperty(1.0);

    public final double getRadius() {
        return radius.get();
    }

    private void setRadius(double radius) {
        this.radius.set(radius);
    }

    public ReadOnlyDoubleProperty radiusProperty() {
        return radius;
    }
    
    @Override
    public String toString() {
        return String.valueOf(radius.get());
    }
}
