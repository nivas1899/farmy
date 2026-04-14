package dao;

import db.DBConnection;
import model.Animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Animal Data Access Object
 * Handles animal CRUD operations and status tracking
 */
public class AnimalDAO {

    private Connection connection;

    public AnimalDAO() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Add a new animal
     */
    public boolean addAnimal(Animal animal) {
        String sql = "INSERT INTO animals (batch_id, tag_number, species, breed, birth_date, gender, health_status, weight, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, animal.getBatchId());
            stmt.setString(2, animal.getTagNumber());
            stmt.setString(3, animal.getSpecies());
            stmt.setString(4, animal.getBreed());
            stmt.setDate(5, animal.getBirthDate() != null ? new java.sql.Date(animal.getBirthDate().getTime()) : null);
            stmt.setString(6, animal.getGender());
            stmt.setString(7, animal.getHealthStatus());
            stmt.setBigDecimal(8, animal.getWeight());
            stmt.setString(9, animal.getNotes());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding animal: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all animals
     */
    public List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals ORDER BY created_at DESC";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Animal animal = extractAnimalFromResultSet(rs);
                animals.add(animal);
            }

        } catch (SQLException e) {
            System.err.println("Error getting animals: " + e.getMessage());
            e.printStackTrace();
        }

        return animals;
    }

    /**
     * Get animals by batch ID
     */
    public List<Animal> getAnimalsByBatch(int batchId) {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals WHERE batch_id = ? ORDER BY tag_number";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, batchId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Animal animal = extractAnimalFromResultSet(rs);
                animals.add(animal);
            }

        } catch (SQLException e) {
            System.err.println("Error getting animals by batch: " + e.getMessage());
            e.printStackTrace();
        }

        return animals;
    }

    /**
     * Update animal status
     */
    public boolean updateAnimalStatus(int animalId, String health_status) {
        String sql = "UPDATE animals SET health_status = ? WHERE animal_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, health_status);
            stmt.setInt(2, animalId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating animal status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete an animal
     */
    public boolean deleteAnimal(int animalId) {
        String sql = "DELETE FROM animals WHERE animal_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, animalId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting animal: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get animal counts by status (for dashboard)
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
     * Helper method to extract Animal from ResultSet
     */
    private Animal extractAnimalFromResultSet(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setAnimalId(rs.getInt("animal_id"));
        animal.setBatchId(rs.getInt("batch_id"));
        animal.setTagNumber(rs.getString("tag_number"));
        animal.setSpecies(rs.getString("species"));
        animal.setBreed(rs.getString("breed"));
        animal.setBirthDate(rs.getDate("birth_date"));
        animal.setGender(rs.getString("gender"));
        animal.setHealthStatus(rs.getString("health_status"));
        animal.setWeight(rs.getBigDecimal("weight"));
        animal.setNotes(rs.getString("notes"));
        animal.setLastCheckup(rs.getDate("last_checkup"));
        animal.setCreatedAt(rs.getTimestamp("created_at"));
        animal.setUpdatedAt(rs.getTimestamp("updated_at"));
        return animal;
    }
}
