package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.entity.Role;
import com.nhom10.quanlikhachsan.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleSevervice {
    @Autowired
    private IRoleRepository roleRepository;
    public List<Role> getAllRole(){
        return roleRepository.findAll();
    }
    public Role getRoleById(Long id){
        Optional<Role> optional = roleRepository.findById(id);
        return optional.orElse(null);
    }
    public Role addRole(Role city){
        return roleRepository.save(city);
    }
    public void updateRole(Role city){
        roleRepository.save(city);
    }
    public void deleteRole(Long id){
        roleRepository.deleteById(id);
    }


}
