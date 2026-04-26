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
import model.dto.Purchase_items;
import services.PurchaseItemService;
import services.impl.PurchaseItemServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PurchaseItemFormController implements Initializable {

    public final ObservableList<Purchase_items> purchaseItemArrayList = FXCollections.observableArrayList();
    public final PurchaseItemService purchaseItemService = new PurchaseItemServiceImpl();


    @FXML
    private TableColumn<?, ?> colbatchno;

    @FXML
    private TableColumn<?, ?> colcreatedat;

    @FXML
    private TableColumn<?, ?> colexpirydate;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colmedicineid;

    @FXML
    private TableColumn<?, ?> colpurchaseid;

    @FXML
    private TableColumn<?, ?> colqty;

    @FXML
    private TableColumn<?, ?> colunitprice;

    @FXML
    private TableView<Purchase_items> tblPurchaseItem;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnAddPurchaseItemOnAction(ActionEvent event) {
        Stage stage1 = new Stage();
        stage1.setOnHidden(e -> loadPurchaseItem());
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddPurchaseItemForm.fxml"))));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        stage1.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colpurchaseid.setCellValueFactory(new PropertyValueFactory<>("purchase_id"));
        colmedicineid.setCellValueFactory(new PropertyValueFactory<>("medicine_id"));
        colbatchno.setCellValueFactory(new PropertyValueFactory<>("batch_no"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colunitprice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colexpirydate.setCellValueFactory(new PropertyValueFactory<>("expiry_date"));
        colcreatedat.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        loadPurchaseItem();
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterPurchaseItemList(newValue);
        });

    }

    public void loadPurchaseItem() {
        purchaseItemArrayList.clear();
        purchaseItemArrayList.addAll(purchaseItemService.getAllPurchaseItems());
        tblPurchaseItem.setItems(purchaseItemArrayList);
    }

    private void filterPurchaseItemList(String searchText) {
        ObservableList<Purchase_items> filteredList = FXCollections.observableArrayList();

        for (Purchase_items item : purchaseItemArrayList) {
            if (String.valueOf(item.getId()).contains(searchText) ||
                    String.valueOf(item.getPurchase_id()).contains(searchText) ||
                    String.valueOf(item.getMedicine_id()).contains(searchText) ||
                    item.getBatch_no().toLowerCase().contains(searchText.toLowerCase()) ||
                    String.valueOf(item.getQty()).contains(searchText) ||
                    String.valueOf(item.getUnit_price()).contains(searchText) ||
                    item.getExpiry_date().toString().contains(searchText)
            ) {
                filteredList.add(item);
            }
        }

        tblPurchaseItem.setItems(filteredList);
    }
}
