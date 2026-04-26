package repository.impl;

import db.DBConnection;
import model.dto.Medicine;
import repository.MedicineRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineRepositoryImpl implements MedicineRepository {

    public boolean addMedicine(Medicine medicine) {
        String sql = "INSERT INTO medicines(name,brand,batch_no,expiry_date,quantity,unit_price, reorder_level ,created_at,updated_at)VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1, medicine.getName());
            preparedStatement.setString(2, medicine.getBrand());
            preparedStatement.setString(3, medicine.getBatch_no());
            preparedStatement.setDate(4, java.sql.Date.valueOf(medicine.getExpiry_date()));
            preparedStatement.setInt(5, medicine.getQuantity());
            preparedStatement.setDouble(6, medicine.getUnit_price());
            preparedStatement.setInt(7, medicine.getReorder_level());
            preparedStatement.setTimestamp(8, java.sql.Timestamp.valueOf(medicine.getCreated_at()));
            preparedStatement.setTimestamp(9, java.sql.Timestamp.valueOf(medicine.getUpdated_at()));
            return preparedStatement.executeUpdate() > 0;


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public ResultSet searchByName(String name) throws SQLException {
        Medicine medicine = null;

        Connection connection = DBConnection.getInstance().getConnection();
        String Sql = "SELECT * FROM medicines WHERE name= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();


        return resultSet;
    }

    public List<Medicine> getAllMedicines() {
        Medicine medicine1 = null;
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM medicines";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                medicine1 = new Medicine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("batch_no"),
                        rs.getDate("expiry_date").toLocalDate(),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price"),
                        rs.getInt("reorder_level"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                list.add(medicine1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateMedicine(Medicine medicine) {
        String sql = "UPDATE medicines SET name=?, brand=?, batch_no=?, expiry_date=?, quantity=?, unit_price=?, reorder_level=?, updated_at=? WHERE id=?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, medicine.getName());
            ps.setString(2, medicine.getBrand());
            ps.setString(3, medicine.getBatch_no());
            ps.setDate(4, java.sql.Date.valueOf(medicine.getExpiry_date()));
            ps.setInt(5, medicine.getQuantity());
            ps.setDouble(6, medicine.getUnit_price());
            ps.setInt(7, medicine.getReorder_level());
            ps.setTimestamp(8, java.sql.Timestamp.valueOf(medicine.getUpdated_at()));
            ps.setInt(9, medicine.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Medicine getMedicineByName(String name) throws SQLException {
        String sql = "SELECT * FROM medicines WHERE name=?";
        try(Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new Medicine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("batch_no"),
                        rs.getDate("expiry_date").toLocalDate(),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price"),
                        rs.getInt("reorder_level"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        }
        return null;
    }
    public boolean deleteMedicine(String name)  {
        String sql = "DELETE FROM medicines WHERE name = ?";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, name);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateMedicineQty(String medicineId, int quantity) throws SQLException {
        String SQL="UPDATE medicines SET quantity= quantity-? WHERE id =? ";
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1,quantity);
            preparedStatement.setInt(2, Integer.parseInt(medicineId));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean UpdateSupplierQyt(String medicineId, int Qty) {
        String sql = "UPDATE medicines SET quantity = quantity + ? WHERE id = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Qty);
            ps.setString(2, medicineId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}


