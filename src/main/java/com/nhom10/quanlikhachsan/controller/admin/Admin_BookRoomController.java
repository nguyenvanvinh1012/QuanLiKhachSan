package com.nhom10.quanlikhachsan.controller.admin;

import com.nhom10.quanlikhachsan.services.BookRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/admin/bookRoom")
public class Admin_BookRoomController {
    @Autowired
    private BookRoomService bookRoomService;
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("list_bookRoom", bookRoomService.getAllBookRoom());
        return "admin/bookRoom/index";
    }
}
