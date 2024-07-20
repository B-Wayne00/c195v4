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

//Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID


public class AppointmentsDB {
    public static ObservableList<Appointment> getAllAppointments() throws SQLException{
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy,
                    customerID, userID, contactID);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }

    public static void addAppointment(Integer addAppointmentId, String addAppointmentTitle, String addAppointmentDescription,
                               String addAppointmentLocation, String addAppointmentType, LocalDateTime addStart,
                               LocalDateTime addEnd, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, Integer addCustomerID, Integer addUserID, Integer addContactID) throws SQLException {

        try{
            String addAppointmentSQL = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psAddAppointment = JDBC.getConnection().prepareStatement(addAppointmentSQL);

            psAddAppointment.setInt(1, addAppointmentId);
            psAddAppointment.setString(2, addAppointmentTitle);
            psAddAppointment.setString(3, addAppointmentDescription);
            psAddAppointment.setString(4, addAppointmentLocation);
            psAddAppointment.setString(5, addAppointmentType);
            psAddAppointment.setTimestamp(6, Timestamp.valueOf(addStart));
            psAddAppointment.setTimestamp(7, Timestamp.valueOf(addEnd));
            psAddAppointment.setTimestamp(8, Timestamp.valueOf(createDate));
            psAddAppointment.setString(9, createdBy);
            psAddAppointment.setTimestamp(10, Timestamp.valueOf(lastUpdate));
            psAddAppointment.setString(11, lastUpdatedBy);
            psAddAppointment.setInt(12, addCustomerID);
            psAddAppointment.setInt(13, addUserID);
            psAddAppointment.setInt(14, addContactID);

            psAddAppointment.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static int getUniqueAppointID() throws SQLException {
        int uniqueAppID = 0;
        String sql = "SELECT MAX(Appointment_ID) AS max_value FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            uniqueAppID = rs.getInt("max_value");
        }
        uniqueAppID++;
        return uniqueAppID;
    }


    public static void modifyAppointment(Integer addAppointmentId, String addAppointmentTitle, String addAppointmentDescription,
                                      String addAppointmentLocation, String addAppointmentType, LocalDateTime addStart,
                                      LocalDateTime addEnd, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, Integer addCustomerID, Integer addUserID, Integer addContactID) throws SQLException {

        try{
            String modifyAppointmentSQL = "UPDATE appointments set Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Created_By=?, " +
                    "Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?" ;
            PreparedStatement psAddAppointment = JDBC.getConnection().prepareStatement(modifyAppointmentSQL);


            psAddAppointment.setString(1, addAppointmentTitle);
            psAddAppointment.setString(2, addAppointmentDescription);
            psAddAppointment.setString(3, addAppointmentLocation);
            psAddAppointment.setString(4, addAppointmentType);
            psAddAppointment.setTimestamp(5, Timestamp.valueOf(addStart));
            psAddAppointment.setTimestamp(6, Timestamp.valueOf(addEnd));
            psAddAppointment.setString(7, createdBy);
            psAddAppointment.setTimestamp(8, Timestamp.valueOf(lastUpdate));
            psAddAppointment.setString(9, lastUpdatedBy);
            psAddAppointment.setInt(10, addCustomerID);
            psAddAppointment.setInt(11, addUserID);
            psAddAppointment.setInt(12, addContactID);
            psAddAppointment.setInt(13, addAppointmentId);

            psAddAppointment.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static int appointmentsByCustomer(Integer custID) throws SQLException {
        String sql = "SELECT COUNT(Appointment_ID) AS appCount FROM appointments WHERE Customer_ID =" + custID + "";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int appointmentCount = 0;
        if (rs.next()) {
            appointmentCount = rs.getInt("appCount");
        }
        return appointmentCount;
    }


    public static void deleteAppointment(int selectedAppID) throws SQLException {
        try{
            String deleteAppointmentSQL = "DELETE from appointments WHERE Appointment_ID = ?";
            PreparedStatement psDeleteAppointment = JDBC.getConnection().prepareStatement(deleteAppointmentSQL);

            psDeleteAppointment.setInt(1, selectedAppID);

            psDeleteAppointment.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Appointment> getSingleAppointment(int selectedAppID) throws SQLException {

            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            String getAppointmentSQL = "SELECT * from appointments WHERE Appointment_ID = ?";
            PreparedStatement psGetAppointment = JDBC.getConnection().prepareStatement(getAppointmentSQL);

            psGetAppointment.setInt(1, selectedAppID);

            psGetAppointment.execute();
            ResultSet rs = psGetAppointment.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy,
                        customerID, userID, contactID);
                appointmentList.add(appointment);
            }
            return appointmentList;

    }
}


