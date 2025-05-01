package org.example.shubackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
        @NotBlank String username,
        String phone,      // 手机号
        @Email String email,     // 邮箱
        @NotBlank String newPassword, // 新密码
        @NotBlank String confirmPassword // 确认密码
) {}