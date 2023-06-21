package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.constants.Provider;
import com.nhom10.quanlikhachsan.entity.User;
import com.nhom10.quanlikhachsan.repository.IRoleRepository;
import com.nhom10.quanlikhachsan.repository.IUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;

    public User findUserByUserName(String userName){
        return userRepository.findByUsername(userName);
    }
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public void save(User user) {
        user.setPassword(new BCryptPasswordEncoder()
                .encode(user.getPassword()));
        userRepository.save(user);

        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if(roleId != 0 && userId != 0){
            userRepository.addRoleToUser(userId,roleId);
        }
    }
    public void saveOauthUser(String email, String username) {
        User usert = userRepository.findByUsername(username);
        if (usert != null){
            return;
        }
        var user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFull_name(email);
        user.setPassword(new BCryptPasswordEncoder().encode(username));
        user.setProvider(Provider.GOOGLE.value);
        userRepository.save(user);

        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if(roleId != 0 && userId != 0){
            userRepository.addRoleToUser(userId,roleId);
        }
    }

    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException{
        User user = userRepository.findByEmail(email);
        if(user != null){
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any user with email "+ email);
        }
    }
    public User getByResetPasswordToken(String resetPasswordToken){
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }
    public void updatePassword(User user, String newPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodePassword);
        user.setResetPasswordToken(null);

        userRepository.save(user);
    }
}
