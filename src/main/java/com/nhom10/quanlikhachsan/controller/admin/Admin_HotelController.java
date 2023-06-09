package com.nhom10.quanlikhachsan.controller.admin;

import com.nhom10.quanlikhachsan.FileUploadUtil;
import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.services.CityService;
import com.nhom10.quanlikhachsan.services.HotelService;
import com.nhom10.quanlikhachsan.services.HotelTypeService;
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
    @Autowired
    private HotelTypeService hotelTypeService;

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
        model.addAttribute("list_type_hotel",hotelTypeService.getAllHotelType());
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

        hotelService.addHotel(hotel, vote, meal, multipartFile1,multipartFile2,multipartFile3,multipartFile4);
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
            model.addAttribute("list_type_hotel",hotelTypeService.getAllHotelType());
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


        hotelService.updateHotel(updateHotel, vote, meal, multipartFile1, multipartFile2, multipartFile3, multipartFile4);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin-hotel";
    }
}
