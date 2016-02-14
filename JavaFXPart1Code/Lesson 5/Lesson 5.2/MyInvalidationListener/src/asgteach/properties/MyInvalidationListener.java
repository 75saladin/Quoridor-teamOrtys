// MyInvalidationListener.java - Property Invalidation Listener
package asgteach.properties;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class MyInvalidationListener {

    public static void main(String[] args) {
        final Person male = new Person();
        male.setFullname("Joe Smith");
        male.setEmployed(true);
        System.out.println(male);
        
        InvalidationListener mylistener = new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println(male);
            }
        };
        
        male.fullnameProperty().addListener(mylistener);
        male.setFullname("Joseph Smith");
        //male.fullnameProperty().removeListener(mylistener);
        //male.setFullname("Joseph Smithers");
    }
}

/****************************************************

$ java MyInvalidationListener
Joe Smith is employed
Joseph Smith is employed

*/