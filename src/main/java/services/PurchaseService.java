package services;

import model.dto.Purchases;

import java.util.List;

public interface PurchaseService {
    boolean addPurchase(Purchases purchases);
    boolean updatePurchase(Purchases purchases);
    List<Purchases>getAllPurchases();
    Purchases findPurchaseById(int id);
    Boolean deletePurchase(String invoiceno);

}
