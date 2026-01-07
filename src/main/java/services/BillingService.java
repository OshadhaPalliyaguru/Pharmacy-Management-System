package services;

import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.Medicine;
import model.dto.Sales;

public interface BillingService {
    ObservableList<Medicine> searchByName(String name);
    void billing(Sales sales, ObservableList<CartItem>cartItems);


}
