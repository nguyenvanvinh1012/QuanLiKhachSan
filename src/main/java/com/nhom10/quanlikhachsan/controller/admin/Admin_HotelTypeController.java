package com.nhom10.quanlikhachsan.controller.admin;

import com.nhom10.quanlikhachsan.ultils.FileUploadUtil;
import com.nhom10.quanlikhachsan.entity.HotelType;
import com.nhom10.quanlikhachsan.services.HotelTypeService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/hotelType")
public class Admin_HotelTypeController {
    @Autowired
    private HotelTypeService hotelTypeService;

    @GetMapping("/{pageNo}")
    public String index(@PathVariable (value = "pageNo") int pageNo, Model model){
        int pageSize = 5;
        Page<HotelType> page = hotelTypeService.findPaginated(pageNo, pageSize);
        List<HotelType> listHotelType = page.getContent();
        model.addAttribute("list_hotelType", listHotelType);
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        return "admin/hotelType/index";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("hotelType", new HotelType());
        return "admin/hotelType/add";
    }

    @PostMapping("/add")
    public String addHotelType(@ModelAttribute("hotelType") HotelType hotelType,
                               @RequestParam("img") MultipartFile multipartFile) throws IOException {
        hotelTypeService.addHotelType(hotelType,multipartFile);
        return "redirect:/admin/hotelType/1";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        HotelType editHotelType = null;
        for(HotelType hotelType: hotelTypeService.getAllHotelType()){
            if(hotelType.getId() == id){
                editHotelType  = hotelType;
            }
        }
        if(editHotelType != null){
            model.addAttribute("hotelType", editHotelType);
            return "admin/hotelType/edit";
        }else {
            return "not-found";
        }
    }

    @PostMapping("edit")
    public String edit(@ModelAttribute("hotelType") HotelType updateHotelType,
                       @RequestParam("img") MultipartFile multipartFile,
                       RedirectAttributes redirectAttributes)throws IOException{

        hotelTypeService.updateHotelType(updateHotelType,multipartFile);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin/hotelType/1";
    }

    @GetMapping("/search")
    public String searchHotelType(
            @NotNull Model model,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<HotelType> searchedHotelType = hotelTypeService.searchHotelType(keyword, pageNo, pageSize, sortBy);
        model.addAttribute("list_hotelType", searchedHotelType.getContent());
        model.addAttribute("currentPage", searchedHotelType.getNumber());
        model.addAttribute("totalPages", searchedHotelType.getTotalPages());
        return "admin/hotelType/index";
    }
}
