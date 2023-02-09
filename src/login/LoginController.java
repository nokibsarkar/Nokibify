package src.login;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;
import src.Configuration;

public class LoginController {
    String instructMessage = "Please enter your username and password";
    String successMessage = "Login successful";
    String failMessage = "Login failed";
    private Callback<Void, Void> loginSuccessCallback = null;
    public void setupLoginSuccessCallback(Callback<Void, Void> callback){
        loginSuccessCallback = callback;
    }
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button resetButton;
    @FXML
    private Text messageText;
    @FXML
    void login(Event e){
        if(!Configuration.isAction(e)) return;
        login();
    }
    void login(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        messageText.setText("Logging in...");
        if(username.equals("admin") && password.equals("admin")){
            messageText.setText(successMessage);
        }else{
            messageText.setText(failMessage);
        }
        if(loginSuccessCallback != null)
            loginSuccessCallback.call(null);
    }
    @FXML
    void reset(Event e){
        if(!Configuration.isAction(e)) return;
        reset();
    }
    void reset(){
        usernameField.setText("");
        passwordField.setText("");
        messageText.setText(instructMessage);
    }
    @FXML
    void checkDisable(){
        boolean disabled = usernameField.getText().equals("") || passwordField.getText().equals("");
        loginButton.setDisable(disabled);
    }
}
