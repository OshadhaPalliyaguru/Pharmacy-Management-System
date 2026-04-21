package repository.impl;

import db.DBConnection;
import model.dto.PurchaseTableModel;
import repository.DashboardRepository;

import java.sql.*;
import java.util.*;

public class DashboardRepositoryImpl implements DashboardRepository {

    @Override
    public Map<String, Integer> getMedicineStockData() {
        Map<String, Integer> data = new HashMap<>();
        String sql = "SELECT name, quantity FROM medicines";
        try (Connection con = DBConnection.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                data.put(rs.getString("name"), rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public Map<String, Map<String, Integer>> getMedicineStockTrend() {
        Map<String, Map<String, Integer>> mainData = new HashMap<>();
        try (Connection con = DBConnection.getInstance().getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet rsMedicine = stmt.executeQuery("SELECT DISTINCT name FROM medicines");
            List<String> medicineNames = new ArrayList<>();
            while (rsMedicine.next()) {
                medicineNames.add(rsMedicine.getString("name"));
            }
            rsMedicine.close(); // Close to avoid locking

            // Note: This loop inside a repo is still not ideal performance-wise (N+1),
            // but we are preserving your friend's logic structure, just moving it.
            for (String name : medicineNames) {
                Map<String, Integer> dateStockMap = new LinkedHashMap<>();
                String stockQuery = "SELECT updated_at, quantity FROM medicines WHERE name=? ORDER BY updated_at";
                try (PreparedStatement ps = con.prepareStatement(stockQuery)) {
                    ps.setString(1, name);
                    ResultSet rsStock = ps.executeQuery();
                    while (rsStock.next()) {
                        String date = rsStock.getString("updated_at").substring(0, 10);
                        dateStockMap.put(date, rsStock.getInt("quantity"));
                    }
                }
                mainData.put(name, dateStockMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mainData;
    }

    @Override
    public int getTotalMedicineStock() {
        String sql = "SELECT SUM(quantity) AS total_stock FROM medicines";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("total_stock");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double getTotalSales() {
        String sql = "SELECT SUM(total_amount) AS total_sales FROM sales";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble("total_sales");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public List<PurchaseTableModel> getRecentPurchases() {
        List<PurchaseTableModel> list = new ArrayList<>();
        String sql = "SELECT id, created_at, total_amount FROM purchases ORDER BY created_at DESC LIMIT 10";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new PurchaseTableModel(
                        rs.getInt("id"),
                        rs.getString("created_at").substring(0, 19),
                        "Purchase",
                        rs.getDouble("total_amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int getLowStockCount() {
        String sql = "SELECT COUNT(*) AS low_count FROM medicines WHERE quantity < 100";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("low_count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}