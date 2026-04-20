package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import model.dto.CartItem;
import model.dto.Medicine;
import model.dto.Sales;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import services.BillingService;
import services.impl.BillingServiceImpl;
import services.impl.MedicineServiceImpl;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class BillingFormController implements Initializable {

    BillingService billingService = new BillingServiceImpl();
    MedicineServiceImpl medicineService = new MedicineServiceImpl();

    @FXML
    private ComboBox<String> cmbCashier;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colMedicineName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colCashier;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblFinalTotal;

    @FXML
    private TextField txtSaleid;

    @FXML
    private TableView<CartItem> tblBilling;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtMedicineName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtMedicineId;

    @FXML
    private Label lblSubTotal;

    // --- SEARCH LOGIC ---
    @FXML
    void MedicineOnKeyReleased(KeyEvent event) {
        String name = txtMedicineName.getText().trim();

        if (name.isEmpty()) {
            txtUnitPrice.clear();
            txtMedicineId.clear();
            return;
        }

        ObservableList<Medicine> medicines = billingService.searchByName(name);

        if (medicines != null && !medicines.isEmpty()) {
            Medicine med = medicines.get(0);
            txtUnitPrice.setText(String.valueOf(med.getUnit_price()));
            txtMedicineId.setText(String.valueOf(med.getId()));
        } else {
            // If no match found, clear these so the user can't add invalid items
            txtUnitPrice.clear();
            txtMedicineId.setText("ID");
        }
    }

    // --- ADD TO CART (FIXED) ---
    @FXML
    void btnAddToCart(ActionEvent event) {

        // 1. Validation: Check Customer Name
        if (txtCustomerName.getText().trim().isEmpty()) {
            show(Alert.AlertType.ERROR, "Error", "Customer Name is required");
            return;
        }

        // 2. Validation: Check if Medicine is actually selected
        String idText = txtMedicineId.getText();
        String priceText = txtUnitPrice.getText();

        if (idText.isEmpty() || idText.equals("ID") || priceText.isEmpty()) {
            show(Alert.AlertType.ERROR, "Selection Error", "Please search and SELECT a valid medicine first!");
            return;
        }

        ObservableList<CartItem> cartItems = tblBilling.getItems();

        String medName = txtMedicineName.getText();
        String customerName = txtCustomerName.getText();
        int qty = 0;
        double unitPrice = 0.0;

        // 3. Validation: Check Numbers
        try {
            qty = Integer.parseInt(txtQty.getText());
            unitPrice = Double.parseDouble(priceText);

            if (qty <= 0) {
                show(Alert.AlertType.ERROR, "Input Error", "Quantity must be greater than 0");
                return;
            }

        } catch (NumberFormatException e) {
            show(Alert.AlertType.ERROR, "Error", "Quantity and Unit Price must be valid numbers!");
            return;
        }

        double total = calculateTotal(qty, unitPrice);

        // 4. Duplicate Check
        for (CartItem item : cartItems) {
            if (item.getMedicineName().equalsIgnoreCase(medName) &&
                    item.getCustomerName().equalsIgnoreCase(customerName)) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Duplicate Item");
                alert.setHeaderText("Item already in cart!");
                alert.setContentText("Do you want to update the quantity?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.isPresent() && option.get() == ButtonType.OK) {
                    int newQty = item.getQuantity() + qty;
                    item.setQuantity(newQty);
                    item.setTotal(newQty * item.getUnitPrice());

                    tblBilling.refresh();
                    calculateFinalyTotal();
                    clearFields();
                }
                return;
            }
        }

        // 5. Add New Item
        CartItem newItem = new CartItem(
                txtMedicineId.getText(),
                customerName,
                medName,
                unitPrice,
                qty,
                total,
                cmbCashier.getValue()
        );

        cartItems.add(newItem);
        tblBilling.setItems(cartItems);
        calculateFinalyTotal();
        clearFields();
    }

    private double calculateTotal(int qty, double unitPrice) {
        return qty * unitPrice;
    }

    @FXML
    void btnExportPDFOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/Pharmacy.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
                System.out.println("PDF Saved Successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        ObservableList<CartItem> cartItems = tblBilling.getItems();
        if (cartItems.isEmpty()) {
            show(Alert.AlertType.ERROR, "Error", "Cart is empty");
            return;
        }
        String cashier = cmbCashier.getValue();
        if (cashier == null || cashier.isEmpty()) {
            show(Alert.AlertType.ERROR, "Error", "Please Select a Cashier!");
            return;
        }

        Sales sales = new Sales();
        sales.setCashier(cmbCashier.getValue());
        sales.setInvoice_no(generateInvoiceNumber());
        sales.setSale_date(LocalDateTime.now());
        // Handle parsing error for final total just in case
        try {
            sales.setTotal_amount(Double.parseDouble(lblFinalTotal.getText()));
        } catch (NumberFormatException e) {
            sales.setTotal_amount(0.0);
        }
        sales.setCreated_at(LocalDateTime.now());

        billingService.billing(sales, cartItems);

        show(Alert.AlertType.INFORMATION, "Success", "Order placed successfully!");

        // Clear table and totals after order
        tblBilling.getItems().clear();
        lblSubTotal.setText("0.00");
        lblDiscount.setText("0.00");
        lblFinalTotal.setText("0.00");
        clearFields();
    }

    @FXML
    void btnPrintOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/Pharmacy.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint);

        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> CmbList = FXCollections.observableArrayList();
        CmbList.addAll("Cashier A", "Cashier B", "Cashier C", "Cashier D", "Cashier E");
        cmbCashier.setItems(CmbList);

        // Optional: Set default selection
        // cmbCashier.getSelectionModel().selectFirst();

        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colCashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));
    }

    public void clearFields() {
        txtCustomerName.clear();
        txtMedicineName.clear();
        txtQty.clear();
        txtUnitPrice.clear();
        txtMedicineId.clear(); // Added this to clear ID too
    }

    public void show(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void calculateFinalyTotal() {
        ObservableList<CartItem> cartItems = tblBilling.getItems();
        double subtotal = 0.0;
        for (CartItem item : cartItems) {
            subtotal += item.getTotal();
        }
        lblSubTotal.setText(String.format("%.2f", subtotal));

        double discount = subtotal * 0.1; // 10% discount logic
        lblDiscount.setText(String.format("%.2f", discount));

        double finalTotal = subtotal - discount;
        lblFinalTotal.setText(String.format("%.2f", finalTotal));
    }

    private String generateInvoiceNumber() {
        String prefix = "INV";
        String uniquePart = String.valueOf(System.currentTimeMillis()).substring(7);
        return prefix + uniquePart;
    }
}