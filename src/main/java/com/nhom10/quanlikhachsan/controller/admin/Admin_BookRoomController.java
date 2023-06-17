package com.nhom10.quanlikhachsan.controller.admin;

import com.nhom10.quanlikhachsan.entity.BookRoom;
import com.nhom10.quanlikhachsan.services.BookRoomService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping ("/admin/bookRoom")
public class Admin_BookRoomController {
    @Autowired
    private BookRoomService bookRoomService;
    @GetMapping("/{pageNo}")
    public String index(@PathVariable(value = "pageNo") int pageNo, Model model){
        int pageSize = 5;
        Page<BookRoom> page = bookRoomService.findPaginated(pageNo, pageSize);
        List<BookRoom> listBookRoom = page.getContent();
        model.addAttribute("list_bookRoom", listBookRoom);
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        return "admin/bookRoom/index";
    }
    @GetMapping("/search")
    public String searchBookRoom(
            @NotNull Model model,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<BookRoom> searchedBookRoom = bookRoomService.searchBookRoom(keyword, pageNo, pageSize, sortBy);
        model.addAttribute("list_bookRoom", searchedBookRoom.getContent());
        model.addAttribute("currentPage", searchedBookRoom.getNumber());
        model.addAttribute("totalPages", searchedBookRoom.getTotalPages());
        return "admin/bookRoom/index";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        bookRoomService.deleteBookRoom(id);
        return "redirect:/admin/bookRoom/1";
    }
}
