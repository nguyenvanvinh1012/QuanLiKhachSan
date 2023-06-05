package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.repository.ICityRepository;
import com.nhom10.quanlikhachsan.repository.IHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Long countHotelByCityId(Long id){
        return hotelRepository.countHotelsByCityId(id);
    }
    public Hotel getHotelById(Long id){
        Optional<Hotel> optional = hotelRepository.findById(id);
        return optional.orElse(null);
    }
    public void addHotel(Hotel hotel){
        hotelRepository.save(hotel);
    }
    //addCity2 trả về kiểu City ,hàm này để dùng trong upload image
    public Hotel addHotel2(Hotel hotel){
        return hotelRepository.save(hotel);
    }
    public void updateCity(Hotel hotel){
        hotelRepository.save(hotel);
    }
    public void deleteHotel(Long id){
        hotelRepository.deleteById(id);
    }
}
