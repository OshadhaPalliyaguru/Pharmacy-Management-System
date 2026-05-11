package services.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Suppliers;
import repository.SupplierRepository;
import repository.impl.SupplierRepositoryImpl;
import services.SupplierService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository = new SupplierRepositoryImpl();

    @Override
    public boolean addSupplier(Suppliers suppliers) {
        return supplierRepository.addSupplier(suppliers);
    }

    @Override
    public boolean updateSupplier(Suppliers suppliers) {
        return supplierRepository.updateSupplier(suppliers);
    }

    @Override
    public List<Suppliers> getAllSuppliers() {

        ObservableList<Suppliers> suppliersList = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        try {
           resultSet = (ResultSet) supplierRepository.getAllSuppliers();

            while (resultSet.next()) {
                Suppliers suppliers = new Suppliers();

                suppliers.setId(String.valueOf(resultSet.getInt("id")));
                suppliers.setName(resultSet.getString("name"));
                suppliers.setPhone(resultSet.getString("phone"));
                suppliers.setEmail(resultSet.getString("email"));
                suppliers.setAddress(resultSet.getString("address"));
                suppliers.setCreatedAt(resultSet.getDate("created_at").toLocalDate());

             suppliersList.add(suppliers);
            }

    } catch(
    SQLException e)

    {
        throw new RuntimeException("Error loading suppliers", e);
    }

        return suppliersList;
}
    @Override
    public Suppliers findByName(String name) {
        return supplierRepository.findByName(name);
    }

    @Override
    public boolean deleteSupplier(String id) {
        return supplierRepository.deleteSupplier(id);
    }

}
