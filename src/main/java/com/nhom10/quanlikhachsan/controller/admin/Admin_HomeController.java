package com.nhom10.quanlikhachsan.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class Admin_HomeController {

    @RequestMapping("")
    public String home(){
        return "admin/home/index";
    }

}
