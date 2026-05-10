package repository.impl;

import db.DBConnection;
import model.dto.SaleItem;
import repository.SalesItemRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesItemRepositoryImpl implements SalesItemRepository {

    @Override
    public List<SaleItem> getAllSaleItems() {
        ArrayList<SaleItem> saleItems = new ArrayList<>();
        String SQL = "SELECT * FROM   sale_items";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SaleItem saleItem = new SaleItem(
                        resultSet.getInt("sale_id"),
                        resultSet.getInt("medicine_id"),
                        resultSet.getInt("qty"),
                        resultSet.getDouble("unit_price"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()

                );
                saleItems.add(saleItem);
            }
            return saleItems;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addSaleItem(SaleItem newSaleItem) {
        String SQL = "INSERT INTO  sale_items(sale_id, medicine_id, qty, unit_price, created_at) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, newSaleItem.getSale_id());
            preparedStatement.setObject(2, newSaleItem.getMedicine_id());
            preparedStatement.setObject(3, newSaleItem.getQty());
            preparedStatement.setObject(4, newSaleItem.getUnit_price());
            preparedStatement.setObject(5, newSaleItem.getCreated_at());


            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
