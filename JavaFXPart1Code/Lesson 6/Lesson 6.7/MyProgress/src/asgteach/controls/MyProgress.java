// MyProgress.java - ProgressBar, ProgressIndicator controls
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyProgress extends Application {

    @Override
    public void start(Stage stage) {
        
        final int sliderLength = 50;

        DropShadow dropShadow = new DropShadow();                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        Label label = new Label("Show Some Progress");
        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        
        Slider slider = new Slider(0, sliderLength, 0);
        ProgressBar bar = new ProgressBar(0);
        ProgressIndicator indicator = new ProgressIndicator(0);
        
        HBox hbox = new HBox(40, slider, bar, indicator);
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.setEffect(dropShadow);
        
        VBox vbox = new VBox(40, label, hbox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: palegreen");

        Scene scene = new Scene(vbox, 400, 200);
        stage.setTitle("ProgressBar, ProgressIndicator Controls"); 
        
        bar.progressProperty().bind(slider.valueProperty()
                .divide(sliderLength));
        indicator.progressProperty().bind(slider.valueProperty()
                .divide(sliderLength));
        //indicator.progressProperty().bind(bar.progressProperty());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
