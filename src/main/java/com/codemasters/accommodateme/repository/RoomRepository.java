package com.codemasters.accommodateme.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codemasters.accommodateme.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> getRoomByRoomType(String roomType);

    Optional<Room> getRoomByRoomNumber(String roomNumber);
}
