package net.javaguides.springboot.service;

import net.javaguides.springboot.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserOtpService {
    User findByEmail(String email);

    void saveOtpCode(String email, String otpCode);

    boolean isValidOtpCode(String email, String otpCode);

    void updatePassword(String email, String newPassword);
}
