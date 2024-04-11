package com.codemasters.accommodateme.service;

import java.util.*;

import com.codemasters.accommodateme.entity.Room;

public interface RoomService {

    String addRoom(Room room, Long resId);

    List<Room> getAllRooms();

    Room updateRoom(Room room, Long id, Long resId);

    Room getRoomById(Long id);

    Optional<Room> getRoomByResidenceId(Long resId);

    // Room getRoomByNumber(String roomNumber);

    // Room findRoomByType(String roomType);

    void deleteRoom(Long resId);
}
