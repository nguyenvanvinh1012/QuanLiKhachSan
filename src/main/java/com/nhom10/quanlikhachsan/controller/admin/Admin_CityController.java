package com.nhom10.quanlikhachsan.controller.admin;

import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.services.CityService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/city")
public class Admin_CityController {
    @Autowired
    private CityService cityService;
    @GetMapping("/{pageNo}")
    public String index(@PathVariable (value = "pageNo") int pageNo, Model model){
        int pageSize = 5;
        Page<City> page = cityService.findPaginated(pageNo, pageSize);
        List<City> listCity = page.getContent();
        model.addAttribute("list_city", listCity);
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
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
        return "redirect:/admin/city/1";
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
        return "redirect:/admin/city/1";
    }

    @GetMapping("/search")
    public String searchCity2(
            @NotNull Model model,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<City> searchedCity2 = cityService.searchCity2(keyword, pageNo, pageSize, sortBy);
        model.addAttribute("list_hotel", searchedCity2.getContent());
        model.addAttribute("currentPage", searchedCity2.getNumber());
        model.addAttribute("totalPages", searchedCity2.getTotalPages());
        return "admin/hotel/index";
    }
}
