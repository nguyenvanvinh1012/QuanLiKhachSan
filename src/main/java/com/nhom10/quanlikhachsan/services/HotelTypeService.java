package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.entity.HotelType;
import com.nhom10.quanlikhachsan.repository.IHotelTypeRepository;
import com.nhom10.quanlikhachsan.ultils.FileUploadUtil;
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
    public void addHotelType (HotelType hotelType, MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        hotelType.setImage(fileName);
        hotelType.setActive(true);
        HotelType savedhotelType = hotelTypeRepository.save(hotelType);
        String upLoadDir = "hotelType-images/" + savedhotelType.getId();
        FileUploadUtil.saveFile(upLoadDir, fileName, multipartFile);
        hotelTypeRepository.save(hotelType);
    }

    public void updateHotelType (HotelType hotelType,MultipartFile multipartFile) throws IOException {
        HotelType existinghotelType = hotelTypeRepository.findById(hotelType.getId()).orElse(null);
        Objects.requireNonNull(existinghotelType).setName(hotelType.getName());
        existinghotelType.setDescription(hotelType.getDescription());
        existinghotelType.setActive(hotelType.getActive());
        if(multipartFile != null && !multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            existinghotelType.setImage(fileName);
            String upLoadDir = "hotelType-images/" + hotelType.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName, multipartFile);
        }
        hotelTypeRepository.save(existinghotelType);
    }
    public void deleteHotelType(Long id){
        hotelTypeRepository.deleteById(id);
    }

    public Page<HotelType> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.hotelTypeRepository.findAll(pageable);
    }

    public Page<HotelType> searchHotelType(String keyword, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy));
        return hotelTypeRepository.searchHotelType(keyword, pageable);
    }
    public List<HotelType> getAllHotelTypeActive(){
        return hotelTypeRepository.findAllHotelTypeActive();
    }
}

