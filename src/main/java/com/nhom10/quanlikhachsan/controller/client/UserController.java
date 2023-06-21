package com.nhom10.quanlikhachsan.controller.client;

import com.nhom10.quanlikhachsan.entity.BookRoom;
import com.nhom10.quanlikhachsan.services.BookRoomService;
import com.nhom10.quanlikhachsan.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.nhom10.quanlikhachsan.entity.User;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    BookRoomService bookRoomService;
    @GetMapping("/login")
    public String login() {
        return "client/user/login";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new  User());
        return "client/user/register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error",
                        error.getDefaultMessage());
            }
            return "client/user/register";
        }
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByUserName(username);
        model.addAttribute("user",user);
        return "client/user/dashboard";
    }
    @GetMapping("/bookedRoom")
    public String bookedRoom(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByUserName(username);
        List<BookRoom> bookRooms = bookRoomService.getAllBookRoomOfUser(user.getId());
        model.addAttribute("list_bookRoom", bookRooms);
        return "client/user/booked_room";
    }
    @GetMapping("/cancelRoom/{id}")
    public String cancel_Room(@PathVariable("id") Long id){
        bookRoomService.deleteBookRoom(id);
        return "redirect:/bookedRoom";
    }

}
