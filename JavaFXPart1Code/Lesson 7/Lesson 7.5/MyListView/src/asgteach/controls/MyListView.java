// MyListView.java - ListView control
package asgteach.controls;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyListView extends Application {

    @Override
    public void start(Stage stage) {

        DropShadow dropShadow = new DropShadow();                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        ObservableList<String> leftList =
            FXCollections.observableArrayList("Red", "Green",
                "Blue", "Yellow", "Orange", "Purple", 
                    "Violet", "Brown", "Black", "White");
        
        ListView<String> leftListView = new ListView<>(leftList);
        leftListView.setMaxWidth(300);
        leftListView.setMaxHeight(300);        
        leftListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        leftListView.setEffect(dropShadow);
        
        ObservableList<String> rightList = FXCollections.observableArrayList();
        
        ListView<String> rightListView = new ListView<>(rightList);
        rightListView.setMaxWidth(300);
        rightListView.setMaxHeight(300);                
        rightListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        rightListView.setEffect(dropShadow);
        
        Button leftButton = new Button("<-");
        Button rightButton = new Button("->");
        
        VBox vbox = new VBox(20, rightButton, leftButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMinWidth(40);
        vbox.setEffect(dropShadow);
        
        HBox hbox = new HBox(20, leftListView, vbox, rightListView);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20));
        hbox.setStyle("-fx-background-color: palegreen");

        Scene scene = new Scene(hbox, 400, 300);
        stage.setTitle("ListView Control");
        
        rightButton.setOnAction(event -> {
            ObservableList<String> selections = 
                    FXCollections.observableArrayList(
                        leftListView.getSelectionModel().getSelectedItems());
            leftList.removeAll(selections);
            rightList.addAll(selections);
        });
        
        leftButton.setOnAction(event -> {
            ObservableList<String> selections = 
                    FXCollections.observableArrayList(
                        rightListView.getSelectionModel().getSelectedItems());
            rightList.removeAll(selections);
            leftList.addAll(selections);
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
