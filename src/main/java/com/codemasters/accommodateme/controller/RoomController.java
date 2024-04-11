package com.codemasters.accommodateme.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemasters.accommodateme.entity.Room;
import com.codemasters.accommodateme.service.RoomService;

@RestController
@RequestMapping("api/vi/rooms")
public class RoomController {

    public RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/save/resId/{resId}")
    public String addRoom(@RequestBody Room room, @PathVariable Long resId) {
        return roomService.addRoom(room, resId);
    }

    @GetMapping("/all")
    public List<Room> getAllRooms() {

        return roomService.getAllRooms();
    }

    @GetMapping("/find/{id}")
    public Room getRoomById(@PathVariable Long id) {

        return roomService.getRoomById(id);
    }

    @GetMapping("find/resid/{resId}")
    public Optional<Room> getRoomByResidenceId(@PathVariable Long resId) {

        return roomService.getRoomByResidenceId(resId);
    }

}
