package DAO;

import Helper.JDBC;
import Model.Country;
import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDB {

    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> custCountryList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country custCountry = new Country(countryId, countryName);
            custCountryList.add(custCountry);
        }
        return custCountryList;
    }

    public static int getCountryIdFromSelection(String selectedCountry) throws SQLException {
        int countryID = -1;
        String sql = "SELECT Country_ID FROM countries WHERE Country='" + selectedCountry + "'";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            countryID = rs.getInt("Country_ID");
        }
        return countryID;
    }

    public static String getCountryFromDivID(Integer divisionID) throws SQLException {
        int divisionId = -1;
        String sql = "SELECT Country FROM countries WHERE Country_ID = (SELECT Country_ID FROM first_level_divisions WHERE Division_ID = " + divisionID + ")";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String countryFromDiv = null;
        if (rs.next()) {
            countryFromDiv = rs.getString("Country");
        }
        return countryFromDiv;
    }


}
