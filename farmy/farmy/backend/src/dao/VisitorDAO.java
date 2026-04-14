package dao;

import db.DBConnection;
import model.Visitor;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Visitor Data Access Object
 * Handles visitor biosecurity tracking
 */
public class VisitorDAO {

    private Connection connection;

    public VisitorDAO() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Add a new visitor entry
     */
    public boolean addVisitor(Visitor visitor) {
        String sql = "INSERT INTO visitors (visitor_name, organization, purpose, visit_date, exit_date, temperature, biosecurity_compliance, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, visitor.getVisitorName());
            stmt.setString(2, visitor.getOrganization());
            stmt.setString(3, visitor.getPurpose());
            stmt.setTimestamp(4, visitor.getVisitDate());
            stmt.setTimestamp(5, visitor.getExitDate());
            stmt.setBigDecimal(6, visitor.getTemperature());
            stmt.setString(7, visitor.getBiosecurityCompliance() != null ? visitor.getBiosecurityCompliance() : "Yes");
            stmt.setString(8, visitor.getNotes());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding visitor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all visitors
     */
    public List<Visitor> getAllVisitors() {
        List<Visitor> visitors = new ArrayList<>();
        String sql = "SELECT * FROM visitors ORDER BY visit_date DESC";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Visitor visitor = extractVisitorFromResultSet(rs);
                visitors.add(visitor);
            }

        } catch (SQLException e) {
            System.err.println("Error getting visitors: " + e.getMessage());
            e.printStackTrace();
        }

        return visitors;
    }

    /**
     * Get today's visitors
     */
    public List<Visitor> getVisitorsToday() {
        List<Visitor> visitors = new ArrayList<>();
        String sql = "SELECT * FROM visitors WHERE DATE(visit_date) = CURDATE() ORDER BY visit_date DESC";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Visitor visitor = extractVisitorFromResultSet(rs);
                visitors.add(visitor);
            }

        } catch (SQLException e) {
            System.err.println("Error getting today's visitors: " + e.getMessage());
            e.printStackTrace();
        }

        return visitors;
    }

    /**
     * Get count of today's visitors
     */
    public int getTodayVisitorCount() {
        String sql = "SELECT COUNT(*) as total FROM visitors WHERE DATE(visit_date) = CURDATE()";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error getting today's visitor count: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Helper method to extract Visitor from ResultSet
     */
    private Visitor extractVisitorFromResultSet(ResultSet rs) throws SQLException {
        Visitor visitor = new Visitor();
        visitor.setVisitorId(rs.getInt("visitor_id"));
        visitor.setVisitorName(rs.getString("visitor_name"));
        visitor.setOrganization(rs.getString("organization"));
        visitor.setPurpose(rs.getString("purpose"));
        visitor.setVisitDate(rs.getTimestamp("visit_date"));
        visitor.setExitDate(rs.getTimestamp("exit_date"));
        visitor.setTemperature(rs.getBigDecimal("temperature"));
        visitor.setBiosecurityCompliance(rs.getString("biosecurity_compliance"));
        visitor.setNotes(rs.getString("notes"));
        visitor.setCreatedAt(rs.getTimestamp("created_at"));
        return visitor;
    }
}
