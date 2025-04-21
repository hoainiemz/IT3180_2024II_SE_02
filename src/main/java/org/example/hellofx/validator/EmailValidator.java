package org.example.hellofx.validator;

import org.example.hellofx.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailValidator {
    @Autowired
    private AccountService accountService;

    public String emailSignupCheck(String value) {
        if (value == null || value.isEmpty()) {
            return "Địa chỉ email không được bỏ trống!";
        }
        if (!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(value)) {
            return "Email không đúng định dạng!";
        }
        if (accountService.checkAccountExistByEmail(value)) {
            return "Địa chỉ email đã được sử dụng cho tài khoản khác!";
        }
        return "OK!";
    }

    public String emailCheck(String value) {
        if (value == null || value.isEmpty()) {
            return "Địa chỉ email không được bỏ trống!";
        }
        if (!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(value)) {
            return "Email không đúng định dạng!";
        }
        return "OK!";
    }
}
