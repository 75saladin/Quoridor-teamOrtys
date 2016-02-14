// MyPasswordFieldController.java - Controller class for MyPasswordField.fxml
package asgteach.controls;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MyPasswordFieldController implements Initializable {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    @FXML
    private void loginAction(ActionEvent event) {
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
        if (!name.isEmpty()) {
            message.append(" for ").append(name);
        }
        messageLabel.setText(message.toString());
        passwordField.clear();
    }

    @FXML
    private void clearAction(ActionEvent event) {
        loginField.clear();
        passwordField.clear();
        messageLabel.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Dynamic initialization of controls (if any)
    }
}
