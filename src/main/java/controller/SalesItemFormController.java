package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.SaleItem;
import services.SalesItemService;
import services.impl.SalesItemServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class SalesItemFormController implements Initializable {

    private final ObservableList<SaleItem>saleItems = FXCollections.observableArrayList();
    SalesItemService salesItemService = new SalesItemServiceImpl();

    @FXML
    private TableColumn<?, ?> colCreatedAt;

    @FXML
    private TableColumn<?, ?> colMedicineid;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSalesid;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableView<SaleItem> tblSalesItems;

    @FXML
    private TextField txtSearch;

//    @FXML
//    void btnAddSalesItemOnAction(ActionEvent event) {
//        Stage stage1 = new Stage();
//        try {
//            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddSalesItemForm.fxml"))));
//        } catch (
//                IOException e) {
//            throw new RuntimeException(e);
//        }
//        stage1.show();
//
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSalesid.setCellValueFactory(new PropertyValueFactory<>("sale_id"));
        colMedicineid.setCellValueFactory(new PropertyValueFactory<>("medicine_id"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        loadAllSalesItems();
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredSalesItemsList(newValue);
        });

    }
    private void loadAllSalesItems(){
        saleItems.clear();
        saleItems.addAll(salesItemService.getAllSaleItems());
        tblSalesItems.setItems(saleItems);
    }
    private void filteredSalesItemsList(String searchText){
        ObservableList<SaleItem> filteredList = FXCollections.observableArrayList();

        for (SaleItem saleItem : saleItems) {
            if (String.valueOf(saleItem.getSale_id()).contains(searchText) ||
                    String.valueOf(saleItem.getMedicine_id()).contains(searchText)) {

                filteredList.add(saleItem);
            }
        }

        tblSalesItems.setItems(filteredList);
    }
    }
