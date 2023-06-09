package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.entity.Room;
import com.nhom10.quanlikhachsan.repository.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private IRoomRepository roomRepository;
    public List<Room> getAllRoom(){
        return roomRepository.findAll();
    }
    public Room getRoomById(Long id){
        Optional<Room> optional = roomRepository.findById(id);
        return optional.orElse(null);
    }
    public void addRoom (Room room){
        roomRepository.save(room);
    }
    //hàm này để dùng trong upload image
    public Room addRoom2 (Room room){
        return roomRepository.save(room);
    }
    public void updateRoom(Room room){
        roomRepository.save(room);
    }
    public void deleteRoom(Long id){
        roomRepository.deleteById(id);
    }
}
