package com.nhom10.quanlikhachsan.controller.client;

import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.services.CityService;
import com.nhom10.quanlikhachsan.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private CityService cityService;
    @GetMapping("/{id}")
    public String list_hotel(@PathVariable("id") Long id, Model model){
        City city = cityService.getCityById(id);
        if(city == null){
            return "not-found";
        }
        model.addAttribute("city_name", city.getName());
        model.addAttribute("count_hotel", hotelService.countHotelByCityId(id) + 462);
        model.addAttribute("list_hotel", hotelService.getAllHotelActiveIdCity(id));
        return "user/hotel/index";
    }
}
