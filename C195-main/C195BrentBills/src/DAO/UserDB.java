package DAO;

import Helper.JDBC;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> userList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            User user = new User(userID, userName, password);
            userList.add(user);
        }
        return userList;
    }

    public static boolean validatePassword (String logInUsername, String logInPassword) throws SQLException {
        String sql = "SELECT Password FROM users WHERE User_Name = ?";
        PreparedStatement psValidatePassword = JDBC.getConnection().prepareStatement(sql);
        psValidatePassword.setString(1, logInUsername);
        ResultSet rs = psValidatePassword.executeQuery();

        try {
            if (rs.next()) {
                String storedPassword = rs.getString("Password");

                if (storedPassword.equals(logInPassword)) {
                    return true;
                }
            }
        } catch (SQLException e) {
                e.printStackTrace();
            }

        return false;
    }
}
