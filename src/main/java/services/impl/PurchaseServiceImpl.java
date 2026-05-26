package services.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Purchases;
import repository.PurchaseRepository;
import repository.impl.PurchaseRepositoryImpl;
import services.PurchaseService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository=new PurchaseRepositoryImpl();

    @Override
    public boolean addPurchase(Purchases purchases) {
        return  purchaseRepository.addPurchase(purchases);
    }

    @Override
    public boolean updatePurchase(Purchases purchases) {
        return purchaseRepository.updatePurchase(purchases);
    }

    @Override
    public List<Purchases> getAllPurchases() {
        ObservableList<Purchases> PurchasesList = FXCollections.observableArrayList();


        try {
            ResultSet resultSet =purchaseRepository.getAllPurchases();

while (resultSet.next()) {
    Purchases purchases = new Purchases(
            resultSet.getInt("id"),
            resultSet.getInt("supplier_id"),
            resultSet.getString("invoice_no"),
            resultSet.getDate("purchase_date").toLocalDate(),
            resultSet.getDouble("total_amount"),
            resultSet.getDate("created_at").toLocalDate()
    );
    PurchasesList.add(purchases);
}
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        return PurchasesList;

    }
    @Override
    public Purchases findPurchaseById(int id) {
        return purchaseRepository.searchByInvoiceNo(String.valueOf(id));}

    @Override
    public Boolean deletePurchase(String invoiceno) {
   return purchaseRepository.deletePurchase(invoiceno);
    }

}






