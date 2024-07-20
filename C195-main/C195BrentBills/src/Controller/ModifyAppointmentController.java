package Controller;

import DAO.AppointmentsDB;
import DAO.ContactDB;
import DAO.CustomerDB;
import DAO.UserDB;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ModifyAppointmentController {

    @FXML
    private Label ModAppContactLabel;

    @FXML
    private ComboBox<Integer> ModAppContactCombo;

    @FXML
    private TextField ModAppCustIDField;

    @FXML
    private Label ModAppCustIDLabel;

    @FXML
    private TextField ModAppDescField;

    @FXML
    private Label ModAppDescLabel;

    @FXML
    private Label ModAppEDLabel;

    @FXML
    private DatePicker ModAppEDMenu;

    @FXML
    private Label ModAppETLabel;

    @FXML
    private ComboBox<LocalTime> ModAppETMenu;

    @FXML
    private TextField ModAppIDField;

    @FXML
    private Label ModAppIDLabel;

    @FXML
    private TextField ModAppLocField;

    @FXML
    private Label ModAppLocLabel;

    @FXML
    private Label ModAppSDLabel;

    @FXML
    private DatePicker ModAppSDMenu;

    @FXML
    private Label ModAppSTLabel;

    @FXML
    private ComboBox<LocalTime> ModAppSTMenu;

    @FXML
    private TextField ModAppTitleField;

    @FXML
    private Label ModAppTitleLabel;

    @FXML
    private TextField ModAppTypeField;

    @FXML
    private Label ModAppTypeLabel;

    @FXML
    private TextField ModAppUserIDField;

    @FXML
    private Label ModAppUserIDLabel;

    @FXML
    private Button ModifyAppointmentCancelButton;

    @FXML
    private Button ModifyAppointmentSaveButton;

    @FXML
    private ComboBox<Integer> ModAppCustIdCombo;

    @FXML
    private ComboBox<Integer> ModAppUserIdCombo;

    Appointment appointmentToModify;

    @FXML
    void toMainScreen(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void saveModifyAppointment(ActionEvent event) {
        Integer addAppointmentID = appointmentToModify.getAppointmentID();
        String addAppointmentTitle = ModAppTitleField.getText();
        String addAppointmentDescription = ModAppDescField.getText();
        String addAppointmentLocation = ModAppLocField.getText();
        String addAppointmentType = ModAppTypeField.getText();
        LocalDateTime addStart = LocalDateTime.of(ModAppSDMenu.getValue(), ModAppSTMenu.getSelectionModel().getSelectedItem());
        LocalDateTime addEnd = LocalDateTime.of(ModAppEDMenu.getValue(), ModAppETMenu.getSelectionModel().getSelectedItem());
        String createdBy = "Admin";
        LocalDateTime lastUpdate = LocalDateTime.now();
        String lastUpdatedBy = "Admin";
        Integer addCustomerID = ModAppCustIdCombo.getSelectionModel().getSelectedItem();
        Integer addUserID = ModAppUserIdCombo.getSelectionModel().getSelectedItem();
        Integer addContact = ModAppContactCombo.getSelectionModel().getSelectedItem();

        if (addAppointmentTitle.isEmpty() || addAppointmentDescription.isEmpty() || addAppointmentLocation.isEmpty() ||
                addAppointmentType.isEmpty() || addCustomerID == null || addUserID == null || addContact == null) {
            Error.errorMessage("Please fill out all fields");
        } else {

            try {
                AppointmentsDB.modifyAppointment(addAppointmentID, addAppointmentTitle, addAppointmentDescription, addAppointmentLocation,
                        addAppointmentType, addStart, addEnd, createdBy, lastUpdate, lastUpdatedBy, addCustomerID, addUserID, addContact);

                toMainScreen(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendAppointment(Appointment selectedAppointment) {
        appointmentToModify = selectedAppointment;
        ModAppIDField.setText(Integer.toString(appointmentToModify.getAppointmentID()));
        ModAppTitleField.setText(appointmentToModify.getTitle());
        ModAppDescField.setText(appointmentToModify.getDescription());
        ModAppLocField.setText(appointmentToModify.getLocation());
        ModAppTypeField.setText(appointmentToModify.getType());
        ModAppContactCombo.setValue(appointmentToModify.getContactID());
        ModAppUserIdCombo.setValue(appointmentToModify.getUserID());
        ModAppContactCombo.setValue(appointmentToModify.getContactID());
        ModAppETMenu.setValue(appointmentToModify.getEnd().toLocalTime());
        ModAppEDMenu.setValue(appointmentToModify.getEnd().toLocalDate());
        ModAppSTMenu.setValue(appointmentToModify.getStart().toLocalTime());
        ModAppSDMenu.setValue(appointmentToModify.getStart().toLocalDate());
        ModAppCustIdCombo.setValue(appointmentToModify.getCustomerID());

    }

    public void cancelModifyAppointment(ActionEvent event) {
    }

    public void initialize() throws SQLException {
        try {
            ObservableList<Customer> customerList = CustomerDB.getAllCustomers();
            ObservableList<Integer> customerIDs = FXCollections.observableArrayList();
            ObservableList<Contact> contactList = ContactDB.getAllContacts();
            ObservableList<Integer> contactIDs = FXCollections.observableArrayList();
            ObservableList<User> userList = UserDB.getAllUsers();
            ObservableList<Integer> userIDs = FXCollections.observableArrayList();

            customerList.forEach(Customer -> customerIDs.add(Customer.getCustomerID()));
            contactList.forEach(Contact -> contactIDs.add(Contact.getContactID()));
            userList.forEach(User -> userIDs.add(User.getUserID()));


            LocalTime startStart = LocalTime.of(5, 0);
            LocalTime startEnd = LocalTime.of(21, 45);
            LocalTime endStart = LocalTime.of(5, 15);
            LocalTime endEnd = LocalTime.of(22, 0);

            while (startStart.isBefore(startEnd.plusSeconds(1))) {
                ModAppSTMenu.getItems().add(startStart);
                startStart = startStart.plusMinutes(15);

                while (endStart.isBefore(endEnd.plusSeconds(1))) {
                    ModAppETMenu.getItems().add(endStart);
                    endStart = endStart.plusMinutes(15);
                }
            }

            ModAppCustIdCombo.setItems(customerIDs);
            ModAppContactCombo.setItems(contactIDs);
            ModAppUserIdCombo.setItems(userIDs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
