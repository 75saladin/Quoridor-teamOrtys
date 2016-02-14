// MyChangeListener.java - Property Change Listener
package asgteach.properties;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MyChangeListener {

    public static void main(String[] args) {
        final Person male = new Person();
        male.setFullname("Joe Smith");
        male.setEmployed(true);
        System.out.println(male);
        
        male.fullnameProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> 
                observableValue, String oldValue, String newValue) {
                    System.out.println(observableValue.getValue());
                    System.out.println("oldValue = " + oldValue);
                    System.out.println("newValue = " + newValue);
                    System.out.println(male);
                }
        });
        male.setFullname("Joseph Smith");
    }
}

/****************************************************

$ java MyChangeListener
Joe Smith is employed
Joseph Smith
oldValue = Joe Smith
newValue = Joseph Smith
Joseph Smith is employed

*/