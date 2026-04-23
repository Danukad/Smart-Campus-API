/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.model;

/**
 *
 * @author Danuka Dulanjan
 */
public class Sensor  {
    private String id;
    private String type; // e.g., "Temperature", "Occupancy", "CO2"
    private String status; // e.g., "ACTIVE", "MAINTENANCE", "OFFLINE"
    private double currentValue; 
    private String roomId;
    
    public Sensor() {}
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getStatus() { return status; }
    public void setStatus(String status ) { this.status = status; }
    
    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double currentValue) { this.currentValue = currentValue; }
    
    public String gestRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public Object getRoomId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
