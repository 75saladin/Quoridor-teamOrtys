// MyPasswordField.java - PasswordField control
package asgteach.controls;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyPasswordField extends Application {

    @Override
    public void start(Stage stage) {
        
        DropShadow dropShadow = new DropShadow();                
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        dropShadow.setColor(Color.rgb(150, 50, 50, .688));
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: palegreen");
        
        Label loginLabel = new Label("Name");
        loginLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        gridPane.add(loginLabel, 0, 0);
        
        TextField loginField = new TextField();
        loginField.setMaxWidth(200);
        loginField.setEffect(dropShadow);
        gridPane.add(loginField, 1, 0);
        
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        gridPane.add(passwordLabel, 0, 1);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(200);
        passwordField.setEffect(dropShadow);
        gridPane.add(passwordField, 1, 1);
        
        Button login = new Button("Login");
        Button clear = new Button("Clear");
        HBox buttons = new HBox(45, login, clear);
        buttons.setPadding(new Insets(0, 10, 0, 0));
        buttons.setEffect(dropShadow);
        gridPane.add(buttons, 1, 2, 2, 1);
        
        Label messageLabel = new Label();
        messageLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
        messageLabel.setPrefWidth(200);
        gridPane.add(messageLabel, 1, 3, 2, 1);

        Scene scene = new Scene(gridPane, 400, 230);
        stage.setTitle("PasswordField Control"); 
        
        login.setOnAction(event -> {
            String name = loginField.getText();
            String password = passwordField.getText();
            StringBuilder message = new StringBuilder();
            if (password.equals("District9")) {
                message.append("access granted");
                messageLabel.setTextFill(Color.GREEN);
            } else {
                message.append("bad password");
                messageLabel.setTextFill(Color.RED);
            }
            if (!name.isEmpty())
                message.append(" for ").append(name);
            messageLabel.setText(message.toString());
            passwordField.clear();
        });
        
        clear.setOnAction(event -> {
            loginField.clear();
            passwordField.clear();
            messageLabel.setText("");
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
