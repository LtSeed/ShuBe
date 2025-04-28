package org.example.shubackend.service.crud;

import org.example.shubackend.entity.User;
import org.example.shubackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserCrudService extends GenericCrudService<User, Integer> {
    public UserCrudService(UserRepository repo) {
        super(repo);
    }
}



