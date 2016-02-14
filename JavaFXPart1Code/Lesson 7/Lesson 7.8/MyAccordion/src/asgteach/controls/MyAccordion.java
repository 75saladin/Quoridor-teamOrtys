// MyAccordion.java - Accordion control
package asgteach.controls;
import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyAccordion extends Application {

    @Override
    public void start(Stage stage) {
        
        DropShadow dropShadow = new DropShadow();                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));

        Label label = new Label("Please Choose an Icon");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

        Label selection = new Label();
        selection.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

        VBox vbox = new VBox(40, label, selection);
        vbox.setAlignment(Pos.CENTER);
        
        TitledPane pane1 = makeTitledPane("alert");
        TitledPane pane2 = makeTitledPane("warning");
        //pane2.setAnimated(false);
        TitledPane pane3 = makeTitledPane("error");
        TitledPane pane4 = makeTitledPane("warn");
        TitledPane pane5 = makeTitledPane("messagebox");

        Accordion accordion = new Accordion();
        accordion.getPanes().addAll(pane1, pane2, pane3, pane4, pane5);
        accordion.setEffect(dropShadow);

        HBox hbox = new HBox(40, vbox, accordion);
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: palegreen");

        Scene scene = new Scene(hbox, 400, 300);
        stage.setTitle("Accordion Control");
        
        StringBinding choiceBinding = new StringBinding() {
            {
                super.bind(accordion.expandedPaneProperty());
            }

            @Override
            protected String computeValue() {
                if (accordion.getExpandedPane() == null){
                    return "";
                }  
                String name = accordion.getExpandedPane().getText();
                return "You chose " + name;
            }
        };
        
        selection.textProperty().bind(choiceBinding);

        stage.setScene(scene);
        stage.show();
    }
    
    private TitledPane makeTitledPane(String iconName) {
        Image image = new Image("resources/" + iconName + ".png");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        return new TitledPane(iconName, imageView);
    }

    public static void main(String[] args) {
        launch();
    }
}
