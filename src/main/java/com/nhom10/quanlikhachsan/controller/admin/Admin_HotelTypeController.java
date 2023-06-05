package com.nhom10.quanlikhachsan.controller.admin;

import com.nhom10.quanlikhachsan.FileUploadUtil;
import com.nhom10.quanlikhachsan.entity.HotelType;
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
@RequestMapping("/admin-hotelType")
public class Admin_HotelTypeController {
    @Autowired
    private HotelTypeService hotelTypeService;
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("list_hotelType", hotelTypeService.getAllCities());
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }
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
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        hotelType.setImage(fileName);
        hotelType.setActive(true);
        HotelType savedhotelType  = hotelTypeService.addHotelType2(hotelType);
        String upLoadDir = "hotelType-images/" + savedhotelType.getId();
        FileUploadUtil.saveFile(upLoadDir, fileName, multipartFile);
        return "redirect:/admin-hotelType";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        HotelType editHotelType = null;
        for(HotelType hotelType: hotelTypeService.getAllCities()){
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

        HotelType hotelType = hotelTypeService.getHotelTypeById(updateHotelType.getId());
        hotelType.setName(updateHotelType.getName());
        hotelType.setDescription(updateHotelType.getDescription());
        hotelType.setActive(updateHotelType.getActive());
        if(multipartFile != null && !multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            hotelType.setImage(fileName);
            String upLoadDir = "hotelType-images/" + hotelType.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName, multipartFile);
        }
        hotelTypeService.updateHotelType(hotelType);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin-hotelType";
    }
}
