package Controller;

import DAO.UserDB;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;


public class LogInController implements Initializable {

    @FXML
    private Button LogInButton;

    @FXML
    private Label LogInCountryLabel;

    @FXML
    private TextField LogInPasswordField;

    @FXML
    private Label LogInPasswordLabel;

    @FXML
    private Label LogInTitleLabel;

    @FXML
    private TextField LogInUsernameField;

    @FXML
    private Label LogInUsernameLabel;


    @FXML
    void userLogIn(ActionEvent event) throws SQLException {
        try {
            String logInUsername = LogInUsernameField.getText();
            String logInPassword = LogInPasswordField.getText();
            Boolean validLogin = UserDB.validatePassword(logInUsername, logInPassword);

            if(validLogin) {
                Parent root = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 700);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
            } else {
               Error.errorMessage("Username or Password is incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void initialize (URL url, ResourceBundle resourceBundle) {

        Locale locale = Locale.getDefault();

        ResourceBundle rb = ResourceBundle.getBundle("Resources/lang", locale);
        //LogInCountryLabel.setText(rb.getString("country"));
        LogInCountryLabel.setText(locale.getCountry());
        LogInTitleLabel.setText(rb.getString("title"));
        LogInUsernameLabel.setText(rb.getString("username"));
        LogInPasswordLabel.setText(rb.getString("password"));



    }

}
