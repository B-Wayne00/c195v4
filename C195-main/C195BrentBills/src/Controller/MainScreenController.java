package Controller;

import DAO.AppointmentsDB;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class MainScreenController {

        @FXML
        private RadioButton AllRadioButton;

        @FXML
        private TableColumn<Appointment, String> AppContactCol;

        @FXML
        private TableColumn<Appointment, Integer> AppCustIDCol;

        @FXML
        private TableColumn<Appointment, String> AppDescCol;

        @FXML
        private TableColumn<Appointment, Integer> AppEndCol;

        @FXML
        private TableColumn<Appointment, Integer> AppIDCol;

        @FXML
        private TableColumn<Appointment, String> AppLocationCol;

        @FXML
        private TableColumn<Appointment, Integer> AppStartCol;

        @FXML
        private TableColumn<Appointment, String> AppTitleCol;

        @FXML
        private TableColumn<Appointment, String> AppTypeCol;

        @FXML
        private TableColumn<Appointment, Integer> AppUserIDCol;

        @FXML
        private TableView<Appointment> AppointmentTable;

        @FXML
        private Button DeleteAppointmentButton;

        @FXML
        private Button LogOutButton;

        @FXML
        private RadioButton MonthRadioButton;

        @FXML
        private Button ToAddAppointmentButton;

        @FXML
        private Button ToCustomersButton;

        @FXML
        private Button ToModifyAppointmentButton;

        @FXML
        private Button ToReportsButton;

        @FXML
        private Label UpcomingAppointmentLabel;

        @FXML
        private RadioButton WeekRadioButton;

        @FXML
        void deleteAppointment(ActionEvent event) throws SQLException {
                int selectedAppID = AppointmentTable.getSelectionModel().getSelectedItem().getAppointmentID();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Warning");
                alert.setContentText("Are you sure you wish to delete appointment " + selectedAppID+ "?");
                Optional<ButtonType> choice = alert.showAndWait();
                if (choice.get() == ButtonType.OK) {
                        AppointmentsDB.deleteAppointment(selectedAppID);
                        AppointmentTable.setItems(AppointmentsDB.getAllAppointments());
                        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                        confirmation.setHeaderText("Success");
                        confirmation.setContentText("Apointment deleted!");
                        confirmation.showAndWait();


                }
        }


        @FXML
        void logOut(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Log Out?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                        System.exit(0);
                }
        }

        @FXML
        void toAddAppointment(ActionEvent event) throws Exception {
                Parent root = FXMLLoader.load(getClass().getResource("../View/AddAppointment.fxml"));
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 700);
                stage.setTitle("Add Appointment");
                stage.setScene(scene);
                stage.show();
        }

        @FXML
        void toCustomers(ActionEvent event) throws IOException {
                Parent root = FXMLLoader.load(getClass().getResource("../View/Customers.fxml"));
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 700);
                stage.setTitle("Customers");
                stage.setScene(scene);
                stage.show();
        }

        @FXML
        void toModifyAppointment(ActionEvent event) throws Exception {
                Appointment selectedAppointment = AppointmentTable.getSelectionModel().getSelectedItem();

                if(selectedAppointment == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Error");
                        alert.setContentText("Please select an appointment");
                        alert.showAndWait();
                }else{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ModifyAppointment.fxml"));
                        Parent root = loader.load();
                        ModifyAppointmentController appController = loader.getController();
                        Stage stage = (Stage) ((Button)
                                event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 850, 575);
                        stage.setTitle("Add Part");
                        stage.setScene(scene);
                        stage.show();
                        appController.sendAppointment(selectedAppointment);
                }

        }

        @FXML
        void toReports(ActionEvent event) throws IOException {
                Parent root = FXMLLoader.load(getClass().getResource("../View/Reports.fxml"));
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 700);
                stage.setTitle("Reports");
                stage.setScene(scene);
                stage.show();
        }

        /**
         * This method filters the table to appointments in the current month when the Month radio button is selected.
         * @param event
         * @throws SQLException
         */
        @FXML
        void appointmentByMonth (ActionEvent event) throws SQLException {
                ObservableList<Appointment> appointmentObservableList = AppointmentsDB.getAllAppointments();
                ObservableList<Appointment> appointmentByMonthList = FXCollections.observableArrayList();

                LocalDateTime currentMonthStart = LocalDateTime.now();
                LocalDateTime currentMonthEnd = LocalDateTime.now().plusMonths(1);

                if (appointmentObservableList != null) {
                        for (Appointment appointment : appointmentObservableList) {
                                if (appointment.getStart().isBefore(currentMonthEnd) && appointment.getStart().isAfter(currentMonthStart)) {
                                        appointmentByMonthList.add(appointment);
                                }
                        }
                        AppointmentTable.setItems(appointmentByMonthList);
                }
        }

        @FXML
        void appointmentByWeek (ActionEvent event) throws SQLException {
                ObservableList<Appointment> appointmentObservableList = AppointmentsDB.getAllAppointments();
                ObservableList<Appointment> appointmentByWeekList = FXCollections.observableArrayList();

                LocalDateTime currentWeekStart = LocalDateTime.now();
                LocalDateTime currentWeekEnd = LocalDateTime.now().plusDays(7);

                if (appointmentObservableList != null) {
                        for (Appointment appointment : appointmentObservableList) {
                                if (appointment.getStart().isBefore(currentWeekEnd) && appointment.getStart().isAfter(currentWeekStart)) {
                                        appointmentByWeekList.add(appointment);
                                }
                        }
                        AppointmentTable.setItems(appointmentByWeekList);
                }
        }

        /**
         * This method resets the Appointment table to all appointments when the All radio button is selected.
         * @param event
         * @throws SQLException
         */
        @FXML
        void appointmentByAll(ActionEvent event) throws SQLException {
                ObservableList<Appointment> appointmentObservableList = AppointmentsDB.getAllAppointments();

                AppointmentTable.setItems(appointmentObservableList);
        }

        /**
         * Initializes the Appointment table and observable list.
         * @throws SQLException
         */


        public void initialize() throws SQLException {
            ObservableList<Appointment> allAppointments = AppointmentsDB.getAllAppointments();

            AppIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            AppTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            AppDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            AppLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            AppTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            AppStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            AppEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            AppCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            AppUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
            AppContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

            AppointmentTable.setItems(allAppointments);
        }
}
