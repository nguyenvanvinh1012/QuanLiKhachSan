package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.entity.HotelType;
import com.nhom10.quanlikhachsan.repository.IHotelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelTypeService {
    @Autowired
    private IHotelTypeRepository hotelTypeRepository;
    public List<HotelType> getAllHotelType(){
        return hotelTypeRepository.findAll();
    }
    public HotelType getHotelTypeById(Long id){
        Optional<HotelType> optional = hotelTypeRepository.findById(id);
        return optional.orElse(null);
    }
    public void addHotelType (HotelType hotelType){
        hotelTypeRepository.save(hotelType);
    }
    //hàm này để dùng trong upload image
    public HotelType addHotelType2 (HotelType hotelType){
        return hotelTypeRepository.save(hotelType);
    }
    public void updateHotelType(HotelType hotelType){
        hotelTypeRepository.save(hotelType);
    }
    public void deleteHotelType(Long id){
        hotelTypeRepository.deleteById(id);
    }
}
