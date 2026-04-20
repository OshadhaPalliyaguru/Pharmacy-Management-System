package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.dto.Purchase_items;
import repository.impl.PurchaseItemRepositoryImpl;
import services.PurchaseItemService;
import services.impl.PurchaseItemServiceImpl;

import java.time.LocalDate;

public class AddPurchaseItemFormController {

    private final PurchaseItemService purchaseItemService = new PurchaseItemServiceImpl();
    private final PurchaseItemRepositoryImpl purchaseItemRepository = new PurchaseItemRepositoryImpl();
    @FXML
    private DatePicker dpcreatedat;

    @FXML
    private DatePicker dpexoiredate;

    @FXML
    private TextField txtBatchNo;

    @FXML
    private TextField txtMedicineid;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtpurchaseid;


    public void OnKeyReleased(javafx.scene.input.KeyEvent keyEvent) {

        String bachNo = txtBatchNo.getText();
        if (bachNo.isEmpty()) {
            return;
        }
        Purchase_items purchaseItems = purchaseItemRepository.findBybatchNo(bachNo);
        if (purchaseItems != null) {
            txtid.setText(purchaseItems.getId().toString());
            txtpurchaseid.setText(String.valueOf(purchaseItems.getPurchase_id()));
            txtMedicineid.setText(String.valueOf(purchaseItems.getMedicine_id()));
            txtQty.setText(String.valueOf(purchaseItems.getQty()));
            txtUnitPrice.setText(String.valueOf(purchaseItems.getUnit_price()));
            txtBatchNo.setText(purchaseItems.getBatch_no());
            dpexoiredate.setValue(purchaseItems.getExpiry_date());
            dpcreatedat.setValue(purchaseItems.getCreated_at());

        }

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String batchNo = txtBatchNo.getText().trim();
        if (txtBatchNo.getText().isEmpty() || txtMedicineid.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill fields");
            return;
        }


        boolean isDeleted = purchaseItemService.deletePurchaseItems(batchNo);

        if (isDeleted) {
            showAlert(Alert.AlertType.INFORMATION, "Purchase item deleted successfully!");
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to delete purchase item.");
        }
    }


    @FXML
    void btnEditOnAction(ActionEvent event) {
        if (txtid.getText().isEmpty() || txtpurchaseid.getText().isEmpty() || txtMedicineid.getText().isEmpty() || txtQty.getText().isEmpty() || txtUnitPrice.getText().isEmpty() || txtBatchNo.getText().isEmpty() || dpexoiredate.getValue() == null || dpcreatedat.getValue() == null) {
            showAlert(Alert.AlertType.valueOf("Error"), "Please fill all fields");
            return;
        }
        Purchase_items purchaseItems = new Purchase_items();
        purchaseItems.setId(Integer.parseInt(txtid.getText().trim()));
        purchaseItems.setPurchase_id(Integer.parseInt(txtpurchaseid.getText().trim()));
        purchaseItems.setMedicine_id(Integer.parseInt(txtMedicineid.getText().trim()));
        purchaseItems.setQty(Integer.parseInt(txtQty.getText().trim()));
        purchaseItems.setUnit_price(Double.parseDouble(txtUnitPrice.getText().trim()));
        purchaseItems.setBatch_no(txtBatchNo.getText().trim());
        purchaseItems.setExpiry_date(dpexoiredate.getValue());
        purchaseItems.setCreated_at(dpcreatedat.getValue());

        boolean isUpdated = purchaseItemService.updatePurchaseItem(purchaseItems);
        if (isUpdated) {
            showAlert(Alert.AlertType.INFORMATION, "Purchase item updated successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to update purchase item.");
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        if (txtpurchaseid.getText().isEmpty() ||
                txtMedicineid.getText().isEmpty() ||
                txtQty.getText().isEmpty() ||
                txtUnitPrice.getText().isEmpty() ||
                txtBatchNo.getText().isEmpty() ||
                dpexoiredate.getValue() == null ||
                dpcreatedat.getValue() == null) {

            showAlert(Alert.AlertType.ERROR, "Please fill all fields");
            return;
        }

        Purchase_items purchaseItems = new Purchase_items();
        purchaseItems.setPurchase_id(Integer.parseInt(txtpurchaseid.getText().trim()));
        purchaseItems.setMedicine_id(Integer.parseInt(txtMedicineid.getText().trim()));
        purchaseItems.setQty(Integer.parseInt(txtQty.getText().trim()));
        purchaseItems.setUnit_price(Double.parseDouble(txtUnitPrice.getText().trim()));
        purchaseItems.setBatch_no(txtBatchNo.getText().trim());
        purchaseItems.setExpiry_date(LocalDate.parse(dpexoiredate.getValue().toString()));
        purchaseItems.setCreated_at(LocalDate.parse(dpcreatedat.getValue().toString()));


        boolean isAdded = purchaseItemService.addPurchaseItem(purchaseItems);

        if (isAdded) {
            showAlert(Alert.AlertType.INFORMATION, "Purchase item saved successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to save purchase item.");
        }
}

    private void showAlert(Alert.AlertType warning, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void clearFields() {
        txtid.clear();
        txtid.clear();
        txtpurchaseid.clear();
        txtMedicineid.clear();
        txtQty.clear();
        txtUnitPrice.clear();
        txtBatchNo.clear();
        dpexoiredate.setValue(null);
        dpcreatedat.setValue(null);

    }



}