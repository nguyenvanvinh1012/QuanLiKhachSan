package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.repository.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    private ICityRepository cityRepository;
    public List<City> getAllCities(){
        return cityRepository.findAll();
    }
    public List<City> getAllCitiesActive(){
        return cityRepository.findAllByActive();
    }
    public City getCityById(Long id){
        Optional<City> optional = cityRepository.findById(id);
        return optional.orElse(null);
    }
    public void addCity(City city){
        cityRepository.save(city);
    }
    //addCity2 trả về kiểu City ,hàm này để dùng trong upload image
    public City addCity2(City city){
        return cityRepository.save(city);
    }
    public void updateCity(City city){
        cityRepository.save(city);
    }
    public void deleteCity(Long id){
        cityRepository.deleteById(id);
    }
}
