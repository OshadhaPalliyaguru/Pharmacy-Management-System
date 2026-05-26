package repository;

import model.dto.Purchases;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface PurchaseRepository {
    boolean addPurchase(Purchases purchases);

    boolean updatePurchase(Purchases purchases);

   ResultSet getAllPurchases() throws SQLException;

    Purchases searchByInvoiceNo(String s);

    Boolean deletePurchase(String invoiceno);
}
