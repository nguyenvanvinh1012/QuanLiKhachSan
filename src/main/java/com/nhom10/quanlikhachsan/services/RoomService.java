package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.entity.Room;
import com.nhom10.quanlikhachsan.repository.IRoomRepository;
import com.nhom10.quanlikhachsan.ultils.FileUploadUtil;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private IRoomRepository roomRepository;
    public List<Room> getAllRoom(){
        return roomRepository.findAll();
    }
    public List<Room> getAllRoomByHotelId(Long id) {
        return roomRepository.findAllByRoomIdHotel(id);
    }
    public Long countRoomsByHotelId(Long id){
        return roomRepository.countRoomsByHotelId(id);
    }
    public Room getRoomById(Long id){
        Optional<Room> optional = roomRepository.findById(id);
        return optional.orElse(null);
    }

    public void addRoom (Room room, int beb_type,
                         MultipartFile multipartFile ) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        room.setImage(fileName);
        room.setBed_type(beb_type);
        Room savedRoom  = roomRepository.save(room);
        String upLoadDir = "room-images/" + savedRoom.getId();
        FileUploadUtil.saveFile(upLoadDir, fileName, multipartFile);
        roomRepository.save(room);
    }

    public void updateRoom(@NotNull Room room, int beb_type,
                           MultipartFile multipartFile ) throws IOException{

        Room existingRoom = roomRepository.findById(room.getId()).orElse(null);
        Objects.requireNonNull(existingRoom).setName(room.getName());
        existingRoom.setArea(room.getArea());
        existingRoom.setRent(room.getRent());
        existingRoom.setConvenient(room.getConvenient());
        existingRoom.setDescription(room.getDescription());
        existingRoom.setHotel(room.getHotel());
        if (beb_type != 0)
            existingRoom.setBed_type(room.getBed_type());
        if(multipartFile != null && !multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            existingRoom.setImage(fileName);
            String upLoadDir = "room-images/" + room.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName, multipartFile);
        }
        roomRepository.save(existingRoom);
    }
    public void deleteRoom(Long id){
        roomRepository.deleteById(id);
    }

    public Page<Room> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.roomRepository.findAll(pageable);
    }

    public Page<Room> searchRoom(String keyword, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy));
        return roomRepository.searchRoom(keyword, pageable);
    }
}
