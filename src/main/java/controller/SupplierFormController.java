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
import services.SupplierService;
import services.impl.SupplierServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {
    SupplierService supplierService = new SupplierServiceImpl();
    private final ObservableList observableList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colPhoneno;

    @FXML
    private TableColumn<?, ?> colcreatedat;

    @FXML
    private TableColumn<?, ?> colemail;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableView<?> tblSuppliers;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnAddSuppliersOnAction(ActionEvent event) {
        Stage stage1 = new Stage();
        stage1.setOnHidden(windowEvent -> loadSuppliers());
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddSupplierForm.fxml"))));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        stage1.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhoneno.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colcreatedat.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterSupplierList(newValue);
        });


        loadSuppliers();

    }

    private void loadSuppliers() {
        observableList.clear();
        observableList.addAll(supplierService.getAllSuppliers());
        tblSuppliers.setItems(observableList);


    }
    private void filterSupplierList(String keyword) {
        ObservableList filteredList = FXCollections.observableArrayList();
        for (Object supplier : observableList) {
            if (supplier.toString().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(supplier);
            }
        }
        tblSuppliers.setItems(filteredList);
    }
}
