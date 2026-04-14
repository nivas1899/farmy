package dao;

import db.DBConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Dashboard Data Access Object
 * Aggregates data for dashboard metrics
 */
public class DashboardDAO {

    private Connection connection;

    public DashboardDAO() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Get comprehensive dashboard metrics
     */
    public Map<String, Object> getDashboardMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        // Total animals
        metrics.put("totalAnimals", getTotalAnimals());

        // Animal counts by status
        Map<String, Integer> statusCounts = getAnimalCountsByStatus();
        metrics.put("normalCount", statusCounts.getOrDefault("Normal", 0));
        metrics.put("sickCount", statusCounts.getOrDefault("Sick", 0));
        metrics.put("isolatedCount", statusCounts.getOrDefault("Quarantine", 0));
        metrics.put("deadCount", statusCounts.getOrDefault("Deceased", 0));

        // Visitor count today
        metrics.put("visitorsToday", getTodayVisitorCount());

        // Pending vaccinations
        metrics.put("pendingVaccinations", getPendingVaccinationCount());

        return metrics;
    }

    /**
     * Get total animal count
     */
    public int getTotalAnimals() {
        String sql = "SELECT COUNT(*) as total FROM animals";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error getting total animals: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Get animal counts by status
     */
    public Map<String, Integer> getAnimalCountsByStatus() {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("Normal", 0);
        counts.put("Sick", 0);
        counts.put("Quarantine", 0);
        counts.put("Deceased", 0);

        String sql = "SELECT health_status, COUNT(*) as count FROM animals GROUP BY health_status";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String status = rs.getString("health_status");
                int count = rs.getInt("count");
                counts.put(status, count);
            }

        } catch (SQLException e) {
            System.err.println("Error getting animal counts: " + e.getMessage());
            e.printStackTrace();
        }

        return counts;
    }

    /**
     * Get today's visitor count
     */
    public int getTodayVisitorCount() {
        String sql = "SELECT COUNT(*) as total FROM visitors WHERE DATE(entry_time) = CURDATE()";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error getting visitor count: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Get pending vaccination count
     */
    public int getPendingVaccinationCount() {
        String sql = "SELECT COUNT(*) as total FROM vaccinations WHERE status = 'Pending'";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error getting pending vaccination count: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }
}
