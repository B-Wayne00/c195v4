package Controller;

import DAO.AppointmentsDB;
import DAO.CustomerDB;
import Helper.JDBC;
import Model.Appointment;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CustomerController {

    @FXML
    private TableColumn<Customer, String> CustAddressCol;

    @FXML
    private TableColumn<Customer, String> CustCreatedByCol;

    @FXML
    private TableColumn<Customer, LocalDateTime> CustCreatedCol;

    @FXML
    private TableColumn<Customer, Integer> CustDivisionIDCol;

    @FXML
    private TableColumn<Customer, Integer> CustIDCol;

    @FXML
    private TableColumn<Customer, String> CustNameCol;

    @FXML
    private TableColumn<Customer, String> CustPhoneCol;

    @FXML
    private TableColumn<Customer, String> CustPostalCol;

    @FXML
    private Button CustToMainScreenButton;

    @FXML
    private TableColumn<Customer, String> CustUpdatedByCol;

    @FXML
    private TableColumn<Customer, LocalDateTime> CustUpdatedCol;

    @FXML
    private TableView<Customer> CustomerTable;

    @FXML
    private Button DeleteCustomerButton;

    @FXML
    private Button ToAddCustomerButton;

    @FXML
    private Button ToModifyCustomerButton;

    @FXML
    void deleteCustomer(ActionEvent event) throws SQLException {
        int selectedCustID = CustomerTable.getSelectionModel().getSelectedItem().getCustomerID();

        if(AppointmentsDB.appointmentsByCustomer(selectedCustID) == 0) {
            CustomerDB.deleteCustomer(selectedCustID);
            CustomerTable.setItems(CustomerDB.getAllCustomers());
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Error");
            alert.setContentText("Must delete customers appointments before deleting customer");
            alert.showAndWait();
        }
    }

    @FXML
    void toAddCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AddCustomer.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 375, 550);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void toModifyCustomer(ActionEvent event) throws IOException, SQLException {
        Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Error");
            alert.setContentText("Please select an appointment");
            alert.showAndWait();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ModifyCustomer.fxml"));
            Parent root = loader.load();
            ModifyCustomerController custController = loader.getController();
            Stage stage = (Stage) ((Button)
                    event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 575);
            stage.setTitle("Add Customer");
            stage.setScene(scene);
            stage.show();
            custController.sendCustomer(selectedCustomer);
        }

    }


    @FXML
    void toMainScreen(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 700);
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();

    }


    //Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID
    public void initialize() throws SQLException {
        ObservableList<Customer> allCustomers = CustomerDB.getAllCustomers();

        CustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        CustNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CustAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        CustPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        CustPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        CustCreatedCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        CustCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        CustUpdatedCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        CustUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        CustDivisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));


        CustomerTable.setItems(allCustomers);
    }

}