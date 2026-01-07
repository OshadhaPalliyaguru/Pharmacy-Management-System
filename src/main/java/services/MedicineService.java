package services;

import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.Medicine;

import java.sql.SQLException;
import java.util.List;

public interface MedicineService {

    boolean addMedicine(Medicine medicine);
    ObservableList<Medicine>  searchByName(String name);
    List<Medicine> getAllMedicines();
    boolean Update(Medicine medicine);
    boolean deleteMedicine(String name) throws SQLException;
    void updateMedicineQty(ObservableList<CartItem> cartItems);
    boolean UpdateSupplierQyt(String medicineId, int qty);
}
