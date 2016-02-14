// CircleReadWrite.java - Read-write property
package asgteach.properties;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CircleReadWrite {
    private final DoubleProperty radius = new SimpleDoubleProperty(1.0);

    public final double getRadius() {
        return radius.get();
    }

    public final void setRadius(double radius) {
        this.radius.set(radius);
    }

    public DoubleProperty radiusProperty() {
        return radius;
    }
    
    @Override
    public String toString() {
        return String.valueOf(radius.get());
    }
}
