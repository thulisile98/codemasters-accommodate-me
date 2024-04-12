package com.codemasters.accommodateme.service.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.entity.Room;
import com.codemasters.accommodateme.service.RoomService;
import com.codemasters.accommodateme.repository.RoomRepository;
import com.codemasters.accommodateme.repository.ResidenceRepository;

@Service
public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;

    private ResidenceRepository residenceRepository;

    public RoomServiceImpl(RoomRepository roomRepository, ResidenceRepository residenceRepository) {
        this.roomRepository = roomRepository;
        this.residenceRepository = residenceRepository;
    }

    @Override
    public String addRoom(Room room, Long resId) {
        Optional<Residence> resOptional = residenceRepository.findById(resId);

        if (resOptional.isPresent()) {
            Residence residence = resOptional.get();

            room.setResidence(residence);

            roomRepository.save(room);

            return "Room successfully saved";
        } else {
            return "Error saving room: Residence with ID " + resId + " does not exist";
        }
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room updateRoom(Room room, Long id, Long resId) {
        Optional<Room> foundRoom = roomRepository.findById(id);

        if (foundRoom.isPresent()) {

            Room existingRoom = foundRoom.get();

            if (!existingRoom.getResidence().getResidenceId().equals(resId)) {
                throw new RuntimeException("Unauthorized to update this room");
            }

            existingRoom.setRoomNumber(room.getRoomNumber());
            existingRoom.setRoomType(room.getRoomType());

            return roomRepository.save(existingRoom);

        } else {

            throw new RuntimeException("Room with ID " + id + " not found");
        }
    }

    @Override
    public Optional<Room> getRoomByResidenceId(Long resId) {
        Optional<Room> foundRoom = roomRepository.findById(resId);

        if (foundRoom.isPresent()) {
            return foundRoom;
        } else {
            throw new RuntimeException("Rooms with residence ID " + resId + " not found");
        }

    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Room getRoomById(Long id) {
        Optional<Room> foundRoom = roomRepository.findById(id);

        if (foundRoom.isPresent()) {
            return foundRoom.get();
        } else {
            throw new RuntimeException("Room with ID " + id + " not found");
        }
    }

    @Override
    public Optional<Room> getRoomByRoomNumber(String roomNumber) {
        Optional<Room> foundRoom = roomRepository.getRoomByRoomNumber(roomNumber);

        if (foundRoom.isPresent()) {
            return foundRoom;
        } else {
            throw new RuntimeException("Room with number: " + roomNumber + " not found");
        }
    }

    @Override
    public Optional<Room> getRoomByRoomType(String roomType) {
        Optional<Room> foundRoom = roomRepository.getRoomByRoomType(roomType);

        if (foundRoom.isPresent()) {
            return foundRoom;
        } else {
            throw new RuntimeException("Room with number: " + roomType + " not found");
        }
    }

}
