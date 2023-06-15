package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.entity.Role;
import com.nhom10.quanlikhachsan.repository.IRoleRepository;
import com.nhom10.quanlikhachsan.ultils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
    public void addRole(Role role) throws IOException {
        roleRepository.save(role);
    }
    public void updateRole(Role role){
        Role savedHotel = roleRepository.save(role);
        roleRepository.save(role);
    }
    public void deleteRole(Long id){
        roleRepository.deleteById(id);
    }
    public Page<Role> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.roleRepository.findAll(pageable);
    }

    public Page<Role> searchRole(String keyword, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy));
        return roleRepository.searchRole(keyword, pageable);
    }

}
