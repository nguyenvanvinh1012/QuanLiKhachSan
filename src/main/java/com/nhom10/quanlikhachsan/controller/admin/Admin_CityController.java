package com.nhom10.quanlikhachsan.controller.admin;

import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/admin/city")
public class Admin_CityController {
    @Autowired
    private CityService cityService;
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("list_city", cityService.getAllCities());
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }
        return "admin/city/index";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("city", new City());
        return "admin/city/add";
    }

    @PostMapping("/add")
    public String addCity(@ModelAttribute("city") City city,
                          @RequestParam("img") MultipartFile multipartFile,
                          RedirectAttributes redirectAttributes) throws IOException {
        cityService.addCity(city, multipartFile);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin/city";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        City editCity = null;
        for(City city: cityService.getAllCities()){
            if(city.getId() == id){
                editCity = city;
            }
        }
        if(editCity != null){
            model.addAttribute("city", editCity);
            return "admin/city/edit";
        }else {
            return "not-found";
        }
    }

    @PostMapping("edit")
    public String edit(@ModelAttribute("city") City updateCity,
                       @RequestParam("img") MultipartFile multipartFile,
                       RedirectAttributes redirectAttributes)throws IOException{

        cityService.updateCity(updateCity, multipartFile);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin/city";
    }
}
