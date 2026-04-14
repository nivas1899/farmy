package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Visitor Model
 * Represents a farm visitor for biosecurity tracking
 */
public class Visitor {
    private int visitorId;
    private String visitorName;
    private String organization;
    private String purpose;
    private Timestamp visitDate;
    private Timestamp exitDate;
    private BigDecimal temperature;
    private String biosecurityCompliance; // Yes, No, Partial
    private String notes;
    private Timestamp createdAt;

    // Constructors
    public Visitor() {
    }

    public Visitor(String visitorName, String organization, String purpose, Timestamp visitDate) {
        this.visitorName = visitorName;
        this.organization = organization;
        this.purpose = purpose;
        this.visitDate = visitDate;
        this.biosecurityCompliance = "Yes";
    }

    // Getters and Setters
    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Timestamp getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Timestamp visitDate) {
        this.visitDate = visitDate;
    }

    public Timestamp getExitDate() {
        return exitDate;
    }

    public void setExitDate(Timestamp exitDate) {
        this.exitDate = exitDate;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public String getBiosecurityCompliance() {
        return biosecurityCompliance;
    }

    public void setBiosecurityCompliance(String biosecurityCompliance) {
        this.biosecurityCompliance = biosecurityCompliance;
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
        return "Visitor{" +
                "visitorId=" + visitorId +
                ", visitorName='" + visitorName + '\'' +
                ", organization='" + organization + '\'' +
                ", purpose='" + purpose + '\'' +
                ", visitDate=" + visitDate +
                ", biosecurityCompliance='" + biosecurityCompliance + '\'' +
                '}';
    }
}
