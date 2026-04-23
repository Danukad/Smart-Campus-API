/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resources;

import com.smartcampus.database.DatabaseClass;
import com.smartcampus.model.Room;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/rooms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

/**
 *
 * @author Danuka Dulanjan
 */

public class RoomResource {
    
    private Map<String, Room> rooms = DatabaseClass.getRooms();
    
    @GET
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }
    
    @POST
    public Response addRooms(Room room) {
        rooms.put(room.getId(), room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }
    
    @GET
    @Path("/{roomId}")
    public Room getRoom(@PathParam("roomId") String id) {
        return rooms.get(id);
    }
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String id) {
        Room room = rooms.get(id);
        if (room != null) {
            if (!room.getSensorIds().isEmpty()) {
                throw new com.smartcampus.exception.SmartCampusExceptions.RoomNotEmptyException("Room cannot be deleted as it contains active sensors.");
            }
            rooms.remove(id);
        }
    return Response.noContent().build();
    }
    
}
