// MyTreeView.java - TreeView control
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyTreeView extends Application {

    @Override
    public void start(Stage stage) {
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        TreeView<String> treeView = MyTree.buildTree();
        treeView.setEffect(dropShadow);
        
        TextField textField = new TextField();
        
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            TreeItem<String> node = treeView.getSelectionModel().getSelectedItem();
            String team = textField.getText();
            if (node != null && !team.isEmpty())
                node.getChildren().add(new TreeItem<>(team));
        });
        
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            TreeItem<String> node = treeView.getSelectionModel().getSelectedItem();
            if (node.getParent() != null)
                node.getParent().getChildren().remove(node);
        });
        
        HBox hbox = new HBox(20, addButton, textField, deleteButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setEffect(dropShadow);
        
        VBox vbox = new VBox(20, treeView, hbox);
        vbox.setPadding(new Insets(30));
        vbox.setStyle("-fx-background-color: palegreen");

        Scene scene = new Scene(vbox, 400, 400);
        stage.setTitle("TreeView Control"); 
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
