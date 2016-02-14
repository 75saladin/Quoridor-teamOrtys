// LazyPerson.java - Person class, lazy evaluation
package asgteach.properties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LazyPerson {
    private String _fullname = "";
    private StringProperty fullname = null;
    
    public LazyPerson() {
        //if (fullname == null)
            //System.out.println("LazyPerson: fullname property not instantiated");
    }
    public StringProperty fullnameProperty() {
        if (fullname == null) {
            fullname = new SimpleStringProperty(this, "fullname", _fullname);
            //System.out.println("fullname property instantiated");
        }
        return fullname;
    }
    public final String getFullname() { 
        if (fullname == null) 
            return _fullname;
        return fullname.get(); 
    }
    public final void setFullname(String fullname) {
        if (this.fullname == null)
            _fullname = fullname;
        else this.fullname.set(fullname);
    }
    @Override
    public String toString() { return getFullname(); }
}
