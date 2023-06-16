package com.nhom10.quanlikhachsan.controller.admin;
import com.nhom10.quanlikhachsan.entity.Role;
import com.nhom10.quanlikhachsan.services.RoleSevervice;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/role")
public class Admin_RoleController {
    @Autowired
    private RoleSevervice roleSevervice;
    @GetMapping("/{pageNo}")
    public String index(@PathVariable (value = "pageNo") int pageNo, Model model){
        int pageSize = 5;
        Page<Role> page = roleSevervice.findPaginated(pageNo, pageSize);
        List<Role> listRole = page.getContent();
        model.addAttribute("list_role", listRole);
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        return "admin/role/index";
    }
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("list_role", roleSevervice.getAllRole());
        model.addAttribute("role", new Role());
        return "admin/role/add";
    }

    @PostMapping("/add")
    public String addRole(@Valid @ModelAttribute("role") Role role, BindingResult bindingResult, Model model,
                           RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error",
                        error.getDefaultMessage());
            }
            return "admin/role/add";
        }

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
    public String edit(@Valid @ModelAttribute("role") Role updateRole, BindingResult bindingResult, Model model,
                       RedirectAttributes redirectAttributes)throws IOException{
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error",
                        error.getDefaultMessage());
            }
            return "admin/role/edit";
        }


        roleSevervice.updateRole(updateRole);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin/role/1";
    }
    @GetMapping("/search")
    public String searchRole(
            @NotNull Model model,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<Role> searchedRoles = roleSevervice.searchRole(keyword, pageNo, pageSize, sortBy);
        model.addAttribute("list_role", searchedRoles.getContent());
        model.addAttribute("currentPage", searchedRoles.getNumber());
        model.addAttribute("totalPages", searchedRoles.getTotalPages());
        return "admin/role/index";
    }
}
