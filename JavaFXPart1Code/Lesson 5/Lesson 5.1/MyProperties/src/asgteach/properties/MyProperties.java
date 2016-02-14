// MyProperties.java - JavaFX Bean properties
package asgteach.properties;

public class MyProperties {

    public static void main(String[] args) {
        CircleReadWrite circle1 = new CircleReadWrite();
        System.out.println(circle1);
        circle1.setRadius(20.5);                // call setter
        System.out.println(circle1);
        circle1.radiusProperty().set(30.5);     // call set with property
        System.out.println(circle1);
        
        CircleReadOnly circle2 = new CircleReadOnly();
        System.out.println(circle2);
        //circle2.setRadius(20.5);              // can't do this
        //circle2.radiusProperty().set(20.5);   // can't do this
        
        CircleImmutable circle3 = new CircleImmutable();
        System.out.println(circle3);
        //circle3.setRadius(20.5);              // can't do this
    }
}

/****************************************************

$ java MyProperties
1.0
20.5
30.5
1.0
1.0

*/