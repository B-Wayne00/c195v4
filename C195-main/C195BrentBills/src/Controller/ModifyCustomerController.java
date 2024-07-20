package Controller;

import DAO.CountryDB;
import DAO.CustomerDB;
import DAO.FirstLevelDivisionDB;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ModifyCustomerController {

    @FXML
    private Button AddCustCancelButton;

    @FXML
    private Button AddCustSaveButton;

    @FXML
    private TextField ModCustAddressField;

    @FXML
    private Label ModCustAddressLabel;

    @FXML
    private ComboBox<String> ModCustDivCombo;

    @FXML
    private Label ModCustDivLabel;

    @FXML
    private TextField ModCustIdField;

    @FXML
    private Label ModCustIdLabel;

    @FXML
    private TextField ModCustNameField;

    @FXML
    private Label ModCustNameLabel;

    @FXML
    private TextField ModCustPhoneField;

    @FXML
    private Label ModCustPhoneLabel;

    @FXML
    private TextField ModCustZipField;

    @FXML
    private Label ModCustZipLabel;

    @FXML
    private ComboBox<String> ModCustCountryCombo;

    @FXML
    private Label ModCustCountryLabel;

    Customer customerToModify;


    public void sendCustomer(Customer selectedCustomer) throws SQLException {
        customerToModify = selectedCustomer;
        ModCustIdField.setText(Integer.toString(customerToModify.getCustomerID()));
        ModCustNameField.setText(customerToModify.getCustomerName());
        ModCustAddressField.setText(customerToModify.getAddress());
        ModCustZipField.setText(customerToModify.getPostalCode());
        ModCustPhoneField.setText(customerToModify.getPhone());
        String divName = FirstLevelDivisionDB.getDivNameFromId(customerToModify.getDivisionID());
        String countryName = CountryDB.getCountryFromDivID(customerToModify.getDivisionID());
        ModCustCountryCombo.setValue(countryName);
        ModCustDivCombo.setValue(divName);

    }

    @FXML
    void onCancelAddCustomer(ActionEvent event) {

    }

    @FXML
    void onSaveAddCustomer(ActionEvent event) {

    }

    @FXML
    public void onCountrySelection() throws SQLException {
        String countrySelected = ModCustCountryCombo.getSelectionModel().getSelectedItem();
        int countrySelectedID = CountryDB.getCountryIdFromSelection(countrySelected);
        ObservableList<FirstLevelDivision> divisionsById = FirstLevelDivisionDB.getDivisionByCountry(countrySelectedID);
        ObservableList<String> custDivisionNames = FXCollections.observableArrayList();
        divisionsById.forEach(FirstLevelDivision -> custDivisionNames.add(FirstLevelDivision.getDivisionName()));
        ModCustDivCombo.setItems(custDivisionNames);

    }

    @FXML
    void toCustomerView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/Customers.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1300, 700);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();

    }


    public void initialize() throws SQLException {
        try {
            ObservableList<FirstLevelDivision> custDivisions = FirstLevelDivisionDB.getAllDivisions();
            ObservableList<Integer> custDivisionIds = FXCollections.observableArrayList();
            ObservableList<Country> custCountries = CountryDB.getAllCountries();
            ObservableList<String> custCountryNames = FXCollections.observableArrayList();


            custDivisions.forEach(FirstLevelDivision -> custDivisionIds.add(FirstLevelDivision.getDivisionID()));
            custCountries.forEach(Country -> custCountryNames.add(Country.getCountryName()));

            //ModCustDivCombo.setItems(custDivisionIds);
            ModCustCountryCombo.setItems(custCountryNames);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}