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
import model.dto.Medicine;
import services.MedicineService;
import services.impl.MedicineServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MedicineFormController implements Initializable {

    private final MedicineService medicineService = new MedicineServiceImpl();
    private final ObservableList<Medicine> medicineList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Medicine, String> colbatchno;

    @FXML
    private TableColumn<Medicine, String> colbrand;

    @FXML
    private TableColumn<Medicine, String> colcreatedat;

    @FXML
    private TableColumn<Medicine, String> colexpireate;

    @FXML
    private TableColumn<Medicine, Integer> colid;

    @FXML
    private TableColumn<Medicine, String> colname;

    @FXML
    private TableColumn<Medicine, Integer> colquantity;

    @FXML
    private TableColumn<Medicine, Integer> colrecorderlevel;

    @FXML
    private TableColumn<Medicine, Double> colunitprice;

    @FXML
    private TableColumn<Medicine, String> colupdatedat;

    @FXML
    private TableView<Medicine> tblmedicine;

    @FXML
    private TextField txtSearchOnAction;

    @FXML
    void btnAddMedicineOnAction(ActionEvent event) {
        Stage stage1 = new Stage();

        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddEditMedicineForm.fxml"))));
            stage1.setOnHidden(e -> loadMedicine());
            stage1.show();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colbrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colbatchno.setCellValueFactory(new PropertyValueFactory<>("batch_no"));
        colexpireate.setCellValueFactory(new PropertyValueFactory<>("expiry_date"));
        colquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colunitprice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colrecorderlevel.setCellValueFactory(new PropertyValueFactory<>("reorder_level"));
        colcreatedat.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        colupdatedat.setCellValueFactory(new PropertyValueFactory<>("updated_at"));
        tblmedicine.setItems(medicineList);
        loadMedicine();

        txtSearchOnAction.textProperty().addListener((obs, oldVal, newVal) -> filterMedicine(newVal));
    }


    public void loadMedicine() {
        medicineList.clear();
        medicineList.addAll(medicineService.getAllMedicines());
    }

    private void filterMedicine(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            tblmedicine.setItems(medicineList);
        } else {
            String lowerKeyword = keyword.toLowerCase();
            ObservableList<Medicine> filtered = FXCollections.observableArrayList();

            for (Medicine m : medicineList) {
                if (m.getName().toLowerCase().contains(lowerKeyword)
                        || m.getBrand().toLowerCase().contains(lowerKeyword)
                        || m.getBatch_no().toLowerCase().contains(lowerKeyword)) {
                    filtered.add(m);
                }
            }
            tblmedicine.setItems(filtered);
        }
    }
}

