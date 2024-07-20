package Controller;

import DAO.*;
import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;

import java.io.IOException;
import java.sql.SQLException;

public class ReportsController {

    @FXML
    private Button LogOutButton;

    @FXML
    private ComboBox<String> ReportsAppByContactCombo;

    @FXML
    private TableView<Appointment> ReportsAppByContactTable;

    @FXML
    private TableView<?> ReportsAppByCountryTable;

    @FXML
    private RadioButton ReportsAppByMonthRadioButton;

    @FXML
    private TableView<?> ReportsAppByMonthTypeTable;

    @FXML
    private RadioButton ReportsAppByTypeRadioButton;

    @FXML
    private TableColumn<?, ?> ReportsAppContactCol;

    @FXML
    private TableColumn<?, ?> ReportsAppCustIDCol;

    @FXML
    private TableColumn<?, ?> ReportsAppDescCol;

    @FXML
    private TableColumn<?, ?> ReportsAppEndCol;

    @FXML
    private TableColumn<?, ?> ReportsAppIDCol;

    @FXML
    private TableColumn<?, ?> ReportsAppLocationCol;

    @FXML
    private TableColumn<?, ?> ReportsAppStartCol;

    @FXML
    private TableColumn<?, ?> ReportsAppTitleCol;

    @FXML
    private TableColumn<?, ?> ReportsAppTypeCol;

    @FXML
    private TableColumn<?, ?> ReportsAppUserIDCol;

    @FXML
    private TableColumn<?, ?> ReportsByCountryCol;

    @FXML
    private TableColumn<?, ?> ReportsCustByMonthCol;

    @FXML
    private TableColumn<?, ?> ReportsMonthCol;

    @FXML
    private TableColumn<?, ?> ReportsTotalAppByMonthCol;

    @FXML
    private TableColumn<?, ?> ReportsTotalByCountryCol;

    @FXML
    void toMainScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void onContactSelection() throws Exception {
        String contactSelected = ReportsAppByContactCombo.getSelectionModel().getSelectedItem();
        int contactFromSelected = ContactDB.getIdFromContact(contactSelected);
        ObservableList<Appointment> appointmentsByContact = ReportsDB.getAppointmentByContact(contactFromSelected);
       // ObservableList<Appointment> allAppointments = AppointmentsDB.getAllAppointments();
       // ObservableList<Contact> contactList = ContactDB.getAllContacts();
       // ObservableList<String> contactNames = FXCollections.observableArrayList();
       // contactList.forEach(Contact -> contactNames.add(Contact.getContactName()));
        ReportsAppIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        ReportsAppTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        ReportsAppDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        ReportsAppLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        ReportsAppTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        ReportsAppStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        ReportsAppEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        ReportsAppCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        ReportsAppUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        ReportsAppContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        ReportsAppByContactTable.setItems(appointmentsByContact);

    }

    public void initialize() throws SQLException {
       // ObservableList<Appointment> allAppointments = AppointmentsDB.getAllAppointments();
        ObservableList<Contact> contactList = ContactDB.getAllContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        contactList.forEach(Contact -> contactNames.add(Contact.getContactName()));
      /*  ReportsAppIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        ReportsAppTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        ReportsAppDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        ReportsAppLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        ReportsAppTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        ReportsAppStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        ReportsAppEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        ReportsAppCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        ReportsAppUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        ReportsAppContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        ReportsAppByContactTable.setItems(allAppointments); */
        ReportsAppByContactCombo.setItems(contactNames);
    }

}
