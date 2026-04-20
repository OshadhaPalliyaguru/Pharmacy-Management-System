package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.dto.Medicine;
import repository.impl.MedicineRepositoryImpl;
import services.MedicineService;
import services.impl.MedicineServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddEditMedicineFormController {

    private final MedicineService medicineService = new MedicineServiceImpl();
    private MedicineRepositoryImpl repository = new MedicineRepositoryImpl();
    private ObservableList<Medicine> medicineList = FXCollections.observableArrayList();

    private Integer currentMedicineId;
    @FXML
    private DatePicker dpcreatedat;

    @FXML
    private DatePicker dpupdatedat;

    @FXML
    private TextField txtbatchno;


    @FXML
    private TextField txtbrand;

    @FXML
    private DatePicker dpexpiredate;
    @FXML
    private TextField txtname;

    @FXML
    private TextField txtqty;

    @FXML
    private TextField txtrecorderlevel;

    @FXML
    private TextField txtunitprice;


    @FXML
    void btnCancelOnAction(ActionEvent event) {
        clearTextFields();
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String name = txtname.getText().trim();
        if (name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter a medicine name to delete!");
            return;
        }
        boolean isDeleted = medicineService.deleteMedicine(name);
        if (isDeleted) {
            showAlert(Alert.AlertType.INFORMATION, "💊 Medicine deleted successfully!");
            clearTextFields();
            loadAllMedicines();
        } else {
            showAlert(Alert.AlertType.ERROR, "❌ Deletion failed! Medicine not found.");
        }



    }

    @FXML
    void btnEditOnAction(ActionEvent event) {

        try {

            if (txtname.getText().isEmpty() || txtbrand.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Please fill all required fields!");

                return;
            }

            Medicine medicine = new Medicine();
            medicine.setName(txtname.getText().trim());
            medicine.setBrand(txtbrand.getText().trim());
            medicine.setBatch_no(txtbatchno.getText().trim());
            medicine.setExpiry_date(dpexpiredate.getValue());
            medicine.setQuantity(Integer.parseInt(txtqty.getText()));
            medicine.setUnit_price(Double.parseDouble(txtunitprice.getText()));
            medicine.setReorder_level(Integer.parseInt(txtrecorderlevel.getText()));
            medicine.setUpdated_at(LocalDateTime.now());
            medicine.setId(currentMedicineId);

            boolean isUpdated = medicineService.Update(medicine);

            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "💊 Medicine updated successfully!");
                //clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "❌ Update failed! Please check the ID or input values.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid input! Please check numeric fields.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }

    }


    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Medicine Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void clearFields() {

        txtname.clear();
        txtbrand.clear();
        txtbatchno.clear();
        dpexpiredate.setValue(null);
        txtqty.clear();
        txtunitprice.clear();
        txtrecorderlevel.clear();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (txtname.getText().isEmpty() || txtbrand.getText().isEmpty() ||
                txtbatchno.getText().isEmpty() || txtqty.getText().isEmpty() ||
                txtunitprice.getText().isEmpty() || dpexpiredate.getValue() == null) {

            showAlert("Please fill all fields!");
            return;
        }

        try {
            String name = txtname.getText().trim();
            String brand = txtbrand.getText().trim();
            String batchno = txtbatchno.getText().trim();
            LocalDate expireDate = dpexpiredate.getValue();
            if (expireDate == null) {
                showAlert("Please select an expiry date!");
                return;
            }
            int quantity = Integer.parseInt(txtqty.getText().trim());
            double unitPrice = Double.parseDouble(txtunitprice.getText().trim());
            int reorderLevel;
            try {
                reorderLevel = Integer.parseInt(txtrecorderlevel.getText().trim());
            } catch (NumberFormatException e) {
                showAlert("Reorder Level must be a number!");
                return;
            }


            Medicine medicine = new Medicine();
            medicine.setName(name);
            medicine.setBrand(brand);
            medicine.setBatch_no(batchno);
            medicine.setExpiry_date(LocalDate.from(expireDate.atStartOfDay()));
            medicine.setQuantity(quantity);
            medicine.setUnit_price(unitPrice);
            medicine.setReorder_level(reorderLevel);
            medicine.setCreated_at(LocalDateTime.now());
            medicine.setUpdated_at(LocalDateTime.now());

            boolean saved = medicineService.addMedicine(medicine);
            if (saved) {
                showAlert("Medicine added successfully!");
                loadAllMedicines();

            } else {
                showAlert("Failed to add medicine!");
            }

        } catch (NumberFormatException e) {
            showAlert("Quantity and Unit Price must be numbers!");
        }

    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearTextFields() {
        txtname.clear();
        txtbrand.clear();
        txtbatchno.clear();
        dpexpiredate.setValue(null);
        txtqty.clear();
        txtunitprice.clear();
        txtrecorderlevel.clear();
        dpcreatedat.setValue(null);
        dpupdatedat.setValue(null);
    }

    private void loadAllMedicines() {
        medicineList.clear();
        medicineList.addAll(repository.getAllMedicines());
    }


    public void onKeyReleased(KeyEvent keyEvent) {
        String name = txtname.getText().trim();
        if (name.isEmpty()) return;

        try {
            Medicine medicine = repository.getMedicineByName(name);
            if (medicine != null) {

                currentMedicineId = medicine.getId();
                txtbrand.setText(medicine.getBrand());
                txtbatchno.setText(medicine.getBatch_no());
                dpexpiredate.setValue(medicine.getExpiry_date());
                txtqty.setText(String.valueOf(medicine.getQuantity()));
                txtunitprice.setText(String.valueOf(medicine.getUnit_price()));
                txtrecorderlevel.setText(String.valueOf(medicine.getReorder_level()));
                dpcreatedat.setValue(LocalDate.from(medicine.getCreated_at()));
                dpupdatedat.setValue(LocalDate.from(medicine.getUpdated_at()));
            } else {

                txtbrand.clear();
                txtbatchno.clear();
                dpexpiredate.setValue(null);
                txtqty.clear();
                txtunitprice.clear();
                txtrecorderlevel.clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}