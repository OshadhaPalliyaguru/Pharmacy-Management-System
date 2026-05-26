package services.impl;

import db.DBConnection;
import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.SaleItem;
import model.dto.Sales;
import repository.SalesItemRepository;
import repository.impl.SalesItemRepositoryImpl;
import services.SalesItemService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SalesItemServiceImpl implements SalesItemService {
    private final SalesItemRepository salesItemRepository = new SalesItemRepositoryImpl();

    @Override
    public List<SaleItem> getAllSaleItems() {
        return salesItemRepository.getAllSaleItems();
    }
    @Override
    public void addSaleItem(Sales sales, ObservableList<CartItem> cartItems) {
        try {
            SalesItemRepository salesItemRepository = new SalesItemRepositoryImpl();


            for (CartItem item : cartItems) {
                int medicineId = Integer.parseInt(item.getMedicineId());
                SaleItem saleItem = new SaleItem(
                        sales.getId(),
                        medicineId,
                        item.getQuantity(),
                        item.getUnitPrice(),
                        sales.getCreated_at()
                );
                salesItemRepository.addSaleItem(saleItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving sales items: " + e.getMessage());
        }
    }


    public String generateInvoiceNo() {

        String SQL = "SELECT invoice_no FROM sales ORDER BY id DESC LIMIT 1";
        String lastInvoice = null;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lastInvoice = resultSet.getString("invoice_no");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (lastInvoice == null) {
            return "INV0001";
        }
        int number = Integer.parseInt(lastInvoice.replace("INV", "")) + 1;

        return String.format("INV%04d", number);
    }
}

