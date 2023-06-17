package com.nhom10.quanlikhachsan.controller.client;

import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.services.CityService;
import com.nhom10.quanlikhachsan.services.HotelTypeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private CityService cityService;
    @Autowired
    private HotelTypeService hotelTypeService;
    @GetMapping("")
    public String home(Model model){
        City HCMCity = cityService.getCityById(1L);
        City HaNoiCity = cityService.getCityById(2L);
        City DNCity = cityService.getCityById(3L);
        City HueCity = cityService.getCityById(4L);
        City HoiAnCity = cityService.getCityById(5L);

        model.addAttribute("list_city", cityService.getAllCitiesActive());
        model.addAttribute("list_hotelType", hotelTypeService.getAllHotelTypeActive());
        model.addAttribute("HoChiMinh",HCMCity);
        model.addAttribute("HaNoi",HaNoiCity);
        model.addAttribute("DaNang",DNCity);
        model.addAttribute("Hue",HueCity);
        model.addAttribute("HoiAn",HoiAnCity);

        return "client/home/index";
    }
    @PostMapping("/search")
    public String search(@RequestParam("keyword") String keyword,
                         @RequestParam("ngayden") LocalDate ngayden,
                         @RequestParam("ngaydi") LocalDate ngaydi,
                         HttpSession session,
                         RedirectAttributes redirectAttributes){

        session.setAttribute("checkin", ngayden);
        session.setAttribute("checkout",ngaydi);
        City city = cityService.searchCity(keyword);
        if(city == null){
            return "client/error/405";
        }
        redirectAttributes.addAttribute("id", city.getId());
        return "redirect:/hotel/{id}/1";
    }
}

