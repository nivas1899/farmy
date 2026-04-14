package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Animal Model Class
 * Represents an individual animal in the farm
 */
public class Animal {
    private int animalId;
    private int batchId;
    private String tagNumber;
    private String species;
    private String breed;
    private Date birthDate;
    private String gender;
    private String healthStatus;
    private BigDecimal weight;
    private String notes;
    private Date lastCheckup;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Animal() {
    }

    public Animal(int animalId, int batchId, String tagNumber, String species, String breed,
            Date birthDate, String gender, String healthStatus, BigDecimal weight) {
        this.animalId = animalId;
        this.batchId = batchId;
        this.tagNumber = tagNumber;
        this.species = species;
        this.breed = breed;
        this.birthDate = birthDate;
        this.gender = gender;
        this.healthStatus = healthStatus;
        this.weight = weight;
    }

    // Getters and Setters
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getLastCheckup() {
        return lastCheckup;
    }

    public void setLastCheckup(Date lastCheckup) {
        this.lastCheckup = lastCheckup;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "animalId=" + animalId +
                ", batchId=" + batchId +
                ", tagNumber='" + tagNumber + '\'' +
                ", species='" + species + '\'' +
                ", breed='" + breed + '\'' +
                ", healthStatus='" + healthStatus + '\'' +
                ", weight=" + weight +
                '}';
    }
}
