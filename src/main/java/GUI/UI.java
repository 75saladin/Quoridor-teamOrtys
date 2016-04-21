
import static java.awt.SystemColor.text;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.event.HyperlinkEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
/**
 *
 * @author jed_lechner
 
public class UI extends Application {
    
    private VBox login;
    private TextArea input;
    private Boolean yes = false;
    private Boolean no = false;
    private String nameInput;
    
    public UI() {
        setUp();
    }
    
    public void start(Stage stage) {
        UI ui = new UI();
        Scene scene = new Scene(login, 1000, 1000, Color.BLUE);
        stage.setTitle("Login");
        
        stage.setScene(scene);
        stage.show();
    }
    
    private void setUp() {
        Text text = new Text("Would you like to play against the AI "
                + "or watch them battle?");
        final CheckBox cb1 = new CheckBox("YES");
        final CheckBox cb2 = new CheckBox("NO");
        
        cb1.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, 
                Boolean old_val, Boolean new_val) -> {
            yes = cb1.isSelected();
        });
        
        cb2.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, 
                Boolean old_val, Boolean new_val) -> {
            no = cb2.isSelected();
        });
        
        login = new VBox(text, cb1, cb2);

        if(yes) {
            setUpUserInterface();
        } else if (no) {
            launchAI();
        }
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    private void setUpUserInterface() {
        Label name = new Label("Name: ");
        input = new TextArea();
        HBox hb = new HBox(name, input);
        input.addEventFilter(EventType.ROOT, letterValidation(3));
        
        
    }
    
    private EventHandler<KeyEvent> letterValidation(final int length) {
            
            return new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                    
                }
            };
    }

    private void launchAI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}*/
