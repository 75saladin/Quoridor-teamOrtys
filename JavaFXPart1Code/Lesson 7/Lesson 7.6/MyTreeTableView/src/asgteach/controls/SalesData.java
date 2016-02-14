// SalesData.java - SalesData class with properties
package asgteach.controls;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SalesData {
    private final StringProperty company = new SimpleStringProperty();
    private final DoubleProperty units = new SimpleDoubleProperty();
    
    public SalesData() {
        this("", 0.0);
    }
    public SalesData(String company, Double units) {
        setCompany(company);
        setUnits(units);
    }
    
    public StringProperty companyProperty() { 
        return company; 
    }
    
    public DoubleProperty unitsProperty() { 
        return units; 
    }

    public final String getCompany() {
        return company.get();
    }

    public final void setCompany(String company) {
        this.company.set(company);
    }

    public final Double getUnits() {
        return units.get();
    }

    public final void setUnits(Double units) {
        this.units.set(units);
    }
    
}
