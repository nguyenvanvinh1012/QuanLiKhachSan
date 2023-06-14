package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.ultils.FileUploadUtil;
import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.repository.ICityRepository;
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
    public City searchCity(String keyword){
        City city = cityRepository.searchCity(keyword);
        if(city == null)
            return null;
        return city;
    }
    public City getCityByIdHotel(Long id){
        return cityRepository.findCitylByIdHotel(id);
    }
    public void addCity(City city, MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        city.setImage(fileName);
        city.setActive(true);
        City savedCity  = cityRepository.save(city);
        String upLoadDir = "city-images/" + savedCity.getId();
        FileUploadUtil.saveFile(upLoadDir, fileName, multipartFile);
        cityRepository.save(city);
    }

    public void updateCity(City city, MultipartFile multipartFile) throws IOException {
        City existingCity = cityRepository.findById(city.getId()).orElse(null);
        Objects.requireNonNull(existingCity).setName(city.getName());
        existingCity.setDescription(city.getDescription());
        existingCity.setActive(city.getActive());

        if(multipartFile != null && !multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            existingCity.setImage(fileName);
            String upLoadDir = "city-images/" + city.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName, multipartFile);
        }
        cityRepository.save(existingCity);
    }
    public void deleteCity(Long id){
        cityRepository.deleteById(id);
    }

    public Page<City> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.cityRepository.findAll(pageable);
    }

    public Page<City> searchCity2(String keyword, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy));
        return cityRepository.searchCity2(keyword, pageable);
    }
}
