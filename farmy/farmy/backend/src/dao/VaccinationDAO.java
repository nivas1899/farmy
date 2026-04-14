package dao;

import db.DBConnection;
import model.Vaccination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Vaccination Data Access Object
 * Handles vaccination schedule CRUD operations
 */
public class VaccinationDAO {

    private Connection connection;

    public VaccinationDAO() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Add a new vaccination schedule
     */
    public boolean addVaccination(Vaccination vaccination) {
        String sql = "INSERT INTO vaccinations (animal_id, vaccine_name, vaccination_date, next_due_date, administered_by, batch_number, status, notes) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vaccination.getAnimalId());
            stmt.setString(2, vaccination.getVaccineName());
            stmt.setDate(3, vaccination.getVaccinationDate());
            stmt.setDate(4, vaccination.getNextDueDate());
            stmt.setString(5, vaccination.getAdministeredBy());
            stmt.setString(6, vaccination.getBatchNumber());
            stmt.setString(7, vaccination.getStatus() != null ? vaccination.getStatus() : "Scheduled");
            stmt.setString(8, vaccination.getNotes());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding vaccination: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all vaccinations
     */
    public List<Vaccination> getAllVaccinations() {
        List<Vaccination> vaccinations = new ArrayList<>();
        String sql = "SELECT * FROM vaccinations ORDER BY vaccination_date DESC";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vaccination vaccination = extractVaccinationFromResultSet(rs);
                vaccinations.add(vaccination);
            }

        } catch (SQLException e) {
            System.err.println("Error getting vaccinations: " + e.getMessage());
            e.printStackTrace();
        }

        return vaccinations;
    }

    /**
     * Get pending vaccinations
     */
    public List<Vaccination> getPendingVaccinations() {
        List<Vaccination> vaccinations = new ArrayList<>();
        String sql = "SELECT * FROM vaccinations WHERE status = 'Scheduled' ORDER BY vaccination_date";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vaccination vaccination = extractVaccinationFromResultSet(rs);
                vaccinations.add(vaccination);
            }

        } catch (SQLException e) {
            System.err.println("Error getting pending vaccinations: " + e.getMessage());
            e.printStackTrace();
        }

        return vaccinations;
    }

    /**
     * Update vaccination status
     */
    public boolean updateVaccinationStatus(int vaccinationId, String status) {
        String sql = "UPDATE vaccinations SET status = ? WHERE vaccination_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, vaccinationId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating vaccination status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete a vaccination schedule
     */
    public boolean deleteVaccination(int vaccinationId) {
        String sql = "DELETE FROM vaccinations WHERE vaccination_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vaccinationId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting vaccination: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get count of pending vaccinations
     */
    public int getPendingVaccinationCount() {
        String sql = "SELECT COUNT(*) as total FROM vaccinations WHERE status = 'Scheduled'";

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

    /**
     * Helper method to extract Vaccination from ResultSet
     */
    private Vaccination extractVaccinationFromResultSet(ResultSet rs) throws SQLException {
        Vaccination vaccination = new Vaccination();
        vaccination.setVaccinationId(rs.getInt("vaccination_id"));
        vaccination.setAnimalId(rs.getInt("animal_id"));
        vaccination.setVaccineName(rs.getString("vaccine_name"));
        vaccination.setVaccinationDate(rs.getDate("vaccination_date"));
        vaccination.setNextDueDate(rs.getDate("next_due_date"));
        vaccination.setAdministeredBy(rs.getString("administered_by"));
        vaccination.setBatchNumber(rs.getString("batch_number"));
        vaccination.setStatus(rs.getString("status"));
        vaccination.setNotes(rs.getString("notes"));
        vaccination.setCreatedAt(rs.getTimestamp("created_at"));
        return vaccination;
    }
}
