package Controller;

import DAO.AppointmentsDB;
import DAO.ContactDB;
import DAO.CustomerDB;
import DAO.UserDB;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointmentController {

    @FXML
    private Label AddAppContactLabel;

    @FXML
    private ComboBox<Integer> AddAppContactCombo;

    @FXML
    private TextField AddAppCustIDField;

    @FXML
    private Label AddAppCustIDLabel;

    @FXML
    private TextField AddAppDescField;

    @FXML
    private Label AddAppDescLabel;

    @FXML
    private Label AddAppEDLabel;

    @FXML
    private DatePicker AddAppEDMenu;

    @FXML
    private Label AddAppETLabel;

    @FXML
    private MenuButton AddAppETMenu;

    @FXML
    private TextField AddAppIDField;

    @FXML
    private Label AddAppIDLabel;

    @FXML
    private TextField AddAppLocField;

    @FXML
    private Label AddAppLocLabel;

    @FXML
    private Label AddAppSDLabel;

    @FXML
    private DatePicker AddAppSDMenu;

    @FXML
    private Label AddAppSTLabel;

    @FXML
    private ComboBox<LocalTime> AddAppStartCombo;

    @FXML
    private ComboBox<LocalTime> AddAppEndCombo;

    @FXML
    private TextField AddAppTitleField;

    @FXML
    private Label AddAppTitleLabel;

    @FXML
    private TextField AddAppTypeField;

    @FXML
    private Label AddAppTypeLabel;

    @FXML
    private TextField AddAppUserIDField;

    @FXML
    private Label AddAppUserIDLabel;

    @FXML
    private Button AddAppointmentCancelButton;

    @FXML
    private ComboBox<Integer> customerIDCombo;

    @FXML
    private Button AddAppointmentSaveButton;

    @FXML
    private ComboBox<Integer> userIDCombo;

    //LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy
    @FXML
    void onSaveAddAppointment(ActionEvent event) throws SQLException {

                Integer addAppointmentID = AppointmentsDB.getUniqueAppointID();
                String addAppointmentTitle = AddAppTitleField.getText();
                String addAppointmentDescription = AddAppDescField.getText();
                String addAppointmentLocation = AddAppLocField.getText();
                String addAppointmentType = AddAppTypeField.getText();
                LocalDateTime addStart = LocalDateTime.of(AddAppSDMenu.getValue(), AddAppStartCombo.getSelectionModel().getSelectedItem());
                LocalDateTime addEnd = LocalDateTime.of(AddAppEDMenu.getValue(), AddAppEndCombo.getSelectionModel().getSelectedItem());
                LocalDateTime createDate = LocalDateTime.now();
                String createdBy = "Admin";
                LocalDateTime lastUpdate = LocalDateTime.now();
                String lastUpdatedBy = "Admin";
                Integer addCustomerID = customerIDCombo.getSelectionModel().getSelectedItem();
                Integer addUserID = userIDCombo.getSelectionModel().getSelectedItem();
                Integer addContact = AddAppContactCombo.getSelectionModel().getSelectedItem();

                if(addAppointmentTitle.isEmpty() || addAppointmentDescription.isEmpty() || addAppointmentLocation.isEmpty() ||
                        addAppointmentType.isEmpty() || addCustomerID == null || addUserID == null || addContact == null ) {
                    Error.errorMessage("Please fill out all fields");
                }else {

                    try {
                        AppointmentsDB.addAppointment(addAppointmentID, addAppointmentTitle, addAppointmentDescription, addAppointmentLocation,
                                addAppointmentType, addStart, addEnd, createDate, createdBy, lastUpdate, lastUpdatedBy, addCustomerID, addUserID, addContact);

                        toMainScreen(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
    }

 /*   @FXML
    void toMainScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();

    } */

    @FXML
    void toMainScreen(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void initialize () throws SQLException {
        try{
            ObservableList<Customer> customerList = CustomerDB.getAllCustomers();
            ObservableList<Integer> customerIDs = FXCollections.observableArrayList();
            ObservableList<Contact> contactList = ContactDB.getAllContacts();
            ObservableList<Integer> contactIDs = FXCollections.observableArrayList();
            ObservableList<User> userList = UserDB.getAllUsers();
            ObservableList<Integer> userIDs = FXCollections.observableArrayList();

            customerList.forEach(Customer -> customerIDs.add(Customer.getCustomerID()));
            contactList.forEach(Contact -> contactIDs.add(Contact.getContactID()));
            userList.forEach(User -> userIDs.add(User.getUserID()));


            LocalTime startStart = LocalTime.of(5,0);
            LocalTime startEnd = LocalTime.of(21,45);
            LocalTime endStart = LocalTime.of(5,15);
            LocalTime endEnd = LocalTime.of(22,0);

            while(startStart.isBefore(startEnd.plusSeconds(1))){
                AddAppStartCombo.getItems().add(startStart);
                startStart = startStart.plusMinutes(15);

                while(endStart.isBefore(endEnd.plusSeconds(1))){
                    AddAppEndCombo.getItems().add(endStart);
                    endStart = endStart.plusMinutes(15);
                }
            }

            customerIDCombo.setItems(customerIDs);
            AddAppContactCombo.setItems(contactIDs);
            userIDCombo.setItems(userIDs);





        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
