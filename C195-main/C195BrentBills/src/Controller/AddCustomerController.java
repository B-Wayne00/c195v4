package Controller;

import DAO.*;
import Model.*;
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
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddCustomerController {

    @FXML
    private Label AddAppContactLabel;

    @FXML
    private Label AddAppDescLabel;

    @FXML
    private TextField AddAppIDField;

    @FXML
    private Label AddAppIDLabel;

    @FXML
    private Label AddAppLocLabel;

    @FXML
    private Label AddAppTitleLabel;

    @FXML
    private Label AddAppTypeLabel;

    @FXML
    private TextField AddCustAddressField;

    @FXML
    private Button AddCustCancelButton;

    @FXML
    private TextField AddCustNameField;

    @FXML
    private Button AddCustSaveButton;

    @FXML
    private ComboBox<String> AddDivisionIDCombo;

    @FXML
    private TextField AddPhoneField;

    @FXML
    private TextField AddPostalCodeField;

    @FXML
    private ComboBox<String> AddCountryCombo;

    @FXML
    private Label AddCountryLabel;

    void toCustomerView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/Customers.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1300, 700);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void onSaveAddCustomer(ActionEvent event) throws SQLException {
        Integer addCustID = CustomerDB.getUniqueCustID();
        String addCustName = AddCustNameField.getText();
        String addCustAddress = AddCustAddressField.getText();
        String addCustPostal = AddPostalCodeField.getText();
        String addCustPhone = AddPhoneField.getText();
        LocalDateTime custCreateDate = LocalDateTime.now();
        String custCreatedBy = "Admin";
        LocalDateTime custLastUpdate = LocalDateTime.now();
        String custLastUpdatedBy = "Admin";
        String addCustDivision = AddDivisionIDCombo.getSelectionModel().getSelectedItem();
        Integer addCustDivisionID = FirstLevelDivisionDB.getDivisionIdDB(addCustDivision);


        try {
            CustomerDB.addCustomer(addCustID, addCustName, addCustAddress, addCustPostal, addCustPhone, custCreateDate, custCreatedBy, custLastUpdate, custLastUpdatedBy, addCustDivisionID);

            toCustomerView(event);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onCountrySelection() throws SQLException {
        String countrySelected = AddCountryCombo.getSelectionModel().getSelectedItem();
        int countrySelectedID = CountryDB.getCountryIdFromSelection(countrySelected);
        ObservableList<FirstLevelDivision> divisionsById = FirstLevelDivisionDB.getDivisionByCountry(countrySelectedID);
        ObservableList<String> custDivisionNames = FXCollections.observableArrayList();
        divisionsById.forEach(FirstLevelDivision -> custDivisionNames.add(FirstLevelDivision.getDivisionName()));
        AddDivisionIDCombo.setItems(custDivisionNames);

    }

    @FXML
    void onCancelAddCustomer(ActionEvent event) throws IOException {
        toCustomerView(event);
    }


    public void initialize () throws SQLException {
        try {
           // ObservableList<FirstLevelDivision> custDivisions = FirstLevelDivisionDB.getAllDivisions();
           // ObservableList<Integer> custDivisionIds = FXCollections.observableArrayList();
            ObservableList<Country> custCountries = CountryDB.getAllCountries();
            ObservableList<String> custCountryNames = FXCollections.observableArrayList();
           // ObservableList<FirstLevelDivision> custDivNames = FirstLevelDivisionDB.getAllDivisions();
           // ObservableList<String> custDivisionNames = FXCollections.observableArrayList();

           // custDivisions.forEach(FirstLevelDivision -> custDivisionIds.add(FirstLevelDivision.getDivisionID()));
            custCountries.forEach(Country -> custCountryNames.add(Country.getCountryName()));
          //  custDivNames.forEach(FirstLevelDivision -> custDivisionNames.add(FirstLevelDivision.getDivisionName()));


            AddCountryCombo.setItems(custCountryNames);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}