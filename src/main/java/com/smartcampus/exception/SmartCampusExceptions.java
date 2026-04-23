/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception;

/**
 *
 * @author Danuka Dulanjan
 */
public class SmartCampusExceptions {
    
    public static class RoomNotEmptyException extends RuntimeException {
        public RoomNotEmptyException(String message) { super(message); }
    }
    
    public static class LinkedResourceNotFoundException extends RuntimeException {
        public LinkedResourceNotFoundException(String message) { super(message); }
    }
    
    public static class SensorUnavailableException extends RuntimeException {
        public SensorUnavailableException(String message) { super(message); }
    }
}
