package com.nhom10.quanlikhachsan.controller.admin;

import com.nhom10.quanlikhachsan.entity.User;
import com.nhom10.quanlikhachsan.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/account")
public class Admin_AccountController {
    @Autowired
    private AccountService accountService;
    //    @GetMapping("")
//    public String index(Model model){
//        model.addAttribute("list_account", accountService.getAllAccounts());
//        if(model.containsAttribute("message")){
//            model.addAttribute("message", model.getAttribute("message"));
//        }
//        return "admin/account/index";
//    }
    @GetMapping("/{pageNo}")
    public String index(@PathVariable(value = "pageNo") int pageNo, Model model){
        int pageSize = 5;
        Page<User> page = accountService.findPaginated(pageNo, pageSize);
        List<User> listAccount = page.getContent();
        model.addAttribute("list_account", listAccount);
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        return "admin/account/index";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        User editUser = null;
        for(User user: accountService.getAllAccounts()){
            if(user.getId() == id){
                editUser = user;
            }
        }
        if(editUser != null){
            model.addAttribute("account", editUser);
            return "admin/account/edit";
        }else {
            return "not-found";
        }
    }

    @PostMapping("edit")
    public String edit(@ModelAttribute("account") User updateUser,
//                       @RequestParam("img") MultipartFile multipartFile,
                       RedirectAttributes redirectAttributes)throws IOException {

        User user = accountService.getAccountById(updateUser.getId());
        user.setActive(updateUser.getActive());
        accountService.updateAccount(user);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin/account/1";
    }
}

