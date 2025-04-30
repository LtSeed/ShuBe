package org.example.shubackend.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shubackend.entity.User;
import org.example.shubackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void resetPassword(@NotBlank String username, String phone, @Email String email, @NotBlank String newPassword) {
        // 1. 验证手机和邮箱是否匹配用户

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("账号或验证信息错误"));
        if (!user.getPhone().equals(phone)
                || !user.getEmail().equalsIgnoreCase(email)) {
            throw new RuntimeException("账号或验证信息错误");
        }
        // 2. 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}