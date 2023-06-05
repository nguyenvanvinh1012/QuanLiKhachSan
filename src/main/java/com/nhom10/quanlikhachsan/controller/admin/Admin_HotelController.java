package com.nhom10.quanlikhachsan.controller.admin;

import com.nhom10.quanlikhachsan.FileUploadUtil;
import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.services.CityService;
import com.nhom10.quanlikhachsan.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/admin-hotel")
public class Admin_HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private CityService cityService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("list_hotel", hotelService.getAllHotel());
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }
        return "admin/hotel/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("list_city", cityService.getAllCities());
        model.addAttribute("hotel", new Hotel());
        return "admin/hotel/add";
    }

    @PostMapping("/add")
    public String addHotel(@ModelAttribute("hotel") Hotel hotel,
                           @RequestParam("vote") Integer vote,
                           @RequestParam("meal") Integer meal,
                           @RequestParam("image1") MultipartFile multipartFile1,
                           @RequestParam("image2") MultipartFile multipartFile2,
                           @RequestParam("image3") MultipartFile multipartFile3,
                           @RequestParam("image4") MultipartFile multipartFile4,
                           RedirectAttributes redirectAttributes) throws IOException {

        String fileName1 = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
        hotel.setImg1(fileName1);
        hotel.setActive(true);
        hotel.setVote(vote);
        hotel.setMeal(meal);
        Hotel savedHotel = hotelService.addHotel2(hotel);
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
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin-hotel";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Hotel editHotel = null;
        for(Hotel hotel: hotelService.getAllHotel()){
            if(hotel.getId() == id){
                editHotel = hotel;
            }
        }
        if(editHotel != null){
            model.addAttribute("hotel", editHotel);
            model.addAttribute("list_city", cityService.getAllCities());
            return "admin/hotel/edit";
        }else {
            return "not-found";
        }
    }
    @PostMapping("/edit")
    public String edit(@ModelAttribute("hotel") Hotel updateHotel,
                       @RequestParam("vote") Integer vote,
                       @RequestParam("meal") Integer meal,
                       @RequestParam("image1") MultipartFile multipartFile1,
                       @RequestParam("image2") MultipartFile multipartFile2,
                       @RequestParam("image3") MultipartFile multipartFile3,
                       @RequestParam("image4") MultipartFile multipartFile4,
                       RedirectAttributes redirectAttributes)throws IOException{

        Hotel hotel = hotelService.getHotelById(updateHotel.getId());
        hotel.setName(updateHotel.getName());
        hotel.setDescription(updateHotel.getDescription());
        hotel.setActive(updateHotel.getActive());
        hotel.setAddress(updateHotel.getAddress());
        hotel.setCenter(updateHotel.getCenter());
        hotel.setPhone(updateHotel.getPhone());
        hotel.setBordering_sea(updateHotel.getBordering_sea());
        hotel.setCity(updateHotel.getCity());
        if(vote != 0)
            hotel.setVote(vote);
        if(meal != 6)
            hotel.setMeal(meal);

        if(multipartFile1 != null && !multipartFile1.isEmpty()){
            String fileName1 = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
            hotel.setImg1(fileName1);
            String upLoadDir = "hotel-images/" + hotel.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName1, multipartFile1);
        }
        if(multipartFile2 != null && !multipartFile2.isEmpty()){
            String fileName2 = StringUtils.cleanPath(multipartFile2.getOriginalFilename());
            hotel.setImg2(fileName2);
            String upLoadDir = "hotel-images/" + hotel.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName2, multipartFile2);
        }
        if(multipartFile3 != null && !multipartFile3.isEmpty()){
            String fileName3 = StringUtils.cleanPath(multipartFile3.getOriginalFilename());
            hotel.setImg3(fileName3);
            String upLoadDir = "hotel-images/" + hotel.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName3, multipartFile3);
        }
        if(multipartFile4 != null && !multipartFile4.isEmpty()){
            String fileName4 = StringUtils.cleanPath(multipartFile4.getOriginalFilename());
            hotel.setImg4(fileName4);
            String upLoadDir = "hotel-images/" + hotel.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName4, multipartFile4);
        }

        hotelService.updateCity(hotel);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin-hotel";
    }
}
