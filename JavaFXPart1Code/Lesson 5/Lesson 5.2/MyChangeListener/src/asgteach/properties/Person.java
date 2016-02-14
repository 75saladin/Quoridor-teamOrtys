// Person.java -  JavaFXBean properties
package asgteach.properties;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
    private final StringProperty fullname = 
        new SimpleStringProperty(this, "fullname", "");

    private final BooleanProperty employed = 
        new SimpleBooleanProperty(this, "employed", false);
    
    public StringProperty fullnameProperty() {
        return fullname;
    }
    public BooleanProperty employedProperty() {
        return employed;
    }
    public final String getFullname() { 
        return fullname.get(); 
    }
    public final void setFullname(String fullname) {
        this.fullname.set(fullname);
    }
    public final boolean isEmployed() {
        return employed.get();
    }
    public final void setEmployed(boolean employed) {
        this.employed.set(employed);
    }
    @Override
    public String toString() {
        return getFullname() + " is " + 
            (isEmployed() ? "employed" : "not employed"); }
}
