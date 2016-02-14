// EagerPerson.java - Person class, eager evaluation
package asgteach.properties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EagerPerson {
    private final StringProperty fullname = 
        new SimpleStringProperty(this, "fullname", "");
    
    public EagerPerson() {
        //if (fullname != null)
            //System.out.println("EagerPerson: fullname property instantiated");
    }
    public StringProperty fullnameProperty() {
        return fullname;
    }
    public final String getFullname() { 
        return fullname.get(); 
    }
    public final void setFullname(String fullname) {
        this.fullname.set(fullname);
    }
    @Override
    public String toString() { return getFullname(); }
}
