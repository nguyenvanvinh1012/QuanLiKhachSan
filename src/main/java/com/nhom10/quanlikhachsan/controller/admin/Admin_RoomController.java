package com.nhom10.quanlikhachsan.controller.admin;

import com.nhom10.quanlikhachsan.ultils.FileUploadUtil;
import com.nhom10.quanlikhachsan.entity.Room;
import com.nhom10.quanlikhachsan.services.HotelService;
import com.nhom10.quanlikhachsan.services.RoomService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/room")
public class Admin_RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private HotelService hotelService;

    @GetMapping("/{pageNo}")
    public String index(@PathVariable (value = "pageNo") int pageNo, Model model){
        int pageSize = 5;
        Page<Room> page = roomService.findPaginated(pageNo, pageSize);
        List<Room> listRoom = page.getContent();
        model.addAttribute("list_room", listRoom);
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        return "admin/room/index";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("list_hotel", hotelService.getAllHotel());
        model.addAttribute("room", new Room());
        return "admin/room/add";
    }

    @PostMapping("/add")
    public String addRoom(@Valid @ModelAttribute("room") Room room, BindingResult bindingResult,
                          @RequestParam("img") MultipartFile multipartFile,
                          @RequestParam("bed_type")  Integer bed_type, Model model,
                          RedirectAttributes redirectAttributes ) throws IOException {

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error",
                        error.getDefaultMessage());
            }
            return "admin/room/add";
        }
        roomService.addRoom(room,bed_type,multipartFile);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin/room/1";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        Room editRoom = null;
        for(Room room: roomService.getAllRoom()){
            if(room.getId() == id){
                editRoom  = room;
            }
        }
        if(editRoom != null){
            model.addAttribute("room", editRoom    );
            model.addAttribute("list_hotel", hotelService.getAllHotel());
            return "admin/room/edit";
        }else {
            return "not-found";
        }
    }

    @PostMapping("edit")
    public String edit(@ModelAttribute("room") Room updateRoom,
                       @RequestParam("img") MultipartFile multipartFile,
                       @RequestParam("bed_type")  Integer bed_type,
                       RedirectAttributes redirectAttributes)throws IOException{
        roomService.updateRoom(updateRoom,bed_type,multipartFile);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin/room/1";
    }

    @GetMapping("/search")
    public String searchRoom(
            @NotNull Model model,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<Room> searchedRoom = roomService.searchRoom(keyword, pageNo, pageSize, sortBy);
        model.addAttribute("list_room", searchedRoom.getContent());
        model.addAttribute("currentPage", searchedRoom.getNumber());
        model.addAttribute("totalPages", searchedRoom.getTotalPages());
        return "admin/room/index";
    }
}
