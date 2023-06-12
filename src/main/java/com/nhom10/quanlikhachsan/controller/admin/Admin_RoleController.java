package com.nhom10.quanlikhachsan.controller.admin;
import com.nhom10.quanlikhachsan.entity.Role;
import com.nhom10.quanlikhachsan.services.RoleSevervice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;

@Controller
@RequestMapping("/admin/role")
public class Admin_RoleController {
    @Autowired
    private RoleSevervice roleSevervice;
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("list_role", roleSevervice.getAllRole());
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }
        return "admin/role/index";

    }
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("list_role", roleSevervice.getAllRole());
        model.addAttribute("role", new Role());
        return "admin/role/add";
    }

    @PostMapping("/add")
    public String addRole(@ModelAttribute("role") Role role,
                           RedirectAttributes redirectAttributes) throws IOException {

        roleSevervice.addRole(role);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin/role";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Role editRole = null;
        for(Role role: roleSevervice.getAllRole()){
            if(role.getId() == id){
                editRole = role;
            }
        }
        if(editRole != null){
            model.addAttribute("role", editRole);
            model.addAttribute("list_role", roleSevervice.getAllRole());
            return "admin/role/edit";
        }else {
            return "not-found";
        }
    }
    @PostMapping("/edit")
    public String edit(@ModelAttribute("role") Role updateRole,
                       RedirectAttributes redirectAttributes)throws IOException{


        roleSevervice.updateRole(updateRole);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin/role";
    }
}
