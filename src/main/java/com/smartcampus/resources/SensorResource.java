/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resources;


import com.smartcampus.database.DatabaseClass;
import com.smartcampus.exception.SmartCampusExceptions.LinkedResourceNotFoundException;
import com.smartcampus.model.Sensor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Danuka Dulanjan
 */
@Path("/sensors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SensorResource {
    private Map<String, Sensor> sensors = DatabaseClass.getSensors();

    @GET
    public List<Sensor> getSensors(@QueryParam("type") String type) { // Filtered Retrieval [cite: 134-135]
        if (type != null && !type.trim().isEmpty()) {
            return sensors.values().stream()
                    .filter(s -> type.equalsIgnoreCase(s.getType()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>(sensors.values());
    }

    @POST
    public Response addSensor(Sensor sensor) {
        if (!DatabaseClass.getRooms().containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room ID does not exist."); // 422 Dependency Validation [cite: 155-156]
        }
        sensors.put(sensor.getId(), sensor);
        DatabaseClass.getRooms().get(sensor.getRoomId()).getSensorIds().add(sensor.getId());
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    // Sub-Resource Locator Pattern [cite: 140-141]
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingsResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}
