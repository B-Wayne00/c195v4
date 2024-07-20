package DAO;

import Helper.JDBC;
import Model.Customer;
import Model.FirstLevelDivision;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class FirstLevelDivisionDB {

    //Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID
    public static ObservableList<FirstLevelDivision> getAllDivisions() throws SQLException {
        ObservableList<FirstLevelDivision> firLvlDivList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivision flvldiv = new FirstLevelDivision(divisionID, divisionName, countryID);
            firLvlDivList.add(flvldiv);
        }
        return firLvlDivList;
    }

    public static ObservableList<FirstLevelDivision> getDivisionByCountry(Integer selectedCountryID) throws SQLException {
        ObservableList<FirstLevelDivision> divList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID ='" + selectedCountryID + "'";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivision flvldiv = new FirstLevelDivision(divisionID, divisionName, countryID);
            divList.add(flvldiv);
        }
        return divList;
    }


    public static int getDivisionIdDB(String divisionName) throws SQLException {
        int divisionId = -1;
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division='" + divisionName + "'";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            divisionId = rs.getInt("Division_ID");
        }
        return divisionId;
    }

    public static String getDivNameFromId(Integer divisionID) throws SQLException {
        String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID =" + divisionID + "";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String divFromId = null;
        if (rs.next()) {
            divFromId = rs.getString("Division");
        }
        return divFromId;
    }

}
