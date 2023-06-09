package com.nhom10.quanlikhachsan.controller.admin;

import com.nhom10.quanlikhachsan.FileUploadUtil;
import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.entity.Room;
import com.nhom10.quanlikhachsan.services.HotelService;
import com.nhom10.quanlikhachsan.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/admin-room")
public class Admin_RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private HotelService hotelService;
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("list_room", roomService.getAllRoom());
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }
        return "admin/room/index";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("list_hotel", hotelService.getAllHotel());
        model.addAttribute("room", new Room());
        return "admin/room/add";
    }

    @PostMapping("/add")
    public String addRoom(@ModelAttribute("room") Room room,
                               @RequestParam("img") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        room.setImage(fileName);
        Room savedroom  = roomService.addRoom2(room);
        String upLoadDir = "room-images/" + savedroom.getId();
        FileUploadUtil.saveFile(upLoadDir, fileName, multipartFile);
        return "redirect:/admin-room";
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
                       RedirectAttributes redirectAttributes)throws IOException{

        Room room = roomService.getRoomById(updateRoom.getId());
        room.setName(updateRoom.getName());
        room.setArea(updateRoom.getArea());
        room.setRent(updateRoom.getRent());
        room.setConvenient(updateRoom.getConvenient());
        room.setBed_type(updateRoom.getBed_type());
        room.setDescription(updateRoom.getDescription());
        room.setHotel(updateRoom.getHotel());
        if(multipartFile != null && !multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            room.setImage(fileName);
            String upLoadDir = "room-images/" + room.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName, multipartFile);
        }
        roomService.updateRoom(room);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin-room";
    }
}
