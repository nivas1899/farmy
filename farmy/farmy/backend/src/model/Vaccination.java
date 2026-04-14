package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Vaccination Model
 * Represents a vaccination record
 */
public class Vaccination {
    private int vaccinationId;
    private int animalId;
    private String vaccineName;
    private Date vaccinationDate;
    private Date nextDueDate;
    private String administeredBy;
    private String batchNumber;
    private String status; // Scheduled, Completed, Overdue
    private String notes;
    private Timestamp createdAt;

    // Constructors
    public Vaccination() {
    }

    public Vaccination(int animalId, String vaccineName, Date vaccinationDate) {
        this.animalId = animalId;
        this.vaccineName = vaccineName;
        this.vaccinationDate = vaccinationDate;
        this.status = "Scheduled";
    }

    // Getters and Setters
    public int getVaccinationId() {
        return vaccinationId;
    }

    public void setVaccinationId(int vaccinationId) {
        this.vaccinationId = vaccinationId;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public Date getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(Date vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public Date getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(Date nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public String getAdministeredBy() {
        return administeredBy;
    }

    public void setAdministeredBy(String administeredBy) {
        this.administeredBy = administeredBy;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Vaccination{" +
                "vaccinationId=" + vaccinationId +
                ", animalId=" + animalId +
                ", vaccineName='" + vaccineName + '\'' +
                ", vaccinationDate=" + vaccinationDate +
                ", nextDueDate=" + nextDueDate +
                ", status='" + status + '\'' +
                '}';
    }
}
