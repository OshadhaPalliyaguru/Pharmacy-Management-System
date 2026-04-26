package repository.impl;

import db.DBConnection;
import model.dto.Purchases;
import repository.PurchaseRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseRepositoryImpl implements PurchaseRepository {

    public boolean addPurchase(Purchases purchases) {
        String SQL = "INSERT INTO purchases (supplier_id,invoice_no,purchase_date,total_amount,created_at) VALUES (?,?,?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, purchases.getSupplier_id());
            preparedStatement.setString(2, purchases.getInvoice_no());
            preparedStatement.setDate(3, java.sql.Date.valueOf(purchases.getPurchase_date()));
            preparedStatement.setDouble(4, purchases.getTotal_amount());
            preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(purchases.getCreated_at().atStartOfDay()));
            return preparedStatement.executeUpdate() > 0;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updatePurchase(Purchases purchases) {
        String SQL = "UPDATE purchases SET id=?, supplier_id=?, invoice_no=?, purchase_date=?, total_amount=?, created_at=? WHERE id=?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setInt(1, purchases.getId());
            preparedStatement.setInt(2, purchases.getSupplier_id());
            preparedStatement.setString(3, purchases.getInvoice_no());
            preparedStatement.setDate(4, java.sql.Date.valueOf(purchases.getPurchase_date()));
            preparedStatement.setDouble(5, purchases.getTotal_amount());
            preparedStatement.setDate(6, java.sql.Date.valueOf(purchases.getCreated_at()));
            preparedStatement.setInt(7, purchases.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllPurchases() throws SQLException {

        String SQL = "SELECT * FROM purchases";

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
    }

    public Purchases searchByInvoiceNo(String invoiceNo) {
        Purchases purchases = null;
        String SQL = "SELECT * FROM purchases WHERE invoice_no = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, invoiceNo);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                purchases = new Purchases(
                        resultSet.getInt("id"),
                        resultSet.getInt("supplier_id"),
                        resultSet.getString("invoice_no"),
                        resultSet.getDate("purchase_date").toLocalDate(),
                        resultSet.getDouble("total_amount"),
                        resultSet.getDate("created_at").toLocalDate()
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return purchases;
    }

    public Boolean deletePurchase(String invoiceno) {
        String SQL = "DELETE FROM purchases WHERE invoice_no = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, invoiceno);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}