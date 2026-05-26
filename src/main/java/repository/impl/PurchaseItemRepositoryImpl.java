package repository.impl;

import db.DBConnection;
import model.dto.Purchase_items;
import repository.PurchaseItemRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseItemRepositoryImpl implements PurchaseItemRepository {
    public boolean addPurchaseItem(Purchase_items purchaseItems) {
        String Sql = "insert into purchase_items(purchase_id,medicine_id,batch_no, expiry_date ,qty,unit_price,created_at) values(?,?,?,?,?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);

            preparedStatement.setInt(1, purchaseItems.getPurchase_id());
            preparedStatement.setInt(2, purchaseItems.getMedicine_id());
            preparedStatement.setString(3, purchaseItems.getBatch_no());
            preparedStatement.setDate(4, java.sql.Date.valueOf(purchaseItems.getExpiry_date()));

            preparedStatement.setInt(5, purchaseItems.getQty());
            preparedStatement.setDouble(6, purchaseItems.getUnit_price());
            preparedStatement.setTimestamp(7, java.sql.Timestamp.valueOf(purchaseItems.getCreated_at().atStartOfDay()));
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updatePurchaseItems(Purchase_items purchaseItems) {
        String Sql = "update purchase_items set purchase_id=?, medicine_id=?, batch_no=?, expiry_date=?, qty=?, unit_price=?, created_at=? where id=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);

            preparedStatement.setInt(1, purchaseItems.getPurchase_id());
            preparedStatement.setInt(2, purchaseItems.getMedicine_id());
            preparedStatement.setString(3, purchaseItems.getBatch_no());
            preparedStatement.setDate(4, java.sql.Date.valueOf(purchaseItems.getExpiry_date()));
            preparedStatement.setInt(5, purchaseItems.getQty());
            preparedStatement.setDouble(6, purchaseItems.getUnit_price());
            preparedStatement.setDate(7, java.sql.Date.valueOf(purchaseItems.getCreated_at()));
            preparedStatement.setInt(8, purchaseItems.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Purchase_items findBybatchNo(String batchNo) {
        String Sql = "SELECT * FROM purchase_items WHERE batch_no = ?";
        Purchase_items purchaseItems = null;
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, batchNo);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                purchaseItems = new Purchase_items(
                        resultSet.getInt("id"),
                        resultSet.getInt("purchase_id"),
                        resultSet.getInt("medicine_id"),
                        resultSet.getString("batch_no"),
                        resultSet.getDate("expiry_date").toLocalDate(),
                        resultSet.getInt("qty"),
                        resultSet.getDouble("unit_price"),
                        resultSet.getDate("created_at").toLocalDate()
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return purchaseItems;
    }

    public List<Purchase_items> getAllPurchaseItems() {
        Purchase_items purchaseItems = null;
        ArrayList<Purchase_items> itemsArrayList = new ArrayList<>();

        String Sql = "SELECT * FROM purchase_items";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                purchaseItems = new Purchase_items(
                        resultSet.getInt("id"),
                        resultSet.getInt("purchase_id"),
                        resultSet.getInt("medicine_id"),
                        resultSet.getString("batch_no"),
                        resultSet.getDate("expiry_date").toLocalDate(),
                        resultSet.getInt("qty"),
                        resultSet.getDouble("unit_price"),
                        resultSet.getDate("created_at").toLocalDate()
                );
                itemsArrayList.add(purchaseItems);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemsArrayList;

    }
    public boolean deletePurchaseItems(String batchNo) {
        String sql = "DELETE FROM purchase_items WHERE batch_no = ?";
        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, batchNo);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


