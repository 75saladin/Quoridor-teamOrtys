// CircleImmutable.java - Immutable property
package asgteach.properties;

public class CircleImmutable {
    private final double radius = 1.0;

    public final double getRadius() {
        return radius;
    }
    
    @Override
    public String toString() {
        return String.valueOf(radius);
    }
}
