package com.codemasters.accommodateme.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.codemasters.accommodateme.entity.Room;
import com.codemasters.accommodateme.service.RoomService;

@RestController
@RequestMapping("api/vi/rooms")
public class RoomController {

    public RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/save/{resId}")
    public String addRoom(@RequestBody Room room, @PathVariable Long resId) {
        return roomService.addRoom(room, resId);
    }

    @GetMapping("/all")
    public List<Room> getAllRooms() {

        return roomService.getAllRooms();
    }

    @PutMapping("/update/{id}/resId/{resId}")
    public Room updateRoom(@RequestBody Room location, @PathVariable Long id, @PathVariable Long resId) {

        try {

            return roomService.updateRoom(location, id, resId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while updating location", e);
        }
    }

    @GetMapping("/findRoomById/{id}")
    public Room getRoomById(@PathVariable Long id) {

        return roomService.getRoomById(id);
    }

    @GetMapping("findRoomByResId/resId/{resId}")
    public Optional<Room> getRoomByResidenceId(@PathVariable Long resId) {

        return roomService.getRoomByResidenceId(resId);
    }

    @GetMapping("findRoomByType/{roomType}")
    public Optional<Room> getRoomByRoomType(@PathVariable String roomType) {

        return roomService.getRoomByRoomType(roomType);
    }

    @GetMapping("findRoomByNumber/{roomNumber}")
    public Optional<Room> getRoomByRoomNumber(@PathVariable String roomNumber) {

        return roomService.getRoomByRoomNumber(roomNumber);
    }

}
