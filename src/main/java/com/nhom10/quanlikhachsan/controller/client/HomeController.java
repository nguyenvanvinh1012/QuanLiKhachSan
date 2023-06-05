package com.nhom10.quanlikhachsan.controller.client;

import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    CityService cityService;
    @RequestMapping("/")
    public String home(Model model){
        City HCMCity = cityService.getCityById(1L);
        City HaNoiCity = cityService.getCityById(2L);
        City DNCity = cityService.getCityById(3L);
        City HueCity = cityService.getCityById(4L);
        City HoiAnCity = cityService.getCityById(5L);

        model.addAttribute("list_city", cityService.getAllCitiesActive());
        model.addAttribute("HoChiMinh",HCMCity);
        model.addAttribute("HaNoi",HaNoiCity);
        model.addAttribute("DaNang",DNCity);
        model.addAttribute("Hue",HueCity);
        model.addAttribute("HoiAn",HoiAnCity);
        return "user/home/index";
    }
}

