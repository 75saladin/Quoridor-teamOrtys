// MyTree.java - Builds tree 
package asgteach.controls;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class MyTree {
    public static TreeView<String> buildTree() {
        TreeItem<String> leagues = new TreeItem<>("Major League Baseball");
        
        TreeItem<String> national = new TreeItem<>("National League");
        TreeItem<String> nationalEast = new TreeItem<>("East");
        TreeItem<String> nationals = new TreeItem<>("Nationals");
        nationalEast.getChildren().add(nationals);
        national.getChildren().add(nationalEast);
        
        TreeItem<String> nationalCentral = new TreeItem<>("Central");
        TreeItem<String> cubs = new TreeItem<>("Cubs");
        nationalCentral.getChildren().add(cubs);
        national.getChildren().add(nationalCentral);
        
        TreeItem<String> nationalWest = new TreeItem<>("West");
        TreeItem<String> padres = new TreeItem<>("Padres");
        nationalWest.getChildren().add(padres);
        national.getChildren().add(nationalWest);
        national.setExpanded(true);
        leagues.getChildren().add(national);
        
        TreeItem<String> american = new TreeItem<>("American League");
        TreeItem<String> americanEast = new TreeItem<>("East");
        TreeItem<String> yankees = new TreeItem<>("Yankees");
        americanEast.getChildren().add(yankees);
        american.getChildren().add(americanEast);
        
        TreeItem<String> americanCentral = new TreeItem<>("Central");
        TreeItem<String> tigers = new TreeItem<>("Tigers");
        americanCentral.getChildren().add(tigers);
        american.getChildren().add(americanCentral);
        
        TreeItem<String> americanWest = new TreeItem<>("West");
        TreeItem<String> angels = new TreeItem<>("Angels");
        americanWest.getChildren().add(angels);
        american.getChildren().add(americanWest);
        american.setExpanded(true);
        leagues.getChildren().add(american);
        leagues.setExpanded(true);
        
        return new TreeView<>(leagues);
    }
}
