package DAO;

import Helper.JDBC;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDB {

    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            contactList.add(contact);
        }
        return contactList;
    }

    public static int getIdFromContact(String contactName) throws SQLException {
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name ='" + contactName + "'";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int contactId = -1;
        if (rs.next()) {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }

}
