package services.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.Medicine;
import repository.MedicineRepository;
import repository.impl.MedicineRepositoryImpl;
import services.MedicineService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository = new MedicineRepositoryImpl();

    @Override
    public boolean addMedicine(Medicine medicine) {
        return medicineRepository.addMedicine(medicine);
    }

    @Override
    public ObservableList<Medicine> searchByName(String name) {

        ObservableList<Medicine> medicinesList = FXCollections.observableArrayList();
        ResultSet resultSet = null;

        try {
            resultSet = medicineRepository.searchByName(name);
            if (resultSet.next()) {
              Medicine  medicine = new Medicine(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("brand"),
                        resultSet.getString("batch_no"),
                        resultSet.getDate("expiry_date").toLocalDate(),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("unit_price"),
                        resultSet.getInt("reorder_level"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                );
              medicinesList.add(medicine);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       return medicinesList;

    }


    @Override
    public List<Medicine> getAllMedicines() {
        return medicineRepository.getAllMedicines();
    }

    @Override
    public boolean Update(Medicine medicine) {
        return medicineRepository.updateMedicine(medicine);
    }

    @Override
    public boolean deleteMedicine(String name) throws SQLException {
      return  medicineRepository.deleteMedicine(name);
    }

    @Override
    public void updateMedicineQty(ObservableList<CartItem> cartItems) {
        for (CartItem cartItem : cartItems){
            try {
                medicineRepository.updateMedicineQty(cartItem.getMedicineId(),cartItem.getQuantity());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean UpdateSupplierQyt(String medicineId, int qty) {
        return medicineRepository.UpdateSupplierQyt(medicineId, qty);
    }

}
