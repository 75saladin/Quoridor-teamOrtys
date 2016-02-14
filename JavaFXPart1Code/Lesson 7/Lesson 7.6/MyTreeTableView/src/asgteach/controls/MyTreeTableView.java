// MyTreeTableView.java - TreeTableView control
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyTreeTableView extends Application {
    
    private final TreeTableView<SalesData> treeTableView = new TreeTableView<>();

    private void initData() {
        TreeItem<SalesData> root = new TreeItem<>(new SalesData("All Companies", 500.0));
        root.setExpanded(true);
        
        root.getChildren().add(new TreeItem<>(new SalesData("Nokia", 77.3)));
        root.getChildren().add(new TreeItem<>(new SalesData("RIM", 51.1)));
        root.getChildren().add(new TreeItem<>(new SalesData("Apple", 93.2)));
        root.getChildren().add(new TreeItem<>(new SalesData("HTC", 43.5)));
        root.getChildren().add(new TreeItem<>(new SalesData("SamSung", 94.0)));
        root.getChildren().add(new TreeItem<>(new SalesData("Others", 132.3)));
        treeTableView.setRoot(root);
    }
    @Override
    public void start(Stage stage) {
        
        initData();
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        Label label = new Label("SmartPhone Sales");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        
        VBox vbox = new VBox(label); 
        vbox.setAlignment(Pos.CENTER);
        
        TreeTableColumn<SalesData, String> companyColumn = 
                new TreeTableColumn<>("Company");
        companyColumn.setMinWidth(75);
        companyColumn.setCellValueFactory(cell ->
                cell.getValue().getValue().companyProperty());

        TreeTableColumn<SalesData, Double> unitsColumn = 
                new TreeTableColumn<>("Units Sold");
        unitsColumn.setMinWidth(50);
        unitsColumn.setCellValueFactory(cell ->
                cell.getValue().getValue().unitsProperty().asObject());
        
        treeTableView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        treeTableView.setPrefHeight(200);
        treeTableView.getColumns().add(companyColumn);
        treeTableView.getColumns().add(unitsColumn);
        treeTableView.setEffect(dropShadow);
        
        final TextField textField = new TextField();
        textField.setPrefWidth(100);
        textField.setEffect(dropShadow);
        
        textField.setOnAction(event -> {
            try {
                Double units = Double.parseDouble(textField.getText());
                TreeItem<SalesData> node = 
                            treeTableView.getSelectionModel().getSelectedItem();
                if (node != null && units > 0.0) {
                    SalesData salesData = node.getValue();
                    salesData.setUnits(units);
                }
            } catch (NumberFormatException e) { }  
        });
        
        Button removeButton = new Button("Remove Company");
        removeButton.setEffect(dropShadow);
        removeButton.setOnAction(event -> {
            TreeItem<SalesData> node = 
                    treeTableView.getSelectionModel().getSelectedItem();
            if (node.getParent() != null)
                node.getParent().getChildren().remove(node);
        });
        
        Button resetButton = new Button("Reset Table");
        resetButton.setEffect(dropShadow);
        resetButton.setOnAction(event -> initData());
        
        Label changeLabel = new Label("Change Units Sold");
        changeLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        
        HBox hbox = new HBox(20, changeLabel, textField);
        hbox.setAlignment(Pos.CENTER_LEFT);
        
        VBox mainVbox = new VBox(20, vbox, treeTableView, hbox, removeButton, resetButton);
        mainVbox.setAlignment(Pos.CENTER_LEFT);
        mainVbox.setPadding(new Insets(20, 30, 30, 30));
        mainVbox.setStyle("-fx-background-color: palegreen");

        Scene scene = new Scene(mainVbox, 400, 450);
        stage.setTitle("TreeTableView Control"); 
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
