package com.nhom10.quanlikhachsan.services;

import com.nhom10.quanlikhachsan.entity.Role;
import com.nhom10.quanlikhachsan.entity.User;
import com.nhom10.quanlikhachsan.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private IAccountRepository accountRepository;
    public List<User> getAllAccounts(){
        return accountRepository.findAll();
    }
    public User getAccountById(Long id){
        Optional<User> optional = accountRepository.findById(id);
        return optional.orElse(null);
    }
    public void updateAccount(User user){
        accountRepository.save(user);
    }
    public Page<User> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.accountRepository.findAll(pageable);
    }
    public Page<User> searchAccount(String keyword, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy));
        return accountRepository.searchAccount(keyword, pageable);
    }
}
