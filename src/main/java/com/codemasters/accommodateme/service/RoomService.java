package com.codemasters.accommodateme.service;

import java.util.*;

import com.codemasters.accommodateme.entity.Room;

public interface RoomService {

    String addRoom(Room room, Long resId);

    List<Room> getAllRooms();

    Room updateRoom(Room room, Long id, Long resId);

    Room getRoomById(Long id);

    Optional<Room> getRoomByResidenceId(Long resId);

    Optional<Room> getRoomByRoomNumber(String roomNumber);

    Optional<Room> getRoomByRoomType(String roomType);

    void deleteRoom(Long resId);
}
