package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.Purchases;
import services.PurchaseService;
import services.impl.PurchaseServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PurchaseFormController implements Initializable {

    private final ObservableList<Purchases> purchaseList = FXCollections.observableArrayList();
    private final PurchaseService purchaseService = new PurchaseServiceImpl();

    @FXML
    private TableColumn<?, ?> colCreatedat;

    @FXML
    private TableColumn<?, ?> colInvoiceno;

    @FXML
    private TableColumn<?, ?> colPurchasedate;

    @FXML
    private TableColumn<?, ?> colSupplierid;

    @FXML
    private TableColumn<?, ?> colTotalamount;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableView<Purchases> tblPurchase;
    @FXML
    private TextField txtSearch;



    @FXML
    void btnAddPurchaseOnAction(ActionEvent event) {
        Stage stage1 = new Stage();
        stage1.setOnHidden(e -> LoadPurchase());
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddPurchaseForm.fxml"))));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        stage1.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colInvoiceno.setCellValueFactory(new PropertyValueFactory<>("invoice_no"));
        colSupplierid.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colPurchasedate.setCellValueFactory(new PropertyValueFactory<>("purchase_date"));
        colTotalamount.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
        colCreatedat.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        LoadPurchase();
       txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterPurchaseList(newValue);
        });

    }

    public void LoadPurchase() {
        purchaseList.clear();
        purchaseList.addAll(purchaseService.getAllPurchases());
        tblPurchase.setItems(purchaseList);

    }
    private void filterPurchaseList(String keyword) {
        ObservableList<Purchases> filteredList = FXCollections.observableArrayList();

        for (Purchases purchase : purchaseList) {
            if (purchase.getInvoice_no().toLowerCase().contains(keyword.toLowerCase()) ||
                    String.valueOf(purchase.getId()).contains(keyword) ||
                    String.valueOf(purchase.getTotal_amount()).contains(keyword)) {
                filteredList.add(purchase);
            }
        }

        tblPurchase.setItems(filteredList);
    }


}

