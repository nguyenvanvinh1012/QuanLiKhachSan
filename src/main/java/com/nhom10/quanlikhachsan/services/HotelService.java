package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.ultils.FileUploadUtil;
import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.repository.IHotelRepository;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    private IHotelRepository hotelRepository;
    public List<Hotel> getAllHotel(){
        return hotelRepository.findAll();
    }
    public List<Hotel> getAllHotelActiveIdCity(Long id){
        return hotelRepository.findAllByActiveIdCity(id);
    }
    public Hotel getHotelByIdRoom(Long id){
        return hotelRepository.findHotelByIdRoom(id);
    }
    public Long countHotelByCityId(Long id){
        return hotelRepository.countHotelsByCityId(id);
    }
    public Hotel getHotelById(Long id){
        Optional<Hotel> optional = hotelRepository.findById(id);
        return optional.orElse(null);
    }
    public void addHotel(Hotel hotel, int vote, int meal,
                         MultipartFile multipartFile1,
                         MultipartFile multipartFile2,
                         MultipartFile multipartFile3,
                         MultipartFile multipartFile4) throws IOException {

        String fileName1 = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
        hotel.setImg1(fileName1);
        hotel.setActive(true);
        hotel.setVote(vote);
        hotel.setMeal(meal);
        Hotel savedHotel = hotelRepository.save(hotel);
        String upLoadDir1 = "hotel-images/" + savedHotel.getId();
        FileUploadUtil.saveFile(upLoadDir1, fileName1, multipartFile1);

        String fileName2 = StringUtils.cleanPath(multipartFile2.getOriginalFilename());
        hotel.setImg2(fileName2);
        String upLoadDir2 = "hotel-images/" + savedHotel.getId();
        FileUploadUtil.saveFile(upLoadDir2, fileName2, multipartFile2);

        String fileName3 = StringUtils.cleanPath(multipartFile3.getOriginalFilename());
        hotel.setImg3(fileName3);
        String upLoadDir3 = "hotel-images/" + savedHotel.getId();
        FileUploadUtil.saveFile(upLoadDir3, fileName3, multipartFile3);

        String fileName4 = StringUtils.cleanPath(multipartFile4.getOriginalFilename());
        hotel.setImg4(fileName4);
        String upLoadDir4 = "hotel-images/" + savedHotel.getId();
        FileUploadUtil.saveFile(upLoadDir4, fileName4, multipartFile4);

        hotelRepository.save(hotel);
    }
    public void updateHotel(@NotNull Hotel hotel, int vote, int meal,
                            MultipartFile multipartFile1,
                            MultipartFile multipartFile2,
                            MultipartFile multipartFile3,
                            MultipartFile multipartFile4) throws IOException {

        Hotel existingHotel = hotelRepository.findById(hotel.getId()).orElse(null);
        Objects.requireNonNull(existingHotel).setName(hotel.getName());
        existingHotel.setCenter(hotel.getCenter());
        existingHotel.setActive(hotel.getActive());
        existingHotel.setAddress(hotel.getAddress());
        existingHotel.setCity(hotel.getCity());
        existingHotel.setHotel_type(hotel.getHotel_type());
        existingHotel.setBordering_sea(hotel.getBordering_sea());
        existingHotel.setPhone(hotel.getPhone());
        existingHotel.setDescription(hotel.getDescription());
        if(vote != 0)
            existingHotel.setVote(hotel.getVote());
        if(meal != 6)
            existingHotel.setMeal(hotel.getMeal());

        if(multipartFile1 != null && !multipartFile1.isEmpty()){
            String fileName1 = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
            existingHotel.setImg1(fileName1);
            String upLoadDir = "hotel-images/" + hotel.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName1, multipartFile1);
        }
        if(multipartFile2 != null && !multipartFile2.isEmpty()){
            String fileName2 = StringUtils.cleanPath(multipartFile2.getOriginalFilename());
            existingHotel.setImg2(fileName2);
            String upLoadDir = "hotel-images/" + hotel.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName2, multipartFile2);
        }
        if(multipartFile3 != null && !multipartFile3.isEmpty()){
            String fileName3 = StringUtils.cleanPath(multipartFile3.getOriginalFilename());
            existingHotel.setImg3(fileName3);
            String upLoadDir = "hotel-images/" + hotel.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName3, multipartFile3);
        }
        if(multipartFile4 != null && !multipartFile4.isEmpty()){
            String fileName4 = StringUtils.cleanPath(multipartFile4.getOriginalFilename());
            existingHotel.setImg4(fileName4);
            String upLoadDir = "hotel-images/" + hotel.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName4, multipartFile4);
        }
        hotelRepository.save(existingHotel);
    }
    public void deleteHotel(Long id){
        hotelRepository.deleteById(id);
    }
}
