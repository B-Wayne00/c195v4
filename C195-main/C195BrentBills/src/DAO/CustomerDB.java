package DAO;

import Helper.JDBC;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

//Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID

public class CustomerDB {

    private static int uniqueCustID;
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            Integer divisionID = rs.getInt("Division_ID");
           // String divisionName = rs.getString("Division");
            Customer customer = new Customer(customerID, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
            customerList.add(customer);
        }
        return customerList;
    }

    public static void deleteCustomer(int selectedCustID) throws SQLException {
        try{
            String deleteCustomerSQL = "DELETE from customers WHERE Customer_ID = ?";
            PreparedStatement psDeleteCustomer = JDBC.getConnection().prepareStatement(deleteCustomerSQL);

            psDeleteCustomer.setInt(1, selectedCustID);

            psDeleteCustomer.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getUniqueCustID() throws SQLException {
        int uniqueCustID = 0;
        String sql = "SELECT MAX(Customer_ID) AS max_value FROM customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            uniqueCustID = rs.getInt("max_value");
        }
        uniqueCustID++;
        return uniqueCustID;
    }

    public static void addCustomer(Integer addCustID, String addCustName, String addCustAddress, String addCustPostal, String addCustPhone, LocalDateTime custCreateDate, String custCreatedBy, LocalDateTime custLastUpdate,
                                   String custLastUpdatedBy, Integer addCustDivision) throws SQLException {

        //addCustID, addCustName, addCustAddress, addCustPostal, addCustPhone, custCreateDate, custCreatedBy, custLastUpdate, custLastUpdatedBy, addCustDivision
        try{
            String addAppointmentSQL = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psAddCustomer = JDBC.getConnection().prepareStatement(addAppointmentSQL);

            psAddCustomer.setInt(1, addCustID);
            psAddCustomer.setString(2, addCustName);
            psAddCustomer.setString(3, addCustAddress);
            psAddCustomer.setString(4, addCustPostal);
            psAddCustomer.setString(5, addCustPhone);
            psAddCustomer.setTimestamp(6, Timestamp.valueOf(custCreateDate));
            psAddCustomer.setString(7, custCreatedBy);
            psAddCustomer.setTimestamp(8, Timestamp.valueOf(custLastUpdate));
            psAddCustomer.setString(9, custLastUpdatedBy);
            psAddCustomer.setInt(10, addCustDivision);


            psAddCustomer.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static int createCustID() {
       uniqueCustID++;
       return uniqueCustID;

    }
}
