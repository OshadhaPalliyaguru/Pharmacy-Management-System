package repository.impl;

import db.DBConnection;
import model.dto.Sales;
import repository.SalesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SaleRepositoryImpl implements SalesRepository {
    @Override
    public int addSales(Sales Sales) {
        String SQL="INSERT INTO sales(invoice_no,sale_date,cashier,total_amount,created_at)VALUES(?,?,?,?,?)";
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, java.sql.Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, Sales.getInvoice_no());
            preparedStatement.setObject(2, Sales.getSale_date());
            preparedStatement.setString(3, Sales.getCashier());
            preparedStatement.setDouble(4, Sales.getTotal_amount());
            preparedStatement.setObject(5, Sales.getCreated_at());

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public List<Sales> getAllSales() {
        Sales sales = null;
        ArrayList<Sales> sales1 = new ArrayList<>();
        String SQL = "SELECT * FROM sales";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sales = new Sales(
                        resultSet.getInt("id"),
                        resultSet.getString("invoice_no"),
                        resultSet.getTimestamp("sale_date") != null
                                ? resultSet.getTimestamp("sale_date").toLocalDateTime()
                                : null,
                        resultSet.getString("cashier"),
                        resultSet.getDouble("total_amount"),
                        resultSet.getTimestamp("created_at") != null
                                ? resultSet.getTimestamp("created_at").toLocalDateTime()
                                : null
                );
                sales1.add(sales);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return sales1;
    }
}
