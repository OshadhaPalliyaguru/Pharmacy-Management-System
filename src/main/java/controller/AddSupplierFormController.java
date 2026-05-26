package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.dto.Suppliers;
import repository.MedicineRepository;
import repository.impl.MedicineRepositoryImpl;
import services.MedicineService;
import services.SupplierService;
import services.impl.MedicineServiceImpl;
import services.impl.SupplierServiceImpl;

public class AddSupplierFormController {

    private final SupplierService supplierService = new SupplierServiceImpl();
    private  final MedicineRepository medicineRepository= new MedicineRepositoryImpl();
    private final MedicineService medicineService = new MedicineServiceImpl();


    @FXML
    private DatePicker dpcreatedat;

    @FXML
    private TextField txtPhoneno;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;
    @FXML
    private TextField txtMedicineId;

    @FXML
    private TextField txtQty;


    @FXML
    void btnCancelOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (txtid.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter supplier ID to delete");
            return;
        }
        boolean isDeleted = supplierService.deleteSupplier(txtid.getText().trim());
        if (isDeleted) {
            showAlert(Alert.AlertType.INFORMATION, "Supplier deleted successfully");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to delete supplier");

        }
    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        if (txtname.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter supplier name to edit");
            return;
        }
        Suppliers suppliers = new Suppliers();
        suppliers.setId(txtid.getText().trim());
        suppliers.setName(txtname.getText().trim());
        suppliers.setPhone(txtPhoneno.getText().trim());
        suppliers.setEmail(txtemail.getText().trim());
        suppliers.setAddress(txtaddress.getText().trim());
        suppliers.setCreatedAt(dpcreatedat.getValue());
        boolean isUpdated = supplierService.updateSupplier(suppliers);
        if (isUpdated) {
            showAlert(Alert.AlertType.INFORMATION, "Supplier updated successfully");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to update supplier");
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String medicineId = txtMedicineId.getText();
        int qty = Integer.parseInt(txtQty.getText());

        boolean ok = medicineService.UpdateSupplierQyt(medicineId, qty);

        if (ok) {
            showAlert(Alert.AlertType.INFORMATION, "Stock increased successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to update stock!");
        }
        if (txtname.getText().isEmpty() || txtemail.getText().isEmpty() || txtPhoneno.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please fill all required fields");
            return;
        }
        Suppliers suppliers = new Suppliers();
        suppliers.setId(txtid.getText().trim());
        suppliers.setName(txtname.getText().trim());
        suppliers.setPhone(txtPhoneno.getText().trim());
        suppliers.setEmail(txtemail.getText().trim());
        suppliers.setAddress(txtaddress.getText().trim());
        suppliers.setCreatedAt(dpcreatedat.getValue());

        boolean isAddeded = supplierService.addSupplier(suppliers);
        if (isAddeded) {
            showAlert(Alert.AlertType.INFORMATION, "Supplier added successfully");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to add supplier");
        }
    }

    private void showAlert(Alert.AlertType warning, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void clearFields() {
        txtid.clear();
        txtname.clear();
        txtPhoneno.clear();
        txtemail.clear();
        txtaddress.clear();
        dpcreatedat.setValue(null);
        txtMedicineId.clear();
        txtQty.clear();
    }

    public void OnKeyReleased(KeyEvent keyEvent) {
        String name = txtname.getText();

        Suppliers suppliers = supplierService.findByName(name);
        if (suppliers != null) {
            txtid.setText(suppliers.getId());
            txtPhoneno.setText(suppliers.getPhone());
            txtemail.setText(suppliers.getEmail());
            txtaddress.setText(suppliers.getAddress());
            dpcreatedat.setValue(suppliers.getCreatedAt());
        } else {

        }

    }
    public boolean UpdateSupplierQyt(String medId, int qty) {
        try {
            medicineRepository.UpdateSupplierQyt(medId, qty);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}


