package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class DashBoardFormController {

    @FXML
    private AnchorPane LoadMedicine;

    @FXML
    private Button btnMedicine;

    // --- NAVIGATION BUTTONS ---

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        loadUI("/view/Home_Page.fxml");
    }

    @FXML
    void btnMedicinesOnAction(ActionEvent event) throws IOException {
        loadUI("/view/MedicineForm.fxml");
    }

    @FXML
    void btnBillingOnAction(ActionEvent event) throws IOException {
        loadUI("/view/Billing.fxml");
    }

    @FXML
    void btnSuppliersOnAction(ActionEvent event) throws IOException {
        loadUI("/view/SupplierForm.fxml");
    }

    @FXML
    void btnPurchaseOnAction(ActionEvent event) throws IOException {
        loadUI("/view/PurchaseForm.fxml");
    }

    @FXML
    void btnPurchaseItemOnAction(ActionEvent event) throws IOException {
        loadUI("/view/Purchase_ItemForm.fxml");
    }

    @FXML
    void btnSalesOnAction(ActionEvent event) throws IOException {
        loadUI("/view/SalesForm.fxml");
    }

    @FXML
    void btnSalesitemsOnAction(ActionEvent event) throws IOException {
        loadUI("/view/Sales_ItemsForm.fxml");
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws IOException {
        loadUI("/view/Registerform.fxml");
    }

    // --- NEW LOGOUT FUNCTION ---
    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        try {
            // 1. Get the current button to find the current window (Stage)
            Button btn = (Button) event.getSource();
            Stage currentStage = (Stage) btn.getScene().getWindow();

            // 2. Close the Dashboard
            currentStage.close();

            // 3. Load the Login/Signup Screen
            // CHANGE "/view/LoginForm.fxml" to your actual login or signup file name!
            URL resource = getClass().getResource("/view/LoginForm.fxml");

            if(resource == null) {
                System.out.println("❌ Error: Login FXML not found!");
                return;
            }

            Parent root = FXMLLoader.load(resource);
            Stage newStage = new Stage();
            newStage.setTitle("Login"); // Optional title
            newStage.setScene(new Scene(root));
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- THE MAGIC METHOD ---
    private void loadUI(String fxmlPath) throws IOException {
        URL resource = getClass().getResource(fxmlPath);
        if (resource == null) {
            System.out.println("❌ Error: FXML file not found at " + fxmlPath);
            return;
        }
        Parent load = FXMLLoader.load(resource);
        LoadMedicine.getChildren().clear();
        LoadMedicine.getChildren().add(load);
        AnchorPane.setTopAnchor(load, 0.0);
        AnchorPane.setBottomAnchor(load, 0.0);
        AnchorPane.setLeftAnchor(load, 0.0);
        AnchorPane.setRightAnchor(load, 0.0);
    }
}