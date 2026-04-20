package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.dto.Purchases;
import repository.impl.PurchaseRepositoryImpl;
import services.PurchaseService;
import services.impl.PurchaseServiceImpl;

import java.sql.Date;

public class AddPurchaseFormController {

  private final   PurchaseService purchaseService=new PurchaseServiceImpl();
   private final PurchaseRepositoryImpl purchaseRepository=  new PurchaseRepositoryImpl();
private ObservableList<Purchases> purchaseList= FXCollections.observableArrayList();

    @FXML
    private DatePicker dpcreatedat;

    @FXML
    private DatePicker dppurchasedate;

    @FXML
    private TextField txtInvoiceno;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtSupplierid;

    @FXML
    private TextField txttotalamount;

    @FXML
    void onKeyReleased(ActionEvent event) {
        String invoiceno = txtInvoiceno.getText();
        if(invoiceno.isEmpty()) {
            return;
        }
        Purchases purchases = purchaseRepository.searchByInvoiceNo(invoiceno);
        if(purchases != null) {
            txtid.setText(purchases.getId().toString());
            txtInvoiceno.setText(purchases.getInvoice_no());
            txtSupplierid.setText(String.valueOf(purchases.getSupplier_id()));
            txttotalamount.setText(String.valueOf(purchases.getTotal_amount()));
            dppurchasedate.setValue(purchases.getPurchase_date());
            dpcreatedat.setValue(purchases.getCreated_at());

        }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
    clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String invoiceno = txtInvoiceno.getText().trim();
        if (invoiceno.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter an invoice number to delete.");
            return;
        }
        boolean isDeleted = purchaseService.deletePurchase(invoiceno);
        if (isDeleted) {
            showAlert(Alert.AlertType.INFORMATION, "Purchase deleted successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to delete purchase. Please try again.");
        }

    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        if(txtInvoiceno.getText().isEmpty() || txtSupplierid.getText().isEmpty() || txttotalamount.getText().isEmpty() || dppurchasedate.getValue() == null || dpcreatedat.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Please fill all required fields!");
            return;
        }
        Purchases purchases = new Purchases();
        purchases.setId(Integer.parseInt(txtid.getText().trim()));
        purchases.setSupplier_id(Integer.parseInt(txtSupplierid.getText().trim()));
        purchases.setInvoice_no(txtInvoiceno.getText().trim());
        purchases.setTotal_amount(Double.parseDouble(txttotalamount.getText().trim()));
        purchases.setPurchase_date(Date.valueOf(dppurchasedate.getValue()).toLocalDate());
        purchases.setCreated_at(Date.valueOf(dpcreatedat.getValue()).toLocalDate());
        boolean isUpdated = purchaseService.updatePurchase(purchases);
        if (isUpdated) {
            showAlert(Alert.AlertType.INFORMATION, "Purchase updated successfully!");

        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to update purchase. Please try again.");

        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        if (txtInvoiceno.getText().isEmpty() || txtSupplierid.getText().isEmpty() || txttotalamount.getText().isEmpty() || dppurchasedate.getValue() == null || dpcreatedat.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Please fill all required fields!");
            return;
        }
        Purchases purchases = new Purchases();
        purchases.setSupplier_id(Integer.parseInt(txtSupplierid.getText().trim()));
        purchases.setInvoice_no(txtInvoiceno.getText().trim());
        purchases.setTotal_amount(Double.parseDouble(txttotalamount.getText().trim()));
        purchases.setPurchase_date(Date.valueOf(dppurchasedate.getValue()).toLocalDate());
        purchases.setCreated_at(Date.valueOf(dpcreatedat.getValue()).toLocalDate());

        boolean isAdded = purchaseService.addPurchase(purchases);
        if (isAdded) {
            showAlert(Alert.AlertType.INFORMATION, "Purchase added successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to add purchase. Please try again.");

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
        txtInvoiceno.clear();
        txtSupplierid.clear();
        txttotalamount.clear();
        dppurchasedate.setValue(null);
        dpcreatedat.setValue(null);
    }

}
