/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.mapper;

import com.smartcampus.exception.SmartCampusExceptions;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Danuka Dulanjan
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        
        if (exception instanceof SmartCampusExceptions.RoomNotEmptyException){
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }
        
        if (exception instanceof SmartCampusExceptions.LinkedResourceNotFoundException){
            return Response.status(422).entity(response).build();
        }
        
        if (exception instanceof SmartCampusExceptions.SensorUnavailableException){
            return Response.status(Response.Status.FORBIDDEN).entity(response).build();
        }
        
        response.put("erroe", "An internal Server error occurred. ");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
    }   
}
