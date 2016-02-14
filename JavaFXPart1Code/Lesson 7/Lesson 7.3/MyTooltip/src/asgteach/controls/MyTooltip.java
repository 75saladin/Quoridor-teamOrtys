// MyTooltip.java - Tooltip control
package asgteach.controls;
import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyTooltip extends Application {
    
    private final DropShadow dropShadow = new DropShadow();
    
    @Override
    public void start(Stage stage) {
                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        Label name = new Label("Name");
        name.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        
        Tooltip textTooltip = 
                new Tooltip("We'd like your name before placing order");
        textTooltip.setGraphic(new ImageView(new Image("resources/ok.png")));
        textTooltip.setPrefWidth(200);
        textTooltip.setWrapText(true);

        TextField textField = new TextField();
        textField.setPrefWidth(200);
        textField.setPromptText("Input your name");
        textField.setTooltip(textTooltip);

        HBox hbox = new HBox(20, name, textField);

        CheckBox shoes = makeCheckBox("Shoes", 30);
        CheckBox hat = makeCheckBox("Hat", 20);
        CheckBox belt = makeCheckBox("Belt", 10);

        Label total = new Label();
        total.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

        VBox vbox = new VBox(20, hbox, shoes, hat, belt, total);
        vbox.setAlignment(Pos.CENTER_LEFT);
        vbox.setPadding(new Insets(20, 20, 20, 80));
        vbox.setStyle("-fx-background-color: palegreen");

        Scene scene = new Scene(vbox, 400, 220);
        stage.setTitle("Tooltip Control");

        StringBinding choiceBinding = new StringBinding() {
            CheckBox[] checkBoxes = { shoes, hat, belt };
            {
                for (CheckBox checkBox : checkBoxes)
                    super.bind(checkBox.selectedProperty());
            }

            @Override
            protected String computeValue() {
                double total = 0;
                for (CheckBox checkBox : checkBoxes)
                    if (checkBox.isSelected())
                        total += (Double)checkBox.getUserData();
                return "Total: $ " + total;
            }
        };
        
        total.textProperty().bind(choiceBinding);
        
        textField.setOnAction(event -> {
            String name1 = textField.getText();
            System.out.println(name1);
        });

        stage.setScene(scene);
        stage.show();
    }
    
    private CheckBox makeCheckBox(String item, double cost) {
        CheckBox checkBox = new CheckBox(item);
        checkBox.setUserData(cost);
        checkBox.setTooltip(new Tooltip("$" + cost));
        checkBox.setEffect(dropShadow);
        return checkBox;
    }

    public static void main(String[] args) {
        launch();
    }
}
