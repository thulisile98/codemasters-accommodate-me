package com.codemasters.accommodateme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codemasters.accommodateme.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
