package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Error {

    static void errorMessage(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Error");
        alert.setContentText(alertMessage);
        alert.showAndWait();

    }


    public void toMainForm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MainScreen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Button)
                event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 450);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();

    }
}
