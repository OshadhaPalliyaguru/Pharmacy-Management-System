package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.Sales;
import services.SaleService;
import services.impl.SalesServiceImpl;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SalesFormController implements Initializable {

      SaleService saleService = new SalesServiceImpl();
      ObservableList<Sales> salesObservableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Sales, LocalDateTime> colCreatedAt;

    @FXML
    private TableColumn<?, ?> colcashier;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colinvoiceno;

    @FXML
    private TableColumn<Sales, LocalDateTime> colsaledate;

    @FXML
    private TableColumn<?, ?> coltotalamount;

    @FXML
    private TableView<Sales> tblSaless;

    @FXML
    private TextField txtSearch;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TableColumn Types
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colinvoiceno.setCellValueFactory(new PropertyValueFactory<>("invoice_no"));
        colsaledate.setCellValueFactory(new PropertyValueFactory<>("sale_date"));
        colcashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));
        coltotalamount.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        loadAllSales();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        colsaledate.setCellFactory(col -> new TableCell<Sales, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : dtf.format(item));
            }
        });



        colCreatedAt.setCellFactory(column -> new TableCell<Sales, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(dtf));
                }
            }
        });


        salesObservableList = FXCollections.observableArrayList();
        loadAllSales();


        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredSalesList(newValue);
        });
    }


    private void loadAllSales() {
        salesObservableList.clear();
        salesObservableList.addAll(saleService.getAllSales());
        tblSaless.setItems(salesObservableList);
    }

    private void  filteredSalesList(String searchText){
        ObservableList<Sales> filteredList=FXCollections.observableArrayList();
        for (Sales sales:salesObservableList){
            if (sales.getInvoice_no().toLowerCase().contains(searchText.toLowerCase())||
            sales.getCashier().toLowerCase().contains(searchText.toLowerCase())){
                filteredList.add(sales);
            }
        }
        tblSaless.setItems(filteredList);
    }
}


