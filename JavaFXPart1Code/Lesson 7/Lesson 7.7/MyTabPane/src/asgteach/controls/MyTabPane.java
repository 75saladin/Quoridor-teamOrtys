// MyTabPane.java - TabPane control
package asgteach.controls;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MyTabPane extends Application {

    @Override
    public void start(Stage stage) {
        
        Tab tab1 = makeTab("Spain", "orange", false);
        Tab tab2 = makeTab("Brazil", "yellow", false);
        Tab tab3 = makeTab("Italy", "white", false);
        Tab tab4 = makeTab("Portugal", "lightgreen", false);
        Tab tab5 = makeTab("Norway", "salmon", true);
        
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);
        tabPane.setStyle("-fx-background-color: lightgray");

        Scene scene = new Scene(tabPane, 400, 300);
        stage.setTitle("TabPane Control"); 
        
        stage.setScene(scene);
        stage.show();
    }
    
    private Tab makeTab(String country, String color, boolean close) {
        Tab tab = new Tab(country);
        tab.setStyle("-fx-background-color: " + color);
        Image flag = new Image("resources/" + country + ".png", 250, 150, true, true);
        tab.setContent(new StackPane(new ImageView(flag)));
        tab.setTooltip(new Tooltip(country + " Flag"));
        tab.setClosable(close);
        if (close)
            tab.setOnCloseRequest(event -> System.out.println("closed"));
        return tab;
    }

    public static void main(String[] args) {
        launch();
    }
}
