package repository;

import model.dto.Medicine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface MedicineRepository {

    boolean addMedicine(Medicine medicine);

    ResultSet searchByName(String name) throws SQLException;

    List<Medicine> getAllMedicines();

    boolean updateMedicine(Medicine medicine);

    boolean deleteMedicine(String name);

    void updateMedicineQty(String medicineId, int quantity) throws SQLException;

    boolean UpdateSupplierQyt(String medicineId,int Qty);
}
